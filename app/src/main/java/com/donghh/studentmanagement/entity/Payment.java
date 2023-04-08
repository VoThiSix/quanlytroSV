package com.donghh.studentmanagement.entity;

public class Payment {
    private int id; //mã thanh toán
    private String studentCode;
    private double electricityBill;//tiền điện
    private double waterBill;//tiền nước
    private double roomBill;//tiền phòng
    private String datePayment;//ngày thanh toán

    public Payment(int id, String studentCode, double electricityBill, double waterBill,  double roomBill,String datePayment) {
        this.id = id;
        this.studentCode = studentCode;
        this.electricityBill = electricityBill;
        this.waterBill = waterBill;
        this.datePayment = datePayment;
        this.roomBill = roomBill;
    }

    public Payment(String studentCode, double electricityBill, double waterBill,  double roomBill,String datePayment) {
        this.studentCode = studentCode;
        this.electricityBill = electricityBill;
        this.waterBill = waterBill;
        this.datePayment = datePayment;
        this.roomBill = roomBill;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStudentCode() {
        return studentCode;
    }

    public void setStudentCode(String studentCode) {
        this.studentCode = studentCode;
    }

    public double getElectricityBill() {
        return electricityBill;
    }

    public void setElectricityBill(double electricityBill) {
        this.electricityBill = electricityBill;
    }

    public double getWaterBill() {
        return waterBill;
    }

    public void setWaterBill(double waterBill) {
        this.waterBill = waterBill;
    }

    public String getDatePayment() {
        return datePayment;
    }

    public void setDatePayment(String datePayment) {
        this.datePayment = datePayment;
    }

    public double getRoomBill() {
        return roomBill;
    }

    public void setRoomBill(double roomBill) {
        this.roomBill = roomBill;
    }
}
