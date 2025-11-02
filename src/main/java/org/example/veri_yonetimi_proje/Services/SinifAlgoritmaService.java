package org.example.veri_yonetimi_proje.Services;

import org.example.veri_yonetimi_proje.hash.LinearProbingHashTable;
import org.example.veri_yonetimi_proje.model.Ogrenci;

public class SinifAlgoritmaService {
    private Ogrenci[] getCompressedArray(LinearProbingHashTable hashTable) {
        Ogrenci[] sourceArray = hashTable.getTable();

        // 1. Dizi Boyutunu Bul
        int actualSize = 0;
        for (Ogrenci o : sourceArray) {
            if (o != null) {
                actualSize++;
            }
        }

        if (actualSize == 0) {
            return new Ogrenci[0];
        }

        // 2. Sıkıştırılmış Diziyi Doldur
        Ogrenci[] compressedArray = new Ogrenci[actualSize];
        int k = 0;
        for (Ogrenci o : sourceArray) {
            if (o != null) {
                compressedArray[k++] = o;
            }
        }
        return compressedArray;
    }

    /**
     * Sıkıştırılmış öğrenci dizisini, sınıflarına (1, 2, 3, 4) göre
     * alt dizilere ayırır ve Ogrenci[][] dizisi olarak döndürür.
     * Dizi index 1: Sınıf 1 öğrencileri, index 2: Sınıf 2 öğrencileri, vb.
     */
    private Ogrenci[][] groupStudentsByClass(Ogrenci[] students) {
        // [0] kullanılmıyor. [1], [2], [3], [4] sınıfları temsil eder.
        Ogrenci[][] groupedArrays = new Ogrenci[5][];

        // a) Sayım (Counting): Her sınıfta kaç öğrenci var?
        int[] counts = new int[5];
        for (Ogrenci o : students) {
            int sinif = o.getSinif();
            // sinif 1, 2, 3 veya 4 olmalı
            if (sinif >= 1 && sinif <= 4) {
                counts[sinif]++;
            }
        }

        // b) Alt Dizileri Başlat (Initialize)
        for (int i = 1; i <= 4; i++) {
            groupedArrays[i] = new Ogrenci[counts[i]];
        }

        // c) Doldurma (Populating): Öğrencileri doğru sınıf dizisine yerleştir
        int[] currentIndices = new int[5]; // Her sınıftaki mevcut indexi tutar
        for (Ogrenci o : students) {
            int sinif = o.getSinif();
            if (sinif >= 1 && sinif <= 4) {
                groupedArrays[sinif][currentIndices[sinif]] = o;
                currentIndices[sinif]++;
            }
        }
        return groupedArrays;
    }

    // =================================================================================
    // PUBLIC SIRALAMA METOTLARI
    // =================================================================================

    public Ogrenci[] Sinif_sira_bubble_sort(LinearProbingHashTable hashTable) {
        Ogrenci[] compressedArray = getCompressedArray(hashTable);
        Ogrenci[][] groupedArrays = groupStudentsByClass(compressedArray);

        // Her sınıf dizisine Bubble Sort uygula
        for (int i = 1; i <= 4; i++) {
            Ogrenci[] classArray = groupedArrays[i];
            if (classArray != null && classArray.length > 0) {
                bubbleSort(classArray);
                // Sıra atama (SinifSira)
                for (int j = 0; j < classArray.length; j++) {
                    classArray[j].setSinifSira(j + 1);
                }
            }
        }
        // Öğrenci nesneleri (referans tip) güncellendiği için sıkıştırılmış diziyi döndür
        return compressedArray;
    }

    public Ogrenci[] Sinif_sira_merge_sort(LinearProbingHashTable hashTable) {
        Ogrenci[] compressedArray = getCompressedArray(hashTable);
        Ogrenci[][] groupedArrays = groupStudentsByClass(compressedArray);

        // Her sınıf dizisine Merge Sort uygula
        for (int i = 1; i <= 4; i++) {
            Ogrenci[] classArray = groupedArrays[i];
            if (classArray != null && classArray.length > 0) {
                mergeSort(classArray, classArray.length);
                // Sıra atama (SinifSira)
                for (int j = 0; j < classArray.length; j++) {
                    classArray[j].setSinifSira(j + 1);
                }
            }
        }
        return compressedArray;
    }

