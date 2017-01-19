package com.paymybill.dao;

import com.paymybill.dao.model.User;

import javax.persistence.NoResultException;
import java.util.UUID;

public interface UserDAO extends GenericDAO<User, UUID> {

    /**
     * Поиск пользователя по username
     *
     * @param username username
     * @return модель пользователя с деталями
     */
    User findByUsername(String username) throws NoResultException;

    /**
     * Поиск пользователя по email
     *
     * @param email email
     * @return
     */
    User findByEmail(String email) throws NoResultException;

    /**
     * Поиск пользователя по мобильному телефону
     *
     * @param cellPhone
     * @return
     */
    User findByCellPhone(String cellPhone) throws NoResultException;
}
