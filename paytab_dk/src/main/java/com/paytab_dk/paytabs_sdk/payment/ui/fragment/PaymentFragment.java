package com.paytab_dk.paytabs_sdk.payment.ui.fragment;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff.Mode;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import androidx.fragment.app.Fragment;
import com.devmarvel.creditcardentry.library.CreditCardForm;
import com.google.android.material.snackbar.Snackbar;
import com.paytab_dk.R;
import com.paytab_dk.paytabs_sdk.utils.RoundCornerDrawable;

import cards.pay.paycardsrecognizer.sdk.Card;
import cards.pay.paycardsrecognizer.sdk.ScanCardIntent.Builder;

import java.util.Calendar;
import java.util.Currency;
import java.util.Locale;

public class PaymentFragment extends Fragment implements OnFocusChangeListener {
    private static final String ARG_AMOUNT = "amount";
    private static final String ARG_CURRENCY = "currency";
    private static final String ARG_TRANS_AMOUNT = "transAmount";
    private static final String ARG_TRANS_CURRENCY = "transCurrency";
    private static final String ARG_BUTTON_COLOR = "buttonColor";
    private static final String ARG_IS_MBME = "ismbme";
    private static final int REQUEST_CODE_SCAN_CARD = 7990;
    private double amount;
    private String currency;
    private String transAmount;
    private String transCurrency;
    private String buttonColor;
    private EditText tEdName;
    private CreditCardForm tEdCardNumber;
    private Spinner tEdMonth;
    private Spinner tEdYear;
    private EditText tEdCvv;
    private PaymentFragment.OnFragmentInteractionListener mListener;
    private View rootView;
    private View tEdCardNumberLinearlayout;
    private String[] years;
    private String[] months;
    private boolean isMbme;

    public PaymentFragment() {
    }

