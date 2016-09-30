package com.clef.restapi;

import com.clef.infra.commons.models.SignupRequest;
import com.clef.infra.commons.services.UserDatabase;
import com.clef.infra.elasticsearch.modules.BasicModules;
import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * Created by low on 27/7/16 1:36 PM.
 */
public class UserFactory {

    private static UserDatabase database;

    public static void init() {

        Injector injector = Guice.createInjector(new BasicModules());
        database = injector.getInstance(UserDatabase.class);
    }

    /**
     *
     * @param username
     * @param secretKey
     * @return AccessKey. Error codes: USER_NOT_EXIST, WRONG_SECRET_KEY, NOT_VALIDATED
     */
    public static String logIn(String username, String secretKey) {
        return database.logIn(username, secretKey);
    }

    /**
     * Checks for username and email clashes before registering user in database
     * @param userInfo request received from signups
     * @return result codes: USER_AND_EMAIL_CLASH, USER_CLASH, EMAIL_CLASH, SUCCESS
     *
     */
    public static int signUp(SignupRequest userInfo) {
        return database.newUser(userInfo);
    }

    /**
     *
     * @param username
     * @param accessKey
     * @return JSON String representing user. Error codes: USER_NOT_EXIST, USER_LOGGED_OUT, WRONG_ACCESS, SESSION_TIMED_OUT
     */
    public static String authenticate(String username, String accessKey) {
        return database.accessUser(username, accessKey);
    }

    /**
     *
     * @param username
     * @return null on failure
     */
    public static String getSalt(String username) {
        return database.getSalt(username);
    }

    /**
     *
     * @param username
     * @return int codes: NOT_EXIST, ALREADY_LOGGED_OUT, SUCCESS
     */
    public static int logOut(String username) {
        return database.logOut(username);
    }
}
