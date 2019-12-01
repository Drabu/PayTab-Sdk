package com.paytab_dk.paytabs_sdk.utils;


import org.json.JSONObject;

public class JsonParser {
    public JsonParser() {
    }

    public static String getString(JSONObject jsonObject, String key) {
        try {
            return jsonObject.getString(key);
        } catch (Exception var3) {
            return null;
        }
    }
}
