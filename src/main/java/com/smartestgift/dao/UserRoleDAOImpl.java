package com.smartestgift.dao;

import com.restfb.types.User;
import com.smartestgift.dao.model.Role;
import com.smartestgift.dao.model.UserRole;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by dikkini on 19/06/14.
 */
@Service
@Transactional
public class UserRoleDAOImpl implements UserRoleDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public UserRole find(Integer id) {
        return (UserRole) sessionFactory.getCurrentSession().get(UserRole.class, id);
    }

    @Override
    public List<UserRole> findAll() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(UserRole.class);
        criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return criteria.list();
    }

    @Override
    public void store(UserRole dmodel) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(dmodel);
        session.flush();
    }

    @Override
    public void delete(UserRole dmodel) {

    }

    @Override
    public void merge(UserRole dmodel) {

    }
}
