package com.smartestgift.dao;

import com.smartestgift.dao.model.AuthProvider;

/**
 * Created by dikkini on 16.02.14.
 * Email: dikkini@gmail.com
 */
public interface AuthProviderDAO {
    public AuthProvider findFacebookProvider();

    public AuthProvider findApplicationProvider();
}
