package org.rhc.server;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Created with IntelliJ IDEA.
 * User: Jun
 * Date: 1/8/13
 * Time: 10:01 AM
 * To change this template use File | Settings | File Templates.
 */
public class SecurityUtil {

    public static String escapeInput(String input) {
        if (input == null) {
            return null;
        }

        return input.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(
                ">", "&gt;");
    }

    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(10));
    }
}