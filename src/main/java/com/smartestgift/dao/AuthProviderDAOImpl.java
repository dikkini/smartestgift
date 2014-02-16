package com.smartestgift.dao;

import com.smartestgift.dao.model.AuthProvider;
import com.smartestgift.dao.model.Role;
import com.smartestgift.enums.AuthProviderEnum;
import com.smartestgift.enums.RolesEnum;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by dikkini on 16.02.14.
 * Email: dikkini@gmail.com
 */
@Service
@Transactional
public class AuthProviderDAOImpl implements AuthProviderDAO {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public AuthProvider findFacebookProvider() {
        return (AuthProvider) sessionFactory.getCurrentSession().get(AuthProvider.class, AuthProviderEnum.FACEBOOK.getId());
    }

    @Override
    public AuthProvider findApplicationProvider() {
        return (AuthProvider) sessionFactory.getCurrentSession().get(AuthProvider.class, AuthProviderEnum.APPLICATION.getId());
    }
}
