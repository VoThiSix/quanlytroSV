package com.donghh.studentmanagement.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.donghh.studentmanagement.entity.Account;
import com.donghh.studentmanagement.entity.CategoryRoom;
import com.donghh.studentmanagement.entity.Department;
import com.donghh.studentmanagement.entity.Payment;
import com.donghh.studentmanagement.entity.Room;
import com.donghh.studentmanagement.entity.Student;

import java.util.ArrayList;

public
class DatabaseHandler extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "StudentManager";
    public static final int DATABASE_VERSION = 1;

    private static final String TABLE_ACCOUNT = "Account";
    private static final String KEY_ID = "ID";
    private static final String KEY_USERNAME = "UserName";
    private static final String KEY_PASSWORD = "Password";
    private static final String KEY_MANAGER = "Manager";


    private static final String TABLE_STUDENT = "Students";
    private static final String KEY_STUDENT_CODE = "StudentCode";
    private static final String KEY_STUDENT_NAME = "StudentName";
    private static final String KEY_STUDENT_BIRTHDAY = "StudentBirthday";
    private static final String KEY_STUDENT_SEX = "StudentSex";
    private static final String KEY_STUDENT_ADDRESS = "StudentAddress";
    private static final String KEY_STUDENT_PHONE = "StudentPhone";

    //khoa
    private static final String TABLE_DEPARTMENT = "Department";
    private static final String KEY_DEPARTMENT_CODE = "DepartmentCode";
    private static final String KEY_DEPARTMENT_NAME = "DepartmentName";

    //loại phòng
    private static final String TABLE_CATEGORY_ROOM = "CategoryRoom";
    private static final String KEY_CATEGORY_ROOM_CODE = "CategoryRoomCode";
    private static final String KEY_CATEGORY_ROOM_NAME = "CategoryRoomName";
    private static final String KEY_CATEGORY_ROOM_NUMBER_ROOM = "CategoryRoomNumberRoom";
    private static final String KEY_CATEGORY_ROOM_PRICE = "CategoryRoomPrice";

    //phòng
    private static final String TABLE_ROOM = "Room";
    private static final String KEY_ROOM_CODE = "RoomCode";
    private static final String KEY_ROOM_NAME = "RoomName";

    //thanh toán
    private static final String TABLE_PAYMENT = "Payment";
    private static final String KEY_ElectricityBill = "ElectricityBill";
    private static final String KEY_WaterBill = "WaterBill";
    private static final String KEY_RoomBill = "RoomBill";
    private static final String KEY_DatePayment = "DatePayment";

    public DatabaseHandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String createTableAccount = String.format("CREATE TABLE %s(%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT, %s INTEGER)",
                TABLE_ACCOUNT, KEY_ID, KEY_USERNAME, KEY_PASSWORD,KEY_MANAGER);
        db.execSQL(createTableAccount);


        String createTableStudent = String.format("CREATE TABLE %s(%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s INTEGER, %s TEXT, %s TEXT, %s TEXT)",
                TABLE_STUDENT, KEY_ID, KEY_STUDENT_CODE, KEY_STUDENT_NAME, KEY_STUDENT_BIRTHDAY,
                KEY_STUDENT_PHONE, KEY_STUDENT_SEX, KEY_STUDENT_ADDRESS, KEY_DEPARTMENT_CODE, KEY_ROOM_CODE);
        db.execSQL(createTableStudent);

        String createTableDepartment = String.format("CREATE TABLE %s(%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT)",
                TABLE_DEPARTMENT, KEY_ID, KEY_DEPARTMENT_CODE, KEY_DEPARTMENT_NAME);
        db.execSQL(createTableDepartment);

        String createTableCategoryRoom = String.format("CREATE TABLE %s(%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT,%s INTEGER, %s DOUBLE)",
                TABLE_CATEGORY_ROOM, KEY_ID, KEY_CATEGORY_ROOM_CODE, KEY_CATEGORY_ROOM_NAME,
                KEY_CATEGORY_ROOM_NUMBER_ROOM, KEY_CATEGORY_ROOM_PRICE);
        db.execSQL(createTableCategoryRoom);

        String createTableRoom = String.format("CREATE TABLE %s(%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT,%s TEXT)",
                TABLE_ROOM, KEY_ID, KEY_ROOM_CODE, KEY_ROOM_NAME, KEY_CATEGORY_ROOM_CODE);
        db.execSQL(createTableRoom);

        String createTablePayment = String.format("CREATE TABLE %s(%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s DOUBLE,%s DOUBLE,%s DOUBLE,%s TEXT)",
                TABLE_PAYMENT, KEY_ID, KEY_STUDENT_CODE, KEY_ElectricityBill, KEY_WaterBill, KEY_RoomBill, KEY_DatePayment);
        db.execSQL(createTablePayment);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTableAccount = String.format("DROP TABLE IF EXISTS %s", TABLE_ACCOUNT);
        db.execSQL(dropTableAccount);

        String dropTableStudent = String.format("DROP TABLE IF EXISTS %s", TABLE_STUDENT);
        db.execSQL(dropTableStudent);

        String dropTableDepartment = String.format("DROP TABLE IF EXISTS %s", TABLE_DEPARTMENT);
        db.execSQL(dropTableDepartment);

        String dropTableCategoryRoom = String.format("DROP TABLE IF EXISTS %s", TABLE_CATEGORY_ROOM);
        db.execSQL(dropTableCategoryRoom);

        String dropTableRoom = String.format("DROP TABLE IF EXISTS %s", TABLE_ROOM);
        db.execSQL(dropTableRoom);

        String dropTablePayment = String.format("DROP TABLE IF EXISTS %s", TABLE_PAYMENT);
        db.execSQL(dropTablePayment);

        onCreate(db);
    }


    //thanh toán
    public ArrayList<Payment> getAllBillByForDate(String startDate, String endDate) {
        ArrayList<Payment> list = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_PAYMENT + " WHERE " + KEY_DatePayment + " >= '" + startDate + "'" + " AND " + KEY_DatePayment + " <='" + endDate + "'";


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Payment item = new Payment(cursor.getInt(0), cursor.getString(1), cursor.getDouble(2), cursor.getDouble(3), cursor.getDouble(4), cursor.getString(5));
            list.add(item);
            cursor.moveToNext();
        }
        return list;
    }

    //thanh toán

    public ArrayList<Payment> getAllPaymentsBKeySearch(String keySearch) {
        ArrayList<Payment> payments = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_PAYMENT + " WHERE " + KEY_STUDENT_CODE + " LIKE '%" + keySearch + "%'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        while (cursor.isAfterLast() == false) {
            Payment student = new Payment(cursor.getInt(0), cursor.getString(1), cursor.getDouble(2), cursor.getDouble(3), cursor.getDouble(4), cursor.getString(5));
            payments.add(student);
            cursor.moveToNext();
        }
        return payments;
    }

    public ArrayList<Payment> getAllPayment() {
        ArrayList<Payment> departmentList = new ArrayList<>();
        String query = String.format("SELECT * FROM %s", TABLE_PAYMENT);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        while (cursor.isAfterLast() == false) {
            Payment department = new Payment(cursor.getInt(0), cursor.getString(1), cursor.getDouble(2), cursor.getDouble(3), cursor.getDouble(4), cursor.getString(5));
            departmentList.add(department);
            cursor.moveToNext();
        }
        return departmentList;
    }

    public ArrayList<Account> getAllAccount() {
        ArrayList<Account> accountArrayList = new ArrayList<>();
        String query = String.format("SELECT * FROM %s", TABLE_ACCOUNT);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Account account = new Account(cursor.getInt(0), cursor.getString(1), cursor.getString(2),cursor.getInt(3));
            accountArrayList.add(account);
            cursor.moveToNext();
        }
        return accountArrayList;
    }

    public void addPayment(Payment payment) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_STUDENT_CODE, payment.getStudentCode());
        values.put(KEY_ElectricityBill, payment.getElectricityBill());
        values.put(KEY_WaterBill, payment.getWaterBill());
        values.put(KEY_RoomBill, payment.getRoomBill());
        values.put(KEY_DatePayment, payment.getDatePayment());


        db.insert(TABLE_PAYMENT, null, values);
        db.close();
    }
    public void addAccount(Account account) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_USERNAME, account.getUserName());
        values.put(KEY_PASSWORD, account.getPassword());
        values.put(KEY_MANAGER, account.getIsManager());

        db.insert(TABLE_ACCOUNT, null, values);
        db.close();
    }

    //phòng
    public void addRoom(Room room) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ROOM_CODE, room.getRoomCode());
        values.put(KEY_ROOM_NAME, room.getRoomName());
        values.put(KEY_CATEGORY_ROOM_CODE, room.getCategoryRoomCode());


        db.insert(TABLE_ROOM, null, values);
        db.close();
    }

    public Room getRoomByCode(String roomCode) {
        String query = String.format("SELECT * FROM %s WHERE " + KEY_ROOM_CODE + " = \'%s\'", TABLE_ROOM, roomCode);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        Room room = new Room(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
        return room;
    }

    public Account getAccountByUserName(String userName) {
        String query = String.format("SELECT * FROM %s WHERE " + KEY_USERNAME + " = \'%s\'", TABLE_ACCOUNT, userName);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        Account room = new Account(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3));
        return room;
    }


    public ArrayList<Room> getAllRoom() {
        ArrayList<Room> roomList = new ArrayList<>();
        String query = String.format("SELECT * FROM %s", TABLE_ROOM);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        while (cursor.isAfterLast() == false) {
            Room room = new Room(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
            roomList.add(room);
            cursor.moveToNext();
        }
        return roomList;
    }

    /**
     * danh sách các phòng còn phòng trống
     *
     * @return
     */
    public ArrayList<Room> getAllRoomExistRoom() {
        ArrayList<Room> roomList = new ArrayList<>();
        String query = String.format("SELECT * FROM %s", TABLE_ROOM);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Room room = new Room(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
            CategoryRoom categoryRoom = getCategoryRoomByCode(room.getCategoryRoomCode());
            //số sinh viên của phòng này
            ArrayList<Student> listStudent = getAllStudentByRoomCode(room.getRoomCode());
            if (categoryRoom.getNumberRoom() > listStudent.size()) {
                roomList.add(room);
            }
            cursor.moveToNext();
        }
        return roomList;
    }

    //loại phòng
    public void addCategoryRoom(CategoryRoom categoryRoom) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_CATEGORY_ROOM_CODE, categoryRoom.getCategoryRoomCode());
        values.put(KEY_CATEGORY_ROOM_NAME, categoryRoom.getCategoryRoomName());
        values.put(KEY_CATEGORY_ROOM_NUMBER_ROOM, categoryRoom.getNumberRoom());
        values.put(KEY_CATEGORY_ROOM_PRICE, categoryRoom.getPrice());

        db.insert(TABLE_CATEGORY_ROOM, null, values);
        db.close();
    }

    public ArrayList<CategoryRoom> getAllCategoryRoom() {
        ArrayList<CategoryRoom> departmentList = new ArrayList<>();
        String query = String.format("SELECT * FROM %s", TABLE_CATEGORY_ROOM);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        while (cursor.isAfterLast() == false) {
            CategoryRoom department = new CategoryRoom(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3), cursor.getDouble(4));
            departmentList.add(department);
            cursor.moveToNext();
        }
        return departmentList;
    }

    public CategoryRoom getCategoryRoomByCode(String categoryCode) {
        String query = String.format("SELECT * FROM %s WHERE %s = '%s'", TABLE_CATEGORY_ROOM, KEY_CATEGORY_ROOM_CODE, categoryCode);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        CategoryRoom categoryRoom = new CategoryRoom(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3), cursor.getDouble(4));

        return categoryRoom;
    }

    public ArrayList<Student> getAllStudentByRoomCode(String roomCode) {
        ArrayList<Student> studentList = new ArrayList<>();

        String query = String.format("SELECT * FROM %s WHERE %s = '%s'", TABLE_STUDENT, KEY_ROOM_CODE, roomCode);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Student student = new Student(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4)
                    , cursor.getInt(5), cursor.getString(6), cursor.getString(7), cursor.getString(8));
            studentList.add(student);
            cursor.moveToNext();
        }

        return studentList;
    }


    //thao tác bảng khoa
    public void addDepartment(Department department) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_DEPARTMENT_CODE, department.getDepartmentCode());
        values.put(KEY_DEPARTMENT_NAME, department.getDepartmentName());

        db.insert(TABLE_DEPARTMENT, null, values);
        db.close();
    }

    public Department getDepartmentByCode(String departmentCode) {
        String query = String.format("SELECT * FROM %s WHERE " + KEY_DEPARTMENT_CODE + " = \'%s\'", TABLE_DEPARTMENT, departmentCode);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        Department department = new Department(cursor.getInt(0), cursor.getString(1), cursor.getString(2));
        return department;
    }

    public ArrayList<Department> getAllDepartment() {
        ArrayList<Department> departmentList = new ArrayList<>();
        String query = String.format("SELECT * FROM %s", TABLE_DEPARTMENT);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Department department = new Department(cursor.getInt(0), cursor.getString(1), cursor.getString(2));
            departmentList.add(department);
            cursor.moveToNext();
        }
        return departmentList;
    }

    public void deleteStudentByID(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("delete from " + TABLE_STUDENT + " where " + KEY_ID + "='" + id + "'");
    }

    public void updateStudentById(int id, String studentCode, String studentName, String birthday, String phone, int sex, String address, String departmentCode, String roomCode) {
        SQLiteDatabase db = this.getWritableDatabase();
        String strSQL = "UPDATE " + TABLE_STUDENT + " SET " + KEY_STUDENT_CODE + " = \'" + studentCode + "\', " + KEY_STUDENT_NAME + " = \'" + studentName +
                "\', " + KEY_STUDENT_BIRTHDAY + " = \'" + birthday +
                "\', " + KEY_STUDENT_PHONE + " = \'" + phone +
                "\', " + KEY_DEPARTMENT_CODE + "= \'" + departmentCode +
                "\', " + KEY_ROOM_CODE + " = \'" + roomCode +
                "\', " + KEY_STUDENT_SEX + " = \'" + sex +
                "\', " + KEY_STUDENT_ADDRESS + " = \'" + address +
                "\'" + " WHERE " + KEY_ID + " = " + id;

        db.execSQL(strSQL);
    }
    public void updateAccountByUserName( String userName, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        String strSQL = "UPDATE " + TABLE_ACCOUNT + " SET " + KEY_PASSWORD + " = \'" + password + "\' " + " WHERE " + KEY_USERNAME + " =  \'" + userName + "\' ";

        db.execSQL(strSQL);
    }


    //thao tác với bảng sinh viên
    public void addStudent(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_STUDENT_CODE, student.getStudentCode());
        values.put(KEY_STUDENT_NAME, student.getStudentName());
        values.put(KEY_STUDENT_BIRTHDAY, student.getStudentBirthday());
        values.put(KEY_STUDENT_PHONE, student.getStudentPhone());
        values.put(KEY_DEPARTMENT_CODE, student.getDepartmentCode());
        values.put(KEY_ROOM_CODE, student.getRoomCode());
        values.put(KEY_STUDENT_SEX, student.getSex());
        values.put(KEY_STUDENT_ADDRESS, student.getAddress());

        db.insert(TABLE_STUDENT, null, values);
        db.close();
    }

    public Student getStudentByStudentCode(String studentCode) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_STUDENT, null, KEY_STUDENT_CODE + " = ?", new String[]{String.valueOf(studentCode)}, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        Student student = new Student(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4),
                cursor.getInt(5), cursor.getString(6), cursor.getString(7), cursor.getString(8));
        return student;
    }

    public ArrayList<Student> getAllStudents() {
        ArrayList<Student> studentList = new ArrayList<>();
        String query = String.format("SELECT * FROM %s", TABLE_STUDENT);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Student student = new Student(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4),
                    cursor.getInt(5), cursor.getString(6), cursor.getString(7), cursor.getString(8));
            studentList.add(student);
            cursor.moveToNext();
        }
        return studentList;
    }

    public ArrayList<Student> getAllStudentsBKeySearch(String keySearch) {
        ArrayList<Student> studentList = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_STUDENT + " WHERE " + KEY_STUDENT_CODE + " LIKE '%" + keySearch + "%'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Student student = new Student(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4),
                    cursor.getInt(5), cursor.getString(6), cursor.getString(7), cursor.getString(8));
            studentList.add(student);
            cursor.moveToNext();
        }
        return studentList;
    }

    public boolean IsExistStudentByStudentCode(String studentCode) {
        String query = String.format("SELECT * FROM %s WHERE " + KEY_STUDENT_CODE + " = \'%s\'", TABLE_STUDENT, studentCode);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.getCount() <= 0) {
            cursor.close();
            return false;
        }
        return true;
    }

}