    public Ogrenci[] Sinif_sira_insertion_sort(LinearProbingHashTable hashTable) {
        Ogrenci[] compressedArray = getCompressedArray(hashTable);
        Ogrenci[][] groupedArrays = groupStudentsByClass(compressedArray);

        // Her sınıf dizisine Insertion Sort uygula
        for (int i = 1; i <= 4; i++) {
            Ogrenci[] classArray = groupedArrays[i];
            if (classArray != null && classArray.length > 0) {
                insertionSort(classArray);
                // Sıra atama (SinifSira)
                for (int j = 0; j < classArray.length; j++) {
                    classArray[j].setSinifSira(j + 1);
                }
            }
        }
        return compressedArray;
    }

    public Ogrenci[] Sinif_sira_selection_sort(LinearProbingHashTable hashTable) {
        Ogrenci[] compressedArray = getCompressedArray(hashTable);
        Ogrenci[][] groupedArrays = groupStudentsByClass(compressedArray);

        // Her sınıf dizisine Selection Sort uygula
        for (int i = 1; i <= 4; i++) {
            Ogrenci[] classArray = groupedArrays[i];
            if (classArray != null && classArray.length > 0) {
                selectionSort(classArray);
                // Sıra atama (SinifSira)
                for (int j = 0; j < classArray.length; j++) {
                    classArray[j].setSinifSira(j + 1);
                }
            }
        }
        return compressedArray;
    }

    // =================================================================================
    // PRIVATE SIRALAMA ALGORİTMALARI (GANO'ya göre AZALAN sıralama yapar)
    // =================================================================================

    // GANO'ya göre azalan Bubble Sort
    private void bubbleSort(Ogrenci[] a) {
        int n = a.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (a[j].getGano() > a[j + 1].getGano()) {
                    Ogrenci temp = a[j];
                    a[j] = a[j + 1];
                    a[j + 1] = temp;
                }
            }
        }
    }

    // GANO'ya göre azalan Insertion Sort
    private void insertionSort(Ogrenci[] a) {
        int n = a.length;
        for (int i = 1; i < n; ++i) {
            Ogrenci key = a[i];
            int j = i - 1;

            while (j >= 0 && a[j].getGano() > key.getGano()) { // AZALAN SIRALAMA için <
                a[j + 1] = a[j];
                j = j - 1;
            }
            a[j + 1] = key;
        }
    }

    // GANO'ya göre azalan Selection Sort
    private void selectionSort(Ogrenci[] a) {
        int n = a.length;
        for (int i = 0; i < n - 1; i++) {
            int max_idx = i;
            for (int j = i + 1; j < n; j++) {
                if (a[j].getGano() > a[max_idx].getGano()) { // AZALAN SIRALAMA için >
                    max_idx = j;
                }
            }
            Ogrenci temp = a[max_idx];
            a[max_idx] = a[i];
            a[i] = temp;
        }
    }

    // Merge Sort'un ana recursive metodu (GANO'ya göre azalan)
    private void mergeSort(Ogrenci[] a, int n) {
        if (n < 2) return;
        int mid = n / 2;
        Ogrenci[] l = new Ogrenci[mid];
        Ogrenci[] r = new Ogrenci[n - mid];

        for (int i = 0; i < mid; i++) l[i] = a[i];
        for (int i = mid; i < n; i++) r[i - mid] = a[i];

        mergeSort(l, mid);
        mergeSort(r, n - mid);
        merge(a, l, r, mid, n - mid);
    }

    // Merge Sort'un birleştirme (merge) metodu
    private void merge(Ogrenci[] a, Ogrenci[] l, Ogrenci[] r, int left, int right) {
        int i = 0, j = 0, k = 0;
        while (i < left && j < right) {
            // GANO'ya göre azalan sıralama (Büyük GANO öne gelsin)
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