    public static PaymentFragment newInstance(double amount, String currency, String transAmount, String transCurrency, String buttonColor, boolean isMbme) {
        PaymentFragment fragment = new PaymentFragment();
        Bundle args = new Bundle();
        args.putDouble("amount", amount);
        args.putString("currency", currency);
        args.putString("transAmount", transAmount);
        args.putString("transCurrency", transCurrency);
        args.putString("buttonColor", buttonColor);
        args.putBoolean("ismbme", isMbme);
        fragment.setArguments(args);
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setHasOptionsMenu(true);
        if (this.getArguments() != null) {
            this.amount = this.getArguments().getDouble("amount");
            this.currency = this.getArguments().getString("currency");
            this.transAmount = this.getArguments().getString("transAmount");
            this.transCurrency = this.getArguments().getString("transCurrency");
            this.buttonColor = this.getArguments().getString("buttonColor");
            this.isMbme = this.getArguments().getBoolean("ismbme");
        }

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.rootView = inflater.inflate(R.layout.paytabs_fragment_payment, container, false);
        TextView tVAmount = (TextView)this.rootView.findViewById(R.id.amount);
        TextView tVCurrency = (TextView)this.rootView.findViewById(R.id.currency);
        TextView tVAmountT = (TextView)this.rootView.findViewById(R.id.amount_t);
        TextView tVCurrencyT = (TextView)this.rootView.findViewById(R.id.currency_t);
        ImageButton scanCardBtn = (ImageButton)this.rootView.findViewById(R.id.scan_card);

        try {
            Class.forName("cards.pay.paycardsrecognizer.sdk.ScanCardIntent");
        } catch (ClassNotFoundException var14) {
            scanCardBtn.setVisibility(4);
        }

        if (this.isMbme) {
            this.rootView.findViewById(R.id.paytabs_mbme).setVisibility(0);
        } else {
            this.rootView.findViewById(R.id.paytabs_mbme).setVisibility(8);
        }

        this.tEdName = (EditText)this.rootView.findViewById(R.id.cardholder_name);
        this.tEdCardNumber = (CreditCardForm)this.rootView.findViewById(R.id.card_number);
        this.tEdMonth = (Spinner)this.rootView.findViewById(R.id.exp_month);
        this.tEdYear = (Spinner)this.rootView.findViewById(R.id.exp_year);
        this.tEdCvv = (EditText)this.rootView.findViewById(R.id.cvv);
        this.tEdCardNumberLinearlayout = this.rootView.findViewById(R.id.card_number_container);
        tVAmount.setText(String.format(Locale.ENGLISH, String.format(Locale.ENGLISH, "%%.%df", Currency.getInstance(this.currency).getDefaultFractionDigits()), this.amount));
        tVCurrency.setText(this.currency);
        if (!this.currency.equalsIgnoreCase(this.transCurrency)) {
            tVAmountT.setText(String.format(Locale.ENGLISH, String.format(Locale.ENGLISH, "%%.%df", Currency.getInstance(this.transCurrency).getDefaultFractionDigits()), Double.parseDouble(this.transAmount)));
            tVCurrencyT.setText(this.transCurrency);
            this.rootView.findViewById(R.id.t).setVisibility(0);
        } else {
            this.rootView.findViewById(R.id.t).setVisibility(8);
        }

        scanCardBtn.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent intent = (new Builder(PaymentFragment.this.getActivity())).build();
                PaymentFragment.this.startActivityForResult(intent, 7990);
            }
        });
        Button btnPay = (Button)this.rootView.findViewById(R.id.btnPay);
        if (this.isMbme) {
            btnPay.setBackgroundResource(R.drawable.paytabs_mbme_button_bg);
        } else if (this.buttonColor != null && !this.buttonColor.isEmpty()) {
            try {
                btnPay.setBackground(new RoundCornerDrawable(Color.parseColor(this.buttonColor)));
            } catch (Exception var13) {
                Log.e("setBackgroundColor", this.buttonColor);
            }
        }

        this.months = new String[13];
        this.months[0] = this.getResources().getString(R.string.paytabs_card_exp_month);

        for(int i = 1; i <= 12; ++i) {
            this.months[i] = i >= 10 ? String.valueOf(i) : "0" + i;
        }

        ArrayAdapter<String> monthsArrayAdapter = new ArrayAdapter(this.getActivity(), R.layout.paytabs_spinner_adapter_row, this.months);
        monthsArrayAdapter.setDropDownViewResource(17367049);
        this.tEdMonth.setAdapter(monthsArrayAdapter);
        this.years = new String[24];
        int currentYear = Integer.parseInt(Integer.toString(Calendar.getInstance().get(1)).substring(2, 4));
        this.years[0] = this.getResources().getString(R.string.paytabs_card_exp_year);

        for(int i = 0; i <= 22; ++i) {
            this.years[i + 1] = String.valueOf(i + currentYear);
        }

        ArrayAdapter<String> yearsArrayAdapter = new ArrayAdapter(this.getActivity(), R.layout.paytabs_spinner_adapter_row, this.years);
        yearsArrayAdapter.setDropDownViewResource(17367049);
        this.tEdYear.setAdapter(yearsArrayAdapter);
        btnPay.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                PaymentFragment.this.resetViews();
                if (!PaymentFragment.this.tEdName.getText().toString().isEmpty() && PaymentFragment.this.tEdName.getText().toString().trim().length() >= 3 && PaymentFragment.this.tEdName.getText().toString().trim().indexOf(32) != -1 && PaymentFragment.this.isAlpha(PaymentFragment.this.tEdName.getText().toString())) {
                    if (!PaymentFragment.this.tEdCardNumber.isCreditCardValid()) {
                        PaymentFragment.this.setErrorBackground(PaymentFragment.this.tEdCardNumberLinearlayout);
                        Snackbar.make(PaymentFragment.this.rootView, PaymentFragment.this.getResources().getString(R.string.paytabs_err_card_no), -1).show();
                    } else if (!PaymentFragment.this.isValidExpirationDate()) {
                        PaymentFragment.this.setErrorBackground(PaymentFragment.this.tEdMonth);
                        PaymentFragment.this.setErrorBackground(PaymentFragment.this.tEdYear);
                        Snackbar.make(PaymentFragment.this.rootView, PaymentFragment.this.getResources().getString(R.string.paytabs_err_card_exp), -1).show();
                    } else if (!PaymentFragment.this.tEdCvv.getText().toString().isEmpty() && PaymentFragment.this.tEdCvv.getText().length() >= 3 && PaymentFragment.this.tEdCvv.getText().length() <= 4) {
                        PaymentFragment.this.mListener.onPayClicked(PaymentFragment.this.tEdName.getText().toString(), PaymentFragment.this.tEdCardNumber.getCreditCard().getCardNumber(), PaymentFragment.this.tEdMonth.getSelectedItem().toString(), PaymentFragment.this.tEdYear.getSelectedItem().toString(), PaymentFragment.this.tEdCvv.getText().toString());
                    } else {
                        PaymentFragment.this.setErrorBackground(PaymentFragment.this.tEdCvv);
                        Snackbar.make(PaymentFragment.this.rootView, PaymentFragment.this.getResources().getString(R.string.paytabs_err_card_cvv), -1).show();
                    }
                } else {
                    PaymentFragment.this.setErrorBackground(PaymentFragment.this.tEdName);
                    Snackbar.make(PaymentFragment.this.rootView, PaymentFragment.this.getResources().getString(R.string.paytabs_err_card_name), -1).show();
                }
            }
        });
        this.tEdName.setOnFocusChangeListener(this);
        this.tEdCardNumber.setOnFocusChangeListener(this);
        this.tEdMonth.setOnFocusChangeListener(this);
        this.tEdYear.setOnFocusChangeListener(this);
        this.tEdCvv.setOnFocusChangeListener(this);
        ((EditText)this.rootView.findViewById(R.id.cc_card)).setOnEditorActionListener(new OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == 5) {
                    PaymentFragment.this.hideKeyboard(PaymentFragment.this.getContext());
                    PaymentFragment.this.rootView.findViewById(R.id.cc_card).clearFocus();
                    PaymentFragment.this.tEdMonth.requestFocus();
                    PaymentFragment.this.tEdMonth.performClick();
                    return true;
                } else {
                    return false;
                }
            }
        });
        this.tEdName.setLongClickable(false);
        this.tEdCardNumber.setLongClickable(false);
        this.rootView.findViewById(R.id.cc_card).setLongClickable(false);
        this.tEdCvv.setLongClickable(false);
        return this.rootView;
    }

    private void hideKeyboard(Context context) {
        InputMethodManager inputManager = (InputMethodManager)context.getSystemService("input_method");
        inputManager.hideSoftInputFromWindow(this.getView().getWindowToken(), 2);
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof PaymentFragment.OnFragmentInteractionListener) {
            this.mListener = (PaymentFragment.OnFragmentInteractionListener)context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    public void onDetach() {
        super.onDetach();
        this.mListener = null;
    }

    private void resetViews() {
        this.tEdName.getBackground().setColorFilter((ColorFilter)null);
        this.tEdMonth.getBackground().setColorFilter((ColorFilter)null);
        this.tEdYear.getBackground().setColorFilter((ColorFilter)null);
        this.tEdCvv.getBackground().setColorFilter((ColorFilter)null);
        this.tEdCardNumberLinearlayout.getBackground().setColorFilter((ColorFilter)null);
    }

    public void onFocusChange(View v, boolean hasFocus) {
        this.resetViews();
        if (hasFocus) {
            if (v.getId() != R.id.cc_card) {
                v.getBackground().setColorFilter(Color.parseColor(this.buttonColor.replace("#", "#20")), Mode.SRC_OVER);
            } else {
                this.tEdCardNumberLinearlayout.getBackground().setColorFilter(Color.parseColor(this.buttonColor.replace("#", "#20")), Mode.SRC_OVER);
            }
        }

    }

    private void setErrorBackground(View v) {
        v.getBackground().setColorFilter(Color.parseColor("#20c00000"), Mode.SRC_OVER);
    }

    private boolean isValidExpirationDate() {
        try {
            String year = this.tEdYear.getSelectedItem().toString();
            String month = this.tEdMonth.getSelectedItem().toString();
            int currentYear = Integer.parseInt(Integer.toString(Calendar.getInstance().get(1)).substring(2, 4));
            int currentMonth = Integer.parseInt(Integer.toString(Calendar.getInstance().get(2))) + 1;
            if (this.tEdYear.getSelectedItemPosition() != 0 && this.tEdMonth.getSelectedItemPosition() != 0) {
                return Integer.parseInt(year) >= currentYear && (Integer.parseInt(year) != currentYear || Integer.parseInt(month) > currentMonth);
            } else {
                return false;
            }
        } catch (NumberFormatException var5) {
            return false;
        }
    }

    private boolean isAlpha(String name) {
        return name.matches("[a-zA-Z ]+");
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 7990) {
            if (resultCode == -1) {
                Card card = (Card)data.getParcelableExtra("RESULT_PAYCARDS_CARD");
                this.tEdName.setText(card.getCardHolderName());
                this.tEdCardNumber.setCardNumber(card.getCardNumber(), false);
                if (card.getExpirationDate() != null) {
                    int i;
                    for(i = 0; i < this.months.length; ++i) {
                        if (card.getExpirationDate().split("/")[0].equals(this.months[i])) {
                            this.tEdMonth.setSelection(i);
                            break;
                        }
                    }

                    for(i = 0; i < this.years.length; ++i) {
                        if (card.getExpirationDate().split("/")[1].equals(this.years[i])) {
                            this.tEdYear.setSelection(i);
                            break;
                        }
                    }
                }
            } else if (resultCode == 0) {
                Log.i("ContentValues", "Scan canceled");
            } else {
                Log.i("ContentValues", "Scan failed");
            }
        }

    }

    public interface OnFragmentInteractionListener {
        void onPayClicked(String var1, String var2, String var3, String var4, String var5);
    }
}
