package com.smartestgift.handler;

import com.smartestgift.dao.model.SmartUser;
import com.smartestgift.service.SmartUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by dikkini on 09.02.14.
 * Email: dikkini@gmail.com
 */
@Component
public class UserInterceptor implements HandlerInterceptor {

    @Autowired
    private SmartUserService smartUserService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object o, ModelAndView mav)
            throws Exception {
        if (mav != null) {
            SmartUser smartUser = smartUserService.findUserByUsername(SecurityContextHolder.getContext()
                    .getAuthentication().getName());
            mav.addObject("smartUser", smartUser);
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object o, Exception e)
            throws Exception {

    }
}