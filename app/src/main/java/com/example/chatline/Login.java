package com.example.chatline;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class Login extends AppCompatActivity {
    public  TextView newUser,loginTxt;
    public  String ename,password;
    public EditText enameEd,passwordEd;
    public FirebaseAuth firebaseAuth;
    public FirebaseUser firebaseUser;
    public FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        enameEd=findViewById(R.id.logEname);
        passwordEd=findViewById(R.id.logPassword);
        newUser=findViewById(R.id.newUser);
        newUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth=FirebaseAuth.getInstance();
                firebaseAuth.signOut();
                startActivity(new Intent(Login.this,Register.class));
            }
        });
        loginTxt=findViewById(R.id.login);
        loginTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });
    }

    private void loginUser() {
        ename=enameEd.getText().toString();
        password=passwordEd.getText().toString();
        if (TextUtils.isEmpty(ename) || TextUtils.isEmpty(password)){
            Toast.makeText(getApplicationContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
        }
        else {
            firebaseAuth=FirebaseAuth.getInstance();
            firebaseAuth.signInWithEmailAndPassword(ename,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        finish();
                        startActivity(new Intent(Login.this,MainActivity.class));
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Login failed check your connection", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseAuth mauth= FirebaseAuth.getInstance();
        FirebaseUser myUser=mauth.getCurrentUser();
        if (myUser!=null){
            startActivity(new Intent(Login.this,MainActivity.class));
            finish();

        }
        else {
            Toast.makeText(Login.this, "Login here", Toast.LENGTH_SHORT).show();
        }
    }
}