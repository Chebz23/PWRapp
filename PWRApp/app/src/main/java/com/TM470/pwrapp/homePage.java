package com.TM470.pwrapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class homePage extends AppCompatActivity {

    FirebaseAuth fireAuth;
    FirebaseFirestore db;
    String fBaseUID;

    TextView header;
    Button bLogout, bRoutines, bSearch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_home_page);

        fireAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        fBaseUID = fireAuth.getCurrentUser().getUid();

        header = findViewById(R.id.header);

        bLogout = findViewById(R.id.logout);
        bRoutines = findViewById(R.id.routines);
        bSearch = findViewById(R.id.search);

        DocumentReference dr = db.collection("account").document(fBaseUID);
        dr.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot dSnap, @Nullable FirebaseFirestoreException error) {
                        header.setText("Welcome, " + dSnap.getString("username"));
                    }
                });
        bRoutines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), routines.class));
            }
        });

        bSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),findRoutines.class));
            }
        });

//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser()


        // Check if user is signed in (non-null) and update UI accordingly.
//           FirebaseUser currentUser = fireAuth.getCurrentUser();
//           updateUI(currentUser);


        //   private void updateUI(FirebaseUser currentUser) {
//    }


    }


    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
    }

    public void account(View view) {
        startActivity(new Intent(getApplicationContext(), profileDetails.class));
        finish();
    }


}