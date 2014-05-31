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
    private GiftShop giftShop;

    public SmartUser getUser() {
        return user;
    }

    public void setUser(SmartUser smartUser) {
        this.user = smartUser;
    }

    public GiftShop getGiftShop() {
        return giftShop;
    }

    public void setGiftShop(GiftShop giftShop) {
        this.giftShop = giftShop;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SmartUserGiftId)) return false;

        SmartUserGiftId that = (SmartUserGiftId) o;

        return !(user != null ? !user.equals(that.user) : that.user != null) &&
                !(giftShop != null ? !giftShop.equals(that.giftShop) : that.giftShop != null);

    }
}
