package org.rhc.client;

/**
 * Created with IntelliJ IDEA.
 * User: Jun
 * Date: 1/8/13
 * Time: 9:03 AM
 * To change this template use File | Settings | File Templates.
 */
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.core.client.GWT;

@RemoteServiceRelativePath("adminService")
public interface AdminService extends RemoteService {

    public Boolean createStudent(String email, String password, String firstName, String lastName, String contact,
                                 String country, String countryCode, String school, String lecturerFirstName, String lecturerLastName,
                                 String lecturerEmail, String language) throws IllegalArgumentException;

    public static class Util {
        private static final AdminServiceAsync Instance = (AdminServiceAsync) GWT.create(AdminService.class);

        public static AdminServiceAsync getInstance() {
            return Instance;
        }
    }
}