package com.example.myhealthtrainer.model;

import android.text.TextUtils;

import com.google.firebase.auth.FirebaseUser;
public class workout {


    private String userId;
    private String userName;
    private String workoutName;
    private String workoutField;
    private String repGoal;
    private String setGoal;
    private String weight;
    private String reps;
    private String sets;
    public workout(/*FirebaseUser user,*/String workoutField, String workoutName, String weight, String reps, String sets, String repGoal, String setGoal){
        /*this.userId = user.getUid();
        this.userName = user.getDisplayName();

        if (TextUtils.isEmpty(this.userName)) {
            this.userName = user.getEmail();
        }*/
        this.workoutField = workoutField;
        this.workoutName = workoutName;
        this.weight = weight;
        this.reps = reps;
        this.sets = sets;
        this.repGoal = repGoal;
        this.setGoal = setGoal;
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

    public String getWorkoutField() {
        return workoutField;
    }

    public void setWorkoutField(String workoutField) {
        this.workoutField = workoutField;
    }


    public String getWorkoutName() {
        return workoutName;
    }

    public void setWorkoutName(String workoutName) {
        this.workoutName = workoutName;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getReps() {
        return reps;
    }

    public void setReps(String reps) {
        this.reps = reps;
    }

    public String getSets() {
        return sets;
    }

    public void setSets(String sets) {
        this.sets = sets;
    }

    public String getRepGoal() {
        return repGoal;
    }

    public void setRepGoal(String repGoal) {
        this.repGoal = repGoal;
    }

    public String getSetGoal() {
        return setGoal;
    }

    public void setSetGoal(String setGoal) {
        this.setGoal = setGoal;
    }
}
