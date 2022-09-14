package halfbyte.app;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class AboutDialog extends JDialog {
    // variables


    // methods
    public AboutDialog(JFrame parent){
        // super size me
        super(parent, "About JexEdit", true);

        // set size of this frame
        this.setSize(256, 256);

        // relative to parent
        this.setLocationRelativeTo(parent);

        // content
        JPanel content = new JPanel(new GridBagLayout());
        this.setContentPane(content);

        // label
        JLabel label = new JLabel("Created by Daniel J. Steffey", SwingConstants.CENTER);
        label.setBorder(new LineBorder(Color.BLACK));
        content.add(label, new GridBagConstraints(
                0, 0,
                1, 1,
                1, 1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(2, 2, 2, 2),
                0, 0
        ));
    }
}
