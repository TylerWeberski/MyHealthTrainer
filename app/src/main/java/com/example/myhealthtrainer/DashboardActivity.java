package com.example.myhealthtrainer;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myhealthtrainer.viewmodel.MainActivityViewModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class DashboardActivity extends AppCompatActivity {

    private Button workoutTracker;
    private Button foodButton;
    private Button macrosButton;
    private Button recipeButton;
    private Button calculateRMR;
    private TextView txtDashHello;
    private TextView txtGoalProgress;
    private ImageButton btnDeleteAccount;
    private int deleteAccountTracker;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout frameLayout = new FrameLayout(this);
        setContentView(frameLayout);

        LayoutInflater inflater = LayoutInflater.from(this);
        View childView = inflater.inflate(R.layout.activity_dashboard, frameLayout, false);
        frameLayout.addView(childView);
        db = FirebaseFirestore.getInstance();

        dashInit();
        deleteAccountTracker = 0;

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
        recipeButton.setOnClickListener(v ->{
            Toast.makeText(DashboardActivity.this, "Going to enter Recipe", Toast.LENGTH_LONG).show();
            startActivity(new Intent(DashboardActivity.this, EnterRecipeActivity.class));
        });
        calculateRMR.setOnClickListener(v ->{
            Toast.makeText(DashboardActivity.this, "Going to RMR calculator ", Toast.LENGTH_LONG).show();
            startActivity(new Intent(DashboardActivity.this, CalculateRMRActivity.class));
        });

        btnDeleteAccount.setOnClickListener(v ->{
            showConfirmationDialog();

            if (deleteAccountTracker != 0)
            {
                startActivity(new Intent(DashboardActivity.this, MainActivity.class));
                MainActivityViewModel.getUser().delete();
            }

        });

    }

    private void dashInit()
    {
        workoutTracker = findViewById(R.id.workoutButton);
        foodButton = findViewById(R.id.foodButton);
        macrosButton = findViewById(R.id.macrosButton);
        recipeButton = findViewById(R.id.recipeButton);
        calculateRMR = findViewById(R.id.buttonCalculateRMR);
        btnDeleteAccount = findViewById(R.id.btnDeleteAccount);
        txtGoalProgress = findViewById(R.id.txtGoalInfo);
        txtDashHello = findViewById(R.id.txtDashGoals);

        txtDashHello.setText(MainActivityViewModel.getUser().getDisplayName().toString().split(" ")[0] + "'s Dashboard");

        updateGoalProgress();


    }

    private void showConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmation");
        builder.setMessage("Do you want to delete your account?");

        // Set up the buttons
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Handle the Yes button click
                deleteAccountTracker = 1;
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Handle the No button click
                deleteAccountTracker = 0;
                dialog.dismiss();
            }
        });

        // Create and show the dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void updateGoalProgress()
    {
        DocumentReference userRef = db.collection("users").document(MainActivityViewModel.getUser().getUid());
        StringBuilder goalBuilder = new StringBuilder();
        goalBuilder.append(txtGoalProgress.getText().toString()).append("\n\n");
        userRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if ( documentSnapshot.exists() )
                {
                    if (Integer.parseInt(documentSnapshot.get("goalCalories").toString()) != 0)
                    {
                        goalBuilder.append("Calories: ").append( String.format("%.2f",(Double.parseDouble(documentSnapshot.get("currentCalories").toString()) / Double.parseDouble(documentSnapshot.get("goalCalories").toString())) * 100)).append("%\n");
                    }
                    else
                    {
                        goalBuilder.append("Calories: 0%\n");
                    }

                    if (Integer.parseInt(documentSnapshot.get("goalCarbs").toString()) != 0)
                    {
                        goalBuilder.append("Carbs: ").append( String.format("%.2f", (Double.parseDouble(documentSnapshot.get("currentCarbs").toString()) / Double.parseDouble(documentSnapshot.get("goalCarbs").toString())) * 100)).append("%\n");
                    }
                    else
                    {
                        goalBuilder.append("Carbs: 0%\n");
                    }

                    if (Integer.parseInt(documentSnapshot.get("goalFat").toString()) != 0)
                    {
                        goalBuilder.append("Fat: ").append( String.format("%.2f", (Double.parseDouble(documentSnapshot.get("currentFat").toString()) / Double.parseDouble(documentSnapshot.get("goalFat").toString())) * 100)).append("%\n");
                    }
                    else
                    {
                        goalBuilder.append("Fat: 0%\n");
                    }

                    if (Integer.parseInt(documentSnapshot.get("goalProtein").toString()) != 0)
                    {
                        goalBuilder.append("Protein: ").append( String.format("%.2f", (Double.parseDouble(documentSnapshot.get("currentProtein").toString()) / Double.parseDouble(documentSnapshot.get("goalProtein").toString())) * 100)).append("%\n");
                    }
                    else
                    {
                        goalBuilder.append("Protein: 0%\n");
                    }

                    if (Integer.parseInt(documentSnapshot.get("goalSodium").toString()) != 0)
                    {
                        goalBuilder.append("Sodium: ").append( String.format("%.2f", (Double.parseDouble(documentSnapshot.get("currentSodium").toString()) / Double.parseDouble(documentSnapshot.get("goalSodium").toString())) * 100)).append("%\n");
                    }
                    else
                    {
                        goalBuilder.append("Sodium: 0%\n");
                    }

                    if (Integer.parseInt(documentSnapshot.get("goalSugar").toString()) != 0)
                    {
                        goalBuilder.append("Sugar: ").append( String.format("%.2f", (Double.parseDouble(documentSnapshot.get("currentSugar").toString()) / Double.parseDouble(documentSnapshot.get("goalSugar").toString())) * 100)).append("%\n");
                    }
                    else
                    {
                        goalBuilder.append("Sugar: 0%\n");
                    }

                    txtGoalProgress.setText(goalBuilder);


                }

            }
        });
    }



}