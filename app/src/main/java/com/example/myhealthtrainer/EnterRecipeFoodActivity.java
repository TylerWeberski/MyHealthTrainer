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

public class EnterRecipeFoodActivity extends AppCompatActivity {

    private TextView foodName;
    private TextView totalCalories;
    private TextView totalFat;
    private TextView totalSodium;
    private TextView totalCarbs;
    private TextView totalSugar;
    private TextView totalProtein;
    private Button addFood;
    private String numFoodsStr;
    private int i = 0;
    private String ingredientsList = "Ingredients: + \n";
    private int numCalories = 0;
    private int numFat = 0;
    private int numSodium = 0;
    private int numCarbs = 0;
    private int numSugar = 0;
    private int numProtein = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout frameLayout = new FrameLayout(this);
        setContentView(frameLayout);

        LayoutInflater inflater = LayoutInflater.from(this);
        View childView = inflater.inflate(R.layout.activity_enter_recipe_food, frameLayout, false);
        frameLayout.addView(childView);

        foodName = findViewById(R.id.foodName);
        totalCalories = findViewById(R.id.totalCalories);
        totalFat = findViewById(R.id.totalFat);
        totalSodium = findViewById(R.id.totalSodium);
        totalCarbs = findViewById(R.id.totalCarbs);
        totalSugar = findViewById(R.id.totalSugar);
        totalProtein = findViewById(R.id.totalProtein);
        addFood = findViewById(R.id.addFood);

        Intent intent = getIntent();
        numFoodsStr = intent.getStringExtra("numFoodsStr");
        addFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveFoodDetails();
            }
        });
    }
    private void saveFoodDetails() {
        String foodNameStr = foodName.getText().toString().trim();
        String totalCaloriesStr = totalCalories.getText().toString().trim();
        String totalFatStr = totalFat.getText().toString().trim();
        String totalSodiumStr = totalSodium.getText().toString().trim();
        String totalCarbsStr = totalCarbs.getText().toString().trim();
        String totalSugarStr = totalSugar.getText().toString().trim();
        String totalProteinStr = totalProtein.getText().toString().trim();

        if(i >= Integer.parseInt(numFoodsStr) - 1){
            startActivity(new Intent(EnterRecipeFoodActivity.this, EnterRecipeActivity.class));
        }
        else if (!(totalCaloriesStr.matches("[0-9]+") || totalFatStr.matches("[0-9]+") ||
                totalSodiumStr.matches("[0-9]+") || totalCarbsStr.matches("[0-9]+") ||
                totalSugarStr.matches("[0-9]+") || totalProteinStr.matches("[0-9]+"))) {
            Toast.makeText(EnterRecipeFoodActivity.this, "All totals must be a number", Toast.LENGTH_LONG).show();
        } else if (!(foodNameStr.isEmpty() || totalCarbsStr.isEmpty() ||
                totalFatStr.isEmpty() || totalSodiumStr.isEmpty() ||
                totalCarbsStr.isEmpty() || totalSugarStr.isEmpty() ||
                totalProteinStr.isEmpty()))
        {
            ingredientsList = ingredientsList + foodNameStr + "\n";
            numCalories += Integer.parseInt(totalCaloriesStr);
            numFat += Integer.parseInt(totalFatStr);
            numSodium += Integer.parseInt(totalSodiumStr);
            numCarbs += Integer.parseInt(totalCarbsStr);
            numSugar += Integer.parseInt(totalSugarStr);
            numProtein += Integer.parseInt(totalProteinStr);
            i++;

        } else {
            Toast.makeText(EnterRecipeFoodActivity.this, "Please do not leave a section empty", Toast.LENGTH_LONG).show();
        }
        foodName.setText("");
        totalCalories.setText("");
        totalFat.setText("");
        totalSodium.setText("");
        totalCarbs.setText("");
        totalSugar.setText("");
        totalProtein.setText("");
    }
}
