import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            KokteylYonetici yonetici = new KokteylYonetici();
            yonetici.dosyadanOku();
            new AnaGiris(yonetici);
        });
    }
}
