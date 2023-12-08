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

import com.google.firebase.firestore.FirebaseFirestore;

public class EnterRecipeActivity extends AppCompatActivity {

    private Button saveRecipe;
    private Button viewRecipes;
    private TextView recipeName;
    private TextView numFoods;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        FrameLayout frameLayout = new FrameLayout(this);
        setContentView(frameLayout);

        LayoutInflater inflater = LayoutInflater.from(this);
        View childView = inflater.inflate(R.layout.activity_enter_recipe, frameLayout, false);
        frameLayout.addView(childView);

        saveRecipe = findViewById(R.id.saveRecipe);
        viewRecipes = findViewById(R.id.viewRecipes);
        recipeName = findViewById(R.id.recipeName);
        numFoods = findViewById(R.id.numFoods);

        db = FirebaseFirestore.getInstance();

        saveRecipe.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                saveRecipeDetails();
            }
        });
    }
    private void saveRecipeDetails(){
        String recipeNameStr = recipeName.getText().toString().trim();
        String numFoodsStr = numFoods.getText().toString().trim();

        if(!(numFoodsStr.matches("[0-9]+"))){
            Toast.makeText(EnterRecipeActivity.this, "Enter number of foods must be a number", Toast.LENGTH_LONG).show();
        }
        else if (!(recipeNameStr.isEmpty() || numFoodsStr.isEmpty())){
            Intent intent = new Intent(EnterRecipeActivity.this, EnterRecipeFoodActivity.class);
            intent.putExtra("recipeName", recipeNameStr);
            intent.putExtra("numFoodsStr", numFoodsStr);
            Toast.makeText(EnterRecipeActivity.this, "Entering Foods for Recipe", Toast.LENGTH_LONG).show();
            startActivity(intent);
        }
        else{
            Toast.makeText(EnterRecipeActivity.this, "Please do not leave a section empty", Toast.LENGTH_LONG).show();
        }

        numFoods.setText("");
        recipeName.setText("");
        finish();
    }
}
