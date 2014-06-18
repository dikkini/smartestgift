package com.smartestgift.dao;

import com.smartestgift.dao.model.File;
import com.smartestgift.dao.model.FileType;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by dikkini on 19.11.13.
 * Email: dikkini@gmail.com
 */
@Service
@Transactional
public class FileDAOImpl implements FileDAO {
    @Autowired
    SessionFactory sessionFactory;

    @Override
    public File find(Integer id) {
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

    @Override
    public void merge(File dmodel) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(dmodel);
        session.flush();
    }

    @Override
    public FileType findFileTypeById(Integer typeId) {
        return (FileType) sessionFactory.getCurrentSession().get(FileType.class, typeId);
    }
}
