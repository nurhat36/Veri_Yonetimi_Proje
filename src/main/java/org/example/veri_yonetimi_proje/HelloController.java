package org.example.veri_yonetimi_proje;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.veri_yonetimi_proje.hash.LinearProbingHashTable;
import org.example.veri_yonetimi_proje.model.Ogrenci;
import org.example.veri_yonetimi_proje.storage.FileManager;

import java.io.IOException;
import java.util.List;
import java.util.Random;

public class HelloController {

    @FXML private TableView<Ogrenci> tblOgrenciler;
    @FXML private TableColumn<Ogrenci, Integer> colNo;
    @FXML private TableColumn<Ogrenci, String> colAd;
    @FXML private TableColumn<Ogrenci, String> colSoyad;
    @FXML private TableColumn<Ogrenci, Float> colGano;
    @FXML private TableColumn<Ogrenci, Integer> colSinif;
    @FXML private TableColumn<Ogrenci, Integer> colBolumSira;
    @FXML private TableColumn<Ogrenci, Character> colCinsiyet;
    @FXML private TextField txtArama;

    private final ObservableList<Ogrenci> ogrenciListesi = FXCollections.observableArrayList();
    private final FileManager fileManager = new FileManager("ogrenciler.txt");
    private final LinearProbingHashTable hashTable = new LinearProbingHashTable(13000); // 1.3 * 10000

    @FXML
    public void initialize() {
        colNo.setCellValueFactory(cellData -> new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getOgrNo()).asObject());
        colAd.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getIsim()));
        colSoyad.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getSoyad()));
        colGano.setCellValueFactory(cellData -> new javafx.beans.property.SimpleFloatProperty(cellData.getValue().getGano()).asObject());
        colSinif.setCellValueFactory(cellData -> new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getSinif()).asObject());
        colBolumSira.setCellValueFactory(cellData -> new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getBolumSira()).asObject());
        colCinsiyet.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getCinsiyet()));

        // 🔹 Dosyadan öğrencileri yükle ve hash tablosuna ekle
        try {
            List<Ogrenci> list = fileManager.readAll();
            ogrenciListesi.setAll(list);
            tblOgrenciler.setItems(ogrenciListesi);

            // ✅ Hash tablosunu da doldur
            for (Ogrenci o : list) {
                hashTable.insert(o);
            }

        } catch (IOException e) {
            showAlert("Dosya Okuma Hatası", "ogrenciler.txt dosyası okunamadı!");
        }

    }

    // 🔹 1000 rastgele öğrenci ekleme
    @FXML
    private void onYeniOgrenciEkle() {
        String[] adlar = {"Ahmet", "Ayşe", "Mehmet", "Zeynep", "Ali", "Elif", "Murat", "Fatma", "Can", "Deniz"};
        String[] soyadlar = {"Yılmaz", "Kaya", "Demir", "Şahin", "Çelik", "Aydın", "Arslan", "Doğan", "Koç", "Öztürk"};
        Random random = new Random();

        int baslangicNo = 10000 + ogrenciListesi.size();

        for (int i = 0; i < 10000; i++) {
            String ad = adlar[random.nextInt(adlar.length)];
            String soyad = soyadlar[random.nextInt(soyadlar.length)];
            int no = baslangicNo + i;
            float gano = (float) (Math.round((1.0 + random.nextDouble() * 3.0) * 100) / 100.0);
            int sinif = random.nextInt(4) + 1;
            int bolumSira = random.nextInt(100) + 1;
            int fakulte = random.nextInt(5) + 1;
            char cinsiyet = random.nextBoolean() ? 'E' : 'K';

            Ogrenci yeni = new Ogrenci(ad, soyad, no, gano, sinif, bolumSira, fakulte, cinsiyet);
            ogrenciListesi.add(yeni);
            hashTable.insert(yeni); // ✅ Hash tablosuna da ekle
        }

        tblOgrenciler.refresh();

        try {
            fileManager.overwriteAll(ogrenciListesi);
            showAlert("Başarılı", "1000 rastgele öğrenci başarıyla eklendi!");
        } catch (IOException e) {
            showAlert("Hata", "Dosyaya yazılamadı!");
        }
    }


    @FXML
    private void onOgrenciGuncelle() {
        Ogrenci secili = tblOgrenciler.getSelectionModel().getSelectedItem();
        if (secili == null) {
            showAlert("Uyarı", "Lütfen bir öğrenci seçin!");
            return;
        }

        secili.setGano(Math.min(4.0f, secili.getGano() + 0.1f));
        tblOgrenciler.refresh();
        hashTable.delete(secili.getOgrNo());
        hashTable.insert(secili);

        try {
            fileManager.overwriteAll(ogrenciListesi);
            showAlert("Başarılı", "Öğrenci güncellendi!");
        } catch (IOException e) {
            showAlert("Hata", "Güncelleme başarısız!");
        }
    }

    @FXML
    private void onOgrenciSil() {
        Ogrenci secili = tblOgrenciler.getSelectionModel().getSelectedItem();
        if (secili == null) {
            showAlert("Uyarı", "Lütfen silinecek öğrenciyi seçin!");
            return;
        }

        ogrenciListesi.remove(secili);
        hashTable.delete(secili.getOgrNo());
        tblOgrenciler.refresh();

        try {
            fileManager.overwriteAll(ogrenciListesi);
            showAlert("Başarılı", "Öğrenci silindi!");
        } catch (IOException e) {
            showAlert("Hata", "Silme işlemi başarısız!");
        }
    }

    @FXML
    private void onTumListele() {
        try {
            ogrenciListesi.setAll(fileManager.readAll());
            tblOgrenciler.setItems(ogrenciListesi);
        } catch (IOException e) {
            showAlert("Hata", "Listeleme başarısız!");
        }
    }

    @FXML
    private void onNumarayaGoreAra() {
        String input = txtArama.getText().trim();
        if (input.isEmpty()) return;

        try {
            int no = Integer.parseInt(input);
            Ogrenci bulunan = hashTable.searchByOgrNo(no);
            if (bulunan != null) {
                tblOgrenciler.getSelectionModel().select(bulunan);
                tblOgrenciler.scrollTo(bulunan);
            } else {
                showAlert("Bulunamadı", "Bu numaraya ait öğrenci yok!");
            }
        } catch (NumberFormatException e) {
            showAlert("Hata", "Geçerli bir numara girin!");
        }
    }

    @FXML
    private void onAdaGoreAra() {
        String ad = txtArama.getText().trim().toLowerCase();
        if (ad.isEmpty()) return;

        ObservableList<Ogrenci> filtreli = ogrenciListesi.filtered(o -> o.getIsim().toLowerCase().contains(ad));
        tblOgrenciler.setItems(filtreli);
    }

    @FXML
    private void onHashGoster() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 20; i++) { // ilk 50 indeks için gösterim
            sb.append(String.format("[%d] -> %s%n", i, hashTable.getDisplayValue(i)));
            System.out.println(String.format("[%d] -> %s%n", i, hashTable.getDisplayValue(i)));
        }
        showAlert("Hash Tablosu (İlk 50 Hücre)", sb.toString());
    }

    @FXML
    private void onAraTikla() {
        onNumarayaGoreAra();
    }

    private void showAlert(String baslik, String mesaj) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(baslik);
        alert.setHeaderText(null);
        alert.setContentText(mesaj);
        alert.showAndWait();
    }
}
