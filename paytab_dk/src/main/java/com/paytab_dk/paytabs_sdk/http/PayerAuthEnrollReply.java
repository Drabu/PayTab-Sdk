package com.paytab_dk.paytabs_sdk.http;

import com.google.gson.annotations.SerializedName;

public class PayerAuthEnrollReply {
    @SerializedName("acsURL")
    private String acsURL;
    @SerializedName("paReq")
    private String paReq;
    @SerializedName("xid")
    private String xid;
    @SerializedName("reasonCode")
    private String reasonCode;

    public PayerAuthEnrollReply() {
    }

    public String getAcsURL() {
        return this.acsURL;
    }

    public void setAcsURL(String acsURL) {
        this.acsURL = acsURL;
    }

    public String getPaReq() {
        return this.paReq;
    }

    public void setPaReq(String paReq) {
        this.paReq = paReq;
    }

    public String getXid() {
        return this.xid;
    }

    public void setXid(String xid) {
        this.xid = xid;
    }

    public String getReasonCode() {
        return this.reasonCode;
    }

    public void setReasonCode(String reasonCode) {
        this.reasonCode = reasonCode;
    }
}
