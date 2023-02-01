package com.eletra.controller;

import hibernate.HibernateUtil;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.Categories;
import model.Lineup;
import model.Models;
import org.hibernate.Session;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private ComboBox<Lineup> comboBox;

    @FXML
    private TreeView<Lineup> treeView;

    @FXML
    private TitledPane titledLineup;

    @FXML
    private TitledPane titledModels;

    @FXML
    private Accordion accordion;

    private Session session = HibernateUtil.getSessionFactory().openSession();


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //Titled expanded 1
        accordion.setExpandedPane(titledLineup);
        titledModels.setDisable(true);

        List<Lineup> lineupList = session.createQuery("FROM Lineup").list();

        // Line up Select Section
        comboBox.setItems(FXCollections.observableArrayList(lineupList));

        // Evente Listener ComboBox
        comboBox.valueProperty().addListener((obs, oldValue, newValue) -> {
            treeReference(newValue);
            titledModels.setDisable(false);
            titledModels.setExpanded(true);
        });

    }

    public void treeReference(Lineup newValue){

            List<Categories> categoriesList = session.createQuery(String.format("FROM Categories WHERE id_lineup = '%s'",newValue)).list();

            //model.Lineup
            TreeItem setTreeView = new TreeItem<>(newValue);
            setTreeView.setExpanded(true);

            //model.Models
            for(Categories cat: categoriesList){

                TreeItem<Categories> categoryItem = new TreeItem<>(cat);
                setTreeView.getChildren().add(categoryItem);

                List<Models> modelsList = session.createQuery(String.format("FROM Models WHERE id_category = '%s'",cat)).list();

                for (Models mod: modelsList){
                    categoryItem.getChildren().add( new TreeItem(mod));
                }
            }
            treeView.setRoot(setTreeView);
    }
}