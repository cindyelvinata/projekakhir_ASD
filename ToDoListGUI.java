import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ToDoListGUI {
    private JFrame frame;
    private DefaultTableModel tableModel;
    private JTable taskTable;
    private ArrayList<Task> tasks;
    private int tugasMaksimal;

    public ToDoListGUI() {
        tasks = new ArrayList<>();
        frame = new JFrame("To Do List Tugas");
        frame.setSize(600, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        String maxInputTugas = JOptionPane.showInputDialog(frame, "Masukkan jumlah tugas yang akan diurutkan");
        try {
            tugasMaksimal = Integer.parseInt(maxInputTugas.trim());
            if (tugasMaksimal <= 0) {
                JOptionPane.showMessageDialog(frame, "Jumlah tugas harus lebih dari 0. Program akan ditutup.");
                System.exit(0);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Input tidak valid. Program akan ditutup.");
            System.exit(0);
        }

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] namaKolom = {"Nama Tugas", "Prioritas", "Deadline"};
        tableModel = new DefaultTableModel(namaKolom, 0);
        taskTable = new JTable(tableModel);
        taskTable.setRowHeight(30);
        taskTable.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(taskTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel taskLabel = new JLabel("Nama Tugas:");
        taskLabel.setFont(new Font("Arial", Font.BOLD, 14));
        JTextField taskField = new JTextField(20);
        taskField.setFont(new Font("Arial", Font.PLAIN, 14));

        JLabel priorityLabel = new JLabel("Prioritas Tugas (1-4):");
        priorityLabel.setFont(new Font("Arial", Font.BOLD, 14));
        JTextField priorityField = new JTextField(20);
        priorityField.setFont(new Font("Arial", Font.PLAIN, 14));

        JLabel priorityDescLabel = new JLabel("<html><b>Keterangan Prioritas:</b><br>1: Sangat Penting<br>2: Penting<br>3: Sedang<br>4: Kurang Penting</html>");
        priorityDescLabel.setFont(new Font("Arial", Font.ITALIC, 12));

        JLabel deadlineLabel = new JLabel("Deadline (YYYY-MM-DD):");
        deadlineLabel.setFont(new Font("Arial", Font.BOLD, 14));
        JTextField deadlineField = new JTextField(20);
        deadlineField.setFont(new Font("Arial", Font.PLAIN, 14));

        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(taskLabel, gbc);
        gbc.gridx = 1;
        inputPanel.add(taskField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(priorityLabel, gbc);
        gbc.gridx = 1;
        inputPanel.add(priorityField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        inputPanel.add(priorityDescLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        inputPanel.add(deadlineLabel, gbc);
        gbc.gridx = 1;
        inputPanel.add(deadlineField, gbc);

        panel.add(inputPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        JButton addButton = new JButton("Tambah Tugas");
        JButton bubbleSortButton = new JButton("Urutkan Deadline");
        JButton selectionSortButton = new JButton("Urutkan Prioritas");

        addButton.setFont(new Font("Arial", Font.BOLD, 14));
        bubbleSortButton.setFont(new Font("Arial", Font.BOLD, 14));
        selectionSortButton.setFont(new Font("Arial", Font.BOLD, 14));

        buttonPanel.add(addButton);
        buttonPanel.add(bubbleSortButton);
        buttonPanel.add(selectionSortButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        frame.add(panel);

        addButton.addActionListener(e -> {
            if (tasks.size() >= tugasMaksimal) {
                JOptionPane.showMessageDialog(frame, "Jumlah tugas telah mencapai batas maksimal: " + tugasMaksimal);
                return;
            }
            String nama = taskField.getText().trim();
            int prioritas;
            String teksDeadline = deadlineField.getText().trim();
            try {
                if (nama.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Nama tugas tidak boleh kosong");
                    return;
                }
                prioritas = Integer.parseInt(priorityField.getText().trim());
                if (prioritas < 1 || prioritas > 4) {
                    JOptionPane.showMessageDialog(frame, "Prioritas harus antara 1-4");
                    return;
                }
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate deadline = LocalDate.parse(teksDeadline, formatter);
                tasks.add(new Task(nama, prioritas, deadline));
                tableModel.addRow(new Object[]{nama, prioritas, deadline});
                taskField.setText("");
                priorityField.setText("");
                deadlineField.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Prioritas tugas harus berupa angka");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Format tanggal tidak valid. Gunakan format: YYYY-MM-DD");
            }
        });

        bubbleSortButton.addActionListener(e -> {
            if (tasks.size() < tugasMaksimal) {
                JOptionPane.showMessageDialog(frame, "Jumlah tugas yang diinputkan kurang lengkap. Tambahkan tugas hingga mencapai " + tugasMaksimal + " tugas");
                return;
            }
            TaskSorter.bubblesortDeadline(tasks);
            updateTaskTable();
        });

        selectionSortButton.addActionListener(e -> {
            if (tasks.size() < tugasMaksimal) {
                JOptionPane.showMessageDialog(frame, "Jumlah tugas yang diinputkan kurang lengkap. Tambahkan tugas hingga mencapai " + tugasMaksimal + " tugas");
                return;
            }
            TaskSorter.selectionsortPrioritas(tasks);
            updateTaskTable();
        });

        frame.setVisible(true);
    }

    private void updateTaskTable() {
        tableModel.setRowCount(0);
        for (Task task : tasks) {
            tableModel.addRow(new Object[]{task.getNamaTugas(), task.getPrioritas(), task.getDeadline()});
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ToDoListGUI::new);
    }
}