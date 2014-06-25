package com.smartestgift.dao.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

/**
 * Created by dikkini on 28/05/14.
 */

@Entity
@Table(name = "gift_shop")
public class GiftShop implements Serializable {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "uuid", unique = true)
    protected String uuid;

    @OneToOne
    @JoinColumn (name = "shop_uuid")
    protected Shop shop;

    @OneToOne
    @JoinColumn (name = "gift_uuid")
    protected Gift gift;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "giftShop")
    protected Set<SmartUserGift> smartUserGifts;

    // TODO сделать модель цены с валютой и прочей ерундой
    @Column(name = "price")
    protected BigDecimal price;

    @Column(name = "discount")
    protected Integer discount;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public Gift getGift() {
        return gift;
    }

    public void setGift(Gift gift) {
        this.gift = gift;
    }

    public Set<SmartUserGift> getSmartUserGifts() {
        return smartUserGifts;
    }

    public void setSmartUserGifts(Set<SmartUserGift> smartUserGifts) {
        this.smartUserGifts = smartUserGifts;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GiftShop giftShop = (GiftShop) o;

        if (discount != null ? !discount.equals(giftShop.discount) : giftShop.discount != null) return false;
        if (gift != null ? !gift.equals(giftShop.gift) : giftShop.gift != null) return false;
        if (price != null ? !price.equals(giftShop.price) : giftShop.price != null) return false;
        if (shop != null ? !shop.equals(giftShop.shop) : giftShop.shop != null) return false;
        if (smartUserGifts != null ? !smartUserGifts.equals(giftShop.smartUserGifts) : giftShop.smartUserGifts != null)
            return false;
        if (uuid != null ? !uuid.equals(giftShop.uuid) : giftShop.uuid != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = uuid != null ? uuid.hashCode() : 0;
        result = 31 * result + (shop != null ? shop.hashCode() : 0);
        result = 31 * result + (gift != null ? gift.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (discount != null ? discount.hashCode() : 0);
        return result;
    }
}
