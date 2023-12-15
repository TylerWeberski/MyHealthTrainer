package com.example.myhealthtrainer;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

/**
 * @author  Ricky Smith
 *
 * EnterWorkoutFieldActivity class is responsible for managing the user interface
 * to enter details about a workout field, such as the type of workout and the
 * number of workouts to perform.
 */
public class EnterWorkoutFieldActivity extends AppCompatActivity {

    private Button saveWorkout;
    private Button viewWorkout;
    private Spinner workoutField;
    private TextView numWorkouts;
    private FirebaseFirestore db;

    /**
     * Called when the activity is first created. Responsible for initializing UI components,
     * setting up event listeners, and configuring the Firebase Firestore instance.
     *
     * @param savedInstanceState The saved state of the activity, if available.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        FrameLayout frameLayout = new FrameLayout(this);
        setContentView(frameLayout);

        LayoutInflater inflater = LayoutInflater.from(this);
        View childView = inflater.inflate(R.layout.activity_enter_workout_field, frameLayout, false);
        frameLayout.addView(childView);

        saveWorkout = findViewById(R.id.saveWorkout);
        viewWorkout = findViewById(R.id.viewWorkouts);
        workoutField = findViewById(R.id.spinnerWorkoutField);
        numWorkouts = findViewById(R.id.numWorkouts);

        db = FirebaseFirestore.getInstance();

         /**
         * Handles the onClick event for the "View Workouts" button, navigating to the
         * WorkoutHistoryActivity to display previously entered workout details.
         */
        viewWorkout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(EnterWorkoutFieldActivity.this, WorkoutHistoryActivity.class);
                startActivity(intent);
            }
        });

        /**
         * Handles the onClick event for the "Save Workout" button, validating user input
         * and initiating the process to save workout details or displaying appropriate
         * error messages.
         */
        saveWorkout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                saveWorkoutDetails();
            }
        });
    }

    /**
     * Saves workout details by extracting the workout name and the number of workouts
     * from the UI. Validates the input and navigates to the InputWorkoutActivity to
     * enter workout details or displays error messages.
     */
    private void saveWorkoutDetails(){
        String workoutNameStr = workoutField.getSelectedItem().toString();
        String numWorkoutsStr = numWorkouts.getText().toString().trim();

        if(!(numWorkoutsStr.matches("[0-9]+"))){
            Toast.makeText(EnterWorkoutFieldActivity.this, "Enter number of workouts must be a number", Toast.LENGTH_LONG).show();
        }
        else if (!(workoutNameStr.isEmpty() || numWorkoutsStr.isEmpty())){
            Intent intent = new Intent(EnterWorkoutFieldActivity.this, InputWorkoutActivity.class);
            intent.putExtra("workoutField", workoutNameStr);
            intent.putExtra("numWorkoutsStr", numWorkoutsStr);
            Toast.makeText(EnterWorkoutFieldActivity.this, "Entering Workouts for field", Toast.LENGTH_LONG).show();
            startActivity(intent);
        }
        else{
            Toast.makeText(EnterWorkoutFieldActivity.this, "Please do not leave a section empty", Toast.LENGTH_LONG).show();
        }

        numWorkouts.setText("");

        finish();
    }
}