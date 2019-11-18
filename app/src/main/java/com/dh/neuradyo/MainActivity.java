package com.dh.neuradyo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.dh.neuradyo.player.PlaybackStatus;
import com.dh.neuradyo.player.RadioManager;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.onesignal.OneSignal;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    final String tag = "MainActivity İhram FM";
    final String streamURL = "http://159.253.37.137:9862/";

    final int[] buton_0 = {R.drawable.bottom1_0, R.drawable.bottom2_0, R.drawable.bottom3_0, R.drawable.bottom4_0, R.drawable.bottom5_0, R.drawable.bottom6_0};
    final int[] buton_1 = {R.drawable.bottom1_1, R.drawable.bottom2_1, R.drawable.bottom3_1, R.drawable.bottom4_1, R.drawable.bottom5_1, R.drawable.bottom6_1};

    List<ImageButton> butonas = new ArrayList<>();

    TextView calanParca;
    ImageButton btn;

    RadioManager radioManager;
    FragmentManager manager;

    boolean doubleBackToExitPressedOnce = false;
    boolean fragmentAcik = false;



    private AnimationDrawable animationDrawable;










    Button mButtonControlStart;
    TextView mTextViewControl;
    RadioManager mRadioManager;





    int basla_bitir=0;

    private Context mContext;
    private Activity mActivity;

    final Context context = this;



    private Button startBtn,chat;
    private MediaPlayer player;
    public static boolean isAlreadyPlaying = false;
    private AudioManager audioManager;




    private static final String AD_UNIT_ID = "ca-app-pub-6192285615218693/5397792990";
    //APP ID admobtan oluşturduğunuz ID değeridir. Uygulamanın ID değeri
    private static final String APP_ID = "ca-app-pub-6192285615218693~4659426390";


    private FirebaseDatabase db;
    private DatabaseReference dbRef2,dbRef3;



    private RewardedVideoAd mRewardedVideoAd;

    private String reklam_linki,çalınan_sarki;



    private Button stopBtn;

    private TextView ac_kapa_text,istek_text,textviewControl;


    FirebaseStorage storage;
    StorageReference storageReference;

    ImageView REKLAM_İMAGE,logo;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();


        Button menu_button=(Button)findViewById(R.id.menu_button);

        menu_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, Menu.class);
                startActivity(intent);


            }
        });


        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference photoReference= storageReference.child("reklam_örnek.png");

        REKLAM_İMAGE=(ImageView)findViewById(R.id.reklam_image);
        logo=(ImageView)findViewById(R.id.logo);

        istek_text=(TextView)findViewById(R.id.istek_text);




        final long ONE_MEGABYTE = 1024 * 1024;
        photoReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                REKLAM_İMAGE.setImageBitmap(bmp);
                logo.setVisibility(View.INVISIBLE);
                REKLAM_İMAGE.setVisibility(View.VISIBLE);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
            }
        });



        db = FirebaseDatabase.getInstance();



        dbRef3 = db.getReference("Çalınan Parça");



        dbRef3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                çalınan_sarki= (String) dataSnapshot.getValue();
                istek_text.setText(çalınan_sarki);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        dbRef2 = db.getReference("reklam_linki");



        dbRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                reklam_linki= (String) dataSnapshot.getValue();
                //Log.d("VALUE",ds.getValue(Message_3.class).getMesajText());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        REKLAM_İMAGE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (reklam_linki!=null){
                    Uri uri = Uri.parse(reklam_linki); // missing 'http://' will cause crashed
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }


            }
        });

        chat=(Button)findViewById(R.id.chat);

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



        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                showMyCustomAlertDialog1();

            }
        });


        Button chat=(Button)findViewById(R.id.chat);



























        LinearLayout linearLayout=(LinearLayout)findViewById(R.id.linear_layout);


        animationDrawable =(AnimationDrawable)linearLayout.getBackground();
        animationDrawable.setEnterFadeDuration(500);
        animationDrawable.setExitFadeDuration(2000);

        radioManager = RadioManager.with(this);
        manager = getSupportFragmentManager();

        btn = findViewById(R.id.playPauseButton);

        final AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        assert audioManager != null;
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int curVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        SeekBar volControl = findViewById(R.id.volBar);
        volControl.setMax(maxVolume);
        volControl.setProgress(curVolume);
        volControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar arg0) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar arg0) {
            }

            @Override
            public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, arg1, 0);
            }
        });

        setCalanParca();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                setCalanParca();
                handler.postDelayed(this, 5000);
                Log.d(tag, "İşlem tekrarı");
            }
        }, 5000);



    }

    private void setCalanParca() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://37.187.196.74:8026/currentsong?sid=1";


    }



    private void uygulamayiPaylas() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT,
                "İhram Fm Mobil Uygulamasını İndirdiniz mi?" +
                        "\n Android: https://play.google.com/store/apps/details?id=com.dh.ihramfm" +
                        "\n iOS: https://apps.apple.com/us/app/i-hram-fm/id1323771495");
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }

   /* public void replaceByBlankFragment(String url, int i) {
        if (fragmentAcik) {
            removeFragments();
            fragmentAcik = false;
        } else {
            BlankFragment blankFragment = new BlankFragment();
            blankFragment.sendData(url);
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.container, blankFragment, "fragBlank");
            transaction.commit();
            fragmentAcik = true;
            if (i < 5) butonas.get(i).setImageResource(buton_1[i]);
        }

    }  */

    public void removeFragments() {
        BlankFragment blankFragment = (BlankFragment) manager.findFragmentByTag("fragBlank");
        FragmentTransaction transaction = manager.beginTransaction();
        if (blankFragment != null) {
            transaction.remove(blankFragment);
            transaction.commit();
        }

        for (int i = 0; i < buton_0.length; i++) {
            butonas.get(i).setImageResource(buton_0[i]);
        }
    }


    @Override
    public void onStart() {

        super.onStart();

        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {

        EventBus.getDefault().unregister(this);

        super.onStop();
    }

    @Override
    protected void onDestroy() {

        radioManager.unbind();

        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();

        radioManager.bind();
        animationDrawable.start();

    }

   /* @Override
    public void onBackPressed() {

        BlankFragment blankFragment = (BlankFragment) manager.findFragmentByTag("fragBlank");
        if (blankFragment != null) {

            if (blankFragment.canGoBack()) {
                blankFragment.goBack();
            } else {
                removeFragments();
            }

        } else {
            if (doubleBackToExitPressedOnce) {
                int pid = android.os.Process.myPid();
                android.os.Process.killProcess(pid);
                super.onBackPressed();

            }
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Kapatmak için tekrar Geri tuşuna basın", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(() -> doubleBackToExitPressedOnce = false, 2000);
        }
    } */

    @Subscribe
    public void onEvent(String status) {

        switch (status) {

            case PlaybackStatus.LOADING:

                Toast.makeText(this, "Sunucuya Bağlanıyor", Toast.LENGTH_SHORT).show();

                break;

            case PlaybackStatus.PLAYING:

                btn.setImageResource(R.drawable.power_off_button);

                break;

            case PlaybackStatus.PAUSED:

            case PlaybackStatus.STOPPED:

                btn.setImageResource(R.drawable.power_button);

                break;

            case PlaybackStatus.ERROR:

                Toast.makeText(this, R.string.no_stream, Toast.LENGTH_SHORT).show();

                break;

        }

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.playPauseButton:
                radioManager.playOrPause(streamURL);
                break;


        }


    }



    @Override
    public void onBackPressed() {
        //Checking for fragment count on backstack
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else if (!doubleBackToExitPressedOnce) {
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this,"Çıkmak için bir kere daha basın...", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        } else {
            super.onBackPressed();
            return;
        }
    }

    public void showMyCustomAlertDialog() {

        // dialog nesnesi oluştur ve layout dosyasına bağlan
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.alert_dialog);

        // custom dialog elemanlarını tanımla - text, image ve button
        Button btnKaydet = (Button) dialog.findViewById(R.id.button_kaydet);
        ImageView ivResim = (ImageView) dialog.findViewById(R.id.imageview_resim);

        ivResim.setImageResource(R.drawable.checked);


        TextView text_view = (TextView) dialog.findViewById(R.id.text_view);

        text_view.setText("Şimdi sohbete katılabilirsin!");
        btnKaydet.setText("Sohbete katıl");
        ivResim.setImageResource(R.drawable.checked);





        // tamam butonunun tıklanma olayları
        btnKaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ChatActivity_3.class);
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


        if (mRewardedVideoAd.isLoaded()) {
            text_view.setText("Sohbete katılabilmen için kısa bir reklam izlemen gerekyor!");
            btnKaydet.setText("İzle");
        }
        else {


            text_view.setText("Reklam şu anda yüklenemiyor, sohbete başlayabilirsin!");
            btnKaydet.setText("Sohbete katıl");


        }

        ivResim.setImageResource(R.drawable.hello);

        // tamam butonunun tıklanma olayları
        btnKaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mRewardedVideoAd.isLoaded()) {
                    mRewardedVideoAd.show();
                }
                else {

                    Intent intent = new Intent(getApplicationContext(), ChatActivity_3.class);
                    startActivity(intent);
                    dialog.dismiss();

                }

                dialog.dismiss();
            }
        });


        dialog.show();

    }





}
