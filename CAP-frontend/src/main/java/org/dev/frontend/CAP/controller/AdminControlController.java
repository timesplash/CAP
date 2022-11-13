package org.dev.frontend.CAP.controller;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.dev.frontend.CAP.Style;
import org.dev.frontend.CAP.store.AdminControlStore;

public class AdminControlController {

    private final Double buttonBoxYSize = 41.0;

    private final Double buttonBoxXSize = 263.0;

    private final Double buttonXSize = 228.0;

    private final Double buttonYSize = 25.0;

    private final AdminControlStore adminControlStore = AdminControlStore.getStore();

    @FXML
    private HBox all;

    @FXML
    private VBox controlsPanel;

    @FXML
    private VBox dataPanel;

    @FXML
    private Button outdatedUsersBtn;

    @FXML
    private Button categoriesRequestBtn;

    @FXML
    private Button infoBtn;

    @FXML
    public ListView<String> listOfUsers;

    @FXML
    private void initialize() {
        all.setStyle(Style.backgroundGreyStyle);
        controlsPanel.setStyle(Style.backgroundGreyStyle);
        dataPanel.setStyle(Style.backgroundBlackStyle);
        dataPanel.getChildren().clear();

        outdatedUsersBtn.styleProperty().bind(Bindings.when(outdatedUsersBtn.hoverProperty())
                .then(Style.buttonStyleHovered)
                .otherwise(Style.buttonStyle));
        categoriesRequestBtn.styleProperty().bind(Bindings.when(categoriesRequestBtn.hoverProperty())
                .then(Style.buttonStyleHovered)
                .otherwise(Style.buttonStyle));
        infoBtn.styleProperty().bind(Bindings.when(infoBtn.hoverProperty())
                .then(Style.buttonStyleHovered)
                .otherwise(Style.buttonStyle));

        adminControlStore.refreshStore();
        adminControlStore.populateEntries();

        listOfUsers.setItems(adminControlStore.getAllEntries());

    }

    @FXML
    private void outdatedUsersBtnClicked() {
        dataPanel.getChildren().clear();
        double xSize = dataPanel.getWidth();
        double ySize = dataPanel.getHeight();
        HBox emptyHBox = new HBox();
        dataPanel.getChildren().add(emptyHBox);
        emptyHBox.setPrefWidth(xSize);
        emptyHBox.setPrefHeight(25);
        emptyHBox.setMinWidth(xSize);
        emptyHBox.setMinHeight(25);

        HBox boxWithListOfUsers = new HBox();
        boxWithListOfUsers.setPrefHeight(ySize - 25);
        boxWithListOfUsers.setMinHeight(ySize - 25);
        boxWithListOfUsers.setPrefWidth(xSize);
        boxWithListOfUsers.setAlignment(Pos.CENTER);


        listOfUsers.setPrefHeight(boxWithListOfUsers.getPrefHeight());
        listOfUsers.setPrefWidth(boxWithListOfUsers.getPrefWidth() - buttonBoxXSize);
        listOfUsers.setVisible(true);
        listOfUsers.setMinWidth(boxWithListOfUsers.getPrefWidth() - buttonBoxXSize);
        listOfUsers.setMinHeight(boxWithListOfUsers.getPrefHeight());

        VBox buttonsBox = new VBox();
        buttonsBox.setPrefWidth(buttonBoxXSize);
        buttonsBox.setPrefHeight(boxWithListOfUsers.getHeight());
        buttonsBox.setAlignment(Pos.TOP_CENTER);

        HBox deleteBtnBox = new HBox();
        deleteBtnBox.setPrefHeight(buttonBoxYSize);
        deleteBtnBox.setMinHeight(buttonBoxYSize);
        deleteBtnBox.setPrefWidth(buttonBoxXSize);
        deleteBtnBox.setAlignment(Pos.CENTER);

        Button deleteBtn = new Button();
        deleteBtn.setPrefWidth(buttonXSize);
        deleteBtn.setPrefHeight(buttonYSize);
        deleteBtn.setText("Delete user");
        deleteBtn.styleProperty().bind(Bindings.when(deleteBtn.hoverProperty())
                .then(Style.buttonStyleHovered)
                .otherwise(Style.buttonStyle));
        deleteBtn.setOnAction(e -> {
            deleteBtnAction();
        });

        deleteBtnBox.getChildren().add(deleteBtn);
        buttonsBox.getChildren().add(deleteBtnBox);
        boxWithListOfUsers.getChildren().add(listOfUsers);
        boxWithListOfUsers.getChildren().add(buttonsBox);
        dataPanel.getChildren().add(boxWithListOfUsers);

        adminControlStore.refreshStore();
        adminControlStore.populateEntries();

        listOfUsers.setItems(adminControlStore.getAllEntries());
    }

    private void deleteBtnAction() {
        String selectedUser = listOfUsers.getSelectionModel().getSelectedItem();
        String[] login = selectedUser.split("; ");
        adminControlStore.deleteInactiveUser(login[0]);
        adminControlStore.refreshStore();
        adminControlStore.populateEntries();

        listOfUsers.setItems(adminControlStore.getAllEntries());
    }
}
