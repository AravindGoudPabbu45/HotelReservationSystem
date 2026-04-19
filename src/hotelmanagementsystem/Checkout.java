package hotelmanagementsystem;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class Checkout extends JFrame implements ActionListener {
    JTextField t1;
    JLabel resultLabel;
    HotelSystem hotel;
    MainFrame mainFrame;
    public Checkout(HotelSystem hotel, MainFrame mainFrame) {
        this.hotel = hotel;
        this.mainFrame = mainFrame;
        setTitle("Checkout");
        setSize(420, 250);
        setLayout(null);
        setLocationRelativeTo(mainFrame);
        setResizable(false);
        getContentPane().setBackground(new Color(245, 248, 250));
        JLabel heading = new JLabel("Guest Checkout", JLabel.CENTER);
        heading.setFont(new Font("Arial", Font.BOLD, 16));
        heading.setForeground(new Color(30, 60, 114));
        heading.setBounds(30, 10, 350, 25);
        add(heading);
        JLabel l1 = new JLabel("Guest ID:");
        l1.setBounds(30, 50, 100, 25);
        add(l1);
        t1 = new JTextField();
        t1.setBounds(140, 50, 220, 25);
        add(t1);
        JButton btnOut = new JButton("Checkout");
        btnOut.setBounds(140, 95, 140, 35);
        btnOut.setBackground(new Color(231, 76, 60));
        btnOut.setForeground(Color.WHITE);
        btnOut.setFocusPainted(false);
        btnOut.addActionListener(this);
        add(btnOut);
        resultLabel = new JLabel("", JLabel.CENTER);
        resultLabel.setBounds(20, 150, 370, 50);
        resultLabel.setFont(new Font("Arial", Font.BOLD, 12));
        add(resultLabel);
        setVisible(true);
    }
    public void actionPerformed(ActionEvent ae) {
        if (t1.getText().trim().isEmpty()) {
            resultLabel.setForeground(Color.RED);
            resultLabel.setText("Please enter a Guest ID!");
            return;
        }
        try {
            int guestId = Integer.parseInt(t1.getText().trim());
            hotel.checkout(guestId);
            resultLabel.setForeground(new Color(39, 174, 96));
            resultLabel.setText("<html>Checkout completed for Guest ID: " + guestId
                + "<br>Pushed to checkout history stack.</html>");
            mainFrame.updateStatus("Guest " + guestId + " checked out successfully");
            t1.setText("");
        } catch (NumberFormatException ex) {
            resultLabel.setForeground(Color.RED);
            resultLabel.setText("Invalid input! Enter a numeric Guest ID.");
        } catch (Exception ex) {
            resultLabel.setForeground(Color.RED);
            resultLabel.setText("Error: " + ex.getMessage());
        }
    }
}
