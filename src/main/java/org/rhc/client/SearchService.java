package org.rhc.client;

/**
 * Created with IntelliJ IDEA.
 * User: Jun
 * Date: 1/8/13
 * Time: 9:45 AM
 * To change this template use File | Settings | File Templates.
 */

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import org.rhc.shared.Student;

import java.util.List;

@RemoteServiceRelativePath("SearchService")
public interface SearchService extends RemoteService{

    public List<Student> loadDB(String search, String field) throws IllegalArgumentException;
    public List<Student> displayDB() throws IllegalArgumentException;
    public Boolean updateProfileData(Student studentRow) throws IllegalArgumentException;
    public Boolean updateEmail(String email, String newEmail) throws IllegalArgumentException;

    public static class Util {
        private static final SearchServiceAsync Instance= (SearchServiceAsync) GWT.create(SearchService.class);

        public static SearchServiceAsync getInstance() {
            return Instance;
        }
    }
}
