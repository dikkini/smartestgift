package com.smartestgift.dao.model;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;

/**
 * Created by dikkini on 01.03.14.
 * Email: dikkini@gmail.com
 */
@Embeddable
public class SmartUserGiftId implements Serializable {
    @ManyToOne
    private SmartUser user;
    @ManyToOne
    private Gift gift;

    public SmartUser getUser() {
        return user;
    }

    public void setUser(SmartUser smartUser) {
        this.user = smartUser;
    }

    public Gift getGift() {
        return gift;
    }

    public void setGift(Gift gift) {
        this.gift = gift;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SmartUserGiftId that = (SmartUserGiftId) o;

        return !(user != null ? !user.equals(that.user) : that.user != null) &&
                !(gift != null ? !gift.equals(that.gift) : that.gift != null);

    }

    public int hashCode() {
        int result;
        result = (user != null ? user.hashCode() : 0);
        result = 31 * result + (gift != null ? gift.hashCode() : 0);
        return result;
    }
}
