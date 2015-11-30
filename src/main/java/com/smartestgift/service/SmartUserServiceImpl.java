package com.smartestgift.service;

import com.smartestgift.controller.model.RegisterSmartUserDTO;
import com.smartestgift.dao.*;
import com.smartestgift.dao.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

import static com.smartestgift.utils.ApplicationConstants.*;

/**
 * Created by dikkini on 18.02.14.
 * Email: dikkini@gmail.com
 */
@Service
public class SmartUserServiceImpl implements SmartUserService {

    @Autowired
    private SmartUserDAO smartUserDAO;

    @Autowired
    private GiftDAO giftDAO;

    @Autowired
    private RoleDAO roleDAO;

    @Autowired
    private FileDAO fileDAO;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public SmartUser findByUsername(String username) {
        return smartUserDAO.findByUsername(username);
    }

    @Override
    public SmartUser create(RegisterSmartUserDTO created) {
        // TODO решить вопрос с файлом
        SmartUser smartUser = SmartUser.getBuilder(created.getUsername(), created.getEmail(), created.getPassword(),
                created.getFirstName(), created.getLastName(), created.getAddress(), created.getAuthProviderId(),
                created.getRegistrationDate()).build();
        smartUser.setFile(fileDAO.findOne(FILE_USER_NO_PHOTO_ID));
        return smartUserDAO.create(smartUser);
    }

    @Override
    public SmartUser create(SmartUser smartUser) {
        // TODO решить вопрос с файлом
        smartUser.setFile(fileDAO.findOne(FILE_USER_NO_PHOTO_ID));
        return smartUserDAO.create(smartUser);
    }


    @Override
    public SmartUser findExistSocialUser(String socialId, Integer providerId) {
        return smartUserDAO.findBySocialIdAndAuthProvider(socialId, providerId);
    }

    @Override
    public boolean isUsernameBusy(String username) {
        SmartUser userDetailsByUsername = smartUserDAO.findByUsername(username);
        return userDetailsByUsername != null;
    }

    @Override
    public boolean isEmailBusy(String email) {
        SmartUser smartUserDetailsByEmail = smartUserDAO.findByEmail(email);
        return smartUserDetailsByEmail != null;
    }

    @Override
    public void createUserAuthority(String username) {
        Role role = roleDAO.findOne(USER_ROLE_ID);
        SmartUser smartUser = smartUserDAO.findByUsername(username);
        UserRole userRole = new UserRole(role, smartUser);
        smartUser.getUserRoles().add(userRole);
        smartUserDAO.create(smartUser);
    }

    @Override
    public void authenticateUser(String userName, String password, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(userName, password);

        // Authenticate the user
        Authentication authentication = authenticationManager.authenticate(authRequest);
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);

        // Create a new session and add the security context.
        HttpSession session = request.getSession(true);
        session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
    }

    @Override
    public List<SmartUser> findByUserInput(String name, SmartUser activeUser) {
        List<SmartUser> resultList = new ArrayList<>();
        String[] split = name.split(" ");
        for (String str : split) {
            if (str.equals("")) {
                continue;
            }
            resultList.addAll(smartUserDAO.findLikeUsername(str, activeUser.getUsername()));
            resultList.addAll(smartUserDAO.findLikeLastName(str, activeUser.getLastName()));
            resultList.addAll(smartUserDAO.findLikeFirstName(str, activeUser.getFirstName()));
            resultList.addAll(smartUserDAO.findLikeMiddleName(str, activeUser.getMiddleName()));
        }

        return removeDuplicates(resultList);
    }

    // TODO вынести метод в api (?)
    @Override
    public Map<String, List> findUsersAndGiftsByUserInput(String searchString, SmartUser activeUser) {
        List<SmartUser> usersByUserInput = findByUserInput(searchString, activeUser);
        List<Gift> giftsBySearchString = giftDAO.findGiftsBySearchString(searchString);
        Map<String, List> result = new HashMap<>();
        result.put(GIFTS_SEARCH_RESULTS, giftsBySearchString);
        result.put(USERS_SEARCH_RESULTS, usersByUserInput);

        return result;
    }

    @Override
    public List<SmartUser> findWithOffset(int offset, SmartUser smartUser) {
        return smartUserDAO.findByOffset(offset, smartUser.getUuid());
    }

    @Override
    public void update(SmartUser smartUser) {
        smartUserDAO.create(smartUser);
    }

    @Override
    public void update(RegisterSmartUserDTO updatedDTO) {
        // TODO
    }

    private List<SmartUser> removeDuplicates(List<SmartUser> l) {
        Set<SmartUser> s = new TreeSet<>(new Comparator<SmartUser>() {

            @Override
            public int compare(SmartUser o1, SmartUser o2) {
                if (o1.getUuid().equals(o2.getUuid())) {
                    return 0;
                }
                return 1;
            }
        });
        s.addAll(l);
        return new ArrayList<>(s);
    }
}
