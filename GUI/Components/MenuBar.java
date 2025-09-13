package appgiaovan.GUI.Components;

import appgiaovan.DAO.TaiKhoanDAO;
import appgiaovan.Entity.TaiKhoan;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MenuBar extends JPanel {

    private JLabel activeLabel = null;
    private JLabel nameLabel;
    private JLabel titleLabel;
    private final Color DEFAULT_BG = new Color(4, 36, 74);
    private final Color HOVER_BG = new Color(30, 60, 100);
    private final List<JLabel> labels = new ArrayList<>();

    public MenuBar(List<String> itemNames, List<String> iconNames, int idtk) throws SQLException, ClassNotFoundException {
        setBackground(DEFAULT_BG);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(180, 700));  

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(DEFAULT_BG);
        mainPanel.add(createLogoSection());
        mainPanel.add(createSeparator());
        mainPanel.add(setupProfileSection(idtk));
        mainPanel.add(createSeparator());

        for (int i = 0; i < itemNames.size(); i++) {
            JLabel label = createMenuItem(itemNames.get(i), iconNames.get(i));
            labels.add(label);
            mainPanel.add(label);
        }

        mainPanel.add(Box.createVerticalGlue()); 
        add(mainPanel, BorderLayout.CENTER);

    }

    private JLabel createMenuItem(String text, String iconName) {
        JLabel label = new JLabel("  " + text);
        label.setForeground(Color.WHITE);

        URL imageUrl = getClass().getResource("/images/icons/" + iconName);
        if (imageUrl != null) {
            ImageIcon originalIcon = new ImageIcon(imageUrl);
            Image scaledImage = originalIcon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
            label.setIcon(new ImageIcon(scaledImage));
        }

        label.setPreferredSize(new Dimension(180, 40)); 
        label.setMaximumSize(new Dimension(180, 40));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        label.setOpaque(true);
        label.setBackground(DEFAULT_BG);
        label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        label.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));

        label.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                setActiveLabel(label);
                if (listener != null) {
                    listener.onMenuClick(text); 
                }
            }

            public void mouseEntered(MouseEvent e) {
                if (label != activeLabel) {
                    label.setBackground(HOVER_BG);
                }
            }

            public void mouseExited(MouseEvent e) {
                if (label != activeLabel) {
                    label.setBackground(DEFAULT_BG);
                }
            }
        });

        return label;
    }

    private void animateBackground(JLabel label, Color start, Color end) {
        final int steps = 15;
        final int delay = 20; 
        Timer timer = new Timer(delay, null);
        final int[] count = {0};

        timer.addActionListener(e -> {
            float ratio = count[0] / (float) steps;

            int r = (int) (start.getRed() + ratio * (end.getRed() - start.getRed()));
            int g = (int) (start.getGreen() + ratio * (end.getGreen() - start.getGreen()));
            int b = (int) (start.getBlue() + ratio * (end.getBlue() - start.getBlue()));

            label.setBackground(new Color(r, g, b));
            count[0]++;

            if (count[0] > steps) {
                label.setBackground(end); 
                ((Timer) e.getSource()).stop();
            }
        });

        timer.start();
    }

    private void setActiveLabel(JLabel label) {
        if (activeLabel != null) {
            activeLabel.setFont(activeLabel.getFont().deriveFont(Font.PLAIN));
            activeLabel.setForeground(Color.WHITE);
            animateBackground(activeLabel, activeLabel.getBackground(), DEFAULT_BG);
        }

        activeLabel = label;
        activeLabel.setFont(label.getFont().deriveFont(Font.BOLD));
        activeLabel.setForeground(Color.WHITE);

        Color targetColor = new Color(255, 140, 0); 
        animateBackground(activeLabel, activeLabel.getBackground(), targetColor);
    }

    private JPanel setupProfileSection(int idtk) throws SQLException, ClassNotFoundException {
        JPanel profilePanel = new JPanel();
        profilePanel.setLayout(new BoxLayout(profilePanel, BoxLayout.Y_AXIS));
        profilePanel.setBackground(DEFAULT_BG);
        profilePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        profilePanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 0)); // lề

        JLabel avatarLabel = new JLabel();
        URL imageUrl = getClass().getResource("/images/avatar.png");
        if (imageUrl != null) {
            ImageIcon originalIcon = new ImageIcon(imageUrl);
            Image scaledImage = originalIcon.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
            avatarLabel.setIcon(new ImageIcon(scaledImage));
            avatarLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        } else {
            avatarLabel.setText("Ảnh");
            avatarLabel.setForeground(Color.WHITE);
        }

        avatarLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        avatarLabel.setOpaque(true);
        avatarLabel.setBackground(DEFAULT_BG);
        avatarLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // Sự kiện khi di chuột
        avatarLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (avatarLabel != activeLabel) {
                    avatarLabel.setBackground(HOVER_BG);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (avatarLabel != activeLabel) {
                    avatarLabel.setBackground(DEFAULT_BG);
                }
            }

        });
        
        
        TaiKhoan tk = new TaiKhoanDAO().LayThongTinTaiKhoan(idtk);
        System.out.println(tk.getTenNguoiDung());

        nameLabel = new JLabel(tk.getTenNguoiDung());
        nameLabel.setForeground(Color.WHITE);
        System.out.println(tk.getTenTaiKhoan());
        titleLabel = new JLabel(tk.getVaiTro());
        titleLabel.setForeground(Color.LIGHT_GRAY);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        profilePanel.add(avatarLabel);
        profilePanel.add(Box.createVerticalStrut(5));
        profilePanel.add(nameLabel);
        profilePanel.add(titleLabel);
        profilePanel.add(Box.createVerticalStrut(10));

        return profilePanel;
    }

    public interface MenuClickListener {

        void onMenuClick(String menuName);
    }
    private MenuClickListener listener;

    public void addMenuClickListener(MenuClickListener listener) {
        this.listener = listener;
    }

    private Component createSeparator() {
        JSeparator separator = new JSeparator();
        separator.setMaximumSize(new Dimension(185, 1));  
        separator.setAlignmentX(Component.LEFT_ALIGNMENT);
        separator.setForeground(new Color(200, 200, 200));  
        return separator;
    }

    private Component createLogoSection() {
        JPanel logoPanel = new JPanel();
        logoPanel.setBackground(DEFAULT_BG);
        logoPanel.setLayout(new BoxLayout(logoPanel, BoxLayout.Y_AXIS));
        logoPanel.setMaximumSize(new Dimension(200, 80));
        logoPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel logoLabel = new JLabel();
        logoLabel.setAlignmentX(Component.LEFT_ALIGNMENT); // căn trái
        logoLabel.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0)); // thêm lề trái 15px

        URL logoUrl = getClass().getResource("/images/logo3p1n.png");
        System.out.println("Logo URL: " + logoUrl);

        if (logoUrl != null) {
            ImageIcon originalIcon = new ImageIcon(logoUrl);
            Image scaledImage = originalIcon.getImage().getScaledInstance(100, 78, Image.SCALE_SMOOTH);
            logoLabel.setIcon(new ImageIcon(scaledImage));
        } else {
            logoLabel.setText("LOGO");
            logoLabel.setForeground(Color.WHITE);
            logoLabel.setFont(new Font("Arial", Font.BOLD, 16));
        }

        logoPanel.add(Box.createVerticalStrut(20));
        logoPanel.add(logoLabel);
        logoPanel.add(Box.createVerticalStrut(10));

        return logoPanel;
    }

    //  Hàm main để test trực tiếp MenuBar với kích thước như bạn yêu cầu
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Test MenuBar");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1200, 700); // Kích thước của JFrame
            frame.setLocationRelativeTo(null);

            List<String> items = Arrays.asList("Quản lý đơn hàng", "Báo cáo", "Hỗ trợ", "Đăng xuất");
            List<String> icons = Arrays.asList("order.png", "report.png", "support.png", "logout.png");

            MenuBar menu = null;
            try {
                menu = new MenuBar(items, icons, 6);
            } catch (SQLException ex) {
                Logger.getLogger(MenuBar.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(MenuBar.class.getName()).log(Level.SEVERE, null, ex);
            }
            frame.setLayout(new BorderLayout());
            frame.add(menu, BorderLayout.WEST);  // Menu sẽ được đặt ở bên trái

            frame.setVisible(true);
        });
    }
}
