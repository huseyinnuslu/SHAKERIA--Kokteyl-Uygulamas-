import javax.swing.*;
import java.awt.*;
import java.util.List;

public class KokteylDetayPenceresi extends JFrame {
    public KokteylDetayPenceresi(Kokteyl kokteyl) {
        setTitle(kokteyl.getAd() + " Detayları");
        setSize(450, 400);
        setLocationRelativeTo(null);
        setResizable(false);

        Color arkaPlan = new Color(250, 250, 250);
        setLayout(new BorderLayout());

        JTextArea detayAlani = new JTextArea();
        detayAlani.setEditable(false);
        detayAlani.setLineWrap(true);
        detayAlani.setWrapStyleWord(true);
        detayAlani.setFont(new Font("SansSerif", Font.PLAIN, 14));

        StringBuilder sb = new StringBuilder();
        sb.append("🥂 ").append(kokteyl.getAd()).append("\n\n");
        sb.append("📖 Hikayesi:\n").append(kokteyl.getHikaye()).append("\n\n");
        sb.append("🧂 Malzemeler:\n");
        for (String m : kokteyl.getMalzemeler()) {
            sb.append("- ").append(m).append("\n");
        }
        sb.append("\n🧪 Tarif:\n").append(kokteyl.getTarif());

        detayAlani.setText(sb.toString());

        JScrollPane scrollPane = new JScrollPane(detayAlani);
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }
}
