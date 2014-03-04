package com.smartestgift.dao.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by dikkini on 01.03.14.
 * Email: dikkini@gmail.com
 */
@Entity
@Table(name = "user_gift")
@AssociationOverrides({
        @AssociationOverride(name = "pk.user",
                joinColumns = @JoinColumn(name = "user_uuid")),
        @AssociationOverride(name = "pk.gift",
                joinColumns = @JoinColumn(name = "gift_uuid")) })
public class SmartUserGift implements Serializable {

    @EmbeddedId
    protected SmartUserGiftId pk = new SmartUserGiftId();

    @Column
    protected Integer moneyCollect;

    public SmartUserGiftId getPk() {
        return pk;
    }

    public void setPk(SmartUserGiftId pk) {
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
    public Gift getGift() {
        return getPk().getGift();
    }

    public void setGift(Gift gift) {
        getPk().setGift(gift);
    }

    public Integer getMoneyCollect() {
        return moneyCollect;
    }

    public void setMoneyCollect(Integer moneyCollect) {
        this.moneyCollect = moneyCollect;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SmartUserGift)) return false;

        SmartUserGift that = (SmartUserGift) o;

        return !(getPk() != null ? !getPk().equals(that.getPk()) : that.getPk() != null);

    }

    public int hashCode() {
        return (getPk() != null ? getPk().hashCode() : 0);
    }
}
