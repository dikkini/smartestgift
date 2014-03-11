package com.smartestgift.dao.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by dikkini on 10.03.14.
 * Email: dikkini@gmail.com
 */
@Entity
@Table(name = "conversation")
@AssociationOverrides({
        @AssociationOverride(name = "pk.userFrom",
                joinColumns = @JoinColumn(name = "user_from_uuid")),
        @AssociationOverride(name = "pk.userTo",
                joinColumns = @JoinColumn(name = "user_to_uuid")) })
public class Conversation implements Serializable {

    @EmbeddedId
    protected ConversationId pk = new ConversationId();

    @Column(name = "last_message")
    protected String lastMessage;

    public ConversationId getPk() {
        return pk;
    }

    public void setPk(ConversationId pk) {
        this.pk = pk;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }
}
