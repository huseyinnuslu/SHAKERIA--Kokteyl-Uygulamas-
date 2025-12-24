import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AdminLogin extends JFrame {
    private KokteylYonetici kokteylYonetici;
    private JTextField kullaniciAdiField;
    private JPasswordField sifreField;

    public AdminLogin(KokteylYonetici kokteylYonetici) {
        this.kokteylYonetici = kokteylYonetici;

        setTitle("Shakeria – Admin Girişi");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setResizable(false);

        // Renk ve fontlar
        Color arkaPlan = new Color(245, 245, 245);
        Font genelFont = new Font("SansSerif", Font.PLAIN, 14);
        Font butonFont = new Font("SansSerif", Font.BOLD, 16);

        // ⬅️ Geri Butonu
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

        // Giriş paneli
        JPanel girisPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        girisPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        girisPanel.setBackground(arkaPlan);

        JLabel kullaniciAdiLabel = new JLabel("Kullanıcı Adı:");
        kullaniciAdiField = new JTextField(20);
        kullaniciAdiLabel.setFont(genelFont);

        JLabel sifreLabel = new JLabel("Şifre:");
        sifreField = new JPasswordField(20);
        sifreLabel.setFont(genelFont);

        JButton girisButton = createStyledButton("Giriş Yap", butonFont);

        girisButton.addActionListener(e -> {
            String kullaniciAdi = kullaniciAdiField.getText();
            String sifre = new String(sifreField.getPassword());

            if (kullaniciAdi.equals("huseyin") && sifre.equals("huseyin10")) {
                JOptionPane.showMessageDialog(this, "Giriş Başarılı!");
                new AdminPanel(kokteylYonetici).setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Kullanıcı adı veya şifre hatalı!", "Hata", JOptionPane.ERROR_MESSAGE);
            }
        });

        girisPanel.add(kullaniciAdiLabel);
        girisPanel.add(kullaniciAdiField);
        girisPanel.add(sifreLabel);
        girisPanel.add(sifreField);
        girisPanel.add(new JLabel()); // boşluk
        girisPanel.add(girisButton);

        add(girisPanel, BorderLayout.CENTER);
        getContentPane().setBackground(arkaPlan);
        setVisible(true);
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
