import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class KokteylOnayPaneli extends JFrame {
    private List<Kokteyl> bekleyenKokteyller;
    private KokteylYonetici kokteylYonetici;
    private JPanel panel;

    public KokteylOnayPaneli(KokteylYonetici kokteylYonetici) {
        this.kokteylYonetici = kokteylYonetici;

        setTitle("Onay Bekleyen Kokteyller");
        setSize(700, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
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

        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(arkaPlan);

        JScrollPane scrollPane = new JScrollPane(panel);
        add(scrollPane, BorderLayout.CENTER);

        refreshList();
        setVisible(true);
    }

    private void refreshList() {
        panel.removeAll();
        bekleyenKokteyller = kokteylYonetici.getOnayBekleyenKokteyller();

        for (Kokteyl k : bekleyenKokteyller) {
            JPanel kokteylPanel = new JPanel();
            kokteylPanel.setLayout(new BoxLayout(kokteylPanel, BoxLayout.Y_AXIS));
            kokteylPanel.setBackground(Color.WHITE);
            kokteylPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(15, 15, 15, 15),
                BorderFactory.createLineBorder(Color.LIGHT_GRAY)
            ));

            // Resim
            if (k.getResimYolu() != null && !k.getResimYolu().isEmpty()) {
                try {
                    ImageIcon icon = new ImageIcon(k.getResimYolu());
                    Image image = icon.getImage().getScaledInstance(250, 200, Image.SCALE_SMOOTH);
                    JLabel resimLabel = new JLabel(new ImageIcon(image));
                    resimLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                    kokteylPanel.add(resimLabel);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            // Bilgi alanı
            JTextArea info = new JTextArea(
                "Ad: " + k.getAd() + "\n\n" +
                "Hikaye:\n" + k.getHikaye() + "\n\n" +
                "Malzemeler:\n" + String.join(", ", k.getMalzemeler()) + "\n\n" +
                "Tarif:\n" + k.getTarif()
            );
            info.setEditable(false);
            info.setLineWrap(true);
            info.setWrapStyleWord(true);
            info.setBackground(new Color(250, 250, 250));
            info.setFont(new Font("SansSerif", Font.PLAIN, 13));
            info.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            JScrollPane infoScroll = new JScrollPane(info);
            infoScroll.setPreferredSize(new Dimension(650, 180));
            kokteylPanel.add(Box.createVerticalStrut(10));
            kokteylPanel.add(infoScroll);

            // Butonlar
            JPanel butonPanel = new JPanel();
            butonPanel.setBackground(Color.WHITE);
            JButton onaylaBtn = createStyledButton("Onayla", new Font("SansSerif", Font.BOLD, 14));
            JButton reddetBtn = createStyledButton("Reddet", new Font("SansSerif", Font.BOLD, 14));

            onaylaBtn.addActionListener(e -> {
                kokteylYonetici.kokteylOnayla(k);
                refreshList();
            });

            reddetBtn.addActionListener(e -> {
                kokteylYonetici.kokteylReddet(k);
                refreshList();
            });

            butonPanel.add(onaylaBtn);
            butonPanel.add(Box.createHorizontalStrut(10));
            butonPanel.add(reddetBtn);

            kokteylPanel.add(Box.createVerticalStrut(10));
            kokteylPanel.add(butonPanel);
            kokteylPanel.add(Box.createVerticalStrut(10));

            panel.add(kokteylPanel);
            panel.add(Box.createVerticalStrut(20));
        }

        panel.revalidate();
        panel.repaint();
    }

    // 🔷 Standart buton stili
    private JButton createStyledButton(String text, Font font) {
        JButton button = new JButton(text);
        button.setFont(font);
        button.setBackground(Color.BLACK);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(140, 40));
        button.setMaximumSize(new Dimension(140, 40));
        button.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        return button;
    }
}
