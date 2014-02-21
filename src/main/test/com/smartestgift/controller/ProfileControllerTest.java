package com.smartestgift.controller;

import com.smartestgift.common.TestControllerBaseConfiguration;
import com.smartestgift.dao.SmartUserDAO;
import com.smartestgift.dao.SmartUserDetailsDAO;
import com.smartestgift.dao.model.SmartUser;
import com.smartestgift.dao.model.SmartUserDetails;
import com.smartestgift.security.UserAuthProvider;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.Date;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * Created by dikkini on 21.02.14.
 * Email: dikkini@gmail.com
 */
public class ProfileControllerTest extends TestControllerBaseConfiguration {

    @InjectMocks
    ProfileController profileController;

    @Autowired
    SmartUserDetailsDAO smartUserDetailsDAO;

    @Autowired
    UserAuthProvider userAuthProvider;

    @Autowired
    SmartUserDAO smartUserDAO;

    protected static final String testUsername = "dikkini";

    @Override
    public void setUp() {
        // Process mock annotations
        MockitoAnnotations.initMocks(this);
        profileController.smartUserDAO = smartUserDAO;
        // Setup Spring test in standalone mode
        this.mockMvc = MockMvcBuilders.standaloneSetup(profileController).build();

        SmartUserDetails userDetails = (SmartUserDetails) userAuthProvider.loadUserByUsername(testUsername);
        Authentication authToken = new UsernamePasswordAuthenticationToken (userDetails, userDetails.getPassword(), userDetails.getAuthorities());
        // Mockito.whens() for your authorization object
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authToken);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    public void testProfilePage() throws Exception {
        this.mockMvc.perform(get("/profile")).andExpect(view().name("users/profile")).andExpect(status().isOk());
    }

    @Test
    public void testProfileSettingsPage() throws Exception {
        this.mockMvc.perform(get("/profile/settings")).andExpect(view().name("users/settings")).andExpect(status().isOk());
    }

    @Test
    public void testSaveSettings() throws Exception {
        String firstName = "A";
        String lastName = "B";
        String middleName = "C";
        String address = "address";
        boolean addressVisible = true;
        boolean profileVisible = true;
        String cellPhone = "123";
        boolean cellphoneVisible = true;

        this.mockMvc.perform(post("/profile/settings/save")
                .param("firstName", firstName)
                .param("lastName", lastName)
                .param("middleName", middleName)
                // TODO now date got from JSP in strange format - need to know this format to test it updating
                .param("birthdate", new Date().toString())
                .param("address", address)
                .param("addressVisible", String.valueOf(addressVisible))
                .param("profileVisible", String.valueOf(profileVisible))
                .param("cellphone", cellPhone)
                .param("cellphoneVisible", String.valueOf(cellphoneVisible))
        );

        SmartUser smartUserByUsername = smartUserDAO.findSmartUserByUsername(testUsername);
        assertTrue(smartUserByUsername.getFirstName().equals(firstName));
        assertTrue(smartUserByUsername.getLastName().equals(lastName));
        assertTrue(smartUserByUsername.getMiddleName().equals(middleName));
        assertTrue(smartUserByUsername.getAddress().equals(address));
        assertTrue(smartUserByUsername.isAddressVisible() == addressVisible);
        assertTrue(smartUserByUsername.isProfileVisible() == profileVisible);
        assertTrue(smartUserByUsername.getCellPhone().equals(cellPhone));
        assertTrue(smartUserByUsername.isCellPhoneVisible() == cellphoneVisible);
    }
}
