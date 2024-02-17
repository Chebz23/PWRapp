package com.TM470.pwrapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class workoutClicked extends AppCompatActivity {

    private static final String TAG = "Workout Clicked Debug";

    FirebaseAuth fireAuth;
    FirebaseFirestore db;
    String user;
    DocumentReference workoutDoc;
    Date date = new Date();
    SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
    String newDate = df.format(date);


    TextView aHeader, aExerciseIpt, aSetNum, aRepNum, aExerciseIpt2, aSetNum2, aRepNum2, aExerciseIpt3, aSetNum3, aRepNum3, aWorkoutNameHidden;
    Button bAddWorkout, bHomeBtn;
    TableRow aRow, aRow2, aRow3;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_workout_clicked);

        fireAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        user = fireAuth.getCurrentUser().getUid();

        bAddWorkout = findViewById(R.id.addRoutineBtn);

        aWorkoutNameHidden = findViewById(R.id.workoutNameHidden);

        aExerciseIpt = findViewById(R.id.exerciseInput);
        aSetNum = findViewById(R.id.numberSets);
        aRepNum = findViewById(R.id.numberReps);

        aExerciseIpt2 = findViewById(R.id.exerciseInput2);
        aSetNum2 = findViewById(R.id.numberSets2);
        aRepNum2 = findViewById(R.id.numberReps2);

        aExerciseIpt3 = findViewById(R.id.exerciseInput3);
        aSetNum3 = findViewById(R.id.numberSets3);
        aRepNum3 = findViewById(R.id.numberReps3);

        aRow = (TableRow) findViewById(R.id.row);
        aRow2 = (TableRow) findViewById(R.id.row2);
        aRow3 = (TableRow) findViewById(R.id.row3);


        Intent intent = getIntent();
        String docID = intent.getStringExtra("Document_ID");
        DocumentReference dr = db.collection("account").document(user).collection("workouts").document(docID);


        if (!docID.isEmpty()) {
            Log.d(TAG, "Checking Document ID is correct " + docID);
        }

        aHeader = findViewById(R.id.header);
        workoutDoc = db.collection("All Workouts").document(docID);
        if (workoutDoc.getId() != null) {
            workoutDoc.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                    aHeader.setText("Workout: " + value.getString("Workout Name"));
                }
            });
            Log.d(TAG, "Collecting document data: " + workoutDoc);
        } else {
            Log.d(TAG, "Collection of document data failed");
        }

        db.collection("All Workouts").document(docID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {

                    if (task.getResult().getString("Exercise 1") != null) {

                        String exercise = task.getResult().getString("Exercise 1");
                        String sets = task.getResult().getString("Sets 1");
                        String reps = task.getResult().getString("Reps 1");

                        TableRow row1 = findViewById(R.id.row);
                        TextView ex1 = findViewById(R.id.exerciseInput);
                        TextView st1 = findViewById(R.id.numberSets);
                        TextView rp1 = findViewById(R.id.numberReps);
                        ex1.setText(exercise);
                        st1.setText(sets);
                        rp1.setText(reps);

                        String workoutNameText = task.getResult().getString("Workout Name");
                        TextView woName = findViewById(R.id.workoutNameHidden);
                        woName.setText(workoutNameText);

                        Log.d(TAG, "Test debug 1");
                        row1.setVisibility(View.VISIBLE);
                    }

                    if (task.getResult().getString("Exercise 2") != null) {


                        String exercise2 = task.getResult().getString("Exercise 2");
                        String sets2 = task.getResult().getString("Sets 2");
                        String reps2 = task.getResult().getString("Reps 2");

                        TableRow row2 = findViewById(R.id.row2);
                        TextView ex2 = findViewById(R.id.exerciseInput2);
                        TextView st2 = findViewById(R.id.numberSets2);
                        TextView rp2 = findViewById(R.id.numberReps2);
                        ex2.setText(exercise2);
                        st2.setText(sets2);
                        rp2.setText(reps2);

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
                        TextView ex3 = findViewById(R.id.exerciseInput3);
                        TextView st3 = findViewById(R.id.numberSets3);
                        TextView rp3 = findViewById(R.id.numberReps3);
                        ex3.setText(exercise3);
                        st3.setText(sets3);
                        rp3.setText(reps3);

                        Log.d(TAG, "Test debug 3");
                        row3.setVisibility(View.VISIBLE);

                    } else {
                        if (task.getResult().getString("Exercise 2") != null) {


                            Log.d(TAG, "Three exercises");
                        }
                    }
                }
            }
        });

        bAddWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (aRow.getVisibility() == View.VISIBLE) {

                    String e = aExerciseIpt.getText().toString();
                    String s = aSetNum.getText().toString();
                    String r = aRepNum.getText().toString();

                    String workoutName = aWorkoutNameHidden.getText().toString();


                    if (TextUtils.isEmpty(e) || TextUtils.isEmpty(s) || TextUtils.isEmpty(r)) {
                        Toast.makeText(workoutClicked.this, "All fields must have values.", Toast.LENGTH_LONG).show();
                        return;
                    }

                    Map<String, Object> row1 = new HashMap<>();
                    row1.put("Workout Name", workoutName);
                    row1.put("Date", newDate);
                    row1.put("Exercise 1", e);
                    row1.put("Sets 1", s);
                    row1.put("Reps 1", r);
                    dr.set(row1).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            if (aRow2.getVisibility() != View.VISIBLE) {
                                Toast.makeText(workoutClicked.this, "One exercise workout created.", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), findRoutines.class));
                            }
                        }
                    });
                } else {

                    Toast.makeText(workoutClicked.this, "One exercise is required.", Toast.LENGTH_SHORT).show();

                }
                if (aRow2.getVisibility() == View.VISIBLE) {

                    String e2 = aExerciseIpt2.getText().toString();
                    String s2 = aSetNum2.getText().toString();
                    String r2 = aRepNum2.getText().toString();

                    Map<String, Object> row2 = new HashMap<>();
                    row2.put("Exercise 2", e2);
                    row2.put("Sets 2", s2);
                    row2.put("Reps 2", r2);

                    dr.update(row2).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            if (aRow3.getVisibility() != View.VISIBLE) {
                                Toast.makeText(workoutClicked.this, "Two exercise workout created.", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), findRoutines.class));
                            }
                        }
                    });
                }
                if (aRow3.getVisibility() == View.VISIBLE) {

                    String e3 = aExerciseIpt3.getText().toString();
                    String s3 = aSetNum3.getText().toString();
                    String r3 = aRepNum3.getText().toString();

                    Map<String, Object> row3 = new HashMap<>();
                    row3.put("Exercise 3", e3);
                    row3.put("Sets 3", s3);
                    row3.put("Reps 3", r3);

                    dr.update(row3).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(workoutClicked.this, "Three exercise workout created.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), findRoutines.class));
                        }
                    });

                }
            }
        });



    }}