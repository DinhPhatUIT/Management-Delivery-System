package appgiaovan.GUI.Components;


import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.awt.*;

public class RoundedComboBox extends JComboBox<String> {

    static {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());

            UIManager.put("ComboBox.arc", 20); 
            UIManager.put("Component.arc", 20);

            UIManager.put("ComboBox.hoverBackground", new Color(220, 220, 255));
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }

    public RoundedComboBox(String... items) {
        super(items);
        setPreferredSize(new Dimension(200, 40));
        setOpaque(false);
        setFocusable(false);
    }
}
