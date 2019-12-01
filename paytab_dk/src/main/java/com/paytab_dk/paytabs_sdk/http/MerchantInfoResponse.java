package com.paytab_dk.paytabs_sdk.http;

import com.google.gson.annotations.SerializedName;


public class MerchantInfoResponse {
    private String result;
    private String merchant_logo;
    private String merchant_title;
    private String merchant_phone;
    private String merchant_currency;
    private String transaction_currency;
    private String transaction_amount;
    private String transaction_exchange_rate;
    private String max_amount;
    private String min_amount;
    private String profile_title;
    private String device_fp_session_id;
    private String device_fp_org_id;
    private String response_code;
    private String payment_timeout;
    private String merchant_logo_file;

    public MerchantInfoResponse() {
    }

    public String getResult() {
        return this.result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMerchant_logo() {
        return this.merchant_logo;
    }

    public void setMerchant_logo(String merchant_logo) {
        this.merchant_logo = merchant_logo;
    }

    public String getMerchant_title() {
        return this.merchant_title;
    }

    public void setMerchant_title(String merchant_title) {
        this.merchant_title = merchant_title;
    }

    public String getMerchant_phone() {
        return this.merchant_phone;
    }

    public void setMerchant_phone(String merchant_phone) {
        this.merchant_phone = merchant_phone;
    }

    public String getMerchant_currency() {
        return this.merchant_currency;
    }

    public void setMerchant_currency(String merchant_currency) {
        this.merchant_currency = merchant_currency;
    }

    public String getTransaction_currency() {
        return this.transaction_currency;
    }

    public void setTransaction_currency(String transaction_currency) {
        this.transaction_currency = transaction_currency;
    }

    public String getTransaction_amount() {
        return this.transaction_amount;
    }

    public void setTransaction_amount(String transaction_amount) {
        this.transaction_amount = transaction_amount;
    }

    public String getTransaction_exchange_rate() {
        return this.transaction_exchange_rate;
    }

    public void setTransaction_exchange_rate(String transaction_exchange_rate) {
        this.transaction_exchange_rate = transaction_exchange_rate;
    }

    public String getMax_amount() {
        return this.max_amount;
    }

    public void setMax_amount(String max_amount) {
        this.max_amount = max_amount;
    }

    public String getMin_amount() {
        return this.min_amount;
    }

    public void setMin_amount(String min_amount) {
        this.min_amount = min_amount;
    }

    public String getProfile_title() {
        return this.profile_title;
    }

    public void setProfile_title(String profile_title) {
        this.profile_title = profile_title;
    }

    public String getDevice_fp_session_id() {
        return this.device_fp_session_id;
    }

    public void setDevice_fp_session_id(String device_fp_session_id) {
        this.device_fp_session_id = device_fp_session_id;
    }

    public String getDevice_fp_org_id() {
        return this.device_fp_org_id;
    }

    public void setDevice_fp_org_id(String device_fp_org_id) {
        this.device_fp_org_id = device_fp_org_id;
    }

    public String getResponse_code() {
        return this.response_code;
    }

    public void setResponse_code(String response_code) {
        this.response_code = response_code;
    }

    public String getPayment_timeout() {
        return this.payment_timeout;
    }

    public void setPayment_timeout(String payment_timeout) {
        this.payment_timeout = payment_timeout;
    }

    public String getMerchant_logo_file() {
        return this.merchant_logo_file;
    }

    public void setMerchant_logo_file(String merchant_logo_file) {
        this.merchant_logo_file = merchant_logo_file;
    }
}
