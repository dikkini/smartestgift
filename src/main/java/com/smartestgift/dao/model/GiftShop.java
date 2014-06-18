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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.giftShop")
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
}
