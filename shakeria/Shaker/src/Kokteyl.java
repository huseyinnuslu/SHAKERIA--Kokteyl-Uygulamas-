import java.util.List;
import java.util.ArrayList;

public class Kokteyl {
    private String ad;
    private String hikaye;
    private List<String> malzemeler;
    private String tarif;
    private boolean onayliMi;
    private String resimYolu; // ✅ yeni eklendi

    // ✅ 6 parametreli yapıcı: dosyadan tam veriyle yükleme
 // Ana constructor
    public Kokteyl(String ad, String hikaye, List<String> malzemeler, String tarif, String resimYolu, boolean onayliMi) {
        this.ad = ad;
        this.hikaye = hikaye;
        this.malzemeler = malzemeler;
        this.tarif = tarif;
        this.resimYolu = resimYolu;
        this.onayliMi = onayliMi;
    }

    // Kullanıcıdan gelen (resimli)
    public Kokteyl(String ad, String hikaye, List<String> malzemeler, String tarif, String resimYolu) {
        this(ad, hikaye, malzemeler, tarif, resimYolu, false);
    }

    // Admin'den gelen (resimsiz)
    public Kokteyl(String ad, String hikaye, List<String> malzemeler, String tarif, boolean onayliMi) {
        this(ad, hikaye, malzemeler, tarif, "", onayliMi);
    }

    // Eski sistem uyumu (resimsiz & onaysız)
    public Kokteyl(String ad, String hikaye, List<String> malzemeler, String tarif) {
        this(ad, hikaye, malzemeler, tarif, "", false);
    }

    // Malzeme nesnesi listesiyle
    public Kokteyl(String ad, List<Malzeme> malzemeListesi) {
        this.ad = ad;
        this.malzemeler = new ArrayList<>();
        for (Malzeme m : malzemeListesi) {
            this.malzemeler.add(m.getAd());
        }
        this.hikaye = "";
        this.tarif = "";
        this.resimYolu = "";
        this.onayliMi = false;
    }


    // Getter - Setter
    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public String getHikaye() {
        return hikaye;
    }

    public void setHikaye(String hikaye) {
        this.hikaye = hikaye;
    }

    public List<String> getMalzemeler() {
        return malzemeler;
    }

    public void setMalzemeler(List<String> malzemeler) {
        this.malzemeler = malzemeler;
    }

    public String getTarif() {
        return tarif;
    }

    public void setTarif(String tarif) {
        this.tarif = tarif;
    }

    public boolean isOnayliMi() {
        return onayliMi;
    }

    public void setOnayliMi(boolean onayliMi) {
        this.onayliMi = onayliMi;
    }

    public String getResimYolu() {
        return resimYolu;
    }

    public void setResimYolu(String resimYolu) {
        this.resimYolu = resimYolu;
    }

    @Override
    public String toString() {
        return ad;
    }
}
