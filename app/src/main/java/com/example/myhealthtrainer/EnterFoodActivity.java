package com.example.myhealthtrainer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class EnterFoodActivity extends AppCompatActivity {

    private EditText etFoodName, etTotalCalories, etTotalFat, etSodium, etTotalCarbs, etTotalSugar, etProtein;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout frameLayout = new FrameLayout(this);
        setContentView(frameLayout);

        LayoutInflater inflater = LayoutInflater.from(this);
        View childView = inflater.inflate(R.layout.activity_enter_food, frameLayout, false);
        frameLayout.addView(childView);

        etFoodName = findViewById(R.id.etFoodName);
        etTotalCalories = findViewById(R.id.etTotalCalories);
        etTotalFat = findViewById(R.id.etTotalFat);
        etSodium = findViewById(R.id.etSodium);
        etTotalCarbs = findViewById(R.id.etTotalCarbs);
        etTotalSugar = findViewById(R.id.etTotalSugar);
        etProtein = findViewById(R.id.etProtein);
        btnSave = findViewById(R.id.btnSave);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveFoodDetails();
            }
        });
    }

    private void saveFoodDetails() {
        String foodName = etFoodName.getText().toString().trim();
        String totalFat = etTotalFat.getText().toString().trim();
        String sodium = etSodium.getText().toString().trim();
        String totalCarbs = etTotalCarbs.getText().toString().trim();
        String totalSugar = etTotalSugar.getText().toString().trim();
        String protein = etProtein.getText().toString().trim();

        // TODO: Save these values to Firebase database
        // Example: Create a Food object and save it to Firebase

        // For now, let's display a toast indicating successful data entry
        Toast.makeText(this, "Food Details Saved", Toast.LENGTH_SHORT).show();

        // Clear the fields after saving
        etFoodName.setText("");
        etTotalCalories.setText("");
        etTotalFat.setText("");
        etSodium.setText("");
        etTotalCarbs.setText("");
        etTotalSugar.setText("");
        etProtein.setText("");

        finish();
    }
}
