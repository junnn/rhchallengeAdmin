package org.rhc.client;

/**
 * Created with IntelliJ IDEA.
 * User: Jun
 * Date: 1/8/13
 * Time: 9:47 AM
 * To change this template use File | Settings | File Templates.
 */

import com.google.gwt.user.client.rpc.AsyncCallback;
import org.rhc.shared.Student;

import java.util.List;

public interface SearchServiceAsync {
    void loadDB(String search, String field, AsyncCallback<List<Student>> async);
    void displayDB(AsyncCallback<List<Student>> async);
    void updateProfileData(Student studentRow, AsyncCallback<Boolean> async);
    void updateEmail(String email, String newEmail, AsyncCallback<Boolean> async);

}
