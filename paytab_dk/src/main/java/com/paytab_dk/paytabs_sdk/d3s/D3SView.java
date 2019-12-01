package com.paytab_dk.paytabs_sdk.d3s;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.text.Html;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.paytab_dk.R;
import com.paytab_dk.paytabs_sdk.payment.ui.fragment.ProgressFragment;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;

public class D3SView extends WebView {
    private static String JavaScriptNS = "PaytabsD3SJS";
    private boolean urlReturned = false;
    private boolean debugMode = false;
    private String postbackUrl = "https://www.google.com";
    private boolean postbackHandled = false;
    private boolean migs;
    private D3SSViewAuthorizationListener authorizationListener = null;
    private ProgressFragment.ProgressListener progressListener;

    public D3SView(Activity activity) {
        super(activity);
        this.progressListener = (ProgressFragment.ProgressListener)activity;
        this.initUI();
    }

    @SuppressLint({"SetJavaScriptEnabled", "AddJavascriptInterface"})
    private void initUI() {
        this.getSettings().setJavaScriptEnabled(true);
        this.getSettings().setBuiltInZoomControls(true);
        this.addJavascriptInterface(new D3SView.PaytabsD3SJSInterface(), JavaScriptNS);
        this.setWebViewClient(new WebViewClient() {
            boolean redirect = false;
            boolean loadingFinished = true;

            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (!this.loadingFinished) {
                    this.redirect = true;
                }

                this.loadingFinished = false;
                if (!D3SView.this.migs && !D3SView.this.postbackHandled && url.toLowerCase().contains(D3SView.this.postbackUrl.toLowerCase())) {
                    D3SView.this.postbackHandled = true;
                    view.loadUrl(String.format("javascript:window.%s.processHTML(document.getElementsByTagName('body')[0].innerHTML,'%s');", D3SView.JavaScriptNS, url));
                    return true;
                } else {
                    return super.shouldOverrideUrlLoading(view, url);
                }
            }

            public void onPageStarted(WebView view, String url, Bitmap icon) {
                if (!D3SView.this.urlReturned && !D3SView.this.postbackHandled || D3SView.this.migs) {
                    if (url.toLowerCase().contains(D3SView.this.postbackUrl.toLowerCase())) {
                        D3SView.this.postbackHandled = true;
                        view.setVisibility(8);
                        D3SView.this.urlReturned = true;
                        D3SView.this.progressListener.showProgressBar(D3SView.this.getResources().getString(R.string.paytabs_msg_payment_in_progress) + "\n" + D3SView.this.getResources().getString(R.string.paytabs_msg_please_wait) + "...");
                    } else {
                        super.onPageStarted(view, url, icon);
                    }
                }

                if (url.toLowerCase().contains(D3SView.this.postbackUrl.toLowerCase())) {
                    D3SView.this.urlReturned = true;
                }

                this.loadingFinished = false;
                if (!D3SView.this.progressListener.isShowing()) {
                    D3SView.this.progressListener.showProgressBar(D3SView.this.getResources().getString(R.string.paytabs_msg_payment_in_progress) + "\n" + D3SView.this.getResources().getString(R.string.paytabs_msg_please_wait) + "...");
                }

            }

            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (!this.redirect) {
                    this.loadingFinished = true;
                }

                if ((!this.loadingFinished || this.redirect) && !D3SView.this.migs) {
                    this.redirect = false;
                } else {
                    if (url.toLowerCase().contains(D3SView.this.postbackUrl.toLowerCase())) {
                        view.loadUrl(String.format("javascript:window.%s.processHTML(document.getElementsByTagName('body')[0].innerHTML,'%s');", D3SView.JavaScriptNS, url));
                        D3SView.this.urlReturned = true;
                    }

                    D3SView.this.progressListener.hideProgressBar();
                }

            }

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                if (!failingUrl.startsWith(D3SView.this.postbackUrl)) {
                    D3SView.this.authorizationListener.onAuthorizationWebPageLoadingError(errorCode, description, failingUrl);
                }

            }

            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                if (D3SView.this.debugMode) {
                    handler.proceed();
                }

            }
        });
        this.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int newProgress) {
                if (D3SView.this.authorizationListener != null) {
                    D3SView.this.authorizationListener.onAuthorizationWebPageLoadingProgressChanged(newProgress);
                }

            }
        });
    }

    public D3SView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.initUI();
    }

    public D3SView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.initUI();
    }

    private void completeAuthorization(String response_code, String result, String customer_email, String customer_password, String customer_token) {
        this.authorizationListener.onAuthorizationCompleted(response_code, result, customer_email, customer_password, customer_token);
    }

    public void setAuthorizationListener(D3SSViewAuthorizationListener authorizationListener) {
        this.authorizationListener = authorizationListener;
    }

    public void authorizeMigs(String migs3dUrl, String postbackUrl, String vpc_AccessCode, String vpc_Amount, String vpc_CardExp, String vpc_CardNum, String vpc_CardSecurityCode, String vpc_Command, String vpc_Currency, String vpc_MerchTxnRef, String vpc_Merchant, String vpc_OrderInfo, String vpc_ReturnURL, String vpc_Version, String vpc_card, String vpc_gateway, String vpc_SecureHash, String vpc_SecureHash_type) {
        if (this.authorizationListener != null) {
            this.authorizationListener.onAuthorizationStarted(this);
        }

        if (!TextUtils.isEmpty(postbackUrl)) {
            this.postbackUrl = postbackUrl;
        }

        this.urlReturned = false;
        this.migs = true;
        String postParams = null;

        try {
            postParams = String.format(Locale.US, "vpc_AccessCode=%1$s&vpc_Amount=%2$s&vpc_CardExp=%3$s&vpc_CardNum=%4$s&vpc_CardSecurityCode=%5$s&vpc_Command=%6$s&vpc_Currency=%7$s&vpc_MerchTxnRef=%8$s&vpc_Merchant=%9$s&vpc_OrderInfo=%10$s&vpc_ReturnURL=%11$s&vpc_Version=%12$s&vpc_card=%13$s&vpc_gateway=%14$s&vpc_SecureHash=%15$s&vpc_SecureHashType=%16$s", URLEncoder.encode(vpc_AccessCode, "UTF-8"), URLEncoder.encode(vpc_Amount, "UTF-8"), URLEncoder.encode(vpc_CardExp, "UTF-8"), URLEncoder.encode(vpc_CardNum, "UTF-8"), URLEncoder.encode(vpc_CardSecurityCode, "UTF-8"), URLEncoder.encode(vpc_Command, "UTF-8"), URLEncoder.encode(vpc_Currency, "UTF-8"), URLEncoder.encode(vpc_MerchTxnRef, "UTF-8"), URLEncoder.encode(vpc_Merchant, "UTF-8"), URLEncoder.encode(vpc_OrderInfo, "UTF-8"), URLEncoder.encode(vpc_ReturnURL, "UTF-8"), URLEncoder.encode(vpc_Version, "UTF-8"), URLEncoder.encode(vpc_card, "UTF-8"), URLEncoder.encode(vpc_gateway, "UTF-8"), URLEncoder.encode(vpc_SecureHash, "UTF-8"), URLEncoder.encode(vpc_SecureHash_type, "UTF-8"));
        } catch (UnsupportedEncodingException var21) {
            var21.printStackTrace();
        }

        this.postUrl(migs3dUrl, postParams.getBytes());
    }

    public void authorize(String acsUrl, String md, String paReq) {
        this.authorize(acsUrl, md, paReq, (String)null);
    }

    public void authorize(String acsUrl, String md, String paReq, String postbackUrl) {
        if (this.authorizationListener != null) {
            this.authorizationListener.onAuthorizationStarted(this);
        }

        if (!TextUtils.isEmpty(postbackUrl)) {
            this.postbackUrl = postbackUrl;
        }

        this.urlReturned = false;

        String postParams;
        try {
            postParams = String.format(Locale.US, "MD=%1$s&TermUrl=%2$s&PaReq=%3$s", URLEncoder.encode(md, "UTF-8"), URLEncoder.encode(this.postbackUrl, "UTF-8"), URLEncoder.encode(paReq, "UTF-8"));
        } catch (UnsupportedEncodingException var7) {
            throw new RuntimeException(var7);
        }

        this.postUrl(acsUrl, postParams.getBytes());
    }

    class PaytabsD3SJSInterface {
        PaytabsD3SJSInterface() {
        }

        @JavascriptInterface
        public void processHTML(String paramString, String url) {
            String responses = Html.fromHtml(paramString).toString();

            try {
                String firstLetter = responses.substring(0, 1);
                if (firstLetter.equals("{")) {
                    JSONObject jsonObj = new JSONObject(responses);
                    String customer_email = "";
                    String customer_password = "";
                    String customer_token = "";
                    if (jsonObj.has("tokenization")) {
                        String tokenization = jsonObj.getString("tokenization");
                        JSONObject tokenObj = new JSONObject(tokenization);
                        customer_email = tokenObj.getString("pt_customer_email");
                        customer_password = tokenObj.getString("pt_customer_password");
                        customer_token = tokenObj.getString("pt_token");
                    }

                    if (jsonObj.getString("response_code") != null && jsonObj.getString("result") != null) {
                        D3SView.this.completeAuthorization(jsonObj.getString("response_code"), jsonObj.getString("result"), customer_email, customer_password, customer_token);
                    }
                }
            } catch (StringIndexOutOfBoundsException var11) {
            } catch (JSONException var12) {
            }

        }
    }
}
