package com.example.myhealthtrainer;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MacrosActivity extends Activity {

    private TextView currentMacros, calories, currCalories, totalFat, currFat, sodium, currSodium, totalCarbs, currCarbs, totalSugar, currSugar, protein, currProtein, macroGoals, ph1, ph2, ph3, ph4, ph5, ph6;

    private EditText goalCalories, goalFat, goalSodium, goalCarbs, goalSugar, goalProtein;

    private Button btnReset, btnSave;
    private ImageButton backButton;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FrameLayout frameLayout = new FrameLayout(this);
        setContentView(frameLayout);

        LayoutInflater inflater = LayoutInflater.from(this);
        View childView = inflater.inflate(R.layout.activity_macros , frameLayout, false);
        frameLayout.addView(childView);

        db = FirebaseFirestore.getInstance();

        // Finding TextViews from the layout
        currentMacros = findViewById(R.id.CurrentMacros);
        calories = findViewById(R.id.Calories);
        currCalories = findViewById(R.id.CurrCalories);
        totalFat = findViewById(R.id.TotalFat);
        currFat = findViewById(R.id.CurrFat);
        sodium = findViewById(R.id.Sodium);
        currSodium = findViewById(R.id.CurrSodium);
        totalCarbs = findViewById(R.id.TotalCarbs);
        currCarbs = findViewById(R.id.CurrCarbs);
        totalSugar = findViewById(R.id.TotalSugar);
        currSugar = findViewById(R.id.CurrSugar);
        protein = findViewById(R.id.Protein);
        currProtein = findViewById(R.id.CurrProtein);
        macroGoals = findViewById(R.id.MacroGoals);
        ph1 = findViewById(R.id.ph1);
        ph2 = findViewById(R.id.ph2);
        ph3 = findViewById(R.id.ph3);
        ph4 = findViewById(R.id.ph4);
        ph5 = findViewById(R.id.ph5);
        ph6 = findViewById(R.id.ph6);

        // Finding EditTexts from the layout
        goalCalories = findViewById(R.id.GoalCalories);
        goalFat = findViewById(R.id.GoalFat);
        goalSodium = findViewById(R.id.GoalSodium);
        goalCarbs = findViewById(R.id.GoalCarbs);
        goalSugar = findViewById(R.id.GoalSugar);
        goalProtein = findViewById(R.id.GoalProtein);

        // Finding Buttons from the layout
        btnReset = findViewById(R.id.btnReset);
        btnSave = findViewById(R.id.btnSave);
        backButton = findViewById(R.id.backButton);

        backButton.setOnClickListener(v -> {
            finish();
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //saveGoals();
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearFields();
            }
        });

    }

    /*private void saveGoals() {
        String totalCalories = goalCalories.getText().toString().trim();
        String totalFat = goalFat.getText().toString().trim();
        String sodium = goalSodium.getText().toString().trim();
        String totalCarbs = goalCarbs.getText().toString().trim();
        String totalSugar = goalSugar.getText().toString().trim();
        String protein = goalProtein.getText().toString().trim();

        // Replace 'Users' with your actual model class for users
        Users userGoals = new Users(
                Integer.parseInt(totalCalories),
                Integer.parseInt(totalFat),
                Integer.parseInt(sodium),
                Integer.parseInt(totalCarbs),
                Integer.parseInt(totalSugar),
                Integer.parseInt(protein)
        );

        Map<String, Object> goalsMap = new HashMap<>();
        goalsMap.put("goalCalories", Integer.parseInt(totalCalories));
        goalsMap.put("goalFat", Integer.parseInt(totalFat));
        goalsMap.put("goalSodium", Integer.parseInt(sodium));
        goalsMap.put("goalCarbs", Integer.parseInt(totalCarbs));
        goalsMap.put("goalSugar", Integer.parseInt(totalSugar));
        goalsMap.put("goalProtein", Integer.parseInt(protein));

        // Replace 'db' with your Firestore instance
        db.collection("users")
                .add(goalsMap)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(MacrosActivity.this, "Goals Saved", Toast.LENGTH_SHORT).show();
                    // Add any necessary actions after successful save
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(MacrosActivity.this, "Failed to save goals", Toast.LENGTH_SHORT).show();
                });
    }*/

    private void clearFields() {
        macroGoals.setText("");
        goalCalories.setText("");
        goalFat.setText("");
        goalSodium.setText("");
        goalCarbs.setText("");
        goalSugar.setText("");
        goalProtein.setText("");
    }

}
