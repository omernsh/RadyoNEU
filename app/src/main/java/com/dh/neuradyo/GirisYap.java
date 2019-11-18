package com.dh.neuradyo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class GirisYap extends AppCompatActivity {

    EditText emailText, passwordText,username_edittext;

    private FirebaseAuth mAuth;



    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    private FirebaseDatabase db;
    private DatabaseReference dbRef;
    private FirebaseUser fUser;
    private String subject;
    private ListView listView;
    private EditText inputChat;
    private Button gonderButton, resimGonderButton;

    private static final int PICK_IMAGE_REQUEST = 1;

    String user_name;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.giris_yap);

        emailText = (EditText) findViewById(R.id.user_email_edit_text);
        passwordText = (EditText) findViewById(R.id.user_password_edit_text);





        mAuth = FirebaseAuth.getInstance();


        db = FirebaseDatabase.getInstance();
        fUser = FirebaseAuth.getInstance().getCurrentUser();




        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null) {

            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }


    }


    public void signUp(View view) {

                            Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                            startActivity(intent);


    }

    public void signIn(View view) {



        ProgressDialog progressDialog = new ProgressDialog(GirisYap.this);
        progressDialog.setMessage("Lütfen bekleyiniz..");
        progressDialog.show();

        mAuth.signInWithEmailAndPassword(emailText.getText().toString(),passwordText.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            finish();

                        } else {
                            Toast.makeText(GirisYap.this,"Kullanıcı bulunamadı, üye oldunuz mu?",Toast.LENGTH_LONG).show();

                        }

                    }
                });



    }


}