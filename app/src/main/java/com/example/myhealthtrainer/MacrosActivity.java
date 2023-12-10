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

import androidx.annotation.NonNull;

import com.example.myhealthtrainer.viewmodel.MainActivityViewModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
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

        initMacros();

        backButton.setOnClickListener(v -> {
            finish();
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveGoals();
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearFields();
            }
        });

    }

    private void saveGoals() {
        String totalCalories = goalCalories.getText().toString().trim();
        String totalFat = goalFat.getText().toString().trim();
        String sodium = goalSodium.getText().toString().trim();
        String totalCarbs = goalCarbs.getText().toString().trim();
        String totalSugar = goalSugar.getText().toString().trim();
        String protein = goalProtein.getText().toString().trim();

        DocumentReference userRef = db.collection("users").document(MainActivityViewModel.getUser().getUid());

        Map<String, Object> userData = new HashMap<>();
        userData.put("goalCalories", Integer.parseInt(totalCalories));
        userData.put("goalFat", Integer.parseInt(totalFat));
        userData.put("goalSodium", Integer.parseInt(sodium));
        userData.put("goalCarbs", Integer.parseInt(totalCarbs));
        userData.put("goalSugar", Integer.parseInt(totalSugar));
        userData.put("goalProtein", Integer.parseInt(protein));
        userRef.update(userData).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(MacrosActivity.this, "Goals Updated!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MacrosActivity.this, "FAILURE ON DATA SET", Toast.LENGTH_SHORT).show();
                    }
                });

        setMacrosDB();

    }

    private void clearFields() {
        macroGoals.setText("");
        goalCalories.setText("");
        goalFat.setText("");
        goalSodium.setText("");
        goalCarbs.setText("");
        goalSugar.setText("");
        goalProtein.setText("");
    }

    private void initMacros()
    {
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

        setMacrosDB();
    }

    private void setMacrosDB()
    {
        DocumentReference userRef = db.collection("users").document(MainActivityViewModel.getUser().getUid());

        userRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if ( documentSnapshot.exists() )
                {
                    goalCalories.setText(documentSnapshot.get("goalCalories").toString());
                    goalCarbs.setText(documentSnapshot.get("goalCarbs").toString());
                    goalFat.setText(documentSnapshot.get("goalFat").toString());
                    goalProtein.setText(documentSnapshot.get("goalProtein").toString());
                    goalSugar.setText(documentSnapshot.get("goalSugar").toString());
                    goalSodium.setText(documentSnapshot.get("goalSodium").toString());

                    currCalories.setText(documentSnapshot.get("currentCalories").toString());
                    currCarbs.setText(documentSnapshot.get("currentCarbs").toString());
                    currFat.setText(documentSnapshot.get("currentFat").toString());
                    currProtein.setText(documentSnapshot.get("currentProtein").toString());
                    currSodium.setText(documentSnapshot.get("currentSodium").toString());
                    currSugar.setText(documentSnapshot.get("currentSugar").toString());
                }

            }
        });
    }

    private void setCurrentMacros(){

    }

}
