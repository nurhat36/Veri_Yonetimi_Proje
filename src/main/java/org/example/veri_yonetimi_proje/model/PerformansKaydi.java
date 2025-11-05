package org.example.veri_yonetimi_proje.model;

import java.util.Locale;

public class PerformansKaydi {
    private final String tarihSaat;
    private final String islemTipi;
    private final String algoritmaAdi;
    private final double sureSaniye;
    private final int veriBoyutu;

    public PerformansKaydi(String tarihSaat, String islemTipi, String algoritmaAdi, double sureSaniye, int veriBoyutu) {
        this.tarihSaat = tarihSaat;
        this.islemTipi = islemTipi;
        this.algoritmaAdi = algoritmaAdi;
        this.sureSaniye = sureSaniye;
        this.veriBoyutu = veriBoyutu;
    }
    public String getTarihSaat() {
        return tarihSaat;
    }
    public String getIslemTipi() {
        return islemTipi;
    }
    public String getAlgoritmaAdi() {
        return algoritmaAdi;
    }
    public double getSureSaniye() {
        return sureSaniye;
    }
    public int getVeriBoyutu() {
        return veriBoyutu;
    }
    @Override
    public String toString() {

        return String.format(Locale.US, "%s,%s,%s,%.6f,%d",
                tarihSaat, islemTipi, algoritmaAdi, sureSaniye, veriBoyutu);
    }
}
