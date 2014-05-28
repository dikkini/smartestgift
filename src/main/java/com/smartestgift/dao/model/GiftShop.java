package com.smartestgift.dao.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

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
    @JoinColumn (name = "shop-uuid")
    protected Shop shop;

    @OneToOne
    @JoinColumn (name = "gift-uuid")
    protected Gift gift;

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
}
