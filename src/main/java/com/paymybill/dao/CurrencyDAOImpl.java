package com.paymybill.dao;

import com.paymybill.dao.model.Currency;
import com.paymybill.dao.model.Target;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class CurrencyDAOImpl extends GenericDAOImpl<Currency, Long> implements CurrencyDAO {

    private SessionFactory sessionFactory;

    public CurrencyDAOImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
        this.sessionFactory = sessionFactory;
    }
}
