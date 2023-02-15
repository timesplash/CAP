package org.dev.frontend.CAP.controller;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.dev.api.CAP.model.DataDTO;
import org.dev.frontend.CAP.Style;

import java.net.URL;
import java.util.Objects;
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
    private HBox parentHbox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        parentHbox.setStyle(Style.backgroundBlackStyle);
        parentHbox.getChildren().clear();

        VBox boxWithButtons = new VBox();
        VBox boxWithContent = new VBox();

        setVboxWidth(boxWithButtons,buttonBoxXSize);
        boxWithButtons.setStyle(Style.backgroundGreyStyle);

        parentHbox.getChildren().add(boxWithButtons);
        parentHbox.getChildren().add(boxWithContent);

        xSize = parentHbox.getWidth() - boxWithButtons.getWidth();
        setVboxWidth(boxWithContent,xSize);
        parentHbox.widthProperty().addListener(e -> {
            xSize = parentHbox.getWidth() - boxWithButtons.getWidth();
            setVboxWidth(boxWithContent,xSize);
        });

        ySize = parentHbox.getHeight();
        setVboxHeight(boxWithButtons,ySize);
        setVboxHeight(boxWithContent,ySize);
        parentHbox.heightProperty().addListener(e -> {
            ySize = parentHbox.getHeight();
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


        widthOfWindow = 800.0;
        heightOfWindow = 600.0;
        boxWithContent.setMinWidth(widthOfWindow - buttonBoxXSize);
        boxWithContent.setPrefWidth(widthOfWindow - buttonBoxXSize);
        boxWithContent.setMaxWidth(widthOfWindow - buttonBoxXSize);
        parentHbox.widthProperty().addListener(e -> {
            widthOfWindow = parentHbox.getWidth();
            boxWithContent.setMinWidth(widthOfWindow - buttonBoxXSize);
            boxWithContent.setPrefWidth(widthOfWindow - buttonBoxXSize);
            boxWithContent.setMaxWidth(widthOfWindow - buttonBoxXSize);
        });

        boxWithContent.setMinHeight(heightOfWindow);
        boxWithContent.setPrefHeight(heightOfWindow);
        boxWithContent.setMaxHeight(heightOfWindow);
        parentHbox.heightProperty().addListener(e -> {
            heightOfWindow = parentHbox.getHeight();
            boxWithContent.setMinHeight(heightOfWindow);
            boxWithContent.setPrefHeight(heightOfWindow);
            boxWithContent.setMaxHeight(heightOfWindow);
        });
    }

    private void gainsAndLosesBtnAcn(VBox boxWithContent) {
        boxWithContent.getChildren().clear();

        HBox emptyLineUpTop = new HBox();
        emptyLineUpTop.setMinHeight(buttonYSize);
        emptyLineUpTop.setPrefHeight(buttonYSize);
        emptyLineUpTop.setMaxHeight(buttonYSize);

        HBox emptyLineBottom = new HBox();
        emptyLineBottom.setMinHeight(buttonYSize);
        emptyLineBottom.setPrefHeight(buttonYSize);
        emptyLineBottom.setMaxHeight(buttonYSize);

        HBox dataPanel = new HBox();

        HBox emptyBoxForAliment = new HBox();
        emptyBoxForAliment.setMinWidth((buttonBoxXSize - buttonXSize) / 2.0);
        emptyBoxForAliment.setMaxWidth((buttonBoxXSize - buttonXSize) / 2.0);
        emptyBoxForAliment.setPrefWidth((buttonBoxXSize - buttonXSize) / 2.0);

        HBox boxForTable = new HBox();

        TableView<DataDTO> tableWithData = new TableView<>();
        TableColumn<DataDTO, String> columnWithCategory = new TableColumn<>("Category");
        columnWithCategory.setCellValueFactory(new PropertyValueFactory<>("categoryName"));

        TableColumn<DataDTO, Double> columnWithValue = new TableColumn<>("Amount");
        columnWithValue.setCellValueFactory(new  PropertyValueFactory<>("amount"));

        tableWithData.getColumns().add(columnWithCategory);
        tableWithData.getColumns().add(columnWithValue);

        dataPanel.setMinHeight(heightOfWindow - 2 * buttonYSize);
        dataPanel.setMaxHeight(heightOfWindow - 2 * buttonYSize);
        dataPanel.setPrefHeight(heightOfWindow - 2 * buttonYSize);
        dataPanel.setMinWidth(widthOfWindow - buttonBoxXSize);
        dataPanel.setMaxWidth(widthOfWindow - buttonBoxXSize);
        dataPanel.setPrefWidth(widthOfWindow - buttonBoxXSize);
        boxWithContent.heightProperty().addListener(e -> {
            dataPanel.setMinHeight(boxWithContent.getHeight() - 2 * buttonYSize);
            dataPanel.setMaxHeight(boxWithContent.getHeight() - 2 * buttonYSize);
            dataPanel.setPrefHeight(boxWithContent.getHeight() - 2 * buttonYSize);
        });
        boxWithContent.widthProperty().addListener(e -> {
            dataPanel.setMinWidth(boxWithContent.getWidth());
            dataPanel.setMaxWidth(boxWithContent.getWidth());
            dataPanel.setPrefWidth(boxWithContent.getWidth());
        });

        VBox boxForButtons = new VBox();
        setVboxWidth(boxForButtons,buttonBoxXSize);

        HBox boxWithAddButton = new HBox();
        setButtonBoxSize(boxWithAddButton);

        Button addButton = new Button("add");
        setButtonSize(addButton);
        setButtonStyle(addButton);
        addButton.setAlignment(Pos.CENTER);

        boxWithAddButton.getChildren().add(addButton);
        boxWithAddButton.setAlignment(Pos.CENTER);
        boxForButtons.getChildren().add(boxWithAddButton);

        boxForTable.getChildren().add(tableWithData);

        boxForTable.setMinWidth(dataPanel.getWidth() - buttonBoxXSize - (buttonBoxXSize - buttonXSize) / 2.0);
        boxForTable.setMaxWidth(dataPanel.getWidth() - buttonBoxXSize - (buttonBoxXSize - buttonXSize) / 2.0);
        boxForTable.setPrefWidth(dataPanel.getWidth() - buttonBoxXSize - (buttonBoxXSize - buttonXSize) / 2.0);
        tableWithData.setMinWidth(dataPanel.getWidth() - buttonBoxXSize - (buttonBoxXSize - buttonXSize) / 2.0);
        tableWithData.setMaxWidth(dataPanel.getWidth() - buttonBoxXSize - (buttonBoxXSize - buttonXSize) / 2.0);
        tableWithData.setPrefWidth(dataPanel.getWidth() - buttonBoxXSize - (buttonBoxXSize - buttonXSize) / 2.0);
        columnWithValue.setMinWidth(buttonXSize * 3 / 4);
        columnWithValue.setPrefWidth(buttonXSize * 3 / 4);
        columnWithValue.setMaxWidth(buttonXSize * 3 / 4);
        columnWithCategory.setMinWidth(tableWithData.getWidth() - buttonXSize * 3 / 4);
        columnWithCategory.setPrefWidth(tableWithData.getWidth() - buttonXSize * 3 / 4);
        columnWithCategory.setMaxWidth(tableWithData.getWidth() - buttonXSize * 3 / 4);

        dataPanel.widthProperty().addListener(e -> {
            boxForTable.setMinWidth(dataPanel.getWidth() - buttonBoxXSize - (buttonBoxXSize - buttonXSize) / 2.0);
            boxForTable.setMaxWidth(dataPanel.getWidth() - buttonBoxXSize - (buttonBoxXSize - buttonXSize) / 2.0);
            boxForTable.setPrefWidth(dataPanel.getWidth() - buttonBoxXSize - (buttonBoxXSize - buttonXSize) / 2.0);
            tableWithData.setMinWidth(dataPanel.getWidth() - buttonBoxXSize - (buttonBoxXSize - buttonXSize) / 2.0);
            tableWithData.setMaxWidth(dataPanel.getWidth() - buttonBoxXSize - (buttonBoxXSize - buttonXSize) / 2.0);
            tableWithData.setPrefWidth(dataPanel.getWidth() - buttonBoxXSize - (buttonBoxXSize - buttonXSize) / 2.0);
            columnWithCategory.setMinWidth(tableWithData.getWidth() - buttonXSize * 3 / 4);
            columnWithCategory.setPrefWidth(tableWithData.getWidth() - buttonXSize * 3 / 4);
            columnWithCategory.setMaxWidth(tableWithData.getWidth() - buttonXSize * 3 / 4);
        });

        String css = Objects.requireNonNull(this.getClass().getResource("tableViewStyle.css")).toExternalForm();
        tableWithData.getStylesheets().add(css);

        tableWithData.widthProperty().addListener(e -> {
            columnWithCategory.setMinWidth(tableWithData.getWidth() - buttonXSize * 3 / 4);
            columnWithCategory.setPrefWidth(tableWithData.getWidth() - buttonXSize * 3 / 4);
            columnWithCategory.setMaxWidth(tableWithData.getWidth() - buttonXSize * 3 / 4);
        });

        dataPanel.getChildren().add(emptyBoxForAliment);
        dataPanel.getChildren().add(boxForTable);
        dataPanel.getChildren().add(boxForButtons);

        boxWithContent.getChildren().add(emptyLineUpTop);
        boxWithContent.getChildren().add(dataPanel);
        boxWithContent.getChildren().add(emptyLineBottom);
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
