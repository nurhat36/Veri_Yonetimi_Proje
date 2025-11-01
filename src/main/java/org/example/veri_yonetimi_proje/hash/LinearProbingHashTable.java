package org.example.veri_yonetimi_proje.hash;

import org.example.veri_yonetimi_proje.model.Ogrenci;

import java.io.FileWriter;
import java.io.IOException;

public class LinearProbingHashTable implements HashTable {
    private Ogrenci[] table;
    private boolean[] tombstone;
    private int M;
    private static final String FILE_PATH = "ogrenciler.txt";

    public LinearProbingHashTable(int size) {
        this.M = size;
        table = new Ogrenci[M];
        tombstone = new boolean[M];
    }

    private int hash(int k) {
        return Math.abs(k) % M;
    }

    @Override
    public void insert(Ogrenci o) {
        int idx = hash(o.getOgrNo());
        int start = idx;
        while (table[idx] != null && !tombstone[idx]) {
            idx = (idx + 1) % M;
            if (idx == start) throw new RuntimeException("Hash tablosu dolu!");
        }
        table[idx] = o;
        tombstone[idx] = false;
        System.out.println("Hash tablosu eklendi."+table[idx].getOgrNo());
        writeToFile(o);
    }

    @Override
    public Ogrenci searchByOgrNo(int ogrNo) {
        int idx = hash(ogrNo);
        int start = idx;
        while (table[idx] != null || tombstone[idx]) {
            if (table[idx] != null && table[idx].getOgrNo() == ogrNo)
                return table[idx];
            idx = (idx + 1) % M;
            if (idx == start) break;
        }
        return null;
    }

    @Override
    public boolean delete(int ogrNo) {
        int idx = hash(ogrNo);
        int start = idx;
        while (table[idx] != null || tombstone[idx]) {
            if (table[idx] != null && table[idx].getOgrNo() == ogrNo) {
                table[idx] = null;
                tombstone[idx] = true;
                rewriteFile();
                return true;
            }
            idx = (idx + 1) % M;
            if (idx == start) break;
        }
        return false;
    }

    @Override
    public void showHashTable() {
        for (int i = 0; i < M; i++) {
            System.out.printf("[%d] -> %s %s%n", i,
                    table[i] == null ? "EMPTY" : String.valueOf(table[i].getOgrNo()),
                    tombstone[i] ? "(TOMBSTONE)" : "");
        }
    }

    @Override
    public void listAll() {
        System.out.println("Tüm Öğrenciler:");
        for (Ogrenci o : table) {
            if (o != null)
                System.out.println(o);
        }
    }

    // --- Dosya İşlemleri ---
    private void writeToFile(Ogrenci o) {
        try (FileWriter fw = new FileWriter(FILE_PATH, true)) {
            fw.write(o.toString() + "\n");
        } catch (IOException e) {
            System.out.println("Dosyaya yazılamadı: " + e.getMessage());
        }
    }

    private void rewriteFile() {
        try (FileWriter fw = new FileWriter(FILE_PATH, false)) {
            for (Ogrenci o : table) {
                if (o != null) fw.write(o.toString() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Dosya yeniden yazılamadı: " + e.getMessage());
        }
    }
    public String getDisplayValue(int index) {
        if (table[index] == null) {
            return tombstone[index] ? "(TOMBSTONE)" : "EMPTY";
        }
        return table[index].toString();
    }
    public Ogrenci[] getTable() {
        return table;
    }

}