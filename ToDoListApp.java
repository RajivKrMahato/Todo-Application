import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class ToDoListApp extends JFrame {
    private DefaultListModel<String> taskListModel;
    private JList<String> taskList;
    private JTextField taskInput;

    public ToDoListApp() {
        setTitle("To-Do List");
        setSize(400, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        taskListModel = new DefaultListModel<>();
        taskList = new JList<>(taskListModel);
        JScrollPane scrollPane = new JScrollPane(taskList);

        taskInput = new JTextField();
        taskInput.addActionListener(e -> addTask());

        JButton addButton = new JButton("Add Task");
        JButton removeButton = new JButton("Remove Selected");
        JButton saveButton = new JButton("Save");
        JButton loadButton = new JButton("Load");

        addButton.addActionListener(e -> addTask());
        removeButton.addActionListener(e -> removeTask());
        saveButton.addActionListener(e -> saveTasks());
        loadButton.addActionListener(e -> loadTasks());

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 4));
        panel.add(addButton);
        panel.add(removeButton);
        panel.add(saveButton);
        panel.add(loadButton);

        add(taskInput, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(panel, BorderLayout.SOUTH);
    }

    private void addTask() {
        String task = taskInput.getText().trim();
        if (!task.isEmpty()) {
            taskListModel.addElement(task);
            taskInput.setText("");
        }
    }

    private void removeTask() {
        int selected = taskList.getSelectedIndex();
        if (selected != -1) {
            taskListModel.remove(selected);
        }
    }

    private void saveTasks() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("tasks.txt"))) {
            for (int i = 0; i < taskListModel.size(); i++) {
                writer.println(taskListModel.getElementAt(i));
            }
            JOptionPane.showMessageDialog(this, "Tasks saved successfully!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving tasks.");
        }
    }

    private void loadTasks() {
        try (BufferedReader reader = new BufferedReader(new FileReader("tasks.txt"))) {
            String line;
            taskListModel.clear();
            while ((line = reader.readLine()) != null) {
                taskListModel.addElement(line);
            }
            JOptionPane.showMessageDialog(this, "Tasks loaded successfully!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading tasks.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ToDoListApp().setVisible(true));
    }
}
