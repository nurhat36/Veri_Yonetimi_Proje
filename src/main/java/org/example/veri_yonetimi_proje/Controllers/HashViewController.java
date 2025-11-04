package org.example.veri_yonetimi_proje.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.veri_yonetimi_proje.hash.HashTable;
import org.example.veri_yonetimi_proje.hash.OverflowHashTable;

public class HashViewController {

    @FXML private TableView<HashRow> hashTableView;
    @FXML private TableColumn<HashRow, Integer> colIndex;
    @FXML private TableColumn<HashRow, String> colValue;
    @FXML private Button btnGelismis;
    @FXML private Button btnOverflow;
    @FXML private Button btnLineer;

    private HashTable gelismisHash;
    private OverflowHashTable overflowHash;
    private HashTable lineerHash;

    // Bu setter'lar ana sahneden çağrılacak
    public void setTables(HashTable gelismis, OverflowHashTable overflow, HashTable lineer) {
        this.gelismisHash = gelismis;
        this.overflowHash = overflow;
        this.lineerHash = lineer;
    }

    @FXML
    private void initialize() {
        colIndex.setCellValueFactory(new PropertyValueFactory<>("index"));
        colValue.setCellValueFactory(new PropertyValueFactory<>("value"));

        btnGelismis.setOnAction(e -> showHash("gelismis"));
        btnOverflow.setOnAction(e -> showHash("overflow"));
        btnLineer.setOnAction(e -> showHash("lineer"));
    }

    private void showHash(String type) {
        hashTableView.getItems().clear();
        HashTable tableToShow = null;

        switch (type) {
            case "gelismis" -> tableToShow = gelismisHash;
            case "overflow" -> tableToShow = overflowHash;
            case "lineer" -> tableToShow = lineerHash;
        }

        if (tableToShow == null) return;

        for (int i = 0; i < 13000; i++) {
            String val;
            if(type == "gelismis") {

                val = tableToShow.getDisplayValue(i+10000);
            }else {
                val = tableToShow.getDisplayValue(i);
            }

            hashTableView.getItems().add(new HashRow(i, val));
        }
    }

    public static class HashRow {
        private final Integer index;
        private final String value;

        public HashRow(Integer index, String value) {
            this.index = index;
            this.value = value;
        }

        public Integer getIndex() { return index; }
        public String getValue() { return value; }
    }
}