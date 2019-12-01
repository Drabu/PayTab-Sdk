package com.paytab_dk.paytabs_sdk.payment.ui.fragment;


import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.paytab_dk.R;

public class ErrorFragment extends DialogFragment {
    private static final String MESSAGE = "message";
    private String message;

    public ErrorFragment() {
    }

    public static ErrorFragment newInstance(String param1) {
        Bundle args = new Bundle();
        args.putString("message", param1);
        ErrorFragment fragment = new ErrorFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (this.getArguments() != null) {
            this.message = this.getArguments().getString("message");
        }

    }

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Builder builder = new Builder(this.getActivity());
        builder.setTitle(this.getResources().getString(R.string.paytabs_error)).setMessage(this.message).setCancelable(false).setNegativeButton(this.getResources().getString(R.string.paytabs_ok), new OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                ErrorFragment.this.getActivity().setResult(0);
                ErrorFragment.this.getActivity().finish();
            }
        });
        return builder.create();
    }

    public void onStart() {
        super.onStart();
        Window window = this.getDialog().getWindow();
        LayoutParams windowParams = window.getAttributes();
        windowParams.dimAmount = 1.0F;
        window.setAttributes(windowParams);
    }
}
