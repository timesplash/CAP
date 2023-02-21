package org.dev.frontend.CAP.controller;


import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.dev.frontend.CAP.store.AdminControlStore;

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
    private HBox parentHBox;

    @FXML
    private VBox dataPanel;

    @FXML
    private ListView<String> listOfUsers;

    @FXML
    private void initialize() {
        dataPanel.getChildren().clear();

        adminControlStore.refreshStore();
        adminControlStore.populateEntries();

        listOfUsers.setItems(adminControlStore.getAllEntries());
        parentHBox.widthProperty().addListener(e -> {
            widthOfWindow = parentHBox.getWidth();
            dataPanel.setMinWidth(widthOfWindow - buttonBoxXSize);
            dataPanel.setPrefWidth(widthOfWindow - buttonBoxXSize);
            dataPanel.setMaxWidth(widthOfWindow - buttonBoxXSize);
        });
        parentHBox.heightProperty().addListener(e -> {
            heightOfWindow = parentHBox.getHeight();
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

        listHeight(boxWithListOfUsers,ySize);
        listWidth(emptyHBox,boxWithListOfUsers,xSize);

        listOfUsers.setVisible(true);

        VBox buttonsBox = new VBox();
        buttonsBox.setAlignment(Pos.TOP_CENTER);

        HBox deleteBtnBox = new HBox();
        deleteBtnBox.setAlignment(Pos.CENTER);

        Button deleteBtn = new Button();
        deleteBtn.setText("Delete user");
        deleteBtn.setOnAction(e -> deleteBtnAction());


        dataPanel.heightProperty().addListener(e -> {
            ySize = dataPanel.getHeight();
            listHeight(boxWithListOfUsers,ySize);
        });

        HBox emptyBox = new HBox();
        emptyBox.setMinWidth((buttonBoxXSize - buttonXSize) / 2.0);
        emptyBox.setMaxWidth((buttonBoxXSize - buttonXSize) / 2.0);
        emptyBox.setPrefWidth((buttonBoxXSize - buttonXSize) / 2.0);

        dataPanel.widthProperty().addListener(e -> {
            xSize = dataPanel.getHeight();
            listWidth(emptyHBox,boxWithListOfUsers,xSize);
        });

        deleteBtnBox.getChildren().add(deleteBtn);
        buttonsBox.getChildren().add(deleteBtnBox);
        boxWithListOfUsers.getChildren().add(emptyBox);
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

    private void listHeight(HBox hBox,double val){
        hBox.setPrefHeight(val - 50);
        hBox.setMinHeight(val - 50);
        hBox.setMaxHeight(val - 50);
    }

    private void listWidth(HBox hBox1, HBox hBox2, double val){
        hBox1.setPrefWidth(val);
        hBox1.setMinWidth(val);
        hBox2.setPrefWidth(val);
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
