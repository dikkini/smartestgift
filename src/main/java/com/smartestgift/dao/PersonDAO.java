package com.smartestgift.dao;

import com.smartestgift.dao.model.Person;
import com.smartestgift.dao.model.PersonAuthDetails;

import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * Person: dikkini
 * Date: 10/6/13
 * Time: 2:06 PM
 */
public interface PersonDAO extends Repository<Person, UUID> {
    public PersonAuthDetails findPersonAuthDetailsByLogin(String login);
}
