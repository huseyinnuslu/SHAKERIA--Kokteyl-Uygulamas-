import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AdminPanel extends JFrame {
    private KokteylYonetici kokteylYonetici;

    public AdminPanel(KokteylYonetici kokteylYonetici) {
        this.kokteylYonetici = kokteylYonetici;

        setTitle("Admin Paneli");
        setSize(450, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Stil renk ve fontlar
        Color arkaPlan = new Color(245, 245, 245);
        Font butonFont = new Font("SansSerif", Font.BOLD, 17);

        // Ana panel
        JPanel anaPanel = new JPanel();
        anaPanel.setLayout(new BoxLayout(anaPanel, BoxLayout.Y_AXIS));
        anaPanel.setBackground(arkaPlan);
        anaPanel.setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));

        // Butonlar
        JButton btnKokteylEkle = createStyledButton("Kokteyl Ekle", butonFont);
        JButton btnKokteylGuncelle = createStyledButton("Kokteyl Güncelle", butonFont);
        JButton btnKokteylSil = createStyledButton("Kokteyl Sil", butonFont);
        JButton btnTarifleriGor = createStyledButton("Kullanıcı Tariflerini Gör", butonFont);
        JButton btnGeri = createStyledButton("Çıkış Yap", butonFont);

        // Buton işlevleri
        btnKokteylEkle.addActionListener(e -> {
            dispose();
            new AdminKokteylEklePanel(kokteylYonetici);
        });

        btnKokteylGuncelle.addActionListener(e -> {
            dispose();
            new AdminKokteylGuncellePanel(kokteylYonetici);
        });

        btnKokteylSil.addActionListener(e -> {
            dispose();
            new AdminKokteylSilmePanel(kokteylYonetici);
        });

        btnTarifleriGor.addActionListener(e -> {
            dispose();
            new KokteylOnayPaneli(kokteylYonetici);
        });

        btnGeri.addActionListener(e -> {
            dispose();
            new AnaGiris(kokteylYonetici);
        });

        // Sırayla ekle
        anaPanel.add(btnKokteylEkle);
        anaPanel.add(Box.createVerticalStrut(20));
        anaPanel.add(btnKokteylGuncelle);
        anaPanel.add(Box.createVerticalStrut(20));
        anaPanel.add(btnKokteylSil);
        anaPanel.add(Box.createVerticalStrut(20));
        anaPanel.add(btnTarifleriGor);
        anaPanel.add(Box.createVerticalStrut(30));
        anaPanel.add(btnGeri);

        setContentPane(anaPanel);
        setVisible(true);
    }

    // 🔷 Siyah arka planlı, beyaz yazılı buton (standart)
    private JButton createStyledButton(String text, Font font) {
        JButton button = new JButton(text);
        button.setFont(font);
        button.setBackground(Color.BLACK);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(280, 50));
        button.setMaximumSize(new Dimension(280, 50));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        return button;
    }
}
