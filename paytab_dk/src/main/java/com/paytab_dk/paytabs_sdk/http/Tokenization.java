package com.paytab_dk.paytabs_sdk.http;


import com.google.gson.annotations.SerializedName;

public class Tokenization {
    @SerializedName("pt_token")
    private String token;
    @SerializedName("pt_customer_password")
    private String password;
    @SerializedName("pt_customer_email")
    private String customerEmail;

    public Tokenization() {
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCustomerEmail() {
        return this.customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }
}
