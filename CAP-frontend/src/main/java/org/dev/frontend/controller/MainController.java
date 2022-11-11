package org.dev.frontend.controller;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.dev.api.enums.Role;
import org.dev.frontend.Style;
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
    private Label usernameLbl;

    @FXML
    private Label passwordLbl;

    @FXML
    private void initialize(){
        all.setStyle(Style.backgroundStyle);
        errorLbl.setStyle(Style.labelWhiteStyle);
        usernameLbl.setStyle(Style.labelWhiteStyle);
        passwordLbl.setStyle(Style.labelWhiteStyle);

        password.setStyle(Style.textFieldStyle);
        username.setStyle(Style.textFieldStyle);
        logInBtn.styleProperty().bind(Bindings.when(logInBtn.hoverProperty())
                .then(Style.buttonStyleHovered)
                .otherwise(Style.buttonStyle));
        register.styleProperty().bind(Bindings.when(register.hoverProperty())
                .then(Style.buttonBorderlessStyleHovered)
                .otherwise(Style.buttonBorderlessStyle));

        if(storeRestUtils.anyUsersPresent()) {

        }
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
