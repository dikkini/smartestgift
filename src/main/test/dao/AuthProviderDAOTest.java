package dao;

import com.smartestgift.dao.AuthProviderDAO;
import com.smartestgift.dao.model.AuthProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static junit.framework.Assert.assertTrue;

/**
 * Created by dikkini on 20.02.14.
 * Email: dikkini@gmail.com
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:root-context.xml"})
public class AuthProviderDAOTest {

    @Autowired
    AuthProviderDAO authProviderDAO;

    @Test
    public void testAuthProviderDAO() throws Exception {
        AuthProvider facebookProvider = authProviderDAO.findFacebookProvider();
        assertTrue(facebookProvider.getName().equals("facebook"));
        AuthProvider applicationProvider = authProviderDAO.findApplicationProvider();
        assertTrue(applicationProvider.getName().equals("application"));
    }
}
