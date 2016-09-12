package com.acescripts.scripts.overloadaio.gui.panels;

import com.acescripts.scripts.overloadaio.OverloadAIO;
import com.acescripts.scripts.overloadaio.skills.cooking.CookingLocations;
import com.acescripts.scripts.overloadaio.skills.cooking.Food;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class CookingPanel extends JPanel {
    /**
     * COOKING MAIN OBJECTS
     */

    private static final long serialVersionUID = -4606875787974072904L;
    private  JPanel panel;
    private JButton addTaskButton;

    /**
     * MAIN OPTIONS
     */

    private JComboBox<String> taskTypeComboBox;
    private JLabel desiredGoalLabel;
    private JTextField desiredGoalTextField;
    private JComboBox<String> cookingLocationComboBox;
    private JComboBox<String> foodTypeComboBox;

    /**
     * ENABLE / DISABLE ADD BUTTON
     */

    private void enableAddButton() {
        if(!desiredGoalTextField.getText().isEmpty() && !cookingLocationComboBox.getSelectedItem().equals("-- Choose --") && !foodTypeComboBox.getSelectedItem().equals("-- Choose --")) {
            addTaskButton.setEnabled(true);
        } else {
            addTaskButton.setEnabled(false);
        }
    }
    /**
     * Create the panel.
     */
    public CookingPanel(OverloadAIO script) {
        panel = new JPanel();
        panel.setVisible(false);
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

        String[] taskTypeBoxOptions = {"Level Task", "Cooked Count Task", "Timed Task"};
        DefaultComboBoxModel<String> taskTypeComboBoxModel = new DefaultComboBoxModel<>(taskTypeBoxOptions);
        taskTypeComboBox = new JComboBox<>(taskTypeComboBoxModel);
        taskTypeComboBox.addActionListener(e -> {
            if(taskTypeComboBox.getSelectedIndex() == 0) {
                desiredGoalLabel.setText("Desired Level:");
            } else if(taskTypeComboBox.getSelectedIndex() == 1) {
                desiredGoalLabel.setText("Desired Cooked Amount:");
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

        JLabel cookingLocationLabel = new JLabel("Location:");
        cookingLocationLabel.setBounds(12, 74, 170, 16);
        mainOptionsPanel.add(cookingLocationLabel);

        String[] cookingLocationsOptions = {"-- Choose --"};
        DefaultComboBoxModel<String> cookingLocationsComboBoxModel = new DefaultComboBoxModel<>(cookingLocationsOptions);

        cookingLocationComboBox = new JComboBox<>(cookingLocationsComboBoxModel);
        cookingLocationComboBox.addActionListener(e -> enableAddButton());
        cookingLocationComboBox.setBounds(194, 71, 250, 22);

        for(CookingLocations cookingLocations : CookingLocations.values()) {
            cookingLocationComboBox.addItem(cookingLocations.toString());
        }
        mainOptionsPanel.add(cookingLocationComboBox);

        JLabel foodTypeLabel = new JLabel("Food Type:");
        foodTypeLabel.setBounds(12, 106, 170, 16);
        mainOptionsPanel.add(foodTypeLabel);

        String[] foodTypeOptions = {"-- Choose --"};
        DefaultComboBoxModel<String> foodTypeComboBoxModel = new DefaultComboBoxModel<>(foodTypeOptions);
        foodTypeComboBox = new JComboBox<>(foodTypeComboBoxModel);
        foodTypeComboBox.addActionListener(e -> enableAddButton());
        foodTypeComboBox.setBounds(194, 103, 250, 22);

        for(Food foodType : Food.values()) {
            foodTypeComboBox.addItem(foodType.getFoodName());
        }
        mainOptionsPanel.add(foodTypeComboBox);

        addTaskButton = new JButton("ADD >>");
        addTaskButton.addActionListener(e -> {
            int taskNumber = script.getGui().table.getRowCount() + 1;
            String taskGoal = null;
            String taskType = null;

            if(taskTypeComboBox.getSelectedIndex() == 0) {
                taskType = "Level";
                taskGoal = desiredGoalTextField.getText() + " Cooking";
            } else if(taskTypeComboBox.getSelectedIndex() == 1) {
                taskType = "Cooked Count";
                taskGoal = desiredGoalTextField.getText() + " Cooked " + foodTypeComboBox.getSelectedItem().toString();
            } else {
                taskType = "Timed";
                taskGoal = desiredGoalTextField.getText() + " Minutes";
            }
            script.getGui().model.addRow(new Object[]{taskNumber, taskType, taskGoal});
            //ADD COOKING TASK TO TASKS QUEUE
        });
        addTaskButton.setBounds(0, 392, 869, 25);
        addTaskButton.setEnabled(false);
        panel.add(addTaskButton);
    }

    public JPanel getPanel() {
        return panel;
    }
}
