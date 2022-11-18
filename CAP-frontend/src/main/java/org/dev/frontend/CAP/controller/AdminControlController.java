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

import java.util.Objects;

public class AdminControlController {

    private final Double buttonBoxYSize = 41.0;

    private final Double buttonBoxXSize = 263.0;

    private final Double buttonXSize = 228.0;

    private final Double buttonYSize = 25.0;

    private Double xSize;
    private Double ySize;

    private Double widthOfWindow;

    private Double heightOfWindow;

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
        all.widthProperty().addListener(e -> {
            widthOfWindow = all.getWidth();
            dataPanel.setMinWidth(widthOfWindow - buttonBoxXSize);
            dataPanel.setPrefWidth(widthOfWindow - buttonBoxXSize);
            dataPanel.setMaxWidth(widthOfWindow - buttonBoxXSize);
        });
        all.heightProperty().addListener(e -> {
            heightOfWindow = all.getHeight();
            dataPanel.setMinHeight(heightOfWindow);
            dataPanel.setPrefHeight(heightOfWindow);
            dataPanel.setMaxHeight(heightOfWindow);
        });
    }

    @FXML
    private void outdatedUsersBtnClicked() {
        dataPanel.getChildren().clear();
        dataPanel.setFillWidth(true);
        xSize = dataPanel.getWidth();
        ySize = dataPanel.getHeight();

        HBox emptyHBox = new HBox();
        emptyHBox.setPrefHeight(25);
        emptyHBox.setMinHeight(25);

        HBox boxWithListOfUsers = new HBox();
        boxWithListOfUsers.setAlignment(Pos.CENTER);

        listOfUsers.setVisible(true);
        listOfUsers.setStyle(Style.textFieldStyle);
        String css = Objects.requireNonNull(this.getClass().getResource("listViewStyle.css")).toExternalForm();
        listOfUsers.getStylesheets().add(css);

        VBox buttonsBox = new VBox();
        buttonsBox.setAlignment(Pos.TOP_CENTER);

        HBox deleteBtnBox = new HBox();
        deleteBtnBox.setAlignment(Pos.CENTER);

        Button deleteBtn = new Button();
        deleteBtn.setText("Delete user");
        deleteBtn.styleProperty().bind(Bindings.when(deleteBtn.hoverProperty())
                .then(Style.buttonStyleHovered)
                .otherwise(Style.buttonStyle));
        deleteBtn.setOnAction(e -> deleteBtnAction());


        dataPanel.heightProperty().addListener(e -> {
            ySize = dataPanel.getHeight();
            boxWithListOfUsers.setPrefHeight(ySize - 50);
            boxWithListOfUsers.setMinHeight(ySize - 50);
            boxWithListOfUsers.setMaxHeight(ySize - 50);
        });

        boxWithListOfUsers.setPrefHeight(785.0);
        boxWithListOfUsers.setMinHeight(785.0);
        boxWithListOfUsers.setMaxHeight(785.0);

        dataPanel.widthProperty().addListener(e -> {
            xSize = dataPanel.getHeight();
            emptyHBox.setPrefWidth(xSize);
            emptyHBox.setMinWidth(xSize);
            boxWithListOfUsers.setPrefWidth(xSize);
        });

        deleteBtnBox.getChildren().add(deleteBtn);
        buttonsBox.getChildren().add(deleteBtnBox);
        boxWithListOfUsers.getChildren().add(listOfUsers);
        boxWithListOfUsers.getChildren().add(buttonsBox);
        dataPanel.getChildren().add(emptyHBox);
        dataPanel.getChildren().add(boxWithListOfUsers);

        boxWithListOfUsers.widthProperty().addListener(e -> {
            listOfUsers.setPrefWidth(boxWithListOfUsers.getWidth() - buttonBoxXSize);
            listOfUsers.setMinWidth(boxWithListOfUsers.getWidth() - buttonBoxXSize);
            buttonsBox.setPrefWidth(buttonBoxXSize);
            deleteBtnBox.setPrefWidth(buttonBoxXSize);
            deleteBtn.setPrefWidth(buttonXSize);
        });
        boxWithListOfUsers.heightProperty().addListener(e -> {
            listOfUsers.setPrefHeight(boxWithListOfUsers.getHeight());
            listOfUsers.setMinHeight(boxWithListOfUsers.getHeight());
            buttonsBox.setPrefHeight(boxWithListOfUsers.getHeight());
            deleteBtnBox.setPrefHeight(buttonBoxYSize);
            deleteBtnBox.setMinHeight(buttonBoxYSize);
            deleteBtn.setPrefHeight(buttonYSize);
        });





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
