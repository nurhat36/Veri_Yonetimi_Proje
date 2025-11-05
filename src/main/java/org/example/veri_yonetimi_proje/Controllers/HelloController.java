package org.example.veri_yonetimi_proje.Controllers;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.veri_yonetimi_proje.HelloApplication;
import org.example.veri_yonetimi_proje.Services.HashMapServices.HashMapBolumAlgoritmaService;
import org.example.veri_yonetimi_proje.Services.LineerServices.LineerBolumAlgoritmaService;
import org.example.veri_yonetimi_proje.Services.LineerServices.LineerOgrNoAlgoritmaService;
import org.example.veri_yonetimi_proje.Services.LineerServices.LineerSinifAlgoritmaService;
import org.example.veri_yonetimi_proje.Services.OverFlowServices.OverflowBolumAlgoritmaService;
import org.example.veri_yonetimi_proje.Services.OverFlowServices.OverflowOgrNoAlgoritmaService;
import org.example.veri_yonetimi_proje.Services.OverFlowServices.OverflowSinifAlgoritmaService;
import org.example.veri_yonetimi_proje.hash.HashMapTable;
import org.example.veri_yonetimi_proje.hash.LinearProbingHashTable;
import org.example.veri_yonetimi_proje.hash.OverflowHashTable;
import org.example.veri_yonetimi_proje.model.Ogrenci;
import org.example.veri_yonetimi_proje.model.PerformansKaydi;
import org.example.veri_yonetimi_proje.storage.FileManager;


