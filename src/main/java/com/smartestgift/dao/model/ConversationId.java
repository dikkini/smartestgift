package com.smartestgift.dao.model;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;

/**
 * Created by dikkini on 01.03.14.
 * Email: dikkini@gmail.com
 */
@Embeddable
public class ConversationId implements Serializable {
    @ManyToOne
    private SmartUser userFrom;
    @ManyToOne
    private SmartUser userTo;

    public SmartUser getUserFrom() {
        return userFrom;
    }

    public void setUserFrom(SmartUser userFrom) {
        this.userFrom = userFrom;
    }

    public SmartUser getUserTo() {
        return userTo;
    }

    public void setUserTo(SmartUser userTo) {
        this.userTo = userTo;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ConversationId)) return false;

        ConversationId that = (ConversationId) o;

        return !(userFrom != null ? !userFrom.equals(that.userFrom) : that.userFrom != null) &&
                !(userTo != null ? !userTo.equals(that.userTo) : that.userTo != null);

    }
}
