package com.paytab_dk.paytabs_sdk.payment.ui.activities;


import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.paytab_dk.R;
import com.paytab_dk.paytabs_sdk.d3s.D3SSViewAuthorizationListener;
import com.paytab_dk.paytabs_sdk.d3s.D3SView;
import com.paytab_dk.paytabs_sdk.http.HttpRequests;
import com.paytab_dk.paytabs_sdk.http.OnAPICallCompleted;
import com.paytab_dk.paytabs_sdk.http.VerifyTransactionResponse;
import com.paytab_dk.paytabs_sdk.payment.ui.fragment.ErrorFragment;
import com.paytab_dk.paytabs_sdk.payment.ui.fragment.ProgressFragment;
import com.paytab_dk.paytabs_sdk.utils.JsonParser;
import com.paytab_dk.paytabs_sdk.utils.Utils;
import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;

public class SecurePaymentActivity extends AppCompatActivity implements OnAPICallCompleted, ProgressFragment.ProgressListener {
    public ProgressFragment progressFragment;
    private String processor;
    private String localTransactionId;
    private String mEmail;
    private String mSecret;
    private String merchantId;
    private String amount;
    private String currency;
    private String storeName;
    private String fullName;
    private JSONObject jsonObjectData;
    private String cEmail;
    private String cPass;
    private String token;

    public SecurePaymentActivity() {
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String language;
        try {
            this.jsonObjectData = new JSONObject(this.getIntent().getExtras().getString("data"));
            this.processor = JsonParser.getString(this.jsonObjectData, "processor");
            this.localTransactionId = JsonParser.getString(this.jsonObjectData, "local_transaction_id");
            if (this.localTransactionId == null) {
                this.localTransactionId = JsonParser.getString(this.jsonObjectData, "transaction_id");
            }

            this.mEmail = JsonParser.getString(this.jsonObjectData, "merchant_email");
            this.mSecret = JsonParser.getString(this.jsonObjectData, "merchant_secret");
            this.merchantId = JsonParser.getString(this.jsonObjectData, "merchant_id");
            language = JsonParser.getString(this.jsonObjectData, "rating");
            String type = JsonParser.getString(this.jsonObjectData, "type");
            String signature = JsonParser.getString(this.jsonObjectData, "signature");
            this.amount = JsonParser.getString(this.jsonObjectData, "amount");
            this.currency = JsonParser.getString(this.jsonObjectData, "currency");
            this.storeName = JsonParser.getString(this.jsonObjectData, "store_name");
            this.fullName = JsonParser.getString(this.jsonObjectData, "cc_holder_name");
        } catch (JSONException var26) {
            var26.printStackTrace();
            this.finish();
        }

        language = this.getIntent().getStringExtra("language");
        if (language == null || !language.equals("ar")) {
            language = "en";
        }

        Locale myLocale = new Locale(language);
        Resources res = this.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        D3SView secureView = new D3SView(this);
        secureView.getSettings().setJavaScriptEnabled(true);
        secureView.requestFocus(130);
        secureView.getSettings().setDefaultTextEncodingName("utf-8");
        secureView.setAuthorizationListener(new D3SSViewAuthorizationListener() {
            public void onAuthorizationCompleted(String response_code, String result, String customer_email, String customer_password, String customer_token) {
                SecurePaymentActivity.this.showProgressBar(SecurePaymentActivity.this.getResources().getString(R.string.paytabs_msg_payment_in_progress) + "\n" + SecurePaymentActivity.this.getResources().getString(R.string.paytabs_msg_please_wait) + "...");
                SecurePaymentActivity.this.cEmail = customer_email;
                SecurePaymentActivity.this.cPass = customer_password;
                SecurePaymentActivity.this.token = customer_token;
                SecurePaymentActivity.this.verifyTransaction();
            }

            public void onAuthorizationStarted(D3SView view) {
                SecurePaymentActivity.this.setContentView(view);
            }

            public void onAuthorizationWebPageLoadingProgressChanged(int progress) {
            }

            public void onAuthorizationWebPageLoadingError(int errorCode, String description, String failingUrl) {
            }
        });
        this.showProgressBar(this.getResources().getString(R.string.paytabs_msg_payment_in_progress) + "\n" + this.getResources().getString(R.string.paytabs_msg_please_wait) + "...");
        String callbackurl = "https://www.paytabs.com/apiv3/payer_auth_complete/" + this.localTransactionId + "/" + this.merchantId;
        if (this.jsonObjectData.has("is_tokenization") && JsonParser.getString(this.jsonObjectData, "is_tokenization").equals("true")) {
            callbackurl = callbackurl + "/new";
        }

        String vpc_AccessCode;
        String vpc_Amount;
        String vpc_CardExp;
        if (!this.processor.equalsIgnoreCase("1") && !this.processor.equalsIgnoreCase("3")) {
            if (this.processor.equalsIgnoreCase("2")) {
                vpc_AccessCode = JsonParser.getString(this.jsonObjectData, "vpc_AccessCode");
                vpc_Amount = JsonParser.getString(this.jsonObjectData, "vpc_Amount");
                vpc_CardExp = JsonParser.getString(this.jsonObjectData, "vpc_CardExp");
                String vpc_CardNum = JsonParser.getString(this.jsonObjectData, "vpc_CardNum");
                String vpc_CardSecurityCode = JsonParser.getString(this.jsonObjectData, "vpc_CardSecurityCode");
                String vpc_Command = JsonParser.getString(this.jsonObjectData, "vpc_Command");
                String vpc_Currency = JsonParser.getString(this.jsonObjectData, "vpc_Currency");
                String vpc_MerchTxnRef = JsonParser.getString(this.jsonObjectData, "vpc_MerchTxnRef");
                String vpc_Merchant = JsonParser.getString(this.jsonObjectData, "vpc_Merchant");
                String vpc_OrderInfo = JsonParser.getString(this.jsonObjectData, "vpc_OrderInfo");
                String vpc_ReturnURL = JsonParser.getString(this.jsonObjectData, "vpc_ReturnURL");
                String vpc_Version = JsonParser.getString(this.jsonObjectData, "vpc_Version");
                String vpc_card = JsonParser.getString(this.jsonObjectData, "vpc_card");
                String vpc_gateway = JsonParser.getString(this.jsonObjectData, "vpc_gateway");
                String vpc_SecureHash = JsonParser.getString(this.jsonObjectData, "vpc_SecureHash");
                String vpc_SecureHash_type = JsonParser.getString(this.jsonObjectData, "vpc_SecureHashType");
                String migs3dUrl = JsonParser.getString(this.jsonObjectData, "migs3dUrl");
                callbackurl = "https://www.paytabs.com/apiv3/auth_processing";
                secureView.authorizeMigs(migs3dUrl, callbackurl, vpc_AccessCode, vpc_Amount, vpc_CardExp, vpc_CardNum, vpc_CardSecurityCode, vpc_Command, vpc_Currency, vpc_MerchTxnRef, vpc_Merchant, vpc_OrderInfo, vpc_ReturnURL, vpc_Version, vpc_card, vpc_gateway, vpc_SecureHash, vpc_SecureHash_type);
            }
        } else {
            vpc_AccessCode = JsonParser.getString(this.jsonObjectData, "acsUrl");
            vpc_Amount = JsonParser.getString(this.jsonObjectData, "paReq");
            vpc_CardExp = JsonParser.getString(this.jsonObjectData, "xid");
            secureView.authorize(vpc_AccessCode, vpc_CardExp, vpc_Amount, callbackurl);
        }

        this.setContentView(secureView);
    }

