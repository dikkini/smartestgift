package com.paymybill.service;


import com.paymybill.controller.model.UserDTO;
import com.paymybill.dao.model.User;
import com.paymybill.exception.EmailExistException;
import com.paymybill.exception.UsernameExistException;

public interface UserService {

    /**
     *
     * @param userDTO {@link UserDTO}
     * @return {@link User}
     */
    User registerUser(final UserDTO userDTO) throws UsernameExistException, EmailExistException;

    /**
     *
     * @param username {@link User#username}
     * @return {@link User}
     */
    User findByUsername(String username);


    /**
     *
     * @param email {@link User#email}
     * @return {@link User}
     */
    User findByEmail(String email);

    /**
     *
     * @param username {@link User#username}
     * @return true if busy, false if not
     */
    boolean isUsernameBusy(String username);

    /**
     *
     * @param email {@link User#email}
     * @return true if busy, false if not
     */
    boolean isEmailBusy(String email);
}
