package com.TM470.pwrapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;

public class findRoutines extends AppCompatActivity {

    private static final String TAG = "Debug Search page";

    FirebaseAuth fireAuth;
    FirebaseFirestore db;
    String fBaseUID;

    EditText aSearchTxt;
    Button bSearchBtn, bHomeBtn;
    TableLayout aTableLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_find_routines);

        fireAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        fBaseUID = fireAuth.getCurrentUser().getUid();

        aSearchTxt = findViewById(R.id.searchTxt);
        bSearchBtn = findViewById(R.id.searchBtn);
        aTableLayout = findViewById(R.id.tableLayout);

        bHomeBtn = findViewById(R.id.homeBtn);


        bSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String search = aSearchTxt.getText().toString();
                Log.d(TAG, "search for " + search + " is found");
                db.collection("All Workouts").orderBy("Workout Name", Query.Direction.ASCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG,"Search completed");
                            aTableLayout.removeAllViews();
                            Log.d(TAG, "Previous search removed");

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, "Exercise 1 is: " + document.get("Exercise 1"));

                                String ex1 = (String) document.get("Exercise 1");
                                String ex2 = (String) document.get("Exercise 2");
                                String ex3 = (String) document.get("Exercise 3");

                                Log.d(TAG, ex1 + " | " + ex2 + " | " + ex3);

                                if (Objects.equals(ex1, search)) {

                                    int i = View.generateViewId();

                                    String workoutName = (String) document.get("Workout Name");
                                    String date = (String) document.get("Date");

                                    TableRow row = new TableRow(findRoutines.this);
                                    row.setId(i);

                                    TableRow.LayoutParams layParams = new TableRow.LayoutParams(
                                            TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);

                                    Log.d(TAG, document.getId() + " Workout Name: " + document.get("Workout Name") + " Date Created: " + document.get("Date") + "Unique ID; " + i);


                                    Button workoutBtn = new Button(findRoutines.this);
                                    workoutBtn.setId(i);
                                    workoutBtn.setText(workoutName);
                                    workoutBtn.setTextSize(20);
                                    workoutBtn.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
                                    workoutBtn.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                                    workoutBtn.setTypeface(getResources().getFont(R.font.convergence));
                                    workoutBtn.setTextColor(getResources().getColor(android.R.color.black));
                                    workoutBtn.setPadding(50, 0, 5, 0);
                                    workoutBtn.setLayoutParams(layParams);
                                    layParams.weight = 1;

                                    TextView dates = new TextView(findRoutines.this);
                                    dates.setId(i);
                                    dates.setText(date);
                                    dates.setTypeface(getResources().getFont(R.font.convergence));
                                    dates.setTextColor(getResources().getColor(android.R.color.black));
                                    dates.setPadding(5, 5, 50, 0);

                                    workoutBtn.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Intent intent = new Intent(getApplicationContext(), workoutClicked.class);
                                            intent.putExtra("Document_ID", document.getId());
                                            startActivity(intent);

                                        }
                                    });

                                    row.addView(workoutBtn);
                                    row.addView(dates);
                                    aTableLayout.addView(row);

                                }

                                    Log.d(TAG, "Exercise 2 is: " + document.get("Exercise 2") + "  Search is currently: " + search);

                                    if (Objects.equals(ex2, search)) {

                                        int i = View.generateViewId();

                                        String workoutName = (String) document.get("Workout Name");
                                        String date = (String) document.get("Date");

                                        TableRow row = new TableRow(findRoutines.this);
                                        row.setId(i);

                                        TableRow.LayoutParams layParams = new TableRow.LayoutParams(
                                                TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);

                                        Log.d(TAG, document.getId() + " Workout Name: " + document.get("Workout Name") + " Date Created: " + document.get("Date") + "Unique ID; " + i);


                                        Button workoutBtn = new Button(findRoutines.this);
                                        workoutBtn.setId(i);
                                        workoutBtn.setText(workoutName);
                                        workoutBtn.setTextSize(20);
                                        workoutBtn.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
                                        workoutBtn.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                                        workoutBtn.setTypeface(getResources().getFont(R.font.convergence));
                                        workoutBtn.setTextColor(getResources().getColor(android.R.color.black));
                                        workoutBtn.setPadding(50, 0, 5, 0);
                                        workoutBtn.setLayoutParams(layParams);
                                        layParams.weight = 1;

                                        TextView dates = new TextView(findRoutines.this);
                                        dates.setId(i);
                                        dates.setText(date);
                                        dates.setTypeface(getResources().getFont(R.font.convergence));
                                        dates.setTextColor(getResources().getColor(android.R.color.black));
                                        dates.setPadding(5, 5, 50, 0);

                                        workoutBtn.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                Intent intent = new Intent(getApplicationContext(), workoutClicked.class);
                                                intent.putExtra("Document_ID", document.getId());
                                                startActivity(intent);

                                            }
                                        });

                                        row.addView(workoutBtn);
                                        row.addView(dates);
                                        aTableLayout.addView(row);


                                        Log.d(TAG, "Exercises do not match the search criteria  (after 1st Else", task.getException());
                                        Log.d(TAG, "Exercise 3 is: " + document.get("Exercise 3"));

                                    }
                                            if (Objects.equals(ex3, search)) {

                                                int i = View.generateViewId();

                                                String workoutName = (String) document.get("Workout Name");
                                                String date = (String) document.get("Date");

                                                TableRow row = new TableRow(findRoutines.this);
                                                row.setId(i);

                                                TableRow.LayoutParams layParams = new TableRow.LayoutParams(
                                                        TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);

                                                Log.d(TAG, document.getId() + " Workout Name: " + document.get("Workout Name") + " Date Created: " + document.get("Date") + "Unique ID; " + i);


                                                Button workoutBtn = new Button(findRoutines.this);
                                                workoutBtn.setId(i);
                                                workoutBtn.setText(workoutName);
                                                workoutBtn.setTextSize(20);
                                                workoutBtn.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
                                                workoutBtn.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                                                workoutBtn.setTypeface(getResources().getFont(R.font.convergence));
                                                workoutBtn.setTextColor(getResources().getColor(android.R.color.black));
                                                workoutBtn.setPadding(50, 0, 5, 0);
                                                workoutBtn.setLayoutParams(layParams);
                                                layParams.weight = 1;

                                                TextView dates = new TextView(findRoutines.this);
                                                dates.setId(i);
                                                dates.setText(date);
                                                dates.setTypeface(getResources().getFont(R.font.convergence));
                                                dates.setTextColor(getResources().getColor(android.R.color.black));
                                                dates.setPadding(5, 5, 50, 0);

                                                workoutBtn.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View view) {
                                                        Intent intent = new Intent(getApplicationContext(), workoutClicked.class);
                                                        intent.putExtra("Document_ID", document.getId());
                                                        startActivity(intent);

                                                    }
                                                });

                                                row.addView(workoutBtn);
                                                row.addView(dates);
                                                aTableLayout.addView(row);


                                                Log.d(TAG, "Exercises do not match the search criteria. after 2nd Else", task.getException());
                                    }}


                            Log.d(TAG, "Exercises do not match the search criteria", task.getException());

                            Log.d(TAG, "Not producing table");
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

            };
    });
        bHomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), homePage.class));
            }
        });

    }}