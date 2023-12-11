package com.example.myhealthtrainer;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myhealthtrainer.model.workout;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;



public class InputWorkoutActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private EditText editTextSets, editTextReps, editTextWeight, editTextWorkout;
    private String workoutDate;
    private ImageButton backButton;
    private String numWorkouts;
    private String workoutField;
    private int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FrameLayout frameLayout = new FrameLayout(this);
        setContentView(frameLayout);

        LayoutInflater inflater = LayoutInflater.from(this);
        View childView = inflater.inflate(R.layout.activity_input_workout, frameLayout, false);
        frameLayout.addView(childView);

        db = FirebaseFirestore.getInstance();

        Intent intent = getIntent();
        numWorkouts = intent.getStringExtra("numWorkoutsStr");
        workoutField = intent.getStringExtra("workoutField");


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        workoutDate = sdf.format(new Date());

        editTextSets = findViewById(R.id.editTextSets);
        editTextReps = findViewById(R.id.editTextReps);
        editTextWeight = findViewById(R.id.editTextWeight);
        editTextWorkout = findViewById(R.id.editTextWorkout);
        backButton = findViewById(R.id.backButton);

        backButton.setOnClickListener(v -> {
            finish();
        });


        Button buttonSave = findViewById(R.id.buttonSave);
        Button workoutHistoryButton = findViewById(R.id.workoutHistory);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveWorkout();
            }
        });

        workoutHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(InputWorkoutActivity.this, WorkoutHistoryActivity.class));
            }
        });
    }


    private void saveWorkout() {

        String sets = editTextSets.getText().toString();
        String reps = editTextReps.getText().toString();
        String weight = editTextWeight.getText().toString();
        String workout = editTextWorkout.getText().toString();

        if(i >= Integer.parseInt(numWorkouts) - 1){

        }else if (!(sets.matches("[0-9]+") || reps.matches("[0-9]+") || weight.matches("[0-9]+"))){

            Toast.makeText(InputWorkoutActivity.this, "Sets,Weight,Reps must be only numbers", Toast.LENGTH_LONG).show();

        } else if (!(sets.isEmpty() || reps.isEmpty() || weight.isEmpty() || workout.isEmpty())) {

            workout newWorkout = new workout(workout, weight, reps, sets);

            Map<String, Object> workoutMap = new HashMap<>();
            workoutMap.put("workoutName", workout);
            workoutMap.put("weight", weight);
            workoutMap.put("sets", sets);
            workoutMap.put("reps", reps);

            Object workoutName;
            db.collection("workout")
                    .add(workoutMap)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NotNull Exception e) {
                            Log.w(TAG, "Error adding document", e);
                        }
                    });

            Toast.makeText(InputWorkoutActivity.this, "Workout saved", Toast.LENGTH_LONG).show();
            editTextSets.setText("");
            editTextReps.setText("");
            editTextWorkout.setText("");
            editTextWeight.setText("");

        } else { Toast.makeText(InputWorkoutActivity.this, "Please do not leave a section empty", Toast.LENGTH_LONG).show(); }
    }
}
