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

    // Global Search hashmap keys for results
    public static final String GIFTS_SEARCH_RESULTS = "gift";
    public static final String USERS_SEARCH_RESULTS = "user";
    public static final Integer GLOBAL_SEARCH_RESULTS_COUNT = 15;


    public static String getFACEBOOK_APP_ID() {
        return FACEBOOK_APP_ID;
    }

    public static String getFacebookRedirectUrl() {
        return FACEBOOK_REDIRECT_URL;
    }

    public static String getFacebookSecretKey() {
        return FACEBOOK_SECRET_KEY;
    }

    public static String getFacebookExchangeKey() {
        return FACEBOOK_EXCHANGE_KEY;
    }

    public static String getFacebookKeyWord() {
        return FACEBOOK_KEY_WORD;
    }

    public static String getInputDateFormatPattern() {
        return INPUT_DATE_FORMAT_PATTERN;
    }

    public static Integer getApplicationAuthProviderId() {
        return APPLICATION_AUTH_PROVIDER_ID;
    }

    public static Integer getFacebookAuthProviderId() {
        return FACEBOOK_AUTH_PROVIDER_ID;
    }

    public static Integer getFileUserNoPhotoId() {
        return FILE_USER_NO_PHOTO_ID;
    }

    public static Integer getUserImageFileTypeId() {
        return USER_IMAGE_FILE_TYPE_ID;
    }

    public static Integer getCategoryImageFileTypeId() {
        return CATEGORY_IMAGE_FILE_TYPE_ID;
    }

    public static Integer getGiftImageFileTypeId() {
        return GIFT_IMAGE_FILE_TYPE_ID;
    }

    public static Integer getUserRoleId() {
        return USER_ROLE_ID;
    }

    public static Integer getAdminRoleId() {
        return ADMIN_ROLE_ID;
    }

    public static Integer getMessageStatusNew() {
        return MESSAGE_STATUS_NEW;
    }

    public static Integer getMessageStatusRead() {
        return MESSAGE_STATUS_READ;
    }

    public String getGIFTS_SEARCH_RESULTS() {
        return GIFTS_SEARCH_RESULTS;
    }

    public String getUSERS_SEARCH_RESULTS() {
        return USERS_SEARCH_RESULTS;
    }

    public Integer getGlobalSearchResultsCount() {
        return GLOBAL_SEARCH_RESULTS_COUNT;
    }
}
