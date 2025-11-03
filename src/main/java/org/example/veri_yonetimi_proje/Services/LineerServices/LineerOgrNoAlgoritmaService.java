package org.example.veri_yonetimi_proje.Services.LineerServices;

import org.example.veri_yonetimi_proje.model.Ogrenci;
import org.example.veri_yonetimi_proje.hash.LinearProbingHashTable;

public class LineerOgrNoAlgoritmaService {
    public Ogrenci[] Ogr_no_quick_sort(LinearProbingHashTable hashTable) {

        Ogrenci[] sourceArray = hashTable.getTable();

        // 1. Hash Table'ın iç dizisindeki dolu (null olmayan) öğrenci sayısını bul.
        int actualSize = 0;
        for (int i = 0; i < sourceArray.length; i++) {
            if (sourceArray[i] != null) {
                actualSize++;
            }
        }

        // Eğer sıralanacak öğrenci yoksa boş bir dizi döndür.
        if (actualSize == 0) {
            return new Ogrenci[0];
        }

        // 2. Sadece dolu öğrencileri içerecek yeni bir dizi oluştur ve doldur (Veri sıkıştırma).
        Ogrenci[] sortedArray = new Ogrenci[actualSize];
        int k = 0;
        for (int i = 0; i < sourceArray.length; i++) {
            if (sourceArray[i] != null) {
                sortedArray[k++] = sourceArray[i];
            }
        }

        // 3. Quick Sort algoritmasıyla GANO'ya göre sırala
        quickSortByGano(sortedArray, 0, sortedArray.length - 1);

        // 4. Bölüm sırasını (1'den n'e kadar) ata
        for (int i = 0; i < sortedArray.length; i++) {
            sortedArray[i].setBolumSira(i + 1);
        }

        return sortedArray;
    }

    // --- Yardımcı Quick Sort metotları ---
    private void quickSortByGano(Ogrenci[] arr, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(arr, low, high);
            quickSortByGano(arr, low, pivotIndex - 1);
            quickSortByGano(arr, pivotIndex + 1, high);
        }
    }

    private int partition(Ogrenci[] arr, int low, int high) {
        double pivot = arr[high].getOgrNo(); // Pivot olarak son elemanın GANO'su
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

        // Pivot'u doğru konuma yerleştir
        Ogrenci temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;

        return i + 1;
    }


    public Ogrenci[] ogr_no_sira_bubble_sort(LinearProbingHashTable hashTable) {

        Ogrenci[] sourceArray = hashTable.getTable();

        // 1. Hash Table'ın iç dizisindeki dolu (null olmayan) öğrenci sayısını bul.
        int actualSize = 0;
        for (int i = 0; i < sourceArray.length; i++) {
            if (sourceArray[i] != null) {
                actualSize++;
            }
        }

        // Eğer sıralanacak öğrenci yoksa boş bir dizi döndür.
        if (actualSize == 0) {
            return new Ogrenci[0];
        }

        // 2. Sadece dolu öğrencileri içerecek yeni bir dizi oluştur ve doldur (Veri sıkıştırma).
        Ogrenci[] sortedArray = new Ogrenci[actualSize];
        int k = 0; // Yeni dizinin indeksi
        for (int i = 0; i < sourceArray.length; i++) {
            if (sourceArray[i] != null) {
                sortedArray[k++] = sourceArray[i];
            }
        }

        // 3. Diziyi Bubble Sort ile öğrenci numarasına göre sırala (Küçükten Büyüğe)
        int n = sortedArray.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {

                // Öğrenci numarasına göre karşılaştırma
                if (sortedArray[j].getOgrNo() > sortedArray[j + 1].getOgrNo()) {

                    // Yer değiştirme (Swap)
                    Ogrenci temp = sortedArray[j];
                    sortedArray[j] = sortedArray[j + 1];
                    sortedArray[j + 1] = temp;
                }
            }
        }

        // 4. Sıralanmış Diziyi döndür.
        return sortedArray;
    }

    public Ogrenci[] ogr_no_sira_merge_sort(LinearProbingHashTable hashTable) {
        Ogrenci[] sourceArray = hashTable.getTable();

        // 1. Veri Sıkıştırma (Bubble Sort ile aynı ilk adımlar)
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

        // 2. Merge Sort Uygulama
        mergeSort(mergedArray, actualSize);

        return mergedArray;
    }

    // Merge Sort'un ana recursive metodu
    private void mergeSort(Ogrenci[] a, int n) {
        if (n < 2) {
            return;
        }
        int mid = n / 2;
        Ogrenci[] l = new Ogrenci[mid];
        Ogrenci[] r = new Ogrenci[n - mid];

        // Sol yarıyı doldur
        for (int i = 0; i < mid; i++) {
            l[i] = a[i];
        }
        // Sağ yarıyı doldur
        for (int i = mid; i < n; i++) {
            r[i - mid] = a[i];
        }

        // Recursive çağrılar
        mergeSort(l, mid);
        mergeSort(r, n - mid);

        // Birleştirme (Merge)
        merge(a, l, r, mid, n - mid);
    }

    // Merge Sort'un birleştirme (merge) metodu
    private void merge(Ogrenci[] a, Ogrenci[] l, Ogrenci[] r, int left, int right) {
        int i = 0, j = 0, k = 0;

        // Sol ve sağ dizilerde eleman olduğu sürece karşılaştır
        while (i < left && j < right) {
            // ogrNo'ya göre küçükten büyüğe sıralama
            if (l[i].getOgrNo() <= r[j].getOgrNo()) {
                a[k++] = l[i++];
            } else {
                a[k++] = r[j++];
            }
        }

        // Kalan sol elemanları ekle
        while (i < left) {
            a[k++] = l[i++];
        }

        // Kalan sağ elemanları ekle
        while (j < right) {
            a[k++] = r[j++];
        }
    }
    public Ogrenci[] ogr_no_sira_insertion_sort(LinearProbingHashTable hashTable) {
        Ogrenci[] sourceArray = hashTable.getTable();

        // 1. Veri Sıkıştırma (Önceki metotlarla aynı)
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

        // 2. Insertion Sort Uygulama
        int n = sortedArray.length;
        for (int i = 1; i < n; ++i) {
            Ogrenci key = sortedArray[i];
            int j = i - 1;

            // Öğrenci numarasına göre küçükten büyüğe sıralama
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

        // 1. Veri Sıkıştırma
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

        // 2. Selection Sort Uygulama
        int n = sortedArray.length;
        for (int i = 0; i < n - 1; i++) {
            // En küçük elemanın indeksini bul
            int min_idx = i;
            for (int j = i + 1; j < n; j++) {
                if (sortedArray[j].getOgrNo() < sortedArray[min_idx].getOgrNo()) {
                    min_idx = j;
                }
            }

            // Bulunan en küçük elemanı mevcut elemanla yer değiştir (Swap)
            Ogrenci temp = sortedArray[min_idx];
            sortedArray[min_idx] = sortedArray[i];
            sortedArray[i] = temp;
        }

        return sortedArray;
    }
}
