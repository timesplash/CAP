package org.dev.frontend.CAP.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.dev.api.CAP.enums.Role;
import org.dev.api.CAP.model.LogInDateDTO;
import org.dev.frontend.CAP.Main;
import org.dev.frontend.CAP.StringConstants;
import org.dev.frontend.CAP.store.StoreRestUtils;

import java.io.IOException;
import java.net.URL;
import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    private final StoreRestUtils storeRestUtils = StoreRestUtils.getInstance();

    @FXML
    private Button logInBtn;

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Label errorLbl;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        reset();

        if(storeRestUtils.anyUsersPresent()) {
            try {
                handleRegisterBtnClicked();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        logInBtn.setDefaultButton(true);
    }

    @FXML
    private void handleLogInBtnClicked() {
        try {
            if(storeRestUtils.login(username.getText() , password.getText()) == Role.ADMIN){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("adminControlWindow.fxml"));
                stageSetup(loader);
            } else if (storeRestUtils.login(username.getText() , password.getText()) == Role.CORPORATE ||
                    storeRestUtils.login(username.getText() , password.getText()) == Role.PERSONAL) {
                LogInDateDTO logInDateDTO = new LogInDateDTO();
                logInDateDTO.setLogin(username.getText());
                LocalDateTime time = LocalDateTime.now();
                logInDateDTO.setDate(time);
                storeRestUtils.saveNewEntryDate(logInDateDTO);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("userControlWindow.fxml"));
                stageSetup(loader);
            } else {
                errorLbl.setText("Non matching login & password");
            }
        } catch (AccessDeniedException e) {
            errorLbl.setText("Non matching login & password");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void stageSetup(FXMLLoader loader) throws IOException {
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage primaryStage = new Stage();
        primaryStage.setTitle(StringConstants.programName);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(600);
        primaryStage.setResizable(true);
        errorLbl.setText("");
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
            reset();
        });
        Platform.runLater(() -> {try {Main.hideMainWindow();} catch (Exception ignored) {}});
    }

    private void reset() {
        username.setText("");
        password.setText("");
    }
}
