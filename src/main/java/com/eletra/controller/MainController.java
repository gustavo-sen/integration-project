package com.eletra.controller;

import com.eletra.dto.CategoryDTO;
import com.eletra.dto.LineupDTO;
import com.eletra.mapper.CategoryMapperDTO;
import com.eletra.mapper.LineupMapperDTO;
import com.eletra.mapper.ModelMapperDTO;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    protected ComboBox<LineupDTO> comboBox;

    @FXML
    protected TreeView<LineupDTO> treeView;

    @FXML
    protected TitledPane titledLineup,titledModels;

    @FXML
    protected Accordion accordion;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Titled expanded 1
        accordion.setExpandedPane(titledLineup);
        titledModels.setDisable(true);
        comboBoxSelectLineup();
    }

    protected void comboBoxSelectLineup() {

        comboBox.setItems(FXCollections.observableArrayList(LineupMapperDTO.getListOfLineups()));

        //get selected value from combox
        comboBox.valueProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue != null) {
                createTree(newValue);
                titledModels.setDisable(false);
                titledModels.setExpanded(true);
            }
        });
    }

    protected void createTree(LineupDTO selectedLineup) {

        TreeItem setTreeView = new TreeItem<>(selectedLineup);
        setTreeView.setExpanded(true);

        for(CategoryDTO category : CategoryMapperDTO.getListOfCategoriesFrom(selectedLineup)){

            TreeItem<CategoryDTO> categoryItem = new TreeItem<>(category);

            setTreeView.getChildren().add(categoryItem);

            //set models
            ModelMapperDTO.getListOfModelsFrom(category).forEach(
                    (model) -> categoryItem.getChildren().add(new TreeItem(model)));
        }
        treeView.setRoot(setTreeView);
    }

}