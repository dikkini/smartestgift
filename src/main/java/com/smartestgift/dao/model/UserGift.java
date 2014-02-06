package com.smartestgift.dao.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by dikkini on 06.02.14.
 * Email: dikkini@gmail.com
 */
@Entity
@Table(name = "user_gift")
public class UserGift implements Serializable {
    @EmbeddedId
    protected UserGiftId userGiftId;

    @Column
    protected Integer moneyCollect;

    public UserGiftId getUserGiftId() {
        return userGiftId;
    }

    public void setUserGiftId(UserGiftId userGiftId) {
        this.userGiftId = userGiftId;
    }

    public Integer getMoneyCollect() {
        return moneyCollect;
    }

    public void setMoneyCollect(Integer moneyCollect) {
        this.moneyCollect = moneyCollect;
    }
}
