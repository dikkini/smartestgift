package com.smartestgift.utils;

/**
 * Created by dikkini on 07.02.14.
 * Email: dikkini@gmail.com
 */
public class ApplicationConstants {
    public static final String FACEBOOK_APP_ID = "563611490386461";
    public static final String FACEBOOK_REDIRECT_URL = "http://localhost:8080/login/facebookAuthentication?authCode=";
    public static final String FACEBOOK_SECRET_KEY = "334901bd51d0aab8fc45a4785413a576";
    public static final String FACEBOOK_EXCHANGE_KEY = "";
    public static final String FACEBOOK_KEY_WORD = "facebook-auth-key";

    public static final String INPUT_DATE_FORMAT_PATTERN = "dd.MM.yyyy";
    public static final String JSP_INPUT_DATE_FORMAT_PATTERN = "dd.mm.yy";

    // Auth providers ids
    public static final Integer APPLICATION_AUTH_PROVIDER_ID = 1;
    public static final Integer FACEBOOK_AUTH_PROVIDER_ID = 2;

    // File ids
    public static final Integer FILE_USER_NO_PHOTO_ID = 10;

    // File type ids
    public static final Integer USER_IMAGE_FILE_TYPE_ID = 1;
    public static final Integer CATEGORY_IMAGE_FILE_TYPE_ID = 1;
    public static final Integer GIFT_IMAGE_FILE_TYPE_ID = 3;

    // Role ids
    public static final Integer USER_ROLE_ID = 2;
    public static final Integer ADMIN_ROLE_ID = 1;

    // Message statuses
    public static final Integer MESSAGE_STATUS_NEW = 1;
    public static final Integer MESSAGE_STATUS_READ = 2;

    // User Friend Type
    public static final int USER_FRIEND_REQUEST_TYPE = 1;
    public static final int USER_FRIEND_FRIEND_TYPE = 2;
    public static final int USER_FRIEND_BLOCK_TYPE = 3;

    // Global Search hashmap keys for results
    public static final String GIFTS_SEARCH_RESULTS = "gift";
    public static final String USERS_SEARCH_RESULTS = "user";
    // Global Search results size
    public static final Integer GLOBAL_SEARCH_RESULTS_COUNT = 15;

    // People List Size
    public static final Integer PEOPLE_SEARCH_RESULTS_COUNT = 10;

    // Exceptions Errors codes
    public static final Integer INTERNAL_EXCEPTION_MESSAGE = 0;
    public static final Integer FACEBOOK_LOGIN_EXCEPTION_MESSAGE = 3;


    // getters for using constants in JSP
    public Integer getPEOPLE_SEARCH_RESULTS_COUNT() {
        return PEOPLE_SEARCH_RESULTS_COUNT;
    }

    public String getINPUT_DATE_FORMAT_PATTERN() {
        return INPUT_DATE_FORMAT_PATTERN;
    }

    public String getJSP_INPUT_DATE_FORMAT_PATTERN() {
        return JSP_INPUT_DATE_FORMAT_PATTERN;
    }

    public String getGIFTS_SEARCH_RESULTS() {
        return GIFTS_SEARCH_RESULTS;
    }

    public String getUSERS_SEARCH_RESULTS() {
        return USERS_SEARCH_RESULTS;
    }
}
