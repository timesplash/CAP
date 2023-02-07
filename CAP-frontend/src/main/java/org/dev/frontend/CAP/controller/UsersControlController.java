package org.dev.frontend.CAP.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.dev.frontend.CAP.Style;

import java.net.URL;
import java.util.ResourceBundle;

public class UsersControlController implements Initializable {
    private final Double buttonBoxYSize = 41.0;

    private final Double buttonBoxXSize = 263.0;

    private final Double buttonXSize = 228.0;

    private final Double buttonYSize = 25.0;

    private Double xSize;

    private Double ySize;

    @FXML
    private HBox all;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        all.setStyle(Style.backgroundBlackStyle);
        all.getChildren().clear();

        VBox boxWithButtons = new VBox();
        VBox boxWithContent = new VBox();

        setVboxWidth(boxWithButtons,buttonBoxXSize);
        boxWithButtons.setStyle(Style.backgroundGreyStyle);

        all.getChildren().add(boxWithButtons);

        xSize = all.getWidth() - boxWithButtons.getWidth();
        setVboxWidth(boxWithContent,xSize);
        all.widthProperty().addListener(e -> {
            xSize = all.getWidth() - boxWithButtons.getWidth();
            setVboxWidth(boxWithContent,xSize);
        });

        ySize = all.getHeight();
        setVboxHeight(boxWithButtons,ySize);
        setVboxHeight(boxWithContent,ySize);
        all.heightProperty().addListener(e -> {
            ySize = all.getHeight();
            setVboxHeight(boxWithButtons,ySize);
            setVboxHeight(boxWithContent,ySize);
        });

        HBox buttonBoxGainsAndLoses = new HBox();
        setButtonBoxSize(buttonBoxGainsAndLoses);


    }

    private void setVboxWidth(VBox vBox, Double width) {
        vBox.setMinWidth(width);
        vBox.setPrefWidth(width);
        vBox.setMaxWidth(width);
    }

    private void setVboxHeight(VBox vBox, Double height) {
        vBox.setMinHeight(height);
        vBox.setPrefHeight(height);
        vBox.setMaxHeight(height);
    }

    private void setButtonBoxSize(HBox buttonBox) {
        buttonBox.setMinWidth(buttonBoxXSize);
        buttonBox.setPrefWidth(buttonBoxXSize);
        buttonBox.setMaxWidth(buttonBoxXSize);
        buttonBox.setMinHeight(buttonBoxYSize);
        buttonBox.setPrefHeight(buttonBoxYSize);
        buttonBox.setMaxHeight(buttonBoxYSize);
    }
}
