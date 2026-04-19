package hotelmanagementsystem;
import javax.swing.*;
import java.awt.*;
public class ViewLastCheckout extends JFrame {
    JTextArea resultArea;
    HotelSystem hotel;
    public ViewLastCheckout(HotelSystem hotel, MainFrame mainFrame) {
        this.hotel = hotel;
        setTitle("Last Checkout");
        setSize(430, 200);
        setLayout(null);
        setLocationRelativeTo(mainFrame);
        setResizable(false);
        getContentPane().setBackground(new Color(245, 248, 250));
        JLabel heading = new JLabel("Last Checkout (Stack.peek)", JLabel.CENTER);
        heading.setFont(new Font("Arial", Font.BOLD, 16));
        heading.setForeground(new Color(30, 60, 114));
        heading.setBounds(30, 10, 360, 25);
        add(heading);
        resultArea = new JTextArea();
        resultArea.setBounds(30, 50, 360, 90);
        resultArea.setEditable(false);
        resultArea.setFocusable(false);
        resultArea.setFont(new Font("Arial", Font.PLAIN, 13));
        resultArea.setBackground(new Color(236, 240, 241));
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
        add(resultArea);
        // immediately show the last checkout details
        String result = hotel.getLastCheckout();
        resultArea.setText(result);
        setVisible(true);
    }
}
