package com.example.myhealthtrainer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout frameLayout = new FrameLayout(this);
        setContentView(frameLayout);

        LayoutInflater inflater = LayoutInflater.from(this);
        View childView = inflater.inflate(R.layout.activity_enter_food, frameLayout, false);
        frameLayout.addView(childView);

        db = FirebaseFirestore.getInstance();

        etFoodName = findViewById(R.id.foodName);
        etTotalCalories = findViewById(R.id.totalCalories);
        etTotalFat = findViewById(R.id.totalFat);
        etSodium = findViewById(R.id.totalSodium);
        etTotalCarbs = findViewById(R.id.totalCarbs);
        etTotalSugar = findViewById(R.id.totalSugar);
        etProtein = findViewById(R.id.totalProtein);
        btnSave = findViewById(R.id.addFood);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveFood();
            }
        });
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