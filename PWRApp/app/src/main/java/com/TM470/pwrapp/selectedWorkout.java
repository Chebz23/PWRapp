package com.TM470.pwrapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.TextView;
import android.content.Intent;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class selectedWorkout extends AppCompatActivity {

    private static final String TAG = "Selected Workout Debug";

    FirebaseAuth fireAuth;
    FirebaseFirestore db;
    String user;
    DocumentReference workoutDoc;

    Button aLogBtn, aLogsBtn;
    TextView aHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_selected_workout);

        fireAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        user = fireAuth.getCurrentUser().getUid();

        aLogBtn = findViewById(R.id.logWorkout);
        aLogsBtn = findViewById(R.id.logs);


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
                        aHeader.setText("Workout: "+ value.getString("Workout Name"));
                    }
                });
                Log.d(TAG, "Collecting document data: " + workoutDoc);
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
                        TextView ex1 = findViewById(R.id.exerciseInput);
                        TextView st1 = findViewById(R.id.numberSets);
                        TextView rp1 = findViewById(R.id.numberReps);
                        ex1.setText(exercise);
                        st1.setText(sets);
                        rp1.setText(reps);

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

                } else { if (task.getResult().getString("Exercise 2") != null) {


                    Log.d(TAG, "Three exercises");
                }}
        }}
    });

        aLogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), logWorkout.class);
                intent.putExtra("Document_ID", workoutDoc.getId());
                startActivity(intent);
            }});

        aLogsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), loggedWorkouts.class);
                intent.putExtra("Document_ID", workoutDoc.getId());
                startActivity(intent);
            }});

    }}