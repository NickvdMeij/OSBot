package com.acescripts.scripts.overloadaio.gui.panels;

import com.acescripts.scripts.overloadaio.OverloadAIO;
import com.acescripts.scripts.overloadaio.framework.Tree;
import com.acescripts.scripts.overloadaio.gui.GUI;
import com.acescripts.scripts.overloadaio.skills.woodcutting.WoodcuttingLocations;
import com.acescripts.scripts.overloadaio.skills.woodcutting.WoodcuttingTask;
import org.osbot.rs07.script.Script;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class WoodcuttingPanel extends JPanel {
    /**
     * WOODCUTTING MAIN OBJECTS
     */
    private static final long serialVersionUID = -4606875787974072904L;
    public static JPanel panel;
    private JButton addTaskButton;
    private boolean bankingEnabled;

    /**
     * MAIN OPTIONS
     */

    private JComboBox<String> taskTypeComboBox;
    private JLabel desiredGoalLabel;
    private JTextField desiredGoalTextField;
    private JComboBox<String> woodcuttingComboBox;
    private JComboBox<String> woodcuttingLocationComboBox;
    private JComboBox<String> treeTypeComboBox;

    /**
     * ENABLE / DISABLE ADD BUTTON
     */

    private void enableAddButton() {
        if(!desiredGoalTextField.getText().isEmpty() && !woodcuttingComboBox.getSelectedItem().equals("-- Choose --") && !woodcuttingLocationComboBox.getSelectedItem().equals("-- Choose --") && !treeTypeComboBox.getSelectedItem().equals("-- Choose --")) {
            addTaskButton.setEnabled(true);
        } else {
            addTaskButton.setEnabled(false);
        }
    }
    /**
     * Create the panel.
     */
    public WoodcuttingPanel(Script script) {
        panel = new JPanel();
        panel.setVisible(false);
        panel.setBounds(187, 195, 869, 418);
        GUI.contentPane.add(panel);
        panel.setLayout(null);

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setBounds(0, 0, 869, 377);
        panel.add(tabbedPane);

        JPanel mainOptionsPanel = new JPanel();
        tabbedPane.addTab("Main Options", null, mainOptionsPanel, null);
        mainOptionsPanel.setLayout(null);

        JLabel taskTypeLabel = new JLabel("Task Type:");
        taskTypeLabel.setBounds(12, 13, 170, 16);
        mainOptionsPanel.add(taskTypeLabel);


        String[] taskTypeBoxOptions = {"Level Task", "Log Count Task", "Timed Task"};
        DefaultComboBoxModel<String> taskTypeComboBoxModel = new DefaultComboBoxModel<>(taskTypeBoxOptions);

        taskTypeComboBox = new JComboBox<>(taskTypeComboBoxModel);
        taskTypeComboBox.addActionListener(e -> {
            if(taskTypeComboBox.getSelectedIndex() == 0) {
                desiredGoalLabel.setText("Desired Level:");
            } else if(taskTypeComboBox.getSelectedIndex() == 1) {
                desiredGoalLabel.setText("Desired Log Count:");
            } else {
                desiredGoalLabel.setText("Desired Time:");
            }
        });

        taskTypeComboBox.setBounds(194, 7, 250, 22);
        mainOptionsPanel.add(taskTypeComboBox);

        desiredGoalLabel = new JLabel("Desired Level:");
        desiredGoalLabel.setBounds(12, 42, 170, 16);
        mainOptionsPanel.add(desiredGoalLabel);

        desiredGoalTextField = new JTextField();
        desiredGoalTextField.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                updateAddButton();
                enableAddButton();
            }

            public void removeUpdate(DocumentEvent e) {
                updateAddButton();
                enableAddButton();
            }

            public void insertUpdate(DocumentEvent e) {
                updateAddButton();
                enableAddButton();
            }

            private void assitInputBox() {
                Runnable doAssist = () -> {
                    String input =  desiredGoalTextField.getText();

                    if(!input.matches("[0-9]+")) {
                        desiredGoalTextField.setText("");
                    }
                };
                SwingUtilities.invokeLater(doAssist);
            }

            private void updateAddButton() {
                if(!desiredGoalTextField.getText().equals("") && !desiredGoalTextField.getText().matches("[0-9]+")) {
                    assitInputBox();
                }
            }
        });
        desiredGoalTextField.setHorizontalAlignment(SwingConstants.CENTER);
        desiredGoalTextField.setBounds(194, 39, 250, 22);
        mainOptionsPanel.add(desiredGoalTextField);
        desiredGoalTextField.setColumns(10);

        JLabel choppingMethodLabel = new JLabel("Chopping Method:");
        choppingMethodLabel.setBounds(12, 74, 170, 16);
        mainOptionsPanel.add(choppingMethodLabel);

        String[] woodcuttingMethodOptions = {"-- Choose --", "BANK", "POWER_CHOP"};
        DefaultComboBoxModel<String> woodcuttingMethodComboBoxModel = new DefaultComboBoxModel<>(woodcuttingMethodOptions);

        woodcuttingComboBox = new JComboBox<>(woodcuttingMethodComboBoxModel);
        woodcuttingComboBox.addActionListener(e -> {
            enableAddButton();
            if(woodcuttingComboBox.getSelectedItem().equals("BANK")) {
                bankingEnabled = true;
            } else if(woodcuttingComboBox.getSelectedItem().equals("POWER_CHOP")) {
                bankingEnabled = false;
            }
        });
        woodcuttingComboBox.setBounds(194, 71, 250, 22);
        mainOptionsPanel.add(woodcuttingComboBox);

        JLabel wooducttingLocationlabel = new JLabel("Location:");
        wooducttingLocationlabel.setBounds(12, 106, 170, 16);
        mainOptionsPanel.add(wooducttingLocationlabel);

        String[] woodcuttingLocationsOptions = {"-- Choose --"};
        DefaultComboBoxModel<String> woodcuttingLocationsComboBoxModel = new DefaultComboBoxModel<>(woodcuttingLocationsOptions);

        woodcuttingLocationComboBox = new JComboBox<>(woodcuttingLocationsComboBoxModel);
        woodcuttingLocationComboBox.addActionListener(arg0 -> {
            if(!woodcuttingLocationComboBox.getSelectedItem().equals("-- Choose --")) {
                String[] woodcuttingLocationsOptions1 = WoodcuttingLocations.valueOf(woodcuttingLocationComboBox.getSelectedItem().toString()).getTreeNames();
                DefaultComboBoxModel<String> woodcuttingLocationsComboBoxModel1 = new DefaultComboBoxModel<>(woodcuttingLocationsOptions1);
                treeTypeComboBox.setModel(woodcuttingLocationsComboBoxModel1);
                treeTypeComboBox.setEnabled(true);
            } else {
                String[] woodcuttingLocationsOptions1 = {"-- Choose --"};
                DefaultComboBoxModel<String> woodcuttingLocationsComboBoxModel1 = new DefaultComboBoxModel<>(woodcuttingLocationsOptions1);
                treeTypeComboBox.setModel(woodcuttingLocationsComboBoxModel1);
                treeTypeComboBox.setEnabled(false);
            }
            enableAddButton();
        });
        woodcuttingLocationComboBox.setBounds(194, 103, 250, 22);

        for(WoodcuttingLocations treeLocations : WoodcuttingLocations.values()) {
            woodcuttingLocationComboBox.addItem(treeLocations.toString());
        }
        mainOptionsPanel.add(woodcuttingLocationComboBox);

        JLabel treeTypeLabel = new JLabel("Tree Type:");
        treeTypeLabel.setBounds(12, 138, 170, 16);
        mainOptionsPanel.add(treeTypeLabel);

        String[] treeTypeOptions = {"-- Choose --"};
        DefaultComboBoxModel<String> treeTypeComboBoxModel = new DefaultComboBoxModel<>(treeTypeOptions);

        treeTypeComboBox = new JComboBox<>(treeTypeComboBoxModel);
        treeTypeComboBox.addActionListener(e -> enableAddButton());
        treeTypeComboBox.setEnabled(false);
        treeTypeComboBox.setBounds(194, 135, 250, 22);
        mainOptionsPanel.add(treeTypeComboBox);

        addTaskButton = new JButton("ADD >>");
        addTaskButton.addActionListener(e -> {
            int taskNumber = GUI.table.getRowCount() + 1;
            String taskGoal = null;
            String taskType = null;

            if(taskTypeComboBox.getSelectedIndex() == 0) {
                taskType = "Level";
                taskGoal = desiredGoalTextField.getText() + " Woodcutting";
            } else if(taskTypeComboBox.getSelectedIndex() == 1) {
                taskType = "Log Count";
                taskGoal = desiredGoalTextField.getText() + " " + Tree.valueOf(treeTypeComboBox.getSelectedItem().toString()).getLogName();
            } else {
                taskType = "Timed";
                taskGoal = desiredGoalTextField.getText() + " Minutes";
            }
            GUI.model.addRow(new Object[]{taskNumber, taskType, taskGoal});
            OverloadAIO.tasks.add(new WoodcuttingTask(script,  Tree.valueOf(treeTypeComboBox.getSelectedItem().toString()).getTreeName(), Integer.parseInt(desiredGoalTextField.getText()), WoodcuttingLocations.valueOf(woodcuttingLocationComboBox.getSelectedItem().toString()).getArea(), bankingEnabled));

        });
        addTaskButton.setBounds(0, 392, 869, 25);
        addTaskButton.setEnabled(false);
        panel.add(addTaskButton);
    }
}