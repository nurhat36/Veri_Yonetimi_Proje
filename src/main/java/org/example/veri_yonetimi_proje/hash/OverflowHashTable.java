package org.example.veri_yonetimi_proje.hash;

import org.example.veri_yonetimi_proje.model.Ogrenci;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OverflowHashTable implements HashTable {
    private Ogrenci[] primary;
    private int[] next;
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

        if (overflowPtr >= overflowSize) System.out.println("Overflow doldu nexte eklenecek");
        overflow[overflowPtr] = o;


        if (next[idx] == -1) {
            next[idx] = M + overflowPtr;
        } else {

            int currEnc = next[idx];
            while (true) {
                int realIndex = currEnc - M;

                if (realIndex == overflowPtr) break; // safety

                break;
            }
        }
        overflowPtr++;
    }

    @Override
    public Ogrenci searchByOgrNo(int ogrNo) {
        int idx = hash(ogrNo);
        if (primary[idx] != null && primary[idx].getOgrNo() == ogrNo) return primary[idx];

        for (int i = 0; i < overflowPtr; i++) {
            if (overflow[i] != null && overflow[i].getOgrNo() == ogrNo) return overflow[i];
        }
        return null;
    }
    @Override
    public List<Ogrenci> getAllStudents() {
        List<Ogrenci> list = new ArrayList<>();

        for (Ogrenci o : primary) {
            if (o != null) {
                list.add(o);
            }
        }

        for (Ogrenci o : overflow) {
            if (o != null) {
                list.add(o);
            }
        }
        return list;
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

        if (index < M) {
            String ogrNo = (primary[index] == null) ? "EMPTY" : String.valueOf(primary[index].getOgrNo());
            String nextVal = (next[index] == -1) ? "NONE" : String.valueOf(next[index]);
            return String.format("%s (next=%s)", ogrNo, nextVal);
        }


        int overflowIndex = index - M;
        if (overflowIndex >= 0 && overflowIndex < overflowSize) {
            String ogrNo = (overflow[overflowIndex] == null) ? "EMPTY" : String.valueOf(overflow[overflowIndex].getOgrNo());
            return String.format("%s (overflow idx=%d)", ogrNo, overflowIndex);
        }

        return "INVALID INDEX";
    }


}