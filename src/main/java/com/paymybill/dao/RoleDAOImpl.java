package com.paymybill.dao;

import com.paymybill.dao.model.Role;
import com.paymybill.dao.model.User;
import com.paymybill.handler.RoleEnum;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Repository
public class RoleDAOImpl extends GenericDAOImpl<Role, Long> implements RoleDAO {

    private SessionFactory sessionFactory;

    public RoleDAOImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Role findUserRole() {
        return this.findOne(Role.class, RoleEnum.USER.getId());
    }
}
