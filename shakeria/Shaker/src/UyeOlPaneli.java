import javax.swing.*;
import java.awt.*;
import java.io.*;

public class UyeOlPaneli extends JFrame {

    public UyeOlPaneli() {
        setTitle("Shakeria - Üye Ol");
        setSize(350, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setResizable(false);

        // Renk ve fontlar
        Color arkaPlan = new Color(245, 245, 245);
        Font butonFont = new Font("SansSerif", Font.BOLD, 16);
        Font yaziFont = new Font("SansSerif", Font.PLAIN, 14);

        // ⬅️ Geri Butonu
        JButton geriButon = createStyledButton("← Geri", butonFont);
        geriButon.setPreferredSize(new Dimension(100, 40));
        geriButon.addActionListener(e -> {
            dispose();
            new AnaGiris(new KokteylYonetici());
        });

        JPanel ustPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ustPanel.setBackground(arkaPlan);
        ustPanel.add(geriButon);
        add(ustPanel, BorderLayout.NORTH);

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        panel.setBackground(arkaPlan);

        JLabel adLabel = new JLabel("Ad Soyad:");
        adLabel.setFont(yaziFont);
        JTextField adField = new JTextField();

        JLabel kullaniciAdiLabel = new JLabel("Kullanıcı Adı:");
        kullaniciAdiLabel.setFont(yaziFont);
        JTextField kullaniciAdiField = new JTextField();

        JLabel sifreLabel = new JLabel("Şifre:");
        sifreLabel.setFont(yaziFont);
        JPasswordField sifreField = new JPasswordField();

        JButton kaydolBtn = createStyledButton("Kaydol", butonFont);

        panel.add(adLabel); panel.add(adField);
        panel.add(kullaniciAdiLabel); panel.add(kullaniciAdiField);
        panel.add(sifreLabel); panel.add(sifreField);
        panel.add(new JLabel()); panel.add(kaydolBtn);

        add(panel, BorderLayout.CENTER);
        getContentPane().setBackground(arkaPlan);

        kaydolBtn.addActionListener(e -> {
            String ad = adField.getText().trim();
            String kullaniciAdi = kullaniciAdiField.getText().trim();
            String sifre = new String(sifreField.getPassword()).trim();

            if (ad.length() < 3 || !ad.matches("[a-zA-ZçÇğĞıİöÖşŞüÜ\\s]+")) {
                JOptionPane.showMessageDialog(this, "Lütfen geçerli bir ad-soyad girin (sadece harf ve boşluk).");
                return;
            }

            if (kullaniciAdi.length() < 4 || kullaniciAdi.length() > 20 || !kullaniciAdi.matches("[a-zA-Z0-9]+")) {
                JOptionPane.showMessageDialog(this, "Kullanıcı adı 4-20 karakter olmalı, sadece harf ve rakam içermeli.");
                return;
            }

            if (sifre.length() < 6 || !sifre.matches(".*[a-zA-Z].*") || !sifre.matches(".*\\d.*")) {
                JOptionPane.showMessageDialog(this, "Şifre en az 6 karakter olmalı, hem harf hem rakam içermeli.");
                return;
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/uyeler.txt", true))) {
                writer.write(ad + ";" + kullaniciAdi + ";" + sifre);
                writer.newLine();
                JOptionPane.showMessageDialog(this, "Kayıt başarılı!");
                dispose();
                new AnaGiris(new KokteylYonetici());
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Hata oluştu, dosya yazılamadı.");
            }
        });

        setVisible(true);
    }

    // 🔷 Stil butonu (siyah zemin, beyaz yazı, 280x45)
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
