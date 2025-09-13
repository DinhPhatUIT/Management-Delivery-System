package appgiaovan.GUI.Components;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeWeather extends JPanel {
    private final JLabel timeLabel;
    private final JLabel weatherLabel;

    public TimeWeather(String weatherText) {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        timeLabel = new JLabel();
        timeLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        add(timeLabel, BorderLayout.WEST);

        weatherLabel = new JLabel(weatherText);
        weatherLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        weatherLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        add(weatherLabel, BorderLayout.EAST);

        new Timer(1000, e -> updateTime()).start();
        updateTime();
    }

    private void updateTime() {
        timeLabel.setText(
          LocalDateTime.now()
            .format(DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy"))
        );
    }

    public void setWeatherText(String text) {
        weatherLabel.setText(text);
    }
}
