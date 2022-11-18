package org.dev.frontend.CAP.controller;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.dev.api.CAP.enums.Role;
import org.dev.api.CAP.model.LogInDateDTO;
import org.dev.frontend.CAP.Style;
import org.dev.frontend.CAP.Main;
import org.dev.frontend.CAP.StringConstants;
import org.dev.frontend.CAP.store.StoreRestUtils;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;

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
    private void initialize() throws IOException {
        username.setText("");
        password.setText("");
        all.setStyle(Style.backgroundGreyStyle);
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
        storeRestUtils.login("Empty","12345");

        if(storeRestUtils.anyUsersPresent()) {
            handleRegisterBtnClicked();
        }
        logInBtn.setDefaultButton(true);
    }

    @FXML
    private void handleLogInBtnClicked() {
        try {
            if(storeRestUtils.login(username.getText() , password.getText()) == Role.ADMIN){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("adminControlWindow.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage primaryStage = new Stage();
                primaryStage.setTitle(StringConstants.programName);
                primaryStage.setScene(scene);
                primaryStage.show();
                primaryStage.setResizable(true);
                errorLbl.setText("");
            } else if (storeRestUtils.login(username.getText() , password.getText()) == Role.CORPORATE) {
                LogInDateDTO logInDateDTO = new LogInDateDTO();
                logInDateDTO.setLogin(username.getText());
                LocalDateTime time = LocalDateTime.now();
                logInDateDTO.setDate(time);
                storeRestUtils.saveNewEntryDate(logInDateDTO);
                errorLbl.setText("");

            } else if (storeRestUtils.login(username.getText() , password.getText()) == Role.PERSONAL){
                LogInDateDTO logInDateDTO = new LogInDateDTO();
                logInDateDTO.setLogin(username.getText());
                LocalDateTime time = LocalDateTime.now();
                logInDateDTO.setDate(time);
                storeRestUtils.saveNewEntryDate(logInDateDTO);
                errorLbl.setText("");

            } else {
                errorLbl.setText("Non matching login & password");
            }
        } catch (AccessDeniedException e) {
            errorLbl.setText("Non matching login & password");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void handleRegisterBtnClicked() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("registerWindow.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage primaryStage = new Stage();
        primaryStage.setTitle(StringConstants.programName);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setResizable(false);
        primaryStage.setOnCloseRequest(e -> {
            Platform.runLater(() -> {try {Main.showMainWindow();} catch (Exception ignored) {}});
            try {
                initialize();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        Platform.runLater(() -> {try {Main.hideMainWindow();} catch (Exception ignored) {}});
    }
}