    private void verifyTransaction() {
        if (Utils.isConnectingToInternet(this)) {
            HttpRequests httpRequests = new HttpRequests(this, this);
            httpRequests.verifyTransaction(this.mEmail, this.mSecret, this.localTransactionId, "verify transaction");
        } else {
            DialogFragment dialogFragment = ErrorFragment.newInstance(this.getString(R.string.paytabs_err_no_internet));
            dialogFragment.show(this.getSupportFragmentManager(), "error");
        }

    }

    public void onAPICallSuccess(JsonElement response, String tag) {
        if (tag.equalsIgnoreCase("verify transaction")) {
            Gson gson = new Gson();
            VerifyTransactionResponse verifyTransactionResponse = (VerifyTransactionResponse)gson.fromJson(response, VerifyTransactionResponse.class);
            Intent intent = new Intent();
            intent.putExtra("transaction_id", this.localTransactionId);
            if (verifyTransactionResponse.getResponseCode() != null) {
                intent.putExtra("response_code", verifyTransactionResponse.getResponseCode());
            }

            if (!verifyTransactionResponse.getResponseCode().equals("100") && !verifyTransactionResponse.getResponseCode().equals("111")) {
                intent.putExtra("result", verifyTransactionResponse.getResult());
                this.setResult(-1, intent);
                this.finish();
            } else {
                intent.putExtra("result", verifyTransactionResponse.getResult());
                intent.putExtra("amount", this.amount);
                intent.putExtra("currency", this.currency);
                intent.putExtra("store_name", this.storeName);
                intent.putExtra("cc_holder_name", this.fullName);
                intent.putExtra("pt_token_customer_email", this.cEmail);
                intent.putExtra("pt_token_customer_password", this.cPass);
                intent.putExtra("pt_token", this.token);
                this.setResult(-1, intent);
                this.finish();
            }
        }

    }

    public void onAPICallFailure(String apiResponse, String tag) {
    }

    public void hideProgressBar() {
        if (this.progressFragment != null) {
            this.progressFragment.dismiss();
        }

    }

    public void showProgressBar(String msg) {
        if (this.progressFragment != null) {
            this.progressFragment.dismiss();
        }

        this.progressFragment = ProgressFragment.newInstance(msg);
        this.progressFragment.setCancelable(false);
        this.progressFragment.show(this.getSupportFragmentManager(), "progress");
    }

    public boolean isShowing() {
        return this.progressFragment != null;
    }
}
