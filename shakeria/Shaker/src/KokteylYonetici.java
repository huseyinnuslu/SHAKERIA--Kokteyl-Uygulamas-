import java.util.*;
import java.io.*;

public class KokteylYonetici extends DosyaOkuyucu {
    private List<Kokteyl> onayliKokteyller = new ArrayList<>();
    private List<Kokteyl> onaysizKokteyller = new ArrayList<>();
    private Set<String> secilenMalzemeler = new HashSet<>();

    public KokteylYonetici() {
        dosyadanOku();
    }

    public void dosyadanOku() {
        onayliKokteyller.clear();
        onaysizKokteyller.clear();
        okumaIslemi("src/kokteyller.txt", true);
        okumaIslemi("src/onaybekleyen_kokteyller.txt", false);
    }

    private void okumaIslemi(String dosyaAdi, boolean onayliMi) {
        try (BufferedReader br = new BufferedReader(new FileReader(dosyaAdi))) {
            String satir;
            while ((satir = br.readLine()) != null) {
                String[] parcalar = satir.split(";");
                if (parcalar.length >= 6) {
                    String ad = parcalar[0];
                    String hikaye = parcalar[1];
                    List<String> malzemeler = Arrays.asList(parcalar[2].split(","));
                    String tarif = parcalar[3];
                    String resimYolu = parcalar[4];
                    boolean onay = Boolean.parseBoolean(parcalar[5]);

                    Kokteyl kokteyl = new Kokteyl(ad, hikaye, malzemeler, tarif, resimYolu, onay);

                    if (onayliMi) {
                        onayliKokteyller.add(kokteyl);
                    } else {
                        onaysizKokteyller.add(kokteyl);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setSecilenMalzemeler(Set<String> malzemeler) {
        this.secilenMalzemeler = malzemeler;
    }

    // ✅ EKLENDİ: GUI tarafının erişmesi için
    public Set<String> getSecilenMalzemeler() {
        return secilenMalzemeler;
    }

    public List<Kokteyl> getUygunKokteyller() {
        List<Kokteyl> uygunlar = new ArrayList<>();
        Set<String> normalizedSecilenler = new HashSet<>();
        for (String s : secilenMalzemeler) {
            normalizedSecilenler.add(s.trim().toLowerCase());
        }

        for (Kokteyl k : onayliKokteyller) {
            int mevcutSayisi = 0;
            for (String m : k.getMalzemeler()) {
                if (normalizedSecilenler.contains(m.trim().toLowerCase())) {
                    mevcutSayisi++;
                }
            }

            if (mevcutSayisi > 0) {
                uygunlar.add(k);
            }
        }

        return uygunlar;
    }

    public Kokteyl getKokteylByAd(String ad) {
        for (Kokteyl k : onayliKokteyller) {
            if (k.getAd().equalsIgnoreCase(ad)) {
                return k;
            }
        }
        return null;
    }

    public List<Kokteyl> getOnayliKokteyller() {
        return onayliKokteyller;
    }

    public List<Kokteyl> getOnaysizKokteyller() {
        return onaysizKokteyller;
    }

    public List<Kokteyl> getOnayBekleyenKokteyller() {
        return onaysizKokteyller;
    }

    public void kokteylOnayla(Kokteyl kokteyl) {
        onayliKokteyller.add(kokteyl);
        onaysizKokteyller.remove(kokteyl);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("src/kokteyller.txt", true))) {
            bw.write(kokteyl.getAd() + ";" +
                     kokteyl.getHikaye() + ";" +
                     String.join(",", kokteyl.getMalzemeler()) + ";" +
                     kokteyl.getTarif() + ";" +
                     kokteyl.getResimYolu() + ";" +
                     "true");
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        guncelleOnaysizDosya();
    }

    public void kokteylReddet(Kokteyl kokteyl) {
        onaysizKokteyller.remove(kokteyl);
        guncelleOnaysizDosya();
    }

    private void guncelleOnaysizDosya() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("src/onaybekleyen_kokteyller.txt"))) {
            for (Kokteyl k : onaysizKokteyller) {
                bw.write(k.getAd() + ";" +
                         k.getHikaye() + ";" +
                         String.join(",", k.getMalzemeler()) + ";" +
                         k.getTarif() + ";" +
                         k.getResimYolu() + ";" +
                         "false");
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void kullaniciKokteylEkle(Kokteyl kokteyl) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/onaybekleyen_kokteyller.txt", true))) {
            writer.write(kokteyl.getAd() + ";" +
                         kokteyl.getHikaye() + ";" +
                         String.join(",", kokteyl.getMalzemeler()) + ";" +
                         kokteyl.getTarif() + ";" +
                         kokteyl.getResimYolu() + ";" +
                         "false");
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        onaysizKokteyller.add(kokteyl);
    }

    public void kokteylEkleDosyaya(Kokteyl kokteyl) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/kokteyller.txt", true))) {
            writer.write(kokteyl.getAd() + ";" +
                         kokteyl.getHikaye() + ";" +
                         String.join(",", kokteyl.getMalzemeler()) + ";" +
                         kokteyl.getTarif() + ";" +
                         kokteyl.getResimYolu() + ";" +
                         "true");
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map<Kokteyl, Integer> getKokteyllerUygunlukPuani() {
        Map<Kokteyl, Integer> uygunluk = new LinkedHashMap<>();

        for (Kokteyl k : onayliKokteyller) {
            int toplam = k.getMalzemeler().size();
            int bulunan = 0;

            for (String m : k.getMalzemeler()) {
                if (secilenMalzemeler.contains(m.trim())) {
                    bulunan++;
                }
            }

            if (bulunan > 0) {
                int oran = (int) ((double) bulunan / toplam * 100);
                uygunluk.put(k, oran);
            }
        }

        return uygunluk;
    }
}
