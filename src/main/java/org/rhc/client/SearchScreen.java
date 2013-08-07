package org.rhc.client;

/**
 * Created with IntelliJ IDEA.
 * User: Jun
 * Date: 1/8/13
 * Time: 9:32 AM
 * To change this template use File | Settings | File Templates.
 */

import au.com.bytecode.opencsv.CSVWriter;
import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.EditTextCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.SelectionCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.*;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.view.client.*;
import org.rhc.shared.Student;

import java.util.ArrayList;
import java.util.List;

public class SearchScreen extends Composite {

    interface SearchScreenUiBinder extends UiBinder<Widget, SearchScreen>{}

    private static SearchScreenUiBinder UiBinder = GWT.create(SearchScreenUiBinder.class);

    private static final ProvidesKey<Student > KEY_PROVIDER =
            new ProvidesKey<Student>() {
                @Override
                public Object getKey(Student item) {
                    return item.getEmail();

                }
            };

    @UiField ListBox resultListBox;
    @UiField TextBox searchTextBox;
    @UiField Button searchButton;
    @UiField ListBox fieldListBox;
    @UiField CellTable<Student> cellTable = new CellTable<Student>(KEY_PROVIDER);
    private String search;
    private String field;
    private List listOfEmail;

    private SearchServiceAsync searchService = null;

    public SearchScreen() {
        initWidget(UiBinder.createAndBindUi(this));
        initTable();
        displayDB();
    }

    public void initTable(){
        cellTable.setKeyboardSelectionPolicy(HasKeyboardSelectionPolicy.KeyboardSelectionPolicy.ENABLED);

        final SelectionModel<Student> selectionModel = new MultiSelectionModel<Student>(KEY_PROVIDER);
        cellTable.setSelectionModel(selectionModel, DefaultSelectionEventManager.<Student> createCheckboxManager());

        Column<Student, Boolean> checkColumn = new Column<Student, Boolean>(
                new CheckboxCell(true, false)) {
            @Override
            public Boolean getValue(Student student) {
// Get the value from the selection model.
                return selectionModel.isSelected(student);
            }
        };

        //Start of getting emails to list
        checkColumn.setFieldUpdater(new FieldUpdater<Student, Boolean>() {
            @Override
            public void update(int i, Student student, Boolean aBoolean) {
//                listOfEmail.add(student.getEmail());
                resultListBox.addItem(student.getEmail().toString());
            }
        });
        //End of getting email

        cellTable.addColumn(checkColumn, SafeHtmlUtils.fromSafeConstant("<br/>"));
        cellTable.setColumnWidth(checkColumn, 40, Style.Unit.PX);


//Contestant Email
        Column<Student, String> emailColumn = new Column<Student, String>(new EditTextCell()) {
            @Override
            public String getValue(Student student) {
                return student.getEmail();
            }
        };
        emailColumn.setSortable(true);

        emailColumn.setFieldUpdater(new FieldUpdater<Student, String>() {
            @Override
            public void update(int i, Student student, String s) {
                searchService = SearchService.Util.getInstance();
//student.setEmail(s);
                searchService.updateEmail(student.getEmail(), s, new AsyncCallback<Boolean>() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        resultListBox.addItem("Fail");
                    }

                    @Override
                    public void onSuccess(Boolean aBoolean) {
                        resultListBox.addItem("Pass");

                    }
                });
            }
        });
        cellTable.redraw();
        cellTable.setColumnWidth(emailColumn, 20, Style.Unit.PCT);
        cellTable.addColumn(emailColumn, "Email Address");

