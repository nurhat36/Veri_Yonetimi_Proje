package org.example.veri_yonetimi_proje;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.veri_yonetimi_proje.Services.AlgoritmaService;
import org.example.veri_yonetimi_proje.hash.LinearProbingHashTable;
import org.example.veri_yonetimi_proje.model.Ogrenci;
import org.example.veri_yonetimi_proje.model.PerformansKaydi;
import org.example.veri_yonetimi_proje.storage.FileManager;


import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

public class HelloController {
    @FXML
    private RadioButton Radio_marge;
    @FXML
    private RadioButton Radio_bubble;
    @FXML
    private RadioButton Radio_Insertion;

    @FXML
    private RadioButton Radio_selection;
    @FXML private TableView<Ogrenci> tblOgrenciler;
    @FXML private TableColumn<Ogrenci, Integer> colNo;
    @FXML private TableColumn<Ogrenci, String> colAd;
    @FXML private TableColumn<Ogrenci, String> colSoyad;
    @FXML private TableColumn<Ogrenci, Float> colGano;
    @FXML private TableColumn<Ogrenci, Integer> colSinif;
    @FXML private TableColumn<Ogrenci, Integer> colBolumSira;
    @FXML private TableColumn<Ogrenci, Character> colCinsiyet;
    @FXML private TextField txtArama;
    private ToggleGroup selectionGroup;

    private final ObservableList<Ogrenci> ogrenciListesi = FXCollections.observableArrayList();
    private final FileManager ogrenciler_txt = new FileManager("ogrenciler.txt");
    private final FileManager sinif_sira_txt = new FileManager("sinif_sira.txt");
    private final FileManager bolum_sira_txt = new FileManager("bolum_sira.txt");
    private final FileManager ogr_no_sira_txt = new FileManager("ogr_no_sira.txt");
    private final FileManager performans_txt = new FileManager("performans_txt");
    private final LinearProbingHashTable hashTable = new LinearProbingHashTable(13000); // 1.3 * 10000

