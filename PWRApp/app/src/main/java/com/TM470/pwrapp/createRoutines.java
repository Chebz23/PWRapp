package com.TM470.pwrapp;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.squareup.okhttp.internal.DiskLruCache;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class createRoutines extends AppCompatActivity {

    FirebaseAuth fireAuth;
    FirebaseFirestore db;
    String user;
    Date date = new Date();
    SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
    String newDate = df.format(date);


    TextView aExercise, aSets, aReps;
    EditText aWorkoutName, aExerciseIpt, aSetNum, aRepNum, aExerciseIpt2, aSetNum2, aRepNum2, aExerciseIpt3, aSetNum3, aRepNum3;
    Button aAddBtn, aAcceptBtn, aDiscardBtn;
    TableLayout aLayout;
    TableRow aRow, aRow2, aRow3;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_create_routines);

        fireAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        aWorkoutName = findViewById(R.id.workoutName);

        aAddBtn = findViewById(R.id.addExerciseBtn);
        aAcceptBtn = findViewById(R.id.acceptBtn);
        aDiscardBtn = findViewById(R.id.discardBtn);

        aExercise = findViewById(R.id.exerciseTxt);
        aSets = findViewById(R.id.sets);
        aReps = findViewById(R.id.reps);

        aExerciseIpt = findViewById(R.id.exerciseInput);
        aSetNum = findViewById(R.id.numberSets);
        aRepNum = findViewById(R.id.numberReps);

        aExerciseIpt2 = findViewById(R.id.exerciseInput2);
        aSetNum2 = findViewById(R.id.numberSets2);
        aRepNum2 = findViewById(R.id.numberReps2);

        aExerciseIpt3 = findViewById(R.id.exerciseInput3);
        aSetNum3 = findViewById(R.id.numberSets3);
        aRepNum3 = findViewById(R.id.numberReps3);


        aLayout = (TableLayout) findViewById(R.id.exerciseTable);
        aRow = (TableRow) findViewById(R.id.row);
        aRow2 = (TableRow) findViewById(R.id.row2);
        aRow3 = (TableRow) findViewById(R.id.row3);


        aAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (aRow.getVisibility() == View.GONE) {
                    aRow.setVisibility(View.VISIBLE);
                } else {
                    if (aRow2.getVisibility() == View.GONE) {
                        aRow2.setVisibility(View.VISIBLE);
                    } else {
                        if (aRow3.getVisibility() == View.GONE) {
                            aRow3.setVisibility(View.VISIBLE);
                        } else {
                            Toast.makeText(createRoutines.this, "Something has gone wrong.", Toast.LENGTH_SHORT);
                        }
                    }
                }
            }
        });

        aAcceptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user = fireAuth.getCurrentUser().getUid();
                String workoutName = aWorkoutName.getText().toString();


                if (TextUtils.isEmpty(workoutName)) {
                    aWorkoutName.setError("A name must be entered to continue.");
                    return;
                }

                /*

                DocumentReference id = db.collection("workouts").document();
                id.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Log.d(TAG, "Document exists!");
                                Toast.makeText(createRoutines.this, "Found!", Toast.LENGTH_SHORT).show();
                            } else {
                                Log.d(TAG, "Document does not exist!");
                                Toast.makeText(createRoutines.this, "Not found", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Log.d(TAG, "Failed with: ", task.getException());
                        }
                    }
                });

*/
                /*!!!!!!!!!! DO NOT DELETE THIS DOC REF !!!!!!!!!!!!!!!!
                *  This Doc Ref is used to create and hold the workout for personal lists */

                DocumentReference dr = db.collection("account").document(user).collection("workouts").document();
                String docID = dr.getId();

                /* This Doc Ref is used to add the workout to the 'All workouts' collection */

                DocumentReference aw = db.collection("All Workouts").document(docID);




