package com.example.myhealthtrainer;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myhealthtrainer.adapter.WorkoutAdapter;
import com.example.myhealthtrainer.model.workout;
import com.example.myhealthtrainer.viewmodel.MainActivityViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ricky Smith
 * WorkoutHistoryActivity class is responsible for displaying the workout history
 * based on various workout types, such as Push, Pull, Arms, Legs, Core, or All.
 */
public class WorkoutHistoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private RadioGroup radioGroup;
    private WorkoutAdapter workoutAdapter;
    private List<workout> workoutList;

    private FirebaseFirestore firestore;
    private CollectionReference workoutCollection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_history);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        workoutList = new ArrayList<>();
        workoutAdapter = new WorkoutAdapter(this, workoutList);
        recyclerView.setAdapter(workoutAdapter);
        firestore = FirebaseFirestore.getInstance();
        workoutCollection = firestore.collection("users/" + MainActivityViewModel.getUser().getUid() + "/workouts");
        radioGroup = findViewById(R.id.radioGroupField);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                handleWorkoutFilterChange(checkedId);
            }
        });

        //loadDataFromFirestore();

    }

    /**
     * Handles the change in workout type selection in the radio group and triggers
     * the appropriate data loading based on the selected workout type.
     *
     * @param checkedId The ID of the selected radio button.
     */
    private void handleWorkoutFilterChange(int checkedId) {
        String selectedWorkoutType = getSelectedWorkoutType(checkedId);

        if ("All".equals(selectedWorkoutType)) {
            workoutList.clear();
            loadDataFromFirestore();
        } else if ("Push".equals(selectedWorkoutType)){
            workoutList.clear();
            loadFilteredDataFromFirestore("Push (Chest/Triceps/Front-Delts)");
        } else if ("Pull".equals(selectedWorkoutType)) {
            workoutList.clear();
            loadFilteredDataFromFirestore("Pull (Back/Biceps/Lats/Serratus)");
        } else if ("Arms".equals(selectedWorkoutType)) {
            workoutList.clear();
            loadFilteredDataFromFirestore("Arms (Biceps/Triceps/Forearms/Delts)");
        } else if ("Legs".equals(selectedWorkoutType)) {
            workoutList.clear();
            loadFilteredDataFromFirestore("Legs (Quads/Hams/Calve)");
        } else if ("Core".equals(selectedWorkoutType)) {
            workoutList.clear();
            loadFilteredDataFromFirestore("Core (Abs/Obliques/Diaphragm)");
        }
    }
    /**
     * Retrieves the text of the selected workout type from the radio group.
     *
     * @param checkedId The ID of the selected radio button.
     * @return The text of the selected workout type.
     */
    private String getSelectedWorkoutType(int checkedId) {
        RadioButton selectedRadioButton = findViewById(checkedId);
        return selectedRadioButton != null ? selectedRadioButton.getText().toString() : "";
    }

    /**
     * Loads all workout data from Firestore and updates the RecyclerView adapter.
     */
    private void loadDataFromFirestore() {
        workoutCollection.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String workoutField = document.getString("workoutField");
                        String exercise = document.getString("workoutName");
                        String sets = document.getString("sets");
                        String reps = document.getString("reps");
                        String weight = document.getString("weight");
                        String repGoal = document.getString("repGoal");
                        String setGoal = document.getString("setGoal");


                        workout Workout = new workout(workoutField,exercise, weight, reps, sets, repGoal,setGoal);
                        workoutList.add(Workout);
                    }
                    workoutAdapter.notifyDataSetChanged();
                } else {
                    // Handle errors
                }
            }
        });
    }


    /**
     * Loads filtered workout data from Firestore based on the specified workout type
     * and updates the RecyclerView adapter.
     *
     * @param workoutType The type of workout to filter.
     */
    private void loadFilteredDataFromFirestore(String workoutType) {
        firestore.collection("users/" + MainActivityViewModel.getUser().getUid() + "/workouts")
                .whereEqualTo("workoutField", workoutType)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String workoutField = document.getString("workoutField");
                                String exercise = document.getString("workoutName");
                                String sets = document.getString("sets");
                                String reps = document.getString("reps");
                                String weight = document.getString("weight");
                                String repGoal = document.getString("repGoal");
                                String setGoal = document.getString("setGoal");


                                workout Workout = new workout(workoutField,exercise, weight, reps, sets, repGoal,setGoal);
                                workoutList.add(Workout);
                            }
                            workoutAdapter.notifyDataSetChanged();
                        } else {
                            // Handle errors
                        }
                    }
                });
    }


}
