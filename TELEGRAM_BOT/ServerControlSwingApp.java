import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ServerControlSwingApp extends JFrame {
    private Process pythonProcess;
    private JTextArea logArea;
    private JLabel statusLabel;
    private JButton toggleServerButton;
    private JLabel memoryLabel;
    private JLabel fileMemoryLabel;

    public ServerControlSwingApp() {
        setTitle("Server Control");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initComponents();
        updateMemoryUsage();
        updateFileMemoryUsage();
    }

    private void initComponents() {
        toggleServerButton = new JButton("Turn On Server");
        statusLabel = new JLabel("Status: Off");
        memoryLabel = new JLabel();
        fileMemoryLabel = new JLabel();

        logArea = new JTextArea();
        logArea.setEditable(false);
        logArea.setBackground(Color.BLACK);
        logArea.setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(logArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
        topPanel.add(statusLabel);
        topPanel.add(toggleServerButton);
        topPanel.add(memoryLabel);
        topPanel.add(fileMemoryLabel);

        toggleServerButton.addActionListener((ActionEvent e) -> {
            if (pythonProcess == null || !pythonProcess.isAlive()) {
                runPythonScript();
                toggleServerButton.setText("Turn Off Server");
                statusLabel.setText("Status: Live");
            } else {
                stopPythonScript();
                toggleServerButton.setText("Turn On Server");
                statusLabel.setText("Status: Off");
            }
            updateMemoryUsage();
            updateFileMemoryUsage();
        });

        getContentPane().add(topPanel, BorderLayout.NORTH);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
    }

    private void updateMemoryUsage() {
        Runtime runtime = Runtime.getRuntime();
        long usedMemory = (runtime.totalMemory() - runtime.freeMemory()) / (1024 * 1024);
        long totalMemory = runtime.totalMemory() / (1024 * 1024);
        memoryLabel.setText("Memory: " + usedMemory + " MB/" + totalMemory + " MB");
    }

    private void updateFileMemoryUsage() {
        Path filePath = Paths.get("user_data.csv");
        try {
            long fileSizeInBytes = Files.size(filePath);
            long fileSizeInKB = fileSizeInBytes / 1024;
            long fileSizeInMB = fileSizeInKB / 1024;
            fileMemoryLabel.setText(String.format("File size: %d bytes, %d KB, %d MB", fileSizeInBytes, fileSizeInKB, fileSizeInMB));
        } catch (IOException e) {
            fileMemoryLabel.setText("File not found");
        }
    }

    private void runPythonScript() {
        try {
            ProcessBuilder pb = new ProcessBuilder("python3", "mybot.py");
            pb.redirectErrorStream(true);  // Combine stdout and stderr into one stream
            pythonProcess = pb.start();
            new Thread(() -> {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(pythonProcess.getInputStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        final String finalLine = line;
                        SwingUtilities.invokeLater(() -> {
                            logArea.append(finalLine + "\n");
                            updateMemoryUsage();
                            updateFileMemoryUsage();
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void stopPythonScript() {
        if (pythonProcess != null) {
            pythonProcess.destroy();
            try {
                pythonProcess.waitFor();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            SwingUtilities.invokeLater(() -> {
                logArea.append("Python server stopped.\n");
                updateMemoryUsage();
                updateFileMemoryUsage();
            });
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ServerControlSwingApp().setVisible(true));
    }
}
