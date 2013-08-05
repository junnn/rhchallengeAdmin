package org.rhc.client;

/**
 * Created with IntelliJ IDEA.
 * User: Jun
 * Date: 1/8/13
 * Time: 9:32 AM
 * To change this template use File | Settings | File Templates.
 */

import au.com.bytecode.opencsv.CSVWriter;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.HasData;
import org.rhc.shared.Student;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class SearchScreen extends Composite {

    interface SearchScreenUiBinder extends UiBinder<Widget, SearchScreen>{}

    private static SearchScreenUiBinder UiBinder = GWT.create(SearchScreenUiBinder.class);

    @UiField ListBox resultListBox;
    @UiField TextBox searchTextBox;
    @UiField Button searchButton;
    @UiField ListBox fieldListBox;
    @UiField CellTable<Student> cellTable = new CellTable<Student>();
    private String search;
    private String field;

    private SearchServiceAsync searchService = null;

    public SearchScreen() {
        initWidget(UiBinder.createAndBindUi(this));
        initTable();
        displayDB();

    }


    public void initTable(){
        cellTable.setKeyboardSelectionPolicy(HasKeyboardSelectionPolicy.KeyboardSelectionPolicy.ENABLED);

        TextColumn<Student> emailColumn = new TextColumn<Student>() {
            @Override
            public String getValue(Student student) {
                return student.getEmail();
            }
        };
        cellTable.addColumn(emailColumn, "Email Address");

        TextColumn<Student> fnColumn = new TextColumn<Student>() {
            @Override
            public String getValue(Student student) {
                return student.getFirstName();
            }
        };
        cellTable.addColumn(fnColumn, "First Name");

        TextColumn<Student> lnColumn = new TextColumn<Student>() {
            @Override
            public String getValue(Student student) {
                return student.getLastName();
            }
        };
        cellTable.addColumn(lnColumn, "Last Name");

        TextColumn<Student> countryColumn = new TextColumn<Student>() {
            @Override
            public String getValue(Student student) {
                return student.getCountry();
            }
        };
        cellTable.addColumn(countryColumn, "Country");

        TextColumn<Student> ccColumn = new TextColumn<Student>() {
            @Override
            public String getValue(Student student) {
                return student.getCountryCode();
            }
        };
        cellTable.addColumn(ccColumn, "Country Code");

        TextColumn<Student> contactColumn = new TextColumn<Student>() {
            @Override
            public String getValue(Student student) {
                return student.getContact();
            }
        };
        cellTable.addColumn(contactColumn, "Contact No.");

        TextColumn<Student> schoolColumn = new TextColumn<Student>() {
            @Override
            public String getValue(Student student) {
                return student.getSchool();
            }
        };
        cellTable.addColumn(schoolColumn, "School");

        TextColumn<Student> lecFnColumn = new TextColumn<Student>() {
            @Override
            public String getValue(Student student) {
                return student.getLecturerFirstName();
            }
        };
        cellTable.addColumn(lecFnColumn, "Lecturer's First Name");

        TextColumn<Student> lecLnColumn = new TextColumn<Student>() {
            @Override
            public String getValue(Student student) {
                return student.getLecturerLastName();
            }
        };
        cellTable.addColumn(lecLnColumn, "Lecturer's Last Name");


        TextColumn<Student> lecEmailColumn = new TextColumn<Student>() {
            @Override
            public String getValue(Student student) {
                return student.getLecturerEmail();
            }
        };
        cellTable.addColumn(lecEmailColumn, "Lecturer's Email");


        TextColumn<Student> verifiedColumn = new TextColumn<Student>() {
            @Override
            public String getValue(Student student) {
                return student.getVerified().toString();
            }
        };
        cellTable.addColumn(verifiedColumn, "Verification Status");

        TextColumn<Student> statusColumn = new TextColumn<Student>() {
            @Override
            public String getValue(Student student) {
                return student.getStatus().toString();
            }
        };
        cellTable.addColumn(statusColumn, "Account Status");

        SimplePager pager = new SimplePager();
        pager.setDisplay(cellTable);

        VerticalPanel vp = new VerticalPanel();
        vp.add(cellTable);
        vp.add(pager);

        RootPanel.get().add(vp);
    }

    public void displayDB(){
        AsyncDataProvider<Student> provider = new AsyncDataProvider<Student>() {
            @Override
            protected void onRangeChanged(HasData<Student> students) {
                final int start =  students.getVisibleRange().getStart();
                int length = students.getVisibleRange().getLength();
                searchService = SearchService.Util.getInstance();
                searchService.displayDB(new AsyncCallback<List<Student>>() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        resultListBox.addItem("Fail");
                    }

                    @Override
                    public void onSuccess(List<Student> students){
                        updateRowData(start, students);

                    }
                });
            }
        };
        provider.addDataDisplay(cellTable);
    }

    @UiHandler("searchButton")
    public void handleOnClick(ClickEvent event){

        search = searchTextBox.getText();
        field = fieldListBox.getItemText(fieldListBox.getSelectedIndex());

        if (field.equals("Email")){
            field = "email";
        }
        if (field.equals("First Name")){
            field = "firstName";
        }
        if (field.equals("Last Name")){
            field = "lastName";
        }
        if (field.equals("Contact No.")){
            field = "contact";
        }
        if (field.equals("Country")){
            field = "country";
        }
        if (field.equals("Country Code")){
            field = "countryCode";
        }
        if (field.equals("School")){
            field = "school";
        }
        if (field.equals("Lecturer's First Name")){
            field = "lecturerFirstName";
        }
        if (field.equals("Lecturer's Last Name")){
            field = "lecturerLastName";
        }
        if (field.equals("Lecturer's Email")){
            field = "lecturerEmail";
        }
        if (field.equals("Verification Status")){
            field = "verified";
        }
        if (field.equals("Account Status")){
            field = "status";
        }


        AsyncDataProvider<Student> provider = new AsyncDataProvider<Student>() {
            @Override
            protected void onRangeChanged(HasData<Student> students) {
                final int start =  students.getVisibleRange().getStart();
                int length = students.getVisibleRange().getLength();
                searchService = SearchService.Util.getInstance();
                searchService.loadDB(search, field, new AsyncCallback<List<Student>>() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        resultListBox.addItem("Fail");
                        resultListBox.addItem(field);
                    }

                    @Override
                    public void onSuccess(List<Student> students){
                        updateRowData(start, students);
                        updateRowCount(students.size(), true);
                    }
                });
            }
        };
        provider.addDataDisplay(cellTable);

    }

}




