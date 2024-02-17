package com.TM470.pwrapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class loggedWorkouts extends AppCompatActivity {
    private static final String TAG = "Selected Workout Debug";

    FirebaseAuth fireAuth;
    FirebaseFirestore db;
    String user;
    DocumentReference workoutDoc;

    TextView aHeader;
    TableLayout aTableLayout;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_logged_workouts);


        fireAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        user = fireAuth.getCurrentUser().getUid();

        aTableLayout = findViewById(R.id.tableLayout);


        Intent intent = getIntent();
        String docID = intent.getStringExtra("Document_ID");

        if (!docID.isEmpty()) {
            Log.d(TAG, "Checking Document ID is correct " + docID);
        }

        aHeader = findViewById(R.id.header);
        workoutDoc = db.collection("account").document(user).collection("workouts").document(docID);
        if (workoutDoc.getId() != null) {
            workoutDoc.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                    aHeader.setText("Workout Log List for: "+ value.getString("Workout Name"));
                }
            });
            Log.d(TAG, "Collecting document data: " + workoutDoc);
        } else {
            Log.d(TAG, "Collection of document data failed");
        }


        db.collection("account").document(user).collection("workouts").document(docID).collection("Logs").orderBy("Created", Query.Direction.DESCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {



                        int i = View.generateViewId();


                        Log.d(TAG, "DocumentID: " + document.getId() + " Date Created: " + document.get("Date") + " Log ID " + document.get("Log ID") + " Unique ID; " + i);


                        String dateCreated = (String) document.get("Date");
                        String timeCreated = (String) document.get("Log ID");


                        TableRow row = new TableRow(loggedWorkouts.this);
                        row.setId(i);
                        Log.d(TAG, "row id = " + i);

                        TableRow.LayoutParams layParams = new TableRow.LayoutParams(
                                TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);


                        Button logBtn = new Button(loggedWorkouts.this);
                        logBtn.setId(i);
                        Log.d(TAG, "Log ID = " + document.get("Log ID"));
                        logBtn.setText(timeCreated + "  on  " + dateCreated);
                        logBtn.setTextSize(20);
                        logBtn.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        logBtn.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                        logBtn.setTypeface(getResources().getFont(R.font.convergence));
                        logBtn.setTextColor(getResources().getColor(android.R.color.black));
                        logBtn.setPadding(50, 0, 5, 0);
                        logBtn.setLayoutParams(layParams);
                        layParams.weight = 1;



                        logBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(loggedWorkouts.this, "Log document ID is: " + document.getId() + "  Row ID is: " + row.getId(), Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), workoutLog.class);
                                intent.putExtra("Document ID", workoutDoc.getId());
                                intent.putExtra("Log ID", document.getId());
                                Log.d(TAG, "Results: " + document.getData());
                                startActivity(intent);

                            }
                        });
                        db.collection("account").document(user).collection("workouts").document(docID).collection("Logs").orderBy("Log ID", Query.Direction.DESCENDING);

                        row.addView(logBtn);
                        aTableLayout.addView(row);


                    }
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }

        });


    }}