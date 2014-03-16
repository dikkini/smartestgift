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

    public static final String INPUT_DATE_FORMAT_PATTERN = "yyyy-MM-dd";

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


}
