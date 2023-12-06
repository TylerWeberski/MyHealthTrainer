package com.example.myhealthtrainer.model;

import android.text.TextUtils;

import com.google.firebase.auth.FirebaseUser;
public class workout {


    private String userId;
    private String userName;
    private String workoutName;
    private int weight;
    private int reps;
    private int sets;
    public workout(FirebaseUser user, String workoutName, int weight, int reps, int set){
        this.userId = user.getUid();
        this.userName = user.getDisplayName();

        if (TextUtils.isEmpty(this.userName)) {
            this.userName = user.getEmail();
        }
        this.workoutName = workoutName;
        this.weight = weight;
        this.reps = reps;
        this.sets = sets;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getWorkoutName() {
        return workoutName;
    }

    public void setWorkoutName(String workoutName) {
        this.workoutName = workoutName;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }
}
