package com.TM470.pwrapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Register extends AppCompatActivity {

private static final String TAG = "EmailPassword";
  //Create Variable of class (XML pallet components)

    EditText aUserName, aPassword, aRePassword, aEmailAddress, aAge;
    Button aRegisterBtn;
    TextView aLoginBtn;
    CheckBox aCheckBox;
    FirebaseAuth fireAuth;
    ProgressBar progressBar;
    FirebaseFirestore db;
    String fBaseUID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_register);

        //Set all entered fields to instances in the initialisation method
        aUserName = findViewById(R.id.userName);
        aPassword = findViewById(R.id.password);
        aRePassword = findViewById(R.id.rePassword);
        aEmailAddress = findViewById(R.id.emailAddress);
        aAge = findViewById(R.id.age);
        aRegisterBtn = findViewById(R.id.registerBtn);
        aLoginBtn = findViewById(R.id.backToLogin);
        aCheckBox = findViewById(R.id.checkBox);

        fireAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        progressBar = findViewById(R.id.progressBar);

        //Check if the user is already logged in, send them to the main activity page
            super.onStart();
            if(fireAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), homePage.class));
            finish();
        }


        /* Create click listener to 'Register' button to create a new user.
         * check for errors in the 'Registration Form' inputs and produce errors for the user
        */

        aRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = aUserName.getText().toString();
                String email = aEmailAddress.getText().toString().trim();
                String password = aPassword.getText().toString().trim();
                String rePassword = aRePassword.getText().toString().trim();
                String age = aAge.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    aEmailAddress.setError("An email address must be used to register.");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    aPassword.setError("A password is required to register.");
                    return;
                }

                if (password.length() < 8) {
                    aPassword.setError("Password must be more than 7 characters.");
                    return;
                }

                if (!Objects.equals(password, rePassword)) {
                    aRePassword.setError("Passwords must match.");
                    return;
                }

                if (!aCheckBox.isChecked()) {
                    Toast.makeText(Register.this, "Disclaimer must be agreed to register.", Toast.LENGTH_SHORT).show();
                return;

                }




                progressBar.setVisibility(View.VISIBLE);

                //Set the registered user into the FireBase database

                fireAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "registerWithEmail:success");
                                    Toast.makeText(Register.this, "Registration Complete!", Toast.LENGTH_SHORT).show();
                                    fBaseUID = fireAuth.getCurrentUser().getUid();
                                    DocumentReference documentReference = db.collection("account").document(fBaseUID);
                                    Map<String, Object> users = new HashMap<>();
                                    users.put("username", userName);
                                    users.put("email", email);
                                    users.put("password", password);
                                    users.put("DOB", age);
                                    documentReference.set(users).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Log.d(TAG, "" + fBaseUID + "user created.");
                                        }
                                    });
                                    startActivity(new Intent(getApplicationContext(), homePage.class));
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "registerWithEmail:failure", task.getException());
                                    Toast.makeText(Register.this, "Email already in use, please use a different email.",
                                            Toast.LENGTH_SHORT).show();
                                    updateUI(null);
                                }
                            }

                            private void updateUI(FirebaseUser user) {
                            }
                        });





            }
        });

        aLoginBtn.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), login.class)));

    }

    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Back out without registering?").setMessage("If you leave now the information provided for registration will be lost. Are you sure?")
                .setNegativeButton(R.string.no, null)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        startActivity(new Intent(getApplicationContext(), login.class));}
                }).create().show();
    }

}