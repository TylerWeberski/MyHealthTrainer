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

    /**
     * To grab a user and their info
     * @return FirebaseUser
     */
    public static FirebaseUser getUser() { return mUser; }

    /**
     * Sets the user upon sign in
     * @param user FirebaseUser
     */
    public static void setUser(FirebaseUser user) { mUser = user; }

    public void setIsSigningIn(boolean mIsSigningIn) {
        this.mIsSigningIn = mIsSigningIn;
    }
}
