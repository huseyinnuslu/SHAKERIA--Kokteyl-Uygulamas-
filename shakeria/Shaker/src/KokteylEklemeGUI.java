import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class KokteylEklemeGUI extends JFrame {
    private KokteylYonetici kokteylYonetici;

    public KokteylEklemeGUI(KokteylYonetici kokteylYonetici) {
        this.kokteylYonetici = kokteylYonetici;

        setTitle("Kokteyl Ekle");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new GridLayout(5, 2, 10, 10));

        JLabel adLabel = new JLabel("Kokteyl Adı:");
        JTextField adField = new JTextField();
        JLabel hikayeLabel = new JLabel("Hikaye:");
        JTextArea hikayeArea = new JTextArea();
        JLabel malzemeLabel = new JLabel("Malzemeler (Virgülle ayırın):");
        JTextField malzemeField = new JTextField();
        JLabel tarifLabel = new JLabel("Tarif:");
        JTextArea tarifArea = new JTextArea();

        JButton ekleBtn = new JButton("Kokteyl Ekle");

        add(adLabel);
        add(adField);
        add(hikayeLabel);
        add(new JScrollPane(hikayeArea));
        add(malzemeLabel);
        add(malzemeField);
        add(tarifLabel);
        add(new JScrollPane(tarifArea));
        add(new JLabel());
        add(ekleBtn);

        ekleBtn.addActionListener(e -> {
            String ad = adField.getText();
            String hikaye = hikayeArea.getText();
            List<String> malzemeler = List.of(malzemeField.getText().split(","));
            String tarif = tarifArea.getText();

            Kokteyl kokteyl = new Kokteyl(ad, hikaye, malzemeler, tarif, false);
            kokteylYonetici.kokteylEkleDosyaya(kokteyl);
            JOptionPane.showMessageDialog(this, "Kokteyl başarıyla eklendi!");
            dispose(); 
        });

        setVisible(true);
    }
    
}
