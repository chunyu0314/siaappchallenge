package com.rep5.sialah.common;

/**
 * Created by low on 3/10/16 9:33 AM.
 */
public class CustomerData {
    private static String firebaseToken;
    private static String friendToken;
    private static String seatNumber = "24C";

    public CustomerData() {
    }

    public static String getFriendToken() {
        return friendToken;
    }

    public static void setFriendToken(String friendToken) {
        CustomerData.friendToken = friendToken;
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
