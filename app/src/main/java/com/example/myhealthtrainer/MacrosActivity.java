package com.example.myhealthtrainer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;


public class MacrosActivity extends AppCompatActivity {

    private EditText etTotalFatLimit, etSodiumLimit, etTotalCarbsLimit, etTotalSugarLimit, etFiberLimit, etProteinLimit;
    private TextView tvCurrentMacros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout frameLayout = new FrameLayout(this);
        setContentView(frameLayout);

        LayoutInflater inflater = LayoutInflater.from(this);
        View childView = inflater.inflate(R.layout.activity_macros, frameLayout, false);
        frameLayout.addView(childView);

        etTotalFatLimit = findViewById(R.id.etTotalFatLimit);
        etSodiumLimit = findViewById(R.id.etSodiumLimit);
        etTotalCarbsLimit = findViewById(R.id.etTotalCarbsLimit);
        etTotalSugarLimit = findViewById(R.id.etTotalSugarLimit);
        etFiberLimit = findViewById(R.id.etFiberLimit);
        etProteinLimit = findViewById(R.id.etProteinLimit);
        Button btnSaveLimits = findViewById(R.id.btnSaveLimits);
        tvCurrentMacros = findViewById(R.id.tvCurrentMacros);
        Button btnSave = findViewById(R.id.btnSave);
        Button btnReset = findViewById(R.id.btnReset);

        // Placeholder to display stored data (0 for each nutrient initially)
        displayCurrentMacros();

        btnSaveLimits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveLimits();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveCurrentMacros();
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetCurrentMacros();
            }
        });
    }

    private void saveLimits() {
        // Retrieve and save user-entered macro limits to Firebase
        // This function will handle the Firebase save operation
        // Replace this with Firebase implementation
    }

    private void saveCurrentMacros() {
        // Placeholder function to save current macros to Firebase
        // Replace this with Firebase implementation
    }

    private void resetCurrentMacros() {
        // Placeholder function to reset current macros for a new day
        // This function should reset the current daily macros
        // Replace this with Firebase implementation or set the values to 0
        displayCurrentMacros(); // Refresh the displayed values after resetting
    }

    private void displayCurrentMacros() {
        // Placeholder to display current daily macros (retrieved from Firebase)
        String currentMacros = "Current Macros for Today:\n" +
                "Total Fat: 0g\n" +
                "Sodium: 0mg\n" +
                "Total Carbs: 0g\n" +
                "Total Sugar: 0g\n" +
                "Fiber: 0g\n" +
                "Protein: 0g";

        // Update the TextView with current daily macros
        tvCurrentMacros.setText(currentMacros);
    }
}
