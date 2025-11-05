package org.example.veri_yonetimi_proje.hash;

import org.example.veri_yonetimi_proje.model.Ogrenci;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HashMapTable implements HashTable {
    private HashMap<Integer, Ogrenci> table;
    private static final String FILE_PATH = "ogrenciler.txt";

    public HashMapTable() {
        this.table = new HashMap<>();
    }

    @Override
    public void insert(Ogrenci o) {
        if (o == null) return;


        table.put(o.getOgrNo(), o);
        System.out.println("HashMap'e eklendi: " + o.getOgrNo());

        writeToFile(o);
    }
    @Override
    public List<Ogrenci> getAllStudents() {
        return new ArrayList<>(table.values());
    }

    @Override
    public Ogrenci searchByOgrNo(int ogrNo) {
        return table.get(ogrNo);
    }

    @Override
    public boolean delete(int ogrNo) {
        if (table.containsKey(ogrNo)) {
            table.remove(ogrNo);
            rewriteFile();
            return true;
        }
        return false;
    }

    @Override
    public void showHashTable() {
        System.out.println("HashMap İçeriği:");
        for (Map.Entry<Integer, Ogrenci> entry : table.entrySet()) {
            System.out.printf("[%d] -> %s%n", entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void listAll() {
        System.out.println("Tüm Öğrenciler:");
        for (Ogrenci o : table.values()) {
            System.out.println(o);
        }
    }


    private void writeToFile(Ogrenci o) {
        try (FileWriter fw = new FileWriter(FILE_PATH, true)) {
            fw.write(o.toString() + "\n");
        } catch (IOException e) {
            System.out.println("Dosyaya yazılamadı: " + e.getMessage());
        }
    }

    private void rewriteFile() {
        try (FileWriter fw = new FileWriter(FILE_PATH, false)) {
            for (Ogrenci o : table.values()) {
                fw.write(o.toString() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Dosya yeniden yazılamadı: " + e.getMessage());
        }
    }


    public HashMap<Integer, Ogrenci> getTable() {
        return table;
    }

    public String getDisplayValue(int ogrNo) {
        Ogrenci o = table.get(ogrNo);
        return o == null ? "EMPTY" : o.toString();
    }
}
