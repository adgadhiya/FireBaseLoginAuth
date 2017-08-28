package com.example.unknown.firebaseloginauth;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private EditText etEmail,etPassword;
    private Button signIn,forgotPassword,signUpHere;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(FirebaseAuth.getInstance().getCurrentUser() != null){
            startActivity(new Intent(MainActivity.this,SignedInActivity.class));
            finish();
        }

        setContentView(R.layout.activity_main);

        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword=(EditText)findViewById(R.id.etPassword);

        signIn = (Button)findViewById(R.id.login);
        forgotPassword=(Button)findViewById(R.id.forgotPassword);
        signUpHere = (Button)findViewById(R.id.signupHere);

        auth = FirebaseAuth.getInstance();

        signIn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String email = etEmail.getText().toString();
                        String pass = etPassword.getText().toString();

                        auth.signInWithEmailAndPassword(email,pass)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {

                                        if(task.isSuccessful()){
                                            Toast.makeText(MainActivity.this,"Login Successfull",Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(MainActivity.this,SignedInActivity.class));
                                        } else {
                                            Toast.makeText(MainActivity.this,"Please Check Your Email or password",Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                    }
                                });
                    }
                }
        );

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = etEmail.getText().toString();

                auth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if(task.isSuccessful()){
                                    Toast.makeText(MainActivity.this,"Please check Your Email",Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(MainActivity.this,"Please Retry",Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });

        signUpHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,SignUpActivity.class));
            }
        });


    }
}
