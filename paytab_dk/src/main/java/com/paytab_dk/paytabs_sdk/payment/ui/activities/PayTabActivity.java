package com.paytab_dk.paytabs_sdk.payment.ui.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.paytab_dk.R;
import com.paytab_dk.paytabs_sdk.http.HttpRequests;
import com.paytab_dk.paytabs_sdk.http.MerchantInfoResponse;
import com.paytab_dk.paytabs_sdk.http.OnAPICallCompleted;
import com.paytab_dk.paytabs_sdk.http.PrepareTransactionResponse;
import com.paytab_dk.paytabs_sdk.http.ValidateKeyResponse;
import com.paytab_dk.paytabs_sdk.payment.process.DialogTitleListener;
import com.paytab_dk.paytabs_sdk.payment.ui.fragment.ErrorFragment;
import com.paytab_dk.paytabs_sdk.payment.ui.fragment.PaymentFragment;
import com.paytab_dk.paytabs_sdk.payment.ui.fragment.ProgressFragment;
import com.paytab_dk.paytabs_sdk.utils.Utils;
import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;

public class PayTabActivity extends AppCompatActivity implements OnAPICallCompleted, PaymentFragment.OnFragmentInteractionListener, OnClickListener {
    private static final String LOGO = "1";
    private ProgressFragment progressFragment;
    private FragmentManager fragmentManager;
    private static final String TAG_ERROR = "error";
    private DialogTitleListener listener;
    private String mEmail;
    private String mSecret;
    private String transactionTitle;
    private double amount;
    private String currencyCode;
    private String customerPhone;
    private String customerEmail;
    private String orderId;
    private String productName;
    private String billAddress;
    private String billCity;
    private String billState;
    private String billCountry;
    private String billPostalCode;
    private String shipAddress;
    private String shipCity;
    private String shipState;
    private String shipCountry;
    private String shipPostalCode;
    private boolean isTokenization;
    private boolean isPreAuth;
    private String storeName;
    private String exchangeRate;
    private String name;
    private static final int SECURE_PAYMENT = 1001;
    private CountDownTimer countDownTimer;
    private String buttonColor;
    private boolean isExistCustomer;
    private boolean isMbme;
    private String tokenPassword;
    private String token;
    private String tokenEmail;
    private String language;

    public PayTabActivity() {
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getUserData();
        this.setTheme(R.style.PaytabsAppTheme_NoActionBar);
        if (this.language == null || !this.language.equals("ar")) {
            this.language = "en";
        }

        Locale myLocale = new Locale(this.language);
        Resources res = this.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        this.setContentView(R.layout.paytabs_activity_pay_tab);
        LinearLayout header = (LinearLayout)this.findViewById(R.id.header);
        if (this.isMbme) {
            header.setBackgroundResource(R.drawable.paytabs_mbme_header_bg);
            this.findViewById(R.id.paytabs_mbme_bg).setVisibility(0);
        } else {
            this.findViewById(R.id.paytabs_mbme_bg).setVisibility(8);

            try {
                header.setBackgroundColor(Color.parseColor(this.buttonColor));
            } catch (Exception var8) {
                this.buttonColor = "#0374bc";
                header.setBackgroundColor(Color.parseColor(this.buttonColor));
            }
        }

        this.fragmentManager = this.getSupportFragmentManager();
        this.progressFragment = ProgressFragment.newInstance(this.getString(R.string.paytabs_msg_preparing_for_payments));
        this.progressFragment.setCancelable(false);
        this.progressFragment.show(this.fragmentManager, "progress");
        this.listener = this.progressFragment;
        if (!this.isValidAmount()) {
            this.showError(this.getString(R.string.paytabs_invalid_amount));
        } else {
            if (Utils.isConnectingToInternet(this)) {
                HttpRequests httpRequests = new HttpRequests(this, this);
                httpRequests.validateSecret(this.mEmail, this.mSecret, "validate secret");
            } else {
                this.showError(this.getString(R.string.paytabs_err_no_internet));
            }

            this.findViewById(R.id.back_button).setOnClickListener(this);
        }
    }

