package com.TM470.pwrapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class editUsername extends AppCompatActivity {


    EditText aUserName;
    Button aConfirm, aBack;
    ProgressBar progressBar;
    FirebaseAuth fireAuth;
    FirebaseFirestore db;
    String fBaseUID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_edit_username);

        aBack = findViewById(R.id.backBtn);
        aConfirm = findViewById(R.id.confirmBtn);

        aUserName = findViewById(R.id.userNameEdit);
        progressBar = findViewById(R.id.progressBar);

        fireAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        fBaseUID = fireAuth.getCurrentUser().getUid();


        DocumentReference dr = db.collection("account").document(fBaseUID);
        dr.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                aUserName.setText(documentSnapshot.getString("username"));
                    }
        });



        aConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String userName = aUserName.getText().toString();

               if (TextUtils.isEmpty(userName)) {
                   aUserName.setError("A username is required");
                   return;
                }


              db.collection("account").document(fBaseUID)
                    .update("username", userName);

                Toast.makeText(editUsername.this, "Username Updated!", Toast.LENGTH_SHORT).show();

            }});




                aBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), profileDetails.class));
                finish();
            }});


/*

                Map<String, Object> username = new HashMap<>();
                username.put("username", userName);
                db.collection("account").document(fBaseUID)
                        .set(username)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Log.d(TAG, "Username Updated");
                            }
                        });
                startActivity(new Intent(getApplicationContext(), profileDetails.class));


*/



/*
                DocumentReference docRef = db.collection("account").document();
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                            } else {
                                Log.d(TAG, "No such document");
                            }
                        } else {
                            Log.d(TAG, "Get failed with ", task.getException());

                            }
                        }
                    }
               );*/


/*
        aConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = aUserName.getText().toString();
                String password = aPassword.getText().toString();
                String rePassword = aRePass.getText().toString();

                fireAuth.updateCurrentUser(userName, password)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {


                                if (!Objects.equals(password, rePassword)) {
                                    aRePass.setError("Passwords must match.");
                                    return;
                                }

                            }
                        });

                startActivity(new Intent(getApplicationContext(), profileDetails.class));
                finish();
                ;
            }
        });
*/


    }};