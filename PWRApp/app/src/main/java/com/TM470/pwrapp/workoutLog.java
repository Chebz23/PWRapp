package com.TM470.pwrapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class workoutLog extends AppCompatActivity {

    private static final String TAG = "Pull log data debug";



    FirebaseAuth fireAuth;
    FirebaseFirestore db;
    String user;

    DocumentReference logDoc;

    TextView aHeader;

    TableLayout aLayout;
    TableRow aRow, aRow2, aRow3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_workout_log);

        fireAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        user = fireAuth.getCurrentUser().getUid();

        aHeader = findViewById(R.id.header);
        
/*
        aSetNum = findViewById(R.id.numberSets);
        aRepNum = findViewById(R.id.numberReps);

        aSetNum2 = findViewById(R.id.numberSets2);
        aRepNum2 = findViewById(R.id.numberReps2);

        aSetNum3 = findViewById(R.id.numberSets3);
        aRepNum3 = findViewById(R.id.numberReps3);

        aLayout = (TableLayout) findViewById(R.id.exerciseTable);
        aRow = (TableRow) findViewById(R.id.row);
        aRow2 = (TableRow) findViewById(R.id.row2);
        aRow3 = (TableRow) findViewById(R.id.row3);
*/

        Intent intent = getIntent();
        String docID = intent.getStringExtra("Document ID");
        String logID = intent.getStringExtra("Log ID");

        logDoc = db.collection("account").document(user).collection("workouts").document(docID)
                .collection("Logs")
                .document(logID);

        if (logDoc.getId() != null) {
            logDoc.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                    aHeader.setText("Logged results for session on "+ value.getString("Date"));
                }
            });
            Log.d(TAG, "Collecting document data: " + logDoc);
        } else {
            Log.d(TAG, "Collection of document data failed");
        }

        db.collection("account").document(user).collection("workouts").document(docID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {

                            if (task.getResult().getString("Exercise 1") != null) {

                                String exercise = task.getResult().getString("Exercise 1");
                                String sets = task.getResult().getString("Sets 1");
                                String reps = task.getResult().getString("Reps 1");

                                TableRow row1 = findViewById(R.id.row);
                                TextView ex1 = findViewById(R.id.exerciseTxt);
                                TextView st1 = findViewById(R.id.sets);
                                TextView rp1 = findViewById(R.id.reps);
                                ex1.setText("Exercise:  " + exercise);
                                st1.setText("Sets:  " + sets);
                                rp1.setText("Reps:  " + reps);

                                Log.d(TAG, "Test debug 1");
                                row1.setVisibility(View.VISIBLE);
                            }

                            if (task.getResult().getString("Exercise 2") != null) {


                                String exercise2 = task.getResult().getString("Exercise 2");
                                String sets2 = task.getResult().getString("Sets 2");
                                String reps2 = task.getResult().getString("Reps 2");

                                TableRow row2 = findViewById(R.id.row2);
                                TextView ex2 = findViewById(R.id.exerciseTxt2);
                                TextView st2 = findViewById(R.id.sets2);
                                TextView rp2 = findViewById(R.id.reps2);
                                ex2.setText("Exercise:  " + exercise2);
                                st2.setText("Sets:  " + sets2);
                                rp2.setText("Reps:  " + reps2);

                                Log.d(TAG, "Test debug 2");
                                row2.setVisibility(View.VISIBLE);

                            } else {
                                Log.d(TAG, "Two exercises in this workout");
                            }
                            if (task.getResult().getString("Exercise 3") != null) {


                                String exercise3 = task.getResult().getString("Exercise 3");
                                String sets3 = task.getResult().getString("Sets 3");
                                String reps3 = task.getResult().getString("Reps 3");

                                TableRow row3 = findViewById(R.id.row3);
                                TextView ex3 = findViewById(R.id.exerciseTxt3);
                                TextView st3 = findViewById(R.id.sets3);
                                TextView rp3 = findViewById(R.id.reps3);
                                ex3.setText("Exercise:  " + exercise3);
                                st3.setText("Sets:  " + sets3);
                                rp3.setText("Reps:  " + reps3);

                                Log.d(TAG, "Test debug 3");
                                row3.setVisibility(View.VISIBLE);

                            } else { if (task.getResult().getString("Exercise 2") != null) {


                                Log.d(TAG, "Three exercises");
                            }}
                        }}
                });

        db.collection("account").document(user).collection("workouts").document(docID)
                .collection("Logs").document(logID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {

                            if (task.getResult().getString("Sets 1 Comp") != null) {

                                String setComps = task.getResult().getString("Sets 1 Comp");
                                String repComps = task.getResult().getString("Reps 1 Comp");

                                TableRow row1 = findViewById(R.id.row);
                                TextView stn1 = findViewById(R.id.numberSets);
                                TextView rpn1 = findViewById(R.id.numberReps);
                                stn1.setText("Completed:  " + setComps);
                                rpn1.setText("Completed:  " + repComps);

                                Log.d(TAG, "Test debug 1");
                                row1.setVisibility(View.VISIBLE);
                            }

                            if (task.getResult().getString("Sets 2 Comp") != null) {


                                String setsComp2 = task.getResult().getString("Sets 2 Comp");
                                String repsComp2 = task.getResult().getString("Reps 2 Comp");

                                TableRow row2 = findViewById(R.id.row2);
                                TextView stn2 = findViewById(R.id.numberSets2);
                                TextView rpn2 = findViewById(R.id.numberReps2);
                                stn2.setText("Completed:  " + setsComp2);
                                rpn2.setText("Completed:  " + repsComp2);

                                Log.d(TAG, "Test debug 2");
                                row2.setVisibility(View.VISIBLE);

                            } else {
                                Log.d(TAG, "Two exercises in this workout");
                            }
                            if (task.getResult().getString("Sets 3 Comp") != null) {


                                String setsComp3 = task.getResult().getString("Sets 3 Comp");
                                String repsComp3 = task.getResult().getString("Reps 3 Comp");

                                TableRow row3 = findViewById(R.id.row3);
                                TextView stn3 = findViewById(R.id.numberSets3);
                                TextView rpn3 = findViewById(R.id.numberReps3);
                                stn3.setText("Completed:  " + setsComp3);
                                rpn3.setText("Completed:  " + repsComp3);

                                Log.d(TAG, "Test debug 3");
                                row3.setVisibility(View.VISIBLE);

                            } else { if (task.getResult().getString("Exercise 2") != null) {


                                Log.d(TAG, "Three exercises");
                            }}
                        }}
                });



        }
}