package com.dh.neuradyo;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class RadyoHakkinda extends AppCompatActivity {

    private AnimationDrawable animationDrawable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.radyo_hakkinda);



        LinearLayout linearLayout=(LinearLayout)findViewById(R.id.linear_layout);


        animationDrawable =(AnimationDrawable)linearLayout.getBackground();
        animationDrawable.setEnterFadeDuration(500);
        animationDrawable.setExitFadeDuration(2000);



    }



    @Override
    protected void onResume() {
        super.onResume();

        animationDrawable.start();



    }


}