package com.example.myhealthtrainer;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.myhealthtrainer.model.food;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class EnterFoodActivity extends AppCompatActivity {

    private EditText etFoodName, etTotalCalories, etTotalFat, etSodium, etTotalCarbs, etTotalSugar, etProtein;
    private Button btnSave;
    private FirebaseFirestore db;
    private ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout frameLayout = new FrameLayout(this);
        setContentView(frameLayout);

        LayoutInflater inflater = LayoutInflater.from(this);
        View childView = inflater.inflate(R.layout.activity_enter_food, frameLayout, false);
        frameLayout.addView(childView);

        db = FirebaseFirestore.getInstance();

        etFoodName = findViewById(R.id.etFoodName);
        etTotalCalories = findViewById(R.id.etTotalCalories);
        etTotalFat = findViewById(R.id.etTotalFat);
        etSodium = findViewById(R.id.etSodium);
        etTotalCarbs = findViewById(R.id.etTotalCarbs);
        etTotalSugar = findViewById(R.id.etTotalSugar);
        etProtein = findViewById(R.id.etProtein);
        btnSave = findViewById(R.id.btnSave);
        backButton = findViewById(R.id.backButton);


        backButton.setOnClickListener(v -> {
            finish();
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateAndSaveFood();
            }
        });
    }
    private void validateAndSaveFood() {
        String foodName = etFoodName.getText().toString().trim();

        if (foodName.isEmpty()) {
            // Show error message if food name is empty
            Toast.makeText(EnterFoodActivity.this, "Please enter a food name", Toast.LENGTH_SHORT).show();
        } else {
            // Continue with saving food details to Firebase
            saveFood();
        }
    }
    private void saveFood() {
        String foodName = etFoodName.getText().toString().trim();
        String totalCalories = etTotalCalories.getText().toString().trim();
        String totalFat = etTotalFat.getText().toString().trim();
        String sodium = etSodium.getText().toString().trim();
        String totalCarbs = etTotalCarbs.getText().toString().trim();
        String totalSugar = etTotalSugar.getText().toString().trim();
        String protein = etProtein.getText().toString().trim();

        food newFood = new food(foodName, Integer.parseInt(totalCalories), Integer.parseInt(totalFat),
                Integer.parseInt(sodium), Integer.parseInt(totalCarbs),
                Integer.parseInt(totalSugar), Integer.parseInt(protein));

        Map<String, Object> foodMap = new HashMap<>();
        foodMap.put("foodName", foodName);
        foodMap.put("calories", Integer.parseInt(totalCalories));
        foodMap.put("fat", Integer.parseInt(totalFat));
        foodMap.put("sodium", Integer.parseInt(sodium));
        foodMap.put("carbs", Integer.parseInt(totalCarbs));
        foodMap.put("sugar", Integer.parseInt(totalSugar));
        foodMap.put("protein", Integer.parseInt(protein));

        db.collection("food")
                .add(foodMap)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(EnterFoodActivity.this, "Food Details Saved", Toast.LENGTH_SHORT).show();
                        clearFields(); // Clear fields after successful save
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NotNull Exception e) {
                        Toast.makeText(EnterFoodActivity.this, "Failed to save food details", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
    }


    private void clearFields() {
        // Clear EditText fields after saving
        etFoodName.setText("");
        etTotalCalories.setText("");
        etTotalFat.setText("");
        etSodium.setText("");
        etTotalCarbs.setText("");
        etTotalSugar.setText("");
        etProtein.setText("");
    }

}