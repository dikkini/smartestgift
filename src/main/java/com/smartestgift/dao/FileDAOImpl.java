package com.smartestgift.dao;

import com.smartestgift.dao.model.File;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: dikkini
 * Date: 11/19/13
 * Time: 1:38 PM
 */
@Service
@Transactional
public class FileDAOImpl implements FileDAO {
    @Autowired
    SessionFactory sessionFactory;

    @Override
    public File find(Long id) {
        return (File) sessionFactory.getCurrentSession().get(File.class, id);
    }

    @Override
    public List<File> findAll() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(File.class);
        criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return criteria.list();
    }

    @Override
    public void store(File dmodel) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(dmodel);
        session.flush();
    }

    @Override
    public void delete(File dmodel) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(dmodel);
    }
}
