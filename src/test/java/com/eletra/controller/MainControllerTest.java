package com.eletra.controller;

import ch.qos.logback.core.util.COWArrayList;
import com.eletra.dto.CategoryDTO;
import com.eletra.dto.LineupDTO;
import com.eletra.dto.ModelDTO;
import com.eletra.helper.db.GETRequest;
import com.eletra.mapper.CategoryMapperDTO;
import com.eletra.mapper.LineupMapperDTO;
import com.eletra.mapper.ModelMapperDTO;
import javafx.scene.control.Accordion;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TitledPane;
import javafx.scene.control.TreeView;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.ui.Model;
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

    @Mock
    CategoryMapperDTO categoryMapperDTO;
    @Mock
    ModelMapperDTO modelMapperDTO;
    @Spy
    GETRequest request;

    @Before
    public void setUp() {
        mc = spy(MainController.class);
        mc.comboBox = new ComboBox<LineupDTO>();
        mc.treeView = new TreeView<LineupDTO>();
        mc.titledLineup = new TitledPane();
        mc.titledModels = new TitledPane();
        mc.accordion = new Accordion();
    }

    @After
    public void finished() {
        mc = null;
    }

    @Test
    public void initializeTest01() {
        mc.initialize(null, null);
        assertNotNull("Check if accordion is expanded",mc.accordion.expandedPaneProperty());
        error.checkThat("Checking if titledLineup is disable", mc.titledModels.isDisable(), is(true));
        doNothing().when(mc).comboBoxSelectLineup();
    }

    @Test
    public void initializeTest02() {
        mc.initialize(null,null);
        verify(mc).comboBoxSelectLineup();
        doNothing().when(mc).comboBoxSelectLineup();
    }

    @Test
    public void comboBoxSelectLineupTest02(){
        mc.comboBox.getItems().clear();
        mc.comboBoxSelectLineup();
        assertFalse("Check if items from comboBox is empty when request Lineup List",mc.comboBox.getItems().isEmpty());
    }

    @Test
    public void comboBoxSelectLineupTest03(){
        mc.comboBoxSelectLineup();
        doNothing().when(mc).createTree(null);
    }

    @Test
    public void comboBoxSelectLineupTest04(){
        mc.comboBoxSelectLineup();
        assertFalse(mc.titledModels.isDisabled());
    }

    @Test
    public void comboBoxSelectLineupTest05(){
        mc.comboBoxSelectLineup();
        assertFalse(mc.titledModels.isDisabled());
    }

    @Test
    public void createTreeTest01(){
        mc.createTree(new LineupDTO(1,"Ares"));
        error.checkThat(mc.setTreeView.isExpanded(),is(true));
    }

    @Test
    public void createTreeTest02(){

        LineupDTO[] lineupDTOS = {new LineupDTO(1,"Ares"), new LineupDTO(2,"Cronos")};
        CategoryDTO[] categoryDTOS = {new CategoryDTO(4,"Ares TB",lineupDTOS[0]),new CategoryDTO(5,"Ares THS",lineupDTOS[0])};
        ModelDTO[] modelDTOS = {new ModelDTO(12, "Ares 7021", categoryDTOS[0]),new ModelDTO(13,"Ares 7031",categoryDTOS[1])};

        List<CategoryDTO> categoryDTOSList = new ArrayList<>(Arrays.asList(categoryDTOS));
        List<ModelDTO> modelDTOList = new ArrayList<>(Arrays.asList(modelDTOS));

        try (MockedStatic<CategoryMapperDTO> categoryMapperDTOMockedStatic = Mockito.mockStatic(CategoryMapperDTO.class)) {
            categoryMapperDTOMockedStatic.when(() -> CategoryMapperDTO.getListOfCategoriesFrom(lineupDTOS[0])).thenReturn(categoryDTOSList);
            try(MockedStatic<ModelMapperDTO> modelMapperDTOMockedStatic = Mockito.mockStatic(ModelMapperDTO.class)){
                modelMapperDTOMockedStatic.when(() -> ModelMapperDTO.getListOfModelsFrom(categoryDTOS[0])).thenReturn(modelDTOList);
                mc.createTree(lineupDTOS[0]);
                System.out.println(mc.treeView.getProperties().size());
                error.checkThat("Check if treeView is empty",mc.treeView.getProperties().isEmpty(),is(false));
            }
        }
    }
}
