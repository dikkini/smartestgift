package com.smartestgift.dao;

import com.smartestgift.dao.SmartUserDetailsDAO;
import com.smartestgift.dao.model.SmartUserDetails;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static junit.framework.Assert.assertTrue;

/**
 * Created by dikkini on 20.02.14.
 * Email: dikkini@gmail.com
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:root-context.xml"})
public class SmartUserDetailsDAOTest {
    @Autowired
    SmartUserDetailsDAO smartUserDetailsDAO;

    String testUsername = "dikkini";
    String testEmail ="dikkini@gmail.com";
    String testUserId = "2c948594442a92ca01442a94222d0000";
    String testSocialId = "";

    @Test
    public void testFindSmartUserDetailsByUsername() throws Exception {
        SmartUserDetails smartUserDetails = smartUserDetailsDAO.findSmartUserDetailsByUsername(testUsername);
        assertTrue(smartUserDetails != null);
    }

    @Test
    public void testFindSmartUserDetailsByEmail() throws Exception {
        SmartUserDetails smartUserDetailsByEmail = smartUserDetailsDAO.findSmartUserDetailsByEmail(testEmail);
        assertTrue(smartUserDetailsByEmail != null);
    }

    @Test
    @Ignore
    public void testFindFacebookUserBySocialId() throws Exception {
        SmartUserDetails facebookUserBySocialId = smartUserDetailsDAO.findFacebookUserBySocialId(testSocialId);
        assertTrue(facebookUserBySocialId != null);
    }

    @Test
    public void testFindAllSmartUsersDetails() throws Exception {
        List<SmartUserDetails> all = smartUserDetailsDAO.findAll();
        assertTrue(all.size() > 0);
    }

    @Test
    public void testFindSmartUserDetailsById() throws Exception {
        SmartUserDetails smartUserDetails = smartUserDetailsDAO.find(testUserId);
        assertTrue(smartUserDetails != null);
    }
}
