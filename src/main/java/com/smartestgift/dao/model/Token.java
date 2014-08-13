package com.smartestgift.dao.model;

import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
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
    private String username;

    @Id
    private String series;

    @Column(name = "token")
    private String tokenValue;

    @Column(name = "last_used")
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

    public void setUsername(String login) {
        this.username = login;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Token)) return false;

        Token token = (Token) o;

        if (!lastUsed.equals(token.lastUsed)) return false;
        if (!series.equals(token.series)) return false;
        if (tokenValue != null ? !tokenValue.equals(token.tokenValue) : token.tokenValue != null) return false;
        if (!username.equals(token.username)) return false;

        return true;
    }
}