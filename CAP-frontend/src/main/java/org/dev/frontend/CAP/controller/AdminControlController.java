package org.dev.frontend.CAP.controller;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.dev.frontend.CAP.Style;

public class AdminControlController {

    private final Double buttonBoxYSize = 41.0;

    private final Double buttonBoxXSize = 263.0;

    private final Double buttonXSize = 228.0;

    private final Double buttonYSize = 25.0;

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
    private void initialize() {
        all.setStyle(Style.backgroundGreyStyle);
        controlsPanel.setStyle(Style.backgroundGreyStyle);
        dataPanel.setStyle(Style.backgroundBlackStyle);

        outdatedUsersBtn.styleProperty().bind(Bindings.when(outdatedUsersBtn.hoverProperty())
                .then(Style.buttonStyleHovered)
                .otherwise(Style.buttonStyle));
        categoriesRequestBtn.styleProperty().bind(Bindings.when(categoriesRequestBtn.hoverProperty())
                .then(Style.buttonStyleHovered)
                .otherwise(Style.buttonStyle));
        infoBtn.styleProperty().bind(Bindings.when(infoBtn.hoverProperty())
                .then(Style.buttonStyleHovered)
                .otherwise(Style.buttonStyle));
    }

    @FXML
    private void outdatedUsersBtnClicked() {
        double xSize = dataPanel.getLayoutX();
        double ySize = dataPanel.getLayoutY();
        HBox emptyHBox = new HBox();
        emptyHBox.setLayoutX(xSize);
        emptyHBox.setLayoutY(25);
        dataPanel.getChildren().add(emptyHBox);

        HBox boxWithListOfUsers = new HBox();
        boxWithListOfUsers.setLayoutY(ySize - 25);
        boxWithListOfUsers.setLayoutX(xSize);
        boxWithListOfUsers.setAlignment(Pos.CENTER);
        dataPanel.getChildren().add(boxWithListOfUsers);

        ListView listOfUsers = new ListView();
        listOfUsers.setLayoutY(boxWithListOfUsers.getLayoutY());
        listOfUsers.setLayoutX(boxWithListOfUsers.getLayoutX() - buttonBoxXSize);
        populatingListOfUsers(listOfUsers);

        VBox buttonsBox = new VBox();
        buttonsBox.setLayoutX(buttonBoxXSize);
        buttonsBox.setLayoutY(boxWithListOfUsers.getLayoutY());
        buttonsBox.setAlignment(Pos.CENTER);

        HBox deleteBtnBox = new HBox();
        deleteBtnBox.setLayoutY(buttonBoxYSize);
        deleteBtnBox.setLayoutX(buttonBoxXSize);
        deleteBtnBox.setAlignment(Pos.CENTER);

        Button deleteBtn = new Button();
        deleteBtn.setLayoutX(buttonXSize);
        deleteBtn.setLayoutY(buttonYSize);
        deleteBtn.styleProperty().bind(Bindings.when(deleteBtn.hoverProperty())
                .then(Style.buttonStyleHovered)
                .otherwise(Style.buttonStyle));

        deleteBtnBox.getChildren().add(deleteBtn);
        buttonsBox.getChildren().add(deleteBtnBox);
        boxWithListOfUsers.getChildren().add(listOfUsers);


    }

    private void populatingListOfUsers (ListView listView) {
        listView.getItems().clear();

    }

    private void deleteBtnAction() {

    }
}