import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class HelloController {
    @FXML
    private RadioButton Radio_marge;
    @FXML
    private RadioButton Radio_Quick;
    @FXML
    private RadioButton Radio_bubble;
    @FXML
    private RadioButton Radio_Insertion;

    @FXML
    private RadioButton Radio_selection;
    @FXML
    private RadioButton Radio_Bolum_Quick;
    @FXML
    private RadioButton Radio_Bolum_marge;
    @FXML
    private RadioButton Radio_Bolum_bubble;
    @FXML
    private RadioButton Radio_Bolum_Insertion;

    @FXML
    private RadioButton Radio_Bolum_selection;
    @FXML
    private RadioButton Radio_Sinif_marge;
    @FXML
    private RadioButton Radio_Sinif_Quick;
    @FXML
    private RadioButton Radio_Sinif_bubble;
    @FXML
    private RadioButton Radio_Sinif_Insertion;
    @FXML
    private RadioButton radioLineer;
    @FXML
    private RadioButton radioOverflow;

    @FXML
    private RadioButton Radio_Sinif_selection;
    @FXML private TableView<Ogrenci> tblOgrenciler;
    @FXML private TableColumn<Ogrenci, Integer> colNo;
    @FXML private TableColumn<Ogrenci, String> colAd;
    @FXML private TableColumn<Ogrenci, String> colSoyad;
    @FXML private TableColumn<Ogrenci, Float> colGano;
    @FXML private TableColumn<Ogrenci, Integer> colSinif;
    @FXML private TableColumn<Ogrenci, Integer> colBolumSira;
    @FXML private TableColumn<Ogrenci, Integer> colSinifSira;
    @FXML private TableColumn<Ogrenci, Character> colCinsiyet;
    @FXML private TextField txtArama;
    @FXML
    private ToggleGroup ogrNoGroup;
    @FXML
    private ToggleGroup bolumGroup;
    @FXML
    private ToggleGroup sinifGroup;
    @FXML private CheckBox chkAdvancedMode;

    private final ObservableList<Ogrenci> ogrenciListesi = FXCollections.observableArrayList();
    private final FileManager ogrenciler_txt = new FileManager("ogrenciler.txt");
    private final FileManager sinif_sira_txt = new FileManager("sinif_sira.txt");
    private final FileManager bolum_sira_txt = new FileManager("bolum_sira.txt");
    private final FileManager ogr_no_sira_txt = new FileManager("ogr_no_sira.txt");
    private final FileManager performans_txt = new FileManager("performans_txt");
    private final LinearProbingHashTable hashTable = new LinearProbingHashTable(13000); // 1.3 * 10000
    private final OverflowHashTable OverflowHashTable= new OverflowHashTable(6500,6500);
    private final HashMapTable HashmapTable=new HashMapTable();

    @FXML
    public void initialize() {
        colNo.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getOgrNo()).asObject());
        colAd.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIsim()));
        colSoyad.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSoyad()));
        colGano.setCellValueFactory(cellData -> new SimpleFloatProperty(cellData.getValue().getGano()).asObject());
        colSinif.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getSinif()).asObject());
        colBolumSira.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getBolumSira()).asObject());
        colSinifSira.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getSinifSira()).asObject());
        colCinsiyet.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getCinsiyet()));
        ogrNoGroup = new ToggleGroup();
        Radio_bubble.setToggleGroup(ogrNoGroup);
        Radio_marge.setToggleGroup(ogrNoGroup);
        Radio_selection.setToggleGroup(ogrNoGroup);
        Radio_Insertion.setToggleGroup(ogrNoGroup);
        Radio_Quick.setToggleGroup(ogrNoGroup);
        Radio_bubble.setSelected(true);
        ogrNoGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {

                RadioButton selectedRB = (RadioButton) newValue;



            } else {
                System.out.println("Lütfen bir seçenek belirleyiniz.");
            }
        });
        bolumGroup = new ToggleGroup();
        Radio_Bolum_bubble.setToggleGroup(bolumGroup);
        Radio_Bolum_marge.setToggleGroup(bolumGroup);
        Radio_Bolum_selection.setToggleGroup(bolumGroup);
        Radio_Bolum_Insertion.setToggleGroup(bolumGroup);
        Radio_Bolum_Quick.setToggleGroup(bolumGroup);
        Radio_Bolum_selection.setSelected(true);
        bolumGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {

                RadioButton selectedRB = (RadioButton) newValue;



            } else {
                System.out.println("Lütfen bir seçenek belirleyiniz.");
            }
        });
        sinifGroup =new ToggleGroup();
        Radio_Sinif_marge.setToggleGroup(sinifGroup);
        Radio_Sinif_bubble.setToggleGroup(sinifGroup);
        Radio_Sinif_Insertion.setToggleGroup(sinifGroup);
        Radio_Sinif_selection.setToggleGroup(sinifGroup);
        Radio_Sinif_Quick.setToggleGroup(sinifGroup);
        Radio_Sinif_selection.setSelected(true);
        sinifGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {

                RadioButton selectedRB = (RadioButton) newValue;



            } else {
                System.out.println("Lütfen bir seçenek belirleyiniz.");
            }
        });






        try {
            List<Ogrenci> list = ogrenciler_txt.readAll();
            ogrenciListesi.setAll(list);
            tblOgrenciler.setItems(ogrenciListesi);


            for (Ogrenci o : list) {
                hashTable.insert(o);
            }

        } catch (IOException e) {
            showAlert("Dosya Okuma Hatası", "ogrenciler.txt dosyası okunamadı!");
        }

    }


    @FXML
    private void onYeniOgrenciEkle() {
        String[] adlar = {"Ahmet", "Ayşe", "Mehmet", "Zeynep", "Ali", "Elif", "Murat", "Fatma", "Can", "Deniz"};
        String[] soyadlar = {"Yılmaz", "Kaya", "Demir", "Şahin", "Çelik", "Aydın", "Arslan", "Doğan", "Koç", "Öztürk"};
        Random random = new Random();


        int baslangicNo = 10000 + (ogrenciListesi != null ? ogrenciListesi.size() : 0);

        for (int i = 0; i < 10000; i++) {
            String ad = adlar[random.nextInt(adlar.length)];
            String soyad = soyadlar[random.nextInt(soyadlar.length)];
            int no = baslangicNo + i;


            float gano = (float) (Math.round((1.0 + random.nextDouble() * 3.0) * 100) / 100.0);


            int sinif = random.nextInt(4) + 1;


            int bolumSira = 0;


            int sinifSira =0;

            char cinsiyet = random.nextBoolean() ? 'E' : 'K';


            Ogrenci yeni = new Ogrenci(
                    ad, soyad, no, gano,
                    bolumSira, sinifSira, sinif, cinsiyet
            );


            if (ogrenciListesi != null) {
                ogrenciListesi.add(yeni);
            }
            if (hashTable != null) {
                hashTable.insert(yeni);
            }
            if (OverflowHashTable != null) {
                OverflowHashTable.insert(yeni);
            }
            if (HashmapTable != null) {
                HashmapTable.insert(yeni);
            }
        }





        if (tblOgrenciler != null) {
            tblOgrenciler.refresh();
        }



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
    private void checkcontrol(){
        if(chkAdvancedMode.isSelected()){
            Radio_Bolum_bubble.setDisable(true);
            Radio_Bolum_selection.setDisable(true);
            Radio_Bolum_Insertion.setDisable(true);
            Radio_Bolum_marge.setDisable(true);
            Radio_bubble.setDisable(true);
            Radio_Insertion.setDisable(true);
            Radio_selection.setDisable(true);
            Radio_marge.setDisable(true);
            Radio_Sinif_bubble.setDisable(true);
            Radio_Sinif_selection.setDisable(true);
            Radio_Sinif_Insertion.setDisable(true);
            Radio_Sinif_marge.setDisable(true);
            radioLineer.setDisable(true);
            radioOverflow.setDisable(true);
            Radio_Bolum_Quick.setDisable(true);
            Radio_Sinif_Quick.setDisable(true);
            Radio_Quick.setDisable(true);
        }else {
            radioLineer.setDisable(false);
            radioOverflow.setDisable(false);
            Radio_Bolum_bubble.setDisable(false);
            Radio_Bolum_selection.setDisable(false);
            Radio_Bolum_Insertion.setDisable(false);
            Radio_Bolum_marge.setDisable(false);
            Radio_bubble.setDisable(false);
            Radio_selection.setDisable(false);
            Radio_marge.setDisable(false);
            Radio_Sinif_bubble.setDisable(false);
            Radio_Sinif_selection.setDisable(false);
            Radio_Sinif_Insertion.setDisable(false);
            Radio_Sinif_marge.setDisable(false);
            Radio_Insertion.setDisable(false);
            Radio_Bolum_Quick.setDisable(false);
            Radio_Sinif_Quick.setDisable(false);
            Radio_Quick.setDisable(false);
        }
    }
    @FXML
    private void ogr_no_sirala_hash(){



        LineerOgrNoAlgoritmaService algoritmaService = new LineerOgrNoAlgoritmaService();
        OverflowOgrNoAlgoritmaService OverFlowAlgoritmaService=new OverflowOgrNoAlgoritmaService();
        HashMapBolumAlgoritmaService HashMapAlgoritmaService=new HashMapBolumAlgoritmaService();
        List<Ogrenci> modernSirali = List.of();
        Ogrenci[] sortedStudents = new Ogrenci[13000];
        String type="";


        long startTime = System.nanoTime();
        if(chkAdvancedMode.isSelected()){
            type="Gelişmiş modda sıralama (Öğrenci No)";
            modernSirali = HashMapAlgoritmaService.ogrNoSiraArtan(HashmapTable);
            System.out.println("\nModern (Collections.sort) ile sıralama:");
            for (Ogrenci o : modernSirali) {
                System.out.println(o.getOgrNo() + " - " + o.getIsim());
            }
        }else{
            if(radioLineer.isSelected()){
                if(Radio_bubble.isSelected()){
                    type="(Lineer) Bubble Sort (Öğrenci No)";
                    sortedStudents = algoritmaService.ogr_no_sira_bubble_sort(hashTable);
                } else if (Radio_marge.isSelected()) {
                    type="(Lineer) Marge Sort (Öğrenci No)";
                    sortedStudents = algoritmaService.ogr_no_sira_merge_sort(hashTable);
                } else if (Radio_Insertion.isSelected()) {
                    type="(Lineer) Insertion Sort (Öğrenci No)";
                    sortedStudents = algoritmaService.ogr_no_sira_insertion_sort(hashTable);
                } else if (Radio_selection.isSelected()) {
                    type="(Lineer) selection Sort (Öğrenci No)";
                    sortedStudents = algoritmaService.ogr_no_sira_selection_sort(hashTable);
                }
                else if (Radio_Quick.isSelected()) {
                    type="(Lineer) Quick Sort (Öğrenci No)";
                    sortedStudents = algoritmaService.Ogr_no_quick_sort(hashTable);
                }
            }else{
                if(Radio_bubble.isSelected()){
                    type="(OverFlow) Bubble Sort (Öğrenci No)";
                    sortedStudents = OverFlowAlgoritmaService.ogr_no_sira_bubble_sort(OverflowHashTable);
                } else if (Radio_marge.isSelected()) {
                    type="(OverFlow) Marge Sort (Öğrenci No)";
                    sortedStudents = OverFlowAlgoritmaService.ogr_no_sira_merge_sort(OverflowHashTable);
                } else if (Radio_Insertion.isSelected()) {
                    type="(OverFlow) Insertion Sort (Öğrenci No)";
                    sortedStudents = OverFlowAlgoritmaService.ogr_no_sira_insertion_sort(OverflowHashTable);
                } else if (Radio_selection.isSelected()) {
                    type="(OverFlow) selection Sort (Öğrenci No)";
                    sortedStudents = OverFlowAlgoritmaService.ogr_no_sira_selection_sort(OverflowHashTable);
                } else if (Radio_Quick.isSelected()) {
                    type="(OverFlow) Quick Sort (Öğrenci No)";
                    sortedStudents = OverFlowAlgoritmaService.ogr_no_sira_quick_sort(OverflowHashTable);

                }

            }

        }



        long endTime = System.nanoTime();

        double durationInSeconds = (endTime - startTime) / 1_000_000_000.0;


        String timestamp = LocalDateTime.now().toString();
        PerformansKaydi kayit = new PerformansKaydi(
                timestamp,
                "Sıralama",
                type,
                durationInSeconds,
                sortedStudents.length // Sıralanan veri boyutu
        );


        try {
            performans_txt.writePerformansKaydi(kayit);
            System.out.printf("Performans kaydı başarıyla yazıldı. Süre: %.6f saniye.%n", durationInSeconds);
        } catch (IOException e) {
            showAlert("Hata", "Performans dosyasına yazılamadı!");
            e.printStackTrace();
        }
        if(chkAdvancedMode.isSelected()){
            try {

                if(modernSirali!=null){
                    ogr_no_sira_txt.writeOgrencimodern(modernSirali);
                }

                showAlert("Başarılı", "Sıralanmış öğrenciler 'ogr_no_sira.txt' dosyasına başarıyla yazıldı.");
            } catch (IOException e) {
                showAlert("Hata", "Dosyaya yazma işlemi sırasında hata oluştu: " + e.getMessage());
                e.printStackTrace();
            }
        }else{
            System.out.println("Öğrenci numarasına göre sıralanmış dizinin ilk 10 elemanı:");
            for (int i = 0; i < Math.min(10, sortedStudents.length); i++) {
                System.out.println(sortedStudents[i].getOgrNo());
            }


            try {

                ogr_no_sira_txt.writeOgrenciArray(sortedStudents);
                showAlert("Başarılı", "Sıralanmış öğrenciler 'ogr_no_sira.txt' dosyasına başarıyla yazıldı.");
            } catch (IOException e) {
                showAlert("Hata", "Dosyaya yazma işlemi sırasında hata oluştu: " + e.getMessage());
                e.printStackTrace();
            }
        }



    }
    @FXML
    private void bolum_sirala_hash(){
        LineerBolumAlgoritmaService algoritmaService = new LineerBolumAlgoritmaService();
        OverflowBolumAlgoritmaService overflowAlgoritmaService = new OverflowBolumAlgoritmaService();
        HashMapBolumAlgoritmaService hashMapBolumAlgoritmaService = new HashMapBolumAlgoritmaService();
        List<Ogrenci> ModernList=List.of();
        Ogrenci[] sortedStudents = new Ogrenci[13000];
        String type="";
        long startTime = System.nanoTime();
        if(chkAdvancedMode.isSelected()){
            type="Gelişmiş modda Bolum sıralama (GANO)";
            ModernList = hashMapBolumAlgoritmaService.Bolum_sira_azalan(HashmapTable);
            System.out.println("\nModern (Collections.sort) ile sıralama:");
            for (Ogrenci o : ModernList) {
                System.out.println(o.getOgrNo() + " - " + o.getIsim());
            }

        }else{
            if(radioLineer.isSelected()){
                if(Radio_Bolum_bubble.isSelected()){
                    type="(Lineer) Bubble Sort (Bölüm sırası)";
                    sortedStudents = algoritmaService.Bolum_sira_bubble_sort(hashTable);
                } else if (Radio_Bolum_marge.isSelected()) {
                    type="(Lineer) Marge Sort (Bölüm sırası)";
                    sortedStudents = algoritmaService.Bolum_sira_merge_sort(hashTable);
                } else if (Radio_Bolum_Insertion.isSelected()) {
                    type="(Lineer) Insertion Sort (Bölüm sırası)";
                    sortedStudents = algoritmaService.ogr_no_sira_insertion_sort(hashTable);
                } else if (Radio_Bolum_selection.isSelected()) {
                    type="(Lineer) selection Sort (Bölüm sırası)";
                    sortedStudents = algoritmaService.Bolum_sira_selection_sort(hashTable);
                }else if (Radio_Bolum_Quick.isSelected()) {
                    type="(Lineer) Quick Sort (Bölüm sırası)";
                    sortedStudents = algoritmaService.Bolum_sira_quick_sort(hashTable);
                }
            }else{
                if(Radio_Bolum_bubble.isSelected()){
                    type="(OverFlow) Bubble Sort (Bölüm sırası)";
                    sortedStudents = overflowAlgoritmaService.Bolum_sira_bubble_sort(OverflowHashTable);
                } else if (Radio_Bolum_marge.isSelected()) {
                    type="(OverFlow) Marge Sort (Bölüm sırası)";
                    sortedStudents = overflowAlgoritmaService.Bolum_sira_merge_sort(OverflowHashTable);
                } else if (Radio_Bolum_Insertion.isSelected()) {
                    type="(OverFlow) Insertion Sort (Bölüm sırası)";
                    sortedStudents = overflowAlgoritmaService.ogr_no_sira_insertion_sort(OverflowHashTable);
                } else if (Radio_Bolum_selection.isSelected()) {
                    type="(OverFlow) selection Sort (Bölüm sırası)";
                    sortedStudents = overflowAlgoritmaService.Bolum_sira_selection_sort(OverflowHashTable);
                } else if (Radio_Bolum_Quick.isSelected()) {
                    type="(OverFlow) Quick Sort (Bölüm sırası)";
                    sortedStudents = overflowAlgoritmaService.Bolum_sira_quick_sort(OverflowHashTable);

                }

            }

        }


        long endTime = System.nanoTime();

        double durationInSeconds = (endTime - startTime) / 1_000_000_000.0;

        String timestamp = LocalDateTime.now().toString();
        PerformansKaydi kayit = new PerformansKaydi(
                timestamp,
                "Sıralama",
                type,
                durationInSeconds,
                sortedStudents.length // Sıralanan veri boyutu
        );


        try {
            performans_txt.writePerformansKaydi(kayit);
            System.out.printf("Performans kaydı başarıyla yazıldı. Süre: %.6f saniye.%n", durationInSeconds);
        } catch (IOException e) {
            showAlert("Hata", "Performans dosyasına yazılamadı!");
            e.printStackTrace();
        }




        if(chkAdvancedMode.isSelected()){
            try {

                if(ModernList!=null){
                    bolum_sira_txt.writeOgrencimodern(ModernList);
                }

                showAlert("Başarılı", "Sıralanmış öğrenciler 'ogr_no_sira.txt' dosyasına başarıyla yazıldı.");
            } catch (IOException e) {
                showAlert("Hata", "Dosyaya yazma işlemi sırasında hata oluştu: " + e.getMessage());
                e.printStackTrace();
            }
        }else{
            System.out.println("Öğrenci numarasına göre sıralanmış dizinin ilk 10 elemanı:");
            for (int i = 0; i < Math.min(10, sortedStudents.length); i++) {
                System.out.println(sortedStudents[i].getOgrNo());
            }


            try {

                bolum_sira_txt.writeOgrenciArray(sortedStudents);
                showAlert("Başarılı", "Sıralanmış öğrenciler 'ogr_no_sira.txt' dosyasına başarıyla yazıldı.");
            } catch (IOException e) {
                showAlert("Hata", "Dosyaya yazma işlemi sırasında hata oluştu: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
    @FXML
    private void sinif_sirala_hash(){
        LineerSinifAlgoritmaService algoritmaService = new LineerSinifAlgoritmaService();
        OverflowSinifAlgoritmaService overflowAlgoritmaService = new OverflowSinifAlgoritmaService();
        HashMapBolumAlgoritmaService hashMapTable = new HashMapBolumAlgoritmaService();
        List<Ogrenci> ModernList=List.of();
        Ogrenci[] sortedStudents = new Ogrenci[13000];
        String type="";
        long startTime = System.nanoTime();
        if(chkAdvancedMode.isSelected()){
            type="Gelişmiş modda sınıf sıralama (GANO)";
            ModernList = hashMapTable.sinifSiraGanoAzalan(HashmapTable);
            System.out.println("\nModern (Collections.sort) ile sıralama:");
            for (Ogrenci o : ModernList) {
                System.out.println(o.getOgrNo() + " - " + o.getIsim());
            }

        }else{
            if(radioLineer.isSelected()){
                if(Radio_Sinif_bubble.isSelected()){
                    type="(Lineer) Bubble Sort (Sınıf sırası)";
                    sortedStudents = algoritmaService.Sinif_sira_bubble_sort(hashTable);
                } else if (Radio_Sinif_marge.isSelected()) {
                    type="(Lineer) Marge Sort (Sınıf sırası)";
                    sortedStudents = algoritmaService.Sinif_sira_merge_sort(hashTable);
                } else if (Radio_Sinif_Insertion.isSelected()) {
                    type="(Lineer) Insertion Sort (Sınıf sırası)";
                    sortedStudents = algoritmaService.Sinif_sira_insertion_sort(hashTable);
                } else if (Radio_Sinif_selection.isSelected()) {
                    type="(Lineer) selection Sort (Sınıf sırası)";
                    sortedStudents = algoritmaService.Sinif_sira_selection_sort(hashTable);
                }else if (Radio_Sinif_Quick.isSelected()) {
                    type="(Lineer) Quick Sort (Sınıf sırası)";
                    sortedStudents = algoritmaService.Sinif_sira_quick_sort(hashTable);
                }
            }else{
                if(Radio_Sinif_bubble.isSelected()){
                    type="(OverFlow) Bubble Sort (Sınıf sırası)";
                    sortedStudents = overflowAlgoritmaService.Sinif_sira_bubble_sort(OverflowHashTable);
                } else if (Radio_Sinif_marge.isSelected()) {
                    type="(OverFlow) Marge Sort (Sınıf sırası)";
                    sortedStudents = overflowAlgoritmaService.Sinif_sira_merge_sort(OverflowHashTable);
                } else if (Radio_Sinif_Insertion.isSelected()) {
                    type="(OverFlow) Insertion Sort (Sınıf sırası)";
                    sortedStudents = overflowAlgoritmaService.Sinif_sira_insertion_sort(OverflowHashTable);
                } else if (Radio_Sinif_selection.isSelected()) {
                    type="(OverFlow) selection Sort (Sınıf sırası)";
                    sortedStudents = overflowAlgoritmaService.Sinif_sira_selection_sort(OverflowHashTable);
                }else if (Radio_Sinif_Quick.isSelected()) {
                    type="(OverFlow) Quick Sort (Sınıf sırası)";
                    sortedStudents = overflowAlgoritmaService.Sinif_sira_quick_sort(OverflowHashTable);
                }
            }

        }


        long endTime = System.nanoTime();

        double durationInSeconds = (endTime - startTime) / 1_000_000_000.0;


        String timestamp = LocalDateTime.now().toString();
        PerformansKaydi kayit = new PerformansKaydi(
                timestamp,
                "Sıralama",
                type,
                durationInSeconds,
                sortedStudents.length // Sıralanan veri boyutu
        );


        try {
            performans_txt.writePerformansKaydi(kayit);
            System.out.printf("Performans kaydı başarıyla yazıldı. Süre: %.6f saniye.%n", durationInSeconds);
        } catch (IOException e) {
            showAlert("Hata", "Performans dosyasına yazılamadı!");
            e.printStackTrace();
        }


        if(chkAdvancedMode.isSelected()){
            try {
                // FileManager'ın Ogrenci[] dizisini yazabilen metodunu çağırıyoruz.
                if(ModernList!=null){
                    sinif_sira_txt.writeOgrencimodern(ModernList);
                }

                showAlert("Başarılı", "Sıralanmış öğrenciler 'ogr_no_sira.txt' dosyasına başarıyla yazıldı.");
            } catch (IOException e) {
                showAlert("Hata", "Dosyaya yazma işlemi sırasında hata oluştu: " + e.getMessage());
                e.printStackTrace();
            }
        }else{
            System.out.println("Öğrenci numarasına göre sıralanmış dizinin ilk 10 elemanı:");
            for (int i = 0; i < Math.min(10, sortedStudents.length); i++) {
                System.out.println(sortedStudents[i].getOgrNo());
            }

            try {

                sinif_sira_txt.writeOgrenciArray(sortedStudents);
                ogrenciler_txt.writeOgrenciArray(sortedStudents);
                ogrenciListesi.setAll(sortedStudents);
                tblOgrenciler.setItems(ogrenciListesi);
                showAlert("Başarılı", "Sıralanmış öğrenciler 'ogr_no_sira.txt' dosyasına başarıyla yazıldı.");
            } catch (IOException e) {
                showAlert("Hata", "Dosyaya yazma işlemi sırasında hata oluştu: " + e.getMessage());
                e.printStackTrace();
            }
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
        String type="";

        try {
            long startTime = System.nanoTime();
            if(chkAdvancedMode.isSelected()){
                type = "Gelişmiş mod (Oğrenci no)";
                int no = Integer.parseInt(input);
                Ogrenci bulunan = HashmapTable.searchByOgrNo(no);
                if (bulunan != null) {
                    tblOgrenciler.getSelectionModel().select(bulunan);
                    tblOgrenciler.scrollTo(bulunan);
                } else {
                    showAlert("Bulunamadı", "Bu numaraya ait öğrenci yok!");
                }
            }else{
                if(radioLineer.isSelected()){
                    type = "lineer proping (Oğrenci no)";
                    int no = Integer.parseInt(input);
                    Ogrenci bulunan = hashTable.searchByOgrNo(no);
                    if (bulunan != null) {
                        tblOgrenciler.getSelectionModel().select(bulunan);
                        tblOgrenciler.scrollTo(bulunan);
                    } else {
                        showAlert("Bulunamadı", "Bu numaraya ait öğrenci yok!");
                    }
                }else {
                    type = "Overflow proping (Oğrenci no)";
                    int no = Integer.parseInt(input);
                    Ogrenci bulunan = OverflowHashTable.searchByOgrNo(no);
                    if (bulunan != null) {
                        tblOgrenciler.getSelectionModel().select(bulunan);
                        tblOgrenciler.scrollTo(bulunan);
                    } else {
                        showAlert("Bulunamadı", "Bu numaraya ait öğrenci yok!");
                    }
                }
            }
            long endTime = System.nanoTime();


            double durationInSeconds = (endTime - startTime) / 1_000_000_000.0;


            String timestamp = LocalDateTime.now().toString();
            PerformansKaydi kayit = new PerformansKaydi(
                    timestamp,
                    "Arama",
                    type,
                    durationInSeconds,
                    10000
            );


            try {
                performans_txt.writePerformansKaydi(kayit);
                System.out.printf("Performans kaydı başarıyla yazıldı. Süre: %.6f saniye.%n", durationInSeconds);
            } catch (IOException e) {
                showAlert("Hata", "Performans dosyasına yazılamadı!");
                e.printStackTrace();
            }

        } catch (NumberFormatException e) {
            showAlert("Hata", "Geçerli bir numara girin!");
        }
    }

    @FXML
    private void onAdaGoreAra() {
        String ad = txtArama.getText().trim().toLowerCase();
        if (ad.isEmpty()) return;

        String type = "";
        try {
            long startTime = System.nanoTime();
            ObservableList<Ogrenci> filtreli;

            if (chkAdvancedMode.isSelected()) {

                type = "Gelişmiş mod (İsim)";
                filtreli = FXCollections.observableArrayList();
                for (Ogrenci o : HashmapTable.getAllStudents()) {
                    if (o != null && o.getIsim().toLowerCase().contains(ad)) {
                        filtreli.add(o);
                    }
                }
            } else {

                if (radioLineer.isSelected()) {
                    type = "Lineer probing (İsim)";
                    filtreli = FXCollections.observableArrayList();
                    for (Ogrenci o : hashTable.getAllStudents()) {
                        if (o != null && o.getIsim().toLowerCase().contains(ad)) {
                            filtreli.add(o);
                        }
                    }
                } else {
                    type = "Overflow hashing (İsim)";
                    filtreli = FXCollections.observableArrayList();
                    for (Ogrenci o : OverflowHashTable.getAllStudents()) {
                        if (o != null && o.getIsim().toLowerCase().contains(ad)) {
                            filtreli.add(o);
                        }
                    }
                }
            }

            long endTime = System.nanoTime();
            double durationInSeconds = (endTime - startTime) / 1_000_000_000.0;


            if (filtreli.isEmpty()) {
                showAlert("Bulunamadı", "Bu isme ait öğrenci yok!");
            } else {
                tblOgrenciler.setItems(filtreli);
            }


            String timestamp = LocalDateTime.now().toString();
            PerformansKaydi kayit = new PerformansKaydi(
                    timestamp,
                    "Arama",
                    type,
                    durationInSeconds,
                    filtreli.size()
            );


            try {
                performans_txt.writePerformansKaydi(kayit);
                System.out.printf("Performans kaydı başarıyla yazıldı. Süre: %.6f saniye.%n", durationInSeconds);
            } catch (IOException e) {
                showAlert("Hata", "Performans dosyasına yazılamadı!");
                e.printStackTrace();
            }

        } catch (Exception e) {
            showAlert("Hata", "Arama sırasında bir hata oluştu!");
            e.printStackTrace();
        }
    }


    @FXML
    private void onHashGoster() {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("hash_view.fxml"));
            Parent root = loader.load();

            HashViewController controller = loader.getController();
            controller.setTables(HashmapTable, OverflowHashTable, hashTable);

            Stage stage = new Stage();
            stage.setTitle("Hash Tablo Görüntüleyici");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    @FXML
    private void onAraTikla() {
        String input = txtArama.getText().trim();
        if (input.isEmpty()) return;

        String type = "";
        try {
            int sinif = Integer.parseInt(input);
            long startTime = System.nanoTime();
            ObservableList<Ogrenci> filtreli;

            if (chkAdvancedMode.isSelected()) {

                type = "Gelişmiş mod (Sınıf)";
                filtreli = FXCollections.observableArrayList();
                for (Ogrenci o : HashmapTable.getAllStudents()) {
                    if (o != null && o.getSinif() == sinif) {
                        filtreli.add(o);
                    }
                }
            } else {

                if (radioLineer.isSelected()) {
                    type = "Lineer probing (Sınıf)";
                    filtreli = FXCollections.observableArrayList();
                    for (Ogrenci o : hashTable.getAllStudents()) {
                        if (o != null && o.getSinif() == sinif) {
                            filtreli.add(o);
                        }
                    }
                } else {
                    type = "Overflow hashing (Sınıf)";
                    filtreli = FXCollections.observableArrayList();
                    for (Ogrenci o : OverflowHashTable.getAllStudents()) {
                        if (o != null && o.getSinif() == sinif) {
                            filtreli.add(o);
                        }
                    }
                }
            }

            long endTime = System.nanoTime();
            double durationInSeconds = (endTime - startTime) / 1_000_000_000.0;


            if (filtreli.isEmpty()) {
                showAlert("Bulunamadı", "Bu sınıfa ait öğrenci yok!");
            } else {
                tblOgrenciler.setItems(filtreli);
            }


            String timestamp = LocalDateTime.now().toString();
            PerformansKaydi kayit = new PerformansKaydi(
                    timestamp,
                    "Arama",
                    type,
                    durationInSeconds,
                    filtreli.size()
            );
            try {
                performans_txt.writePerformansKaydi(kayit);
                System.out.printf("Performans kaydı başarıyla yazıldı. Süre: %.6f saniye.%n", durationInSeconds);
            } catch (IOException e) {
                showAlert("Hata", "Performans dosyasına yazılamadı!");
                e.printStackTrace();
            }

        } catch (NumberFormatException e) {
            showAlert("Hata", "Lütfen geçerli bir sınıf numarası girin!");
        } catch (Exception e) {
            showAlert("Hata", "Arama sırasında bir hata oluştu!");
            e.printStackTrace();
        }
    }

    private void showAlert(String baslik, String mesaj) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(baslik);
        alert.setHeaderText(null);
        alert.setContentText(mesaj);
        alert.showAndWait();
    }
    @FXML
    private void performansGoster(){
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("Performans_view.fxml"));
            Parent root = loader.load();



            Stage stage = new Stage();
            stage.setTitle("Performans Tablo Görüntüleyici");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
