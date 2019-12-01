package com.paytab_dk.paytabs_sdk.d3s;

public interface D3SSViewAuthorizationListener {
    void onAuthorizationCompleted(String var1, String var2, String var3, String var4, String var5);

    void onAuthorizationStarted(D3SView var1);

    void onAuthorizationWebPageLoadingProgressChanged(int var1);

    void onAuthorizationWebPageLoadingError(int var1, String var2, String var3);
}
