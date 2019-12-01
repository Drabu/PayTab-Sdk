package com.paytab_dk.paytabs_sdk.http;

import com.google.gson.annotations.SerializedName;

public class ValidateKeyResponse {
    @SerializedName("result")
    private String result;
    @SerializedName("response_code")
    private String response_code;

    public ValidateKeyResponse() {
    }

    public String getResult() {
        return this.result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getResponse_code() {
        return this.response_code;
    }

    public void setResponse_code(String response_code) {
        this.response_code = response_code;
    }
}
