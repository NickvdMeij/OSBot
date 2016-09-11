package com.acescripts.scripts.overloadaio.gui;

import com.acescripts.scripts.overloadaio.OverloadAIO;
import com.acescripts.scripts.overloadaio.gui.panels.CookingPanel;
import com.acescripts.scripts.overloadaio.gui.panels.FiremakingPanel;
import com.acescripts.scripts.overloadaio.gui.panels.FishingPanel;
import com.acescripts.scripts.overloadaio.gui.panels.WoodcuttingPanel;
import com.acescripts.scripts.overloadaio.tutorialisland.TutorialIslandTask;
import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.script.Script;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.imageio.ImageIO;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class GUI extends JFrame {
    /**
     * MAIN.
     */
    public static JPanel contentPane;
    private JLabel skillHeaderLabel;

    /**
     * SKILL BUTTONS.
     */
    private JButton combatButton;
    private JButton rangedButton;
    private JButton prayerButton;
    private JButton magicButton;
    private JButton constructionButton;
    private JButton agilityButton;
    private JButton herbloreButton;
    private JButton thievingButton;
    private JButton runecraftingButton;
    private JButton craftingButton;
    private JButton smithingButton;
    private JButton miningButton;
    private JButton hunterButton;
    private JButton slayerButton;
    private JButton fletchingButton;
    private JButton woodcuttingButton;
    private JButton firemakingButton;
    private JButton cookingButton;
    private JButton fishingButton;
    private JButton farmingButton;
    private JButton questButton;
    private JButton minigameButton;

    /**
     * SKILL PANELS.
     */
    private WoodcuttingPanel woodcuttingPanel;
    private FiremakingPanel firemakingPanel;
    private FishingPanel fishingPanel;
    private CookingPanel cookingPanel;

    /**
     * TASK TABLE.
     */

    public static JTable table;
    public static DefaultTableModel model;

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

    private void deselectButtons() {
        combatButton.setSelected(false);
        rangedButton.setSelected(false);
        prayerButton.setSelected(false);
        magicButton.setSelected(false);
        constructionButton.setSelected(false);
        agilityButton.setSelected(false);
        herbloreButton.setSelected(false);
        thievingButton.setSelected(false);
        runecraftingButton.setSelected(false);
        craftingButton.setSelected(false);
        smithingButton.setSelected(false);
        miningButton.setSelected(false);
        hunterButton.setSelected(false);
        slayerButton.setSelected(false);
        fletchingButton.setSelected(false);
        woodcuttingButton.setSelected(false);
        firemakingButton.setSelected(false);
        cookingButton.setSelected(false);
        fishingButton.setSelected(false);
        farmingButton.setSelected(false);
        questButton.setSelected(false);
        minigameButton.setSelected(false);
    }

    private void setButtonOption(String skillName, JButton buttonName) {
        skillHeaderLabel.setText(skillName);
        //if(CombatPanel.panel != null && CombatPanel.panel.isVisible()) {
        //    CombatPanel.panel.setVisible(false);
        //}

        if(WoodcuttingPanel.panel != null && WoodcuttingPanel.panel.isVisible()) {
            WoodcuttingPanel.panel.setVisible(false);
        }

        if(FiremakingPanel.panel != null && FiremakingPanel.panel.isVisible()) {
            FiremakingPanel.panel.setVisible(false);
        }

        if(FishingPanel.panel != null && FishingPanel.panel.isVisible()) {
            FishingPanel.panel.setVisible(false);
        }

        if(CookingPanel.panel != null && CookingPanel.panel.isVisible()) {
            CookingPanel.panel.setVisible(false);
        }
        deselectButtons();
        buttonName.setSelected(true);
    }

    /**
     * Create the frame.
     */
    public GUI(Script script) throws IOException {
        setResizable(false);
        setTitle("Overload GUI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1450, 700);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        //Panel = new CombatPanel();
        combatButton = new JButton("");
        combatButton.setEnabled(false);
        //combatButton.addActionListener(arg0 -> {
        //    setButtonOption("COMBAT", combatButton);
        //    CombatPanel.panel.setVisible(true);
        //});
        loadImage("http://i.imgur.com/QiXZUFK.png", combatButton);
        combatButton.setBounds(12, 158, 40, 40);
        contentPane.add(combatButton);

        rangedButton = new JButton("");
        rangedButton.setEnabled(false);
        rangedButton.addActionListener(arg0 -> setButtonOption("RANGED", rangedButton));
        loadImage("http://i.imgur.com/5xvULO6.png", rangedButton);
        rangedButton.setBounds(53, 199, 40, 40);
        contentPane.add(rangedButton);

        prayerButton = new JButton("");
        prayerButton.setEnabled(false);
        prayerButton.addActionListener(arg0 -> setButtonOption("PRAYER", prayerButton));
        loadImage("http://i.imgur.com/qsMFGt3.png", prayerButton);
        prayerButton.setBounds(12, 240, 40, 40);
        contentPane.add(prayerButton);

        magicButton = new JButton("");
        magicButton.setEnabled(false);
        magicButton.addActionListener(arg0 -> setButtonOption("MAGIC", magicButton));
        loadImage("http://i.imgur.com/fFRWphQ.png", magicButton);
        magicButton.setBounds(12, 281, 40, 40);
        contentPane.add(magicButton);

        constructionButton = new JButton("");
        constructionButton.setEnabled(false);
        constructionButton.addActionListener(arg0 -> setButtonOption("CONSTRUCTION", constructionButton));
        loadImage("http://i.imgur.com/MUubyfQ.png", constructionButton);
        constructionButton.setBounds(53, 158, 40, 40);
        contentPane.add(constructionButton);

        agilityButton = new JButton("");
        agilityButton.setEnabled(false);
        agilityButton.addActionListener(arg0 -> setButtonOption("AGILITY", agilityButton));
        loadImage("http://i.imgur.com/LYXlhDL.png", agilityButton);
        agilityButton.setBounds(12, 199, 40, 40);
        contentPane.add(agilityButton);

        herbloreButton = new JButton("");
        herbloreButton.setEnabled(false);
        herbloreButton.addActionListener(arg0 -> setButtonOption("HERBLORE", herbloreButton));
        loadImage("http://i.imgur.com/Rdmwkhl.png", herbloreButton);
        herbloreButton.setBounds(53, 240, 40, 40);
        contentPane.add(herbloreButton);

        thievingButton = new JButton("");
        thievingButton.setEnabled(false);
        thievingButton.addActionListener(arg0 -> setButtonOption("THIEVING", thievingButton));
        loadImage("http://i.imgur.com/g5DciSF.png", thievingButton);
        thievingButton.setBounds(53, 281, 40, 40);
        contentPane.add(thievingButton);

        runecraftingButton = new JButton("");
        runecraftingButton.setEnabled(false);
        runecraftingButton.addActionListener(arg0 -> setButtonOption("RUNECRAFTING", runecraftingButton));
        loadImage("http://i.imgur.com/5nMyCeW.png", runecraftingButton);
        runecraftingButton.setBounds(12, 322, 40, 40);
        contentPane.add(runecraftingButton);

        craftingButton = new JButton("");
        craftingButton.setEnabled(false);
        craftingButton.addActionListener(arg0 -> setButtonOption("CRAFTING", craftingButton));
        loadImage("http://i.imgur.com/mrQI6D2.png", craftingButton);
        craftingButton.setBounds(53, 322, 40, 40);
        contentPane.add(craftingButton);

        smithingButton = new JButton("");
        smithingButton.setEnabled(false);
        smithingButton.addActionListener(arg0 -> setButtonOption("SMITHING", smithingButton));
        loadImage("http://i.imgur.com/NhziXZw.png", smithingButton);
        smithingButton.setBounds(94, 322, 40, 40);
        contentPane.add(smithingButton);

        miningButton = new JButton("");
        miningButton.setEnabled(false);
        miningButton.addActionListener(arg0 -> setButtonOption("MINING", miningButton));
        loadImage("http://i.imgur.com/22HR9Bi.png", miningButton);
        miningButton.setBounds(94, 281, 40, 40);
        contentPane.add(miningButton);

        hunterButton = new JButton("");
        hunterButton.setEnabled(false);
        hunterButton.addActionListener(arg0 -> setButtonOption("HUNTER", hunterButton));
        loadImage("http://i.imgur.com/eaIcB5K.png", hunterButton);
        hunterButton.setBounds(94, 240, 40, 40);
        contentPane.add(hunterButton);

        slayerButton = new JButton("");
        slayerButton.setEnabled(false);
        slayerButton.addActionListener(arg0 -> setButtonOption("SLAYER", slayerButton));
        loadImage("http://i.imgur.com/nFS9dB5.png", slayerButton);
        slayerButton.setBounds(94, 199, 40, 40);
        contentPane.add(slayerButton);

        fletchingButton = new JButton("");
        fletchingButton.setEnabled(false);
        fletchingButton.addActionListener(arg0 -> setButtonOption("FLETCHING", fletchingButton));
        loadImage("http://i.imgur.com/FC1ho3L.png", fletchingButton);
        fletchingButton.setBounds(94, 158, 40, 40);
        contentPane.add(fletchingButton);

        woodcuttingPanel = new WoodcuttingPanel(script);
        woodcuttingButton = new JButton("");
        woodcuttingButton.addActionListener(arg0 -> {
            setButtonOption("WOODCUTTING", woodcuttingButton);
            WoodcuttingPanel.panel.setVisible(true);
        });
        loadImage("http://i.imgur.com/HsA18aP.png", woodcuttingButton);
        woodcuttingButton.setBounds(135, 281, 40, 40);
        contentPane.add(woodcuttingButton);

        firemakingPanel = new FiremakingPanel(script);
        firemakingButton = new JButton("");
        firemakingButton.addActionListener(arg0 -> {
            setButtonOption("FIREMAKING", firemakingButton);
            FiremakingPanel.panel.setVisible(true);
        });
        loadImage("http://i.imgur.com/oIVSH1z.png", firemakingButton);
        firemakingButton.setBounds(135, 240, 40, 40);
        contentPane.add(firemakingButton);

        cookingPanel = new CookingPanel();
        cookingButton = new JButton("");
        cookingButton.addActionListener(arg0 -> {
            setButtonOption("COOKING", cookingButton);
            CookingPanel.panel.setVisible(true);
        });
        loadImage("http://i.imgur.com/PnLh0np.png", cookingButton);
        cookingButton.setBounds(135, 199, 40, 40);
        contentPane.add(cookingButton);

        fishingPanel = new FishingPanel();
        fishingButton = new JButton("");
        fishingButton.addActionListener(arg0 -> {
            setButtonOption("FISHING", fishingButton);
            FishingPanel.panel.setVisible(true);
        });
        loadImage("http://i.imgur.com/60dPW66.png", fishingButton);
        fishingButton.setBounds(135, 158, 40, 40);
        contentPane.add(fishingButton);

        farmingButton = new JButton("");
        farmingButton.setEnabled(false);
        farmingButton.addActionListener(arg0 -> setButtonOption("FARMING", farmingButton));
        loadImage("http://i.imgur.com/5dkmXz1.png", farmingButton);
        farmingButton.setBounds(135, 322, 40, 40);
        contentPane.add(farmingButton);

        questButton = new JButton("");
        questButton.setEnabled(false);
        questButton.addActionListener(arg0 -> setButtonOption("QUESTING", questButton));
        loadImage("http://i.imgur.com/ayOSrhp.png", questButton);
        questButton.setBounds(12, 363, 81, 40);
        contentPane.add(questButton);

        minigameButton = new JButton("");
        minigameButton.setEnabled(false);
        minigameButton.addActionListener(arg0 -> setButtonOption("MINIGAMES", minigameButton));
        loadImage("http://i.imgur.com/wj1GmYZ.png", minigameButton);
        minigameButton.setBounds(94, 363, 81, 40);
        contentPane.add(minigameButton);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(1066, 158, 354, 453);
        contentPane.add(scrollPane);

        model = new DefaultTableModel();
        table = new JTable(model) {
            @Override
            public boolean isCellEditable ( int row, int column ) {
                return false;
            }
        };

        ((DefaultTableCellRenderer)table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
        scrollPane.setViewportView(table);

        model.addColumn("Task Number");
        model.addColumn("Task Type");
        model.addColumn("Task Goal");

        table.setRowSelectionAllowed(true);
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setResizingAllowed(false);

        DefaultTableCellRenderer rightRenderer_c = new DefaultTableCellRenderer();
        rightRenderer_c.setHorizontalAlignment(JLabel.CENTER);

        for(int i = 0; i < table.getColumnCount(); i++){
            table.getColumnModel().getColumn(i).setCellRenderer(rightRenderer_c);
        }

        JButton startScriptButton = new JButton("START SCRIPT");
        startScriptButton.addActionListener(arg0 -> {
            Area tutorialIslandArea = new Area(3059, 3136, 3151, 3059);

            if(script.configs.get(406) < 20 || tutorialIslandArea.contains(script.myPosition())) {
                OverloadAIO.tasks.addFirst(new TutorialIslandTask(script));
            }
            OverloadAIO.guiWait = false;
            dispose();
        });
        startScriptButton.setBounds(12, 624, 1408, 25);
        contentPane.add(startScriptButton);

        JSeparator separator = new JSeparator();
        separator.setBounds(12, 143, 1408, 2);
        contentPane.add(separator);

        skillHeaderLabel = new JLabel("FISHING");
        skillHeaderLabel.setBounds(187, 158, 552, 25);
        contentPane.add(skillHeaderLabel);
        skillHeaderLabel.setFont(new Font("Tahoma", Font.BOLD, 25));

        JLabel loadSettingsLabel = new JLabel("Load Saved Settings");
        loadSettingsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        loadSettingsLabel.setBounds(12, 420, 163, 16);
        contentPane.add(loadSettingsLabel);

        JComboBox<String> loadSettingsComboBox = new JComboBox<>();
        loadSettingsComboBox.setBounds(12, 449, 163, 22);
        contentPane.add(loadSettingsComboBox);

        JButton loadSettingsButton = new JButton("Load Settings");
        loadSettingsButton.setBounds(12, 484, 163, 25);
        contentPane.add(loadSettingsButton);

        JLabel saveSettingsLabel = new JLabel("Save New Settings");
        saveSettingsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        saveSettingsLabel.setBounds(12, 522, 163, 16);
        contentPane.add(saveSettingsLabel);

        JButton saveSettingsButton = new JButton("Save Settings");
        saveSettingsButton.setBounds(12, 586, 163, 25);
        contentPane.add(saveSettingsButton);

        JTextField saveSettingsTextField = new JTextField();
        saveSettingsTextField.setHorizontalAlignment(SwingConstants.CENTER);
        saveSettingsTextField.setBounds(12, 551, 163, 22);
        contentPane.add(saveSettingsTextField);
        saveSettingsTextField.setColumns(10);

        JLabel scriptLogoLabel = new JLabel("");
        scriptLogoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        scriptLogoLabel.setBounds(12, 13, 1408, 117);
        loadMainImage("http://i.imgur.com/4NYnmpo.png", scriptLogoLabel);
        contentPane.add(scriptLogoLabel);
    }
}