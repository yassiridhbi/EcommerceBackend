package org.ecommercebackend.security.service;

import java.io.Serializable;

/**
 * Created by stephan on 20.03.16.
 */
public class JwtAuthenticationResponse implements Serializable {

    private static final long serialVersionUID = 1250166508152483573L;

    private final String token;

    private final long id;

    public JwtAuthenticationResponse(String token, long id) {
        this.token = token;
        this.id = id;
    }

    public String getToken() {
        return this.token;
    }

    public long getId() {
        return this.id;
    }
}
