package com.example.unknown.firebaseloginauth;

import android.content.Intent;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.nio.LongBuffer;

public class SignedInActivity extends AppCompatActivity {

    private EditText etChangeEmail,etChangePassword;
    private Button changeEmail,changePassword,signout;
    private FirebaseAuth auth;
    private FirebaseUser user;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signed_in);

        etChangeEmail = (EditText)findViewById(R.id.etChangeEmail);
        etChangePassword = (EditText)findViewById(R.id.etChangePassword);
        signout = (Button)findViewById(R.id.signout);
        changeEmail = (Button)findViewById(R.id.btnChangeEmail);
        changePassword = (Button)findViewById(R.id.btnChangePassword);
    }

    @Override
    protected void onStart() {
        super.onStart();

        auth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

        etChangePassword.setVisibility(View.GONE);
        etChangeEmail.setVisibility(View.GONE);

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
                startActivity(new Intent(SignedInActivity.this,MainActivity.class));
            }
        });

        changeEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count == 0){
                 etChangeEmail.setVisibility(View.VISIBLE);
                    count ++;
                } else {
                    count = 0;
                    String email = etChangeEmail.getText().toString();

                    user.updateEmail(email)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(SignedInActivity.this,"Email Updated SuccessFully",Toast.LENGTH_SHORT).show();
                                        signOut();
                                    } else {
                                        Toast.makeText(SignedInActivity.this,"Some Error Occured,Please try again",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count == 0){
                    etChangePassword.setVisibility(View.VISIBLE);
                } else {
                    count = 0;
                    String pass = etChangePassword.getText().toString();

                    user.updatePassword(pass)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(SignedInActivity.this,"Password update successfully",Toast.LENGTH_LONG).show();
                                        signOut();
                                    } else {
                                        Toast.makeText(SignedInActivity.this,"Some Error Occured, Please Try Again",Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
            }
        });
    }

    private void signOut(){
        auth.signOut();
        startActivity(new Intent(SignedInActivity.this,MainActivity.class));
    }
}
