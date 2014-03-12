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
@Table(name = "conversation")
public class Conversation implements Serializable {

    @Id
    @GeneratedValue(generator = "system-uuid", strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "uuid", unique = true)
    protected String uuid;

    @ManyToOne
    private SmartUser user_from;

    @ManyToOne
    private SmartUser user_to;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public SmartUser getUser_from() {
        return user_from;
    }

    public void setUser_from(SmartUser user_from) {
        this.user_from = user_from;
    }

    public SmartUser getUser_to() {
        return user_to;
    }

    public void setUser_to(SmartUser user_to) {
        this.user_to = user_to;
    }
}
