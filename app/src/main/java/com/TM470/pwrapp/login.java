package com.TM470.pwrapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class login extends AppCompatActivity {

    private static final String TAG = "EmailPassword";

    EditText aEmail, aPassword;
    Button bLogin, bRegister;
    FirebaseAuth fireAuth;
    ProgressBar aProgressBar;
    FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);

        aEmail = findViewById(R.id.username);
        aPassword = findViewById(R.id.password);
        bLogin = findViewById(R.id.login);
        bRegister = findViewById(R.id.register);

        fireAuth = FirebaseAuth.getInstance();
        aProgressBar = findViewById(R.id.progressBar);

        //Check if the user is already logged in, send them to the main activity page
        if(fireAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), homePage.class));
            finish();
        }


        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = aEmail.getText().toString().trim();
                String password = aPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    aEmail.setError("Please enter an email address to login.");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    aPassword.setError("Please enter a password.");
                    return;
                }

                if (password.length() < 8) {
                    aPassword.setError("Password must be more than 7 characters.");
                    return;
                }



                aProgressBar.setVisibility(View.VISIBLE);

                //LOGIN AUTHENTICATION
                fireAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "signInWithEmail:success");
                                    Toast.makeText(login.this, "Login Complete!", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(), homePage.class));
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                                    Toast.makeText(login.this, "Login Failed. Please make sure the email and password are both correct.",
                                            Toast.LENGTH_SHORT).show();
                                    aProgressBar.setVisibility(View.INVISIBLE);
                                }
                            }
                        });
           }
        });

        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Register.class));
                finish();
            }
        });
    }
}