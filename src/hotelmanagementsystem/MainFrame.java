package hotelmanagementsystem;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Calendar;
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
            openAddRoom();
        } else if (e.getSource() == btnCheckAvail) {
            openCheckAvailability();
        } else if (e.getSource() == btnMakeRes) {
            openMakeReservation();
        } else if (e.getSource() == btnConfirmRes) {
            openConfirmReservation();
        } else if (e.getSource() == btnCheckout) {
            openCheckout();
        } else if (e.getSource() == btnLastCheckout) {
            openViewLastCheckout();
        } else if (e.getSource() == btnExit) {
            int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to exit?", "Exit", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                DBConnection.closeConnection();
                System.exit(0);
            }
        }
    }
    // ========== Add Room Window ==========
    void openAddRoom() {
        JFrame f = new JFrame("Add Room");
        f.setSize(430, 340);
        f.setLayout(null);
        f.setLocationRelativeTo(this);
        f.setResizable(false);
        f.getContentPane().setBackground(new Color(245, 248, 250));
        JLabel heading = new JLabel("Add New Room", JLabel.CENTER);
        heading.setFont(new Font("Arial", Font.BOLD, 16));
        heading.setForeground(new Color(30, 60, 114));
        heading.setBounds(30, 10, 360, 25);
        f.add(heading);
        JLabel l1 = new JLabel("Room ID:");
        l1.setBounds(30, 50, 120, 25);
        f.add(l1);
        JTextField t1 = new JTextField();
        t1.setBounds(160, 50, 210, 25);
        f.add(t1);
        JLabel l2 = new JLabel("Room Type:");
        l2.setBounds(30, 90, 120, 25);
        f.add(l2);
        String[] types = {"STANDARD", "DELUXE", "SUITE", "PENTHOUSE"};
        JComboBox<String> combo = new JComboBox<>(types);
        combo.setBounds(160, 90, 210, 25);
        f.add(combo);
        JLabel l3 = new JLabel("Price (Rs.):");
        l3.setBounds(30, 130, 130, 25);
        f.add(l3);
        JTextField t3 = new JTextField();
        t3.setBounds(160, 130, 210, 25);
        f.add(t3);
        JButton btnAdd = new JButton("Add Room");
        btnAdd.setBounds(145, 180, 140, 35);
        btnAdd.setBackground(new Color(39, 174, 96));
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setFocusPainted(false);
        f.add(btnAdd);
        JLabel resultLabel = new JLabel("", JLabel.CENTER);
        resultLabel.setBounds(20, 230, 380, 50);
        resultLabel.setFont(new Font("Arial", Font.BOLD, 12));
        f.add(resultLabel);
        btnAdd.addActionListener(new ActionListener() {
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
                    updateStatus("Room " + id + " added. Total rooms: " + Room.getTotalRooms());
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
        });
        f.setVisible(true);
    }
    // ========== Check Availability Window ==========
    void openCheckAvailability() {
        JFrame f = new JFrame("Check Availability");
        f.setSize(430, 300);
        f.setLayout(null);
        f.setLocationRelativeTo(this);
        f.setResizable(false);
        f.getContentPane().setBackground(new Color(245, 248, 250));
        JLabel heading = new JLabel("Check Room Availability", JLabel.CENTER);
        heading.setFont(new Font("Arial", Font.BOLD, 16));
        heading.setForeground(new Color(30, 60, 114));
        heading.setBounds(30, 10, 360, 25);
        f.add(heading);
        JLabel l1 = new JLabel("Enter Room ID:");
        l1.setBounds(30, 50, 120, 25);
        f.add(l1);
        JTextField t1 = new JTextField();
        t1.setBounds(160, 50, 210, 25);
        f.add(t1);
        JButton btnCheck = new JButton("Check");
        btnCheck.setBounds(160, 90, 120, 35);
        btnCheck.setBackground(new Color(41, 128, 185));
        btnCheck.setForeground(Color.WHITE);
        btnCheck.setFocusPainted(false);
        f.add(btnCheck);
        JTextArea resultArea = new JTextArea("Result will appear here...");
        resultArea.setBounds(30, 140, 360, 100);
        resultArea.setEditable(false);
        resultArea.setFocusable(false);
        resultArea.setFont(new Font("Arial", Font.PLAIN, 13));
        resultArea.setBackground(new Color(236, 240, 241));
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
        f.add(resultArea);
        btnCheck.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                if (t1.getText().trim().isEmpty()) {
                    resultArea.setText("Please enter a Room ID!");
                    return;
                }
                try {
                    int roomId = Integer.parseInt(t1.getText().trim());
                    String result = hotel.checkAvailability(roomId);
                    resultArea.setText(result);
                    updateStatus("Checked availability for Room " + roomId);
                } catch (NumberFormatException ex) {
                    resultArea.setText("Invalid input! Please enter a numeric Room ID.");
                } catch (Exception ex) {
                    resultArea.setText("Error: " + ex.getMessage());
                }
            }
        });
        f.setVisible(true);
    }
    // ========== Make Reservation Window ==========
    void openMakeReservation() {
        JFrame f = new JFrame("Make Reservation");
        f.setSize(470, 560);
        f.setLayout(null);
        f.setLocationRelativeTo(this);
        f.setResizable(false);
        f.getContentPane().setBackground(new Color(245, 248, 250));
        // Guest details section
        JLabel header1 = new JLabel("--- Guest Details ---");
        header1.setFont(new Font("Arial", Font.BOLD, 13));
        header1.setForeground(new Color(30, 60, 114));
        header1.setBounds(30, 15, 200, 20);
        f.add(header1);
        JLabel l1 = new JLabel("Guest ID:");
        l1.setBounds(30, 45, 130, 25);
        f.add(l1);
        JTextField tGuestId = new JTextField();
        tGuestId.setBounds(170, 45, 240, 25);
        f.add(tGuestId);
        JLabel l2 = new JLabel("Guest Name:");
        l2.setBounds(30, 80, 130, 25);
        f.add(l2);
        JTextField tName = new JTextField();
        tName.setBounds(170, 80, 240, 25);
        f.add(tName);
        JLabel l3 = new JLabel("Contact No:");
        l3.setBounds(30, 115, 130, 25);
        f.add(l3);
        JTextField tContact = new JTextField();
        tContact.setBounds(170, 115, 240, 25);
        f.add(tContact);
        // Reservation details section
        JLabel header2 = new JLabel("--- Reservation Details ---");
        header2.setFont(new Font("Arial", Font.BOLD, 13));
        header2.setForeground(new Color(30, 60, 114));
        header2.setBounds(30, 155, 250, 20);
        f.add(header2);
        JLabel l4 = new JLabel("Reservation ID:");
        l4.setBounds(30, 185, 130, 25);
        f.add(l4);
        JTextField tResId = new JTextField();
        tResId.setBounds(170, 185, 240, 25);
        f.add(tResId);
        JLabel l5 = new JLabel("Room ID:");
        l5.setBounds(30, 220, 130, 25);
        f.add(l5);
        JTextField tRoomId = new JTextField();
        tRoomId.setBounds(170, 220, 240, 25);
        f.add(tRoomId);
        JLabel l6 = new JLabel("Check-In (dd/mm/yyyy):");
        l6.setBounds(30, 255, 140, 25);
        f.add(l6);
        JTextField tCheckIn = new JTextField();
        tCheckIn.setBounds(170, 255, 240, 25);
        f.add(tCheckIn);
        JLabel l7 = new JLabel("Check-Out (dd/mm/yyyy):");
        l7.setBounds(30, 290, 145, 25);
        f.add(l7);
        JTextField tCheckOut = new JTextField();
        tCheckOut.setBounds(170, 290, 240, 25);
        f.add(tCheckOut);
        JButton btnSubmit = new JButton("Make Reservation");
        btnSubmit.setBounds(155, 335, 160, 38);
        btnSubmit.setBackground(new Color(39, 174, 96));
        btnSubmit.setForeground(Color.WHITE);
        btnSubmit.setFocusPainted(false);
        f.add(btnSubmit);
        JTextArea resultArea = new JTextArea("Reservation details will appear here...");
        resultArea.setBounds(30, 390, 400, 110);
        resultArea.setEditable(false);
        resultArea.setFocusable(false);
        resultArea.setFont(new Font("Arial", Font.PLAIN, 12));
        resultArea.setBackground(new Color(236, 240, 241));
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
        f.add(resultArea);
        btnSubmit.addActionListener(new ActionListener() {
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
                    updateStatus("Reservation " + resId + " created for " + guestName);
                } catch (NumberFormatException ex) {
                    resultArea.setText("Error: Please enter valid numeric values for IDs and dates!");
                } catch (Exception ex) {
                    resultArea.setText("Error: " + ex.getMessage());
                }
            }
        });
        f.setVisible(true);
    }
    // ========== Confirm Reservation Window ==========
    void openConfirmReservation() {
        JFrame f = new JFrame("Confirm Reservation");
        f.setSize(430, 250);
        f.setLayout(null);
        f.setLocationRelativeTo(this);
        f.setResizable(false);
        f.getContentPane().setBackground(new Color(245, 248, 250));
        JLabel heading = new JLabel("Confirm Next Reservation", JLabel.CENTER);
        heading.setFont(new Font("Arial", Font.BOLD, 16));
        heading.setForeground(new Color(30, 60, 114));
        heading.setBounds(30, 10, 360, 25);
        f.add(heading);
        JTextArea resultArea = new JTextArea("Click button to confirm next reservation from queue...");
        resultArea.setBounds(30, 50, 360, 100);
        resultArea.setEditable(false);
        resultArea.setFocusable(false);
        resultArea.setFont(new Font("Arial", Font.PLAIN, 13));
        resultArea.setBackground(new Color(236, 240, 241));
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
        f.add(resultArea);
        JButton btnConfirm = new JButton("Confirm Next");
        btnConfirm.setBounds(145, 165, 140, 35);
        btnConfirm.setBackground(new Color(39, 174, 96));
        btnConfirm.setForeground(Color.WHITE);
        btnConfirm.setFocusPainted(false);
        f.add(btnConfirm);
        btnConfirm.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                String result = hotel.confirmNextReservation();
                resultArea.setText(result);
                updateStatus("Confirm reservation action performed");
            }
        });
        f.setVisible(true);
    }
    // ========== Checkout Window ==========
    void openCheckout() {
        JFrame f = new JFrame("Checkout");
        f.setSize(420, 250);
        f.setLayout(null);
        f.setLocationRelativeTo(this);
        f.setResizable(false);
        f.getContentPane().setBackground(new Color(245, 248, 250));
        JLabel heading = new JLabel("Guest Checkout", JLabel.CENTER);
        heading.setFont(new Font("Arial", Font.BOLD, 16));
        heading.setForeground(new Color(30, 60, 114));
        heading.setBounds(30, 10, 350, 25);
        f.add(heading);
        JLabel l1 = new JLabel("Guest ID:");
        l1.setBounds(30, 50, 100, 25);
        f.add(l1);
        JTextField t1 = new JTextField();
        t1.setBounds(140, 50, 220, 25);
        f.add(t1);
        JButton btnOut = new JButton("Checkout");
        btnOut.setBounds(140, 95, 140, 35);
        btnOut.setBackground(new Color(231, 76, 60));
        btnOut.setForeground(Color.WHITE);
        btnOut.setFocusPainted(false);
        f.add(btnOut);
        JLabel resultLabel = new JLabel("", JLabel.CENTER);
        resultLabel.setBounds(20, 150, 370, 50);
        resultLabel.setFont(new Font("Arial", Font.BOLD, 12));
        f.add(resultLabel);
        btnOut.addActionListener(new ActionListener() {
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
                    updateStatus("Guest " + guestId + " checked out successfully");
                    t1.setText("");
                } catch (NumberFormatException ex) {
                    resultLabel.setForeground(Color.RED);
                    resultLabel.setText("Invalid input! Enter a numeric Guest ID.");
                } catch (Exception ex) {
                    resultLabel.setForeground(Color.RED);
                    resultLabel.setText("Error: " + ex.getMessage());
                }
            }
        });
        f.setVisible(true);
    }
    // ========== View Last Checkout Window ==========
    void openViewLastCheckout() {
        JFrame f = new JFrame("Last Checkout");
        f.setSize(430, 200);
        f.setLayout(null);
        f.setLocationRelativeTo(this);
        f.setResizable(false);
        f.getContentPane().setBackground(new Color(245, 248, 250));
        JLabel heading = new JLabel("Last Checkout (Stack.peek)", JLabel.CENTER);
        heading.setFont(new Font("Arial", Font.BOLD, 16));
        heading.setForeground(new Color(30, 60, 114));
        heading.setBounds(30, 10, 360, 25);
        f.add(heading);
        JTextArea resultArea = new JTextArea();
        resultArea.setBounds(30, 50, 360, 90);
        resultArea.setEditable(false);
        resultArea.setFocusable(false);
        resultArea.setFont(new Font("Arial", Font.PLAIN, 13));
        resultArea.setBackground(new Color(236, 240, 241));
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
        f.add(resultArea);
        // immediately show the last checkout details
        String result = hotel.getLastCheckout();
        resultArea.setText(result);
        f.setVisible(true);
    }
    // Main method
    public static void main(String[] args) {
        new MainFrame();
    }
}
