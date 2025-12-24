import javax.swing.*;
import java.awt.*;

public class KullanıcıPaneli extends JFrame {
    private KokteylYonetici kokteylYonetici;

    public KullanıcıPaneli(KokteylYonetici kokteylYonetici) {
        this.kokteylYonetici = kokteylYonetici;

        // Pencere ayarları
        setTitle("Kullanıcı Paneli");
        setSize(450, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Renk ve fontlar
        Color arkaPlan = new Color(245, 245, 245);
        Font butonFont = new Font("SansSerif", Font.BOLD, 17);

        // Ana panel
        JPanel anaPanel = new JPanel();
        anaPanel.setLayout(new BoxLayout(anaPanel, BoxLayout.Y_AXIS));
        anaPanel.setBackground(arkaPlan);
        anaPanel.setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));

        // Butonlar
        JButton btnMalzemeSec = createStyledButton("Malzemelerimi Seç", butonFont);
        JButton btnKokteylListele = createStyledButton("Kokteyller", butonFont);
        JButton btnKokteylEkle = createStyledButton("Yeni Kokteyl Ekle", butonFont);
        JButton btnGeri = createStyledButton("Çıkış Yap", butonFont);

        btnMalzemeSec.addActionListener(e -> {
            dispose();
            new MalzemeSecimiGUI(kokteylYonetici);
        });

        btnKokteylListele.addActionListener(e -> {
            dispose();
            new TumKokteyllerGUI(kokteylYonetici);
        });


        btnKokteylEkle.addActionListener(e -> {
            dispose();
            new KullanıcıKokteylEklePaneli(kokteylYonetici);
        });

        btnGeri.addActionListener(e -> {
            dispose();
            new AnaGiris(kokteylYonetici);
        });

        // Butonları sırayla ekle
        anaPanel.add(btnMalzemeSec);
        anaPanel.add(Box.createVerticalStrut(20));
        anaPanel.add(btnKokteylListele);
        anaPanel.add(Box.createVerticalStrut(20));
        anaPanel.add(btnKokteylEkle);
        anaPanel.add(Box.createVerticalStrut(30));
        anaPanel.add(btnGeri);

        // Ana paneli pencereye ekle
        setContentPane(anaPanel);
        setVisible(true);
    }

    // 🔷 Siyah arka planlı, beyaz yazılı buton (AnaGiris'ten aynen alındı)
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
