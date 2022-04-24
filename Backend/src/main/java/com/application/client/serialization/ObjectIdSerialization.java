package com.application.client.serialization;

import com.google.gson.*;
import org.bson.types.ObjectId;

import java.lang.reflect.Type;

public class ObjectIdSerialization implements JsonSerializer<ObjectId>, JsonDeserializer<ObjectId> {

    @Override
    public JsonElement serialize(ObjectId src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.toHexString());
    }

    @Override
    public ObjectId deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (json.isJsonPrimitive()) {
            return new ObjectId(json.getAsString());
        } else {
            throw new JsonParseException("Expected ObjectId as JsonPrimitive");
        }
    }
}
