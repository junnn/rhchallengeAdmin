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
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import org.rhc.shared.Student;

import java.util.List;

public class SearchScreen extends Composite {

    interface SearchScreenUiBinder extends UiBinder<Widget, SearchScreen>{}

    private static SearchScreenUiBinder UiBinder = GWT.create(SearchScreenUiBinder.class);

    @UiField ListBox resultListBox;
    @UiField TextBox searchTextBox;
    @UiField Button searchButton;
    @UiField ListBox fieldListBox;
    private String search;
    private String field;

    private SearchServiceAsync searchService = null;

    public SearchScreen() {
        initWidget(UiBinder.createAndBindUi(this));
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
}

