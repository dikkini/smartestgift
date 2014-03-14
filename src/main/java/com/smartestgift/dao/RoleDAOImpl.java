package com.smartestgift.dao;

import com.smartestgift.dao.model.Role;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by dikkini on 08.02.14.
 * Email: dikkini@gmail.com
 */
@Service
@Transactional
public class RoleDAOImpl implements RoleDAO {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public Role find(Integer id) {
        return (Role) sessionFactory.getCurrentSession().get(Role.class, id);
    }

    @Override
    public List<Role> findAll() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Role.class);
        criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return criteria.list();
    }

    @Override
    public void store(Role dmodel) {

    }

    @Override
    public void delete(Role dmodel) {

    }

    @Override
    public void merge(Role dmodel) {

    }
}
