package com.rep5.sialah.common;

/**
 * Created by low on 3/10/16 9:33 AM.
 */
public class CustomerData {
    private static String firebaseToken;

    public CustomerData() {
    }

    public static String getFirebaseToken() {
        return firebaseToken;
    }

    public static void setFirebaseToken(String firebaseToken) {
        CustomerData.firebaseToken = firebaseToken;
    }
}
