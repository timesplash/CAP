package org.dev.frontend.CAP.controller;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.dev.api.CAP.model.DataDTO;
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

    private Double widthOfWindow;

    private Double heightOfWindow;

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

        HBox emptyHBoxForStyleOfButtons = new HBox();
        emptyHBoxForStyleOfButtons.setMinHeight(buttonYSize);
        emptyHBoxForStyleOfButtons.setPrefHeight(buttonYSize);
        emptyHBoxForStyleOfButtons.setMaxHeight(buttonYSize);

        HBox buttonBoxGainsAndLoses = new HBox();
        setButtonBoxSize(buttonBoxGainsAndLoses);
        buttonBoxGainsAndLoses.setAlignment(Pos.CENTER);

        Button gainsAndLosesBtn = new Button();
        gainsAndLosesBtn.setText("Gains and spending");
        setButtonSize(gainsAndLosesBtn);
        buttonBoxGainsAndLoses.getChildren().add(gainsAndLosesBtn);
        setButtonStyle(gainsAndLosesBtn);
        gainsAndLosesBtn.setOnAction(e -> gainsAndLosesBtnAcn(boxWithContent));

        HBox buttonBoxCreditCalculator = new HBox();
        setButtonBoxSize(buttonBoxCreditCalculator);
        buttonBoxCreditCalculator.setAlignment(Pos.CENTER);

        Button creditCalculatorBtn = new Button();
        creditCalculatorBtn.setText("Credit calculator");
        setButtonSize(creditCalculatorBtn);
        buttonBoxCreditCalculator.getChildren().add(creditCalculatorBtn);
        setButtonStyle(creditCalculatorBtn);

        boxWithButtons.getChildren().add(emptyHBoxForStyleOfButtons);
        boxWithButtons.getChildren().add(buttonBoxGainsAndLoses);
        boxWithButtons.getChildren().add(buttonBoxCreditCalculator);

        widthOfWindow = all.getWidth();
        heightOfWindow = all.getHeight();
        all.widthProperty().addListener(e -> {
            widthOfWindow = all.getWidth();
            boxWithContent.setMinWidth(widthOfWindow - buttonBoxXSize);
            boxWithContent.setPrefWidth(widthOfWindow - buttonBoxXSize);
            boxWithContent.setMaxWidth(widthOfWindow - buttonBoxXSize);
        });
        all.heightProperty().addListener(e -> {
            heightOfWindow = all.getHeight();
            boxWithContent.setMinHeight(heightOfWindow);
            boxWithContent.setPrefHeight(heightOfWindow);
            boxWithContent.setMaxHeight(heightOfWindow);
        });
    }

    private void gainsAndLosesBtnAcn(VBox boxWithContent) {
        HBox emptyLineUpTop = new HBox();
        emptyLineUpTop.setMinHeight(buttonYSize);
        emptyLineUpTop.setPrefHeight(buttonYSize);
        emptyLineUpTop.setMaxHeight(buttonYSize);

        HBox dataPanel = new HBox();

        HBox emptyBoxForAliment = new HBox();
        emptyBoxForAliment.setMinWidth((buttonBoxXSize - buttonXSize) / 2.0);

        HBox boxForTable = new HBox();

        TableView<DataDTO> tableWithData = new TableView<>();
        TableColumn<DataDTO, String> columnWithCategory = new TableColumn<>("Category");

        tableWithData.getColumns().add(columnWithCategory);

        dataPanel.getChildren().add(emptyBoxForAliment);

        boxWithContent.getChildren().add(emptyLineUpTop);
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

    private void setButtonSize(Button button) {
        button.setPrefWidth(buttonXSize);
        button.setPrefHeight(buttonYSize);
        button.setMinWidth(buttonXSize);
        button.setMinHeight(buttonYSize);
        button.setMaxWidth(buttonXSize);
        button.setMaxHeight(buttonYSize);
    }

    private void setButtonStyle(Button button) {
        button.styleProperty().bind(Bindings.when(button.hoverProperty())
                .then(Style.buttonStyleHovered)
                .otherwise(Style.buttonStyle));
    }
}
