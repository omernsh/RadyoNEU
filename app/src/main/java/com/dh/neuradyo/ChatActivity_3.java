package com.dh.neuradyo;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ChatActivity_3 extends AppCompatActivity {

    private FirebaseDatabase db;
    private DatabaseReference dbRef2;
    private DatabaseReference dbRef;
    private FirebaseUser fUser;
    private String fUserID;
    private ArrayList<Message_3> chatLists = new ArrayList<>();
    private CustomAdapter_3 customAdapter;
    private String subject,username;
    private ListView listView;
    private EditText inputChat;
    private Button gonder_button,istek_sarki;






    private static final String AD_UNIT_ID = "ca-app-pub-6192285615218693/5397792990";
    //APP ID admobtan oluşturduğunuz ID değeridir. Uygulamanın ID değeri
    private static final String APP_ID = "ca-app-pub-6192285615218693~4659426390";


    Context context=this;

    private RewardedVideoAd mRewardedVideoAd;



    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_3);



        inputChat = (EditText)findViewById(R.id.inputChat);










        Button istek_sarki=(Button)findViewById(R.id.istek_sarki);

        gonder_button=(Button)findViewById(R.id.chat_activity_send_button);



        MobileAds.initialize(this,getString(R.string.admob_app_id));

        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);

        mRewardedVideoAd.setRewardedVideoAdListener(new RewardedVideoAdListener() {
            @Override
            public void onRewarded(RewardItem rewardItem) {
            }

            @Override
            public void onRewardedVideoAdLoaded() {
            }

            @Override
            public void onRewardedVideoAdOpened() {
            }

            @Override
            public void onRewardedVideoStarted() {
            }

            @Override
            public void onRewardedVideoAdClosed() {
                mRewardedVideoAd.loadAd(getString(R.string.ad_unit_id), new AdRequest.Builder().build());

            }

            @Override
            public void onRewardedVideoAdLeftApplication() {
            }

            @Override
            public void onRewardedVideoAdFailedToLoad(int i) {
            }

            @Override
            public void onRewardedVideoCompleted() {


                showMyCustomAlertDialog();



            }
        });

        mRewardedVideoAd.loadAd(getString(R.string.ad_unit_id), new AdRequest.Builder().build());

        istek_sarki = (Button) findViewById(R.id.istek_sarki);
        istek_sarki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                showMyCustomAlertDialog1();

            }
        });


        listView = (ListView)findViewById(R.id.chatListView);

        db = FirebaseDatabase.getInstance();
        fUser = FirebaseAuth.getInstance().getCurrentUser();
        fUserID= FirebaseAuth.getInstance().getUid();

        dbRef2 = db.getReference("Kullanıcılar/"+fUserID+"/Kullanıcı adı");



        dbRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                     username= (String) dataSnapshot.getValue();
                    //Log.d("VALUE",ds.getValue(Message_3.class).getMesajText());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





        customAdapter = new CustomAdapter_3(getApplicationContext(),chatLists,fUser);
        listView.setAdapter(customAdapter);




            subject="Sohbet";
            dbRef = db.getReference("ChatSubjects/"+"chat"+"/mesaj");
            setTitle(subject);



        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                chatLists.clear();
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    Message_3 message3 = ds.getValue(Message_3.class);
                    chatLists.add(message3);
                    //Log.d("VALUE",ds.getValue(Message_3.class).getMesajText());
                }
                customAdapter.notifyDataSetChanged();
                scrollMyListViewToBottom();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        gonder_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(inputChat.getText().length()>=1){

                    long msTime = System.currentTimeMillis();
                    Date curDateTime = new Date(msTime);
                    SimpleDateFormat formatter = new SimpleDateFormat("dd'/'MM'/'y hh:mm");
                    String dateTime = formatter.format(curDateTime);
                    Message_3 message3 = new Message_3(inputChat.getText().toString(),fUser.getEmail(),dateTime,username);
                    dbRef.push().setValue(message3);
                    inputChat.setText("");

                    scrollMyListViewToBottom();

                }else{

                }


            }
        });






    }


    public void showMyCustomAlertDialog() {

        // dialog nesnesi oluştur ve layout dosyasına bağlan
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.alert_dialog);

        // custom dialog elemanlarını tanımla - text, image ve button
        Button btnKaydet = (Button) dialog.findViewById(R.id.button_kaydet);
        ImageView ivResim = (ImageView) dialog.findViewById(R.id.imageview_resim);

        ivResim.setImageResource(R.drawable.checked);

        // tamam butonunun tıklanma olayları
       btnKaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), IstekParca.class);
                intent.putExtra("username_key",username);
                startActivity(intent);
                dialog.dismiss();
            }
        });


        dialog.show();

    }


    public void showMyCustomAlertDialog1() {

        // dialog nesnesi oluştur ve layout dosyasına bağlan
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.alert_dialog);

        // custom dialog elemanlarını tanımla - text, image ve button
        Button btnKaydet = (Button) dialog.findViewById(R.id.button_kaydet);
        ImageView ivResim = (ImageView) dialog.findViewById(R.id.imageview_resim);

        TextView text_view = (TextView) dialog.findViewById(R.id.text_view);




        text_view.setText("Şarkı isteğinde bulunabilmek kısa bir reklam izlemen gerekyor!");
        btnKaydet.setText("İzle");


        ivResim.setImageResource(R.drawable.hello);

        // tamam butonunun tıklanma olayları
        btnKaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mRewardedVideoAd.isLoaded()) {
                    mRewardedVideoAd.show();
                }
                else {


                    Toast.makeText(getApplicationContext(),"Reklam şu anda yüklenemedi, istek parçanı girebilirsn!", Toast.LENGTH_SHORT).show();



                    Intent intent = new Intent(getApplicationContext(), IstekParca.class);
                    intent.putExtra("username_key",username);
                    startActivity(intent);
                    dialog.dismiss();


                }

                dialog.dismiss();
            }
        });


        dialog.show();

    }


    private void scrollMyListViewToBottom() {
        listView.post(new Runnable() {
            @Override
            public void run() {
                // Select the last row so it will scroll into view...
                listView.setSelection(listView.getCount() - 1);
            }
        });
    }







}
