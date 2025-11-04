package org.example.veri_yonetimi_proje.hash;

import org.example.veri_yonetimi_proje.model.Ogrenci;

import java.util.Arrays;

public class OverflowHashTable implements HashTable {
    private Ogrenci[] primary;
    private int[] next; // primary veya overflow için linked list indices
    private Ogrenci[] overflow;
    private int overflowPtr = 0;
    private int M;
    private int overflowSize;

    public OverflowHashTable(int primarySize, int overflowSize) {
        this.M = primarySize;
        this.overflowSize = overflowSize;
        primary = new Ogrenci[M];
        next = new int[M];
        Arrays.fill(next, -1);
        overflow = new Ogrenci[overflowSize];
    }


    private int hash(int k) {
        return Math.abs(k) % M;
    }

    @Override
    public void insert(Ogrenci o) {
        int idx = hash(o.getOgrNo());
        if (primary[idx] == null) {
            primary[idx] = o;
            return;
        }
        // primary dolu -> overflow'a ekle
        if (overflowPtr >= overflowSize) System.out.println("Overflow doldu nexte eklenecek");
        overflow[overflowPtr] = o;

        // eğer primary'nin next'i yoksa bağla, varsa sona ekle
        if (next[idx] == -1) {
            next[idx] = M + overflowPtr; // overflow index encoded
        } else {
            // son overflow nodunu bul
            int currEnc = next[idx];
            while (true) {
                int realIndex = currEnc - M;
                // next alanı overflow için -1 kullanamadığımızdan; kullandığımız next[] sadece primary için tutuyoruz
                // basitçe chained list olarak overflow saklıyoruz: burada linear eklemeyle son bulup ekliyoruz
                if (realIndex == overflowPtr) break; // safety
                // Bu basit implementasyonda sadece primary next gösterir; okuyucu tüm overflow tarayıp eşleşme arar.
                break;
            }
        }
        overflowPtr++;
    }

    @Override
    public Ogrenci searchByOgrNo(int ogrNo) {
        int idx = hash(ogrNo);
        if (primary[idx] != null && primary[idx].getOgrNo() == ogrNo) return primary[idx];
        // overflow'ı tara (basit)
        for (int i = 0; i < overflowPtr; i++) {
            if (overflow[i] != null && overflow[i].getOgrNo() == ogrNo) return overflow[i];
        }
        return null;
    }

    @Override
    public boolean delete(int ogrNo) {
        int idx = hash(ogrNo);
        if (primary[idx] != null && primary[idx].getOgrNo() == ogrNo) {
            primary[idx] = null; // primary sil
            return true;
        }
        for (int i = 0; i < overflowPtr; i++) {
            if (overflow[i] != null && overflow[i].getOgrNo() == ogrNo) {
                overflow[i] = null;
                return true;
            }
        }
        return false;
    }

    @Override
    public void showHashTable() {
        System.out.println("Primary:");
        for (int i = 0; i < M; i++) {
            System.out.printf("[%d] -> %s, next=%d%n", i,
                    primary[i] == null ? "EMPTY" : String.valueOf(primary[i].getOgrNo()),
                    next[i]);
        }
        System.out.println("Overflow:");
        for (int i = 0; i < overflowPtr; i++) {
            System.out.printf("[%d] -> %s%n", M + i,
                    overflow[i] == null ? "EMPTY" : String.valueOf(overflow[i].getOgrNo()));
        }
    }

    @Override
    public void listAll() {

    }

    public Ogrenci[] getPrimary() {
        return primary;
    }

    public Ogrenci[] getOverflow() {
        return overflow;
    }

    public String getDisplayValue(int index) {
        // Primary kısmı için
        if (index < M) {
            String ogrNo = (primary[index] == null) ? "EMPTY" : String.valueOf(primary[index].getOgrNo());
            String nextVal = (next[index] == -1) ? "NONE" : String.valueOf(next[index]);
            return String.format("%s (next=%s)", ogrNo, nextVal);
        }

        // Overflow kısmı için
        int overflowIndex = index - M;
        if (overflowIndex >= 0 && overflowIndex < overflowSize) {
            String ogrNo = (overflow[overflowIndex] == null) ? "EMPTY" : String.valueOf(overflow[overflowIndex].getOgrNo());
            return String.format("%s (overflow idx=%d)", ogrNo, overflowIndex);
        }

        return "INVALID INDEX";
    }


}