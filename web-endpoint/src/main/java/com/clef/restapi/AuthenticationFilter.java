package com.clef.restapi;

import com.alibaba.fastjson.JSON;
import com.clef.infra.commons.models.ClefHttpStatus;
import com.clef.infra.commons.models.ReturnCodes;
import com.clef.infra.commons.models.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Priority;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.security.Principal;

/**
 * Filters requests to check authentication access key.
 * Implements ContainerRequestFilter and assigns security context based on access token
 * Created by low on 19/7/16 6:45 PM.
 */
@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

    private static Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        logger.info("Processing authentication");

        // Get the HTTP Authorization header from the request
        String authorizationHeader =
                requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        // Check if the HTTP Authorization header is present and formatted correctly
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new NotAuthorizedException("Authorization header must be provided");
        }

        // Extract the token from the HTTP Authorization header
        String token = authorizationHeader.substring("Bearer".length()).trim();

        // Get the HTTP Authorization header from the request
        String username = requestContext.getHeaderString(HttpHeaders.USER_AGENT);

        // Check if the HTTP Authorization header is present and formatted correctly
        if (username == null) {
            throw new NotAuthorizedException("Username must be provided");
        }

        String userInfo = null;


        logger.info("validating... token = " + token);
        userInfo = UserFactory.authenticate(username, token);

        switch (userInfo) {
            case ReturnCodes.WRONG_ACCESS:
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
                break;
            case ReturnCodes.SESSION_TIMED_OUT:
                requestContext.abortWith(Response.status(ClefHttpStatus.SESSION_TIMED_OUT).build());
                break;
            case ReturnCodes.USER_NOT_EXIST:
                logger.error("user not exist when authenticating with access code");
                requestContext.abortWith(Response.status(ClefHttpStatus.UNPROCCESSABLE_ENTITY).build());
                break;
            case ReturnCodes.USER_LOGGED_OUT:
                requestContext.abortWith(Response.status(ClefHttpStatus.USER_LOGGED_OUT).build());
                break;
            default:
                break;
        }

        UserInfo info = JSON.parseObject(userInfo, UserInfo.class);

        requestContext.setSecurityContext(new SecurityContext() {

            /**
             * set Principle in SecurityContext.
             * @return Principal containing username retrieved from token
             */
            @Override
            public Principal getUserPrincipal() {
                return () -> username;
            }

            /**
             * Assign role hierarchy according to retrieved roles.
             * @param role called from resources
             * @return boolean whether a role is considered match
             */
            @Override
            public boolean isUserInRole(String role) {
                return role.matches(info.getRole());
            }

            /**
             *
             * @return boolean indicating whether this request was made using a secure channel, such as HTTPS
             */
            @Override
            public boolean isSecure() {
                return true;
            }

            /**
             *
             * @return string value of the authentication scheme used to protect the resource, eg BASIC, FORM
             */
            @Override
            public String getAuthenticationScheme() {
                return null;
            }
        });
    }
}
