package com.smartestgift.dao;

import com.smartestgift.dao.model.Person;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by dikkini on 27.01.14.
 * Email: dikkini@gmail.com
 */
@Service
@Transactional
public class PersonDAOImpl implements PersonDAO {
    @Autowired
    SessionFactory sessionFactory;

    @Override
    public Person find(Long id) {
        return (Person) sessionFactory.getCurrentSession().get(Person.class, id);
    }

    @Override
    public List<Person> findAll() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Person.class);
        criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return criteria.list();
    }

    @Override
    public void store(Person dmodel) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(dmodel);
        session.flush();
        session.refresh(dmodel);
    }

    @Override
    public void delete(Person dmodel) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Person findPersonByLogin(String login) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Person.class);
        criteria.add(Restrictions.eq("login", login));
        return (Person) criteria.uniqueResult();
    }
}
