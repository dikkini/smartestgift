package com.paymybill.service;

import com.paymybill.dao.UserDAO;
import com.paymybill.dao.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import javax.persistence.NoResultException;

public class YetAuthenticationProvider extends DaoAuthenticationProvider {

    @Autowired
    private LoginAttemptService loginAttemptService;

    @Autowired
    private UserDAO userDAO;

    @Override
    public Authentication authenticate(Authentication auth) throws AuthenticationException {
        User user;
        try {
            user = userDAO.findByEmail(auth.getName());
            Authentication result = super.authenticate(auth);
            return new UsernamePasswordAuthenticationToken(user, result.getCredentials(), result.getAuthorities());
        } catch (NoResultException e) {
            throw new BadCredentialsException("bad_credentials");
        } catch (BadCredentialsException e) {
            loginAttemptService.loginFailed(auth.getName());
            throw new BadCredentialsException("bad_credentials");
        } catch (LockedException e) {
            throw new LockedException("locked");
        } catch (AccountExpiredException e) {
            throw new AccountExpiredException("expired");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
