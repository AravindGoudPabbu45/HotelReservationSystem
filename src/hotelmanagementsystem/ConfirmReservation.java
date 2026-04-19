package hotelmanagementsystem;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class ConfirmReservation extends JFrame implements ActionListener {
    JTextArea resultArea;
    HotelSystem hotel;
    MainFrame mainFrame;
    public ConfirmReservation(HotelSystem hotel, MainFrame mainFrame) {
        this.hotel = hotel;
        this.mainFrame = mainFrame;
        setTitle("Confirm Reservation");
        setSize(430, 250);
        setLayout(null);
        setLocationRelativeTo(mainFrame);
        setResizable(false);
        getContentPane().setBackground(new Color(245, 248, 250));
        JLabel heading = new JLabel("Confirm Next Reservation", JLabel.CENTER);
        heading.setFont(new Font("Arial", Font.BOLD, 16));
        heading.setForeground(new Color(30, 60, 114));
        heading.setBounds(30, 10, 360, 25);
        add(heading);
        resultArea = new JTextArea("Click button to confirm next reservation from queue...");
        resultArea.setBounds(30, 50, 360, 100);
        resultArea.setEditable(false);
        resultArea.setFocusable(false);
        resultArea.setFont(new Font("Arial", Font.PLAIN, 13));
        resultArea.setBackground(new Color(236, 240, 241));
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
        add(resultArea);
        JButton btnConfirm = new JButton("Confirm Next");
        btnConfirm.setBounds(145, 165, 140, 35);
        btnConfirm.setBackground(new Color(39, 174, 96));
        btnConfirm.setForeground(Color.WHITE);
        btnConfirm.setFocusPainted(false);
        btnConfirm.addActionListener(this);
        add(btnConfirm);
        setVisible(true);
    }
    public void actionPerformed(ActionEvent ae) {
        String result = hotel.confirmNextReservation();
        resultArea.setText(result);
        mainFrame.updateStatus("Confirm reservation action performed");
    }
}
