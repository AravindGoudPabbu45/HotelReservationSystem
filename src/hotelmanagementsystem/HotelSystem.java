package hotelmanagementsystem;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
public class HotelSystem {
    // Collections
    Hashtable<Integer, Room> roomInventory;
    Queue<Reservation> reservationQueue;
    Stack<String> checkoutLog;
    public HotelSystem() {
        roomInventory = new Hashtable<>();
        reservationQueue = new LinkedList<>();
        checkoutLog = new Stack<>();
    }
    // 1. Add Room - stores in Hashtable and inserts into database
    public void addRoom(Room room) {
        roomInventory.put(room.getRoomId(), room);
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO rooms VALUES (?, ?, ?)");
            ps.setInt(1, room.getRoomId());
            ps.setString(2, room.getRoomType().toString());
            ps.setDouble(3, room.getPricePerNight());
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            System.out.println("Error inserting room: " + e.getMessage());
        }
    }
    // 2. Check Availability - checks Hashtable and database
    public String checkAvailability(int roomId) {
        // check in Hashtable first
        Room room = roomInventory.get(roomId);
        if (room != null) {
            // check if reserved
            boolean reserved = false;
            try {
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(
                    "SELECT reservation_id FROM reservations WHERE room_id = ?");
                ps.setInt(1, roomId);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    reserved = true;
                }
                rs.close();
                ps.close();
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
            if (reserved) {
                return "Room " + roomId + " is RESERVED.\n" + room;
            } else {
                return "Room " + roomId + " is AVAILABLE.\n" + room;
            }
        }
        // if not in Hashtable, try database
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                "SELECT * FROM rooms WHERE room_id = ?");
            ps.setInt(1, roomId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String type = rs.getString("room_type");
                double price = rs.getDouble("price_per_night");
                rs.close();
                ps.close();
                return "Room " + roomId + " found in database.\nType: " + type + " | Price: Rs." + price;
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return "Room " + roomId + " does NOT exist.";
    }
    // 3. Queue Reservation - adds to Queue and inserts into database
    public void queueReservation(Reservation res) {
        reservationQueue.offer(res);
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO reservations VALUES (?, ?, ?, ?, ?)");
            ps.setInt(1, res.getReservationId());
            ps.setInt(2, res.getGuestId());
            ps.setInt(3, res.getRoomId());
            ps.setDate(4, new java.sql.Date(res.getCheckIn().getTimeInMillis()));
            ps.setDate(5, new java.sql.Date(res.getCheckOut().getTimeInMillis()));
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            System.out.println("Error inserting reservation: " + e.getMessage());
        }
    }
    // 4. Confirm Next Reservation - uses Queue.poll()
    public String confirmNextReservation() {
        Reservation res = reservationQueue.poll();
        if (res != null) {
            return "Reservation Confirmed!\n" + res;
        }
        return "No pending reservations in queue.";
    }
    // 5. Checkout - pushes to Stack
    public void checkout(int guestId) {
        String log = "Guest ID: " + guestId + " | Checked out at: " + new java.util.Date();
        checkoutLog.push(log);
    }
    // 6. Get Last Checkout - uses Stack.peek()
    public String getLastCheckout() {
        if (!checkoutLog.isEmpty()) {
            return checkoutLog.peek();
        }
        return "No checkout records found.";
    }
    // 7. Calculate Total Cost - uses Calendar difference
    public double calculateTotalCost(Calendar checkIn, Calendar checkOut, double pricePerNight) {
        long diffMillis = checkOut.getTimeInMillis() - checkIn.getTimeInMillis();
        int days = (int) (diffMillis / (1000 * 60 * 60 * 24));
        if (days <= 0) {
            return 0;
        }
        return days * pricePerNight;
    }
    // Insert guest into database
    public void insertGuest(Guest guest) {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO guests VALUES (?, ?, ?)");
            ps.setInt(1, guest.getGuestId());
            ps.setString(2, guest.getName());
            ps.setString(3, guest.getContactNo());
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            System.out.println("Guest may already exist: " + e.getMessage());
        }
    }
    // Get room price from Hashtable
    public double getRoomPrice(int roomId) {
        Room room = roomInventory.get(roomId);
        if (room != null) {
            return room.getPricePerNight();
        }
        return 0;
    }
}
