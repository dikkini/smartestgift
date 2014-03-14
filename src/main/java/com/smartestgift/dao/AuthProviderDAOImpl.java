package com.smartestgift.dao;

import com.smartestgift.dao.model.AuthProvider;
import com.smartestgift.utils.ApplicationConstants;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
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
    public AuthProvider find(Integer id) {
        return (AuthProvider) sessionFactory.getCurrentSession().get(AuthProvider.class, id);
    }

    @Override
    public List<AuthProvider> findAll() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(AuthProvider.class);
        criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return criteria.list();
    }

    @Override
    public void store(AuthProvider dmodel) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(AuthProvider dmodel) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void merge(AuthProvider dmodel) {
        throw new UnsupportedOperationException();
    }
}
