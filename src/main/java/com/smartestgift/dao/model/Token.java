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
@Table(name = "persistentLogins")
public class Token implements Serializable {
    @Column
    private String username;

    @Id
    private String series;

    @Column(name = "token")
    private String tokenValue;

    @Column
    private Date lastUsed;

    public Token() {}

    public Token(PersistentRememberMeToken persistentRememberMeToken) {
        this.username = persistentRememberMeToken.getUsername();
        this.series = persistentRememberMeToken.getSeries();
        this.lastUsed = persistentRememberMeToken.getDate();
        this.tokenValue = persistentRememberMeToken.getTokenValue();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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