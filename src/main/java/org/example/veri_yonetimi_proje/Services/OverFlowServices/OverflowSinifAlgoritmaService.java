package org.example.veri_yonetimi_proje.Services.OverFlowServices;

import org.example.veri_yonetimi_proje.hash.OverflowHashTable;
import org.example.veri_yonetimi_proje.model.Ogrenci;

public class OverflowSinifAlgoritmaService {


    private Ogrenci[] getCompressedArray(OverflowHashTable hashTable) {
        Ogrenci[] mainTable = hashTable.getPrimary();
        Ogrenci[] overflowTable = hashTable.getOverflow();

        int total = 0;
        for (Ogrenci o : mainTable) if (o != null) total++;
        for (Ogrenci o : overflowTable) if (o != null) total++;

        if (total == 0) return new Ogrenci[0];

        Ogrenci[] compressed = new Ogrenci[total];
        int index = 0;
        for (Ogrenci o : mainTable) if (o != null) compressed[index++] = o;
        for (Ogrenci o : overflowTable) if (o != null) compressed[index++] = o;

        return compressed;
    }


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


        int[] idx = new int[5];
        for (Ogrenci o : students) {
            int sinif = o.getSinif();
            if (sinif >= 1 && sinif <= 4) {
                groupedArrays[sinif][idx[sinif]++] = o;
            }
        }

        return groupedArrays;
    }


    private void sortEachClass(Ogrenci[][] groupedArrays, String algorithm) {
        for (int i = 1; i <= 4; i++) {
            Ogrenci[] arr = groupedArrays[i];
            if (arr != null && arr.length > 0) {
                switch (algorithm) {
                    case "bubble" -> bubbleSort(arr);
                    case "insertion" -> insertionSort(arr);
                    case "selection" -> selectionSort(arr);
                    case "merge" -> mergeSort(arr, arr.length);
                    case "quick" -> quickSort(arr, 0, arr.length - 1);
                }


                for (int j = 0; j < arr.length; j++) {
                    arr[j].setSinifSira(j + 1);
                }
            }
        }
    }


    public Ogrenci[] Sinif_sira_bubble_sort(OverflowHashTable hashTable) {
        return sortByAlgorithm(hashTable, "bubble");
    }

    public Ogrenci[] Sinif_sira_insertion_sort(OverflowHashTable hashTable) {
        return sortByAlgorithm(hashTable, "insertion");
    }

    public Ogrenci[] Sinif_sira_selection_sort(OverflowHashTable hashTable) {
        return sortByAlgorithm(hashTable, "selection");
    }

    public Ogrenci[] Sinif_sira_merge_sort(OverflowHashTable hashTable) {
        return sortByAlgorithm(hashTable, "merge");
    }

    public Ogrenci[] Sinif_sira_quick_sort(OverflowHashTable hashTable) {
        return sortByAlgorithm(hashTable, "quick");
    }


    private Ogrenci[] sortByAlgorithm(OverflowHashTable hashTable, String algorithm) {
        Ogrenci[] compressed = getCompressedArray(hashTable);
        Ogrenci[][] grouped = groupStudentsByClass(compressed);
        sortEachClass(grouped, algorithm);
        return compressed;
    }


    private void bubbleSort(Ogrenci[] a) {
        int n = a.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (a[j].getGano() < a[j + 1].getGano()) { // büyük GANO öne
                    Ogrenci tmp = a[j];
                    a[j] = a[j + 1];
                    a[j + 1] = tmp;
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

        System.arraycopy(a, 0, left, 0, mid);
        System.arraycopy(a, mid, right, 0, n - mid);

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

    private void quickSort(Ogrenci[] arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);
            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }

    private int partition(Ogrenci[] arr, int low, int high) {
        double pivot = arr[high].getGano();
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (arr[j].getGano() >= pivot) {
                i++;
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