/*
                if (Objects.equals(workoutName, docName)) {
                    Toast.makeText(createRoutines.this,"This workout name is already in use: " + workoutName, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(createRoutines.this, "This name is available: " + workoutName, Toast.LENGTH_SHORT).show();
                }

*/
/*

                db.collection("workouts").whereEqualTo(workoutName, true).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(createRoutines.this, "This workout exists: " + workoutName,Toast.LENGTH_LONG).show();
                            return;

                        }
                    }
                });

*/

                if (aRow.getVisibility() == View.VISIBLE) {

                    String e = aExerciseIpt.getText().toString();
                    String s = aSetNum.getText().toString();
                    String r = aRepNum.getText().toString();


                    if (TextUtils.isEmpty(e) || TextUtils.isEmpty(s) || TextUtils.isEmpty(r)) {
                        Toast.makeText(createRoutines.this, "All fields must have values.", Toast.LENGTH_LONG).show();
                        return;
                    }

                    Map<String, Object> row1 = new HashMap<>();
                    row1.put("Workout Name", workoutName);
                    row1.put("Date", newDate);
                    row1.put("Exercise 1", e);
                    row1.put("Sets 1", s);
                    row1.put("Reps 1", r);
                    aw.set(row1);
                    dr.set(row1).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            if (aRow2.getVisibility() != View.VISIBLE) {
                            Toast.makeText(createRoutines.this, "One exercise workout created.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), routines.class));
                        }
                    }});
                } else {

                            Toast.makeText(createRoutines.this, "One exercise is required.", Toast.LENGTH_SHORT).show();

                    }
                if (aRow2.getVisibility() == View.VISIBLE) {

                    String e2 = aExerciseIpt2.getText().toString();
                    String s2 = aSetNum2.getText().toString();
                    String r2 = aRepNum2.getText().toString();

                    if (TextUtils.isEmpty(e2) || TextUtils.isEmpty(s2) || TextUtils.isEmpty(r2)) {
                        Toast.makeText(createRoutines.this, "All fields must have values.", Toast.LENGTH_LONG).show();
                        db.collection("account").document(user).collection("workouts").document(docID).delete();
                    }

                    Map<String, Object> row2 = new HashMap<>();
                    row2.put("Exercise 2", e2);
                    row2.put("Sets 2", s2);
                    row2.put("Reps 2", r2);

/*
                        dr.update(row2).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(createRoutines.this, "Row 2 added for testing", Toast.LENGTH_SHORT).show();
                            }
                        });
*/
                    aw.update(row2);
                    dr.update(row2).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            if (aRow3.getVisibility() != View.VISIBLE) {
                            Toast.makeText(createRoutines.this, "Two exercise workout created.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), routines.class));
                            }
                    }});}
                if (aRow3.getVisibility() == View.VISIBLE) {

                    String e3 = aExerciseIpt3.getText().toString();
                    String s3 = aSetNum3.getText().toString();
                    String r3 = aRepNum3.getText().toString();

                    if (TextUtils.isEmpty(e3) || TextUtils.isEmpty(s3) || TextUtils.isEmpty(r3)) {
                        Toast.makeText(createRoutines.this, "All fields must have values.", Toast.LENGTH_LONG).show();
                        db.collection("account").document(user).collection("workouts").document(docID).delete();
                    }

                    Map<String, Object> row3 = new HashMap<>();
                    row3.put("Exercise 3", e3);
                    row3.put("Sets 3", s3);
                    row3.put("Reps 3", r3);

                    aw.update(row3);
                    dr.update(row3).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(createRoutines.this, "Three exercise workout created.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), routines.class));
                        }
                    });

                }}});

        aDiscardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String workoutName = aWorkoutName.getText().toString();

                if (aRow3.getVisibility() == View.VISIBLE) {
                    aExerciseIpt3.setText("");
                    aSetNum3.setText("");
                    aRepNum3.setText("");
                    aRow3.setVisibility(View.GONE);
                } else {

                    if (aRow2.getVisibility() == View.VISIBLE) {
                        aExerciseIpt2.setText("");
                        aSetNum2.setText("");
                        aRepNum2.setText("");
                        aRow2.setVisibility(View.GONE);
                    } else {

                        if (aRow.getVisibility() == View.VISIBLE) {
                            aExerciseIpt.setText("");
                            aSetNum.setText("");
                            aRepNum.setText("");
                            aRow.setVisibility(View.GONE);
                        } else {

                            if (!TextUtils.isEmpty(workoutName)) {
                                aWorkoutName.setText("");
                            } else {
                                Toast.makeText(createRoutines.this, "Nothing to discard.", Toast.LENGTH_SHORT).show();
                            }
                        }

                }}}});


    }
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Are you sure?").setMessage("If you leave now your current workout data will be lost. Are you sure?")
                .setNegativeButton(R.string.no, null)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                startActivity(new Intent(getApplicationContext(), routines.class));}
            }).create().show();
    }

}