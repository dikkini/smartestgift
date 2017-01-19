package com.paymybill.dao;

import com.paymybill.dao.model.Role;
import com.paymybill.dao.model.User;

import java.util.UUID;

public interface RoleDAO extends GenericDAO<Role, Long> {

    /**
     * Get user role
     *
     * @return {@link Role}
     */
    Role findUserRole();
}
