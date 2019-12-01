package com.paytab_dk.paytabs_sdk.payment.ui.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.paytab_dk.R;
import com.paytab_dk.paytabs_sdk.payment.process.DialogTitleListener;

public class ProgressFragment extends DialogFragment implements DialogTitleListener {
    private static final String TITLE = "title";
    private String title;
    private TextView tvTitle;

    public ProgressFragment() {
    }

    public static ProgressFragment newInstance(String param1) {
        Bundle args = new Bundle();
        args.putString("title", param1);
        ProgressFragment fragment = new ProgressFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.title = this.getArguments().getString("title");
    }

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.getDialog().getWindow().requestFeature(1);
        View rootView = inflater.inflate(R.layout.paytabs_progress_dialog, (ViewGroup)null);
        this.tvTitle = (TextView)rootView.findViewById(R.id.title);
        this.setTitle(this.title);
        return rootView;
    }

    public void setTitle(String title) {
        this.tvTitle.setText(title);
    }

    public interface ProgressListener {
        void hideProgressBar();

        void showProgressBar(String var1);

        boolean isShowing();
    }
}
