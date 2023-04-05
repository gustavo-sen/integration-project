package com.eletra.controller;

import com.eletra.dto.CategoryDTO;
import com.eletra.dto.LineupDTO;
import com.eletra.dto.ModelDTO;
import com.eletra.mapper.*;
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

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class MainControllerTest extends ApplicationTest {

    @Rule
    public ErrorCollector error = new ErrorCollector();

    MainController mc;

    @Before
    public void setUp() {
        mc = spy(MainController.class);
        mc.comboBox = new ComboBox<>();
        mc.treeView = new TreeView<>();
        mc.titledLineup = new TitledPane();
        mc.titledModels = new TitledPane();
        mc.accordion = new Accordion();
        mc.rootTreeView = new TreeItem();
    }

    @After
    public void finished() {
        mc = null;
    }

    @Test
    public void initializeTest01() {
        doNothing().when(mc).comboBoxSelectLineup();
        mc.initialize(null, null);
        assertNotNull("Check if accordion is expanded",mc.accordion.expandedPaneProperty());
        error.checkThat("Checking if titledLineup is disable", mc.titledModels.isDisable(), is(true));

    }

    @Test
    public void initializeTest02() {
        doNothing().when(mc).comboBoxSelectLineup();

        mc.initialize(null,null);
        verify(mc).comboBoxSelectLineup();
    }

    @Test
    public void comboBoxSelectLineupTest01(){
        LineupDTO[] lineupDTOS = {new LineupDTO(1,"Ares"), new LineupDTO(2,"Cronos")};

        try(MockedStatic<LineupMapperDTO> lineupMapperDTOMockedStatic = Mockito.mockStatic(LineupMapperDTO.class)){
            lineupMapperDTOMockedStatic.when(() -> LineupMapperDTO.getListOfLineups()).thenReturn(FXCollections.observableArrayList(lineupDTOS));

            mc.comboBox.getItems().clear();
            mc.comboBoxSelectLineup();
            error.checkThat("Check if items from comboBox is empty when request Lineup List",mc.comboBox.getItems().isEmpty(),is(false));
        }
    }

    @Test
    public void comboBoxSelectLineupTest02(){
        LineupDTO[] lineupDTOS = {new LineupDTO(1,"Ares"), new LineupDTO(2,"Cronos")};

        try(MockedStatic<LineupMapperDTO> lineupMapperDTOMockedStatic = Mockito.mockStatic(LineupMapperDTO.class)){
            lineupMapperDTOMockedStatic.when(() -> LineupMapperDTO.getListOfLineups()).thenReturn(FXCollections.observableArrayList(lineupDTOS));

            mc.comboBoxSelectLineup();
            doNothing().when(mc).createTree(null);
            error.checkThat("Check if items from comboBox is empty when request Lineup List",mc.comboBox.getItems().isEmpty(),is(false));
        }
    }

    @Test
    public void comboBoxSelectLineupTest03(){
        LineupDTO[] lineupDTOS = {new LineupDTO(1,"Ares"), new LineupDTO(2,"Cronos")};

        try(MockedStatic<LineupMapperDTO> lineupMapperDTOMockedStatic = Mockito.mockStatic(LineupMapperDTO.class)){
            lineupMapperDTOMockedStatic.when(() -> LineupMapperDTO.getListOfLineups()).thenReturn(FXCollections.observableArrayList(lineupDTOS));

            mc.comboBoxSelectLineup();
            assertFalse(mc.titledModels.isDisabled());
        }
    }

    @Test
    public void comboBoxSelectLineupTest04(){
        LineupDTO[] lineupDTOS = {new LineupDTO(1,"Ares"), new LineupDTO(2,"Cronos")};

        try(MockedStatic<LineupMapperDTO> lineupMapperDTOMockedStatic = Mockito.mockStatic(LineupMapperDTO.class)){
            lineupMapperDTOMockedStatic.when(() -> LineupMapperDTO.getListOfLineups()).thenReturn(FXCollections.observableArrayList(lineupDTOS));

            mc.comboBoxSelectLineup();
            assertFalse(mc.titledModels.isDisabled());
        }

    }

    @Test
    public void createTreeTest01(){
        LineupDTO aresLineup = new LineupDTO(1,"Ares");

        CategoryDTO[] categoryDTOS = {new CategoryDTO(4,"Ares TB",aresLineup),new CategoryDTO(5,"Ares THS",aresLineup)};
        ModelDTO[] modelDTOS = {new ModelDTO(12, "Ares 7021", categoryDTOS[0]),new ModelDTO(13,"Ares 7031",categoryDTOS[1])};
        List<CategoryDTO> categoryDTOSList = new ArrayList<>(Arrays.asList(categoryDTOS));
        List<ModelDTO> modelDTOList = new ArrayList<>(Arrays.asList(modelDTOS));

        try (MockedStatic<CategoryMapperDTO> categoryDTOMockedStatic = Mockito.mockStatic(CategoryMapperDTO.class)) {
            try(MockedStatic<ModelMapperDTO> modelMapperDTOMockedStatic = Mockito.mockStatic(ModelMapperDTO.class)){
                categoryDTOMockedStatic.when(() -> com.eletra.mapper.CategoryMapperDTO.getListOfCategoriesFrom(aresLineup)).thenReturn(categoryDTOSList);
                modelMapperDTOMockedStatic.when(() -> ModelMapperDTO.getListOfModelsFrom(categoryDTOS[0])).thenReturn(modelDTOList);

                mc.createTree(aresLineup);
                error.checkThat("Check if TreeView is expanded",mc.rootTreeView.isExpanded(),is(true));
            }
        }

    }

    @Test
    public void createTreeTest02(){
        LineupDTO aresLineup = new LineupDTO(1,"Ares");

        CategoryDTO[] categoryDTOS = {new CategoryDTO(4,"Ares TB",aresLineup),new CategoryDTO(5,"Ares THS",aresLineup)};
        ModelDTO[] modelDTOS = {new ModelDTO(12, "Ares 7021", categoryDTOS[0]),new ModelDTO(13,"Ares 7031",categoryDTOS[1])};
        List<CategoryDTO> categoryDTOSList = new ArrayList<>(Arrays.asList(categoryDTOS));
        List<ModelDTO> modelDTOList = new ArrayList<>(Arrays.asList(modelDTOS));

        try (MockedStatic<CategoryMapperDTO> categoryMapperDTOMockedStatic = Mockito.mockStatic(CategoryMapperDTO.class)) {
            try(MockedStatic<ModelMapperDTO> modelMapperDTOMockedStatic = Mockito.mockStatic(ModelMapperDTO.class)){
                categoryMapperDTOMockedStatic.when(() -> com.eletra.mapper.CategoryMapperDTO.getListOfCategoriesFrom(aresLineup)).thenReturn(categoryDTOSList);
                modelMapperDTOMockedStatic.when(() -> com.eletra.mapper.ModelMapperDTO.getListOfModelsFrom(categoryDTOS[0])).thenReturn(modelDTOList);

                mc.createTree(aresLineup);
                error.checkThat("Check if Main treeView is filled by Categories",mc.rootTreeView.getChildren().isEmpty(),is(false));
            }
        }
    }

    @Test
    public void createTreeTest03(){
        LineupDTO aresLineup = new LineupDTO(1,"Ares");

        CategoryDTO[] categoryDTOS = {new CategoryDTO(4,"Ares TB",aresLineup),new CategoryDTO(5,"Ares THS",aresLineup)};
        ModelDTO[] modelDTOS = {new ModelDTO(12, "Ares 7021", categoryDTOS[0]),new ModelDTO(13,"Ares 7031",categoryDTOS[1])};
        List<CategoryDTO> categoryDTOSList = new ArrayList<>(Arrays.asList(categoryDTOS));
        List<ModelDTO> modelDTOList = new ArrayList<>(Arrays.asList(modelDTOS));

        try (MockedStatic<CategoryMapperDTO> categoryMapperDTOMockedStatic = Mockito.mockStatic(CategoryMapperDTO.class)) {
            try(MockedStatic<ModelMapperDTO> modelMapperDTOMockedStatic = Mockito.mockStatic(ModelMapperDTO.class)){
                categoryMapperDTOMockedStatic.when(() -> com.eletra.mapper.CategoryMapperDTO.getListOfCategoriesFrom(aresLineup)).thenReturn(categoryDTOSList);
                modelMapperDTOMockedStatic.when(() -> com.eletra.mapper.ModelMapperDTO.getListOfModelsFrom(categoryDTOS[0])).thenReturn(modelDTOList);

                mc.createTree(aresLineup);
                error.checkThat("Check if Main treeView is expanded",mc.rootTreeView.isExpanded(),is(true));
            }
        }
    }

    @Test
    public void createTreeTest04(){

        LineupDTO aresLineup = new LineupDTO(1,"Ares");

        LineupDTO[] lineupDTOS = {aresLineup, new LineupDTO(2,"Cronos")};
        CategoryDTO[] categoryDTOS = {new CategoryDTO(4,"Ares TB",lineupDTOS[0]),new CategoryDTO(5,"Ares THS",lineupDTOS[0])};
        ModelDTO[] modelDTOS = {new ModelDTO(12, "Ares 7021", categoryDTOS[0]),new ModelDTO(13,"Ares 7031",categoryDTOS[1])};

        List<CategoryDTO> categoryDTOSList = new ArrayList<>(Arrays.asList(categoryDTOS));
        List<ModelDTO> modelDTOList = new ArrayList<>(Arrays.asList(modelDTOS));

        try (MockedStatic<CategoryMapperDTO> categoryMapperDTOMockedStatic = Mockito.mockStatic(CategoryMapperDTO.class)) {
            try(MockedStatic<ModelMapperDTO> modelMapperDTOMockedStatic = Mockito.mockStatic(ModelMapperDTO.class)){
                categoryMapperDTOMockedStatic.when(() -> com.eletra.mapper.CategoryMapperDTO.getListOfCategoriesFrom(lineupDTOS[0])).thenReturn(categoryDTOSList);
                modelMapperDTOMockedStatic.when(() -> ModelMapperDTO.getListOfModelsFrom(categoryDTOS[0])).thenReturn(modelDTOList);

                mc.createTree(aresLineup);
                error.checkThat("Check if Main treeView is empty",mc.treeView.getRoot().getChildren().isEmpty(),is(false));
            }
        }
    }

}
