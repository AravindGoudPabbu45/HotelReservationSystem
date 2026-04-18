package hotelmanagementsystem;
public class Room {
    int roomId;
    RoomType roomType;
    double pricePerNight;
    static int totalRooms = 0;

    public Room(int roomId, RoomType roomType, double pricePerNight) {
        this.roomId = roomId;
        this.roomType = roomType;
        this.pricePerNight = pricePerNight;
        totalRooms++;
    }
    public int getRoomId() {
        return roomId;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    public static int getTotalRooms() {
        return totalRooms;
    }

    public String toString() {
        return "Room ID: " + roomId + " | Type: " + roomType + " | Price: Rs." + pricePerNight;
    }
}
