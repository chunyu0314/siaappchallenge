package com.rep5.sialah.common;

/**
 * Created by low on 3/10/16 9:33 AM.
 */
public class CustomerData {
    private static String firebaseToken;
    private static String seatNumber = "18E";

    public CustomerData() {
    }

    public static String getSeatNumber() {
        return seatNumber;
    }

    public static void setSeatNumber(String seatNumber) {
        CustomerData.seatNumber = seatNumber;
    }

    public static String getFirebaseToken() {
        return firebaseToken;
    }

    public static void setFirebaseToken(String firebaseToken) {
        CustomerData.firebaseToken = firebaseToken;
    }
}
