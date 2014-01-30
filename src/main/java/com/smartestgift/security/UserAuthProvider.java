package com.smartestgift.security;

import com.smartestgift.dao.SmartUserDAO;
import com.smartestgift.dao.model.SmartUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by dikkini on 27.01.14.
 * Email: dikkini@gmail.com
 */

@Service
@Transactional(readOnly=true)
public class UserAuthProvider implements UserDetailsService {

    @Autowired
    private SmartUserDAO smartUserDAO;

    public UserDetails loadUserByUsername(String login)
            throws UsernameNotFoundException {

        SmartUserDetails domainPerson = smartUserDAO.findSmartUserDetailsByEmail(login);

        return new User(
                domainPerson.getEmail(),
                domainPerson.getPasswordMd5(),
                domainPerson.isEnabled(),
                domainPerson.isAccountNonLocked(),
                domainPerson.isCredentialsNonExpired(),
                domainPerson.isAccountNonLocked(),
                getAuthorities(domainPerson.getRole().getId().intValue())
        );
    }

    public Collection<? extends GrantedAuthority> getAuthorities(Integer role) {
        // TODO продумать как раздавать и какие могут быть права у каждой роли
        return getGrantedAuthorities(getRoles(role));
    }

    public List<String> getRoles(Integer role) {
        // TODO продумать определение роли. enumerate ?
        List<String> roles = new ArrayList<>();

        if (role == 1) {
            roles.add("ROLE_ADMIN");
        } else if (role == 2) {
            roles.add("ROLE_USER");
        }
        return roles;
    }

    public static List<GrantedAuthority> getGrantedAuthorities(List<String> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();

        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }

}