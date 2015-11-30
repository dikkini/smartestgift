package com.smartestgift.handler;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.smartestgift.dao.model.SmartUser;
import com.smartestgift.dao.model.SmartUserFriend;
import com.smartestgift.dao.model.SmartUserGift;

import java.io.IOException;
import java.lang.reflect.Field;

/**
 * Created by dikkini on 07/07/14.
 */
public class JsonUserSerializer extends JsonSerializer<SmartUser> {
    @Override
    public void serialize(SmartUser o, JsonGenerator jsonGen, SerializerProvider serializerProvider)
            throws IOException, JsonProcessingException {
        jsonGen.writeStartObject();
        Field[] smartUserFields = o.getClass().getDeclaredFields();

        jsonGen.writeStringField(smartUserFields[0].getName(), o.getUuid());
        jsonGen.writeStringField(smartUserFields[1].getName(), o.getUsername());
        jsonGen.writeStringField(smartUserFields[2].getName(), o.getPassword());
        jsonGen.writeStringField(smartUserFields[3].getName(), o.getEmail());
        jsonGen.writeBooleanField(smartUserFields[4].getName(), o.isEnabled());
        jsonGen.writeNumberField(smartUserFields[5].getName(), o.getAuthProvider());
        // TODO формат даты
        jsonGen.writeStringField(smartUserFields[6].getName(), o.getRegistrationDate().toString());
        jsonGen.writeStringField(smartUserFields[7].getName(), o.getSocialId());
        // TODO формат даты
        if (o.getBirthDate() != null) {
            jsonGen.writeStringField(smartUserFields[8].getName(), o.getBirthDate().toString());
        }
        jsonGen.writeStringField(smartUserFields[9].getName(), o.getFirstName());
        jsonGen.writeStringField(smartUserFields[10].getName(), o.getLastName());
        jsonGen.writeStringField(smartUserFields[11].getName(), o.getMiddleName());
        if (o.getGender() == null) {
            jsonGen.writeNullField(smartUserFields[12].getName());
        } else {
            jsonGen.writeBooleanField(smartUserFields[12].getName(), o.getGender());
        }
        jsonGen.writeStringField(smartUserFields[13].getName(), o.getAddress());
        jsonGen.writeBooleanField(smartUserFields[14].getName(), o.isAddressVisible());
        jsonGen.writeBooleanField(smartUserFields[15].getName(), o.isProfileVisible());
        jsonGen.writeStringField(smartUserFields[16].getName(), o.getCellPhone());
        jsonGen.writeBooleanField(smartUserFields[17].getName(), o.isCellPhoneVisible());

        jsonGen.writeFieldName(smartUserFields[18].getName()); // "namearray" :
        jsonGen.writeStartArray(); // [
        for (SmartUserGift smartUserGift : o.getSmartUserGifts()) {
            jsonGen.writeObject(smartUserGift);
        }
        jsonGen.writeEndArray(); // ]

        jsonGen.writeFieldName(smartUserFields[19].getName()); // "namearray" :
        jsonGen.writeStartArray(); // [
        Field[] smartUserFriendFields = SmartUserFriend.class.getDeclaredFields();
        for (SmartUserFriend user : o.getSmartUserFriends()) {
            jsonGen.writeStartObject();
            jsonGen.writeStringField(smartUserFriendFields[0].getName(), user.getUuid());
            jsonGen = serializeSmartUserWithoutRecursion(smartUserFriendFields[1].getName(), user.getSmartUser(), jsonGen);
            jsonGen = serializeSmartUserWithoutRecursion(smartUserFriendFields[2].getName(), user.getFriendUser(), jsonGen);
            jsonGen.writeEndObject();
        }
        jsonGen.writeEndArray(); // ]

        jsonGen.writeFieldName(smartUserFields[20].getName()); // "namearray" :
        jsonGen.writeStartArray(); // [
        for (SmartUserFriend user : o.getSmartUserFriendsOf()) {
            jsonGen.writeStartObject();
            jsonGen.writeStringField(smartUserFriendFields[0].getName(), user.getUuid());
            jsonGen = serializeSmartUserWithoutRecursion(smartUserFriendFields[1].getName(), user.getSmartUser(), jsonGen);
            jsonGen = serializeSmartUserWithoutRecursion(smartUserFriendFields[2].getName(), user.getFriendUser(), jsonGen);
            jsonGen.writeEndObject();
        }
        jsonGen.writeEndArray(); // ]
        jsonGen.writeObjectField(smartUserFields[22].getName(), o.getFile());
        jsonGen.writeEndObject(); // }
    }

    @Override
    public Class<SmartUser> handledType() {
        return SmartUser.class;
    }

    private JsonGenerator serializeSmartUserWithoutRecursion(String objFieldName, SmartUser o, JsonGenerator jsonGen)
            throws IOException {
        jsonGen.writeFieldName(objFieldName);
        jsonGen.writeStartObject();
        Field[] smartUserFields = o.getClass().getDeclaredFields();

        jsonGen.writeStringField(smartUserFields[0].getName(), o.getUuid());
        jsonGen.writeStringField(smartUserFields[1].getName(), o.getUsername());
        jsonGen.writeStringField(smartUserFields[2].getName(), o.getPassword());
        jsonGen.writeStringField(smartUserFields[3].getName(), o.getEmail());
        jsonGen.writeBooleanField(smartUserFields[4].getName(), o.isEnabled());
        jsonGen.writeNumberField(smartUserFields[5].getName(), o.getAuthProvider());
        // TODO формат даты
        jsonGen.writeStringField(smartUserFields[6].getName(), o.getRegistrationDate().toString());
        jsonGen.writeStringField(smartUserFields[7].getName(), o.getSocialId());
        // TODO формат даты
        if (o.getBirthDate() != null) {
            jsonGen.writeStringField(smartUserFields[8].getName(), o.getBirthDate().toString());
        }
        jsonGen.writeStringField(smartUserFields[9].getName(), o.getFirstName());
        jsonGen.writeStringField(smartUserFields[10].getName(), o.getLastName());
        jsonGen.writeStringField(smartUserFields[11].getName(), o.getMiddleName());
        if (o.getGender() == null) {
            jsonGen.writeNullField(smartUserFields[12].getName());
        } else {
            jsonGen.writeBooleanField(smartUserFields[12].getName(), o.getGender());
        }
        jsonGen.writeStringField(smartUserFields[13].getName(), o.getAddress());
        jsonGen.writeBooleanField(smartUserFields[14].getName(), o.isAddressVisible());
        jsonGen.writeBooleanField(smartUserFields[15].getName(), o.isProfileVisible());
        jsonGen.writeStringField(smartUserFields[16].getName(), o.getCellPhone());
        jsonGen.writeBooleanField(smartUserFields[17].getName(), o.isCellPhoneVisible());
        jsonGen.writeObjectField(smartUserFields[22].getName(), o.getFile());

        jsonGen.writeEndObject();
        return jsonGen;
    }
}
