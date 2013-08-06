package org.rhc.shared;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Jun
 * Date: 6/8/13
 * Time: 9:43 AM
 * To change this template use File | Settings | File Templates.
 */
public class ConfirmationTokens implements Serializable {
    private String email;
    private String token;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
