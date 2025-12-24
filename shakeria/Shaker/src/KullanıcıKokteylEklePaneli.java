import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class KullanıcıKokteylEklePaneli extends JFrame {
    private KokteylYonetici kokteylYonetici;
    private String secilenResimYolu = "";

    public KullanıcıKokteylEklePaneli(KokteylYonetici kokteylYonetici) {
        this.kokteylYonetici = kokteylYonetici;

        setTitle("Kokteyl Ekle");
        setSize(450, 370);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setResizable(false);

        // Renk ve fontlar
        Color arkaPlan = new Color(245, 245, 245);
        Font genelFont = new Font("SansSerif", Font.PLAIN, 14);
        Font butonFont = new Font("SansSerif", Font.BOLD, 16);

        // ÜST panel: Geri butonu
        JButton geriButon = createStyledButton("← Geri", butonFont);
        geriButon.setPreferredSize(new Dimension(100, 40));
        geriButon.addActionListener(e -> {
            dispose();
            new KullanıcıPaneli(kokteylYonetici);
        });


        JPanel ustPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ustPanel.setBackground(arkaPlan);
        ustPanel.add(geriButon);
        add(ustPanel, BorderLayout.NORTH);

        // MERKEZ: Form
        JPanel panel = new JPanel(new GridLayout(7, 2, 10, 10));
        panel.setBackground(arkaPlan);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));

        JLabel adLabel = new JLabel("Kokteyl Adı:");
        JTextField adField = new JTextField();
        JLabel hikayeLabel = new JLabel("Hikaye:");
        JTextField hikayeField = new JTextField();
        JLabel malzemeLabel = new JLabel("Malzemeler (virgülle ayırınız):");
        JTextField malzemeField = new JTextField();
        JLabel tarifLabel = new JLabel("Tarif:");
        JTextField tarifField = new JTextField();

        JButton resimSecButton = createStyledButton("Resim Seç", butonFont);
        JLabel resimYoluLabel = new JLabel("Hiçbir resim seçilmedi");

        JButton ekleButton = createStyledButton("Kokteyl Ekle", butonFont);

        panel.add(adLabel); panel.add(adField);
        panel.add(hikayeLabel); panel.add(hikayeField);
        panel.add(malzemeLabel); panel.add(malzemeField);
        panel.add(tarifLabel); panel.add(tarifField);
        panel.add(resimSecButton); panel.add(resimYoluLabel);
        panel.add(new JLabel()); panel.add(ekleButton);

        add(panel, BorderLayout.CENTER);
        getContentPane().setBackground(arkaPlan);

        // Resim seçme işlemi
        resimSecButton.addActionListener(e -> {
            JFileChooser dosyaSecici = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Resimler (*.jpg, *.png)", "jpg", "jpeg", "png");
            dosyaSecici.setFileFilter(filter);

            int sonuc = dosyaSecici.showOpenDialog(null);
            if (sonuc == JFileChooser.APPROVE_OPTION) {
                File secilenDosya = dosyaSecici.getSelectedFile();
                try {
                    String hedefKlasor = "src/resimler";
                    new File(hedefKlasor).mkdirs();

                    String yeniDosyaAdi = adField.getText().trim().replaceAll("\\s+", "_") + "_" + secilenDosya.getName();
                    Path hedefYol = Paths.get(hedefKlasor, yeniDosyaAdi);
                    Files.copy(secilenDosya.toPath(), hedefYol, StandardCopyOption.REPLACE_EXISTING);

                    secilenResimYolu = hedefYol.toString().replace("\\", "/");
                    resimYoluLabel.setText("✓ Seçildi: " + yeniDosyaAdi);
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Resim kopyalanırken hata oluştu.");
                }
            }
        });

        // Ekleme butonu işlevi
        ekleButton.addActionListener(e -> {
            String ad = adField.getText().trim();
            String hikaye = hikayeField.getText().trim();
            String tarif = tarifField.getText().trim();

            String[] malzemelerArray = malzemeField.getText().split(",");
            List<String> malzemeList = new ArrayList<>();
            for (String m : malzemelerArray) {
                malzemeList.add(m.trim());
            }

            if (ad.isEmpty() || hikaye.isEmpty() || malzemeList.isEmpty() || tarif.isEmpty() || secilenResimYolu.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Lütfen tüm alanları ve resmi doldurun.");
                return;
            }

            Kokteyl yeniKokteyl = new Kokteyl(ad, hikaye, malzemeList, tarif, secilenResimYolu);
            kokteylYonetici.kullaniciKokteylEkle(yeniKokteyl);
            kokteylYonetici.dosyadanOku();

            JOptionPane.showMessageDialog(this, "Kokteyl başarıyla eklendi, onay bekliyor.");
            dispose();
            new KullanıcıPaneli(kokteylYonetici);

        });

        setVisible(true);
    }

    // 🔷 Siyah arka planlı, beyaz yazılı buton (proje stiline uygun)
    private JButton createStyledButton(String text, Font font) {
        JButton button = new JButton(text);
        button.setFont(font);
        button.setBackground(Color.BLACK);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(280, 45));
        button.setMaximumSize(new Dimension(280, 45));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        return button;
    }
}
