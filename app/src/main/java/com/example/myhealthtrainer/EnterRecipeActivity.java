package com.example.myhealthtrainer;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class EnterRecipeActivity extends AppCompatActivity {

    private Button saveButton;
    private TextView recipeName;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        FrameLayout frameLayout = new FrameLayout(this);
        setContentView(frameLayout);

        saveButton = findViewById(R.id.saveRecipe);
        recipeName = findViewById(R.id.recipeName);

        saveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                saveRecipeDetails();
            }
        });
    }
    private void saveRecipeDetails(){
        String recipeNameStr = recipeName.getText().toString().trim();

        recipeName.setText("");

        finish();
    }
}
