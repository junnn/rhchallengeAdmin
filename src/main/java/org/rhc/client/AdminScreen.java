package org.rhc.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;

public class AdminScreen extends Composite {
    interface AdminScreenUiBinder extends UiBinder<Widget, AdminScreen> {

    }

    private static AdminScreenUiBinder UiBinder = GWT.create(AdminScreenUiBinder.class);

    @UiField TextBox emailField;
    @UiField TextBox passwordField;
    @UiField TextBox confirmPasswordField;
    @UiField TextBox firstNameField;
    @UiField TextBox lastNameField;
    @UiField TextBox contactField;
    @UiField ListBox countryField;
    @UiField ListBox countryCodeField;
    @UiField TextBox schoolField;
    @UiField TextBox lecturerFirstNameField;
    @UiField TextBox lecturerLastNameField;
    @UiField TextBox lecturerEmailField;
    @UiField ListBox languageField;
    @UiField Button createButton;
    @UiField Label errorLabel;
    @UiField Label errorLabel3;
    @UiField CheckBox verifyUser;
    @UiField Button generatePasswordButton;



    private AdminServiceAsync adminService = null;

    public AdminScreen(){
        initWidget(UiBinder.createAndBindUi(this));
    }

    @UiHandler("countryField")
    public void handleCountryChange(ChangeEvent event) {
        switch (countryField.getSelectedIndex()) {
            // Singapore
            case 0:
                languageField.setSelectedIndex(0);
                countryCodeField.setSelectedIndex(0);
                break;
            // Malaysia
            case 1:
                languageField.setSelectedIndex(0);
                countryCodeField.setSelectedIndex(1);
                break;
            // Thailand
            case 2:
                languageField.setSelectedIndex(0);
                countryCodeField.setSelectedIndex(2);
                break;
            // China
            case 3:
                languageField.setSelectedIndex(1);
                countryCodeField.setSelectedIndex(3);
                break;
            // Hong Kong
            case 4:
                languageField.setSelectedIndex(2);
                countryCodeField.setSelectedIndex(4);
                break;
            // Taiwan
            case 5:
                languageField.setSelectedIndex(2);
                countryCodeField.setSelectedIndex(5);
                break;
        }
    }

    @UiHandler("createButton")
    public void handleCreateButtonClick(ClickEvent event) {
        createStudent();
    }

    @UiHandler({"emailField", "passwordField", "confirmPasswordField", "firstNameField",
            "lastNameField", "contactField", "countryField", "countryCodeField",
            "schoolField", "lecturerFirstNameField", "lecturerLastNameField",
            "lecturerEmailField", "languageField"})
    public void handleKeyUp(KeyUpEvent event) {
        if(event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
            createStudent();
        }
    }

    private void createStudent() {

        String email = emailField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String contact = contactField.getText();
        String country = countryField.getItemText(countryField.getSelectedIndex());
        String countryCode = countryCodeField.getItemText(countryCodeField.getSelectedIndex());
        String school = schoolField.getText();
        String lecturerFirstName = lecturerFirstNameField.getText();
        String lecturerLastName = lecturerLastNameField.getText();
        String lecturerEmail = lecturerEmailField.getText();
        String language = languageField.getItemText(languageField.getSelectedIndex());

        adminService = AdminService.Util.getInstance();

        createButton.setEnabled(false);

        if (verifyUser.getValue().toString() == "false"){

        adminService.createStudent(email, password, firstName, lastName, contact,
                country, countryCode, school, lecturerFirstName, lecturerLastName,
                lecturerEmail, language, new AsyncCallback<Boolean>() {
            @Override
            public void onFailure(Throwable throwable) {
                errorLabel.setText("An unexpected error has occurred, please try again later!");
                createButton.setEnabled(true);
            }

            @Override
            public void onSuccess(Boolean bool) {
                if(bool){
                    errorLabel.setText("User added successfully!");
                    createButton.setEnabled(true);
                }

                else {
                    errorLabel.setText("Your registration is a failure, please double check your inputs!");
                    createButton.setEnabled(true);
                }
            }
        });
        }
        else{
            adminService.createAndVerifyStudent(email, password, firstName, lastName, contact,
                    country, countryCode, school, lecturerFirstName, lecturerLastName,
                    lecturerEmail, language, new AsyncCallback<Boolean>() {
                @Override
                public void onFailure(Throwable throwable) {
                    errorLabel.setText("An unexpected error has occurred, please try again later!");
                    createButton.setEnabled(true);
                }

                @Override
                public void onSuccess(Boolean aBoolean) {
                    if(aBoolean){
                        errorLabel.setText("User added & verified successfully!");
                        createButton.setEnabled(true);
                    }

                    else {
                        errorLabel.setText("Your registration is a failure, please double check your inputs!");
                        createButton.setEnabled(true);
                    }
                }
            });
        }
    }
    @UiHandler("generatePasswordButton")
    public void handleGeneratePasswordButton(ClickEvent event){
        adminService = AdminService.Util.getInstance();
        adminService.generateRandomPassword(new AsyncCallback<String>() {
            @Override
            public void onFailure(Throwable throwable) {
                errorLabel3.setText("Password could not be generated");
            }

            @Override
            public void onSuccess(String s) {
                passwordField.setText(s);
                confirmPasswordField.setText(s);
            }
        });
    }
}