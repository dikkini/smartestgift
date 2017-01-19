package com.paymybill.service;

import com.paymybill.dao.UserDAO;
import com.paymybill.dao.model.Privilege;
import com.paymybill.dao.model.User;
import com.paymybill.exception.UserBlockedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Transactional
@Service("userDetailsService")
public class YetUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private LoginAttemptService loginAttemptService;

    public YetUserDetailsServiceImpl() {
        super();
    }

    @Override
    public UserDetails loadUserByUsername(final String inputData) throws UsernameNotFoundException {
        boolean blocked = loginAttemptService.isBlocked(inputData);
        if (blocked) {
            throw new UserBlockedException("user_blocked_message", 1000);
        }

        final User user = userDAO.findByEmail(inputData);

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (final Privilege privilege : user.getRole().getPrivileges()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(privilege.getName()));
        }

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                user.getEnabled(), user.getAccountNonExpired(), user.getCredentialsNonExpired(),
                user.getAccountNonLocked(), grantedAuthorities);
    }
}
