package com.smartestgift.dao;

import com.smartestgift.dao.model.Person;
import com.smartestgift.dao.model.PersonAuthDetails;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

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
    public Person find(UUID id) {
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
    public PersonAuthDetails findPersonAuthDetailsByLogin(String login) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PersonAuthDetails.class);
        criteria.add(Restrictions.eq("login", login));
        return (PersonAuthDetails) criteria.uniqueResult();
    }
}
