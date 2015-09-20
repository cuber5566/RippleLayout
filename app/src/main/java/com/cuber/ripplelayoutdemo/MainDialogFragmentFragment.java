package com.cuber.ripplelayoutdemo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cuber.ripplelayout.RippleLinearLayout;

/**
 * Created by cuber on 2015/9/19.
 */
public class MainDialogFragmentFragment extends BaseDialogFragment {

    private RippleLinearLayout rippleView;
    private View clickView;

    public MainDialogFragmentFragment clickView(View view){
        this.clickView = view;
        return this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_main, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rippleView = (RippleLinearLayout)view.findViewById(R.id.dialog_rippleLayout);
        rippleView.startRippleAnimation(clickView, true);
    }

    @Override
    protected boolean onBackPressed() {
        rippleView.startRippleAnimation(clickView, false);
        rippleView.postDelayed(new Runnable() {
            @Override
            public void run() {
                dismiss();
            }
        }, rippleView.duration);
        return true;
    }
}
