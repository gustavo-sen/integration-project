package com.eletra.controller;

import com.eletra.dto.CategoryDTO;
import com.eletra.dto.LineupDTO;
import com.eletra.dto.ModelDTO;
import com.eletra.mappers.CategoriesDTOMapper;
import com.eletra.mappers.CategoriesDTOMapperTest;
import com.eletra.mappers.LineupDTOMapper;
import com.eletra.mappers.ModelDTOMapper;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.testfx.framework.junit.ApplicationTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class MainControllerTest extends ApplicationTest {

    @Rule
    public ErrorCollector error = new ErrorCollector();

    MockedStatic<LineupDTOMapper> lineupDTOMockedStatic;
    MockedStatic<CategoriesDTOMapper> categoryDTOMockedStatic;
    MockedStatic<ModelDTOMapper> modelDTOMockedStatic;

    public MainController mc;

    @Before
    public void setUp() {
        mc = spy(MainController.class);
        mc.comboBox = new ComboBox<>();
        mc.treeView = new TreeView<>();
        mc.titledLineup = new TitledPane();
        mc.titledModels = new TitledPane();
        mc.accordion = new Accordion();
        mc.rootTreeView = new TreeItem();

        lineupDTOMockedStatic = Mockito.mockStatic(LineupDTOMapper.class);
        categoryDTOMockedStatic = Mockito.mockStatic(CategoriesDTOMapper.class);
        modelDTOMockedStatic = Mockito.mockStatic(ModelDTOMapper.class);
    }

    @After
    public void finished() {
        mc = null;
        lineupDTOMockedStatic.close();
        categoryDTOMockedStatic.close();
        modelDTOMockedStatic.close();
    }


    @Test
    public void initializeTest01() {
        doNothing().when(mc).comboBoxSelectLineup();
        mc.initialize(null, null);

        error.checkThat("Checking if titledLineup is disable", mc.titledModels.isDisable(), is(true));
        error.checkThat("Check if accordion is expanded",
                mc.accordion.expandedPaneProperty().getValue().isExpanded(),
                is(true)
        );
        verify(mc,times(1)).comboBoxSelectLineup();
    }

    @Test
    public void comboBoxSelectLineupTest01(){
        LineupDTO[] lineupDTOS = {new LineupDTO(1,"Ares"), new LineupDTO(2,"Cronos")};
        lineupDTOMockedStatic.when(LineupDTOMapper::getListOfLineups).thenReturn(FXCollections.observableArrayList(lineupDTOS));

        mc.comboBox.getItems().clear();
        mc.comboBoxSelectLineup();

        assertEquals("Check if items from comboBox is set when request Lineup List",
                FXCollections.observableArrayList(lineupDTOS),
                mc.comboBox.getItems()
        );

    }

    @Test
    public void comboBoxSelectLineupTest02(){

        LineupDTO[] lineupDTOS = {new LineupDTO(1,"Ares"), new LineupDTO(2,"Cronos")};
        lineupDTOMockedStatic.when(LineupDTOMapper::getListOfLineups).thenReturn(FXCollections.observableArrayList(lineupDTOS));

        doNothing().when(mc).createTree(null);

        mc.titledModels.setDisable(true); // desabilita o titledModels
        mc.titledModels.setExpanded(false); //modo NÃO expandido

        mc.comboBoxSelectLineup();
        mc.comboBox.setValue(new LineupDTO(1,"Ares"));

        error.checkThat("Check if titledModels is not disabled",
                mc.titledModels.isDisabled(),
                is(false)
        );
        error.checkThat("Check if titledModels is expanded",
                mc.titledModels.isExpanded(),
                is(true)
        );
        verify(mc,times(1)).createTree(any());

    }

    @Test
    public void createTreeTest01(){

        LineupDTO aresLineup = new LineupDTO(1,"Ares");
        LineupDTO cronosLineup = new LineupDTO(2,"Cronos");

        LineupDTO[] lineupDTOS = {aresLineup, cronosLineup};
        CategoryDTO[] categoryDTOS = {new CategoryDTO(4,"Ares TB",lineupDTOS[0]),new CategoryDTO(5,"Ares THS",lineupDTOS[0])};
        ModelDTO[] modelDTOS = {new ModelDTO(12, "Ares 7021", categoryDTOS[0]),new ModelDTO(13,"Ares 7031",categoryDTOS[1])};

        lineupDTOMockedStatic.when(LineupDTOMapper::getListOfLineups).thenReturn(Arrays.asList(lineupDTOS));
        categoryDTOMockedStatic.when(() -> CategoriesDTOMapper.getListOfCategoriesFrom(aresLineup)).thenReturn(Arrays.asList(categoryDTOS));
        modelDTOMockedStatic.when(() -> ModelDTOMapper.getListOfModelsFrom(categoryDTOS[0])).thenReturn(Arrays.asList(modelDTOS));

        mc.rootTreeView.setExpanded(false);
        mc.createTree(aresLineup);

        List<TreeItem<CategoryDTO>> treeItem = mc.rootTreeView.getChildren();

        mc.createTree(aresLineup);
        error.checkThat("Check if TreeView is expanded",
                mc.rootTreeView.isExpanded(),is(true));

        assertEquals(
                "Check if categories position 0 name match given",
                categoryDTOS[0],
                treeItem.get(0).getValue());

    }

    @Test
    public void createTreeTest02(){

        LineupDTO aresLineup = new LineupDTO(1,"Ares");
        LineupDTO cronosLineup = new LineupDTO(2,"Cronos");

        LineupDTO[] lineupDTOS = {aresLineup, cronosLineup};
        CategoryDTO[] categoryDTOS = {new CategoryDTO(4,"Ares TB",lineupDTOS[0]),new CategoryDTO(5,"Ares THS",lineupDTOS[0])};
        ModelDTO[] modelDTOS = {new ModelDTO(12, "Ares 7021", categoryDTOS[0]),new ModelDTO(13,"Ares 7031",categoryDTOS[1])};

        lineupDTOMockedStatic.when(LineupDTOMapper::getListOfLineups).thenReturn(Arrays.asList(lineupDTOS));
        categoryDTOMockedStatic.when(() -> CategoriesDTOMapper.getListOfCategoriesFrom(aresLineup)).thenReturn(Arrays.asList(categoryDTOS));
        modelDTOMockedStatic.when(() -> ModelDTOMapper.getListOfModelsFrom(categoryDTOS[0])).thenReturn(Arrays.asList(modelDTOS));

        mc.createTree(aresLineup);

        List<TreeItem<CategoryDTO>> treeItem = mc.rootTreeView.getChildren();


        assertEquals("Check if categories position 1 name match given",
                categoryDTOS[1].getName(),
                String.valueOf(treeItem.get(1).getValue()));
    }

    @Test
    public void createTreeTest03(){

        LineupDTO aresLineup = new LineupDTO(1,"Ares");
        LineupDTO cronosLineup = new LineupDTO(2,"Cronos");

        LineupDTO[] lineupDTOS = {aresLineup, cronosLineup};
        CategoryDTO[] categoryDTOS = {new CategoryDTO(4,"Ares TB",lineupDTOS[0]),new CategoryDTO(5,"Ares THS",lineupDTOS[0])};
        ModelDTO[] modelDTOS = {new ModelDTO(12, "Ares 7021", categoryDTOS[0]),new ModelDTO(13,"Ares 7031",categoryDTOS[1])};

        lineupDTOMockedStatic.when(LineupDTOMapper::getListOfLineups).thenReturn(Arrays.asList(lineupDTOS));
        categoryDTOMockedStatic.when(() -> CategoriesDTOMapper.getListOfCategoriesFrom(aresLineup)).thenReturn(Arrays.asList(categoryDTOS));
        modelDTOMockedStatic.when(() -> ModelDTOMapper.getListOfModelsFrom(categoryDTOS[0])).thenReturn(Arrays.asList(modelDTOS));

        mc.createTree(aresLineup);

        List<TreeItem<CategoryDTO>> treeItem = mc.rootTreeView.getChildren();

        assertEquals("Check if model position 0 name match given",
                modelDTOS[0].getName(),
                String.valueOf(treeItem.get(0).getChildren().get(0).getValue()));

    }
    @Test
    public void createTreeTest04(){

        LineupDTO aresLineup = new LineupDTO(1,"Ares");
        LineupDTO cronosLineup = new LineupDTO(2,"Cronos");

        LineupDTO[] lineupDTOS = {aresLineup, cronosLineup};
        CategoryDTO[] categoryDTOS = {new CategoryDTO(4,"Ares TB",lineupDTOS[0]),new CategoryDTO(5,"Ares THS",lineupDTOS[0])};
        ModelDTO[] modelDTOS = {new ModelDTO(12, "Ares 7021", categoryDTOS[0]),new ModelDTO(13,"Ares 7031",categoryDTOS[1])};

        lineupDTOMockedStatic.when(LineupDTOMapper::getListOfLineups).thenReturn(Arrays.asList(lineupDTOS));
        categoryDTOMockedStatic.when(() -> CategoriesDTOMapper.getListOfCategoriesFrom(aresLineup)).thenReturn(Arrays.asList(categoryDTOS));
        modelDTOMockedStatic.when(() -> ModelDTOMapper.getListOfModelsFrom(categoryDTOS[0])).thenReturn(Arrays.asList(modelDTOS));

        mc.createTree(aresLineup);

        List<TreeItem<CategoryDTO>> treeItem = mc.rootTreeView.getChildren();


        assertEquals("Check if model position 1 name match given",
                modelDTOS[1].getName(),
                String.valueOf(treeItem.get(0).getChildren().get(1).getValue()));

    }

    @Test
    public void createTreeTest05(){

        LineupDTO aresLineup = new LineupDTO(1,"Ares");
        LineupDTO cronosLineup = new LineupDTO(2,"Cronos");

        LineupDTO[] lineupDTOS = {aresLineup, cronosLineup};
        CategoryDTO[] categoryDTOS = {new CategoryDTO(4,"Ares TB",lineupDTOS[0]),new CategoryDTO(5,"Ares THS",lineupDTOS[0])};
        ModelDTO[] modelDTOS = {new ModelDTO(12, "Ares 7021", categoryDTOS[0]),new ModelDTO(13,"Ares 7031",categoryDTOS[1])};

        lineupDTOMockedStatic.when(LineupDTOMapper::getListOfLineups).thenReturn(Arrays.asList(lineupDTOS));
        categoryDTOMockedStatic.when(() -> CategoriesDTOMapper.getListOfCategoriesFrom(aresLineup)).thenReturn(Arrays.asList(categoryDTOS));
        modelDTOMockedStatic.when(() -> ModelDTOMapper.getListOfModelsFrom(categoryDTOS[0])).thenReturn(Arrays.asList(modelDTOS));

        mc.createTree(aresLineup);

        List<TreeItem<CategoryDTO>> treeItem = mc.rootTreeView.getChildren();

        assertEquals("Check if the title of rootTreeView is matching the Lineup chosen",
                lineupDTOS[0].getName(),
                String.valueOf(mc.rootTreeView.getValue()));
    }

}
