package com.example.unknown.firebaseloginauth;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {

    private Button btnSignUp;
    private EditText emailSignUp,passwordSignUp;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        btnSignUp = (Button) findViewById(R.id.signup);

        emailSignUp = (EditText) findViewById(R.id.etSignUpEmail);
        passwordSignUp = (EditText) findViewById(R.id.etSignUpPassword);

        auth = FirebaseAuth.getInstance();

        btnSignUp.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String email = emailSignUp.getText().toString();
                        String pass = passwordSignUp.getText().toString();

                        auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if(task.isSuccessful()){
                                    Toast.makeText(SignUpActivity.this,"User Created",Toast.LENGTH_LONG).show();
                                    finish();
                                } else {
                                    Toast.makeText(SignUpActivity.this,"Some Problem Occred Please try again",Toast.LENGTH_LONG).show();
                                }
                            }
                        });

                    }
                }
        );
    }
}
