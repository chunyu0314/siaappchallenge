package com.rep5.sialah.common;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;


/**
 * Created by low on 17/7/16 1:12 PM.
 */
public class RestClient {
    private static Client client;
    private static SSLContext ssl;
    private static final Logger logger = LoggerFactory.getLogger(RestClient.class);

    public static void init() {
        /*
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager(){
            public X509Certificate[] getAcceptedIssuers(){return null;}
            public void checkClientTrusted(X509Certificate[] certs, String authType){}
            public void checkServerTrusted(X509Certificate[] certs, String authType){}
        }};
        try {
            ssl = SSLContext.getInstance("TLS");
            ssl.init(null, trustAllCerts, new SecureRandom());
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
        }
        client = ClientBuilder.newBuilder().sslContext(ssl).hostnameVerifier((s, sslSession) -> true).register(JacksonFeature.class).build();
        */
        client = ClientBuilder.newBuilder().register(JacksonFeature.class).build();
    }

    public static Client getClient() {
        if (client == null) {
            init();
        }
        return client;
    }

    public static WebTarget getTarget(String url) {
        if (client == null) {
            init();
        }
        return client.target(url);
    }

    /**
     *
     * @param username
     * @return Error codes: USER_NOT_EXIST, null
     *//*
    public String getSalt(String username) {

        Future<Response> fs = target.path("user/verify_user").request().async().post(Entity.entity(username, MediaType.TEXT_PLAIN));
        Response response;
        try {
            response = fs.get();
        } catch (InterruptedException | ExecutionException e) {
            logger.error("error getting salt");
            e.printStackTrace();
            return null;
        }
        if (response.getStatus() == ClefHttpStatus.NOT_FOUND && response.readEntity(String.class).matches(ReturnCodes.USER_NOT_EXIST)) {
            return ReturnCodes.USER_NOT_EXIST;
        }
        if (response.getStatus() != ClefHttpStatus.OK) {
            return null;
        }
        return response.readEntity(String.class);
    }

    *//**
     *
     * @param username
     * @param secretKey
     * @return AccessKey. Error codes: WRONG_SECRET_KEY, null for unknown error
     *//*
    public String logIn(String username, String secretKey) {
        Future<Response> fs = target.path("user/login").request().header(HttpHeaders.USER_AGENT, username).async().post(Entity.entity(secretKey, MediaType.TEXT_PLAIN));
        Response result;
        String accessKey = null;
        int status;
        try {
            result = fs.get();
        } catch (InterruptedException | ExecutionException e) {
            logger.error("error logging in, failed http request");
            e.printStackTrace();
            return null;
        }
        status = result.getStatus();
        switch (status) {
            case ClefHttpStatus.INTERNAL_SERVER_ERROR:
                logger.error("server error, failed to log in");
                logger.error(result.readEntity(String.class));
                break;
            case ClefHttpStatus.UNAUTHORIZED:
                logger.error("Incorrect password");
                accessKey = ReturnCodes.WRONG_SECRET_KEY;
                break;
            case ClefHttpStatus.OK:
                accessKey = result.readEntity(String.class);
                logger.info("log in success");
                break;
            default:
                logger.error("unknown error. Status code " + result.getStatus());
                logger.error(result.readEntity(String.class));
                break;
        }
        return accessKey;
    }

    public int logOut(String username, String accessKey) {
        Future<Response> fs = target.path("user/logout").request().header(HttpHeaders.USER_AGENT, username).header(HttpHeaders.AUTHORIZATION, accessKey)
                .async().get();
        int status;
        Response result;
        try {
            result = fs.get();
        }
        catch (InterruptedException | ExecutionException e) {
            logger.error("error logging out, failed http request");
            e.printStackTrace();
            return ReturnCodes.INTERNAL_ERROR;
        }
        status = result.getStatus();
        switch (status) {
            case ClefHttpStatus.ALREADY_ACCEPTED:
                logger.warn("user is already logged out");
                return ReturnCodes.SUCCESS;
            case ClefHttpStatus.UNAUTHORIZED:
                logger.error("wrong access key, proceed with forced log out");
                return ReturnCodes.WRONG_ACCESS_KEY;
            case ClefHttpStatus.UNPROCCESSABLE_ENTITY:
                if (result.readEntity(String.class).matches(ReturnCodes.USER_NOT_EXIST)) {
                    logger.error("User does not exist");
                    return ReturnCodes.NOT_EXIST;
                }
                logger.error("Unknown error with unproccessable entity");
                return ReturnCodes.INTERNAL_ERROR;
            case ClefHttpStatus.ACCEPTED:
                logger.info("successfully logged out");
                return ReturnCodes.SUCCESS;
            default:
                logger.error("Unknown error, status code: " + status);
                logger.error(result.readEntity(String.class));
                return ReturnCodes.INTERNAL_ERROR;
        }
    }

    public void signup() {
        Signup user = new Signup();
        user.setEmail("heok.hong.low@gmail.com");
        String result = target.path("customers/signup").request(MediaType.TEXT_PLAIN).post(Entity.entity(user, MediaType.APPLICATION_JSON), String.class);
        System.out.println(result);
    }


    public ResultResponse retrieve(String transactionId, String username, String accessKey) throws Exception {
        Response response = target.path("query/" + transactionId).request(MediaType.APPLICATION_JSON).header(HttpHeaders.USER_AGENT, username).header(HttpHeaders.AUTHORIZATION, accessKey)
                .get();
        switch (response.getStatus()) {
            case ClefHttpStatus.NO_CONTENT:
                logger.info("No content available yet");
                return null;
            case ClefHttpStatus.OK:
                logger.info("response received");
                return response.readEntity(ResultResponse.class);
            case ClefHttpStatus.SESSION_TIMED_OUT:
                logger.warn("session timed out, log in again");
                throw new Exception(ReturnCodes.SESSION_TIMED_OUT);
            default:
                logger.error("unknown internal error occurred. Status code: " + response.getStatus() + ", transaction: " + transactionId);
                logger.error(response.readEntity(String.class));
                throw new Exception(ReturnCodes.UNKNOWN_ERROR + " status code: " + response.getStatus());
        }
    }

    public void acknowledge(String transactionId, String username, String accessKey) throws Exception {
        Response response = target.path("query/" + transactionId).request(MediaType.APPLICATION_JSON).header(HttpHeaders.USER_AGENT, username).header(HttpHeaders.AUTHORIZATION, accessKey)
                .delete();
        switch (response.getStatus()) {
            case ClefHttpStatus.ACCEPTED:
                logger.info("successfully acknowledged");
                return;
            case ClefHttpStatus.SESSION_TIMED_OUT:
                logger.warn("session timed out");
                throw new Exception(ReturnCodes.SESSION_TIMED_OUT);
            default:
                logger.error("unknown error occurred. Status code: " + response.getStatus() + ", transaction: " + transactionId);
                logger.error(response.readEntity(String.class));
                throw new Exception(ReturnCodes.UNKNOWN_ERROR + " status code: " + response.getStatus());
        }
    }

    *//**
     *
     * @param queryType
     * @param query
     * @param username
     * @param accessKey
     * @return transactionId
     * @throws Exception
     *//*
    public String query(String queryType, String query, String username, String accessKey) throws Exception {
        String transactionId = ClefUUID.randomId();
        ClefQuery clefQuery = new ClefQuery();
        clefQuery.setQuery(query);
        clefQuery.setQueryType(queryType);
        clefQuery.setTransactionId(transactionId);;
        Response response = target.path("query/" + queryType).request().header(HttpHeaders.USER_AGENT, username).header(HttpHeaders.AUTHORIZATION, accessKey)
                .post(Entity.entity(clefQuery, MediaType.APPLICATION_JSON));
        switch (response.getStatus()) {
            case ClefHttpStatus.ACCEPTED:
                logger.info("successfully acknowledged");
                return transactionId;
            case ClefHttpStatus.SESSION_TIMED_OUT:
                logger.warn("session timed out");
                throw new Exception(ReturnCodes.SESSION_TIMED_OUT);
            default:
                logger.error("unknown error occurred. Status code: " + response.getStatus() + ", transaction: " + transactionId);
                logger.error(response.readEntity(String.class));
                throw new Exception(ReturnCodes.UNKNOWN_ERROR + " status code: " + response.getStatus());
        }
    }*/
}

