import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.List;

public class TumKokteyllerGUI extends JFrame {
    private KokteylYonetici kokteylYonetici;

    public TumKokteyllerGUI(KokteylYonetici kokteylYonetici) {
        this.kokteylYonetici = kokteylYonetici;

        setTitle("Tüm Onaylı Kokteyller");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        Color arkaPlan = new Color(245, 245, 245);
        Font butonFont = new Font("SansSerif", Font.BOLD, 16);

        JPanel anaPanel = new JPanel(new BorderLayout());
        anaPanel.setBackground(arkaPlan);

        // 🔙 Geri butonu
        JButton geriButon = createStyledButton("← Geri", butonFont);
        geriButon.addActionListener(e -> {
            dispose();
            new KullanıcıPaneli(kokteylYonetici);
        });

        JPanel ustPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ustPanel.setBackground(arkaPlan);
        ustPanel.add(geriButon);
        anaPanel.add(ustPanel, BorderLayout.NORTH);

        // 🔽 Kokteyl kartları
        JPanel kartPanel = new JPanel();
        kartPanel.setLayout(new BoxLayout(kartPanel, BoxLayout.Y_AXIS));
        kartPanel.setBackground(arkaPlan);

        List<Kokteyl> tumKokteyller = kokteylYonetici.getOnayliKokteyller();
        for (Kokteyl k : tumKokteyller) {
            JPanel kart = createKokteylKart(k);
            kartPanel.add(kart);
            kartPanel.add(Box.createVerticalStrut(10));
        }

        JScrollPane scroll = new JScrollPane(kartPanel);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        anaPanel.add(scroll, BorderLayout.CENTER);

        getContentPane().add(anaPanel);
        getContentPane().setBackground(arkaPlan);
        setVisible(true);
    }

    // 🧩 Kokteyl kartı oluştur
    private JPanel createKokteylKart(Kokteyl kokteyl) {
        JPanel kart = new JPanel();
        kart.setLayout(new BorderLayout());
        kart.setPreferredSize(new Dimension(500, 100));
        kart.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        kart.setBackground(Color.WHITE);

        // Resim yükle
        String resimAdi = kokteyl.getAd().toLowerCase().replace(" ", "") + ".jpg.png";
        ImageIcon icon = new ImageIcon("src/resimler/" + resimAdi);
        Image img = icon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
        JLabel imgLabel = new JLabel(new ImageIcon(img));
        kart.add(imgLabel, BorderLayout.WEST);

        // Başlık
        JLabel adLabel = new JLabel(kokteyl.getAd());
        adLabel.setFont(new Font("Serif", Font.BOLD, 20));
        adLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

        kart.add(adLabel, BorderLayout.CENTER);

        // Tıklanabilirlik
        kart.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        kart.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new KokteylDetayPenceresi(kokteyl);
            }
        });

        return kart;
    }

    // 🔲 Siyah arka planlı buton
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
