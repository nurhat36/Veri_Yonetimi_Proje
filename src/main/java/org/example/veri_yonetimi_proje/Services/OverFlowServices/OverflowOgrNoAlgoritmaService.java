package org.example.veri_yonetimi_proje.Services.OverFlowServices;



import org.example.veri_yonetimi_proje.hash.OverflowHashTable;
import org.example.veri_yonetimi_proje.model.Ogrenci;

public class OverflowOgrNoAlgoritmaService {


    public Ogrenci[] ogr_no_sira_bubble_sort(OverflowHashTable hashTable) {
        Ogrenci[] allStudents = getAllStudents(hashTable);
        int n = allStudents.length;

        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (allStudents[j].getOgrNo() > allStudents[j + 1].getOgrNo()) {
                    Ogrenci temp = allStudents[j];
                    allStudents[j] = allStudents[j + 1];
                    allStudents[j + 1] = temp;
                }
            }
        }
        return allStudents;
    }


    public Ogrenci[] ogr_no_sira_merge_sort(OverflowHashTable hashTable) {
        Ogrenci[] allStudents = getAllStudents(hashTable);
        mergeSort(allStudents, allStudents.length);
        return allStudents;
    }

    private void mergeSort(Ogrenci[] a, int n) {
        if (n < 2) return;
        int mid = n / 2;
        Ogrenci[] left = new Ogrenci[mid];
        Ogrenci[] right = new Ogrenci[n - mid];

        for (int i = 0; i < mid; i++)
            left[i] = a[i];
        for (int i = mid; i < n; i++)
            right[i - mid] = a[i];

        mergeSort(left, mid);
        mergeSort(right, n - mid);
        merge(a, left, right, mid, n - mid);
    }

    private void merge(Ogrenci[] a, Ogrenci[] left, Ogrenci[] right, int leftSize, int rightSize) {
        int i = 0, j = 0, k = 0;
        while (i < leftSize && j < rightSize) {
            if (left[i].getOgrNo() <= right[j].getOgrNo())
                a[k++] = left[i++];
            else
                a[k++] = right[j++];
        }
        while (i < leftSize)
            a[k++] = left[i++];
        while (j < rightSize)
            a[k++] = right[j++];
    }


    public Ogrenci[] ogr_no_sira_insertion_sort(OverflowHashTable hashTable) {
        Ogrenci[] allStudents = getAllStudents(hashTable);

        for (int i = 1; i < allStudents.length; i++) {
            Ogrenci key = allStudents[i];
            int j = i - 1;
            while (j >= 0 && allStudents[j].getOgrNo() > key.getOgrNo()) {
                allStudents[j + 1] = allStudents[j];
                j--;
            }
            allStudents[j + 1] = key;
        }
        return allStudents;
    }


    public Ogrenci[] ogr_no_sira_selection_sort(OverflowHashTable hashTable) {
        Ogrenci[] allStudents = getAllStudents(hashTable);
        int n = allStudents.length;

        for (int i = 0; i < n - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < n; j++) {
                if (allStudents[j].getOgrNo() < allStudents[minIdx].getOgrNo()) {
                    minIdx = j;
                }
            }
            Ogrenci temp = allStudents[minIdx];
            allStudents[minIdx] = allStudents[i];
            allStudents[i] = temp;
        }

        return allStudents;
    }


    private Ogrenci[] getAllStudents(OverflowHashTable hashTable) {

        Ogrenci[] primary = hashTable.getPrimary();
        Ogrenci[] overflow = hashTable.getOverflow();

        int count = 0;
        for (Ogrenci o : primary)
            if (o != null) count++;
        for (Ogrenci o : overflow)
            if (o != null) count++;

        Ogrenci[] all = new Ogrenci[count];
        int idx = 0;

        for (Ogrenci o : primary)
            if (o != null) all[idx++] = o;
        for (Ogrenci o : overflow)
            if (o != null) all[idx++] = o;

        return all;
    }

    public Ogrenci[] ogr_no_sira_quick_sort(OverflowHashTable hashTable) {
        Ogrenci[] allStudents = getAllStudents(hashTable);
        quickSort(allStudents, 0, allStudents.length - 1);
        return allStudents;
    }

    private void quickSort(Ogrenci[] arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);

            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }

    private int partition(Ogrenci[] arr, int low, int high) {
        int pivot = arr[high].getOgrNo();
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (arr[j].getOgrNo() <= pivot) {
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
