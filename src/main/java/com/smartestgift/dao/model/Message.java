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
@Table(name = "message")
public class Message implements Serializable {
    @Id
    @GeneratedValue(generator = "system-uuid", strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "uuid", unique = true)
    protected String uuid;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_uuid")
    protected SmartUser smartUser;

    @Column(name = "message")
    protected String message;

    @Column(name = "date")
    protected Date date;

    @OneToOne
    @JoinColumn(name = "conversation_uuid")
    protected Conversation conversation;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public SmartUser getSmartUser() {
        return smartUser;
    }

    public void setSmartUser(SmartUser smartUser) {
        this.smartUser = smartUser;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
