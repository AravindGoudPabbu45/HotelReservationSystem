package hotelmanagementsystem;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class CheckAvailability extends JFrame implements ActionListener {
    JTextField t1;
    JTextArea resultArea;
    HotelSystem hotel;
    MainFrame mainFrame;
    public CheckAvailability(HotelSystem hotel, MainFrame mainFrame) {
        this.hotel = hotel;
        this.mainFrame = mainFrame;
        setTitle("Check Availability");
        setSize(430, 300);
        setLayout(null);
        setLocationRelativeTo(mainFrame);
        setResizable(false);
        getContentPane().setBackground(new Color(245, 248, 250));
        JLabel heading = new JLabel("Check Room Availability", JLabel.CENTER);
        heading.setFont(new Font("Arial", Font.BOLD, 16));
        heading.setForeground(new Color(30, 60, 114));
        heading.setBounds(30, 10, 360, 25);
        add(heading);
        JLabel l1 = new JLabel("Enter Room ID:");
        l1.setBounds(30, 50, 120, 25);
        add(l1);
        t1 = new JTextField();
        t1.setBounds(160, 50, 210, 25);
        add(t1);
        JButton btnCheck = new JButton("Check");
        btnCheck.setBounds(160, 90, 120, 35);
        btnCheck.setBackground(new Color(41, 128, 185));
        btnCheck.setForeground(Color.WHITE);
        btnCheck.setFocusPainted(false);
        btnCheck.addActionListener(this);
        add(btnCheck);
        resultArea = new JTextArea("Result will appear here...");
        resultArea.setBounds(30, 140, 360, 100);
        resultArea.setEditable(false);
        resultArea.setFocusable(false);
        resultArea.setFont(new Font("Arial", Font.PLAIN, 13));
        resultArea.setBackground(new Color(236, 240, 241));
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
        add(resultArea);
        setVisible(true);
    }
    public void actionPerformed(ActionEvent ae) {
        if (t1.getText().trim().isEmpty()) {
            resultArea.setText("Please enter a Room ID!");
            return;
        }
        try {
            int roomId = Integer.parseInt(t1.getText().trim());
            String result = hotel.checkAvailability(roomId);
            resultArea.setText(result);
            mainFrame.updateStatus("Checked availability for Room " + roomId);
        } catch (NumberFormatException ex) {
            resultArea.setText("Invalid input! Please enter a numeric Room ID.");
        } catch (Exception ex) {
            resultArea.setText("Error: " + ex.getMessage());
        }
    }
}
