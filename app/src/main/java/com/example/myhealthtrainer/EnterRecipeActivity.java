package com.example.myhealthtrainer;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

/**
 * EnterRecipeActivity class represents an activity for entering recipe details.
 */
public class EnterRecipeActivity extends AppCompatActivity {

    // UI components
    private Button saveRecipe;
    private Button viewRecipes;
    private TextView recipeName;
    private TextView numFoods;
    private ImageButton backButton;
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
        View childView = inflater.inflate(R.layout.activity_enter_recipe, frameLayout, false);
        frameLayout.addView(childView);

        // Initialize UI components
        saveRecipe = findViewById(R.id.saveRecipe);
        viewRecipes = findViewById(R.id.viewRecipes);
        recipeName = findViewById(R.id.recipeName);
        numFoods = findViewById(R.id.numFoods);
        backButton = findViewById(R.id.backButton);

        // Set listener for the back button
        backButton.setOnClickListener(v -> {
            finish();
        });

        // Initialize Firestore database
        db = FirebaseFirestore.getInstance();

        // Set listener for the saveRecipe button
        saveRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveRecipeDetails();
            }
        });

        // Set listener for the viewRecipes button
        viewRecipes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EnterRecipeActivity.this, RecipeListActivity.class));
            }
        });
    }

    /**
     * Saves the entered recipe details and navigates to the EnterRecipeFoodActivity.
     * Displays error messages for invalid input.
     */
    private void saveRecipeDetails() {
        String recipeNameStr = recipeName.getText().toString().trim();
        String numFoodsStr = numFoods.getText().toString().trim();

        // Validate if numFoodsStr is a number
        if (!(numFoodsStr.matches("[0-9]+"))) {
            Toast.makeText(EnterRecipeActivity.this, "Enter number of foods must be a number", Toast.LENGTH_LONG).show();
        } else if (!(recipeNameStr.isEmpty() || numFoodsStr.isEmpty())) {
            // Proceed to EnterRecipeFoodActivity if inputs are valid
            Intent intent = new Intent(EnterRecipeActivity.this, EnterRecipeFoodActivity.class);
            intent.putExtra("recipeName", recipeNameStr);
            intent.putExtra("numFoodsStr", numFoodsStr);
            Toast.makeText(EnterRecipeActivity.this, "Entering Foods for Recipe", Toast.LENGTH_LONG).show();
            startActivity(intent);
        } else {
            // Display error message for empty fields
            Toast.makeText(EnterRecipeActivity.this, "Please do not leave a section empty", Toast.LENGTH_LONG).show();
        }

        // Clear input fields and finish the activity
        numFoods.setText("");
        recipeName.setText("");
        finish();
    }
}