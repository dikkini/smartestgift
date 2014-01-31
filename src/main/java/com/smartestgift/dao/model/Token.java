package com.smartestgift.dao.model;

import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by dikkini on 30.01.14.
 * Email: dikkini@gmail.com
 */
@Entity
@Table(name = "persistent_login")
public class Token implements Serializable {
    @Column
    private String login;

    @Id
    private String series;

    @Column(name = "token")
    private String tokenValue;

    @Column
    private Date lastUsed;

    public Token() {}

    public Token(PersistentRememberMeToken persistentRememberMeToken) {
        this.login = persistentRememberMeToken.getUsername();
        this.series = persistentRememberMeToken.getSeries();
        this.lastUsed = persistentRememberMeToken.getDate();
        this.tokenValue = persistentRememberMeToken.getTokenValue();
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getTokenValue() {
        return tokenValue;
    }

    public void setTokenValue(String tokenValue) {
        this.tokenValue = tokenValue;
    }

    public Date getLastUsed() {
        return lastUsed;
    }

    public void setLastUsed(Date date) {
        this.lastUsed = date;
    }
}