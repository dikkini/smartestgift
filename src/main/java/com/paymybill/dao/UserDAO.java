package com.paymybill.dao;

import com.paymybill.dao.model.User;

import javax.persistence.NoResultException;
import java.util.UUID;

public interface UserDAO extends GenericDAO<User, UUID> {

    /**
     * Search user by username
     *
     * @param username {@link User#username}
     * @return {@link User}
     */
    User findByUsername(String username) throws NoResultException;

    /**
     * Search user by email
     *
     * @param email {@link User#email}
     * @return {@link User}
     */
    User findByEmail(String email) throws NoResultException;

    /**
     * Search user by cell phone
     *
     * @param cellPhone {@link User#cellPhone}
     * @return {@link User}
     */
    User findByCellPhone(String cellPhone) throws NoResultException;
}
