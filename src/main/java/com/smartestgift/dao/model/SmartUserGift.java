package com.smartestgift.dao.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by dikkini on 01.03.14.
 * Email: dikkini@gmail.com
 */
@Entity
@Table(name = "user_gift")
@AssociationOverrides({
        @AssociationOverride(name = "pk.user",
                joinColumns = @JoinColumn(name = "user_uuid")),
        @AssociationOverride(name = "pk.giftShop",
                joinColumns = @JoinColumn(name = "gift_shop_uuid")) })
public class SmartUserGift implements Serializable {

    @EmbeddedId
    protected SmartUserGiftId pk = new SmartUserGiftId();

    @Column(name = "moneyCollect")
    protected Integer moneyCollect;

    @Column(name = "endDate")
    protected Date endDate;

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
    public GiftShop getGiftShop() {
        return getPk().getGiftShop();
    }

    public void setGiftShop(GiftShop giftShop) {
        getPk().setGiftShop(giftShop);
    }

    public Integer getMoneyCollect() {
        return moneyCollect;
    }

    public void setMoneyCollect(Integer moneyCollect) {
        this.moneyCollect = moneyCollect;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SmartUserGift that = (SmartUserGift) o;

        if (endDate != null ? !endDate.equals(that.endDate) : that.endDate != null) return false;
        if (moneyCollect != null ? !moneyCollect.equals(that.moneyCollect) : that.moneyCollect != null) return false;
        if (pk != null ? !pk.equals(that.pk) : that.pk != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = pk != null ? pk.hashCode() : 0;
        result = 31 * result + (moneyCollect != null ? moneyCollect.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        return result;
    }
}
