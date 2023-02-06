package com.eletra.controller;



import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import model.CategoriesEntity;
import model.LineupEntity;
import model.ModelsEntity;
import org.hibernate.Session;
import util.HibernateUtil;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private ComboBox<LineupEntity> comboBox;

    @FXML
    private TreeView<LineupEntity> treeView;

    @FXML
    private TitledPane titledLineup;

    @FXML
    private TitledPane titledModels;

    @FXML
    private Accordion accordion;

    private final Session session = HibernateUtil.getSessionFactory().openSession();


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //Titled expanded 1
        accordion.setExpandedPane(titledLineup);
        titledModels.setDisable(true);

        List<LineupEntity> lineupList = session.createQuery("FROM LineupEntity").list();

        // Line up Select Section
        comboBox.setItems(FXCollections.observableArrayList(lineupList));

        // Evente Listener ComboBox
        comboBox.valueProperty().addListener((obs, oldValue, newValue) -> {
            treeReference(newValue);
            titledModels.setDisable(false);
            titledModels.setExpanded(true);
        });

    }

    public void treeReference(LineupEntity newValue){

            List<CategoriesEntity> categoriesList = session.createQuery(String.format("FROM CategoriesEntity WHERE id_lineup = '%s'",newValue)).list();

            //model.Lineup
            TreeItem setTreeView = new TreeItem<>(newValue);
            setTreeView.setExpanded(true);

            //model.Models
            for(CategoriesEntity cat: categoriesList){

                TreeItem<CategoriesEntity> categoryItem = new TreeItem<>(cat);
                setTreeView.getChildren().add(categoryItem);

                List<ModelsEntity> modelsList = session.createQuery(String.format("FROM ModelsEntity WHERE id_category = '%s'",cat)).list();

                for (ModelsEntity mod: modelsList){
                    categoryItem.getChildren().add( new TreeItem(mod));
                }
            }
            treeView.setRoot(setTreeView);
    }
}