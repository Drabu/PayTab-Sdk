package com.paytab_dk.paytabs_sdk.http;


import com.google.gson.annotations.SerializedName;

public class PrepareTransactionResponse {
    @SerializedName("response_code")
    private String responseCode;
    @SerializedName("transaction_id")
    private String transactionId;
    @SerializedName("tokenization")
    private Tokenization tokenization;
    @SerializedName("merchant_id")
    private String merchantId;
    @SerializedName("secure_hash")
    private String secureHash;
    @SerializedName("transaction_url")
    private String transactionUrl;
    @SerializedName("authentication_array")
    private AuthenticationArray authenticationArray;
    @SerializedName("mpgs_authentication_array")
    private MPGSAuthenticationArray MPGSauthenticationArray;
    @SerializedName("decision")
    private String decision;
    @SerializedName("local_transaction_id")
    private String localTransactionId;
    @SerializedName("payerAuthEnrollReply")
    private PayerAuthEnrollReply payerAuthEnrollReply;
    @SerializedName("result")
    private String result;

    public PrepareTransactionResponse() {
    }

    public MPGSAuthenticationArray getMPGSauthenticationArray() {
        return this.MPGSauthenticationArray;
    }

    public String getResponseCode() {
        return this.responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getTransactionId() {
        return this.transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public Tokenization getTokenization() {
        return this.tokenization;
    }

    public void setTokenization(Tokenization tokenization) {
        this.tokenization = tokenization;
    }

    public String getMerchantId() {
        return this.merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getSecureHash() {
        return this.secureHash;
    }

    public void setSecureHash(String secureHash) {
        this.secureHash = secureHash;
    }

    public String getTransactionUrl() {
        return this.transactionUrl;
    }

    public void setTransactionUrl(String transactionUrl) {
        this.transactionUrl = transactionUrl;
    }

    public AuthenticationArray getAuthenticationArray() {
        return this.authenticationArray;
    }

    public void setAuthenticationArray(AuthenticationArray authenticationArray) {
        this.authenticationArray = authenticationArray;
    }

    public String getDecision() {
        return this.decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }

    public String getLocalTransactionId() {
        return this.transactionId != null && !this.transactionId.isEmpty() ? this.transactionId : this.localTransactionId;
    }

    public void setLocalTransactionId(String localTransactionId) {
        this.localTransactionId = localTransactionId;
    }

    public PayerAuthEnrollReply getPayerAuthEnrollReply() {
        return this.payerAuthEnrollReply;
    }

    public void setPayerAuthEnrollReply(PayerAuthEnrollReply payerAuthEnrollReply) {
        this.payerAuthEnrollReply = payerAuthEnrollReply;
    }

    public String getResult() {
        return this.result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
