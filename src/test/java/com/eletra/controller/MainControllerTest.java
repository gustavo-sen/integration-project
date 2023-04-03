package com.eletra.controller;

import com.eletra.dto.LineupDTO;
import javafx.scene.control.Accordion;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TitledPane;
import javafx.scene.control.TreeView;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.testfx.framework.junit.ApplicationTest;

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

        LineupDTO aresLineup = new LineupDTO();
        aresLineup.setName("Ares");
        aresLineup.setId(1);

        mc.createTree(aresLineup);
        error.checkThat("Check if treeView is empty",mc.treeView.getProperties().values().isEmpty(),is(false));

    }
}
