package com.smartestgift.security;

import com.smartestgift.dao.TokenDAO;
import com.smartestgift.dao.model.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by dikkini on 30.01.14.
 * Email: dikkini@gmail.com
 */

@Component
public class SmartTokenRepository implements PersistentTokenRepository {

    @Autowired
    private TokenDAO tokenDao;

    @Override
    public void createNewToken(PersistentRememberMeToken token) {
        tokenDao.createNewToken(new Token(token));
    }

    @Override
    public void updateToken(String series, String tokenValue, Date lastUsed) {
        tokenDao.updateToken(series, tokenValue, lastUsed);
    }

    @Override
    public PersistentRememberMeToken getTokenForSeries(String seriesId) {
        Token token = tokenDao.getTokenForSeries(seriesId);
        if (token == null) {
            return null;
        }
        return new PersistentRememberMeToken(token.getUsername(),
                token.getSeries(), token.getTokenValue(), token.getLastUsed());
    }

    @Override
    public void removeUserTokens(String username) {
        tokenDao.removeUserTokens(username);
    }
}