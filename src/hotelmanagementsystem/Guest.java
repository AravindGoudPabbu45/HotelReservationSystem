package hotelmanagementsystem;

public class Guest {

    int guestId;
    String name;
    String contactNo;

    public Guest(int guestId, String name, String contactNo) {
        this.guestId = guestId;
        this.name = name;
        this.contactNo = contactNo;
    }

    public int getGuestId() {
        return guestId;
    }

    public String getName() {
        return name;
    }

    public String getContactNo() {
        return contactNo;
    }

    public String toString() {
        return "Guest ID: " + guestId + " | Name: " + name + " | Contact: " + contactNo;
    }
}
