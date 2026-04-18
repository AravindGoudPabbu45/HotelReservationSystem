package hotelmanagementsystem;
import java.util.Calendar;
public class Reservation {
    int reservationId;
    int guestId;
    int roomId;
    Calendar checkIn;
    Calendar checkOut;
    public Reservation(int reservationId, int guestId, int roomId,
                       Calendar checkIn, Calendar checkOut) {
        this.reservationId = reservationId;
        this.guestId = guestId;
        this.roomId = roomId;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }
    public int getReservationId() {
        return reservationId;
    }
    public int getGuestId() {
        return guestId;
    }
    public int getRoomId() {
        return roomId;
    }
    public Calendar getCheckIn() {
        return checkIn;
    }
    public Calendar getCheckOut() {
        return checkOut;
    }
    public String toString() {
        return "Reservation ID: " + reservationId
             + " | Guest ID: " + guestId
             + " | Room ID: " + roomId
             + " | Check-In: " + checkIn.get(Calendar.DAY_OF_MONTH) + "/" + (checkIn.get(Calendar.MONTH) + 1) + "/" + checkIn.get(Calendar.YEAR)
             + " | Check-Out: " + checkOut.get(Calendar.DAY_OF_MONTH) + "/" + (checkOut.get(Calendar.MONTH) + 1) + "/" + checkOut.get(Calendar.YEAR);
    }
}
