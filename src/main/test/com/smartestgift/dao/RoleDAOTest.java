package dao;

import com.smartestgift.dao.AuthProviderDAO;
import com.smartestgift.dao.RoleDAO;
import com.smartestgift.dao.model.AuthProvider;
import com.smartestgift.dao.model.Role;
import com.smartestgift.utils.ApplicationConstants;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static junit.framework.Assert.assertTrue;

/**
 * Created by dikkini on 20.02.14.
 * Email: dikkini@gmail.com
 */
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ContextConfiguration(locations={"classpath:root-context.xml"})
public class RoleDAOTest {
    @Autowired
    RoleDAO roleDAO;

    @Test
    public void testRoleDAO() throws Exception {
        Role userRole = roleDAO.find(ApplicationConstants.USER_ROLE_ID);
        Role adminRole = roleDAO.find(ApplicationConstants.ADMIN_ROLE_ID);
        assertTrue(userRole.getRole().equals("user"));
        assertTrue(adminRole.getRole().equals("admin"));
    }
}
