package org.example.veri_yonetimi_proje.model;

public class Ogrenci {
    private String isim;
    private String soyad;
    private int ogrNo;
    private float gano;
    private int bolumSira;
    private int sinifSira;
    private int sinif;
    private char cinsiyet;

    public Ogrenci(String isim, String soyad, int ogrNo, float gano,
                   int bolumSira, int sinifSira, int sinif, char cinsiyet) {
        this.isim = isim;
        this.soyad = soyad;
        this.ogrNo = ogrNo;
        this.gano = gano;
        this.bolumSira = bolumSira;
        this.sinifSira = sinifSira;
        this.sinif = sinif;
        this.cinsiyet = cinsiyet;
    }

    public int getOgrNo() { return ogrNo; }
    public String getIsim() { return isim; }
    public String getSoyad() { return soyad; }
    public float getGano() { return gano; }

    public int getBolumSira() { return bolumSira; }
    public int getSinifSira() { return sinifSira; }
    public int getSinif() { return sinif; }
    public char getCinsiyet() { return cinsiyet; }

    @Override
    public String toString() {
        return ogrNo + "," + isim + "," + soyad + "," + gano + "," + bolumSira + "," +
                sinifSira + "," + sinif + "," + cinsiyet;
    }
    public static Ogrenci fromCsv(String line) {
        String[] p = line.split(",");
        if (p.length < 8) return null;
        return new Ogrenci(
                p[0], p[1], Integer.parseInt(p[2]), Float.parseFloat(p[3]),
                Integer.parseInt(p[4]), Integer.parseInt(p[5]), Integer.parseInt(p[6]),
                p[7].charAt(0)
        );
    }

    public void setGano(float min) {
        this.gano = min;
    }
}
