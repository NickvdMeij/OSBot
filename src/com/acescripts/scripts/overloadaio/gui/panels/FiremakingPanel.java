package com.acescripts.scripts.overloadaio.gui.panels;

import com.acescripts.scripts.overloadaio.OverloadAIO;
import com.acescripts.scripts.overloadaio.framework.Tree;
import com.acescripts.scripts.overloadaio.gui.GUI;
import com.acescripts.scripts.overloadaio.skills.firemaking.FirePath;
import com.acescripts.scripts.overloadaio.skills.firemaking.FirePathData;
import com.acescripts.scripts.overloadaio.skills.firemaking.FiremakingLocations;
import com.acescripts.scripts.overloadaio.skills.firemaking.FiremakingTask;
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
import java.util.ArrayList;

public class FiremakingPanel extends JPanel {
    /**
     * FIREMAKING MAIN OBJECTS
     */
    private static final long serialVersionUID = -4606875787974072904L;
    public static JPanel panel;
    private JButton addTaskButton;

    /**
     * MAIN OPTIONS
     */

    private JComboBox<String> taskTypeComboBox;
    private JLabel desiredGoalLabel;
    private JTextField desiredGoalTextField;
    private JComboBox<String> firemakingLocationComboBox;
    private JComboBox<String> logTypeComboBox;

    /**
     * ENABLE / DISABLE ADD BUTTON
     */

    private void enableAddButton() {
        if(!desiredGoalTextField.getText().isEmpty() && !firemakingLocationComboBox.getSelectedItem().equals("-- Choose --") && !logTypeComboBox.getSelectedItem().equals("-- Choose --")) {
            addTaskButton.setEnabled(true);
        } else {
            addTaskButton.setEnabled(false);
        }
    }

    private ArrayList<FirePath> getPaths (Script script, String townName) {
        ArrayList<FirePath> paths = new ArrayList<>();

        paths.add(0, new FirePath(script, FirePathData.valueOf(townName).getStartTileOne(), FirePathData.valueOf(townName).getEndTileOne()));
        paths.add(1, new FirePath(script, FirePathData.valueOf(townName).getStartTileTwo(), FirePathData.valueOf(townName).getEndTileTwo()));
        paths.add(2, new FirePath(script, FirePathData.valueOf(townName).getStartTileThree(), FirePathData.valueOf(townName).getEndTileThree()));
        paths.add(3, new FirePath(script, FirePathData.valueOf(townName).getStartTileFour(), FirePathData.valueOf(townName).getEndTileFour()));
        return paths;
    }

    /**
     * Create the panel.
     */
    public FiremakingPanel(Script script) {
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

        String[] taskTypeBoxOptions = {"Level Task", "Fire Count Task", "Timed Task"};
        DefaultComboBoxModel<String> taskTypeComboBoxModel = new DefaultComboBoxModel<>(taskTypeBoxOptions);

        taskTypeComboBox = new JComboBox<>(taskTypeComboBoxModel);
        taskTypeComboBox.addActionListener(e -> {
            if(taskTypeComboBox.getSelectedIndex() == 0) {
                desiredGoalLabel.setText("Desired Level:");
            } else if(taskTypeComboBox.getSelectedIndex() == 1) {
                desiredGoalLabel.setText("Desired Fire Count:");
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
                    String input = desiredGoalTextField.getText();

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

        JLabel firemakingLocationLabel = new JLabel("Location:");
        firemakingLocationLabel.setBounds(12, 74, 170, 16);
        mainOptionsPanel.add(firemakingLocationLabel);

        String[] firemakingLocationsOptions = {"-- Choose --"};
        DefaultComboBoxModel<String> firemakingLocationsComboBoxModel = new DefaultComboBoxModel<>(firemakingLocationsOptions);

        firemakingLocationComboBox = new JComboBox<>(firemakingLocationsComboBoxModel);
        firemakingLocationComboBox.addActionListener(e -> enableAddButton());
        firemakingLocationComboBox.setBounds(194, 71, 250, 22);

        for(FiremakingLocations fireLocations : FiremakingLocations.values()) {
            firemakingLocationComboBox.addItem(fireLocations.toString());
        }
        mainOptionsPanel.add(firemakingLocationComboBox);

        JLabel logTypeLabel = new JLabel("Log Type:");
        logTypeLabel.setBounds(12, 106, 170, 16);
        mainOptionsPanel.add(logTypeLabel);

        String[] logTypeOptions = {"-- Choose --"};
        DefaultComboBoxModel<String> logTypeComboBoxModel = new DefaultComboBoxModel<>(logTypeOptions);

        logTypeComboBox = new JComboBox<>(logTypeComboBoxModel);
        logTypeComboBox.addActionListener(e -> enableAddButton());
        logTypeComboBox.setBounds(194, 103, 250, 22);

        for(Tree logType : Tree.values()) {
            logTypeComboBox.addItem(logType.getLogName());
        }
        mainOptionsPanel.add(logTypeComboBox);

        addTaskButton = new JButton("ADD >>");
        addTaskButton.addActionListener(e -> {
            int taskNumber = GUI.table.getRowCount() + 1;
            String taskGoal = null;
            String taskType = null;

            if(taskTypeComboBox.getSelectedIndex() == 0) {
                taskType = "Level";
                taskGoal = desiredGoalTextField.getText() + " Firemaking";
            } else if(taskTypeComboBox.getSelectedIndex() == 1) {
                taskType = "Fire Count";
                taskGoal = desiredGoalTextField.getText() + " Fires";
            } else {
                taskType = "Timed";
                taskGoal = desiredGoalTextField.getText() + " Minutes";
            }
            GUI.model.addRow(new Object[]{taskNumber, taskType, taskGoal});
            OverloadAIO.tasks.add(new FiremakingTask(script, logTypeComboBox.getSelectedItem().toString(), Integer.parseInt(desiredGoalTextField.getText()), FiremakingLocations.valueOf(firemakingLocationComboBox.getSelectedItem().toString()).getArea(), getPaths(script, firemakingLocationComboBox.getSelectedItem().toString())));

        });
        addTaskButton.setBounds(0, 392, 869, 25);
        addTaskButton.setEnabled(false);
        panel.add(addTaskButton);
    }
}