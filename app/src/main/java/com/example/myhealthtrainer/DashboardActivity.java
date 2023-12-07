package com.example.myhealthtrainer;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DashboardActivity extends AppCompatActivity {

    private Button workoutTracker;
    private Button foodButton;
    private Button macrosButton;
    private Button calculateRMRButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout frameLayout = new FrameLayout(this);
        setContentView(frameLayout);

        LayoutInflater inflater = LayoutInflater.from(this);
        View childView = inflater.inflate(R.layout.activity_dashboard, frameLayout, false);
        frameLayout.addView(childView);

        workoutTracker = findViewById(R.id.workoutButton);
        foodButton = findViewById(R.id.foodButton);
        macrosButton = findViewById(R.id.macrosButton);
        calculateRMRButton = findViewById(R.id.buttonCalculateRMR);

       workoutTracker.setOnClickListener(v -> {
            Toast.makeText(DashboardActivity.this, "Going to workout", Toast.LENGTH_LONG).show();
            startActivity(new Intent(DashboardActivity.this, InputWorkoutActivity.class));
        });
        foodButton.setOnClickListener(v -> {
            Toast.makeText(DashboardActivity.this, "Going to enter food", Toast.LENGTH_LONG).show();
            startActivity(new Intent(DashboardActivity.this, EnterFoodActivity.class));
        });
        macrosButton.setOnClickListener(v -> {
            Toast.makeText(DashboardActivity.this, "Going to Macros", Toast.LENGTH_LONG).show();
            startActivity(new Intent(DashboardActivity.this, MacrosActivity.class));
        });
        calculateRMRButton.setOnClickListener(v -> {
            Toast.makeText(DashboardActivity.this, "Going to RMR Calculator", Toast.LENGTH_LONG).show();
            startActivity(new Intent(DashboardActivity.this, CalculateRMRActivity.class));
        });
    }



}