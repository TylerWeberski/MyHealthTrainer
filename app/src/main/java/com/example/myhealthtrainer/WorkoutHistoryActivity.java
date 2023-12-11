package com.example.myhealthtrainer;
import android.os.Bundle;
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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class WorkoutHistoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
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

        loadDataFromFirestore();
    }

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
}
