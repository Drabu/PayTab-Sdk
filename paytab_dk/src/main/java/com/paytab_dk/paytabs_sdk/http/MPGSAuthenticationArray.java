package com.paytab_dk.paytabs_sdk.http;

import com.google.gson.annotations.SerializedName;

public class MPGSAuthenticationArray {
    @SerializedName("TermUrl")
    private String termUrl;
    @SerializedName("xid")
    private String xid;
    @SerializedName("paReq")
    private String paReq;
    @SerializedName("acsURL")
    private String acsURL;

    public MPGSAuthenticationArray() {
    }

    public String getTermUrl() {
        return this.termUrl;
    }

    public String getXid() {
        return this.xid;
    }

    public String getPaReq() {
        return this.paReq;
    }

    public String getAcsURL() {
        return this.acsURL;
    }
}
