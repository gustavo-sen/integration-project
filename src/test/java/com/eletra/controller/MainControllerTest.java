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
import org.testfx.framework.junit.ApplicationTest;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.spy;

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
        doNothing().when(mc).comboBoxSelectLineup();
        mc.accordion.setExpandedPane(null);

        mc.initialize(null, null);

        assertEquals("Checking if initialize is setting expanded pane in accordion",
                mc.titledLineup,
                mc.accordion.getExpandedPane());
       // error.checkThat("Checking if accordion is disable", mc.accordion.isDisable(), is(true));
    }

    @Test
    public void isComboBoxItemsSetTest01(){
        mc.comboBox.getItems().clear();
        mc.comboBoxSelectLineup();
        assertFalse("Check if items from comboBox is empty when request Lineup List",mc.comboBox.getItems().isEmpty());
    }

    @Test
    public void isComboBoxListenerReceivingNotNullNewValuesTest01(){
        mc.comboBoxSelectLineup();

        //assertNull((mc.comboBox.valueProperty().addListener();
    }


}