    private void getUserData() {
        this.mEmail = this.getIntent().getStringExtra("pt_merchant_email");
        this.mSecret = this.getIntent().getStringExtra("pt_secret_key");
        this.transactionTitle = this.getIntent().getStringExtra("pt_transaction_title");
        this.amount = this.getIntent().getDoubleExtra("pt_amount", 0.0D);
        this.currencyCode = this.getIntent().getStringExtra("pt_currency_code");
        this.customerPhone = this.getIntent().getStringExtra("pt_customer_phone_number");
        this.customerEmail = this.getIntent().getStringExtra("pt_customer_email");
        this.orderId = this.getIntent().getStringExtra("pt_order_id");
        this.productName = this.getIntent().getStringExtra("pt_product_name");
        this.billAddress = this.getIntent().getStringExtra("pt_address_billing");
        this.billCity = this.getIntent().getStringExtra("pt_city_billing");
        this.billState = this.getIntent().getStringExtra("pt_state_billing");
        this.billCountry = this.getIntent().getStringExtra("pt_country_billing");
        this.billPostalCode = this.getIntent().getStringExtra("pt_postal_code_billing");
        this.shipAddress = this.getIntent().getStringExtra("pt_address_shipping");
        this.shipCity = this.getIntent().getStringExtra("pt_city_shipping");
        this.shipState = this.getIntent().getStringExtra("pt_state_shipping");
        this.shipCountry = this.getIntent().getStringExtra("pt_country_shipping");
        this.shipPostalCode = this.getIntent().getStringExtra("pt_postal_code_shipping");
        this.buttonColor = this.getIntent().getStringExtra("pay_button_color");
        this.language = this.getIntent().getStringExtra("language");
        if (this.getIntent().hasExtra("pt_is_tokenization")) {
            this.isTokenization = this.getIntent().getBooleanExtra("pt_is_tokenization", false);
        }

        if (this.getIntent().hasExtra("pt_is_preauth")) {
            this.isPreAuth = this.getIntent().getBooleanExtra("pt_is_preauth", false);
        }

        if (this.mEmail.equals("vkumar@mbmepay.ae")) {
            this.isMbme = true;
            this.buttonColor = "#D8418D";
        } else {
            this.isMbme = false;
        }

        this.isExistCustomer = false;
        this.tokenPassword = null;
        this.token = null;
        this.tokenEmail = null;
    }

    private boolean isValidAmount() {
        return this.amount > 0.0D;
    }

    private void showError(String msg) {
        this.progressFragment.dismiss();
        DialogFragment dialogFragment = ErrorFragment.newInstance(msg);
        dialogFragment.show(this.fragmentManager, "error");
    }

