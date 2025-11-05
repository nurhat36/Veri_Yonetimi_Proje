package org.example.veri_yonetimi_proje.Services.LineerServices;

import org.example.veri_yonetimi_proje.hash.LinearProbingHashTable;
import org.example.veri_yonetimi_proje.model.Ogrenci;

public class LineerSinifAlgoritmaService {



    private Ogrenci[] getCompressedArray(LinearProbingHashTable hashTable) {
        Ogrenci[] sourceArray = hashTable.getTable();

        int actualSize = 0;
        for (Ogrenci o : sourceArray) {
            if (o != null) actualSize++;
        }

        Ogrenci[] compressedArray = new Ogrenci[actualSize];
        int k = 0;
        for (Ogrenci o : sourceArray) {
            if (o != null) compressedArray[k++] = o;
        }
        return compressedArray;
    }

    private Ogrenci[][] groupStudentsByClass(Ogrenci[] students) {
        Ogrenci[][] grouped = new Ogrenci[5][];
        int[] counts = new int[5];


        for (Ogrenci o : students) {
            int s = o.getSinif();
            if (s >= 1 && s <= 4) counts[s]++;
        }


        for (int i = 1; i <= 4; i++)
            grouped[i] = new Ogrenci[counts[i]];


        int[] idx = new int[5];
        for (Ogrenci o : students) {
            int s = o.getSinif();
            if (s >= 1 && s <= 4)
                grouped[s][idx[s]++] = o;
        }

        return grouped;
    }



    public Ogrenci[] Sinif_sira_bubble_sort(LinearProbingHashTable hashTable) {
        return sortAndRank(hashTable, "bubble");
    }

    public Ogrenci[] Sinif_sira_insertion_sort(LinearProbingHashTable hashTable) {
        return sortAndRank(hashTable, "insertion");
    }

    public Ogrenci[] Sinif_sira_selection_sort(LinearProbingHashTable hashTable) {
        return sortAndRank(hashTable, "selection");
    }

    public Ogrenci[] Sinif_sira_merge_sort(LinearProbingHashTable hashTable) {
        return sortAndRank(hashTable, "merge");
    }

    public Ogrenci[] Sinif_sira_quick_sort(LinearProbingHashTable hashTable) {
        return sortAndRank(hashTable, "quick");
    }


    private Ogrenci[] sortAndRank(LinearProbingHashTable hashTable, String algorithm) {
        Ogrenci[] compressedArray = getCompressedArray(hashTable);
        Ogrenci[][] grouped = groupStudentsByClass(compressedArray);

        for (int i = 1; i <= 4; i++) {
            Ogrenci[] arr = grouped[i];
            if (arr == null || arr.length == 0) continue;

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
        return compressedArray;
    }

    private void bubbleSort(Ogrenci[] a) {
        int n = a.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (a[j].getGano() < a[j + 1].getGano()) {
                    swap(a, j, j + 1);
                }
            }
        }
    }

    private void insertionSort(Ogrenci[] a) {
        int n = a.length;
        for (int i = 1; i < n; ++i) {
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
            int max_idx = i;
            for (int j = i + 1; j < n; j++) {
                if (a[j].getGano() > a[max_idx].getGano()) {
                    max_idx = j;
                }
            }
            swap(a, i, max_idx);
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
        merge(a, left, right);
    }

    private void merge(Ogrenci[] a, Ogrenci[] l, Ogrenci[] r) {
        int i = 0, j = 0, k = 0;
        while (i < l.length && j < r.length) {
            if (l[i].getGano() >= r[j].getGano()) {
                a[k++] = l[i++];
            } else {
                a[k++] = r[j++];
            }
        }
        while (i < l.length) a[k++] = l[i++];
        while (j < r.length) a[k++] = r[j++];
    }

    private void quickSort(Ogrenci[] a, int low, int high) {
        if (low < high) {
            int pi = partition(a, low, high);
            quickSort(a, low, pi - 1);
            quickSort(a, pi + 1, high);
        }
    }

    private int partition(Ogrenci[] a, int low, int high) {
        double pivot = a[high].getGano();
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (a[j].getGano() >= pivot) {
                i++;
                swap(a, i, j);
            }
        }
        swap(a, i + 1, high);
        return i + 1;
    }

    private void swap(Ogrenci[] a, int i, int j) {
        Ogrenci temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
}
