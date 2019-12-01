package com.paytab_dk.paytabs_sdk.http;


import com.google.gson.JsonElement;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {
    @FormUrlEncoded
    @POST("/apiv3/validate_secret_key")
    Call<JsonElement> validateKey(@Field("merchant_email") String var1, @Field("secret_key") String var2);

    @FormUrlEncoded
    @POST("/apiv3/get_merchant_info")
    Call<JsonElement> getMerchantInfo(@Field("merchant_email") String var1, @Field("secret_key") String var2, @Field("get_logo") String var3, @Field("amount") double var4, @Field("currency_code") String var6, @Field("is_tokenization") String var7, @Field("is_preauth") String var8, @Field("is_existing_customer") String var9, @Field("pt_token") String var10, @Field("pt_customer_email") String var11, @Field("pt_customer_password") String var12);

    @FormUrlEncoded
    @POST("/apiv3/prepare_transaction")
    Call<JsonElement> prepareTransaction(@Field("secret_key") String var1, @Field("merchant_email") String var2, @Field("amount") double var3, @Field("title") String var5, @Field("cc_first_name") String var6, @Field("cc_last_name") String var7, @Field("card_exp") String var8, @Field("cvv") String var9, @Field("card_number") String var10, @Field("original_assignee_code") String var11, @Field("currency") String var12, @Field("email") String var13, @Field("skip_email") String var14, @Field("phone_number") String var15, @Field("order_id") String var16, @Field("product_name") String var17, @Field("customer_email") String var18, @Field("country_billing") String var19, @Field("address_billing") String var20, @Field("city_billing") String var21, @Field("state_billing") String var22, @Field("postal_code_billing") String var23, @Field("country_shipping") String var24, @Field("address_shipping") String var25, @Field("city_shipping") String var26, @Field("state_shipping") String var27, @Field("postal_code_shipping") String var28, @Field("exchange_rate") String var29, @Field("is_tokenization") String var30, @Field("is_preauth") String var31, @Field("is_existing_customer") String var32, @Field("pt_token") String var33, @Field("pt_customer_email") String var34, @Field("pt_customer_password") String var35);

    @FormUrlEncoded
    @POST("/apiv2/verify_payment_transaction")
    Call<JsonElement> verifyTransaction(@Field("merchant_email") String var1, @Field("secret_key") String var2, @Field("transaction_id") String var3);
}
