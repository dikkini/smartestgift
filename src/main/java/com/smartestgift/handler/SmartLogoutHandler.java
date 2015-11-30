package com.smartestgift.handler;

import com.smartestgift.dao.TokenDAO;
import com.smartestgift.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by dikkini on 27.01.14.
 * Email: dikkini@gmail.com
 */

@Service
public class SmartLogoutHandler implements LogoutHandler {

    @Autowired
    TokenDAO tokenDAO;

    @Autowired
    MessageService messageService;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        try {
            // TODO очистка cookies и прочие непотребства
            authentication.setAuthenticated(false);
            response.sendRedirect(request.getContextPath() + "/login");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
