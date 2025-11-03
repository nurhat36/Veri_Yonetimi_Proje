package org.example.veri_yonetimi_proje.Services.HashMapServices;

import org.example.veri_yonetimi_proje.hash.HashMapTable;
import org.example.veri_yonetimi_proje.model.Ogrenci;

import java.util.*;

public class HashMapBolumAlgoritmaService {

    /**
     * HashMap içeriğini bir listeye dönüştürür.
     */
    private List<Ogrenci> getAllOgrenciler(HashMapTable hashTable) {
        return new ArrayList<>(hashTable.getTable().values());
    }

    // ===================== BÖLÜM SIRASI (GANO'ya göre artan) =======================
    public List<Ogrenci> Bolum_sira_artan(HashMapTable hashTable) {
        List<Ogrenci> ogrenciler = getAllOgrenciler(hashTable);

        Collections.sort(ogrenciler, Comparator.comparingDouble(Ogrenci::getGano));

        int sira = 1;
        for (Ogrenci o : ogrenciler) {
            o.setBolumSira(sira++);
        }

        return ogrenciler;
    }

    // ===================== BÖLÜM SIRASI (GANO'ya göre azalan) =======================
    public List<Ogrenci> Bolum_sira_azalan(HashMapTable hashTable) {
        List<Ogrenci> ogrenciler = getAllOgrenciler(hashTable);

        Collections.sort(ogrenciler, Comparator.comparingDouble(Ogrenci::getGano).reversed());

        int sira = 1;
        for (Ogrenci o : ogrenciler) {
            o.setBolumSira(sira++);
        }

        return ogrenciler;
    }

    // Öğrencileri sınıflarına göre grupla (1–4)
    private Map<Integer, List<Ogrenci>> groupByClass(List<Ogrenci> ogrenciler) {
        Map<Integer, List<Ogrenci>> grouped = new HashMap<>();
        for (Ogrenci o : ogrenciler) {
            int sinif = o.getSinif();
            if (sinif >= 1 && sinif <= 4) {
                grouped.putIfAbsent(sinif, new ArrayList<>());
                grouped.get(sinif).add(o);
            }
        }
        return grouped;
    }

    // =================================================================================
    // SIRALAMA METOTLARI (GANO'ya göre AZALAN)
    // =================================================================================

    public List<Ogrenci> sinifSiraGanoAzalan(HashMapTable hashTable) {
        List<Ogrenci> all = getAllOgrenciler(hashTable);
        Map<Integer, List<Ogrenci>> grouped = groupByClass(all);

        for (List<Ogrenci> sinifList : grouped.values()) {
            // GANO’ya göre azalan sırala
            sinifList.sort(Comparator.comparingDouble(Ogrenci::getGano).reversed());
            // Sınıf sırası ata
            for (int i = 0; i < sinifList.size(); i++) {
                sinifList.get(i).setSinifSira(i + 1);
            }
        }

        return all;
    }

    public List<Ogrenci> sinifSiraGanoArtan(HashMapTable hashTable) {
        List<Ogrenci> all = getAllOgrenciler(hashTable);
        Map<Integer, List<Ogrenci>> grouped = groupByClass(all);

        for (List<Ogrenci> sinifList : grouped.values()) {
            // GANO’ya göre artan sırala
            sinifList.sort(Comparator.comparingDouble(Ogrenci::getGano));
            // Sınıf sırası ata
            for (int i = 0; i < sinifList.size(); i++) {
                sinifList.get(i).setSinifSira(i + 1);
            }
        }

        return all;
    }
    /**
     * Öğrenci numarasına göre artan sıralama (Küçükten büyüğe)
     */
    public List<Ogrenci> ogrNoSiraArtan(HashMapTable hashTable) {
        List<Ogrenci> ogrenciler = getAllOgrenciler(hashTable);

        // Java’nın sort metodu ile sıralama
        ogrenciler.sort(Comparator.comparingInt(Ogrenci::getOgrNo));

        // Listeyi diziye çevirip döndür
        return ogrenciler;
    }

    /**
     * Öğrenci numarasına göre azalan sıralama (Büyükten küçüğe)
     */
    public List<Ogrenci> ogrNoSiraAzalan(HashMapTable hashTable) {
        List<Ogrenci> ogrenciler = getAllOgrenciler(hashTable);

        ogrenciler.sort(Comparator.comparingInt(Ogrenci::getOgrNo).reversed());

        return ogrenciler;
    }

    // ===================== EKRANA YAZDIRMA (Opsiyonel) =======================
    public void printList(String baslik, List<Ogrenci> ogrenciler) {
        System.out.println("\n=== " + baslik + " ===");
        for (Ogrenci o : ogrenciler) {
            System.out.printf("%d - %s %s - GANO: %.2f - Bölüm Sıra: %d - Sınıf Sıra: %d%n",
                    o.getOgrNo(), o.getIsim(),o.getSoyad(), o.getGano(), o.getBolumSira(), o.getSinifSira());
        }
    }
}
