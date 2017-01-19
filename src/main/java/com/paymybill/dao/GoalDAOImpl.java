package com.paymybill.dao;

import com.paymybill.dao.model.Goal;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class GoalDAOImpl extends GenericDAOImpl<Goal, UUID> implements GoalDAO {

    private SessionFactory sessionFactory;

    public GoalDAOImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
        this.sessionFactory = sessionFactory;
    }
}
