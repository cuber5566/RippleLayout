package com.cuber.ripplelayoutdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;

import com.cuber.ripplelayout.RippleLinearLayout;
import com.cuber.ripplelayout.RippleRelativeLayout;

public class MainActivity extends AppCompatActivity {

    private RippleRelativeLayout rippleRelativeLayout;
    private RippleLinearLayout rippleLinearLayout1, rippleLinearLayout2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        final int radius = (int) Math.sqrt(metrics.widthPixels * metrics.widthPixels + metrics.heightPixels * metrics.heightPixels);

        rippleRelativeLayout = (RippleRelativeLayout) findViewById(R.id.rippleRelativeLayout);
        rippleRelativeLayout.startRippleAnimation(0, 0, 0, radius, true);

        (findViewById(R.id.Button1)).setOnClickListener(new View.OnClickListener() {

            boolean expand = false;

            @Override
            public void onClick(View v) {

                expand = !expand;
                rippleLinearLayout1.startRippleAnimation(0, 0, 0, radius, expand);
            }
        });

        rippleLinearLayout1 = (RippleLinearLayout) findViewById(R.id.rippleLinearLayout1);
        (findViewById(R.id.Button2)).setOnClickListener(new View.OnClickListener() {

            boolean expand = false;

            @Override
            public void onClick(View v) {

                expand = !expand;
                rippleLinearLayout2.startRippleAnimation(0, 0, 0, radius, expand);
            }
        });

        rippleLinearLayout2 = (RippleLinearLayout) findViewById(R.id.rippleLinearLayout2);
        (findViewById(R.id.Button3)).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                new MainDialogFragmentFragment()
                        .clickView(v)
                        .show(getSupportFragmentManager(), "MainDialogFragment");
            }
        });
    }
}
