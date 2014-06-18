package com.smartestgift.dao.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by dikkini on 01.03.14.
 * Email: dikkini@gmail.com
 */
@Entity
@Table(name = "user_friends")
@AssociationOverrides({
        @AssociationOverride(name = "pk.user",
                joinColumns = @JoinColumn(name = "user_uuid")),
        @AssociationOverride(name = "pk.friend",
                joinColumns = @JoinColumn(name = "friend_uuid")) })
public class SmartUserFriend implements Serializable {

    @EmbeddedId
    protected SmartUserFriendId pk = new SmartUserFriendId();

    @Column(name = "friendadddate")
    protected Date friendAddDate;

    @Column(name = "friendtypeid")
    protected int friendTypeId;

    public SmartUserFriendId getPk() {
        return pk;
    }

    public void setPk(SmartUserFriendId pk) {
        this.pk = pk;
    }

    @Transient
    public SmartUser getSmartUser() {
        return getPk().getUser();
    }

    public void setSmartUser(SmartUser smartUser) {
        getPk().setUser(smartUser);
    }

    @Transient
    public SmartUser getFriend() {
        return getPk().getFriend();
    }

    public void setFriend(SmartUser friend) {
        getPk().setFriend(friend);
    }

    public Date getFriendAddDate() {
        return friendAddDate;
    }

    public void setFriendAddDate(Date friendAddDate) {
        this.friendAddDate = friendAddDate;
    }

    public int getFriendTypeId() {
        return friendTypeId;
    }

    public void setFriendTypeId(int friendTypeId) {
        this.friendTypeId = friendTypeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SmartUserFriend that = (SmartUserFriend) o;

        if (friendTypeId != that.friendTypeId) return false;
        if (friendAddDate != null ? !friendAddDate.equals(that.friendAddDate) : that.friendAddDate != null)
            return false;
        if (pk != null ? !pk.equals(that.pk) : that.pk != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = friendAddDate != null ? friendAddDate.hashCode() : 0;
        result = 31 * result + friendTypeId;
        return result;
    }
}
