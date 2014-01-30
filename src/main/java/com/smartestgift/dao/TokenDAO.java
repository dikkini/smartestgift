package com.smartestgift.dao;

import com.smartestgift.dao.model.Token;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by dikkini on 30.01.14.
 * Email: dikkini@gmail.com
 */

@Repository
@Transactional
public class TokenDAO {

    @Resource
    private  SessionFactory sessionFactory;

    public void createNewToken(Token token) {
        sessionFactory.getCurrentSession().save(token);
    }

    public void updateToken(String series, String tokenValue, Date lastUsed) {

        Token existingToken = (Token) sessionFactory.getCurrentSession().get(Token.class, series);
        existingToken.setTokenValue(tokenValue);
        existingToken.setDate(lastUsed);
        sessionFactory.getCurrentSession().merge(existingToken);
    }

    public Token getTokenForSeries(String seriesId) {
        return (Token) sessionFactory.getCurrentSession().get(Token.class, seriesId);
    }

    public void removeUserTokens(final String username) {

        Token token =
                (Token) sessionFactory.getCurrentSession().createCriteria(Token.class)
                        .add(Restrictions.eq("username", username)).uniqueResult();
        sessionFactory.getCurrentSession().delete(token);
    }
}
