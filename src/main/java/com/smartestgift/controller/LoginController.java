package com.smartestgift.controller;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.types.User;
import com.smartestgift.dao.model.SmartUser;
import com.smartestgift.dao.model.SmartUser;
import com.smartestgift.service.SmartUserService;
import com.smartestgift.utils.ApplicationConstants;
import com.smartestgift.utils.Utils;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by dikkini on 27.01.14.
 * Email: dikkini@gmail.com
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    SmartUserService smartUserService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView login(@RequestParam(required = false, value = "error") boolean error) {
        ModelAndView mav = new ModelAndView("login");
        mav.addObject("error", error);
        return mav;
    }

    @RequestMapping(value = "/facebookLogin", method = RequestMethod.GET)
    public void facebookLogin(HttpServletResponse response, HttpServletRequest request) {
        String hashFacebookAuth = Utils.getHashFacebookAuth(RandomStringUtils.randomAlphabetic(10));
        request.getSession().setAttribute(ApplicationConstants.FACEBOOK_KEY_WORD, hashFacebookAuth);

        String url = "https://www.facebook.com/dialog/oauth/?"
                + "client_id=" + ApplicationConstants.FACEBOOK_APP_ID
                + "&redirect_uri=" + ApplicationConstants.FACEBOOK_REDIRECT_URL + hashFacebookAuth
                + "&scope=email,publish_stream,user_about_me,friends_about_me"
                + "&state=" + ApplicationConstants.FACEBOOK_EXCHANGE_KEY
                + "&display=page"
                + "&response_type=code";
        try {
            response.sendRedirect(url);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/facebookAuthentication", method = RequestMethod.GET)
    public String facebookAuthentication(HttpServletRequest request,
                                         @RequestParam (required = true, value = "authCode") String authCode) {
        // internal test
        String hashFacebookAuth = (String) request.getSession().getAttribute(ApplicationConstants.FACEBOOK_KEY_WORD);
        if (!hashFacebookAuth.equals(authCode)) {
            return "redirect:/login?errors=social_network_error";
        }
        //Get the parameter "code" from the request
        String code = request.getParameter("code");
        //Check if its null or blank or empty
        if (StringUtils.isNotEmpty(code)) {
            //If we received a valid code, we can continue to the next step
            //Next we want to get the access_token from Facebook using the code we got,
            //use the following url for that, in this url,
            //client_id-our app id(same as above),  redirect_uri-same as above, client_secret-same as
            // above, code-the code we just got
            String url = "https://graph.facebook.com/oauth/access_token?"
                    + "client_id=" + ApplicationConstants.FACEBOOK_APP_ID
                    + "&redirect_uri=" + ApplicationConstants.FACEBOOK_REDIRECT_URL + hashFacebookAuth
                    + "&client_secret=" + ApplicationConstants.FACEBOOK_SECRET_KEY
                    + "&code=" + code;
            // Create an instance of HttpClient.
            HttpClient client = new HttpClient();
            // Create a method instance.
            GetMethod method = new GetMethod(url);
            // Provide custom retry handler is necessary
            method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
                    new DefaultHttpMethodRetryHandler(3, false));
            try {
                // Execute the method.
                int statusCode = client.executeMethod(method);
                if (statusCode != HttpStatus.SC_OK) {
                    System.err.println("Method failed: " + method.getStatusLine());
                    return "redirect:/login?errors=external_error";
                }
                // Read the response body.
                byte[] responseBody = method.getResponseBody();
                // Deal with the response.
                // Use caution: ensure correct character encoding and is not binary data
                String responseBodyString = new String(responseBody);
                //will be like below,
                // access_token=AAADD1QFhDlwBADrKkn87ZABAz6ZCBQZ//DZD&expires=5178320
                //now get the access_token from the response
                if (responseBodyString.contains("access_token")) {
                    //success
                    String[] mainResponseArray = responseBodyString.split("&");
                    //like
                    // {"access_token= AAADD1QFhDlwBADrKkn87ZABAz6ZCBQZ//DZD ","expires=5178320"}
                    String accesstoken = "";
                    for (String string : mainResponseArray) {
                        if (string.contains("access_token")) {
                            accesstoken = string.replace("access_token=", "").trim();
                        }
                    }
                    //Great. Now we have the access token, I have used restfb to get the user details here
                    FacebookClient facebookClient = new DefaultFacebookClient(accesstoken);

                    // TODO сделать проверку на валидность юзера
                    User facebookUser = facebookClient.fetchObject("me", User.class);
                    // In this user object, you will have the details you want from Facebook,
                    // Since we have the access token with us, can play around and see what more can be done

                    SmartUser existSocialUser = smartUserService.findExistSocialUser(facebookUser.getId(),
                            ApplicationConstants.FACEBOOK_AUTH_PROVIDER_ID);

                    if (existSocialUser != null) {
                        smartUserService.authenticateUser(existSocialUser.getUsername(), existSocialUser.getPassword(), request);
                    } else {
                        boolean emailIsFree = smartUserService.checkOccupiedEmail(facebookUser.getEmail());
                        boolean usernameIsFree = smartUserService.checkOccupiedUsername(facebookUser.getUsername());


                        SmartUser fbUser = new SmartUser(facebookUser);
                        if (emailIsFree && usernameIsFree) {

                            SmartUser smartUser = smartUserService.createSmartUser(fbUser);
                            smartUserService.authenticateUser(smartUser.getUsername(),
                                    smartUser.getPassword(), request);
                            return "redirect:/profile";
                        } else {
                            request.getSession().setAttribute(facebookUser.getId(), fbUser);
                            return "redirect:/signup/facebook?id=" + facebookUser.getId() + "&errors="
                                    + (!emailIsFree ? "username_error," : "")
                                    + (!usernameIsFree ? "email_error" : "");
                        }
                    }
                } else {
                    return "redirect:/login?errors=internal_error";
                }
            // TODO
            } catch (HttpException e) {
                System.err.println("Fatal protocol violation: " + e.getMessage());
                e.printStackTrace();
                return "redirect:/login?errors=internal_error";
            } catch (IOException e) {
                System.err.println("Fatal transport error: " + e.getMessage());
                e.printStackTrace();
                return "redirect:/login?errors=internal_error";
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("Common Exception: " + e.getMessage());
                return "redirect:/login?errors=internal_error";
            }
            // Release the connection.
            method.releaseConnection();

            return "redirect:/login?errors=internal_error";
        } else {
            return "redirect:/login?errors=internal_error";
        }
    }
}