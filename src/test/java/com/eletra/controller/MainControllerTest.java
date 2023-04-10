package com.eletra.controller;

import com.eletra.dto.CategoryDTO;
import com.eletra.dto.LineupDTO;
import com.eletra.dto.ModelDTO;
import com.eletra.helper.db.GETRequest;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import org.junit.*;
import org.junit.rules.ErrorCollector;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.testfx.framework.junit.ApplicationTest;

import javax.sound.sampled.Line;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class MainControllerTest extends ApplicationTest {

    @Rule
    public ErrorCollector error = new ErrorCollector();

    MockedStatic<GETRequest> getRequestMockedStatic;

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

        getRequestMockedStatic = Mockito.mockStatic(GETRequest.class);
    }

    @After
    public void finished() {
        mc = null;
        getRequestMockedStatic.close();
    }


    @Test
    public void initializeTest01() {
        doNothing().when(mc).comboBoxSelectLineup();
        mc.initialize(null, null);

        error.checkThat("Checking if titledLineup is disable", mc.titledModels.isDisable(), is(true));
        assertTrue("Check if accordion is expanded"
                ,mc.accordion.expandedPaneProperty().getValue().isExpanded());
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
        getRequestMockedStatic.when(() -> GETRequest.getListOfLineups()).thenReturn(FXCollections.observableArrayList(lineupDTOS));

        mc.comboBox.getItems().clear();
        mc.comboBoxSelectLineup();
        List<LineupDTO> actualList = FXCollections.observableArrayList(mc.comboBox.getItems());

        assertEquals("Check if items from comboBox is set when request Lineup List",
                FXCollections.observableArrayList(lineupDTOS),actualList);

    }

    @Test
    public void comboBoxSelectLineupTest02(){

        LineupDTO[] lineupDTOS = {new LineupDTO(1,"Ares"), new LineupDTO(2,"Cronos")};
        getRequestMockedStatic.when(GETRequest::getListOfLineups).thenReturn(FXCollections.observableArrayList(lineupDTOS));

        doNothing().when(mc).createTree(null);

        mc.titledModels.setDisable(true); // desabilita o titledModels
        mc.comboBoxSelectLineup();
        mc.comboBox.setValue(new LineupDTO(1,"Ares"));

        assertFalse("Check if titledModels is not disabled"
                ,mc.titledModels.isDisabled());
    }

    @Test
    public void comboBoxSelectLineupTest03(){

        LineupDTO lineupDTO = new LineupDTO(1,"Ares");

        mc.comboBoxSelectLineup();
        mc.comboBox.setValue(lineupDTO);

        assertEquals("Check if comboBox receive the given set value"
                ,mc.comboBox.valueProperty().getValue(),lineupDTO);
    }

    @Test
    public void createTreeTest01(){

        LineupDTO aresLineup = new LineupDTO(1,"Ares");

        CategoryDTO[] categoryDTOS = {new CategoryDTO(4,"Ares TB",aresLineup),new CategoryDTO(5,"Ares THS",aresLineup)};
        ModelDTO[] modelDTOS = {new ModelDTO(12, "Ares 7021", categoryDTOS[0]),new ModelDTO(13,"Ares 7031",categoryDTOS[1])};

        List<CategoryDTO> categoryDTOSList = new ArrayList<>(Arrays.asList(categoryDTOS));
        List<ModelDTO> modelDTOList = new ArrayList<>(Arrays.asList(modelDTOS));

        getRequestMockedStatic.when(() -> GETRequest.getListOfCategoriesFrom(aresLineup)).thenReturn(categoryDTOSList);
        getRequestMockedStatic.when(() -> GETRequest.getListOfModelsFrom(categoryDTOS[0])).thenReturn(modelDTOList);

        mc.createTree(aresLineup);
        error.checkThat("Check if TreeView is expanded"
                ,mc.rootTreeView.isExpanded(),is(true));

    }

    @Test
    public void createTreeTest02(){
        LineupDTO aresLineup = new LineupDTO(1,"Ares");

        CategoryDTO[] categoryDTOS = {new CategoryDTO(4,"Ares TB",aresLineup),new CategoryDTO(5,"Ares THS",aresLineup)};
        ModelDTO[] modelDTOS = {new ModelDTO(12, "Ares 7021", categoryDTOS[0]),new ModelDTO(13,"Ares 7031",categoryDTOS[1])};
        List<CategoryDTO> categoryDTOSList = new ArrayList<>(Arrays.asList(categoryDTOS));
        List<ModelDTO> modelDTOList = new ArrayList<>(Arrays.asList(modelDTOS));
        getRequestMockedStatic.when(() -> GETRequest.getListOfCategoriesFrom(aresLineup)).thenReturn(categoryDTOSList);
        getRequestMockedStatic.when(() -> GETRequest.getListOfModelsFrom(categoryDTOS[0])).thenReturn(modelDTOList);

        TreeView expectedTreeView = new TreeView();

        TreeItem<CategoryDTO> categoryDTOTreeItem = (TreeItem<CategoryDTO>) new TreeItem<>(categoryDTOS);

        TreeView categoryTreeView = new TreeView(categoryDTOTreeItem);

        TreeItem<ModelDTO> modelDTOTreeItem = (TreeItem<ModelDTO>) new TreeItem<>(modelDTOS);



        mc.createTree(aresLineup);

        assertEquals("Check if Main treeView is filled by Categories",mc.rootTreeView.getChildren(),);
    }

    @Test
    public void createTreeTest03(){

        LineupDTO aresLineup = new LineupDTO(1,"Ares");

        LineupDTO[] lineupDTOS = {aresLineup, new LineupDTO(2,"Cronos")};
        CategoryDTO[] categoryDTOS = {new CategoryDTO(4,"Ares TB",lineupDTOS[0]),new CategoryDTO(5,"Ares THS",lineupDTOS[0])};
        ModelDTO[] modelDTOS = {new ModelDTO(12, "Ares 7021", categoryDTOS[0]),new ModelDTO(13,"Ares 7031",categoryDTOS[1])};

        List<CategoryDTO> categoryDTOSList = new ArrayList<>(Arrays.asList(categoryDTOS));
        List<ModelDTO> modelDTOList = new ArrayList<>(Arrays.asList(modelDTOS));

        getRequestMockedStatic.when(() -> GETRequest.getListOfCategoriesFrom(aresLineup)).thenReturn(categoryDTOSList);
        getRequestMockedStatic.when(() -> GETRequest.getListOfModelsFrom(categoryDTOS[0])).thenReturn(modelDTOList);

        mc.createTree(aresLineup);
        error.checkThat("Check if Main treeView is empty"
                ,mc.treeView.getRoot().getChildren().isEmpty(),is(false));

    }

}
