package com.example.myhealthtrainer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CalculateRMRActivity extends AppCompatActivity {

    private EditText editTextHeight, editTextWeight, editTextAge;
    private RadioGroup radioGroupGender;
    private TextView textView;
    private Spinner spinnerActivityLevel;
    private Button buttonCalculateRMR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate_rmr);

        editTextHeight = findViewById(R.id.editTextHeight);
        editTextWeight = findViewById(R.id.editTextWeight);
        radioGroupGender = findViewById(R.id.radioGroupGender);
        spinnerActivityLevel = findViewById(R.id.spinnerActivityLevel);
        buttonCalculateRMR = findViewById(R.id.buttonCalculateRMR);
        editTextAge = findViewById(R.id.editTextAge);
        textView = findViewById(R.id.textView);



        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.activity_levels,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerActivityLevel.setAdapter(adapter);

        buttonCalculateRMR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateRMR();
            }
        });
    }

    private void calculateRMR() {
        // Get user input
        String heightStr = editTextHeight.getText().toString();
        String weightStr = editTextWeight.getText().toString();
        String ageStr = editTextAge.getText().toString();
        int selectedGenderId = radioGroupGender.getCheckedRadioButtonId();
        int activityLevelPosition = spinnerActivityLevel.getSelectedItemPosition();

        // Perform validation
        if (heightStr.isEmpty() || weightStr.isEmpty() || selectedGenderId == -1) {
            Toast.makeText(this, "Please enter all information", Toast.LENGTH_SHORT).show();
            return;
        }


        double height = Double.parseDouble(heightStr);
        double weight = Double.parseDouble(weightStr);
        double age = Double.parseDouble(ageStr);

        // Get selected gender
        RadioButton selectedGender = findViewById(selectedGenderId);
        String gender = selectedGender.getText().toString();

        double bmr;
        if (gender.equals("Male")) {
            bmr = 88.362 + (13.397 * weight) + (4.799 * height) - (5.677 * age); // 25 is placeholder age
        } else {
            bmr = 447.593 + (9.247 * weight) + (3.098 * height) - (4.330 * age); // 25 is placeholder age
        }


        double activityMultiplier;
        switch (activityLevelPosition) {
            case 0:
                activityMultiplier = 1.2; // Little to no exercise
                break;
            case 1:
                activityMultiplier = 1.375; // Light exercise
                break;
            case 2:
                activityMultiplier = 1.55; // Moderate exercise
                break;
            case 3:
                activityMultiplier = 1.725; // Heavy exercise
                break;
            case 4:
                activityMultiplier = 1.9; // Very heavy exercise
                break;
            default:
                activityMultiplier = 1.0; // Default to sedentary
        }

        double rmr = bmr * activityMultiplier;

        // Display the calculated RMR
        textView.setText("Your estimated RMR: is " + rmr + " Please Take in mind that this is an estimate, your Genetics and overall muscle mass also affect RMR");
    }
}
