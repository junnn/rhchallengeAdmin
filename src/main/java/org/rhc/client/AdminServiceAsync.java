package org.rhc.client;

/**
 * Created with IntelliJ IDEA.
 * User: Jun
 * Date: 1/8/13
 * Time: 9:04 AM
 * To change this template use File | Settings | File Templates.
 */
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface AdminServiceAsync {
    void createStudent(String email, String password, String firstName, String lastName, String contact,
                       String country, String countryCode, String school, String lecturerFirstName, String lecturerLastName,
                       String lecturerEmail, String language, AsyncCallback<Boolean> async);
}