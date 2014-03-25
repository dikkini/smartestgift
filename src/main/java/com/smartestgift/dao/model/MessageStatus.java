package com.smartestgift.dao.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by dikkini on 16.03.14.
 * Email: dikkini@gmail.com
 */
@Entity
@Table (name = "message_status")
public class MessageStatus implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    @Column(name = "status")
    protected String status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MessageStatus)) return false;

        MessageStatus that = (MessageStatus) o;

        if (!id.equals(that.id)) return false;
        if (!status.equals(that.status)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + status.hashCode();
        return result;
    }
}
