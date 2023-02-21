package org.dev.frontend.CAP.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.dev.api.CAP.model.LogInDateDTO;
import org.dev.frontend.CAP.store.RegisterStore;
import org.dev.frontend.CAP.store.StoreRestUtils;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

/**
 * RegisterController - controller for a registration window
 */
public class RegisterController implements Initializable {

    private final RegisterStore registerStore = RegisterStore.getStore();

    private final StoreRestUtils storeRestUtils = StoreRestUtils.getInstance();

    @FXML
    private Label roleLbl;

    @FXML
    private Label saveMessage;

    @FXML
    private TextField username;

    @FXML
    private TextField password;

    @FXML
    private TextField question;

    @FXML
    private TextField answer;

    @FXML
    private ComboBox<String> role;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        reset();

        username.textProperty().bindBidirectional(registerStore.getLogin());
        password.textProperty().bindBidirectional(registerStore.getPassword());
        question.textProperty().bindBidirectional(registerStore.getKeyQuestion());
        answer.textProperty().bindBidirectional(registerStore.getKeyAnswer());
        if (storeRestUtils.anyUsersPresent()) {
            role.setDisable(true);
            role.setVisible(false);
            roleLbl.setVisible(false);
        } else {
            role.setDisable(false);
            role.setVisible(true);
            roleLbl.setVisible(true);
        }
        role.setItems(registerStore.getRoles());
        role.getSelectionModel().selectFirst();
        registerStore.bindRolesToEnum(role.getSelectionModel().selectedIndexProperty());
    }

    @FXML
    private void createBtnAction() {
        try {
            if (!storeRestUtils.anyUsersPresent()) {
                LogInDateDTO logInDateDTO = new LogInDateDTO();
                logInDateDTO.setLogin(username.getText());
                LocalDateTime time = LocalDateTime.now();
                logInDateDTO.setDate(time);
                registerStore.addUser();
                storeRestUtils.saveNewEntryDate(logInDateDTO);
            } else {
                registerStore.addUser();
            }
            saveMessage.setText("Great Success! You can log in now.");
            reset();
        } catch (RuntimeException e) {
            saveMessage.setText("Something gone south... Try again later.");
        }
    }

    private void reset() {
        username.setText("");
        password.setText("");
        question.setText("");
        answer.setText("");
    }
}
