package com.acescripts.scripts.overloadaio.paint;

/**
 * Created by Transporter on 03/08/2016 at 01:39.
 */

import org.osbot.rs07.script.Script;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class GUIPaint extends JFrame {
    private JLabel lblTimeRunning;
    private DefaultTableModel model;
    private JLabel lblStatus;

    private void loadMainImage(String fullUrlPath, JLabel buttonName) {
        try {
            URL url = new URL(fullUrlPath);
            BufferedImage img = ImageIO.read(url);
            ImageIcon icon = new ImageIcon(img);
            buttonName.setIcon(icon);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadImage(String fullUrlPath, JButton buttonName) {
        try {
            URL url = new URL(fullUrlPath);
            BufferedImage img = ImageIO.read(url);
            ImageIcon icon = new ImageIcon(img);
            buttonName.setIcon(icon);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setTimeRunning(String timeRan) {
        lblTimeRunning.setText(timeRan);
    }

    public void setStatus(String status) {
        lblStatus.setText(status);
    }

    public DefaultTableModel getModel() {
        return model;
    }

    /**
     * Create the frame.
     */
    public GUIPaint(Script script) {
        setResizable(false);
        setTitle("Overload Paint");
        setBounds(100, 100, 850, 570);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);// <- prevent closing
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel label = new JLabel("");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setBounds(12, 13, 808, 117);
        loadMainImage("http://i.imgur.com/4NYnmpo.png", label);
        contentPane.add(label);

        JSeparator separator = new JSeparator();
        separator.setBounds(12, 143, 808, 2);
        contentPane.add(separator);

        lblStatus = new JLabel("Status: Loading GUI...");
        lblStatus.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lblStatus.setBounds(12, 158, 390, 25);
        contentPane.add(lblStatus);

        lblTimeRunning = new JLabel("Time Running: 00:00:00:00");
        lblTimeRunning.setHorizontalAlignment(SwingConstants.RIGHT);
        lblTimeRunning.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lblTimeRunning.setBounds(430, 158, 390, 25);
        contentPane.add(lblTimeRunning);

        JSeparator separator_1 = new JSeparator();
        separator_1.setBounds(12, 196, 808, 2);
        contentPane.add(separator_1);

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setBounds(12, 211, 808, 299);
        contentPane.add(tabbedPane);

        JPanel panel = new JPanel();
        tabbedPane.addTab("Skills", null, panel, null);
        panel.setLayout(null);

        JButton button_22 = new JButton("");
        button_22.setBounds(12, 13, 30, 30);
        loadImage("http://i.imgur.com/nFx1MeY.png", button_22);
        panel.add(button_22);

        JButton button_23 = new JButton("");
        button_23.setBounds(43, 13, 30, 30);
        loadImage("http://i.imgur.com/P6TYuZp.png", button_23);
        panel.add(button_23);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(12, 56, 779, 200);
        panel.add(scrollPane);

        model = new DefaultTableModel();
        JTable table = new JTable(model) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        ((DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
        scrollPane.setViewportView(table);

        model.addColumn("Skill Name");
        model.addColumn("Current Level");
        model.addColumn("Levels Gained");
        model.addColumn("XP Gained");
        model.addColumn("XP/HR");
        model.addColumn("Time Until Level");

        table.setRowSelectionAllowed(true);
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setResizingAllowed(false);

        DefaultTableCellRenderer rightRenderer_c = new DefaultTableCellRenderer();
        rightRenderer_c.setHorizontalAlignment(JLabel.CENTER);

        //for cells :
        for(int i = 0; i < table.getColumnCount(); i++){
            table.getColumnModel().getColumn(i).setCellRenderer(rightRenderer_c);
        }

        JPanel panel_1 = new JPanel();
        tabbedPane.addTab("Tasks", null, panel_1, null);
        panel_1.setLayout(null);
    }
}

