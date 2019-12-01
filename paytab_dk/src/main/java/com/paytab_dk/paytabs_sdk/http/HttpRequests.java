package com.paytab_dk.paytabs_sdk.http;

import android.content.Context;
import com.google.gson.JsonElement;
import com.paytab_dk.R;
//import com.paytab_dk.paytabs_sdk.string;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HttpRequests {
    OnAPICallCompleted onAPICallCompleted;
    Context context;

    public HttpRequests(Context context, OnAPICallCompleted onAPICallCompleted) {
        this.context = context;
        this.onAPICallCompleted = onAPICallCompleted;
    }

    public void validateSecret(String email, String key, final String tag) {
        Retrofit retrofit = APIClient.getClient();
        ApiInterface apiInterface = (ApiInterface)retrofit.create(ApiInterface.class);
        Call<JsonElement> validateKeyResponseCall = apiInterface.validateKey(email, key);
        validateKeyResponseCall.enqueue(new Callback<JsonElement>() {
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                HttpRequests.this.onAPICallCompleted.onAPICallSuccess((JsonElement)response.body(), tag);
            }

            public void onFailure(Call<JsonElement> call, Throwable t) {
                HttpRequests.this.onAPICallCompleted.onAPICallFailure(HttpRequests.this.context.getString(R.string.paytabs_err_unknown), tag);
            }
        });
    }

    public void getMerchantInfo(String email, String key, String logo, double amount, String currencyCode, boolean isTokenization, boolean isPreAuth, boolean existingMerchent, String tokenizationToken, String tokenizationCustomerEmail, String tokenizationCustomerPassword, final String tag) {
        Retrofit retrofit = APIClient.getClient();
        ApiInterface apiInterface = (ApiInterface)retrofit.create(ApiInterface.class);
        Call<JsonElement> merchantInfo = apiInterface.getMerchantInfo(email, key, logo, amount, currencyCode, isTokenization ? "TRUE" : null, isPreAuth ? "1" : "0", existingMerchent ? "yess" : "no", tokenizationToken, tokenizationCustomerEmail, tokenizationCustomerPassword);
        merchantInfo.enqueue(new Callback<JsonElement>() {
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                HttpRequests.this.onAPICallCompleted.onAPICallSuccess((JsonElement)response.body(), tag);
            }

            public void onFailure(Call<JsonElement> call, Throwable t) {
                HttpRequests.this.onAPICallCompleted.onAPICallFailure(HttpRequests.this.context.getString(R.string.paytabs_err_unknown), tag);
            }
        });
    }

    public void prepareTransaction(String key, String merchantEmail, double amount, String title, String firstName, String lastName, String expiryDate, String cvv, String cardNumber, String assigneeCode, String currency, String email, String skipEmail, String phoneNumber, String orderId, String productName, String customerEmail, String billCountry, String billAddress, String billCity, String billState, String billPostalCode, String shipCountry, String shipAddress, String shipCity, String shipState, String shipPostalCode, String rate, boolean isTokenization, boolean isPreAuth, boolean existingMerchent, String tokenizationToken, String tokenizationCustomerEmail, String tokenizationCustomerPassword, final String tag) {
        Retrofit retrofit = APIClient.getClient();
        ApiInterface apiInterface = (ApiInterface)retrofit.create(ApiInterface.class);
        if (!existingMerchent) {
            tokenizationToken = null;
            tokenizationCustomerEmail = null;
            tokenizationCustomerPassword = null;
        }

        Call<JsonElement> prepare = apiInterface.prepareTransaction(key, merchantEmail, amount, title, firstName, lastName, expiryDate, cvv, cardNumber, assigneeCode, currency, email, skipEmail, phoneNumber, orderId, productName, customerEmail, billCountry, billAddress, billCity, billState, billPostalCode, shipCountry, shipAddress, shipCity, shipState, shipPostalCode, rate, isTokenization ? "TRUE" : null, isPreAuth ? "1" : "0", existingMerchent ? "yess" : "no", tokenizationToken, tokenizationCustomerEmail, tokenizationCustomerPassword);
        prepare.enqueue(new Callback<JsonElement>() {
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                HttpRequests.this.onAPICallCompleted.onAPICallSuccess((JsonElement)response.body(), tag);
            }

            public void onFailure(Call<JsonElement> call, Throwable t) {
                HttpRequests.this.onAPICallCompleted.onAPICallFailure(HttpRequests.this.context.getString(R.string.paytabs_err_unknown), tag);
            }
        });
    }

    public void verifyTransaction(String merchantEmail, String key, String transactionId, final String tag) {
        Retrofit retrofit = APIClient.getClient();
        ApiInterface apiInterface = (ApiInterface)retrofit.create(ApiInterface.class);
        Call<JsonElement> verify = apiInterface.verifyTransaction(merchantEmail, key, transactionId);
        verify.enqueue(new Callback<JsonElement>() {
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                HttpRequests.this.onAPICallCompleted.onAPICallSuccess((JsonElement)response.body(), tag);
            }

            public void onFailure(Call<JsonElement> call, Throwable t) {
                HttpRequests.this.onAPICallCompleted.onAPICallFailure(HttpRequests.this.context.getString(R.string.paytabs_err_unknown), tag);
            }
        });
    }
}