    public void onAPICallSuccess(JsonElement response, String tag) {
        byte var4 = -1;
        switch(tag.hashCode()) {
            case -568615748:
                if (tag.equals("get merchant info")) {
                    var4 = 1;
                }
                break;
            case 303388922:
                if (tag.equals("validate secret")) {
                    var4 = 0;
                }
                break;
            case 918894917:
                if (tag.equals("prepare transaction")) {
                    var4 = 2;
                }
        }

        switch(var4) {
            case 0:
                Gson gson = new Gson();
                ValidateKeyResponse validateKeyResponse = (ValidateKeyResponse)gson.fromJson(response, ValidateKeyResponse.class);
                if (validateKeyResponse != null && validateKeyResponse.getResponse_code().equals("4000")) {
                    if (this.listener != null) {
                        this.listener.setTitle(this.getString(R.string.paytabs_start_payment_process));
                        if (Utils.isConnectingToInternet(this)) {
                            HttpRequests httpRequests = new HttpRequests(this, this);
                            httpRequests.getMerchantInfo(this.mEmail, this.mSecret, "1", this.amount, this.currencyCode, this.isTokenization, this.isPreAuth, this.isExistCustomer, this.token, this.tokenEmail, this.tokenPassword, "get merchant info");
                        } else {
                            this.showError(this.getString(R.string.paytabs_err_no_internet));
                        }
                    }
                } else {
                    this.showError(this.getString(R.string.paytabs_err_auth));
                }
                break;
            case 1:
                Gson gson1 = new Gson();
                MerchantInfoResponse merchantInfoResponse = (MerchantInfoResponse)gson1.fromJson(response, MerchantInfoResponse.class);
                if (merchantInfoResponse != null && merchantInfoResponse.getResponse_code().equals("4000")) {
                    if (this.amount >= (double)Float.parseFloat(merchantInfoResponse.getMin_amount()) && this.amount <= (double)Float.parseFloat(merchantInfoResponse.getMax_amount())) {
                        this.storeName = merchantInfoResponse.getMerchant_title();
                        String transactionCurrency = merchantInfoResponse.getTransaction_currency();
                        String paymentTimeout = merchantInfoResponse.getPayment_timeout();
                        this.exchangeRate = merchantInfoResponse.getTransaction_exchange_rate();
                        String transactionAmount = merchantInfoResponse.getTransaction_amount();
                        int timeoutInSecs;
                        if (paymentTimeout != null && !paymentTimeout.isEmpty()) {
                            try {
                                timeoutInSecs = Integer.parseInt(paymentTimeout);
                            } catch (NumberFormatException var14) {
                                timeoutInSecs = -1;
                            }
                        } else {
                            timeoutInSecs = -1;
                        }

                        if (timeoutInSecs > 0) {
                            this.countDownTimer = new CountDownTimer((long)(timeoutInSecs * 1000), 1000L) {
                                public void onTick(long millisUntilFinished) {
                                }

                                public void onFinish() {
                                    PayTabActivity.this.showError(PayTabActivity.this.getResources().getString(R.string.paytabs_err_timeout));
                                }
                            };
                            this.countDownTimer.start();
                        }

                        this.progressFragment.dismiss();
                        FragmentManager fragmentManager = this.getSupportFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.container, PaymentFragment.newInstance(this.amount, this.currencyCode, transactionAmount, transactionCurrency, this.buttonColor, this.isMbme)).commit();
                        return;
                    }

                    this.showError(this.getString(R.string.paytabs_err_amount) + " " + this.currencyCode + " " + merchantInfoResponse.getMin_amount() + " " + this.getResources().getString(R.string.paytabs_and) + " " + this.currencyCode + " " + merchantInfoResponse.getMax_amount());
                    return;
                }

                this.showError(merchantInfoResponse.getResult());
                break;
            case 2:
                Gson gson2 = new Gson();
                PrepareTransactionResponse prepareTransactionResponse = (PrepareTransactionResponse)gson2.fromJson(response, PrepareTransactionResponse.class);
                Intent in;
                JSONObject jsonObjectData;
                if (prepareTransactionResponse != null && prepareTransactionResponse.getResponseCode().equals("0909")) {
                    in = new Intent(this, SecurePaymentActivity.class);
                    jsonObjectData = new JSONObject();

                    try {
                        jsonObjectData.put("local_transaction_id", prepareTransactionResponse.getLocalTransactionId());
                        jsonObjectData.put("transaction_id", prepareTransactionResponse.getTransactionId());
                        jsonObjectData.put("merchant_email", this.mEmail);
                        jsonObjectData.put("merchant_secret", this.mSecret);
                        jsonObjectData.put("merchant_id", prepareTransactionResponse.getMerchantId());
                        jsonObjectData.put("rating", "3");
                        jsonObjectData.put("type", "");
                        jsonObjectData.put("signature", "test");
                        jsonObjectData.put("amount", this.amount);
                        jsonObjectData.put("currency", this.currencyCode);
                        jsonObjectData.put("store_name", this.storeName);
                        jsonObjectData.put("cc_holder_name", this.name);
                        jsonObjectData.put("processor", "2");
                        jsonObjectData.put("vpc_AccessCode", prepareTransactionResponse.getAuthenticationArray().getVpcAccessCode());
                        jsonObjectData.put("vpc_Amount", prepareTransactionResponse.getAuthenticationArray().getVpcAmount());
                        jsonObjectData.put("vpc_CardExp", prepareTransactionResponse.getAuthenticationArray().getVpcCardExp());
                        jsonObjectData.put("vpc_CardNum", prepareTransactionResponse.getAuthenticationArray().getVpcCardNum());
                        jsonObjectData.put("vpc_CardSecurityCode", prepareTransactionResponse.getAuthenticationArray().getVpcCardSecurityCode());
                        jsonObjectData.put("vpc_Command", prepareTransactionResponse.getAuthenticationArray().getVpcCommand());
                        jsonObjectData.put("vpc_Currency", prepareTransactionResponse.getAuthenticationArray().getVpcCurrency());
                        jsonObjectData.put("vpc_MerchTxnRef", prepareTransactionResponse.getAuthenticationArray().getVpcMerchTxnRef());
                        jsonObjectData.put("vpc_Merchant", prepareTransactionResponse.getAuthenticationArray().getVpcMerchant());
                        jsonObjectData.put("vpc_OrderInfo", prepareTransactionResponse.getAuthenticationArray().getVpcOrderInfo());
                        jsonObjectData.put("vpc_ReturnURL", prepareTransactionResponse.getAuthenticationArray().getVpcReturnURL());
                        jsonObjectData.put("vpc_Version", prepareTransactionResponse.getAuthenticationArray().getVpcVersion());
                        jsonObjectData.put("vpc_card", prepareTransactionResponse.getAuthenticationArray().getVpcCard());
                        jsonObjectData.put("vpc_gateway", prepareTransactionResponse.getAuthenticationArray().getVpcGateway());
                        jsonObjectData.put("vpc_SecureHash", prepareTransactionResponse.getSecureHash());
                        jsonObjectData.put("vpc_SecureHashType", "SHA256");
                        jsonObjectData.put("migs3dUrl", prepareTransactionResponse.getTransactionUrl());
                        jsonObjectData.put("is_tokenization", this.isTokenization);
                        jsonObjectData.put("is_preauth", this.isPreAuth);
                    } catch (JSONException var17) {
                        var17.printStackTrace();
                    }

                    in.putExtra("data", jsonObjectData.toString());
                    in.putExtra("language", this.language);
                    this.progressFragment.dismiss();
                    this.startActivityForResult(in, 1001);
                } else if (prepareTransactionResponse.getResponseCode().trim().equals("0900")) {
                    in = new Intent(this, SecurePaymentActivity.class);
                    jsonObjectData = new JSONObject();

                    try {
                        jsonObjectData.put("merchant_email", this.mEmail);
                        jsonObjectData.put("merchant_secret", this.mSecret);
                        jsonObjectData.put("processor", "1");
                        jsonObjectData.put("acsUrl", prepareTransactionResponse.getPayerAuthEnrollReply().getAcsURL());
                        jsonObjectData.put("paReq", prepareTransactionResponse.getPayerAuthEnrollReply().getPaReq());
                        jsonObjectData.put("xid", prepareTransactionResponse.getPayerAuthEnrollReply().getXid());
                        jsonObjectData.put("local_transaction_id", prepareTransactionResponse.getLocalTransactionId());
                        jsonObjectData.put("merchant_id", prepareTransactionResponse.getMerchantId());
                        jsonObjectData.put("rating", "3");
                        jsonObjectData.put("signature", "test");
                        jsonObjectData.put("amount", this.amount);
                        jsonObjectData.put("currency", this.currencyCode);
                        jsonObjectData.put("store_name", this.storeName);
                        jsonObjectData.put("cc_holder_name", this.name);
                        jsonObjectData.put("is_tokenization", this.isTokenization);
                        jsonObjectData.put("is_preauth", this.isPreAuth);
                    } catch (JSONException var16) {
                        var16.printStackTrace();
                    }

                    in.putExtra("data", jsonObjectData.toString());
                    in.putExtra("language", this.language);
                    this.progressFragment.dismiss();
                    this.startActivityForResult(in, 1001);
                } else if (prepareTransactionResponse.getResponseCode().trim().equals("0910")) {
                    in = new Intent(this, SecurePaymentActivity.class);
                    jsonObjectData = new JSONObject();

                    try {
                        jsonObjectData.put("merchant_email", this.mEmail);
                        jsonObjectData.put("merchant_secret", this.mSecret);
                        jsonObjectData.put("processor", "3");
                        jsonObjectData.put("acsUrl", prepareTransactionResponse.getMPGSauthenticationArray().getAcsURL());
                        jsonObjectData.put("paReq", prepareTransactionResponse.getMPGSauthenticationArray().getPaReq());
                        jsonObjectData.put("xid", prepareTransactionResponse.getMPGSauthenticationArray().getXid());
                        jsonObjectData.put("local_transaction_id", prepareTransactionResponse.getLocalTransactionId());
                        jsonObjectData.put("merchant_id", prepareTransactionResponse.getMerchantId());
                        jsonObjectData.put("amount", this.amount);
                        jsonObjectData.put("currency", this.currencyCode);
                        jsonObjectData.put("store_name", this.storeName);
                        jsonObjectData.put("cc_holder_name", this.name);
                        jsonObjectData.put("is_tokenization", this.isTokenization);
                        jsonObjectData.put("is_preauth", this.isPreAuth);
                    } catch (JSONException var15) {
                        var15.printStackTrace();
                    }

                    in.putExtra("data", jsonObjectData.toString());
                    in.putExtra("language", this.language);
                    this.progressFragment.dismiss();
                    this.startActivityForResult(in, 1001);
                } else if (prepareTransactionResponse.getResponseCode().trim().equals("1")) {
                    this.progressFragment.dismiss();
                    this.onFragmentInteraction(prepareTransactionResponse.getResponseCode(), prepareTransactionResponse.getResult(), prepareTransactionResponse.getTransactionId(), (String)null, (String)null, (String)null);
                } else if (!prepareTransactionResponse.getResponseCode().trim().equals("0") && !prepareTransactionResponse.getResponseCode().trim().equals("4001")) {
                    if (prepareTransactionResponse.getResponseCode().trim().equals("100")) {
                        this.progressFragment.dismiss();
                        if (prepareTransactionResponse.getTokenization() != null) {
                            if (prepareTransactionResponse.getTokenization().getToken() != null && !prepareTransactionResponse.getTokenization().getToken().isEmpty()) {
                                this.onFragmentInteraction(prepareTransactionResponse.getResponseCode(), prepareTransactionResponse.getResult(), prepareTransactionResponse.getLocalTransactionId(), prepareTransactionResponse.getTokenization().getToken(), prepareTransactionResponse.getTokenization().getPassword(), prepareTransactionResponse.getTokenization().getCustomerEmail());
                            }
                        } else {
                            this.onFragmentInteraction(prepareTransactionResponse.getResponseCode(), prepareTransactionResponse.getResult(), prepareTransactionResponse.getLocalTransactionId(), (String)null, (String)null, (String)null);
                        }
                    } else {
                        this.progressFragment.dismiss();
                        this.onFragmentInteraction(prepareTransactionResponse.getResponseCode(), prepareTransactionResponse.getResult(), prepareTransactionResponse.getLocalTransactionId(), (String)null, (String)null, (String)null);
                    }
                } else {
                    this.progressFragment.dismiss();
                    this.onFragmentInteraction(prepareTransactionResponse.getResponseCode(), prepareTransactionResponse.getResult(), "", (String)null, (String)null, (String)null);
                }
        }

    }

    public void onAPICallFailure(String apiResponse, String tag) {
        this.showError(apiResponse);
    }

    public void onPayClicked(String name, String cardNumber, String month, String year, String cvv) {
        if (this.countDownTimer != null) {
            this.countDownTimer.cancel();
        }

        this.progressFragment = ProgressFragment.newInstance(this.getResources().getString(R.string.paytabs_msg_payment_in_progress) + "\n" + this.getResources().getString(R.string.paytabs_msg_please_wait) + "...");
        this.progressFragment.setCancelable(false);
        this.progressFragment.show(this.fragmentManager, "progress");
        this.name = name;
        if (Utils.isConnectingToInternet(this)) {
            HttpRequests httpRequests = new HttpRequests(this, this);
            httpRequests.prepareTransaction(this.mSecret, this.mEmail, this.amount, this.transactionTitle, name.substring(0, name.indexOf(" ")), name.substring(name.indexOf(" ") + 1), year + month, cvv, cardNumber.replace(" ", ""), "SDK", this.currencyCode, this.customerEmail, "1", this.customerPhone, this.orderId, this.productName, this.customerEmail, this.billCountry, this.billAddress, this.billCity, this.billState, this.billPostalCode, this.shipCountry, this.shipAddress, this.shipCity, this.shipState, this.shipPostalCode, this.exchangeRate, this.isTokenization, this.isPreAuth, this.isExistCustomer, this.token, this.tokenEmail, this.tokenPassword, "prepare transaction");
        } else {
            this.showError(this.getString(R.string.paytabs_err_no_internet));
        }

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001 && resultCode == -1) {
            JSONObject jsonObject = new JSONObject();

            try {
                jsonObject.put("result", data.getStringExtra("result"));
                jsonObject.put("amount", data.getStringExtra("amount"));
                jsonObject.put("currency", data.getStringExtra("currency"));
                jsonObject.put("store_name", data.getStringExtra("store_name"));
                jsonObject.put("cc_holder_name", data.getStringExtra("cc_holder_name"));
                jsonObject.put("response_code", data.getStringExtra("response_code"));
                jsonObject.put("transaction_id", data.getStringExtra("transaction_id"));
                jsonObject.put("pt_token_customer_email", data.getStringExtra("pt_token_customer_email"));
                jsonObject.put("pt_token_customer_password", data.getStringExtra("pt_token_customer_password"));
                jsonObject.put("pt_token", data.getStringExtra("pt_token"));
            } catch (JSONException var6) {
                var6.printStackTrace();
            }

            this.progressFragment.dismiss();
            this.onFragmentInteraction(data.getStringExtra("response_code"), data.getStringExtra("result"), data.getStringExtra("transaction_id"), data.getStringExtra("pt_token"), data.getStringExtra("pt_token_customer_password"), data.getStringExtra("pt_token_customer_email"));
        }

    }

    public void onFragmentInteraction(String responseCode, String result, String transactionId, String token, String customerPassword, String customerEmail) {
        Intent intent = new Intent();
        intent.putExtra("pt_response_code", responseCode);
        intent.putExtra("pt_transaction_id", transactionId == null ? "" : transactionId);
        intent.putExtra("pt_result", result);
        if (token != null) {
            intent.putExtra("pt_token", token);
            intent.putExtra("pt_customer_password", customerPassword);
            intent.putExtra("pt_customer_email", customerEmail);
        }

        this.setResult(-1, intent);
        this.finish();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 16908332) {
            this.setResult(0);
            this.finish();
            return false;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    protected void onPause() {
        super.onPause();
        if (this.countDownTimer != null) {
            this.countDownTimer.cancel();
        }

        this.progressFragment.dismiss();
        this.fragmentManager.popBackStack();
    }

    public void onClick(View v) {
        if (v.getId() == R.id.back_button) {
            this.setResult(0);
            this.finish();
        }

    }
}
