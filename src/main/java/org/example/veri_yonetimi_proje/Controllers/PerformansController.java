package org.example.veri_yonetimi_proje.Controllers;



import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.veri_yonetimi_proje.model.PerformansKaydi;
import org.example.veri_yonetimi_proje.storage.FileManager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PerformansController {

    @FXML
    private TableView<PerformansKaydi> performansTable;
    @FXML
    private TableColumn<PerformansKaydi, String> colTarih;
    @FXML
    private TableColumn<PerformansKaydi, String> colIslem;
    @FXML
    private TableColumn<PerformansKaydi, String> colAlgoritma;
    @FXML
    private TableColumn<PerformansKaydi, Double> colSure;
    @FXML
    private TableColumn<PerformansKaydi, Integer> colVeriBoyutu;


    private final FileManager FILE_PATH = new FileManager("performans_txt");

    @FXML
    public void initialize() {

        colTarih.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getTarihSaat()));
        colIslem.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getIslemTipi()));
        colAlgoritma.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getAlgoritmaAdi()));
        colSure.setCellValueFactory(data -> new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getSureSaniye()));
        colVeriBoyutu.setCellValueFactory(data -> new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getVeriBoyutu()));


        performansTable.setItems(dosyaOku());
    }

    @FXML
    private void yenileVeriler() {
        performansTable.setItems(dosyaOku());
    }

    private ObservableList<PerformansKaydi> dosyaOku() {
        ObservableList<PerformansKaydi> list = FXCollections.observableArrayList();

        for (String line : FILE_PATH.readLines()) {
            String[] parts = line.split(",");
            System.out.println(parts.length);
            if (parts.length ==5) {
                System.out.println(3);
                String tarihSaat = parts[0].trim();
                String islemTipi = parts[1].trim();
                String algoritmaAdi = parts[2].trim();
                double sureSaniye = Double.parseDouble(parts[3].trim());
                int veriBoyutu = Integer.parseInt(parts[4].trim());

                list.add(new PerformansKaydi(tarihSaat, islemTipi, algoritmaAdi, sureSaniye, veriBoyutu));
            }
        }

        return list;
    }
}
