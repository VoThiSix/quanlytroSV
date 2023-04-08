package com.donghh.studentmanagement.entity;

import java.io.Serializable;

public class Room implements Serializable {
    private int id;
    private String roomCode;
    private String roomName;
    private String categoryRoomCode;
//    private String numberRoomEmpty;//số giường trống

    public Room(int id, String roomCode, String roomName, String categoryRoomCode) {
        this.id = id;
        this.roomCode = roomCode;
        this.roomName = roomName;
        this.categoryRoomCode = categoryRoomCode;
    }

    public Room(String roomCode, String roomName, String categoryRoomCode) {
        this.roomCode = roomCode;
        this.roomName = roomName;
        this.categoryRoomCode = categoryRoomCode;
    }

//    public String getNumberRoomEmpty() {
//        return numberRoomEmpty;
//    }
//
//    public void setNumberRoomEmpty(String numberRoomEmpty) {
//        this.numberRoomEmpty = numberRoomEmpty;
//    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoomCode() {
        return roomCode;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getCategoryRoomCode() {
        return categoryRoomCode;
    }

    public void setCategoryRoomCode(String categoryRoomCode) {
        this.categoryRoomCode = categoryRoomCode;
    }
}
