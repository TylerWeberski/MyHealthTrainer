package com.example.myhealthtrainer.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myhealthtrainer.R;
import com.example.myhealthtrainer.model.workout;

import java.util.List;

public class WorkoutAdapter extends RecyclerView.Adapter<WorkoutAdapter.WorkoutViewHolder> {

    private Context context;
    private List<workout> workoutList;

    public WorkoutAdapter(Context context, List<workout> workoutList) {
        this.context = context;
        this.workoutList = workoutList;
    }

    @NonNull
    @Override
    public WorkoutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.workout_item, parent, false);
        return new WorkoutViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkoutViewHolder holder, int position) {
        workout Workout = workoutList.get(position);
        holder.workoutFieldTextView.setText(Workout.getWorkoutField());
        holder.exerciseTextView.setText(Workout.getWorkoutName());
        holder.setsTextView.setText("Sets: " + Workout.getSets());
        holder.repsTextView.setText("Reps: " + Workout.getReps());
        holder.weightTextView.setText("Weight (lbs): " + Workout.getWeight());
        holder.repGoalTextView.setText(Workout.getRepGoal());
        holder.setGoalTextView.setText(Workout.getSetGoal());
    }

    @Override
    public int getItemCount() {
        return workoutList.size();
    }

    public class WorkoutViewHolder extends RecyclerView.ViewHolder {
        TextView workoutFieldTextView,exerciseTextView, setsTextView, repsTextView, weightTextView,repGoalTextView,setGoalTextView;

        public WorkoutViewHolder(@NonNull View itemView) {
            super(itemView);
            workoutFieldTextView = itemView.findViewById(R.id.workoutFieldTextView);
            exerciseTextView = itemView.findViewById(R.id.exerciseTextView);
            setsTextView = itemView.findViewById(R.id.setsTextView);
            repsTextView = itemView.findViewById(R.id.repsTextView);
            weightTextView = itemView.findViewById(R.id.weightTextView);
            repGoalTextView = itemView.findViewById(R.id.repGoalTextView);
            setGoalTextView = itemView.findViewById(R.id.setGoalTextView);
        }
    }
}
