package org.dev.frontend.CAP.controller;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.dev.api.CAP.model.LogInDateDTO;
import org.dev.frontend.CAP.Style;
import org.dev.frontend.CAP.store.RegisterStore;
import org.dev.frontend.CAP.store.StoreRestUtils;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * RegisterController - controller for a registration window
 */
public class RegisterController {

    private final RegisterStore registerStore = RegisterStore.getStore();

    private final StoreRestUtils storeRestUtils = StoreRestUtils.getInstance();

    @FXML
    private VBox all;

    @FXML
    private Button createNewUser;

    @FXML
    private Label usernameLbl;

    @FXML
    private Label passwordLbl;

    @FXML
    private Label questionLbl;

    @FXML
    private Label answerLbl;

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

    @FXML
    private void initialize() {
        username.setText("");
        password.setText("");
        question.setText("");
        answer.setText("");
        all.setStyle(Style.backgroundGreyStyle);
        createNewUser.styleProperty().bind(Bindings.when(createNewUser.hoverProperty())
                .then(Style.buttonStyleHovered)
                .otherwise(Style.buttonStyle));
        usernameLbl.setStyle(Style.labelWhiteStyle);
        passwordLbl.setStyle(Style.labelWhiteStyle);
        questionLbl.setStyle(Style.labelWhiteStyle);
        answerLbl.setStyle(Style.labelWhiteStyle);
        roleLbl.setStyle(Style.labelWhiteStyle);
        saveMessage.setStyle(Style.labelWhiteStyle);
        username.setStyle(Style.textFieldStyle);
        password.setStyle(Style.textFieldStyle);
        question.setStyle(Style.textFieldStyle);
        answer.setStyle(Style.textFieldStyle);
        role.setStyle(Style.textFieldStyle);

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

        String css = Objects.requireNonNull(this.getClass().getResource("comboboxStyle.css")).toExternalForm();
        role.getStylesheets().add(css);
    }

    @FXML
    private void createBtnAction() {
        try {
            registerStore.addUser();
            saveMessage.setText("Great Success! You can log in now.");
            LogInDateDTO logInDateDTO = new LogInDateDTO();
            logInDateDTO.setLogin(username.getText());
            LocalDateTime time = LocalDateTime.now();
            logInDateDTO.setDate(time);
            storeRestUtils.saveNewEntryDate(logInDateDTO);
        } catch (RuntimeException e) {
            saveMessage.setText("Something gone south... Try again later.");
        }
    }
}
