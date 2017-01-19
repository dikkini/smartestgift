package com.paymybill.service;


import com.paymybill.controller.model.UserDTO;
import com.paymybill.dao.model.User;
import com.paymybill.exception.EmailExistException;
import com.paymybill.exception.UsernameExistException;

public interface UserService {

    /**
     *
     * @param socialId
     * @return
     */
    User findBySocialId(String socialId);

    /**
     *
     * @param userDTO
     * @return
     */
    User registerUser(final UserDTO userDTO) throws UsernameExistException, EmailExistException;

    /**
     *
     * @param user
     * @return
     */
    User update(final User user);

    /**
     *
     * @param username
     * @return
     */
    User findByUsername(String username);


    /**
     *
     * @param email
     * @return
     */
    User findByEmail(String email);

    /**
     *
     * @param username
     * @return
     */
    boolean isUsernameBusy(String username);

    /**
     *
     * @param username
     * @return
     */
    boolean isEmailBusy(String username);
}
