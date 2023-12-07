package com.example.myhealthtrainer.model;

import android.text.TextUtils;

import com.google.firebase.auth.FirebaseUser;
public class workout {


    private String userId;
    private String userName;
    private String workoutName;
    private String weight;
    private String reps;
    private String sets;
    public workout(/*FirebaseUser user,*/ String workoutName, String weight, String reps, String sets){
        /*this.userId = user.getUid();
        this.userName = user.getDisplayName();

        if (TextUtils.isEmpty(this.userName)) {
            this.userName = user.getEmail();
        }*/
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
}
