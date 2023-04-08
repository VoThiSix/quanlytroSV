package com.donghh.studentmanagement.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class Student implements Parcelable {
    private int id;
    private String studentCode;
    private String studentName;
    private String studentBirthday;
    private String studentPhone;
    private int sex;//giới tính
    private String address;//địa chỉ
    private String departmentCode;//mã khoa
    private String roomCode; //mã phòng


    protected Student(Parcel in) {
        id = in.readInt();
        studentCode = in.readString();
        studentName = in.readString();
        studentBirthday = in.readString();
        studentPhone = in.readString();
        sex = in.readInt();
        address = in.readString();
        departmentCode = in.readString();
        roomCode = in.readString();
    }

    public static final Creator<Student> CREATOR = new Creator<Student>() {
        @Override
        public Student createFromParcel(Parcel in) {
            return new Student(in);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }




    public Student(String studentCode, String studentName, String studentBirthday, String studentPhone, int sex, String address, String departmentCode, String roomCode) {
        this.studentCode = studentCode;
        this.studentName = studentName;
        this.studentBirthday = studentBirthday;
        this.studentPhone = studentPhone;
        this.sex = sex;
        this.address = address;
        this.departmentCode = departmentCode;
        this.roomCode = roomCode;
    }


    public Student(int id, String studentCode, String studentName, String studentBirthday, String studentPhone, int sex, String address, String departmentCode, String roomCode) {
        this.id = id;
        this.studentCode = studentCode;
        this.studentName = studentName;
        this.studentBirthday = studentBirthday;
        this.studentPhone = studentPhone;
        this.sex = sex;
        this.address = address;
        this.departmentCode = departmentCode;
        this.roomCode = roomCode;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    public String getRoomCode() {
        return roomCode;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentCode() {
        return studentCode;
    }

    public void setStudentCode(String studentCode) {
        this.studentCode = studentCode;
    }

    public String getStudentBirthday() {
        return studentBirthday;
    }

    public void setStudentBirthday(String studentBirthday) {
        this.studentBirthday = studentBirthday;
    }

    public String getStudentPhone() {
        return studentPhone;
    }

    public void setStudentPhone(String studentPhone) {
        this.studentPhone = studentPhone;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(studentCode);
        dest.writeString(studentName);
        dest.writeString(studentBirthday);
        dest.writeString(studentPhone);
        dest.writeInt(sex);
        dest.writeString(address);
        dest.writeString(departmentCode);
        dest.writeString(roomCode);
    }
}
