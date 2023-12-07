package com.example.myhealthtrainer;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myhealthtrainer.viewmodel.MainActivityViewModel;

public class DashboardActivity extends AppCompatActivity {

    private Button workoutTracker;
    private Button foodButton;
    private Button macrosButton;

    private ImageButton btnDeleteAccount;
    private int deleteAccountStatus = 0;

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
        btnDeleteAccount = findViewById(R.id.btnDeleteAccount);

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


        btnDeleteAccount.setOnClickListener(v -> {
            showConfirmationDialog();

            if (deleteAccountStatus == 1)
            {
                startActivity(new Intent(DashboardActivity.this, MainActivity.class));
                MainActivityViewModel.getUser().delete();
                Toast.makeText(this, "Deleted user", Toast.LENGTH_LONG).show();
            }
        });
      
        calculateRMRButton.setOnClickListener(v -> {
            Toast.makeText(DashboardActivity.this, "Going to RMR Calculator", Toast.LENGTH_LONG).show();
            startActivity(new Intent(DashboardActivity.this, CalculateRMRActivity.class));
          
        });
    }

    private void showConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmation");
        builder.setMessage("Are you sure you would like to delete your Account?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Handle "Yes" button click
                dialog.dismiss();
                deleteAccountStatus = 1;

            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Handle "No" button click
                dialog.dismiss();
                deleteAccountStatus = 0;
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}

