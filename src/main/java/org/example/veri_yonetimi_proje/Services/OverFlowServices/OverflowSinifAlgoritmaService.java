package org.example.veri_yonetimi_proje.Services.OverFlowServices;

import org.example.veri_yonetimi_proje.hash.OverflowHashTable;
import org.example.veri_yonetimi_proje.model.Ogrenci;

public class OverflowSinifAlgoritmaService {

    // =====================================================================
    // 1. Ana tablo + overflow tablodan sıkıştırılmış birleşik dizi oluşturur
    // =====================================================================
    private Ogrenci[] getCompressedArray(OverflowHashTable hashTable) {
        Ogrenci[] mainTable = hashTable.getPrimary();
        Ogrenci[] overflowTable = hashTable.getOverflow();

        int mainCount = 0, overflowCount = 0;
        for (Ogrenci o : mainTable) if (o != null) mainCount++;
        for (Ogrenci o : overflowTable) if (o != null) overflowCount++;

        int total = mainCount + overflowCount;
        if (total == 0) return new Ogrenci[0];

        Ogrenci[] compressed = new Ogrenci[total];
        int idx = 0;
        for (Ogrenci o : mainTable) if (o != null) compressed[idx++] = o;
        for (Ogrenci o : overflowTable) if (o != null) compressed[idx++] = o;

        return compressed;
    }

    // =====================================================================
    // 2. Öğrencileri sınıfa göre gruplar
    // =====================================================================
    private Ogrenci[][] groupStudentsByClass(Ogrenci[] students) {
        Ogrenci[][] groupedArrays = new Ogrenci[5][];
        int[] counts = new int[5];

        for (Ogrenci o : students) {
            int sinif = o.getSinif();
            if (sinif >= 1 && sinif <= 4) counts[sinif]++;
        }

        for (int i = 1; i <= 4; i++) {
            groupedArrays[i] = new Ogrenci[counts[i]];
        }

        int[] currentIndices = new int[5];
        for (Ogrenci o : students) {
            int sinif = o.getSinif();
            if (sinif >= 1 && sinif <= 4) {
                groupedArrays[sinif][currentIndices[sinif]++] = o;
            }
        }

        return groupedArrays;
    }

    // =====================================================================
    // 3. Public sıralama metotları
    // =====================================================================

    public Ogrenci[] Sinif_sira_bubble_sort(OverflowHashTable hashTable) {
        Ogrenci[] compressedArray = getCompressedArray(hashTable);
        Ogrenci[][] groupedArrays = groupStudentsByClass(compressedArray);

        for (int i = 1; i <= 4; i++) {
            Ogrenci[] classArray = groupedArrays[i];
            if (classArray != null && classArray.length > 0) {
                bubbleSort(classArray);
                for (int j = 0; j < classArray.length; j++) {
                    classArray[j].setSinifSira(j + 1);
                }
            }
        }
        return compressedArray;
    }

    public Ogrenci[] Sinif_sira_insertion_sort(OverflowHashTable hashTable) {
        Ogrenci[] compressedArray = getCompressedArray(hashTable);
        Ogrenci[][] groupedArrays = groupStudentsByClass(compressedArray);

        for (int i = 1; i <= 4; i++) {
            Ogrenci[] classArray = groupedArrays[i];
            if (classArray != null && classArray.length > 0) {
                insertionSort(classArray);
                for (int j = 0; j < classArray.length; j++) {
                    classArray[j].setSinifSira(j + 1);
                }
            }
        }
        return compressedArray;
    }

    public Ogrenci[] Sinif_sira_selection_sort(OverflowHashTable hashTable) {
        Ogrenci[] compressedArray = getCompressedArray(hashTable);
        Ogrenci[][] groupedArrays = groupStudentsByClass(compressedArray);

        for (int i = 1; i <= 4; i++) {
            Ogrenci[] classArray = groupedArrays[i];
            if (classArray != null && classArray.length > 0) {
                selectionSort(classArray);
                for (int j = 0; j < classArray.length; j++) {
                    classArray[j].setSinifSira(j + 1);
                }
            }
        }
        return compressedArray;
    }

    public Ogrenci[] Sinif_sira_merge_sort(OverflowHashTable hashTable) {
        Ogrenci[] compressedArray = getCompressedArray(hashTable);
        Ogrenci[][] groupedArrays = groupStudentsByClass(compressedArray);

        for (int i = 1; i <= 4; i++) {
            Ogrenci[] classArray = groupedArrays[i];
            if (classArray != null && classArray.length > 0) {
                mergeSort(classArray, classArray.length);
                for (int j = 0; j < classArray.length; j++) {
                    classArray[j].setSinifSira(j + 1);
                }
            }
        }
        return compressedArray;
    }

    // =====================================================================
    // 4. Private sıralama algoritmaları (GANO’ya göre azalan)
    // =====================================================================

    private void bubbleSort(Ogrenci[] a) {
        int n = a.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (a[j].getGano() < a[j + 1].getGano()) { // büyük GANO öne gelsin
                    Ogrenci temp = a[j];
                    a[j] = a[j + 1];
                    a[j + 1] = temp;
                }
            }
        }
    }

    private void insertionSort(Ogrenci[] a) {
        int n = a.length;
        for (int i = 1; i < n; i++) {
            Ogrenci key = a[i];
            int j = i - 1;
            while (j >= 0 && a[j].getGano() < key.getGano()) {
                a[j + 1] = a[j];
                j--;
            }
            a[j + 1] = key;
        }
    }

    private void selectionSort(Ogrenci[] a) {
        int n = a.length;
        for (int i = 0; i < n - 1; i++) {
            int maxIdx = i;
            for (int j = i + 1; j < n; j++) {
                if (a[j].getGano() > a[maxIdx].getGano()) {
                    maxIdx = j;
                }
            }
            Ogrenci temp = a[maxIdx];
            a[maxIdx] = a[i];
            a[i] = temp;
        }
    }

    private void mergeSort(Ogrenci[] a, int n) {
        if (n < 2) return;
        int mid = n / 2;
        Ogrenci[] left = new Ogrenci[mid];
        Ogrenci[] right = new Ogrenci[n - mid];

        for (int i = 0; i < mid; i++) left[i] = a[i];
        for (int i = mid; i < n; i++) right[i - mid] = a[i];

        mergeSort(left, mid);
        mergeSort(right, n - mid);
        merge(a, left, right, mid, n - mid);
    }

    private void merge(Ogrenci[] a, Ogrenci[] l, Ogrenci[] r, int left, int right) {
        int i = 0, j = 0, k = 0;
        while (i < left && j < right) {
            if (l[i].getGano() >= r[j].getGano()) {
                a[k++] = l[i++];
            } else {
                a[k++] = r[j++];
            }
        }
        while (i < left) a[k++] = l[i++];
        while (j < right) a[k++] = r[j++];
    }
}
