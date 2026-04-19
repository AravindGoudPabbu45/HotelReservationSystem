package hotelmanagementsystem;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class AddRoom extends JFrame implements ActionListener {
    JTextField t1, t3;
    JComboBox<String> combo;
    JLabel resultLabel;
    HotelSystem hotel;
    MainFrame mainFrame;
    public AddRoom(HotelSystem hotel, MainFrame mainFrame) {
        this.hotel = hotel;
        this.mainFrame = mainFrame;
        setTitle("Add Room");
        setSize(430, 340);
        setLayout(null);
        setLocationRelativeTo(mainFrame);
        setResizable(false);
        getContentPane().setBackground(new Color(245, 248, 250));
        JLabel heading = new JLabel("Add New Room", JLabel.CENTER);
        heading.setFont(new Font("Arial", Font.BOLD, 16));
        heading.setForeground(new Color(30, 60, 114));
        heading.setBounds(30, 10, 360, 25);
        add(heading);
        JLabel l1 = new JLabel("Room ID:");
        l1.setBounds(30, 50, 120, 25);
        add(l1);
        t1 = new JTextField();
        t1.setBounds(160, 50, 210, 25);
        add(t1);
        JLabel l2 = new JLabel("Room Type:");
        l2.setBounds(30, 90, 120, 25);
        add(l2);
        String[] types = {"STANDARD", "DELUXE", "SUITE", "PENTHOUSE"};
        combo = new JComboBox<>(types);
        combo.setBounds(160, 90, 210, 25);
        add(combo);
        JLabel l3 = new JLabel("Price (Rs.):");
        l3.setBounds(30, 130, 130, 25);
        add(l3);
        t3 = new JTextField();
        t3.setBounds(160, 130, 210, 25);
        add(t3);
        JButton btnAdd = new JButton("Add Room");
        btnAdd.setBounds(145, 180, 140, 35);
        btnAdd.setBackground(new Color(39, 174, 96));
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setFocusPainted(false);
        btnAdd.addActionListener(this);
        add(btnAdd);
        resultLabel = new JLabel("", JLabel.CENTER);
        resultLabel.setBounds(20, 230, 380, 50);
        resultLabel.setFont(new Font("Arial", Font.BOLD, 12));
        add(resultLabel);
        setVisible(true);
    }
    public void actionPerformed(ActionEvent ae) {
        if (t1.getText().trim().isEmpty() || t3.getText().trim().isEmpty()) {
            resultLabel.setForeground(Color.RED);
            resultLabel.setText("Please fill all fields!");
            return;
        }
        try {
            int id = Integer.parseInt(t1.getText().trim());
            double price = Double.parseDouble(t3.getText().trim());
            if (price <= 0) {
                resultLabel.setForeground(Color.RED);
                resultLabel.setText("Price must be greater than 0!");
                return;
            }
            String type = combo.getSelectedItem().toString();
            RoomType rt = RoomType.valueOf(type);
            Room room = new Room(id, rt, price);
            hotel.addRoom(room);
            resultLabel.setForeground(new Color(39, 174, 96));
            resultLabel.setText("<html>Room added successfully!<br>Total Rooms: " + Room.getTotalRooms() + "</html>");
            mainFrame.updateStatus("Room " + id + " added. Total rooms: " + Room.getTotalRooms());
            t1.setText("");
            t3.setText("");
        } catch (NumberFormatException ex) {
            resultLabel.setForeground(Color.RED);
            resultLabel.setText("Please enter valid numeric values!");
        } catch (Exception ex) {
            resultLabel.setForeground(Color.RED);
            resultLabel.setText("Error: " + ex.getMessage());
        }
    }
}
