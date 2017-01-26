package com.paymybill.service;

import com.paymybill.controller.model.UserDTO;
import com.paymybill.dao.RoleDAO;
import com.paymybill.dao.UserDAO;
import com.paymybill.dao.model.Goal;
import com.paymybill.dao.model.Role;
import com.paymybill.dao.model.User;
import com.paymybill.exception.EmailExistException;
import com.paymybill.exception.UsernameExistException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.persistence.NoResultException;
import java.util.Collection;

@Service
public class UserServiceImpl implements UserService {

    private Logger logger = Logger.getLogger(this.getClass());

    private UserDAO userDAO;
    private RoleDAO roleDAO;
    private PasswordEncoder passwordEncoder;
    private ApplicationContext context;

    @Autowired
    public UserServiceImpl(UserDAO userDAO, RoleDAO roleDAO, PasswordEncoder passwordEncoder, ApplicationContext context) {
        logger.trace("UserServiceImpl constructor");
        this.userDAO = userDAO;
        this.roleDAO = roleDAO;
        this.passwordEncoder = passwordEncoder;
        this.context = context;
    }

    @Override
    public User findByUsername(String username) {
        logger.trace("findByUsername method with arguments {username:" + username + "}");
        Assert.notNull(username, "username can't be null");
        User user;
        try {
            user = userDAO.findByUsername(username);
        } catch (NoResultException e) {
            user = null;
            logger.warn("User with username " + username + " not found.");
        }
        return user;
    }

    @Override
    public User findByEmail(String email) {
        logger.trace("findByEmail method with arguments {email:" + email + "}");
        Assert.notNull(email, "email can't be null");
        User user;
        try {
            user = userDAO.findByEmail(email);
        } catch (NoResultException e) {
            logger.warn("User with email " + email + " not found.");
            user = null;
        }
        return user;
    }

    @Override
    public boolean isUsernameBusy(String username) {
        Assert.notNull(username, "username can't be null");
        logger.trace("isUsernameBusy method with arguments {username:" + username + "}");
        User user = this.findByUsername(username);
        return user != null;
    }

    @Override
    public boolean isEmailBusy(String email) {
        Assert.notNull(email, "email can't be null");
        logger.trace("isEmailBusy method with arguments {email:" + email + "}");
        User user = this.findByEmail(email);
        return user != null;
    }

    @Override
    public void addUserGoal(User user, Goal goal) {
        Collection<Goal> goalCollection = user.getGoalCollection();
        goalCollection.add(goal);
        userDAO.update(user);
    }

    @Transactional
    @Override
    public User registerUser(UserDTO userDTO) throws UsernameExistException, EmailExistException {
        Assert.notNull(userDTO, "userDTO can't be null");
        logger.trace("registerUser method with arguments = " + userDTO.toString());

        String username = userDTO.getUsername();
        boolean usernameBusy = isUsernameBusy(username);
        if (usernameBusy) {
            throw new UsernameExistException(context.getMessage("UsernameExistException", null, LocaleContextHolder.getLocale()));
        }

        String email = userDTO.getEmail();
        boolean emailBusy = isEmailBusy(email);
        if (emailBusy) {
            throw new EmailExistException(context.getMessage("EmailExistException", null, LocaleContextHolder.getLocale()));
        }

        String password = passwordEncoder.encode(userDTO.getPassword());

        String firstName = userDTO.getFirstName();

        Role userRole = roleDAO.findUserRole();

        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean accountNonLocked = true;
        boolean credentialsNonExpired = true;

        // TODO
        User user = new User();
        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(password);
        user.setFirstName(firstName);
        user.setEnabled(enabled);
        user.setAccountNonExpired(accountNonExpired);
        user.setAccountNonLocked(accountNonLocked);
        user.setCredentialsNonExpired(credentialsNonExpired);
        user.setRole(userRole);


        return userDAO.create(user);
    }
}
