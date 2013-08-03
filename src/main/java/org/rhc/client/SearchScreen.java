package org.rhc.client;

/**
 * Created with IntelliJ IDEA.
 * User: Jun
 * Date: 1/8/13
 * Time: 9:32 AM
 * To change this template use File | Settings | File Templates.
 */

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
    }

    @UiHandler("searchButton")
    public void handleOnClick(ClickEvent event){
        resultListBox.clear();
        search = searchTextBox.getText();
        field = fieldListBox.getItemText(fieldListBox.getSelectedIndex());

        if (field.equals("Email")){
            field = "email";
        }
        if (field.equals("Country")){
            field = "country";
        }

        searchService = SearchService.Util.getInstance();
        searchService.loadDB(search, field, new AsyncCallback<List<Student>>(){
            @Override
            public void onFailure(Throwable throwable) {
                resultListBox.addItem("Fail");
            }

            @Override
            public void onSuccess(List <Student> student) {
                for(int i=0; i< student.size(); i++){
                    resultListBox.addItem(student.get(i).getEmail());
                }
            }
        });
        searchTextBox.selectAll();
    }

    public void initTable(){
        cellTable.setKeyboardSelectionPolicy(HasKeyboardSelectionPolicy.KeyboardSelectionPolicy.ENABLED);

        TextColumn<Student> emailColumn = new TextColumn<Student>() {
            @Override
            public String getValue(Student student) {
                return student.getEmail();
            }
        };

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
        cellTable.addColumn(lnColumn, "First Name");

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
                return student.getLastName();
            }
        };
        cellTable.addColumn(ccColumn, "Country Code");

        TextColumn<Student> contactColumn = new TextColumn<Student>() {
            @Override
            public String getValue(Student student) {
                return student.getContact();
            }
        };
        cellTable.addColumn(contactColumn, "Contact");

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
        cellTable.addColumn(lecFnColumn, "Lecturer FN");

        TextColumn<Student> lecLnColumn = new TextColumn<Student>() {
            @Override
            public String getValue(Student student) {
                return student.getLecturerLastName();
            }
        };
        cellTable.addColumn(lecLnColumn, "Lecturer LN");


        TextColumn<Student> lecEmailColumn = new TextColumn<Student>() {
            @Override
            public String getValue(Student student) {
                return student.getLecturerFirstName();
            }
        };
        cellTable.addColumn(lecEmailColumn, "Lecturer Email");


        TextColumn<Student> verifiedColumn = new TextColumn<Student>() {
            @Override
            public String getValue(Student student) {
                return student.getVerified().toString();
            }
        };
        cellTable.addColumn(verifiedColumn, "Verified");

        TextColumn<Student> statusColumn = new TextColumn<Student>() {
            @Override
            public String getValue(Student student) {
                return student.getStatus().toString();
            }
        };
        cellTable.addColumn(statusColumn, "Account Activated");

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

        SimplePager pager = new SimplePager();
        pager.setDisplay(cellTable);

        VerticalPanel vp = new VerticalPanel();
        vp.add(cellTable);
        vp.add(pager);

        RootPanel.get().add(vp);

    }
}




