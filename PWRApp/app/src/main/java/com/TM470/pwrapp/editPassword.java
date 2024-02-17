package com.TM470.pwrapp;

import androidx.annotation.NonNull;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.core.Tag;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Objects;

public class editPassword extends AppCompatActivity {


    EditText aPassword, aRePassword;
    Button aConfirm, aBack;
    ProgressBar progressBar;
    FirebaseAuth fireAuth;
    FirebaseFirestore db;
    String fBaseUID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_edit_password);

        aBack = findViewById(R.id.backBtn);
        aConfirm = findViewById(R.id.confirmBtn);

        aPassword = findViewById(R.id.passwordEdit);
        aRePassword = findViewById(R.id.rePasswordEdit);
        progressBar = findViewById(R.id.progressBar);

        fireAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        fBaseUID = fireAuth.getCurrentUser().getUid();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();






        DocumentReference dr = db.collection("account").document(fBaseUID);
        dr.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                aPassword.setText(documentSnapshot.getString("password"));
                aRePassword.setText(documentSnapshot.getString("password"));
            }
        });

        aBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), profileDetails.class));
                finish();
            }});





        // This following section updates the FireBase Firestore Database password array/field and the authentication for logging in.

        aConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String password = aPassword.getText().toString().trim();
                String rePassword = aRePassword.getText().toString().trim();


                if (TextUtils.isEmpty(password)) {
                    aPassword.setError("A password is required for the change to take effect.");
                   return;
                }

                if (TextUtils.isEmpty(password)) {
                    aRePassword.setError("The password needs to be repeated for the change to take effect");
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


                String newPassword = password;

            user.updatePassword(newPassword)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                db.collection("account").document(fBaseUID)
                                        .update("password", password);
                                Toast.makeText(editPassword.this, "Password Updated!", Toast.LENGTH_SHORT).show();
                                return;
                            } else {
                                Toast.makeText(editPassword.this, "Password update failed!", Toast.LENGTH_SHORT).show();
                                return;
                            }
        }});

    }});}}