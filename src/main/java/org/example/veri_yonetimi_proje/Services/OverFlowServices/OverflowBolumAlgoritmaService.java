package org.example.veri_yonetimi_proje.Services.OverFlowServices;

import org.example.veri_yonetimi_proje.hash.OverflowHashTable;
import org.example.veri_yonetimi_proje.model.Ogrenci;

public class OverflowBolumAlgoritmaService {

    /**
     * Overflow Hash Table'daki tüm öğrencileri bir diziye sıkıştırır.
     * (Hem primary hem overflow dizilerini birleştirir)
     */
    private Ogrenci[] getAllOgrenciler(OverflowHashTable hashTable) {
        Ogrenci[] primary = hashTable.getPrimary();
        Ogrenci[] overflow = hashTable.getOverflow();

        // Geçerli (null olmayan) kayıtları say
        int count = 0;
        for (int i = 0; i < primary.length; i++) {
            if (primary[i] != null) count++;
        }
        for (int i = 0; i < overflow.length; i++) {
            if (overflow[i] != null) count++;
        }

        // Hepsini birleştir
        Ogrenci[] all = new Ogrenci[count];
        int k = 0;
        for (int i = 0; i < primary.length; i++) {
            if (primary[i] != null) all[k++] = primary[i];
        }
        for (int i = 0; i < overflow.length; i++) {
            if (overflow[i] != null) all[k++] = overflow[i];
        }

        return all;
    }

    // ===================== BUBBLE SORT =======================
    public Ogrenci[] Bolum_sira_bubble_sort(OverflowHashTable hashTable) {
        Ogrenci[] ogrenciler = getAllOgrenciler(hashTable);

        for (int i = 0; i < ogrenciler.length - 1; i++) {
            for (int j = 0; j < ogrenciler.length - i - 1; j++) {
                if (ogrenciler[j].getGano() > ogrenciler[j + 1].getGano()) {
                    Ogrenci temp = ogrenciler[j];
                    ogrenciler[j] = ogrenciler[j + 1];
                    ogrenciler[j + 1] = temp;
                }
            }
        }

        for (int i = 0; i < ogrenciler.length; i++) {
            ogrenciler[i].setBolumSira(i + 1);
        }

        return ogrenciler;
    }

    // ===================== SELECTION SORT =======================
    public Ogrenci[] Bolum_sira_selection_sort(OverflowHashTable hashTable) {
        Ogrenci[] ogrenciler = getAllOgrenciler(hashTable);
        int n = ogrenciler.length;

        for (int i = 0; i < n - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < n; j++) {
                if (ogrenciler[j].getGano() < ogrenciler[minIdx].getGano()) {
                    minIdx = j;
                }
            }
            Ogrenci temp = ogrenciler[minIdx];
            ogrenciler[minIdx] = ogrenciler[i];
            ogrenciler[i] = temp;
        }

        for (int i = 0; i < ogrenciler.length; i++) {
            ogrenciler[i].setBolumSira(i + 1);
        }

        return ogrenciler;
    }

    // ===================== INSERTION SORT =======================
    public Ogrenci[] ogr_no_sira_insertion_sort(OverflowHashTable hashTable) {
        Ogrenci[] ogrenciler = getAllOgrenciler(hashTable);
        int n = ogrenciler.length;

        for (int i = 1; i < n; i++) {
            Ogrenci key = ogrenciler[i];
            int j = i - 1;

            while (j >= 0 && ogrenciler[j].getGano() > key.getGano()) {
                ogrenciler[j + 1] = ogrenciler[j];
                j--;
            }
            ogrenciler[j + 1] = key;
        }

        for (int i = 0; i < ogrenciler.length; i++) {
            ogrenciler[i].setBolumSira(i + 1);
        }

        return ogrenciler;
    }

    // ===================== MERGE SORT =======================
    public Ogrenci[] Bolum_sira_merge_sort(OverflowHashTable hashTable) {
        Ogrenci[] ogrenciler = getAllOgrenciler(hashTable);
        mergeSort(ogrenciler, 0, ogrenciler.length - 1);

        for (int i = 0; i < ogrenciler.length; i++) {
            ogrenciler[i].setBolumSira(i + 1);
        }

        return ogrenciler;
    }

    private void mergeSort(Ogrenci[] arr, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSort(arr, left, mid);
            mergeSort(arr, mid + 1, right);
            merge(arr, left, mid, right);
        }
    }

    private void merge(Ogrenci[] arr, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        Ogrenci[] L = new Ogrenci[n1];
        Ogrenci[] R = new Ogrenci[n2];

        for (int i = 0; i < n1; ++i) L[i] = arr[left + i];
        for (int j = 0; j < n2; ++j) R[j] = arr[mid + 1 + j];

        int i = 0, j = 0, k = left;

        while (i < n1 && j < n2) {
            if (L[i].getGano() <= R[j].getGano()) {
                arr[k++] = L[i++];
            } else {
                arr[k++] = R[j++];
            }
        }

        while (i < n1) arr[k++] = L[i++];
        while (j < n2) arr[k++] = R[j++];
    }
    // ===================== QUICK SORT =======================
    public Ogrenci[] Bolum_sira_quick_sort(OverflowHashTable hashTable) {
        Ogrenci[] ogrenciler = getAllOgrenciler(hashTable);
        quickSort(ogrenciler, 0, ogrenciler.length - 1);

        for (int i = 0; i < ogrenciler.length; i++) {
            ogrenciler[i].setBolumSira(i + 1);
        }

        return ogrenciler;
    }

    private void quickSort(Ogrenci[] arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);

            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }

    private int partition(Ogrenci[] arr, int low, int high) {
        double pivot = arr[high].getGano();
        int i = (low - 1);

        for (int j = low; j < high; j++) {
            if (arr[j].getGano() <= pivot) {
                i++;

                // swap
                Ogrenci temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }

        Ogrenci temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;

        return i + 1;
    }

}
