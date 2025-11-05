package org.example.veri_yonetimi_proje.Services.HashMapServices;

import org.example.veri_yonetimi_proje.hash.HashMapTable;
import org.example.veri_yonetimi_proje.model.Ogrenci;

import java.util.*;

public class HashMapBolumAlgoritmaService {


    private List<Ogrenci> getAllOgrenciler(HashMapTable hashTable) {
        return new ArrayList<>(hashTable.getTable().values());
    }


    public List<Ogrenci> Bolum_sira_artan(HashMapTable hashTable) {
        List<Ogrenci> ogrenciler = getAllOgrenciler(hashTable);

        Collections.sort(ogrenciler, Comparator.comparingDouble(Ogrenci::getGano));

        int sira = 1;
        for (Ogrenci o : ogrenciler) {
            o.setBolumSira(sira++);
        }

        return ogrenciler;
    }


    public List<Ogrenci> Bolum_sira_azalan(HashMapTable hashTable) {
        List<Ogrenci> ogrenciler = getAllOgrenciler(hashTable);

        Collections.sort(ogrenciler, Comparator.comparingDouble(Ogrenci::getGano).reversed());

        int sira = 1;
        for (Ogrenci o : ogrenciler) {
            o.setBolumSira(sira++);
        }

        return ogrenciler;
    }


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



    public List<Ogrenci> sinifSiraGanoAzalan(HashMapTable hashTable) {
        List<Ogrenci> all = getAllOgrenciler(hashTable);
        Map<Integer, List<Ogrenci>> grouped = groupByClass(all);

        for (List<Ogrenci> sinifList : grouped.values()) {

            sinifList.sort(Comparator.comparingDouble(Ogrenci::getGano).reversed());

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

            sinifList.sort(Comparator.comparingDouble(Ogrenci::getGano));

            for (int i = 0; i < sinifList.size(); i++) {
                sinifList.get(i).setSinifSira(i + 1);
            }
        }

        return all;
    }

    public List<Ogrenci> ogrNoSiraArtan(HashMapTable hashTable) {
        List<Ogrenci> ogrenciler = getAllOgrenciler(hashTable);


        ogrenciler.sort(Comparator.comparingInt(Ogrenci::getOgrNo));


        return ogrenciler;
    }


    public List<Ogrenci> ogrNoSiraAzalan(HashMapTable hashTable) {
        List<Ogrenci> ogrenciler = getAllOgrenciler(hashTable);

        ogrenciler.sort(Comparator.comparingInt(Ogrenci::getOgrNo).reversed());

        return ogrenciler;
    }


    public void printList(String baslik, List<Ogrenci> ogrenciler) {
        System.out.println("\n=== " + baslik + " ===");
        for (Ogrenci o : ogrenciler) {
            System.out.printf("%d - %s %s - GANO: %.2f - Bölüm Sıra: %d - Sınıf Sıra: %d%n",
                    o.getOgrNo(), o.getIsim(),o.getSoyad(), o.getGano(), o.getBolumSira(), o.getSinifSira());
        }
    }
}
