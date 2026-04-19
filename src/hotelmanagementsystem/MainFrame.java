package hotelmanagementsystem;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class MainFrame extends JFrame implements ActionListener {
    JButton btnAddRoom, btnCheckAvail, btnMakeRes, btnConfirmRes;
    JButton btnCheckout, btnLastCheckout, btnExit;
    HotelSystem hotel;
    JLabel statusLabel;
    MainFrame() {
        hotel = new HotelSystem();
        setTitle("Hotel Reservation System");
        setSize(500, 650);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(new Color(240, 244, 248));
        // Title
        JLabel titleLabel = new JLabel("Hotel Reservation System", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(30, 60, 114));
        titleLabel.setBounds(50, 25, 400, 35);
        add(titleLabel);
        JLabel subLabel = new JLabel("Java Swing + Oracle JDBC", JLabel.CENTER);
        subLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        subLabel.setForeground(new Color(120, 120, 120));
        subLabel.setBounds(50, 58, 400, 20);
        add(subLabel);
        // Buttons
        btnAddRoom = makeButton("Add Room", 100);
        btnCheckAvail = makeButton("Check Availability", 155);
        btnMakeRes = makeButton("Make Reservation", 210);
        btnConfirmRes = makeButton("Confirm Reservation", 265);
        btnCheckout = makeButton("Checkout", 320);
        btnLastCheckout = makeButton("View Last Checkout", 375);
        btnExit = makeButton("Exit", 440);
        btnExit.setBackground(new Color(192, 57, 43));
        // Status bar at the bottom
        statusLabel = new JLabel("Ready", JLabel.CENTER);
        statusLabel.setBounds(20, 500, 450, 50);
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        statusLabel.setForeground(new Color(80, 80, 80));
        statusLabel.setOpaque(true);
        statusLabel.setBackground(new Color(230, 234, 238));
        add(statusLabel);
        updateStatus("System ready. Total rooms in session: " + Room.getTotalRooms());
        setVisible(true);
    }
    // helper to create buttons
    JButton makeButton(String text, int yPos) {
        JButton btn = new JButton(text);
        btn.setBounds(140, yPos, 220, 42);
        btn.setFont(new Font("Arial", Font.BOLD, 13));
        btn.setBackground(new Color(41, 128, 185));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.addActionListener(this);
        add(btn);
        return btn;
    }
    // helper to update status bar
    void updateStatus(String msg) {
        statusLabel.setText("<html><center>" + msg + "</center></html>");
    }
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnAddRoom) {
            new AddRoom(hotel, this);
        } else if (e.getSource() == btnCheckAvail) {
            new CheckAvailability(hotel, this);
        } else if (e.getSource() == btnMakeRes) {
            new MakeReservation(hotel, this);
        } else if (e.getSource() == btnConfirmRes) {
            new ConfirmReservation(hotel, this);
        } else if (e.getSource() == btnCheckout) {
            new Checkout(hotel, this);
        } else if (e.getSource() == btnLastCheckout) {
            new ViewLastCheckout(hotel, this);
        } else if (e.getSource() == btnExit) {
            int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to exit?", "Exit", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                DBConnection.closeConnection();
                System.exit(0);
            }
        }
    }
    // Main method
    public static void main(String[] args) {
        new MainFrame();
    }
}
