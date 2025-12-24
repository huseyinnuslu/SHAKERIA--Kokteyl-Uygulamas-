import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.*;

public class AdminKokteylEklePanel extends JFrame {
    private KokteylYonetici kokteylYonetici;
    private String secilenResimYolu = "";

    public AdminKokteylEklePanel(KokteylYonetici kokteylYonetici) {
        this.kokteylYonetici = kokteylYonetici;

        setTitle("Kokteyl Ekle");
        setSize(450, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);

        // Renk ve fontlar
        Color arkaPlan = new Color(245, 245, 245);
        Font genelFont = new Font("SansSerif", Font.PLAIN, 14);
        Font butonFont = new Font("SansSerif", Font.BOLD, 16);

        // Geri butonu
        JButton geriButon = createStyledButton("← Geri", butonFont);
        geriButon.setPreferredSize(new Dimension(100, 40));
        geriButon.addActionListener(e -> {
            dispose();
            new AdminPanel(kokteylYonetici);
        });
        JPanel ustPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ustPanel.setBackground(arkaPlan);
        ustPanel.add(geriButon);
        add(ustPanel, BorderLayout.NORTH);

        // Form paneli
        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        panel.setBackground(arkaPlan);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));

        JLabel lblAd = new JLabel("Kokteyl Adı:");
        JTextField txtAd = new JTextField();

        JLabel lblHikaye = new JLabel("Hikayesi:");
        JTextField txtHikaye = new JTextField();

        JLabel lblMalzeme = new JLabel("Malzemeler (virgülle):");
        JTextField txtMalzeme = new JTextField();

        JLabel lblTarif = new JLabel("Tarif:");
        JTextField txtTarif = new JTextField();

        JButton btnResimSec = createStyledButton("Resim Seç", butonFont);
        JLabel lblResim = new JLabel("Hiçbir resim seçilmedi");

        JButton btnKaydet = createStyledButton("Kaydet", butonFont);

        panel.add(lblAd); panel.add(txtAd);
        panel.add(lblHikaye); panel.add(txtHikaye);
        panel.add(lblMalzeme); panel.add(txtMalzeme);
        panel.add(lblTarif); panel.add(txtTarif);
        panel.add(btnResimSec); panel.add(lblResim);
        panel.add(new JLabel()); panel.add(btnKaydet);

        add(panel, BorderLayout.CENTER);
        getContentPane().setBackground(arkaPlan);

        // Resim seçme işlemi
        btnResimSec.addActionListener(e -> {
            JFileChooser dosyaSecici = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Resimler (*.jpg, *.png)", "jpg", "jpeg", "png");
            dosyaSecici.setFileFilter(filter);

            int sonuc = dosyaSecici.showOpenDialog(null);
            if (sonuc == JFileChooser.APPROVE_OPTION) {
                File secilenDosya = dosyaSecici.getSelectedFile();
                try {
                    String hedefKlasor = "src/resimler";
                    new File(hedefKlasor).mkdirs();

                    String yeniDosyaAdi = txtAd.getText().trim().replaceAll("\\s+", "_") + "_" + secilenDosya.getName();
                    Path hedefYol = Paths.get(hedefKlasor, yeniDosyaAdi);
                    Files.copy(secilenDosya.toPath(), hedefYol, StandardCopyOption.REPLACE_EXISTING);

                    secilenResimYolu = hedefYol.toString().replace("\\", "/");
                    lblResim.setText("✓ Seçildi: " + yeniDosyaAdi);
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Resim kopyalanırken hata oluştu.");
                }
            }
        });

        // Kaydet butonu işlevi
        btnKaydet.addActionListener(e -> {
            String ad = txtAd.getText().trim();
            String hikaye = txtHikaye.getText().trim();
            String malzeme = txtMalzeme.getText().trim();
            String tarif = txtTarif.getText().trim();

            if (ad.isEmpty() || hikaye.isEmpty() || malzeme.isEmpty() || tarif.isEmpty() || secilenResimYolu.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Tüm alanları ve resmi doldurmalısınız.");
                return;
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/kokteyller.txt", true))) {
                writer.write(ad + ";" + hikaye + ";" + malzeme + ";" + tarif + ";" + secilenResimYolu + ";" + "true");
                writer.newLine();
                JOptionPane.showMessageDialog(this, "Kokteyl başarıyla eklendi!");
                dispose();
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Dosyaya yazılamadı!");
            }
        });

        setVisible(true);
    }

    // 🔷 Siyah arka planlı, beyaz yazılı buton
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
