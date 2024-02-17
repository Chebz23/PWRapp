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
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ServerTimestamp;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class logWorkout extends AppCompatActivity {
    private static final String TAG = "Create log Debug";



    FirebaseAuth fireAuth;
    FirebaseFirestore db;
    String user;
    Date date = new Date();
    SimpleDateFormat df = new SimpleDateFormat("EEE dd MMM yyyy", Locale.getDefault());
    String newDate = df.format(date);

    Date time = Calendar.getInstance().getTime();
    SimpleDateFormat tf = new SimpleDateFormat("h:mm a");
    String newTime = tf.format(time);
    DocumentReference workoutDoc;

    SimpleDateFormat tsFormat = new SimpleDateFormat("dd.MM.yyyy  HH.mm.SS");

    Timestamp timestampNow;


    TextView aHeader;
    EditText aSetNum, aRepNum, aSetNum2, aRepNum2, aSetNum3, aRepNum3;
    Button aSubmitBtn;
    TableLayout aLayout;
    TableRow aRow, aRow2, aRow3;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_log_workout);

        timestampNow = Timestamp.now();

        fireAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        user = fireAuth.getCurrentUser().getUid();

        aHeader = findViewById(R.id.header);
        aSubmitBtn = findViewById(R.id.submitBtn);

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



        Intent intent = getIntent();
        String docID = intent.getStringExtra("Document_ID");

        Log.d(TAG, "Creation complete!");


        if (!docID.isEmpty()) {
            Log.d(TAG, "Checking Document ID is correct " + docID);
        }


        workoutDoc = db.collection("account").document(user).collection("workouts").document(docID);
        if (workoutDoc.getId() != null) {
            workoutDoc.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                    aHeader.setText("Log for workout - "+ value.getString("Workout Name"));
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
                        TextView ex1 = findViewById(R.id.exerciseTxt);
                        TextView st1 = findViewById(R.id.sets);
                        TextView rp1 = findViewById(R.id.reps);
                        ex1.setText("Exercise:  " + exercise);
                        st1.setText("Sets:  " + sets);
                        rp1.setText("Reps:  " + reps);

                        Log.d(TAG, "Test debug 1");
                        row1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
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
                        row2.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
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
                        row3.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        row3.setVisibility(View.VISIBLE);

                    } else { if (task.getResult().getString("Exercise 2") != null) {


                        Log.d(TAG, "Three exercises");
                    }}
                }}
        });

        aSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
                user = fireAuth.getCurrentUser().getUid();

                DocumentReference dr = db.collection("account").document(user).collection("workouts").document(docID).collection("Logs").document();
                String logID = dr.getId();
                Log.d(TAG, "This is the ID: " + logID);
                
                if (aRow.getVisibility() == View.VISIBLE) {

                    String s = aSetNum.getText().toString();
                    String r = aRepNum.getText().toString();

                    int i = View.generateViewId();



                    if (TextUtils.isEmpty(s) || TextUtils.isEmpty(r)) {
                        Toast.makeText(logWorkout.this, "All fields must have values. Please enter 'Log' again and submit.", Toast.LENGTH_LONG).show();
                        return;
                    }

                    Map<String, Object> row1 = new HashMap<>();
                    row1.put("Log ID", newTime);
                    row1.put("Date", newDate);
                    row1.put("Created", timestampNow);
                    row1.put("Sets 1 Comp", s);
                    row1.put("Reps 1 Comp", r);
                    dr.set(row1).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            if (aRow2.getVisibility() != View.VISIBLE) {
                                Toast.makeText(logWorkout.this, "One exercise logged.", Toast.LENGTH_SHORT).show();

                            }
                        }});
                } else {

                    Toast.makeText(logWorkout.this, "All fields must have a value.", Toast.LENGTH_SHORT).show();

                }
                if (aRow2.getVisibility() == View.VISIBLE) {

                    String s2 = aSetNum2.getText().toString();
                    String r2 = aRepNum2.getText().toString();

                    if (TextUtils.isEmpty(s2) || TextUtils.isEmpty(r2)) {
                        Toast.makeText(logWorkout.this, "All fields must have values. Please enter 'Log' again and submit. Please", Toast.LENGTH_LONG).show();
                        db.collection("account").document(user).collection("workouts").document(docID).collection("Logs").document(logID).delete();
                    }

                    Map<String, Object> row2 = new HashMap<>();
                    row2.put("Sets 2 Comp", s2);
                    row2.put("Reps 2 Comp", r2);

                    dr.update(row2).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            if (aRow3.getVisibility() != View.VISIBLE) {
                                Toast.makeText(logWorkout.this, "Two exercise logged.", Toast.LENGTH_SHORT).show();
                            }
                        }});}
                if (aRow3.getVisibility() == View.VISIBLE) {

                    String s3 = aSetNum3.getText().toString();
                    String r3 = aRepNum3.getText().toString();

                    if (TextUtils.isEmpty(s3) || TextUtils.isEmpty(r3)) {
                        Toast.makeText(logWorkout.this, "All fields must have values. Please enter 'Log' again and submit.", Toast.LENGTH_LONG).show();
                        db.collection("account").document(user).collection("workouts").document(docID).collection("Logs").document(logID).delete();
                    }

                    Map<String, Object> row3 = new HashMap<>();
                    row3.put("Sets 3 Comp", s3);
                    row3.put("Reps 3 Comp", r3);

                    dr.update(row3).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(logWorkout.this, "Three exercises logged.", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            Toast.makeText(logWorkout.this, "Workout Logged!", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(), selectedWorkout.class));
                finish();

            }});







    }
}