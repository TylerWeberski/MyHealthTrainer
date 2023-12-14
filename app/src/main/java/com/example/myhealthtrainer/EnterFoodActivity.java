/**
 * This class represents an activity to enter food details.
 * It allows users to input information about food items and saves the details to Firebase.
 * It also updates the user's current macronutrient values based on the entered food details.
 */
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
import com.example.myhealthtrainer.model.users;
import com.example.myhealthtrainer.viewmodel.MainActivityViewModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class EnterFoodActivity extends AppCompatActivity {

    private EditText etFoodName, etTotalCalories, etTotalFat, etSodium, etTotalCarbs, etTotalSugar, etProtein;
    private Button btnSave;
    private FirebaseFirestore db;
    private ImageButton backButton;

    /**
     * This method initializes the EnterFoodActivity and sets up the UI components.
     * It handles button clicks to save food details or navigate back.
     * @param savedInstanceState The saved instance state Bundle.
     */
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
    /**
     * Validates the entered food name and initiates the process to save food details.
     */
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
    /**
     * Saves the entered food details to Firebase Firestore.
     * Also updates the user's macronutrient values based on the entered food details.
     */
    private void saveFood() {
        String foodName = etFoodName.getText().toString().trim();
        String totalCalories = etTotalCalories.getText().toString().trim();
        String totalFat = etTotalFat.getText().toString().trim();
        String sodium = etSodium.getText().toString().trim();
        String totalCarbs = etTotalCarbs.getText().toString().trim();
        String totalSugar = etTotalSugar.getText().toString().trim();
        String protein = etProtein.getText().toString().trim();

        if (totalCalories.isEmpty()){
            totalCalories = "0";
        }

        if (totalFat.isEmpty()){
            totalFat = "0";
        }

        if (sodium.isEmpty()){
            sodium = "0";
        }

        if (totalCarbs.isEmpty()){
            totalCarbs = "0";
        }

        if (totalSugar.isEmpty()){
            totalSugar = "0";
        }

        if (protein.isEmpty()){
            protein = "0";
        }

        Map<String, Object> foodMap = new HashMap<>();
        foodMap.put("foodName", foodName);
        foodMap.put("calories", Integer.parseInt(totalCalories));
        foodMap.put("fat", Integer.parseInt(totalFat));
        foodMap.put("sodium", Integer.parseInt(sodium));
        foodMap.put("carbs", Integer.parseInt(totalCarbs));
        foodMap.put("sugar", Integer.parseInt(totalSugar));
        foodMap.put("protein", Integer.parseInt(protein));


        db.collection("users/" + MainActivityViewModel.getUser().getUid() + "/food")
                .add(foodMap)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(EnterFoodActivity.this, "Food Details Saved", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NotNull Exception e) {
                        Toast.makeText(EnterFoodActivity.this, "Failed to save food details", Toast.LENGTH_SHORT).show();
                    }
                });
        saveMacros(totalCalories, totalFat, sodium, totalCarbs, totalSugar, protein);
        finish();


    }
    /**
     * Updates the user's macronutrient values in the Firestore database based on the entered food details.
     * @param totalCals    Total calories of the food.
     * @param totalFat     Total fat content of the food.
     * @param sodium       Sodium content of the food.
     * @param totalCarbs   Total carbohydrates content of the food.
     * @param totalSugar   Total sugar content of the food.
     * @param protein      Protein content of the food.
     */
    public void saveMacros(String totalCals, String totalFat, String sodium, String totalCarbs, String totalSugar, String protein){
        DocumentReference userRef = db.collection("users").document(MainActivityViewModel.getUser().getUid());
        userRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if ( documentSnapshot.exists() )
                {
                    int currCals = Integer.parseInt(documentSnapshot.get("currentCalories").toString()) + Integer.parseInt(totalCals);
                    int currCarbs = Integer.parseInt(documentSnapshot.get("currentCarbs").toString()) + Integer.parseInt(totalFat);
                    int currFat = Integer.parseInt(documentSnapshot.get("currentFat").toString()) + Integer.parseInt(sodium);
                    int currProtein = Integer.parseInt(documentSnapshot.get("currentProtein").toString()) + Integer.parseInt(totalCarbs);
                    int currSodium = Integer.parseInt(documentSnapshot.get("currentSodium").toString()) + Integer.parseInt(totalSugar);
                    int currSugar = Integer.parseInt(documentSnapshot.get("currentSugar").toString()) + Integer.parseInt(protein);

                    Map<String, Object> macroMap = new HashMap<>();
                    macroMap.put("currentCalories", currCals);
                    macroMap.put("currentFat", currFat);
                    macroMap.put("currentSodium", currSodium);
                    macroMap.put("currentCarbs", currCarbs);
                    macroMap.put("currentSugar", currSugar);
                    macroMap.put("currentProtein", currProtein);

                    userRef.update(macroMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(EnterFoodActivity.this, "Goals Updated!", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(EnterFoodActivity.this, "FAILURE ON DATA SET", Toast.LENGTH_SHORT).show();
                                }
                            });

                }
            }
        });
    }

}