package com.dh.neuradyo;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;


public class IstekParca extends AppCompatActivity {

    private AnimationDrawable animationDrawable;
    Context context=this;



    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    String username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.istek_parca);



        Bundle extras = getIntent().getExtras();
         username = extras.getString("username_key");


        LinearLayout linearLayout=(LinearLayout)findViewById(R.id.linear_layout);


        animationDrawable =(AnimationDrawable)linearLayout.getBackground();
        animationDrawable.setEnterFadeDuration(500);
        animationDrawable.setExitFadeDuration(2000);


        Button gonder_button=(Button)findViewById(R.id.gonder_button);

        final EditText istek_text=(EditText)findViewById(R.id.istek_text);



        gonder_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                UUID uuid = UUID.randomUUID();
                String uuidString = uuid.toString();


                if (istek_text!=null){

                    myRef.child("İstek parçalar").child(uuidString).setValue(username+" - "+istek_text.getText().toString());


                }
                else {
                    Toast.makeText(getApplicationContext(), "Lütfen istek parçanı gir!", Toast.LENGTH_LONG).show();

                }

                showMyCustomAlertDialog1();


            }
        });


    }



    public void showMyCustomAlertDialog1() {

        // dialog nesnesi oluştur ve layout dosyasına bağlan
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.alert_dialog);

        // custom dialog elemanlarını tanımla - text, image ve button
        Button btnKaydet = (Button) dialog.findViewById(R.id.button_kaydet);
        ImageView ivResim = (ImageView) dialog.findViewById(R.id.imageview_resim);

        TextView text_view = (TextView) dialog.findViewById(R.id.text_view);

        text_view.setText("Şarkı isteğini aldık, istek parça çalındığında ana sayfada gözükecek!");
        btnKaydet.setText("Tamam");



        ivResim.setImageResource(R.drawable.checked);

        // tamam butonunun tıklanma olayları
        btnKaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });


        dialog.show();

    }


    @Override
    protected void onResume() {
        super.onResume();

        animationDrawable.start();



    }


}