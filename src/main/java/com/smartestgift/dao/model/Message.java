package com.smartestgift.dao.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by dikkini on 10.03.14.
 * Email: dikkini@gmail.com
 */
@Entity
@Table(name = "messages")
public class Message implements Serializable {
    @Id
    @GeneratedValue(generator = "system-uuid", strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "uuid", unique = true)
    protected String uuid;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_from_uuid")
    protected SmartUser smartUserFrom;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_to_uuid")
    protected SmartUser smartUserTo;

    @Column
    protected Date date;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public SmartUser getSmartUserFrom() {
        return smartUserFrom;
    }

    public void setSmartUserFrom(SmartUser smartUserFrom) {
        this.smartUserFrom = smartUserFrom;
    }

    public SmartUser getSmartUserTo() {
        return smartUserTo;
    }

    public void setSmartUserTo(SmartUser smartUserTo) {
        this.smartUserTo = smartUserTo;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
