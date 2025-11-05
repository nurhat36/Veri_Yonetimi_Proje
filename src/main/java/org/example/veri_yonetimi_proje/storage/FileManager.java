package org.example.veri_yonetimi_proje.storage;

import org.example.veri_yonetimi_proje.model.Ogrenci;
import org.example.veri_yonetimi_proje.model.PerformansKaydi;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    private final File dataFile;

    public FileManager(String path) {
        this.dataFile = new File(path);
    }


    public List<Ogrenci> readAll() throws IOException {
        List<Ogrenci> list = new ArrayList<>();
        if (!dataFile.exists()) return list;
        try (BufferedReader br = new BufferedReader(new FileReader(dataFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                if (line.startsWith("DELETED,")) continue; // tombstone
                Ogrenci o = Ogrenci.fromCsv(line);
                if (o != null) list.add(o);
            }
        }
        return list;
    }


    public void append(Ogrenci o) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(dataFile, true))) {
            bw.write(o.toString());
            bw.newLine();
        }
    }


    public void overwriteAll(List<Ogrenci> all) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(dataFile, false))) {
            for (Ogrenci o : all) {
                bw.write(o.toString());
                bw.newLine();
            }
        }
    }
    public void writeOgrenciArray(Ogrenci[] students) throws IOException {
        try (FileWriter fw = new FileWriter(dataFile, false)) {
            for (Ogrenci o : students) {
                if (o != null) {
                    fw.write(o.toString() + "\n");
                }
            }
        }
    }
    public void writeOgrencimodern(List<Ogrenci> students) throws IOException {
        try (FileWriter fw = new FileWriter(dataFile, false)) {
            for (Ogrenci o : students) {
                if (o != null) {
                    fw.write(o.toString() + "\n");
                }
            }
        }
    }
    public void writePerformansKaydi(PerformansKaydi kayit) throws IOException {
        // true: dosyaya ekleme (append) modunda açar
        try (FileWriter fw = new FileWriter(dataFile, true)) {
            fw.write(kayit.toString() + "\n");
        }
    }

    public List<String> readLines() {
        List<String> lines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(dataFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    lines.add(line.trim());

                }
            }
        } catch (IOException e) {
            System.err.println("Dosya okunamadı: " + e.getMessage());
        }

        return lines;
    }
}