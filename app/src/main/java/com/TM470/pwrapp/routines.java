package com.TM470.pwrapp;

import androidx.annotation.NonNull;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class routines extends AppCompatActivity {

    private static final String TAG = "Debug 22";
    FirebaseAuth fireAuth;
    FirebaseFirestore db;
    String user;


    Button bCreateRoutine, aHomeBtn;
    TableLayout aTableLayout;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_routines);

        bCreateRoutine = findViewById(R.id.createRoutineBtn);
        aHomeBtn = findViewById(R.id.homeBtn);

        aTableLayout = findViewById(R.id.tableLayout);

        fireAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        user = fireAuth.getCurrentUser().getUid();



        db.collection("account").document(user).collection("workouts").orderBy("Workout Name", Query.Direction.ASCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                int i = View.generateViewId();


                                Log.d(TAG, document.getId() + " Workout Name: " + document.get("Workout Name") + " Date Created: " + document.get("Date") + "Unique ID; " + i);


                                String woName = (String) document.get("Workout Name");
                                String dateCreated = (String) document.get("Date");


                                TableRow row = new TableRow(routines.this);
                                row.setId(i);
                                Log.d(TAG, "row id = " + i);

                                TableRow.LayoutParams layParams = new TableRow.LayoutParams(
                                       TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);


                                Button workoutNames = new Button(routines.this);
                                workoutNames.setId(i);
                                Log.d(TAG, "workout name id = " + i);
                                workoutNames.setText(woName);
                                workoutNames.setTextSize(20);
                                workoutNames.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                                workoutNames.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                                workoutNames.setTypeface(getResources().getFont(R.font.convergence));
                                workoutNames.setTextColor(getResources().getColor(android.R.color.black));
                                workoutNames.setPadding(50, 0, 5, 0);
                                workoutNames.setLayoutParams(layParams);
                                layParams.weight = 1;


                                TextView dates = new TextView(routines.this);
                                dates.setId(i);
                                Log.d(TAG, "date id = " + i);
                                dates.setText(dateCreated);
                                dates.setTypeface(getResources().getFont(R.font.convergence));
                                dates.setTextColor(getResources().getColor(android.R.color.black));
                                dates.setPadding(5, 5, 50, 0);

                                workoutNames.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent intent = new Intent(getApplicationContext(), selectedWorkout.class);
                                        intent.putExtra("Document_ID", document.getId());
                                        Log.d(TAG, "Results: " + document.getData());
                                        startActivity(intent);

                                    }
                                });


                                row.addView(workoutNames);
                                row.addView(dates);
                                aTableLayout.addView(row);

                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });





        bCreateRoutine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), createRoutines.class));
            }
        });

        aHomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), homePage.class));
            }
        });


    }}