package com.example.myhealthtrainer;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Map;

public class WorkoutHistoryActivity extends AppCompatActivity {

    private TableLayout tableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_history);

        tableLayout = findViewById(R.id.tableLayout);

        // Load and display workout history
        displayWorkoutHistory();
    }

    private void displayWorkoutHistory() {
        // Retrieve workout history from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("WorkoutPrefs", MODE_PRIVATE);
        Map<String, ?> allEntries = sharedPreferences.getAll();

        // Iterate through the entries and populate the table
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            String workoutDate = entry.getKey();
            String workoutData = entry.getValue().toString();

            // Create a new row
            TableRow row = new TableRow(this);

            // Create date and data TextViews
            TextView dateTextView = new TextView(this);
            dateTextView.setText(workoutDate);

            TextView dataTextView = new TextView(this);
            dataTextView.setText(workoutData);

            // Add TextViews to the row
            row.addView(dateTextView);
            row.addView(dataTextView);

            // Add the row to the table
            tableLayout.addView(row);
        }
    }
}
