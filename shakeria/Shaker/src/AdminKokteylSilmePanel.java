import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class AdminKokteylSilmePanel extends JFrame {
    private KokteylYonetici kokteylYonetici;
    private JList<String> liste;
    private DefaultListModel<String> model;
    private JLabel resimLabel;
    private JTextArea detayAlani;

    public AdminKokteylSilmePanel(KokteylYonetici kokteylYonetici) {
        this.kokteylYonetici = kokteylYonetici;

        setTitle("Kokteyl Sil");
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setResizable(false);

        // Stil renk ve font
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

        // Liste modeli ve JList
        model = new DefaultListModel<>();
        for (Kokteyl k : kokteylYonetici.getOnayliKokteyller()) {
            model.addElement(k.getAd());
        }

        liste = new JList<>(model);
        liste.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane listeScroll = new JScrollPane(liste);
        listeScroll.setPreferredSize(new Dimension(200, 300));

        // Resim ve detay alanı
        resimLabel = new JLabel();
        detayAlani = new JTextArea();
        detayAlani.setEditable(false);
        detayAlani.setLineWrap(true);
        detayAlani.setWrapStyleWord(true);
        JScrollPane detayScroll = new JScrollPane(detayAlani);

        JPanel detayPanel = new JPanel(new BorderLayout());
        detayPanel.setBackground(arkaPlan);
        detayPanel.add(resimLabel, BorderLayout.NORTH);
        detayPanel.add(detayScroll, BorderLayout.CENTER);

        // Sil butonu
        JButton btnSil = createStyledButton("Seçili Kokteyli Sil", butonFont);
        btnSil.addActionListener(e -> kokteylSil());

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(arkaPlan);
        mainPanel.add(listeScroll, BorderLayout.WEST);
        mainPanel.add(detayPanel, BorderLayout.CENTER);

        JPanel altPanel = new JPanel();
        altPanel.setBackground(arkaPlan);
        altPanel.add(btnSil);
        mainPanel.add(altPanel, BorderLayout.SOUTH);

        liste.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String seciliAd = liste.getSelectedValue();
                Kokteyl k = kokteylYonetici.getKokteylByAd(seciliAd);
                if (k != null) {
                    detayAlani.setText(
                        "Ad: " + k.getAd() + "\n" +
                        "Hikaye: " + k.getHikaye() + "\n" +
                        "Malzemeler: " + String.join(", ", k.getMalzemeler()) + "\n" +
                        "Tarif: " + k.getTarif()
                    );

                    if (k.getResimYolu() != null && !k.getResimYolu().isEmpty()) {
                        ImageIcon icon = new ImageIcon(k.getResimYolu());
                        Image image = icon.getImage().getScaledInstance(200, 150, Image.SCALE_SMOOTH);
                        resimLabel.setIcon(new ImageIcon(image));
                    } else {
                        resimLabel.setIcon(null);
                    }
                }
            }
        });

        add(mainPanel, BorderLayout.CENTER);
        getContentPane().setBackground(arkaPlan);
        setVisible(true);
    }

    private void kokteylSil() {
        String seciliAd = liste.getSelectedValue();
        if (seciliAd == null) {
            JOptionPane.showMessageDialog(this, "Lütfen silinecek bir kokteyl seçin.");
            return;
        }

        int onay = JOptionPane.showConfirmDialog(this, "Emin misiniz?", "Silme Onayı", JOptionPane.YES_NO_OPTION);
        if (onay != JOptionPane.YES_OPTION) return;

        kokteylYonetici.getOnayliKokteyller().removeIf(k -> k.getAd().equalsIgnoreCase(seciliAd));

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

        model.removeElement(seciliAd);
        detayAlani.setText("");
        resimLabel.setIcon(null);
        JOptionPane.showMessageDialog(this, "Kokteyl başarıyla silindi.");
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
