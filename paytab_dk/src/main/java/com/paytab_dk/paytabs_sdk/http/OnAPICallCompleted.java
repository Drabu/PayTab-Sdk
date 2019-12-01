package com.paytab_dk.paytabs_sdk.http;

import com.google.gson.JsonElement;

public interface OnAPICallCompleted {
    void onAPICallSuccess(JsonElement var1, String var2);

    void onAPICallFailure(String var1, String var2);
}