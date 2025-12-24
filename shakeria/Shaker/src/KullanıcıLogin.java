import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class KullanıcıLogin extends JFrame {
    private KokteylYonetici kokteylYonetici;

    public KullanıcıLogin(KokteylYonetici kokteylYonetici) {
        this.kokteylYonetici = kokteylYonetici;

        // Pencere ayarları
        setTitle("Shakeria – Kullanıcı Girişi");
        setSize(400, 260);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
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
            new AnaGiris(kokteylYonetici);
        });

        JPanel ustPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ustPanel.setBackground(arkaPlan);
        ustPanel.add(geriButon);
        add(ustPanel, BorderLayout.NORTH);

        // Giriş formu
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBackground(arkaPlan);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JLabel lblKullaniciAdi = new JLabel("Kullanıcı Adı:");
        JTextField txtKullaniciAdi = new JTextField();
        JLabel lblSifre = new JLabel("Şifre:");
        JPasswordField txtSifre = new JPasswordField();
        JButton btnGiris = createStyledButton("Giriş Yap", butonFont);

        lblKullaniciAdi.setFont(genelFont);
        lblSifre.setFont(genelFont);

        panel.add(lblKullaniciAdi);
        panel.add(txtKullaniciAdi);
        panel.add(lblSifre);
        panel.add(txtSifre);
        panel.add(new JLabel()); // boşluk
        panel.add(btnGiris);

        add(panel, BorderLayout.CENTER);
        getContentPane().setBackground(arkaPlan);
        setVisible(true);

        // Giriş butonu işlevi
        btnGiris.addActionListener(e -> {
            String kullaniciAdi = txtKullaniciAdi.getText().trim();
            String sifre = new String(txtSifre.getPassword()).trim();

            if (girisKontrol(kullaniciAdi, sifre)) {
                JOptionPane.showMessageDialog(this, "Giriş başarılı!");
                new KullanıcıPaneli(kokteylYonetici);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Hatalı kullanıcı adı veya şifre!");
            }
        });
    }

    // ✅ Giriş kontrolü (uyeler.txt dosyasından okur)
    private boolean girisKontrol(String kullaniciAdi, String sifre) {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/uyeler.txt"))) {
            String satir;
            while ((satir = reader.readLine()) != null) {
                String[] parcalar = satir.split(";");
                if (parcalar.length >= 3) {
                    String dosyaKullaniciAdi = parcalar[1].trim();
                    String dosyaSifre = parcalar[2].trim();

                    if (kullaniciAdi.equals(dosyaKullaniciAdi) && sifre.equals(dosyaSifre)) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Dosya okunamadı: uyeler.txt");
        }
        return false;
    }

    // 🔷 Siyah arka planlı, beyaz yazılı buton (AnaGiris ile uyumlu)
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
