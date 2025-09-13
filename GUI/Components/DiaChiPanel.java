package appgiaovan.GUI.Components;

import appgiaovan.Entity.QuanHuyen;
import appgiaovan.Entity.TinhThanh;
import appgiaovan.Entity.XaPhuong;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.awt.Color;
import java.awt.Dimension;
import java.io.File;
import java.util.*;
import java.util.stream.Collectors;
import javax.swing.*;

public class DiaChiPanel extends JPanel {

    private List<TinhThanh> tinhList;
    private Map<String, List<QuanHuyen>> quanByTinh;
    private Map<String, List<XaPhuong>> xaByQuan;

    private JComboBox<TinhThanh> cbTinh;
    private JComboBox<QuanHuyen> cbHuyen;
    private JComboBox<XaPhuong> cbXa;

    public DiaChiPanel() {
        setOpaque(true);
        setBackground(Color.WHITE);
        setLayout(null); 

        cbTinh = new JComboBox<>();
        cbTinh.setBorder(BorderFactory.createTitledBorder("Tỉnh/Thành *"));
        
        cbHuyen = new JComboBox<>();
        cbHuyen.setBorder(BorderFactory.createTitledBorder("Quận/Huyện *"));
        
        cbXa = new JComboBox<>();
        cbXa.setBorder(BorderFactory.createTitledBorder("Xã/Phường *"));

        cbTinh.setBounds(0, 0, 150, 50);
        cbHuyen.setBounds(160, 0, 150, 50);
        cbXa.setBounds(320, 0, 150, 50);

        add(cbTinh);
        add(cbHuyen);
        add(cbXa);

        setPreferredSize(new Dimension(480, 40));

        loadData();
        setupEvents();
    }

    private void loadData() {
        try {
            ObjectMapper mapper = new ObjectMapper();

            Map<String, TinhThanh> tinhMap = mapper.readValue(
                new File("asset/tinh_tp.json"),
                new com.fasterxml.jackson.core.type.TypeReference<Map<String, TinhThanh>>() {}
            );
            tinhList = new ArrayList<>(tinhMap.values());

            Map<String, QuanHuyen> huyenMap = mapper.readValue(
                new File("asset/quan_huyen.json"),
                new com.fasterxml.jackson.core.type.TypeReference<Map<String, QuanHuyen>>() {}
            );
            List<QuanHuyen> huyenList = new ArrayList<>(huyenMap.values());

            Map<String, XaPhuong> xaMap = mapper.readValue(
                new File("asset/xa_phuong.json"),
                new com.fasterxml.jackson.core.type.TypeReference<Map<String, XaPhuong>>() {}
            );
            List<XaPhuong> xaList = new ArrayList<>(xaMap.values());

            quanByTinh = huyenList.stream().collect(Collectors.groupingBy(q -> q.parent_code));
            xaByQuan = xaList.stream().collect(Collectors.groupingBy(x -> x.parent_code));

            for (TinhThanh t : tinhList) {
                cbTinh.addItem(t);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupEvents() {
        cbTinh.addActionListener(e -> {
            TinhThanh selectedTinh = (TinhThanh) cbTinh.getSelectedItem();
            cbHuyen.removeAllItems();
            cbXa.removeAllItems();
            if (selectedTinh != null) {
                List<QuanHuyen> huyen = quanByTinh.getOrDefault(selectedTinh.code, new ArrayList<>());
                for (QuanHuyen q : huyen) {
                    cbHuyen.addItem(q);
                }
            }
        });

        cbHuyen.addActionListener(e -> {
            QuanHuyen selectedHuyen = (QuanHuyen) cbHuyen.getSelectedItem();
            cbXa.removeAllItems();
            if (selectedHuyen != null) {
                List<XaPhuong> xa = xaByQuan.getOrDefault(selectedHuyen.code, new ArrayList<>());
                for (XaPhuong x : xa) {
                    cbXa.addItem(x);
                }
            }
        });
    }

    public String getSelectedTinh() {
        TinhThanh tinh = (TinhThanh) cbTinh.getSelectedItem();
        return tinh != null ? tinh.name : null;
    }

    public String getSelectedHuyen() {
        QuanHuyen huyen = (QuanHuyen) cbHuyen.getSelectedItem();
        return huyen != null ? huyen.name : null;
    }

    public String getSelectedXa() {
        XaPhuong xa = (XaPhuong) cbXa.getSelectedItem();
        return xa != null ? xa.name : null;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Test DiaChiPanel");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600, 200);
            frame.setLocationRelativeTo(null);

            DiaChiPanel dc = new DiaChiPanel();
            dc.setBounds(50, 50, 500, 40);
            dc.setLayout(null);

            JPanel wrapper = new JPanel(null);
            wrapper.setBackground(Color.WHITE);
            wrapper.add(dc);
            frame.add(wrapper);

            frame.setVisible(true);
        });
    }
}

