import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.*;
import java.util.List;

public class AdminKokteylGuncellePanel extends JFrame {
    private KokteylYonetici kokteylYonetici;
    private JComboBox<String> kokteylSec;
    private JTextField txtAd, txtHikaye, txtMalzeme, txtTarif;
    private JLabel resimLabel;
    private String secilenResimYolu = "";

    public AdminKokteylGuncellePanel(KokteylYonetici kokteylYonetici) {
        this.kokteylYonetici = kokteylYonetici;

        setTitle("Kokteyl Güncelle");
        setSize(550, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);

        Color arkaPlan = new Color(245, 245, 245);
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

        JPanel panel = new JPanel(new GridLayout(7, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        panel.setBackground(arkaPlan);

        kokteylSec = new JComboBox<>();
        for (Kokteyl k : kokteylYonetici.getOnayliKokteyller()) {
            kokteylSec.addItem(k.getAd());
        }

        txtAd = new JTextField();
        txtHikaye = new JTextField();
        txtMalzeme = new JTextField();
        txtTarif = new JTextField();

        resimLabel = new JLabel("Resim yok", SwingConstants.CENTER);
        JButton btnResimSec = createStyledButton("Yeni Resim Seç", butonFont);
        JButton btnGuncelle = createStyledButton("Güncelle", butonFont);

        panel.add(new JLabel("Kokteyl Seç:")); panel.add(kokteylSec);
        panel.add(new JLabel("Yeni Ad:")); panel.add(txtAd);
        panel.add(new JLabel("Yeni Hikaye:")); panel.add(txtHikaye);
        panel.add(new JLabel("Yeni Malzemeler (virgülle):")); panel.add(txtMalzeme);
        panel.add(new JLabel("Yeni Tarif:")); panel.add(txtTarif);
        panel.add(btnResimSec); panel.add(resimLabel);
        panel.add(new JLabel()); panel.add(btnGuncelle);

        add(panel, BorderLayout.CENTER);
        getContentPane().setBackground(arkaPlan);

        kokteylSec.addActionListener(e -> bilgileriDoldur());
        btnResimSec.addActionListener(e -> resimSec());
        btnGuncelle.addActionListener(e -> kokteyliGuncelle());

        bilgileriDoldur();
        setVisible(true);
    }

    private void bilgileriDoldur() {
        String secilenAd = (String) kokteylSec.getSelectedItem();
        for (Kokteyl k : kokteylYonetici.getOnayliKokteyller()) {
            if (k.getAd().equalsIgnoreCase(secilenAd)) {
                txtAd.setText(k.getAd());
                txtHikaye.setText(k.getHikaye());
                txtMalzeme.setText(String.join(",", k.getMalzemeler()));
                txtTarif.setText(k.getTarif());
                secilenResimYolu = k.getResimYolu();

                if (secilenResimYolu != null && !secilenResimYolu.isEmpty()) {
                    ImageIcon icon = new ImageIcon(secilenResimYolu);
                    Image scaled = icon.getImage().getScaledInstance(100, 80, Image.SCALE_SMOOTH);
                    resimLabel.setIcon(new ImageIcon(scaled));
                    resimLabel.setText("");
                } else {
                    resimLabel.setIcon(null);
                    resimLabel.setText("Resim yok");
                }
                break;
            }
        }
    }

    private void resimSec() {
        JFileChooser dosyaSecici = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Resimler (*.jpg, *.png)", "jpg", "jpeg", "png");
        dosyaSecici.setFileFilter(filter);

        int sonuc = dosyaSecici.showOpenDialog(this);
        if (sonuc == JFileChooser.APPROVE_OPTION) {
            File secilenDosya = dosyaSecici.getSelectedFile();
            try {
                String hedefKlasor = "src/resimler";
                new File(hedefKlasor).mkdirs();
                String yeniDosyaAdi = txtAd.getText().trim().replaceAll("\\s+", "_") + "_" + secilenDosya.getName();
                Path hedefYol = Paths.get(hedefKlasor, yeniDosyaAdi);
                Files.copy(secilenDosya.toPath(), hedefYol, StandardCopyOption.REPLACE_EXISTING);

                secilenResimYolu = hedefYol.toString().replace("\\", "/");
                ImageIcon icon = new ImageIcon(secilenResimYolu);
                Image scaled = icon.getImage().getScaledInstance(100, 80, Image.SCALE_SMOOTH);
                resimLabel.setIcon(new ImageIcon(scaled));
                resimLabel.setText("");
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Resim kopyalanırken hata oluştu.");
            }
        }
    }

    private void kokteyliGuncelle() {
        String eskiAd = (String) kokteylSec.getSelectedItem();
        String yeniAd = txtAd.getText().trim();
        String yeniHikaye = txtHikaye.getText().trim();
        String yeniMalzeme = txtMalzeme.getText().trim();
        String yeniTarif = txtTarif.getText().trim();

        if (yeniAd.isEmpty() || yeniHikaye.isEmpty() || yeniMalzeme.isEmpty() || yeniTarif.isEmpty() || secilenResimYolu.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tüm alanları doldurun ve resim seçin.");
            return;
        }

        for (Kokteyl k : kokteylYonetici.getOnayliKokteyller()) {
            if (k.getAd().equalsIgnoreCase(eskiAd)) {
                k.setAd(yeniAd);
                k.setHikaye(yeniHikaye);
                k.setMalzemeler(List.of(yeniMalzeme.split(",")));
                k.setTarif(yeniTarif);
                k.setResimYolu(secilenResimYolu);
                break;
            }
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("src/kokteyller.txt"))) {
            for (Kokteyl k : kokteylYonetici.getOnayliKokteyller()) {
                bw.write(k.getAd() + ";" +
                        k.getHikaye() + ";" +
                        String.join(",", k.getMalzemeler()) + ";" +
                        k.getTarif() + ";" +
                        k.getResimYolu() + ";" +
                        "true");
                bw.newLine();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        JOptionPane.showMessageDialog(this, "Kokteyl başarıyla güncellendi.");
        dispose();
    }

    // 🔷 Stil buton metodu
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
