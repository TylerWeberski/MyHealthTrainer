package com.example.myhealthtrainer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.ViewGroup.LayoutParams;

public class AdviceActivity extends AppCompatActivity {

    private Button sleepButton, workoutButton, nutritionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advice);

        sleepButton = findViewById(R.id.sleepButton);
        workoutButton = findViewById(R.id.workoutButton);
        nutritionButton = findViewById(R.id.nutritionButton);

        sleepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopup(getString(R.string.sleep_advice));
            }
        });

        workoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopup(getString(R.string.workout_advice));
            }
        });

        nutritionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopup(getString(R.string.nutrition_advice));
            }
        });
    }

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