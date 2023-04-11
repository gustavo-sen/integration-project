package com.eletra.controller;

import com.eletra.mappers.CategoriesDTOMapper;
import com.eletra.mappers.LineupDTOMapper;
import com.eletra.mappers.ModelDTOMapper;
import com.eletra.dto.CategoryDTO;
import com.eletra.dto.LineupDTO;
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

    protected TreeItem rootTreeView;
    protected TreeItem modelDTOTreeItem;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Titled expanded 1
        accordion.setExpandedPane(titledLineup);
        titledModels.setDisable(true);
        comboBoxSelectLineup();
    }

    protected void comboBoxSelectLineup() {

        comboBox.setItems(FXCollections.observableArrayList(LineupDTOMapper.getListOfLineups()));

        comboBox.valueProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue != null) {
                createTree(newValue);
                titledModels.setDisable(false);
                titledModels.setExpanded(true);
            }
        });
    }

    protected void createTree(LineupDTO selectedLineup) {

        rootTreeView = new TreeItem<>(selectedLineup);
        rootTreeView.setExpanded(true);

        for(CategoryDTO category : CategoriesDTOMapper.getListOfCategoriesFrom(selectedLineup)){

            TreeItem<CategoryDTO> categoryItem = new TreeItem<>(category);

            rootTreeView.getChildren().add(categoryItem);

            //set models
            ModelDTOMapper.getListOfModelsFrom(category).forEach(
                    (model) ->{
                        modelDTOTreeItem = new TreeItem<>(model);
                        categoryItem.getChildren().add(modelDTOTreeItem);
                    });
        }

        treeView.setRoot(rootTreeView);
    }
}