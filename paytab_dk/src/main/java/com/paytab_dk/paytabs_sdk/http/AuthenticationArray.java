package com.paytab_dk.paytabs_sdk.http;


import com.google.gson.annotations.SerializedName;

public class AuthenticationArray {
    @SerializedName("vpc_AccessCode")
    private String vpcAccessCode;
    @SerializedName("vpc_Amount")
    private String vpcAmount;
    @SerializedName("vpc_CardExp")
    private String vpcCardExp;
    @SerializedName("vpc_CardNum")
    private String vpcCardNum;
    @SerializedName("vpc_CardSecurityCode")
    private String vpcCardSecurityCode;
    @SerializedName("vpc_Command")
    private String vpcCommand;
    @SerializedName("vpc_Currency")
    private String vpcCurrency;
    @SerializedName("vpc_MerchTxnRef")
    private String vpcMerchTxnRef;
    @SerializedName("vpc_Merchant")
    private String vpcMerchant;
    @SerializedName("vpc_OrderInfo")
    private String vpcOrderInfo;
    @SerializedName("vpc_ReturnURL")
    private String vpcReturnURL;
    @SerializedName("vpc_Version")
    private String vpcVersion;
    @SerializedName("vpc_card")
    private String vpcCard;
    @SerializedName("vpc_gateway")
    private String vpcGateway;

    public AuthenticationArray() {
    }

    public String getVpcAccessCode() {
        return this.vpcAccessCode;
    }

    public void setVpcAccessCode(String vpcAccessCode) {
        this.vpcAccessCode = vpcAccessCode;
    }

    public String getVpcAmount() {
        return this.vpcAmount;
    }

    public void setVpcAmount(String vpcAmount) {
        this.vpcAmount = vpcAmount;
    }

    public String getVpcCardExp() {
        return this.vpcCardExp;
    }

    public void setVpcCardExp(String vpcCardExp) {
        this.vpcCardExp = vpcCardExp;
    }

    public String getVpcCardNum() {
        return this.vpcCardNum;
    }

    public void setVpcCardNum(String vpcCardNum) {
        this.vpcCardNum = vpcCardNum;
    }

    public String getVpcCardSecurityCode() {
        return this.vpcCardSecurityCode;
    }

    public void setVpcCardSecurityCode(String vpcCardSecurityCode) {
        this.vpcCardSecurityCode = vpcCardSecurityCode;
    }

    public String getVpcCommand() {
        return this.vpcCommand;
    }

    public void setVpcCommand(String vpcCommand) {
        this.vpcCommand = vpcCommand;
    }

    public String getVpcCurrency() {
        return this.vpcCurrency;
    }

    public void setVpcCurrency(String vpcCurrency) {
        this.vpcCurrency = vpcCurrency;
    }

    public String getVpcMerchTxnRef() {
        return this.vpcMerchTxnRef;
    }

    public void setVpcMerchTxnRef(String vpcMerchTxnRef) {
        this.vpcMerchTxnRef = vpcMerchTxnRef;
    }

    public String getVpcMerchant() {
        return this.vpcMerchant;
    }

    public void setVpcMerchant(String vpcMerchant) {
        this.vpcMerchant = vpcMerchant;
    }

    public String getVpcOrderInfo() {
        return this.vpcOrderInfo;
    }

    public void setVpcOrderInfo(String vpcOrderInfo) {
        this.vpcOrderInfo = vpcOrderInfo;
    }

    public String getVpcReturnURL() {
        return this.vpcReturnURL;
    }

    public void setVpcReturnURL(String vpcReturnURL) {
        this.vpcReturnURL = vpcReturnURL;
    }

    public String getVpcVersion() {
        return this.vpcVersion;
    }

    public void setVpcVersion(String vpcVersion) {
        this.vpcVersion = vpcVersion;
    }

    public String getVpcCard() {
        return this.vpcCard;
    }

    public void setVpcCard(String vpcCard) {
        this.vpcCard = vpcCard;
    }

    public String getVpcGateway() {
        return this.vpcGateway;
    }

    public void setVpcGateway(String vpcGateway) {
        this.vpcGateway = vpcGateway;
    }
}
