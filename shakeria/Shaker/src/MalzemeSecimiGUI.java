import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class MalzemeSecimiGUI extends JFrame {
    private List<JCheckBox> secimKutulari = new ArrayList<>();
    private KokteylYonetici kokteylYonetici;

    public MalzemeSecimiGUI(KokteylYonetici kokteylYonetici) {
        this.kokteylYonetici = kokteylYonetici;

        // Pencere ayarları
        setTitle("Malzeme Seçimi");
        setSize(450, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setResizable(false);

        // Renk ve fontlar
        Color arkaPlan = new Color(245, 245, 245);
        Font butonFont = new Font("SansSerif", Font.BOLD, 17);

        List<Malzeme> tumMalzemeler = MalzemeDosyaOkuyucu.malzemeDosyasiniOku("src/malzemeler.txt");

        // ÜST panel: Geri butonu
        JButton geriButon = createStyledButton("← Geri", butonFont);
        geriButon.setPreferredSize(new Dimension(100, 40));
        geriButon.addActionListener(e -> {
            dispose();
            new KullanıcıPaneli(kokteylYonetici);
        });


        JPanel ustPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ustPanel.setBackground(arkaPlan);
        ustPanel.add(geriButon);
        add(ustPanel, BorderLayout.NORTH);

        // ORTA panel: Malzeme kutuları
        JPanel secimPanel = new JPanel();
        secimPanel.setLayout(new BoxLayout(secimPanel, BoxLayout.Y_AXIS));
        secimPanel.setBackground(arkaPlan);

        for (Malzeme malzeme : tumMalzemeler) {
            JCheckBox kutu = new JCheckBox(malzeme.getAd());
            kutu.setBackground(arkaPlan);
            kutu.setFont(new Font("SansSerif", Font.PLAIN, 15));
            secimKutulari.add(kutu);
            secimPanel.add(kutu);
        }

        JScrollPane kaydirmaPaneli = new JScrollPane(secimPanel);
        kaydirmaPaneli.setPreferredSize(new Dimension(350, 400));
        add(kaydirmaPaneli, BorderLayout.CENTER);

        // ALT panel: Buton
        JButton btnKokteylGoster = createStyledButton("Yapılabilir Kokteylleri Göster", butonFont);
        btnKokteylGoster.addActionListener(e -> kokteylleriGoster());

        JPanel altPanel = new JPanel();
        altPanel.setBackground(arkaPlan);
        altPanel.add(btnKokteylGoster);
        add(altPanel, BorderLayout.SOUTH);

        getContentPane().setBackground(arkaPlan);
        setVisible(true);
    }

    private void kokteylleriGoster() {
        Set<String> secilenMalzemeler = new HashSet<>();

        for (JCheckBox kutu : secimKutulari) {
            if (kutu.isSelected()) {
                secilenMalzemeler.add(kutu.getText());
            }
        }

        kokteylYonetici.setSecilenMalzemeler(secilenMalzemeler);

        if (secilenMalzemeler.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Lütfen en az bir malzeme seçin.");
        } else {
            dispose();
            new KokteylListelemeGUI(kokteylYonetici);
        }
    }

    // 🔷 Siyah arka planlı, beyaz yazılı buton (standart stile uyumlu)
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
