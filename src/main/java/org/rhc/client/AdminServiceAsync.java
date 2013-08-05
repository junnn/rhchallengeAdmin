package org.rhc.client;

/**
 * Created with IntelliJ IDEA.
 * User: Jun
 * Date: 1/8/13
 * Time: 9:04 AM
 * To change this template use File | Settings | File Templates.
 */
import com.google.gwt.user.client.rpc.AsyncCallback;
import org.rhc.shared.Student;

import java.util.List;

public interface AdminServiceAsync {
    void createStudent(String email, String password, String firstName, String lastName, String contact,
                       String country, String countryCode, String school, String lecturerFirstName, String lecturerLastName,
                       String lecturerEmail, String language, AsyncCallback<Boolean> async);

    void createAndVerifyStudent(String email, String password, String firstName, String lastName, String contact,
                       String country, String countryCode, String school, String lecturerFirstName, String lecturerLastName,
                       String lecturerEmail, String language, AsyncCallback<Boolean> async);



    void setConfirmationStatus(String email, AsyncCallback<Boolean> async);
    void deleteStudent(String email, AsyncCallback<Boolean> async);
    void generateRandomPassword(AsyncCallback<String> async);
}
