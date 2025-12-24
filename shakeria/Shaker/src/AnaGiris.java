import javax.swing.*;
import java.awt.*;

public class AnaGiris extends JFrame {
    private KokteylYonetici kokteylYonetici;

    public AnaGiris(KokteylYonetici kokteylYonetici) {
        this.kokteylYonetici = kokteylYonetici;

        // Genel ayarlar
        setTitle("SHAKERİA");
        setSize(450, 520);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Renk ve fontlar
        Color arkaPlan = new Color(245, 245, 245);
        Font baslikFont = new Font("Serif", Font.BOLD, 36);
        Font sloganFont = new Font("SansSerif", Font.ITALIC, 15);
        Font butonFont = new Font("SansSerif", Font.BOLD, 17);

        // Ana panel
        JPanel anaPanel = new JPanel(new BorderLayout());
        anaPanel.setBackground(arkaPlan);

        // ÜST: Başlık ve slogan
        JPanel ustPanel = new JPanel();
        ustPanel.setLayout(new BoxLayout(ustPanel, BoxLayout.Y_AXIS));
        ustPanel.setBackground(arkaPlan);

        JLabel baslik = new JLabel("Shakeria");
        baslik.setFont(baslikFont);
        baslik.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel slogan = new JLabel("Her karışım bir hikâyedir.");
        slogan.setFont(new Font("SansSerif", Font.ITALIC, 16)); // Italic + 16 pt
        slogan.setForeground(new Color(80, 80, 80)); // Daha koyu gri
        slogan.setAlignmentX(Component.CENTER_ALIGNMENT);


        ustPanel.add(Box.createVerticalStrut(60));
        ustPanel.add(baslik);
        ustPanel.add(Box.createVerticalStrut(5));
        ustPanel.add(slogan);
        ustPanel.add(Box.createVerticalStrut(40));

        anaPanel.add(ustPanel, BorderLayout.NORTH);

        // ORTA: Butonlar
        JPanel butonPanel = new JPanel();
        butonPanel.setLayout(new BoxLayout(butonPanel, BoxLayout.Y_AXIS));
        butonPanel.setBackground(arkaPlan);
        butonPanel.setBorder(BorderFactory.createEmptyBorder(10, 60, 30, 60));

        JButton kullaniciBtn = createStyledButton("Kullanıcı Girişi", butonFont);
        JButton adminBtn = createStyledButton("Admin Girişi", butonFont);
        JButton uyeOlBtn = createStyledButton("Üye Ol", butonFont);

        kullaniciBtn.addActionListener(e -> {
            dispose();
            new KullanıcıLogin(kokteylYonetici);
        });

        adminBtn.addActionListener(e -> {
            dispose();
            new AdminLogin(kokteylYonetici);
        });

        uyeOlBtn.addActionListener(e -> {
            dispose();
            new UyeOlPaneli();
        });

        butonPanel.add(kullaniciBtn);
        butonPanel.add(Box.createVerticalStrut(20));
        butonPanel.add(adminBtn);
        butonPanel.add(Box.createVerticalStrut(20));
        butonPanel.add(uyeOlBtn);

        anaPanel.add(butonPanel, BorderLayout.CENTER);

        // Paneli frame’e ekle
        setContentPane(anaPanel);
        setVisible(true);
    }

    // 🔷 Siyah arka planlı, beyaz yazılı buton
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