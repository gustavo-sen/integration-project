package controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.Categories;
import model.Lineup;
import model.Models;

import java.net.URL;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //Titled expanded 1
        accordion.setExpandedPane(titledLineup);
        titledModels.setDisable(true);

        // Line up Select Section
        comboBox.setItems(FXCollections.observableArrayList(Lineup.values()));

        // Evente Listener ComboBox
        comboBox.valueProperty().addListener((obs, oldValue, newValue) -> {
            treeReference(newValue);
            titledModels.setDisable(false);
            titledModels.setExpanded(true);
        });

    }

    public void treeReference(Lineup newValue){

            //model.Lineup

            TreeItem setTreeView = new TreeItem<>(newValue);
            setTreeView.setExpanded(true);

            //model.Models
            for(Categories cat: Categories.values()){

                if(cat.getLineup().equals(newValue)){
                    TreeItem<Categories> categoryItem = new TreeItem<>(cat);
                    setTreeView.getChildren().add(categoryItem);

                    for (Models mod: Models.values()){
                        if (mod.getCategories().equals(categoryItem.getValue())){
                            categoryItem.getChildren().add( new TreeItem(mod));
                        }

                    }
                }
            }
            treeView.setRoot(setTreeView);
    }

}