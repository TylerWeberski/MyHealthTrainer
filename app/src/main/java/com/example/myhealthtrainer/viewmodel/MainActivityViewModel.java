package com.example.myhealthtrainer.viewmodel;

import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseUser;

public class MainActivityViewModel extends ViewModel {

    private boolean mIsSigningIn;
    private static FirebaseUser mUser;

    public MainActivityViewModel() {
        mIsSigningIn = false;
    }

    public boolean getIsSigningIn() {
        return mIsSigningIn;
    }
    public static FirebaseUser getUser() { return mUser; }
    public static void setUser(FirebaseUser user) { mUser = user; }

    public void setIsSigningIn(boolean mIsSigningIn) {
        this.mIsSigningIn = mIsSigningIn;
    }
}
