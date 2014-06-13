package com.smartestgift.dao.model;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;

/**
 * Created by dikkini on 01.03.14.
 * Email: dikkini@gmail.com
 */
@Embeddable
public class SmartUserFriendId implements Serializable {

    @ManyToOne
    private SmartUser user;

    @ManyToOne
    private SmartUser friend;

    public SmartUser getUser() {
        return user;
    }

    public void setUser(SmartUser user) {
        this.user = user;
    }

    public SmartUser getFriend() {
        return friend;
    }

    public void setFriend(SmartUser friend) {
        this.friend = friend;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SmartUserFriendId)) return false;

        SmartUserFriendId that = (SmartUserFriendId) o;

        if (friend != null ? !friend.equals(that.friend) : that.friend != null) return false;
        if (user != null ? !user.equals(that.user) : that.user != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = user != null ? user.hashCode() : 0;
        result = 31 * result + (friend != null ? friend.hashCode() : 0);
        return result;
    }
}
