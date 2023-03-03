package com.eletra.controller;


import com.eletra.model.CategoryEntity;
import com.eletra.model.LineupEntity;
import com.eletra.model.ModelEntity;
import com.google.gson.Gson;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

import static java.util.Arrays.*;

public class MainController implements Initializable {

    @FXML
    private ComboBox<LineupEntity> comboBox;

    @FXML
    private TreeView<LineupEntity> treeView;

    @FXML
    private TitledPane titledLineup,titledModels;

    @FXML
    private Accordion accordion;

    @FXML
    private Button adicionar;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //Titled expanded 1
        accordion.setExpandedPane(titledLineup);
        titledModels.setDisable(true);

        comboBoxSelect();
    }

    private void comboBoxSelect() {

        comboBox.setItems(FXCollections.observableArrayList(
                asList(new Gson().fromJson(GetRequest.getListOfEntities("lineups"),LineupEntity[].class))));

        // Evente Listener ComboBox -- Observable
        comboBox.valueProperty().addListener((obs, oldValue, newValue) -> {
            createTree(newValue);
            titledModels.setDisable(false);
            titledModels.setExpanded(true);
        });

        adicionar.get


    }

    private void createTree(LineupEntity selectedLineup) {

        TreeItem setTreeView = new TreeItem<>(selectedLineup);
        setTreeView.setExpanded(true);

        for(CategoryEntity category : new Gson().fromJson(GetRequest.getListOfEntities("categories", selectedLineup.getName()), CategoryEntity[].class)) {

            TreeItem<CategoryEntity> categoryItem = new TreeItem<>(category);

            setTreeView.getChildren().add(categoryItem);

            //set models
            asList(new Gson().fromJson(GetRequest.getListOfEntities("models", category.getName()), ModelEntity[].class)).forEach(
                    (model) -> categoryItem.getChildren().add(new TreeItem(model)));
        }
        treeView.setRoot(setTreeView);
    }
}