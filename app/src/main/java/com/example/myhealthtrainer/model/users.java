package com.example.myhealthtrainer.model;

public class users {
    private String email;
    private int curCalories;
    private int curCarbs;
    private int curFat;
    private int curProtein;
    private int curSodium;
    private int curSugar;
    private int goalCalories;
    private int goalCarbs;
    private int goalFat;
    private int goalProtein;
    private int goalSodium;
    private int goalSugar;

    public users(String email, int curCalories, int curCarbs, int curFat, int curProtein, int curSodium, int curSugar, int goalCalories, int goalCarbs, int goalFat, int goalProtein, int goalSodium, int goalSugar){
        this.email = email;
        this.curCalories = curCalories;
        this.curCarbs = curCarbs;
        this.curFat = curFat;
        this.curProtein = curProtein;
        this.curSodium = curSodium;
        this.curSugar = curSugar;
        this.goalCalories = goalCalories;
        this.goalCarbs = goalCarbs;
        this.goalFat = goalFat;
        this.goalProtein = goalProtein;
        this.goalSodium = goalSodium;
        this.goalSugar = goalSugar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getCurCalories() {
        return curCalories;
    }

    public void setCurCalories(int curCalories) {
        this.curCalories = curCalories;
    }

    public int getCurCarbs() {
        return curCarbs;
    }

    public void setCurCarbs(int curCarbs) {
        this.curCarbs = curCarbs;
    }

    public int getCurFat() {
        return curFat;
    }

    public void setCurFat(int curFat) {
        this.curFat = curFat;
    }

    public int getCurProtein() {
        return curProtein;
    }

    public void setCurProtein(int curProtein) {
        this.curProtein = curProtein;
    }

    public int getCurSodium() {
        return curSodium;
    }

    public void setCurSodium(int curSodium) {
        this.curSodium = curSodium;
    }

    public int getCurSugar() {
        return curSugar;
    }

    public void setCurSugar(int curSugar) {
        this.curSugar = curSugar;
    }

    public int getGoalCalories() {
        return goalCalories;
    }

    public void setGoalCalories(int goalCalories) {
        this.goalCalories = goalCalories;
    }

    public int getGoalCarbs() {
        return goalCarbs;
    }

    public void setGoalCarbs(int goalCarbs) {
        this.goalCarbs = goalCarbs;
    }

    public int getGoalFat() {
        return goalFat;
    }

    public void setGoalFat(int goalFat) {
        this.goalFat = goalFat;
    }

    public int getGoalProtein() {
        return goalProtein;
    }

    public void setGoalProtein(int goalProtein) {
        this.goalProtein = goalProtein;
    }

    public int getGoalSodium() {
        return goalSodium;
    }

    public void setGoalSodium(int goalSodium) {
        this.goalSodium = goalSodium;
    }

    public int getGoalSugar() {
        return goalSugar;
    }

    public void setGoalSugar(int goalSugar) {
        this.goalSugar = goalSugar;
    }
}
