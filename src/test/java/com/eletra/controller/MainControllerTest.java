package com.eletra.controller;

import com.eletra.dto.LineupDTO;
import com.eletra.mapper.LineupMapperDTO;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Accordion;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TitledPane;
import javafx.scene.control.TreeView;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.mockito.Spy;
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
        error.checkThat("Check if accordion is expanded",mc.accordion.isVisible(),is(true));
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
    public void isComboBoxListenerReceivingNotNullNewValuesTest01(){
        mc.comboBoxSelectLineup();
        doNothing().when(mc).createTree(null);
    }

    @Test
    public void isTitledModelDisabled(){
        mc.comboBoxSelectLineup();
        assertFalse(mc.titledModels.isDisabled());
    }
    @Test
    public void isTitledModelExpanded(){
        mc.comboBoxSelectLineup();
        assertFalse(mc.titledModels.isDisabled());
    }


}
