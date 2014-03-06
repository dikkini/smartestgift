package com.smartestgift.handler;

import com.smartestgift.dao.model.SmartUser;
import com.smartestgift.dao.model.SmartUserDetails;
import com.smartestgift.utils.ActiveUser;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.security.Principal;

/**
 * Created by dikkini on 07.03.14.
 * Email: dikkini@gmail.com
 */
public class CurrentUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return
                methodParameter.getParameterAnnotation(ActiveUser.class) != null
                        && methodParameter.getParameterType().equals(SmartUserDetails.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) throws Exception {

        if (this.supportsParameter(methodParameter)) {
            Principal principal = webRequest.getUserPrincipal();
            return (SmartUserDetails) ((Authentication) principal).getPrincipal();
        } else {
            return WebArgumentResolver.UNRESOLVED;
        }
    }
}