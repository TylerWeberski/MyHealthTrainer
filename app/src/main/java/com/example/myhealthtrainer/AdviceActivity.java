package com.example.myhealthtrainer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.ViewGroup.LayoutParams;

/**
 * AdviceActivity class represents an activity that provides advice on sleep, workout, and nutrition.
 */
public class AdviceActivity extends AppCompatActivity {

    // UI components
    private Button sleepButton, workoutButton, nutritionButton;
    private ImageButton backButton;

    /**
     * Called when the activity is first created. Responsible for initializing the activity.
     *
     * @param savedInstanceState A Bundle containing the saved state of the activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advice);

        // Initialize UI components
        sleepButton = findViewById(R.id.sleepButton);
        workoutButton = findViewById(R.id.workoutButton);
        nutritionButton = findViewById(R.id.nutritionButton);
        backButton = findViewById(R.id.backButton);

        // Set listener for the back button
        backButton.setOnClickListener(v -> {
            finish();
        });

        // Set listener for the sleep advice button
        sleepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopup(getString(R.string.sleep_advice));
            }
        });

        // Set listener for the workout advice button
        workoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopup(getString(R.string.workout_advice));
            }
        });

        // Set listener for the nutrition advice button
        nutritionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopup(getString(R.string.nutrition_advice));
            }
        });
    }

    /**
     * Displays a popup with the given advice.
     *
     * @param advice The advice to be displayed in the popup.
     */
    private void showPopup(String advice) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.activity_popup, null);

        TextView adviceTextView = popupView.findViewById(R.id.adviceTextView);
        adviceTextView.setText(advice);

        PopupWindow popupWindow = new PopupWindow(
                popupView,
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT,
                true
        );

        popupWindow.showAtLocation(popupView, 0, 0, 0);

        popupView.setOnTouchListener((v, event) -> {
            popupWindow.dismiss();
            return true;
        });
    }
}