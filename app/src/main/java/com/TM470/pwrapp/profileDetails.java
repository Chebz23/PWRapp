package com.TM470.pwrapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class profileDetails extends AppCompatActivity {

    TextView aUsername, aPassword, aEmail;
    Button aEditUser, aEditPass, aBack;
    FirebaseAuth fireAuth;
    FirebaseFirestore db;
    String fBaseUID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_profile_details);

        aBack = findViewById(R.id.backBtn);
        aEditUser = findViewById(R.id.editUserBtn);
        aEditPass = findViewById(R.id.editPassBtn);

        aUsername = findViewById(R.id.getUsername);
        aPassword = findViewById(R.id.getPassword);
        aEmail = findViewById(R.id.getEmail);

        fireAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        fBaseUID = fireAuth.getCurrentUser().getUid();



        DocumentReference dr = db.collection("account").document(fBaseUID);
        dr.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                aUsername.setText(documentSnapshot.getString("username"));
                aEmail.setText(documentSnapshot.getString("email"));
            }
        });





               aBack.setOnClickListener(new View.OnClickListener() {
                 @Override
               public void onClick(View view) {
                     startActivity(new Intent(getApplicationContext(), homePage.class));
                     finish();
                 }});


              aEditUser.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                      startActivity(new Intent(getApplicationContext(), editUsername.class));
                      finish();
                  }
              });

             aEditPass.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                    startActivity(new Intent(getApplicationContext(), editPassword.class));
                    finish();
            }
        });


         }
}