//Contestant First Name
        Column<Student, String> fnColumn = new Column<Student, String>(new EditTextCell()) {
            @Override
            public String getValue(Student student) {
                return student.getFirstName();
            }
        };
        fnColumn.setSortable(true);

        fnColumn.setFieldUpdater(new FieldUpdater<Student, String>() {
            @Override
            public void update(int i, Student student, String s) {
                searchService = SearchService.Util.getInstance();
                student.setFirstName(s);

                searchService.updateProfileData(student, new AsyncCallback<Boolean>() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        resultListBox.addItem("Fail");
                    }

                    @Override
                    public void onSuccess(Boolean aBoolean) {
                        resultListBox.addItem("Pass");
                    }
                });
            }
        });
        cellTable.redraw();
        cellTable.setColumnWidth(fnColumn, 20, Style.Unit.PCT);
        cellTable.addColumn(fnColumn, "First Name");

//Contestant Last Name
        Column<Student, String> lnColumn = new Column<Student, String>(new EditTextCell()) {
            @Override
            public String getValue(Student student) {
                return student.getLastName();
            }
        };
        lnColumn.setSortable(true);

        lnColumn.setFieldUpdater(new FieldUpdater<Student, String>() {
            @Override
            public void update(int i, Student student, String s) {
                searchService = SearchService.Util.getInstance();
                student.setLastName(s);

                searchService.updateProfileData(student, new AsyncCallback<Boolean>() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        resultListBox.addItem("Fail");
                    }

                    @Override
                    public void onSuccess(Boolean aBoolean) {
                        resultListBox.addItem("Pass");

                    }
                });
            }
        });
        cellTable.redraw();
        cellTable.setColumnWidth(lnColumn, 20, Style.Unit.PCT);
        cellTable.addColumn(lnColumn, "Last Name");

//Contestant Country ListBox
        List countryList = new ArrayList();
        countryList.add("Singapore");
        countryList.add("Malaysia");
        countryList.add("Thailand");
        countryList.add("China");
        countryList.add("Hong Kong");
        countryList.add("Taiwan");

        SelectionCell countryCell = new SelectionCell(countryList);
        Column<Student, String> countryColumn = new Column<Student, String> (countryCell){
            @Override
            public String getValue(Student student) {
                return student.getCountry();
            }
        };
        countryColumn.setSortable(true);

        countryColumn.setFieldUpdater(new FieldUpdater<Student, String>() {
            @Override
            public void update(int index, Student student, String s) {
                student.setCountry(s);
                searchService.updateProfileData(student, new AsyncCallback<Boolean>() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        resultListBox.addItem("Fail");
                    }

                    @Override
                    public void onSuccess(Boolean aBoolean) {
                        resultListBox.addItem("Pass");

                    }
                });
            }
        });

        cellTable.redraw();
        cellTable.setColumnWidth(countryColumn, 200, Style.Unit.PCT);
        cellTable.addColumn(countryColumn, "Country");

//Contestant Country Code ListBox
        List countryCodeList = new ArrayList();
        countryCodeList.add("+65");
        countryCodeList.add("+60");
        countryCodeList.add("+66");
        countryCodeList.add("+86");
        countryCodeList.add("+852");
        countryCodeList.add("+886");

        SelectionCell countryCodeCell = new SelectionCell(countryCodeList);
        Column<Student, String> countryCodeColumn = new Column<Student, String> (countryCodeCell){
            @Override
            public String getValue(Student student) {
                return student.getCountryCode();
            }
        };
        countryCodeColumn.setSortable(true);

        countryCodeColumn.setFieldUpdater(new FieldUpdater<Student, String>() {
            @Override
            public void update(int index, Student student, String s) {
                student.setCountryCode(s);
                searchService.updateProfileData(student, new AsyncCallback<Boolean>() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        resultListBox.addItem("Fail");
                    }

                    @Override
                    public void onSuccess(Boolean aBoolean) {
                        resultListBox.addItem("Pass");

                    }
                });
            }
        });

        cellTable.redraw();
        cellTable.setColumnWidth(countryCodeColumn, 20, Style.Unit.PCT);
        cellTable.addColumn(countryCodeColumn, "Country Code");

