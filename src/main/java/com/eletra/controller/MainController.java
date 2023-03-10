package com.eletra.controller;


import com.eletra.model.CategoryDTO;
import com.eletra.model.LineupDTO;
import com.eletra.model.modelDTO;
import com.google.gson.Gson;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

import static java.util.Arrays.*;

public class MainController implements Initializable {

    @FXML
    private ComboBox<LineupDTO> comboBox;

    @FXML
    private TreeView<LineupDTO> treeView;

    @FXML
    private TitledPane titledLineup,titledModels;

    @FXML
    private Accordion accordion;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Titled expanded 1
        accordion.setExpandedPane(titledLineup);
        titledModels.setDisable(true);
        comboBoxSelect();
    }

    private void comboBoxSelect() {

        //Is mouse pressed
            comboBox.addEventHandler(MouseEvent.MOUSE_PRESSED, mousePressed -> {

                comboBox.setItems(FXCollections.observableArrayList(
                        asList(new Gson().fromJson(GETRequest.getListOfEntities("lineups"), LineupDTO[].class))));

                //get selected value from combox
                comboBox.valueProperty().addListener((obs, oldValue, newValue) -> {
                    if (newValue != null) {
                        createTree(newValue);
                        titledModels.setDisable(false);
                        titledModels.setExpanded(true);
                    }
                });
            });
    }



    private void createTree(LineupDTO selectedLineup) {

        TreeItem setTreeView = new TreeItem<>(selectedLineup);
        setTreeView.setExpanded(true);

        for(CategoryDTO category : new Gson().fromJson(GETRequest.getListOfEntities("categories", selectedLineup.getName()), CategoryDTO[].class)) {

            TreeItem<CategoryDTO> categoryItem = new TreeItem<>(category);

            setTreeView.getChildren().add(categoryItem);

            //set models
            asList(new Gson().fromJson(GETRequest.getListOfEntities("models", category.getName()), modelDTO[].class)).forEach(
                    (model) -> categoryItem.getChildren().add(new TreeItem(model)));
        }
        treeView.setRoot(setTreeView);
    }
}