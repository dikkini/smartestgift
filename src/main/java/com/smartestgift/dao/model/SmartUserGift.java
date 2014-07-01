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
@Table(name = "user_gifts")
@AssociationOverrides({
        @AssociationOverride(name = "smartUser",
                joinColumns = @JoinColumn(name = "user_uuid")),
        @AssociationOverride(name = "giftShop",
                joinColumns = @JoinColumn(name = "gift_shop_uuid")) })
public class SmartUserGift implements Serializable {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "uuid", unique = true)
    protected String uuid;

    @ManyToOne
    @JoinColumn(name="user_uuid")
    protected SmartUser smartUser;

    @ManyToOne
    @JoinColumn(name="gift_shop_uuid")
    protected GiftShop giftShop;

    @Column(name = "moneyCollect")
    protected Integer moneyCollect;

    @Column(name = "endDate")
    protected Date endDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "url_uuid")
    protected SmartUserGiftURL smartUserGiftURL;

    public String getUuid() {
        return uuid;
    }

    public SmartUser getSmartUser() {
        return smartUser;
    }

    public void setSmartUser(SmartUser smartUser) {
        this.smartUser = smartUser;
    }

    public GiftShop getGiftShop() {
        return giftShop;
    }

    public void setGiftShop(GiftShop giftShop) {
        this.giftShop = giftShop;
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

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public SmartUserGiftURL getSmartUserGiftURL() {
        return smartUserGiftURL;
    }

    public void setSmartUserGiftURL(SmartUserGiftURL smartUserGiftURL) {
        this.smartUserGiftURL = smartUserGiftURL;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SmartUserGift that = (SmartUserGift) o;

        if (endDate != null ? !endDate.equals(that.endDate) : that.endDate != null) return false;
        if (giftShop != null ? !giftShop.equals(that.giftShop) : that.giftShop != null) return false;
        if (moneyCollect != null ? !moneyCollect.equals(that.moneyCollect) : that.moneyCollect != null) return false;
        if (smartUser != null ? !smartUser.equals(that.smartUser) : that.smartUser != null) return false;
        if (uuid != null ? !uuid.equals(that.uuid) : that.uuid != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = uuid != null ? uuid.hashCode() : 0;
        result = 31 * result + (smartUser != null ? smartUser.hashCode() : 0);
        result = 31 * result + (moneyCollect != null ? moneyCollect.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        return result;
    }
}
