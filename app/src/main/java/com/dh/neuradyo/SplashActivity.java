package com.dh.neuradyo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.widget.Toast;

public class SplashActivity extends AppCompatActivity {

    public boolean isNetworkAvailable(final Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if (!isNetworkAvailable(this)) {
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Bağlantı Hatası");
            alertDialog.setMessage("Lütfen internet bağlantınızı kontrol ettikten sonra tekrar deneyin");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            int pid = android.os.Process.myPid();
                            android.os.Process.killProcess(pid);
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();

        } else {
            Thread mSplashThread;//thread classdan obje olustrduk uygulamann 4 saniye uyutulmasi icin
            mSplashThread = new Thread() {
                @Override
                public void run() {
                    try {
                        synchronized (this) {
                            wait(1000);
                        }
                    } catch (InterruptedException ex) {
                        Toast.makeText(SplashActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                    } finally {
                        startActivity(new Intent(getApplicationContext(), GirisYap.class));
                        finish();
                    }
                }
            };//thread objesini olustrduk ve istedmz sekilde sekillendrdik
            mSplashThread.start();// thread objesini calistriyoruz
        }

    }
}