//Contestant Contact Int
        Column<Student, String> contactColumn = new Column<Student, String>(new EditTextCell()) {
            @Override
            public String getValue(Student student) {
                return student.getContact();
            }
        };
        contactColumn.setSortable(true);

        contactColumn.setFieldUpdater(new FieldUpdater<Student, String>() {
            @Override
            public void update(int i, Student student, String s) {
                searchService = SearchService.Util.getInstance();
                student.setContact(s);

                searchService.updateProfileData(student, new AsyncCallback<Boolean>() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        resultListBox.addItem("Fail");
                    }

                    @Override
                    public void onSuccess(Boolean aBoolean) {
                        resultListBox.addItem("Pass");

                    }
                });
            }
        });
        cellTable.redraw();
        cellTable.setColumnWidth(contactColumn, 20, Style.Unit.PCT);
        cellTable.addColumn(contactColumn, "Contact");


//Contestant School
        Column<Student, String> schooolColumn = new Column<Student, String>(new EditTextCell()) {
            @Override
            public String getValue(Student student) {
                return student.getSchool();
            }
        };
        schooolColumn.setSortable(true);

        schooolColumn.setFieldUpdater(new FieldUpdater<Student, String>() {
            @Override
            public void update(int i, Student student, String s) {
                searchService = SearchService.Util.getInstance();
                student.setSchool(s);

                searchService.updateProfileData(student, new AsyncCallback<Boolean>() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        resultListBox.addItem("Fail");
                    }

                    @Override
                    public void onSuccess(Boolean aBoolean) {
                        resultListBox.addItem("Pass");

                    }
                });
            }
        });
        cellTable.redraw();
        cellTable.setColumnWidth(schooolColumn, 20, Style.Unit.PCT);
        cellTable.addColumn(schooolColumn, "School");

//Contestant Lecturer First Name
        Column<Student, String> lecFNColumn = new Column<Student, String>(new EditTextCell()) {
            @Override
            public String getValue(Student student) {
                return student.getLecturerFirstName();
            }
        };
        lecFNColumn.setSortable(true);

        lecFNColumn.setFieldUpdater(new FieldUpdater<Student, String>() {
            @Override
            public void update(int i, Student student, String s) {
                searchService = SearchService.Util.getInstance();
                student.setLecturerFirstName(s);

                searchService.updateProfileData(student, new AsyncCallback<Boolean>() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        resultListBox.addItem("Fail");
                    }

                    @Override
                    public void onSuccess(Boolean aBoolean) {
                        resultListBox.addItem("Pass");

                    }
                });
            }
        });
        cellTable.redraw();
        cellTable.setColumnWidth(lecFNColumn, 20, Style.Unit.PCT);
        cellTable.addColumn(lecFNColumn, "Lecturer First Name");


//Contestant Lecturer Last Name
        Column<Student, String> lecLNColumn = new Column<Student, String>(new EditTextCell()) {
            @Override
            public String getValue(Student student) {
                return student.getLecturerLastName();
            }
        };
        lecLNColumn.setSortable(true);

        lecLNColumn.setFieldUpdater(new FieldUpdater<Student, String>() {
            @Override
            public void update(int i, Student student, String s) {
                searchService = SearchService.Util.getInstance();
                student.setLecturerLastName(s);

                searchService.updateProfileData(student, new AsyncCallback<Boolean>() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        resultListBox.addItem("Fail");
                    }

                    @Override
                    public void onSuccess(Boolean aBoolean) {
                        resultListBox.addItem("Pass");

                    }
                });
            }
        });
        cellTable.redraw();
        cellTable.setColumnWidth(lecLNColumn, 20, Style.Unit.PCT);
        cellTable.addColumn(lecLNColumn, "Lecturer Last Name");


