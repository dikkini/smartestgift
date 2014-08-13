package com.smartestgift.dao.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by dikkini on 30/06/14.
 */

@Entity
@Table(name = "user_gift_url")
public class SmartUserGiftURL implements Serializable {

    @Id
    @Column(name = "id", unique = true)
    protected Integer id;

    @Column(name = "short_url")
    protected String shortUrl;

    @Column(name = "url")
    protected String url;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SmartUserGiftURL that = (SmartUserGiftURL) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (shortUrl != null ? !shortUrl.equals(that.shortUrl) : that.shortUrl != null) return false;
        if (url != null ? !url.equals(that.url) : that.url != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (shortUrl != null ? shortUrl.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        return result;
    }
}
