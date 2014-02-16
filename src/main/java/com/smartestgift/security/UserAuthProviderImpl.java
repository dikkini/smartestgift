package com.smartestgift.security;

import com.smartestgift.dao.SmartUserDAO;
import com.smartestgift.dao.SmartUserDetailsDAO;
import com.smartestgift.dao.model.SmartUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.smartestgift.utils.Utils.isEmail;

/**
 * Created by dikkini on 27.01.14.
 * Email: dikkini@gmail.com
 */

@Service
@Transactional
public class UserAuthProviderImpl implements UserAuthProvider {

    @Autowired
    private SmartUserDAO smartUserDAO;

    public UserDetails loadUserByUsername(String login)
            throws UsernameNotFoundException {
        if (isEmail(login)) {
            return smartUserDAO.findSmartUserByEmail(login).getSmartUserDetails();
        } else {
            return smartUserDAO.findSmartUserByUsername(login).getSmartUserDetails();
        }
    }

    @Override
    public void authenticateUser(SmartUserDetails smartUserDetails, HttpServletRequest request) {
        Authentication authRequest = new UsernamePasswordAuthenticationToken(smartUserDetails, null,
                    smartUserDetails.getAuthorities());

        // Authenticate the user
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authRequest);

        // Create a new session and add the security context.
        HttpSession session = request.getSession(true);
        session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
    }
}