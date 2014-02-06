package com.smartestgift.dao.model;

import javax.persistence.Basic;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Created by dikkini on 06.02.14.
 * Email: dikkini@gmail.com
 */
@Embeddable
public class UserGiftId implements Serializable {

    @Basic
    private String userUuid;

    @Basic
    private String giftUuid;

    public String getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(String userUuid) {
        this.userUuid = userUuid;
    }

    public String getGiftUuid() {
        return giftUuid;
    }

    public void setGiftUuid(String giftUuid) {
        this.giftUuid = giftUuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserGiftId)) return false;

        UserGiftId userGiftId = (UserGiftId) o;

        return !(giftUuid != null ? !giftUuid.equals(userGiftId.giftUuid) : userGiftId.giftUuid != null) && !(userUuid != null ? !userUuid.equals(userGiftId.userUuid) : userGiftId.userUuid != null);

    }

    @Override
    public int hashCode() {
        int result = userUuid != null ? userUuid.hashCode() : 0;
        result = 31 * result + (giftUuid != null ? giftUuid.hashCode() : 0);
        return result;
    }
}