package com.example.myhealthtrainer;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myhealthtrainer.viewmodel.MainActivityViewModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * EnterRecipeFoodActivity class represents an activity for entering food details for a recipe.
 */
public class EnterRecipeFoodActivity extends AppCompatActivity {

    // UI components
    private TextView foodName;
    private TextView totalCalories;
    private TextView totalFat;
    private TextView totalSodium;
    private TextView totalCarbs;
    private TextView totalSugar;
    private TextView totalProtein;
    private Button addFood;

    // Activity parameters
    private String numFoodsStr;
    private String recipeName;
    private int i = 0;
    private String ingredientsList = "Ingredients: \n";
    private int numCalories = 0;
    private int numFat = 0;
    private int numSodium = 0;
    private int numCarbs = 0;
    private int numSugar = 0;
    private int numProtein = 0;
    private FirebaseFirestore db;

    /**
     * Called when the activity is first created. Responsible for initializing the activity.
     *
     * @param savedInstanceState A Bundle containing the saved state of the activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout frameLayout = new FrameLayout(this);
        setContentView(frameLayout);

        LayoutInflater inflater = LayoutInflater.from(this);
        View childView = inflater.inflate(R.layout.activity_enter_recipe_food, frameLayout, false);
        frameLayout.addView(childView);

        // Initialize Firestore database
        db = FirebaseFirestore.getInstance();

        // Initialize UI components
        foodName = findViewById(R.id.foodName);
        totalCalories = findViewById(R.id.totalCalories);
        totalFat = findViewById(R.id.totalFat);
        totalSodium = findViewById(R.id.totalSodium);
        totalCarbs = findViewById(R.id.totalCarbs);
        totalSugar = findViewById(R.id.totalSugar);
        totalProtein = findViewById(R.id.totalProtein);
        addFood = findViewById(R.id.addFood);

        // Retrieve data from the previous activity
        Intent intent = getIntent();
        numFoodsStr = intent.getStringExtra("numFoodsStr");
        recipeName = intent.getStringExtra("recipeName");

        // Set listener for the addFood button
        addFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveFoodDetails();
            }
        });
    }

    /**
     * Saves the entered food details, updates totals, and navigates to EnterRecipeActivity.
     * Displays error messages for invalid input.
     */
    private void saveFoodDetails() {
        String foodNameStr = foodName.getText().toString().trim();
        String totalCaloriesStr = totalCalories.getText().toString().trim();
        String totalFatStr = totalFat.getText().toString().trim();
        String totalSodiumStr = totalSodium.getText().toString().trim();
        String totalCarbsStr = totalCarbs.getText().toString().trim();
        String totalSugarStr = totalSugar.getText().toString().trim();
        String totalProteinStr = totalProtein.getText().toString().trim();

        // Check if the entered values are valid
        if (i >= Integer.parseInt(numFoodsStr) - 1) {
            //Final Add food details to the ingredients list and update totals
            ingredientsList = ingredientsList + foodNameStr + "\n";
            numCalories += Integer.parseInt(totalCaloriesStr);
            numFat += Integer.parseInt(totalFatStr);
            numSodium += Integer.parseInt(totalSodiumStr);
            numCarbs += Integer.parseInt(totalCarbsStr);
            numSugar += Integer.parseInt(totalSugarStr);
            numProtein += Integer.parseInt(totalProteinStr);

            // Create a map for the recipe and save it to Firestore database
            Map<String, Object> recipeMap = new HashMap<>();
            recipeMap.put("recipeName", recipeName);
            recipeMap.put("ingredientsList", ingredientsList);
            recipeMap.put("numCalories", numCalories);
            recipeMap.put("numFat", numFat);
            recipeMap.put("numSodium", numSodium);
            recipeMap.put("numCarbs", numCarbs);
            recipeMap.put("numSugar", numSugar);
            recipeMap.put("numProtein", numProtein);

            db.collection("users/" + MainActivityViewModel.getUser().getUid() + "/recipes")
                    .add(recipeMap)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(EnterRecipeFoodActivity.this, "Food Details Saved", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NotNull Exception e) {
                            Toast.makeText(EnterRecipeFoodActivity.this, "Failed to save food details", Toast.LENGTH_SHORT).show();
                        }
                    });

            // Navigate to EnterRecipeActivity
            startActivity(new Intent(EnterRecipeFoodActivity.this, EnterRecipeActivity.class));
        } else if (!(totalCaloriesStr.matches("[0-9]+") || totalFatStr.matches("[0-9]+") ||
                totalSodiumStr.matches("[0-9]+") || totalCarbsStr.matches("[0-9]+") ||
                totalSugarStr.matches("[0-9]+") || totalProteinStr.matches("[0-9]+"))) {
            // Display error message for non-numeric totals
            Toast.makeText(EnterRecipeFoodActivity.this, "All totals must be a number", Toast.LENGTH_LONG).show();
        } else if (!(foodNameStr.isEmpty() || totalCarbsStr.isEmpty() ||
                totalFatStr.isEmpty() || totalSodiumStr.isEmpty() ||
                totalCarbsStr.isEmpty() || totalSugarStr.isEmpty() ||
                totalProteinStr.isEmpty())) {
            // Add food details to the ingredients list and update totals
            ingredientsList = ingredientsList + foodNameStr + "\n";
            numCalories += Integer.parseInt(totalCaloriesStr);
            numFat += Integer.parseInt(totalFatStr);
            numSodium += Integer.parseInt(totalSodiumStr);
            numCarbs += Integer.parseInt(totalCarbsStr);
            numSugar += Integer.parseInt(totalSugarStr);
            numProtein += Integer.parseInt(totalProteinStr);
            i++;
        } else {
            // Display error message for empty fields
            Toast.makeText(EnterRecipeFoodActivity.this, "Please do not leave a section empty", Toast.LENGTH_LONG).show();
        }

        // Clear input fields
        foodName.setText("");
        totalCalories.setText("");
        totalFat.setText("");
        totalSodium.setText("");
        totalCarbs.setText("");
        totalSugar.setText("");
        totalProtein.setText("");
    }
}
