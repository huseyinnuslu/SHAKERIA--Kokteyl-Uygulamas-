import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

public class KokteylListelemeGUI {
    private KokteylYonetici kokteylYonetici;
    private JFrame pencere;
    private JList<Kokteyl> liste;
    private DefaultListModel<Kokteyl> listeModeli;
    private List<Kokteyl> gosterilenKokteyller;
    private Map<Kokteyl, Integer> uygunlukMap;

    public KokteylListelemeGUI(KokteylYonetici kokteylYonetici) {
        this.kokteylYonetici = kokteylYonetici;
        pencereOlustur();
    }

    private void pencereOlustur() {
        pencere = new JFrame("Yapabileceğiniz Kokteyller");
        pencere.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pencere.setSize(500, 400);
        pencere.setLayout(new BorderLayout());

        // Geri butonu
        JButton geriButon = new JButton("← Geri");
        geriButon.addActionListener(e -> {
            pencere.dispose();
            new KullanıcıPaneli(kokteylYonetici); // 🔁 Ana panele dönüş sağlandı
        });

        JPanel ustPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ustPanel.add(geriButon);
        pencere.add(ustPanel, BorderLayout.NORTH);

        listeModeli = new DefaultListModel<>();
        liste = new JList<>(listeModeli);
        liste.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        liste.setCellRenderer(new KokteylRenderer());
        JScrollPane kaydirma = new JScrollPane(liste);

        uygunKokteylleriYukle();

        liste.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int index = liste.getSelectedIndex();
                if (index >= 0 && index < gosterilenKokteyller.size()) {
                    Kokteyl secilen = gosterilenKokteyller.get(index);
                    String bilgi = "🍹 Kokteyl Adı: " + secilen.getAd()
                            + "\n\n📖 Hikayesi:\n" + secilen.getHikaye()
                            + "\n\n🧾 Malzemeler:\n" + String.join(", ", secilen.getMalzemeler())
                            + "\n\n👨‍🍳 Tarif:\n" + secilen.getTarif();

                    JTextArea detay = new JTextArea(bilgi);
                    detay.setEditable(false);
                    detay.setLineWrap(true);
                    detay.setWrapStyleWord(true);
                    detay.setFont(new Font("SansSerif", Font.PLAIN, 15)); // 🔠 Yazı büyütüldü

                    JDialog dialog = new JDialog(pencere, secilen.getAd(), true);
                    dialog.setSize(500, 400);
                    dialog.setLocationRelativeTo(pencere);
                    dialog.add(new JScrollPane(detay));
                    dialog.setVisible(true);
                }
            }
        });

        pencere.add(kaydirma, BorderLayout.CENTER);
        pencere.setLocationRelativeTo(null);
        pencere.setVisible(true);
    }

    private void uygunKokteylleriYukle() {
        listeModeli.clear();
        gosterilenKokteyller = new ArrayList<>();
        uygunlukMap = kokteylYonetici.getKokteyllerUygunlukPuani();

        List<Map.Entry<Kokteyl, Integer>> siraliListe = new ArrayList<>(uygunlukMap.entrySet());
        siraliListe.sort((a, b) -> Integer.compare(b.getValue(), a.getValue()));

        for (Map.Entry<Kokteyl, Integer> entry : siraliListe) {
            Kokteyl k = entry.getKey();
            gosterilenKokteyller.add(k);
            listeModeli.addElement(k);
        }
    }

    // Özel renderer (resimli, oranlı)
    class KokteylRenderer extends JLabel implements ListCellRenderer<Kokteyl> {
        public KokteylRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getListCellRendererComponent(JList<? extends Kokteyl> list, Kokteyl kokteyl, int index,
                                                      boolean isSelected, boolean cellHasFocus) {
            String ad = kokteyl.getAd();
            int toplam = kokteyl.getMalzemeler().size();
            int bulunan = (int) Math.round(toplam * uygunlukMap.get(kokteyl) / 100.0);
            setText(ad + " (" + bulunan + "/" + toplam + ")");

            if (kokteyl.getResimYolu() != null && !kokteyl.getResimYolu().isEmpty()) {
                ImageIcon icon = new ImageIcon(kokteyl.getResimYolu());
                Image scaled = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                setIcon(new ImageIcon(scaled));
            } else {
                setIcon(null);
            }

            setFont(new Font("Arial", Font.PLAIN, 14));
            setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            setIconTextGap(15);

            if (isSelected) {
                setBackground(new Color(200, 220, 240));
                setForeground(Color.BLACK);
            } else {
                setBackground(Color.WHITE);
                setForeground(Color.BLACK);
            }

            return this;
        }
    }
}
