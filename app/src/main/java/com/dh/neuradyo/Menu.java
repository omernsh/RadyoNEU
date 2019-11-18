package com.dh.neuradyo;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Menu extends AppCompatActivity {

    private AnimationDrawable animationDrawable;
    Context context=this;



    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    String username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);



        LinearLayout linearLayout=(LinearLayout)findViewById(R.id.linear_layout);


        animationDrawable =(AnimationDrawable)linearLayout.getBackground();
        animationDrawable.setEnterFadeDuration(500);
        animationDrawable.setExitFadeDuration(2000);


        Button radyomuz_hakkında=(Button)findViewById(R.id.radyomuz_hakkında);
        Button oy_ver=(Button)findViewById(R.id.oy_ver);
        Button paylaş=(Button)findViewById(R.id.paylaş);
        Button bize_ulaş=(Button)findViewById(R.id.bize_ulaş);
        Button çıkış_yap=(Button)findViewById(R.id.çıkış_yap);


        radyomuz_hakkında.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Menu.this, RadyoHakkinda.class);
                startActivity(intent);
            }
        });


        oy_ver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.dh.neuradyo"));
                startActivity(intent);





            }
        });


        paylaş.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "Radyo NUE'YU İNDİR \"+\"market://details?id=com.dh.neuradyon";
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Radyo NUE'YU İNDİR "+"market://details?id=com.dh.neuradyo");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share"));






            }
        });

        bize_ulaş.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"bakisenal50@gmail.com"});

                try {
                    startActivity(Intent.createChooser(i, "Mail gönder"));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(Menu.this, "Hata...", Toast.LENGTH_SHORT).show();
                }






            }
        });

        çıkış_yap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(Menu.this, GirisYap.class);
                startActivity(intent);







            }
        });


    }



    @Override
    protected void onResume() {
        super.onResume();

        animationDrawable.start();



    }


}