    @FXML
    public void initialize() {
        colNo.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getOgrNo()).asObject());
        colAd.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIsim()));
        colSoyad.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSoyad()));
        colGano.setCellValueFactory(cellData -> new SimpleFloatProperty(cellData.getValue().getGano()).asObject());
        colSinif.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getSinif()).asObject());
        colBolumSira.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getBolumSira()).asObject());
        colCinsiyet.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getCinsiyet()));
        selectionGroup = new ToggleGroup();
        Radio_bubble.setToggleGroup(selectionGroup);
        Radio_marge.setToggleGroup(selectionGroup);
        Radio_selection.setToggleGroup(selectionGroup);
        Radio_Insertion.setToggleGroup(selectionGroup);
        Radio_bubble.setSelected(true);
        selectionGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Seçilen RadioButton nesnesini al
                RadioButton selectedRB = (RadioButton) newValue;

                // Seçilen değeri Label'a yaz

            } else {
                System.out.println("Lütfen bir seçenek belirleyiniz.");
            }
        });

        // 🔹 Dosyadan öğrencileri yükle ve hash tablosuna ekle
        try {
            List<Ogrenci> list = ogrenciler_txt.readAll();
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

        // ogrenciListesi'nin başlangıçta null olmadığını ve başlatıldığını varsayıyoruz.
        int baslangicNo = 10000 + (ogrenciListesi != null ? ogrenciListesi.size() : 0);

        for (int i = 0; i < 10000; i++) {
            String ad = adlar[random.nextInt(adlar.length)];
            String soyad = soyadlar[random.nextInt(soyadlar.length)];
            int no = baslangicNo + i;

            // GANO (1.00 ile 4.00 arası, iki ondalık hassasiyet)
            float gano = (float) (Math.round((1.0 + random.nextDouble() * 3.0) * 100) / 100.0);

            // Sınıf (1-4 arası)
            int sinif = random.nextInt(4) + 1;

            // Bölüm Sırası (Rastgele 1-100)
            int bolumSira = 0;

            // Sınıf Sırası (Rastgele 1-100)
            // 'sinifSira' alanı için rastgele bir değer ekledik.
            int sinifSira =0;

            char cinsiyet = random.nextBoolean() ? 'E' : 'K';

            // OGRENCI CONSTRUCTOR SIRALAMASI:
            // (isim, soyad, ogrNo, gano, bolumSira, sinifSira, sinif, cinsiyet)
            Ogrenci yeni = new Ogrenci(
                    ad, soyad, no, gano,
                    bolumSira, sinifSira, sinif, cinsiyet
            );

            // ogrenciListesi ve hashTable'ın null kontrolünü ekleyebilirsiniz
            if (ogrenciListesi != null) {
                ogrenciListesi.add(yeni);
            }
            if (hashTable != null) {
                hashTable.insert(yeni); // ✅ Hash tablosuna da ekle
            }
        }




        // Tabloyu güncelle
        if (tblOgrenciler != null) {
            tblOgrenciler.refresh();
        }


        // Dosyaya yazma işlemi
        try {
            if (ogrenciler_txt != null && ogrenciListesi != null) {
                ogrenciler_txt.overwriteAll(ogrenciListesi);
                showAlert("Başarılı", "1000 rastgele öğrenci başarıyla eklendi!");
            }
        } catch (IOException e) {
            showAlert("Hata", "Dosyaya yazılamadı! Hata: " + e.getMessage());
        }
    }
    @FXML
    private void ogr_no_sirala_hash(){
        AlgoritmaService algoritmaService = new AlgoritmaService();
        Ogrenci[] sortedStudents = new Ogrenci[13000];
        String type="";

        // 1. Zaman ölçümü BAŞLANGICI
        long startTime = System.nanoTime();
        if(Radio_bubble.isSelected()){
            type="Bubble Sort (Öğrenci No)";
            sortedStudents = algoritmaService.ogr_no_sira_bubble_sort(hashTable);
        } else if (Radio_marge.isSelected()) {
            type="Marge Sort (Öğrenci No)";
            sortedStudents = algoritmaService.ogr_no_sira_merge_sort(hashTable);
        } else if (Radio_Insertion.isSelected()) {
            type="Insertion Sort (Öğrenci No)";
            sortedStudents = algoritmaService.ogr_no_sira_insertion_sort(hashTable);
        } else if (Radio_selection.isSelected()) {
            type="selection Sort (Öğrenci No)";
            sortedStudents = algoritmaService.ogr_no_sira_selection_sort(hashTable);
        }

        // 2. Sıralama işlemini gerçekleştir


        // 3. Zaman ölçümü SONU
        long endTime = System.nanoTime();

        // Süreyi hesapla (saniye cinsinden)
        double durationInSeconds = (endTime - startTime) / 1_000_000_000.0;

        // 4. Performans Kaydını oluştur
        String timestamp = LocalDateTime.now().toString();
        PerformansKaydi kayit = new PerformansKaydi(
                timestamp,
                "Sıralama",
                type,
                durationInSeconds,
                sortedStudents.length // Sıralanan veri boyutu
        );

        // 5. Performans kaydını dosyaya yaz
        try {
            performans_txt.writePerformansKaydi(kayit);
            System.out.printf("Performans kaydı başarıyla yazıldı. Süre: %.6f saniye.%n", durationInSeconds);
        } catch (IOException e) {
            showAlert("Hata", "Performans dosyasına yazılamadı!");
            e.printStackTrace();
        }

        // 6. Konsola yazdırma (Mevcut kod)
        System.out.println("Öğrenci numarasına göre sıralanmış dizinin ilk 10 elemanı:");
        for (int i = 0; i < Math.min(10, sortedStudents.length); i++) {
            System.out.println(sortedStudents[i].getOgrNo());
        }

        // 7. Sıralanmış öğrencileri dosyaya yaz (Mevcut kod)
        try {
            // FileManager'ın Ogrenci[] dizisini yazabilen metodunu çağırıyoruz.
            ogr_no_sira_txt.writeOgrenciArray(sortedStudents);
            showAlert("Başarılı", "Sıralanmış öğrenciler 'ogr_no_sira.txt' dosyasına başarıyla yazıldı.");
        } catch (IOException e) {
            showAlert("Hata", "Dosyaya yazma işlemi sırasında hata oluştu: " + e.getMessage());
            e.printStackTrace();
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
            ogrenciler_txt.overwriteAll(ogrenciListesi);
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
            ogrenciler_txt.overwriteAll(ogrenciListesi);
            showAlert("Başarılı", "Öğrenci silindi!");
        } catch (IOException e) {
            showAlert("Hata", "Silme işlemi başarısız!");
        }
    }

    @FXML
    private void onTumListele() {
        try {
            ogrenciListesi.setAll(ogrenciler_txt.readAll());
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
