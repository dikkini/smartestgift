package com.smartestgift.dao;

import com.smartestgift.dao.model.Person;

/**
 * Created with IntelliJ IDEA.
 * Person: dikkini
 * Date: 10/6/13
 * Time: 2:06 PM
 */
public interface PersonDAO extends Repository<Person, Long> {
    public Person findPersonByLogin(String login);
}
