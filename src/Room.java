import java.time.LocalDate;
public class Room {    private int roomNumber;
    private String roomType;    private double price;
    private boolean isAvailable;    private LocalDate availableDate;
    public Room(int roomNumber, String roomType, double price, boolean isAvailable, LocalDate availableDate) {
        this.roomNumber = roomNumber;        this.roomType = roomType;
        this.price = price;        this.isAvailable = isAvailable;
        this.availableDate = availableDate;    }
    public int getRoomNumber() {
        return roomNumber;
    }
    public void setRoomNumber(int roomNumber) {        this.roomNumber = roomNumber;
    }
    public String getRoomType() {        return roomType;
    }
    public void setRoomType(String roomType) {        this.roomType = roomType;
    }
    public double getPrice() {        return price;
    }
    public void setPrice(double price) {        this.price = price;
    }
    public boolean isAvailable() {        return isAvailable;
    }
    public void setAvailable(boolean available) {        isAvailable = available;
    }
    public LocalDate getAvailableDate() {        return availableDate;
    }
    public void setAvailableDate(LocalDate availableDate) {        this.availableDate = availableDate;
    }
    @Override    public String toString() {
        return "Room{" +                "roomNumber=" + roomNumber +
                ", roomType='" + roomType + '\'' +                ", price=" + price +
                ", isAvailable=" + isAvailable +                ", availableDate=" + (availableDate != null ? availableDate : "Now") +
                '}';    }
}