package com.smartestgift.service.model;

import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: dikkini
 * Date: 10/8/13
 * Time: 4:09 PM
 */
public class ClientPK implements Serializable {
    @Id
    @Type(type = "pg-uuid")
    protected UUID uuid;

    @Id
    @Column(nullable = false, insertable = false, unique = true)
    protected String login;

    public ClientPK() {}

    public ClientPK(UUID uuid, String login) {
        this.uuid = uuid;
        this.login = login;
    }

    @Override
    public boolean equals(Object obj) {
        // TODO
        return true;
    }

    @Override
    public int hashCode() {
        // TODO
        return 0;
    }
}
