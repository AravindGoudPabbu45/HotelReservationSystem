package hotelmanagementsystem;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Calendar;
public class MakeReservation extends JFrame implements ActionListener {
    JTextField tGuestId, tName, tContact, tResId, tRoomId, tCheckIn, tCheckOut;
    JTextArea resultArea;
    HotelSystem hotel;
    MainFrame mainFrame;
    public MakeReservation(HotelSystem hotel, MainFrame mainFrame) {
        this.hotel = hotel;
        this.mainFrame = mainFrame;
        setTitle("Make Reservation");
        setSize(470, 560);
        setLayout(null);
        setLocationRelativeTo(mainFrame);
        setResizable(false);
        getContentPane().setBackground(new Color(245, 248, 250));
        // Guest details section
        JLabel header1 = new JLabel("--- Guest Details ---");
        header1.setFont(new Font("Arial", Font.BOLD, 13));
        header1.setForeground(new Color(30, 60, 114));
        header1.setBounds(30, 15, 200, 20);
        add(header1);
        JLabel l1 = new JLabel("Guest ID:");
        l1.setBounds(30, 45, 130, 25);
        add(l1);
        tGuestId = new JTextField();
        tGuestId.setBounds(170, 45, 240, 25);
        add(tGuestId);
        JLabel l2 = new JLabel("Guest Name:");
        l2.setBounds(30, 80, 130, 25);
        add(l2);
        tName = new JTextField();
        tName.setBounds(170, 80, 240, 25);
        add(tName);
        JLabel l3 = new JLabel("Contact No:");
        l3.setBounds(30, 115, 130, 25);
        add(l3);
        tContact = new JTextField();
        tContact.setBounds(170, 115, 240, 25);
        add(tContact);
        // Reservation details section
        JLabel header2 = new JLabel("--- Reservation Details ---");
        header2.setFont(new Font("Arial", Font.BOLD, 13));
        header2.setForeground(new Color(30, 60, 114));
        header2.setBounds(30, 155, 250, 20);
        add(header2);
        JLabel l4 = new JLabel("Reservation ID:");
        l4.setBounds(30, 185, 130, 25);
        add(l4);
        tResId = new JTextField();
        tResId.setBounds(170, 185, 240, 25);
        add(tResId);
        JLabel l5 = new JLabel("Room ID:");
        l5.setBounds(30, 220, 130, 25);
        add(l5);
        tRoomId = new JTextField();
        tRoomId.setBounds(170, 220, 240, 25);
        add(tRoomId);
        JLabel l6 = new JLabel("Check-In (dd/mm/yyyy):");
        l6.setBounds(30, 255, 140, 25);
        add(l6);
        tCheckIn = new JTextField();
        tCheckIn.setBounds(170, 255, 240, 25);
        add(tCheckIn);
        JLabel l7 = new JLabel("Check-Out (dd/mm/yyyy):");
        l7.setBounds(30, 290, 145, 25);
        add(l7);
        tCheckOut = new JTextField();
        tCheckOut.setBounds(170, 290, 240, 25);
        add(tCheckOut);
        JButton btnSubmit = new JButton("Make Reservation");
        btnSubmit.setBounds(155, 335, 160, 38);
        btnSubmit.setBackground(new Color(39, 174, 96));
        btnSubmit.setForeground(Color.WHITE);
        btnSubmit.setFocusPainted(false);
        btnSubmit.addActionListener(this);
        add(btnSubmit);
        resultArea = new JTextArea("Reservation details will appear here...");
        resultArea.setBounds(30, 390, 400, 110);
        resultArea.setEditable(false);
        resultArea.setFocusable(false);
        resultArea.setFont(new Font("Arial", Font.PLAIN, 12));
        resultArea.setBackground(new Color(236, 240, 241));
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
        add(resultArea);
        setVisible(true);
    }
    public void actionPerformed(ActionEvent ae) {
        // validate all fields
        if (tGuestId.getText().trim().isEmpty() || tName.getText().trim().isEmpty()
            || tContact.getText().trim().isEmpty() || tResId.getText().trim().isEmpty()
            || tRoomId.getText().trim().isEmpty() || tCheckIn.getText().trim().isEmpty()
            || tCheckOut.getText().trim().isEmpty()) {
            resultArea.setText("Error: Please fill all fields!");
            return;
        }
        try {
            int guestId = Integer.parseInt(tGuestId.getText().trim());
            String guestName = tName.getText().trim();
            String contact = tContact.getText().trim();
            int resId = Integer.parseInt(tResId.getText().trim());
            int roomId = Integer.parseInt(tRoomId.getText().trim());
            // parse check-in date
            String[] cinParts = tCheckIn.getText().trim().split("/");
            if (cinParts.length != 3) {
                resultArea.setText("Error: Check-In date must be in dd/mm/yyyy format!");
                return;
            }
            Calendar checkIn = Calendar.getInstance();
            checkIn.set(Integer.parseInt(cinParts[2]),
                        Integer.parseInt(cinParts[1]) - 1,
                        Integer.parseInt(cinParts[0]));
            // parse check-out date
            String[] coutParts = tCheckOut.getText().trim().split("/");
            if (coutParts.length != 3) {
                resultArea.setText("Error: Check-Out date must be in dd/mm/yyyy format!");
                return;
            }
            Calendar checkOut = Calendar.getInstance();
            checkOut.set(Integer.parseInt(coutParts[2]),
                         Integer.parseInt(coutParts[1]) - 1,
                         Integer.parseInt(coutParts[0]));
            // check if checkout is after checkin
            if (checkOut.before(checkIn) || checkOut.equals(checkIn)) {
                resultArea.setText("Error: Check-Out date must be after Check-In date!");
                return;
            }
            // insert guest
            Guest guest = new Guest(guestId, guestName, contact);
            hotel.insertGuest(guest);
            // queue reservation
            Reservation res = new Reservation(resId, guestId, roomId, checkIn, checkOut);
            hotel.queueReservation(res);
            // calculate cost
            double price = hotel.getRoomPrice(roomId);
            double totalCost = hotel.calculateTotalCost(checkIn, checkOut, price);
            long diff = checkOut.getTimeInMillis() - checkIn.getTimeInMillis();
            int days = (int) (diff / (1000 * 60 * 60 * 24));
            String msg = "Reservation stored in database!\n";
            msg += "Guest: " + guestName + " (ID: " + guestId + ")\n";
            msg += "Room ID: " + roomId + "\n";
            msg += "Stay Duration: " + days + " night(s)\n";
            if (price > 0) {
                msg += "Total Cost: Rs." + totalCost;
            } else {
                msg += "Note: Room not in current session inventory.";
            }
            resultArea.setText(msg);
            mainFrame.updateStatus("Reservation " + resId + " created for " + guestName);
        } catch (NumberFormatException ex) {
            resultArea.setText("Error: Please enter valid numeric values for IDs and dates!");
        } catch (Exception ex) {
            resultArea.setText("Error: " + ex.getMessage());
        }
    }
}
