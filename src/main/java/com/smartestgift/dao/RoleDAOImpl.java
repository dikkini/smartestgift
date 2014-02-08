package com.smartestgift.dao;

import com.smartestgift.dao.model.Role;
import com.smartestgift.dao.model.SmartUserDetails;
import com.smartestgift.enums.RolesEnum;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public Role findUserRole() {
        return (Role) sessionFactory.getCurrentSession().get(Role.class, RolesEnum.USER_IMAGE.getId());
    }
}
