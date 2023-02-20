package org.dev.frontend.CAP.controller;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.dev.api.CAP.enums.Type;
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
    private HBox parentHBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        parentHBox.setStyle(Style.backgroundBlackStyle);
        parentHBox.getChildren().clear();

        VBox boxWithButtons = new VBox();
        VBox boxWithContent = new VBox();

        setVboxWidth(boxWithButtons,buttonBoxXSize);
        boxWithButtons.setStyle(Style.backgroundGreyStyle);

        parentHBox.getChildren().add(boxWithButtons);
        parentHBox.getChildren().add(boxWithContent);

        xSize = parentHBox.getWidth() - boxWithButtons.getWidth();
        setVboxWidth(boxWithContent,xSize);
        parentHBox.widthProperty().addListener(e -> {
            xSize = parentHBox.getWidth() - boxWithButtons.getWidth();
            setVboxWidth(boxWithContent,xSize);
        });

        ySize = parentHBox.getHeight();
        setVboxHeight(boxWithButtons,ySize);
        setVboxHeight(boxWithContent,ySize);
        parentHBox.heightProperty().addListener(e -> {
            ySize = parentHBox.getHeight();
            setVboxHeight(boxWithButtons,ySize);
            setVboxHeight(boxWithContent,ySize);
        });

        HBox emptyHBoxForStyleOfButtons = new HBox();
        setEmptyBoxSize(emptyHBoxForStyleOfButtons);

        HBox buttonBoxGainsAndLoses = new HBox();
        setButtonBoxSize(buttonBoxGainsAndLoses);
        buttonBoxGainsAndLoses.setAlignment(Pos.CENTER);

        Button gainsAndLosesBtn = new Button();
        gainsAndLosesBtn.setText("Gains and spending");
        setButtonSize(gainsAndLosesBtn);
        buttonBoxGainsAndLoses.getChildren().add(gainsAndLosesBtn);
        setButtonStyle(gainsAndLosesBtn);
        gainsAndLosesBtn.setOnAction(e -> openGainsAndLosesPanelBtnAcn(boxWithContent));

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
        setVboxWidth(boxWithContent,widthOfWindow - buttonBoxXSize);
        parentHBox.widthProperty().addListener(e -> {
            widthOfWindow = parentHBox.getWidth();
            setVboxWidth(boxWithContent,widthOfWindow - buttonBoxXSize);
        });

        setVboxHeight(boxWithContent,heightOfWindow);
        parentHBox.heightProperty().addListener(e -> {
            heightOfWindow = parentHBox.getHeight();
            setVboxHeight(boxWithContent,heightOfWindow);
        });
    }

    /**
     * @param boxWithContent - right side of the window, containing data selected with buttons on the left
     */
    private void openGainsAndLosesPanelBtnAcn(VBox boxWithContent) {
        boxWithContent.getChildren().clear();

        HBox dataPanel = new HBox();

        TableView<DataDTO> tableWithData = new TableView<>();
        TableColumn<DataDTO, String> columnWithCategory = new TableColumn<>("Category");
        columnWithCategory.setCellValueFactory(new PropertyValueFactory<>("categoryName"));

        TableColumn<DataDTO, Double> columnWithValue = new TableColumn<>("Amount");
        columnWithValue.setCellValueFactory(new  PropertyValueFactory<>("amount"));

        tableWithData.getColumns().add(columnWithCategory);
        tableWithData.getColumns().add(columnWithValue);

        VBox boxForButtons = new VBox();
        setVboxWidth(boxForButtons,buttonBoxXSize);

        HBox boxWithAddCategoryButton = new HBox();
        setButtonBoxSize(boxWithAddCategoryButton);

        Button addCategoryButton = new Button("new category");
        setButtonSize(addCategoryButton);
        setButtonStyle(addCategoryButton);
        addCategoryButton.setAlignment(Pos.CENTER);
        addCategoryButton.setOnAction(e -> addCategoryBtnAction());

        boxWithAddCategoryButton.getChildren().add(addCategoryButton);
        boxWithAddCategoryButton.setAlignment(Pos.CENTER);

        HBox boxWithAddButton = new HBox();
        setButtonBoxSize(boxWithAddButton);

        Button addButton = new Button("add");
        setButtonSize(addButton);
        setButtonStyle(addButton);
        addButton.setAlignment(Pos.CENTER);

        boxWithAddButton.getChildren().add(addButton);
        boxWithAddButton.setAlignment(Pos.CENTER);

        boxForButtons.getChildren().add(boxWithAddCategoryButton);
        boxForButtons.getChildren().add(boxWithAddButton);

        HBox boxForTable = new HBox();
        boxForTable.getChildren().add(tableWithData);

        setTableWidth(tableWithData, dataPanel.getWidth() - buttonBoxXSize - (buttonBoxXSize - buttonXSize) / 2.0);
        setDoubleColumnWidth(columnWithValue, buttonXSize * 3 / 4);
        setStingColumnWidth(columnWithCategory, tableWithData.getWidth() - buttonXSize * 3 / 4);

        dataPanel.widthProperty().addListener(e -> {
            setTableWidth(tableWithData, dataPanel.getWidth() - buttonBoxXSize - (buttonBoxXSize - buttonXSize) / 2.0);
            setStingColumnWidth(columnWithCategory, tableWithData.getWidth() - buttonXSize * 3 / 4);
        });

        String css = Objects.requireNonNull(this.getClass().getResource("tableViewStyle.css")).toExternalForm();
        tableWithData.getStylesheets().add(css);

        tableWithData.widthProperty().addListener(e -> setStingColumnWidth(columnWithCategory,
                tableWithData.getWidth() - buttonXSize * 3 / 4));

        setUpPanelWithData(boxWithContent,dataPanel,boxForTable,boxForButtons,true);
    }

    private void addCategoryBtnAction() {
        VBox parentVBoxForPopup = new VBox();
        parentVBoxForPopup.setAlignment(Pos.CENTER);
        parentVBoxForPopup.setStyle(Style.backgroundBlackStyle);

        HBox emptyBoxUpTop = new HBox();
        setEmptyBoxSize(emptyBoxUpTop);

        HBox categoryNameBox = new HBox();
        HBox categoryNameLblBox = new HBox();
        HBox categoryNameTxtBox = new HBox();

        Label categoryNameLbl = new Label("Category name: ");
        setLblStyle(categoryNameLbl);

        TextField categoryTxtField = new TextField();
        categoryTxtField.setText("");
        categoryTxtField.setMinWidth(300);
        categoryTxtField.setStyle(Style.textFieldStyle);

        setUpLabelAndTextOrComboBoxContainers(categoryNameBox,categoryNameLblBox,categoryNameTxtBox);

        categoryNameLblBox.getChildren().add(categoryNameLbl);
        categoryNameTxtBox.getChildren().add(categoryTxtField);

        HBox typeBox = new HBox();
        HBox typeLblBox = new HBox();
        HBox typeComboBoxContainer = new HBox();

        setUpLabelAndTextOrComboBoxContainers(typeBox,typeLblBox,typeComboBoxContainer);

        Label typeLbl = new Label("Type: ");
        setLblStyle(typeLbl);

        ComboBox<Type> type = new ComboBox<>();
        type.getItems().addAll(Type.values());
        setComboBoxTypeStyle(type);

        typeLblBox.getChildren().add(typeLbl);
        typeComboBoxContainer.getChildren().add(type);

        HBox errorBox = new HBox();
        setHBoxHeight(errorBox, buttonBoxYSize);

        HBox saveButtonBox = new HBox();
        Button saveBtn = new Button("Save");
        setButtonBoxSize(saveButtonBox);
        setButtonSize(saveBtn);
        setButtonStyle(saveBtn);
        saveButtonBox.getChildren().add(saveBtn);

        parentVBoxForPopup.getChildren().add(emptyBoxUpTop);
        parentVBoxForPopup.getChildren().add(categoryNameBox);
        parentVBoxForPopup.getChildren().add(typeBox);
        parentVBoxForPopup.getChildren().add(errorBox);
        parentVBoxForPopup.getChildren().add(saveButtonBox);

        Stage popUpWithCategoryStage = new Stage();
        popUpWithCategoryStage.setMinWidth(500);
        popUpWithCategoryStage.setMinHeight(200);
        popUpWithCategoryStage.setScene(new Scene(parentVBoxForPopup,500,200));
        popUpWithCategoryStage.show();
        popUpWithCategoryStage.setResizable(false);
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

    private void setHBoxWidth(HBox hBox, Double width) {
        hBox.setMinWidth(width);
        hBox.setPrefWidth(width);
        hBox.setMaxWidth(width);
    }

    private void setHBoxHeight(HBox hBox, Double height) {
        hBox.setMinHeight(height);
        hBox.setPrefHeight(height);
        hBox.setMaxHeight(height);
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
        String css = Objects.requireNonNull(this.getClass().getResource("buttonStyle.css")).toExternalForm();
        button.getStylesheets().add(css);
    }

    private void setEmptyBoxSize(HBox hBox) {
        hBox.setMinHeight(buttonYSize);
        hBox.setPrefHeight(buttonYSize);
        hBox.setMaxHeight(buttonYSize);
    }

    private void setEmptyBoxForAlimentSize(HBox emptyBoxForAliment) {
        emptyBoxForAliment.setMinWidth((buttonBoxXSize - buttonXSize) / 2.0);
        emptyBoxForAliment.setMaxWidth((buttonBoxXSize - buttonXSize) / 2.0);
        emptyBoxForAliment.setPrefWidth((buttonBoxXSize - buttonXSize) / 2.0);
    }

    private void setTableWidth(TableView<DataDTO> table, Double width) {
        table.setMinWidth(width);
        table.setMaxWidth(width);
        table.setPrefWidth(width);
    }

    private void setStingColumnWidth(TableColumn<DataDTO, String> tableColumn, Double width) {
        tableColumn.setMinWidth(width);
        tableColumn.setPrefWidth(width);
        tableColumn.setMaxWidth(width);
    }

    private void setDoubleColumnWidth(TableColumn<DataDTO, Double> tableColumn, Double width) {
        tableColumn.setMinWidth(width);
        tableColumn.setPrefWidth(width);
        tableColumn.setMaxWidth(width);
    }

    private void setLblStyle (Label label) {
        label.setAlignment(Pos.CENTER);
        label.setStyle(Style.labelBrightGreyStyle);
    }

    private void setComboBoxTypeStyle (ComboBox<Type> comboBox) {
        String css = Objects.requireNonNull(this.getClass().getResource("comboboxStyle.css")).toExternalForm();
        comboBox.getStylesheets().add(css);
    }

    private void setUpLabelAndTextOrComboBoxContainers(HBox wholeLineBox, HBox leftBox, HBox rightBox){
        setHBoxWidth(leftBox,150.0);
        setHBoxHeight(leftBox, buttonBoxYSize);
        leftBox.setAlignment(Pos.CENTER_RIGHT);

        setHBoxWidth(rightBox,350.0);
        setHBoxHeight(rightBox, buttonBoxYSize);
        rightBox.setAlignment(Pos.CENTER_LEFT);

        wholeLineBox.getChildren().add(leftBox);
        wholeLineBox.getChildren().add(rightBox);
    }

    private void setUpPanelWithData(VBox boxWithContent, HBox dataPanel, HBox centralBox, VBox buttonsBox, Boolean buttonsBoxPresent) {
        HBox emptyLineUpTop = new HBox();
        setEmptyBoxSize(emptyLineUpTop);

        HBox emptyLineBottom = new HBox();
        setEmptyBoxSize(emptyLineBottom);

        HBox emptyBoxForAliment = new HBox();
        setEmptyBoxForAlimentSize(emptyBoxForAliment);

        setHBoxHeight(dataPanel,heightOfWindow - 2 * buttonYSize);
        setHBoxWidth(dataPanel, widthOfWindow - buttonBoxXSize);
        boxWithContent.heightProperty().addListener(e -> setHBoxHeight(dataPanel,boxWithContent.getHeight() - 2 * buttonYSize));
        boxWithContent.widthProperty().addListener(e -> setHBoxWidth(dataPanel, boxWithContent.getWidth()));

        dataPanel.getChildren().add(emptyBoxForAliment);
        dataPanel.getChildren().add(centralBox);
        dataPanel.getChildren().add(buttonsBox);

        setCentralBoxWidth(dataPanel,centralBox,buttonsBoxPresent);

        dataPanel.widthProperty().addListener(e -> setCentralBoxWidth(dataPanel,centralBox,buttonsBoxPresent));

        boxWithContent.getChildren().add(emptyLineUpTop);
        boxWithContent.getChildren().add(dataPanel);
        boxWithContent.getChildren().add(emptyLineBottom);
    }

    private void setCentralBoxWidth (HBox dataPanel, HBox centralBox, Boolean buttonsBoxPresent) {
        if (buttonsBoxPresent) {
            setHBoxWidth(centralBox, dataPanel.getWidth() - buttonBoxXSize - (buttonBoxXSize - buttonXSize) / 2.0);
        } else {
            setHBoxWidth(centralBox, dataPanel.getWidth() - (buttonBoxXSize - buttonXSize) / 2.0);
        }
    }
}
