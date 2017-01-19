package com.paymybill.dao;

import com.paymybill.dao.model.Target;
import com.paymybill.dao.model.User;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class TargetDAOImpl extends GenericDAOImpl<Target, UUID> implements TargetDAO {

    private SessionFactory sessionFactory;

    public TargetDAOImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
        this.sessionFactory = sessionFactory;
    }
}
