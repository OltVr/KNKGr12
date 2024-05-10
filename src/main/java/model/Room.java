package model;

public class Room {
    private int roomNumber;
    private int floorNumber;
    private String roomType;
    private int capacity;
    private int bedNumber;
    private double price;
    private boolean isAvailable;

    public Room(int roomNumber, int floorNumber, String roomType, int capacity, int bedNumber, double price,boolean isAvailable) {
        this.roomNumber = roomNumber;
        this.floorNumber=floorNumber;
        this.roomType=roomType;
        this.capacity=capacity;
        this.bedNumber=bedNumber;
        this.price=price;
        this.isAvailable=isAvailable;
    }

    public double getPrice() {
        return price;
    }

    public int getBedNumber() {
        return bedNumber;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public String getRoomType() {
        return roomType;
    }

    public boolean isAvailable() {
        return isAvailable;
    }
}
