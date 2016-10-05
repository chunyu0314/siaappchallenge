package com.rep5.sialah.common;

/**
 * Created by low on 5/10/16 9:46 PM.
 */
public class MessageStore {
    private static String message;

    public MessageStore() {
    }

    public static String getMessage() {
        return message;
    }

    public static void setMessage(String message) {
        MessageStore.message = message;
    }

    public static String getAndClear() {
        String get = message;
        message = null;
        return get;
    }
}
