package com.smartestgift.dao;

import com.smartestgift.dao.SmartUserDAO;
import com.smartestgift.dao.model.SmartUser;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static junit.framework.Assert.assertTrue;

/**
 * Created by dikkini on 20.02.14.
 * Email: dikkini@gmail.com
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:root-context.xml"})
public class SmartUserDAOTest {
    @Autowired
    SmartUserDAO smartUserDAO;

    @Autowired
    SessionFactory sessionFactory;

    String testUsername = "dikkini";
    String testUserId = "2c948594442a92ca01442a94222d0000";

    @Test
    public void testFindSmartUserByUsername() throws Exception {
        SmartUser smartUserByUsername = smartUserDAO.findSmartUserByUsername(testUsername);
        assertTrue(smartUserByUsername != null);
    }

    @Test
    public void testFindSmartUserById() throws Exception {
        SmartUser smartUser = smartUserDAO.find(testUserId);
        assertTrue(smartUser != null);
    }

    @Test
    public void testFindAllSmartUsers() throws Exception {
        List<SmartUser> all = smartUserDAO.findAll();
        assertTrue(all.size() > 0);
    }
}
