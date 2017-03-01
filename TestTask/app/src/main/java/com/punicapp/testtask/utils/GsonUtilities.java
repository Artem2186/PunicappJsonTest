package com.punicapp.testtask.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import java.lang.reflect.Type;

public class GsonUtilities {

    private static Gson gson;

    public static Gson getGson() {
        if (gson == null) {
            initGson();
        }
        return gson;
    }

    private static void initGson() {
        GsonBuilder gsb = new GsonBuilder();
        gson = gsb.create();
    }

    public static String serialize(Object obj) {
        String representation = getGson().toJson(obj);
        return representation;
    }

    public static <T> T deserialize(String repr, Class<T> clazz) {
        T obj = getGson().fromJson(repr, clazz);
        return obj;
    }

    public static <T> T deserialize(String repr, Type clazz) {
        T obj = getGson().fromJson(repr, clazz);
        return obj;
    }

    public static <T> T deserialize(JsonElement repr, Type clazz) {
        T obj = getGson().fromJson(repr, clazz);
        return obj;
    }
}
