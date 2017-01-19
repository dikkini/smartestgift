package com.paymybill.service;

import com.paymybill.controller.model.UserDTO;
import com.paymybill.dao.RoleDAO;
import com.paymybill.dao.UserDAO;
import com.paymybill.dao.model.Role;
import com.paymybill.dao.model.User;
import com.paymybill.exception.EmailExistException;
import com.paymybill.exception.UsernameExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private RoleDAO roleDAO;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ApplicationContext context;

    @Override
    public User findBySocialId(String socialId) {
        return null;
    }

    @Override
    public User findByUsername(String username) {
        User user;
        try {
            user = userDAO.findByUsername(username);
        } catch (NoResultException e) {
            user = null;
        }
        return user;
    }

    @Override
    public User findByEmail(String email) {
        User user;
        try {
            user = userDAO.findByEmail(email);
        } catch (NoResultException e) {
            user = null;
        }
        return user;
    }

    @Override
    public boolean isUsernameBusy(String username) {
        User user = this.findByUsername(username);
        return user != null;
    }

    @Override
    public boolean isEmailBusy(String username) {
        User user = this.findByEmail(username);
        return user != null;
    }

    @Transactional
    @Override
    public User registerUser(UserDTO userDTO) throws UsernameExistException, EmailExistException {

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

    @Override
    public User update(User user) {
        return userDAO.update(user);
    }
}
