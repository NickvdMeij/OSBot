package com.acescripts.scripts.overloadaio.gui.panels;

import com.acescripts.scripts.overloadaio.OverloadAIO;
import com.acescripts.scripts.overloadaio.skills.fishing.FishingLocations;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class FishingPanel extends JPanel {
    /**
     * FISHING MAIN OBJECTS
     */
    private static final long serialVersionUID = -4606875787974072904L;
    private JPanel panel;
    private JButton addTaskButton;

    /**
     * MAIN OPTIONS
     */

    private JComboBox<String> taskTypeComboBox;
    private JLabel desiredGoalLabel;
    private JTextField desiredGoalTextField;
    private JComboBox<String> fishingMethodComboBox;
    private JComboBox<String> fishingLocationComboBox;
    private JComboBox<String> fishTypeComboBox;

    /**
     * ENABLE / DISABLE ADD BUTTON
     */

    private void enableAddButton() {
        if(!desiredGoalTextField.getText().isEmpty() && !fishingMethodComboBox.getSelectedItem().equals("-- Choose --") && !fishingLocationComboBox.getSelectedItem().equals("-- Choose --") && !fishTypeComboBox.getSelectedItem().equals("-- Choose --")) {
            addTaskButton.setEnabled(true);
        } else {
            addTaskButton.setEnabled(false);
        }
    }

    /**
     * Create the panel.
     */
    public FishingPanel(OverloadAIO script) {
        panel = new JPanel();
        panel.setVisible(true);
        panel.setBounds(187, 195, 869, 418);
        script.getGui().contentPane.add(panel);
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

        String[] taskTypeBoxOptions = {"Level Task", "Fish Count Task", "Timed Task"};
        DefaultComboBoxModel<String> taskTypeComboBoxModel = new DefaultComboBoxModel<>(taskTypeBoxOptions);
        taskTypeComboBox = new JComboBox<>(taskTypeComboBoxModel);
        taskTypeComboBox.addActionListener(e -> {
            if(taskTypeComboBox.getSelectedIndex() == 0) {
                desiredGoalLabel.setText("Desired Level:");
            } else if(taskTypeComboBox.getSelectedIndex() == 1) {
                desiredGoalLabel.setText("Desired Fish Count:");
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

        JLabel fishingMethodLabel = new JLabel("Fishing Method:");
        fishingMethodLabel.setBounds(12, 74, 170, 16);
        mainOptionsPanel.add(fishingMethodLabel);

        String[] fishingMethodOptions = {"-- Choose --", "BANK", "POWER_FISH"};
        DefaultComboBoxModel<String> fishingMethodComboBoxModel = new DefaultComboBoxModel<>(fishingMethodOptions);
        fishingMethodComboBox = new JComboBox<>(fishingMethodComboBoxModel);
        fishingMethodComboBox.addActionListener(e -> enableAddButton());
        fishingMethodComboBox.setBounds(194, 71, 250, 22);
        mainOptionsPanel.add(fishingMethodComboBox);

        JLabel fishingLocationLabel = new JLabel("Location:");
        fishingLocationLabel.setBounds(12, 106, 170, 16);
        mainOptionsPanel.add(fishingLocationLabel);

        String[] fishingLocationsOptions = {"-- Choose --"};
        DefaultComboBoxModel<String> fishingLocationsComboBoxModel = new DefaultComboBoxModel<>(fishingLocationsOptions);

        fishingLocationComboBox = new JComboBox<>(fishingLocationsComboBoxModel);
        fishingLocationComboBox.addActionListener(arg0 -> {
            if(!fishingLocationComboBox.getSelectedItem().equals("-- Choose --")) {
                String[] fishingLocationsOptions1 = FishingLocations.valueOf(fishingLocationComboBox.getSelectedItem().toString()).getFishNames();
                DefaultComboBoxModel<String> fishingLocationsComboBoxModel1 = new DefaultComboBoxModel<>(fishingLocationsOptions1);
                fishTypeComboBox.setModel(fishingLocationsComboBoxModel1);
                fishTypeComboBox.setEnabled(true);
            } else {
                String[] fishingLocationsOptions1 = {"-- Choose --"};
                DefaultComboBoxModel<String> fishingLocationsComboBoxModel1 = new DefaultComboBoxModel<>(fishingLocationsOptions1);
                fishTypeComboBox.setModel(fishingLocationsComboBoxModel1);
                fishTypeComboBox.setEnabled(false);
            }
            enableAddButton();
        });
        fishingLocationComboBox.setBounds(194, 103, 250, 22);

        for(FishingLocations fishLocations : FishingLocations.values()) {
            fishingLocationComboBox.addItem(fishLocations.toString());
        }
        mainOptionsPanel.add(fishingLocationComboBox);

        JLabel fishTypeLabel = new JLabel("Fish Type:");
        fishTypeLabel.setBounds(12, 138, 170, 16);
        mainOptionsPanel.add(fishTypeLabel);

        String[] fishTypeOptions = {"-- Choose --"};
        DefaultComboBoxModel<String> fishTypeComboBoxModel = new DefaultComboBoxModel<>(fishTypeOptions);

        fishTypeComboBox = new JComboBox<>(fishTypeComboBoxModel);
        fishTypeComboBox.addActionListener(e -> enableAddButton());
        fishTypeComboBox.setEnabled(false);
        fishTypeComboBox.setBounds(194, 135, 250, 22);
        mainOptionsPanel.add(fishTypeComboBox);

        addTaskButton = new JButton("ADD >>");
        addTaskButton.addActionListener(e -> {
            int taskNumber = script.getGui().table.getRowCount() + 1;
            String taskGoal = null;
            String taskType = null;

            if(taskTypeComboBox.getSelectedIndex() == 0) {
                taskType = "Level";
                taskGoal = desiredGoalTextField.getText() + " Fishing";
            } else if(taskTypeComboBox.getSelectedIndex() == 1) {
                taskType = "Fish Count";
                taskGoal = desiredGoalTextField.getText() + " " + fishTypeComboBox.getSelectedItem().toString();
            } else {
                taskType = "Timed";
                taskGoal = desiredGoalTextField.getText() + " Minutes";
            }
            script.getGui().model.addRow(new Object[]{taskNumber, taskType, taskGoal});
            //ADD FISHING TASK TO TASKS QUEUE
        });
        addTaskButton.setBounds(0, 392, 869, 25);
        addTaskButton.setEnabled(false);
        panel.add(addTaskButton);
    }

    public JPanel getPanel() {
        return panel;
    }
}
