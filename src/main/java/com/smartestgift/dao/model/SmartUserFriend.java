package com.smartestgift.dao.model;

import org.hibernate.annotations.GenericGenerator;

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
        @AssociationOverride(name = "smartUser",
                joinColumns = @JoinColumn(name = "user_uuid")),
        @AssociationOverride(name = "friendUser",
                joinColumns = @JoinColumn(name = "friend_uuid")) })
public class SmartUserFriend implements Serializable {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "uuid", unique = true)
    protected String uuid;

    @ManyToOne
    @JoinColumn(name="user_uuid")
    private SmartUser smartUser;

    @ManyToOne
    @JoinColumn(name="friend_uuid")
    private SmartUser friendUser;

    @Column(name = "friendadddate")
    protected Date friendAddDate;

    @Column(name = "friendtypeid")
    protected int friendTypeId;

    public String getUuid() {
        return uuid;
    }

    public SmartUser getSmartUser() {
        return smartUser;
    }

    public void setSmartUser(SmartUser smartUser) {
        this.smartUser = smartUser;
    }

    public SmartUser getFriendUser() {
        return friendUser;
    }

    public void setFriendUser(SmartUser friendUser) {
        this.friendUser = friendUser;
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
        if (friendUser != null ? !friendUser.equals(that.friendUser) : that.friendUser != null) return false;
        if (smartUser != null ? !smartUser.equals(that.smartUser) : that.smartUser != null) return false;
        if (uuid != null ? !uuid.equals(that.uuid) : that.uuid != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = uuid != null ? uuid.hashCode() : 0;
        result = 31 * result + (smartUser != null ? smartUser.hashCode() : 0);
        result = 31 * result + (friendUser != null ? friendUser.hashCode() : 0);
        result = 31 * result + (friendAddDate != null ? friendAddDate.hashCode() : 0);
        result = 31 * result + friendTypeId;
        return result;
    }
}
