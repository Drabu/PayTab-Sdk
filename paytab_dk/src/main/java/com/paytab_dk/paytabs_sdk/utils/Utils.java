package com.paytab_dk.paytabs_sdk.utils;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class Utils {
    private static final Map<Utils.CcnTypeEnum, String> regexes = new HashMap<Utils.CcnTypeEnum, String>() {
        {
            this.put(Utils.CcnTypeEnum.VISA, "^4\\d{3}([\\ \\-]?)(?:\\d{4}\\1){2}\\d(?:\\d{3})?$");
            this.put(Utils.CcnTypeEnum.MASTERCARD, "^5[1-5]\\d{2}([\\ \\-]?)\\d{4}\\1\\d{4}\\1\\d{4}$");
            this.put(Utils.CcnTypeEnum.AMERICAN_EXPRESS, "^3[47]\\d\\d([\\ \\-]?)\\d{6}\\1\\d{5}$");
            this.put(Utils.CcnTypeEnum.CHINA_UNIONPAY, "^62[0-5]\\d{13,16}$");
            this.put(Utils.CcnTypeEnum.DISCOVER, "^6(?:011|22(?:1(?=[\\ \\-]?(?:2[6-9]|[3-9]))|[2-8]|9(?=[\\ \\-]?(?:[01]|2[0-5])))|4[4-9]\\d|5\\d\\d)([\\ \\-]?)\\d{4}\\1\\d{4}\\1\\d{4}$");
            this.put(Utils.CcnTypeEnum.JAPANESE_CREDIT_BUREAU, "^35(?:2[89]|[3-8]\\d)([\\ \\-]?)\\d{4}\\1\\d{4}\\1\\d{4}$");
            this.put(Utils.CcnTypeEnum.MAESTRO, "^(?:5[0678]\\d\\d|6304|6390|67\\d\\d)\\d{8,15}$");
            this.put(Utils.CcnTypeEnum.DINERS, "^3(?:0[0-5]|[68][0-9])[0-9]{11}$");
        }
    };

    public Utils() {
    }

    public static boolean isConnectingToInternet(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService("connectivity");
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; ++i) {
                    if (info[i].getState() == State.CONNECTED) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public static Utils.CcnTypeEnum validate(String cardNumber) {
        Iterator var1 = regexes.entrySet().iterator();

        Entry entry;
        do {
            if (!var1.hasNext()) {
                return Utils.CcnTypeEnum.INVALID;
            }

            entry = (Entry) var1.next();
        } while (!cardNumber.matches((String) entry.getValue()));

        return (Utils.CcnTypeEnum) entry.getKey();
    }

    public static enum CcnTypeEnum {
        INVALID,
        VISA,
        MASTERCARD,
        DISCOVER,
        JAPANESE_CREDIT_BUREAU,
        AMERICAN_EXPRESS,
        CHINA_UNIONPAY,
        MAESTRO,
        DINERS;

        private CcnTypeEnum() {
        }
    }
}
