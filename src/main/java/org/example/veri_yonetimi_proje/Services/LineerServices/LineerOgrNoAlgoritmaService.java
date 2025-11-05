package org.example.veri_yonetimi_proje.Services.LineerServices;

import org.example.veri_yonetimi_proje.model.Ogrenci;
import org.example.veri_yonetimi_proje.hash.LinearProbingHashTable;

public class LineerOgrNoAlgoritmaService {
    public Ogrenci[] Ogr_no_quick_sort(LinearProbingHashTable hashTable) {

        Ogrenci[] sourceArray = hashTable.getTable();


        int actualSize = 0;
        for (int i = 0; i < sourceArray.length; i++) {
            if (sourceArray[i] != null) {
                actualSize++;
            }
        }


        if (actualSize == 0) {
            return new Ogrenci[0];
        }


        Ogrenci[] sortedArray = new Ogrenci[actualSize];
        int k = 0;
        for (int i = 0; i < sourceArray.length; i++) {
            if (sourceArray[i] != null) {
                sortedArray[k++] = sourceArray[i];
            }
        }


        quickSortByGano(sortedArray, 0, sortedArray.length - 1);


        for (int i = 0; i < sortedArray.length; i++) {
            sortedArray[i].setBolumSira(i + 1);
        }

        return sortedArray;
    }

    private void quickSortByGano(Ogrenci[] arr, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(arr, low, high);
            quickSortByGano(arr, low, pivotIndex - 1);
            quickSortByGano(arr, pivotIndex + 1, high);
        }
    }

    private int partition(Ogrenci[] arr, int low, int high) {
        double pivot = arr[high].getOgrNo();
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (arr[j].getOgrNo() <= pivot) {
                i++;

                // Swap
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


    public Ogrenci[] ogr_no_sira_bubble_sort(LinearProbingHashTable hashTable) {

        Ogrenci[] sourceArray = hashTable.getTable();


        int actualSize = 0;
        for (int i = 0; i < sourceArray.length; i++) {
            if (sourceArray[i] != null) {
                actualSize++;
            }
        }


        if (actualSize == 0) {
            return new Ogrenci[0];
        }


        Ogrenci[] sortedArray = new Ogrenci[actualSize];
        int k = 0;
        for (int i = 0; i < sourceArray.length; i++) {
            if (sourceArray[i] != null) {
                sortedArray[k++] = sourceArray[i];
            }
        }


        int n = sortedArray.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {


                if (sortedArray[j].getOgrNo() > sortedArray[j + 1].getOgrNo()) {


                    Ogrenci temp = sortedArray[j];
                    sortedArray[j] = sortedArray[j + 1];
                    sortedArray[j + 1] = temp;
                }
            }
        }


        return sortedArray;
    }

    public Ogrenci[] ogr_no_sira_merge_sort(LinearProbingHashTable hashTable) {
        Ogrenci[] sourceArray = hashTable.getTable();


        int actualSize = 0;
        for (Ogrenci o : sourceArray) {
            if (o != null) {
                actualSize++;
            }
        }

        if (actualSize == 0) {
            return new Ogrenci[0];
        }

        Ogrenci[] mergedArray = new Ogrenci[actualSize];
        int k = 0;
        for (Ogrenci o : sourceArray) {
            if (o != null) {
                mergedArray[k++] = o;
            }
        }

        mergeSort(mergedArray, actualSize);

        return mergedArray;
    }


    private void mergeSort(Ogrenci[] a, int n) {
        if (n < 2) {
            return;
        }
        int mid = n / 2;
        Ogrenci[] l = new Ogrenci[mid];
        Ogrenci[] r = new Ogrenci[n - mid];


        for (int i = 0; i < mid; i++) {
            l[i] = a[i];
        }

        for (int i = mid; i < n; i++) {
            r[i - mid] = a[i];
        }


        mergeSort(l, mid);
        mergeSort(r, n - mid);


        merge(a, l, r, mid, n - mid);
    }


    private void merge(Ogrenci[] a, Ogrenci[] l, Ogrenci[] r, int left, int right) {
        int i = 0, j = 0, k = 0;


        while (i < left && j < right) {

            if (l[i].getOgrNo() <= r[j].getOgrNo()) {
                a[k++] = l[i++];
            } else {
                a[k++] = r[j++];
            }
        }


        while (i < left) {
            a[k++] = l[i++];
        }


        while (j < right) {
            a[k++] = r[j++];
        }
    }
    public Ogrenci[] ogr_no_sira_insertion_sort(LinearProbingHashTable hashTable) {
        Ogrenci[] sourceArray = hashTable.getTable();


        int actualSize = 0;
        for (Ogrenci o : sourceArray) {
            if (o != null) {
                actualSize++;
            }
        }

        if (actualSize == 0) {
            return new Ogrenci[0];
        }

        Ogrenci[] sortedArray = new Ogrenci[actualSize];
        int k = 0;
        for (Ogrenci o : sourceArray) {
            if (o != null) {
                sortedArray[k++] = o;
            }
        }


        int n = sortedArray.length;
        for (int i = 1; i < n; ++i) {
            Ogrenci key = sortedArray[i];
            int j = i - 1;


            while (j >= 0 && sortedArray[j].getOgrNo() > key.getOgrNo()) {
                sortedArray[j + 1] = sortedArray[j];
                j = j - 1;
            }
            sortedArray[j + 1] = key;
        }

        return sortedArray;
    }
    public Ogrenci[] ogr_no_sira_selection_sort(LinearProbingHashTable hashTable) {
        Ogrenci[] sourceArray = hashTable.getTable();


        int actualSize = 0;
        for (Ogrenci o : sourceArray) {
            if (o != null) {
                actualSize++;
            }
        }

        if (actualSize == 0) {
            return new Ogrenci[0];
        }

        Ogrenci[] sortedArray = new Ogrenci[actualSize];
        int k = 0;
        for (Ogrenci o : sourceArray) {
            if (o != null) {
                sortedArray[k++] = o;
            }
        }


        int n = sortedArray.length;
        for (int i = 0; i < n - 1; i++) {

            int min_idx = i;
            for (int j = i + 1; j < n; j++) {
                if (sortedArray[j].getOgrNo() < sortedArray[min_idx].getOgrNo()) {
                    min_idx = j;
                }
            }


            Ogrenci temp = sortedArray[min_idx];
            sortedArray[min_idx] = sortedArray[i];
            sortedArray[i] = temp;
        }

        return sortedArray;
    }
}
