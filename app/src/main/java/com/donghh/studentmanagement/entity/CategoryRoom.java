package com.donghh.studentmanagement.entity;

public class CategoryRoom {
    private int id;
    private String categoryRoomCode;
    private String categoryRoomName;
    private int numberRoom;//số giường
    private double price; //giá 1 giường của phòng

    public CategoryRoom(int id, String categoryRoomCode, String categoryRoomName, int numberRoom, double price) {
        this.id = id;
        this.categoryRoomCode = categoryRoomCode;
        this.categoryRoomName = categoryRoomName;
        this.numberRoom = numberRoom;
        this.price = price;
    }

    public CategoryRoom(String categoryRoomCode, String categoryRoomName, int numberRoom, double price) {
        this.categoryRoomCode = categoryRoomCode;
        this.categoryRoomName = categoryRoomName;
        this.numberRoom = numberRoom;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategoryRoomCode() {
        return categoryRoomCode;
    }

    public void setCategoryRoomCode(String categoryRoomCode) {
        this.categoryRoomCode = categoryRoomCode;
    }

    public String getCategoryRoomName() {
        return categoryRoomName;
    }

    public void setCategoryRoomName(String categoryRoomName) {
        this.categoryRoomName = categoryRoomName;
    }

    public int getNumberRoom() {
        return numberRoom;
    }

    public void setNumberRoom(int numberRoom) {
        this.numberRoom = numberRoom;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
