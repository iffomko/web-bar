package org.iffomko.server.domain;

public class ControllerNames {
    public static final String BASE_URL = "/api/v1";

    public static final String RESERVATION_URL = BASE_URL + "/reservations";
    public static final String USERS_URI_PART = "/users";

    public static final String REGISTRATION_URI_PART = "/registration";
    public static final String REGISTRATION_URL = BASE_URL + USERS_URI_PART + REGISTRATION_URI_PART;
}
