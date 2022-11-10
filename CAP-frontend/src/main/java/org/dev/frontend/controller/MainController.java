package org.dev.frontend.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.dev.api.enums.Role;
import org.dev.frontend.store.StoreRestUtils;

import java.io.IOException;
import java.nio.file.AccessDeniedException;

public class MainController {
    private final StoreRestUtils storeRestUtils = StoreRestUtils.getInstance();

    @FXML
    private Button register;

    @FXML
    private Button logInBtn;

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private VBox all;

    @FXML
    private Label errorLbl;

    @FXML
    private void initialize(){
        all.setStyle("-fx-background-color: #515151");
        //if(storeRestUtils.anyUsersPresent()) {

        //}
        logInBtn.setDefaultButton(true);
    }

    @FXML
    private void handleLogInBtnClicked(ActionEvent event) throws IOException {
        try {
            if(storeRestUtils.login(username.getText() , password.getText()) == Role.ADMIN){

            } else if (storeRestUtils.login(username.getText() , password.getText()) == Role.CORPORATE) {

            } else {

            }
        } catch (AccessDeniedException e) {
            errorLbl.setText("Non matching login & password");
        }
    }
}
