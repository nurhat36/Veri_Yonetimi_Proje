package org.example.veri_yonetimi_proje.model;

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

    @Override
    public String toString() {
        // Kayıt formatı: TarihSaat,IslemTipi,AlgoritmaAdi,SureSaniye,VeriBoyutu
        return String.format("%s,%s,%s,%.6f,%d",
                tarihSaat, islemTipi, algoritmaAdi, sureSaniye, veriBoyutu);
    }
}
