package org.dev.frontend.CAP.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.dev.api.CAP.enums.Type;
import org.dev.api.CAP.model.CategoriesDTO;
import org.dev.api.CAP.model.DataDTO;
import org.dev.frontend.CAP.store.UsersControlStore;

import java.net.URL;
import java.util.*;

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

    @FXML
    private VBox boxWithButtons;

    @FXML
    private VBox boxWithContent;

    private final UsersControlStore usersControlStore = UsersControlStore.getStore();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        parentHBox.getChildren().clear();
        usersControlStore.refreshStore();

        setVboxWidth(boxWithButtons,buttonBoxXSize);

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

        Button gainsAndLosesBtn = new Button();
        gainsAndLosesBtn.setText("Gains and spendings");
        setButtonSize(gainsAndLosesBtn);
        setUpButtonBox(buttonBoxGainsAndLoses, gainsAndLosesBtn);
        gainsAndLosesBtn.setOnAction(e -> openGainsAndLosesPanelBtnAcn(boxWithContent));

        HBox buttonBoxCreditCalculator = new HBox();

        Button creditCalculatorBtn = new Button();
        creditCalculatorBtn.setText("Credit calculator");
        setButtonSize(creditCalculatorBtn);
        setUpButtonBox(buttonBoxCreditCalculator,creditCalculatorBtn);

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
        addCategoryButton.setAlignment(Pos.CENTER);
        addCategoryButton.setOnAction(e -> addCategoryBtnAction());

        boxWithAddCategoryButton.getChildren().add(addCategoryButton);
        boxWithAddCategoryButton.setAlignment(Pos.CENTER);

        HBox boxWithAddButton = new HBox();
        setButtonBoxSize(boxWithAddButton);

        Button addButton = new Button("add");
        setButtonSize(addButton);
        addButton.setAlignment(Pos.CENTER);
        addButton.setOnAction(e -> addGainsOrLosesBtnAction());

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

        tableWithData.widthProperty().addListener(e -> setStingColumnWidth(columnWithCategory,
                tableWithData.getWidth() - buttonXSize * 3 / 4));

        setUpPanelWithData(boxWithContent,dataPanel,boxForTable,boxForButtons,true);
    }

    private void addCategoryBtnAction() {
        HBox categoryNameBox = new HBox();

        Label categoryNameLbl = new Label("Category name: ");

        TextField categoryTxtField = new TextField();
        initialTextFieldSetUpForPopUp(categoryTxtField);

        setUpLabelAndControls(categoryNameBox,categoryNameLbl,categoryTxtField);

        HBox typeBox = new HBox();

        Label typeLbl = new Label("Type: ");
        ComboBox<Type> type = new ComboBox<>();
        type.getItems().addAll(Type.values());

        setUpLabelAndControls(typeBox,typeLbl,type);

        HBox errorBox = new HBox();
        setHBoxHeight(errorBox, buttonBoxYSize);

        Button saveBtn = new Button("Save");
        saveBtn.setOnAction(e -> usersControlStore.saveNewCategory(createNewCategory(categoryTxtField,type)));

        List<? extends Pane> popUpNodes = new ArrayList<>(
                Arrays.asList(categoryNameBox,typeBox,errorBox)
        );
        generalPopupSetup(popUpNodes,saveBtn);
    }

    private void addGainsOrLosesBtnAction() {
        HBox amountBox = new HBox();

        Label amountLbl = new Label("Amount: ");
        TextField amountTxt = new TextField();
        initialTextFieldSetUpForPopUp(amountTxt);

        setUpLabelAndControls(amountBox,amountLbl,amountTxt);

        HBox categoryBox = new HBox();

        Label categoryLbl = new Label("Category: ");
        ComboBox<String> categoryComboBox = new ComboBox<>();
        usersControlStore.populateAllCategories();
        categoryComboBox.setItems(usersControlStore.getCategoriesList());

        setUpLabelAndControls(categoryBox,categoryLbl,categoryComboBox);

        HBox errorBox = new HBox();
        setHBoxHeight(errorBox, buttonBoxYSize);

        Button saveBtn = new Button("Save");
        saveBtn.setOnAction(e -> usersControlStore.saveNewGainsOrLoses(createNewData(categoryComboBox,amountTxt)));

        List<? extends Pane> popUpNodes = new ArrayList<>(
                Arrays.asList(amountBox,categoryBox,errorBox)
        );
        generalPopupSetup(popUpNodes,saveBtn);
    }

    private void generalPopupSetup(List<? extends Pane> popUpNodes, Button saveBtn) {
        AnchorPane parentAnchorPane = new AnchorPane();
        parentAnchorPane.getStylesheets().add(getClass().getResource("popUpWindowStyle.css").toExternalForm());

        VBox parentVBoxForPopup = new VBox();
        parentVBoxForPopup.setAlignment(Pos.CENTER);

        HBox emptyBoxUpTop = new HBox();
        setEmptyBoxSize(emptyBoxUpTop);

        HBox saveButtonBox = new HBox();
        setButtonBoxSize(saveButtonBox);
        setButtonSize(saveBtn);
        saveButtonBox.getChildren().add(saveBtn);

        parentVBoxForPopup.getChildren().add(emptyBoxUpTop);
        addListOfNodesToParentNode(parentVBoxForPopup, popUpNodes);
        parentVBoxForPopup.getChildren().add(saveButtonBox);
        parentAnchorPane.getChildren().add(parentVBoxForPopup);

        Stage popUpStage = new Stage();
        setUpPopUpStage(popUpStage, parentAnchorPane, 500.0, 200.0);
    }

    private CategoriesDTO createNewCategory(TextField categoryTxtField, ComboBox<Type> type){
        int id = usersControlStore.getIdForNewCategory();
        CategoriesDTO categoriesDTO = new CategoriesDTO();
        categoriesDTO.setId(id);
        categoriesDTO.setName(categoryTxtField.getText());
        categoriesDTO.setUsername(usersControlStore.getUsername());
        categoriesDTO.setType(type.getValue());
        categoriesDTO.setRange(usersControlStore.getRangeForNewCategory());
        return categoriesDTO;
    }

    private DataDTO createNewData(ComboBox<String> categoryName, TextField amount) {
        DataDTO dataDTO = new DataDTO();
        dataDTO.setCategoryName(categoryName.getValue());
        dataDTO.setAmount(Double.valueOf(amount.getText()));
        return dataDTO;
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

    private void setUpButtonBox(HBox buttonBox, Button button){
        setButtonBoxSize(buttonBox);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().add(button);
    }

    private void setButtonSize(Button button) {
        button.setPrefWidth(buttonXSize);
        button.setPrefHeight(buttonYSize);
        button.setMinWidth(buttonXSize);
        button.setMinHeight(buttonYSize);
        button.setMaxWidth(buttonXSize);
        button.setMaxHeight(buttonYSize);
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

    private void setUpLabelAndControls(HBox wholeLineBox, Label label, Control controlNode){
        HBox leftBox = new HBox();
        HBox rightBox = new HBox();

        setHBoxWidth(leftBox,150.0);
        setHBoxHeight(leftBox, buttonBoxYSize);
        leftBox.setAlignment(Pos.CENTER_RIGHT);

        setHBoxWidth(rightBox,350.0);
        setHBoxHeight(rightBox, buttonBoxYSize);
        rightBox.setAlignment(Pos.CENTER_LEFT);

        wholeLineBox.getChildren().add(leftBox);
        wholeLineBox.getChildren().add(rightBox);

        leftBox.getChildren().add(label);
        rightBox.getChildren().add(controlNode);
    }

    private void addListOfNodesToParentNode (Pane parentNode, List<? extends Pane> listOfChildrenNodes){
        for (Pane child: listOfChildrenNodes){
            parentNode.getChildren().add(child);
        }
    }

    private void initialTextFieldSetUpForPopUp (TextField textField) {
        textField.setText("");
        textField.setMinWidth(300);
    }

    private void setUpPanelWithData(VBox boxWithContent, HBox dataPanel, HBox centralBox, VBox buttonsBox, boolean buttonsBoxPresent) {
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

        List<? extends Pane> nodesThatSetUpDataPanel = new ArrayList<>(
                Arrays.asList(emptyBoxForAliment,centralBox,buttonsBox)
        );
        addListOfNodesToParentNode(dataPanel, nodesThatSetUpDataPanel);

        setCentralBoxWidth(dataPanel,centralBox,buttonsBoxPresent);

        dataPanel.widthProperty().addListener(e -> setCentralBoxWidth(dataPanel,centralBox,buttonsBoxPresent));

        List<? extends Pane> nodesThatSetStyleToBoxWithContent = new LinkedList<>(
                Arrays.asList(emptyLineUpTop,dataPanel,emptyLineBottom)
        );
        addListOfNodesToParentNode(boxWithContent, nodesThatSetStyleToBoxWithContent);
    }

    private void setCentralBoxWidth (HBox dataPanel, HBox centralBox, Boolean buttonsBoxPresent) {
        if (buttonsBoxPresent) {
            setHBoxWidth(centralBox, dataPanel.getWidth() - buttonBoxXSize - (buttonBoxXSize - buttonXSize) / 2.0);
        } else {
            setHBoxWidth(centralBox, dataPanel.getWidth() - (buttonBoxXSize - buttonXSize) / 2.0);
        }
    }

    private void setUpPopUpStage(Stage popUpStage, AnchorPane anchorPane, Double widthOfWindow, Double heightOfWindow){
        popUpStage.setMinWidth(widthOfWindow);
        popUpStage.setMinHeight(heightOfWindow);
        popUpStage.setScene(new Scene(anchorPane,widthOfWindow,heightOfWindow));
        popUpStage.show();
        popUpStage.setResizable(false);
    }
}
