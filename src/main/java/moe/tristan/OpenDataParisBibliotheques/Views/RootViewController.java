/*
 * Copyright (c) 2016. Tristan Deloche
 * This work is licensed under the Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License. To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-sa/4.0/ or send a letter to Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
 */

package moe.tristan.OpenDataParisBibliotheques.Views;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import moe.tristan.OpenDataParisBibliotheques.Model.APIHandler;
import moe.tristan.OpenDataParisBibliotheques.Model.Elements.CommonField;
import moe.tristan.OpenDataParisBibliotheques.Model.Elements.Element;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Tristan9 on 03/06/2016.
 */
public class RootViewController extends AnchorPane {
    @FXML
    private TableView<Element> elementsTableView;
    @FXML
    private TextField searchFieldPredicate;
    @FXML
    private Label numberOfHits;

    private final List<Element> allElements;
    private ObservableList<Element> currentSelectedElements;

    public RootViewController() {
        this.allElements = APIHandler.executeAPICall().getElementList();
        this.currentSelectedElements = FXCollections.observableList(allElements);
    }

    public void initialize() {
        initializeCells();
    }

    private void initializeCells() {
        elementsTableView.setItems(currentSelectedElements);

        TableColumn<Element,String> recordIDCol = new TableColumn<>("Record ID");
        recordIDCol.setCellValueFactory(element -> new SimpleStringProperty(element.getValue().getRecordID()));


        LinkedList<TableColumn<Element, String>> columns = new LinkedList<>();

        for (CommonField commonField : CommonField.values()) {
            System.out.println("Working out : "+commonField.name());
            TableColumn<Element, String> aColumn = new TableColumn<>(commonField.name());
            aColumn.setCellValueFactory(
                    element -> element.getValue().getProperty(
                            commonField.toString()
                    )
            );
            columns.add(aColumn);
        }

        //It works, stfu java
        //noinspection unchecked
        elementsTableView.getColumns().setAll(columns);
        numberOfHits.setText(""+elementsTableView.getItems().size());
    }
}