//Contestant Lecturer Email
        Column<Student, String> lecEmailColumn = new Column<Student, String>(new EditTextCell()) {
            @Override
            public String getValue(Student student) {
                return student.getLecturerEmail();
            }
        };
        lecEmailColumn.setSortable(true);

        lecEmailColumn.setFieldUpdater(new FieldUpdater<Student, String>() {
            @Override
            public void update(int i, Student student, String s) {
                searchService = SearchService.Util.getInstance();
                student.setLecturerEmail(s);

                searchService.updateProfileData(student, new AsyncCallback<Boolean>() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        resultListBox.addItem("Fail");
                    }

                    @Override
                    public void onSuccess(Boolean aBoolean) {
                        resultListBox.addItem("Pass");

                    }
                });
            }
        });
        cellTable.redraw();
        cellTable.setColumnWidth(lecEmailColumn, 20, Style.Unit.PCT);
        cellTable.addColumn(lecEmailColumn, "Lecturer Email");

//Contestant Country ListBox
        List languageList = new ArrayList();
        languageList.add("English");
        languageList.add("Chinese (Simplified)");
        languageList.add("Chinese (Tranditional)");

        SelectionCell languageCell = new SelectionCell(languageList);
        Column<Student, String> languageColumn = new Column<Student, String> (languageCell){
            @Override
            public String getValue(Student student) {
                return student.getLanguage();
            }
        };
        languageColumn.setSortable(true);

        languageColumn.setFieldUpdater(new FieldUpdater<Student, String>() {
            @Override
            public void update(int index, Student student, String s) {
                student.setLanguage(s);
                searchService.updateProfileData(student, new AsyncCallback<Boolean>() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        resultListBox.addItem("Fail");
                    }

                    @Override
                    public void onSuccess(Boolean aBoolean) {
                        resultListBox.addItem("Pass");

                    }
                });
            }
        });

        cellTable.redraw();
        cellTable.setColumnWidth(languageColumn, 130, Style.Unit.PCT);
        cellTable.addColumn(languageColumn, "Language");

//Contestant Verified CheckBox


        Column<Student, Boolean> verifiedColumn = new Column<Student, Boolean>(
                new CheckboxCell(true, false)) {
            @Override
            public Boolean getValue(Student student) {
// Get the value from the selection model.
                return student.getVerified();
            }
        };

        verifiedColumn.setFieldUpdater(new FieldUpdater<Student, Boolean>() {
            @Override
            public void update(int index, Student student, Boolean s) {
                student.setVerified(s);
                searchService.updateProfileData(student, new AsyncCallback<Boolean>() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        resultListBox.addItem("Fail");
                    }

                    @Override
                    public void onSuccess(Boolean aBoolean) {
                        resultListBox.addItem("Pass");

                    }
                });
            }
        });

        cellTable.addColumn(verifiedColumn, "Verified Account");
        cellTable.setColumnWidth(checkColumn, 40, Style.Unit.PX);

//Contestant Status
        Column<Student, Boolean> statusColumn = new Column<Student, Boolean>(
                new CheckboxCell(true, false)) {
            @Override
            public Boolean getValue(Student student) {
// Get the value from the selection model.
                return student.getStatus();
            }
        };

        statusColumn.setFieldUpdater(new FieldUpdater<Student, Boolean>() {
            @Override
            public void update(int index, Student student, Boolean s) {
                student.setStatus(s);
                searchService.updateProfileData(student, new AsyncCallback<Boolean>() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        resultListBox.addItem("Fail");
                    }

                    @Override
                    public void onSuccess(Boolean aBoolean) {
                        resultListBox.addItem("Pass");

                    }
                });
            }
        });

        cellTable.addColumn(statusColumn, "Enabled");
        cellTable.setColumnWidth(statusColumn, 40, Style.Unit.PX);


        SimplePager pager = new SimplePager();
        pager.setDisplay(cellTable);

        VerticalPanel vp = new VerticalPanel();
        vp.add(cellTable);
        vp.add(pager);
        cellTable.redraw();
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


//    @UiHandler("resultListBox")
//    public void handleCleck(ClickEvent event){
//        for (int i=0; i < listOfEmail.size(); i++){
//            resultListBox.addItem(listOfEmail.get(i).toString());
//        }
//    }
}