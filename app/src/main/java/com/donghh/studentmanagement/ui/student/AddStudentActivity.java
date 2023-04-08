package com.donghh.studentmanagement.ui.student;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.donghh.studentmanagement.R;
import com.donghh.studentmanagement.database.DatabaseHandler;
import com.donghh.studentmanagement.databinding.ActivityAddStudentBinding;
import com.donghh.studentmanagement.entity.Department;
import com.donghh.studentmanagement.entity.Room;
import com.donghh.studentmanagement.entity.Student;
import com.donghh.studentmanagement.entity.UpdateStudentEvent;
import com.donghh.studentmanagement.ui.student.detailstudent.DetailStudentActivity;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class AddStudentActivity extends AppCompatActivity {
    private int positionSex; // 0 là nam, 1 là nữ
    private int positionDepartmentSelect;
    private int positionRoomSelect;
    final Calendar myCalendar = Calendar.getInstance();
    ActivityAddStudentBinding binding;
    private DatabaseHandler databaseHandler;

    private ArrayList<Department> departmentList = new ArrayList<>();
    private ArrayList<String> sexList = new ArrayList<>();
    private ArrayList<Room> roomList = new ArrayList<>(); //danh sách phòng còn trống giường

    private Student student;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddStudentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        databaseHandler = new DatabaseHandler(this, DatabaseHandler.DATABASE_NAME, null, DatabaseHandler.DATABASE_VERSION);
        departmentList = databaseHandler.getAllDepartment();
        roomList = databaseHandler.getAllRoomExistRoom();

        if (getIntent() != null) {
            student = getIntent().getParcelableExtra(DetailStudentActivity.DetailStudent);
        }

        if (student != null) {
            binding.tvTitle.setText("Sửa sinh viên");
            binding.tipStudentCode.setText(student.getStudentCode());
            binding.tipStudentName.setText(student.getStudentName());
            binding.tipStudentPhone.setText(student.getStudentPhone());
            binding.tipStudentAddress.setText(student.getAddress());
            binding.tvAdd.setText("Cập nhật");
        }

        sexList.add("Nam");
        sexList.add("Nữ");
        initListener();
        initSpinnerDepartment();
        initSpinnerRoom();


        initSpinnerSex();
    }

    private void initSpinnerSex() {
        SpinnerSexAdapter spinnerAdapter = new SpinnerSexAdapter(this, sexList);

        binding.spSex.setAdapter(spinnerAdapter);

        if (student != null) {
            positionSex = student.getSex();
            binding.spSex.setSelection(student.getSex());
        }

        //Bắt sự kiện cho Spinner, khi chọn phần tử nào thì hiển thị lên Toast
        binding.spSex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                //đối số postion là vị trí phần tử trong list Data
//                    String msg = "position :" + position + " value :" + list.get(position);
//                    Toast.makeText(SpinnerActivity.this, msg, Toast.LENGTH_SHORT).show();
                positionSex = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
//                    Toast.makeText(SpinnerActivity.this, "onNothingSelected", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initSpinnerRoom() {
        try {
            SpinnerRoomAdapter spinnerAdapter = new SpinnerRoomAdapter(this, roomList);

            binding.spRoom.setAdapter(spinnerAdapter);
            if (student != null) {
                for (int i = 0; i < roomList.size(); i++) {
                    if (roomList.get(i).getRoomCode().equals(student.getRoomCode())) {
                        positionRoomSelect = i;
                        binding.spRoom.setSelection(i);
                        break;

                    }
                }
            }
            //Bắt sự kiện cho Spinner, khi chọn phần tử nào thì hiển thị lên Toast
            binding.spRoom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    //đối số postion là vị trí phần tử trong list Data
//                    String msg = "position :" + position + " value :" + list.get(position);
//                    Toast.makeText(SpinnerActivity.this, msg, Toast.LENGTH_SHORT).show();
                    positionRoomSelect = position;
                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
//                    Toast.makeText(SpinnerActivity.this, "onNothingSelected", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initListener() {
        try {
            DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int day) {
                    myCalendar.set(Calendar.YEAR, year);
                    myCalendar.set(Calendar.MONTH, month);
                    myCalendar.set(Calendar.DAY_OF_MONTH, day);
                    updateLabel();
                }
            };
            binding.tipStudentBirthday.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new DatePickerDialog(AddStudentActivity.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                }
            });

            binding.tvAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (student != null) {
                        databaseHandler.updateStudentById(student.getId(),binding.tipStudentCode.getText().toString(), binding.tipStudentName.getText().toString(),
                                binding.tipStudentBirthday.getText().toString(),
                                binding.tipStudentPhone.getText().toString(),
                                positionSex,
                                binding.tipStudentAddress.getText().toString(),
                                departmentList.get(positionDepartmentSelect).getDepartmentCode(),
                                roomList.get(positionRoomSelect).getRoomCode()
                        );

                        EventBus.getDefault().post(new UpdateStudentEvent());
                        finish();
                        Toast.makeText(AddStudentActivity.this, getString(R.string.update_student_success), Toast.LENGTH_SHORT).show();
                    } else {
                        if (!binding.tipStudentCode.getText().toString().isEmpty()) {
                            if (!databaseHandler.IsExistStudentByStudentCode(binding.tipStudentCode.getText().toString())) {
                                databaseHandler.addStudent(new Student(binding.tipStudentCode.getText().toString(), binding.tipStudentName.getText().toString(),
                                        binding.tipStudentBirthday.getText().toString(),
                                        binding.tipStudentPhone.getText().toString(),
                                        positionSex,
                                        binding.tipStudentAddress.getText().toString(),
                                        departmentList.get(positionDepartmentSelect).getDepartmentCode(),
                                        roomList.get(positionRoomSelect).getRoomCode()
                                ));
                                binding.tipStudentCode.getText().clear();
                                binding.tipStudentName.getText().clear();
                                binding.tipStudentPhone.getText().clear();
                                binding.tipStudentAddress.getText().clear();

                                Toast.makeText(AddStudentActivity.this, getString(R.string.add_student_success), Toast.LENGTH_SHORT).show();

                                EventBus.getDefault().post(new UpdateStudentEvent());
                                initSpinnerRoom();
                                positionRoomSelect = 0;

                            } else {
                                Toast.makeText(AddStudentActivity.this, getString(R.string.exist_student), Toast.LENGTH_SHORT).show();

                            }

                        }
                    }
                }
            });

            binding.ivBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateLabel() {
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
        binding.tipStudentBirthday.setText(dateFormat.format(myCalendar.getTime()));
    }

    private void initSpinnerDepartment() {
        try {

            SpinnerDepartmentAdapter spinnerAdapter = new SpinnerDepartmentAdapter(this, departmentList);

            binding.spDepartment.setAdapter(spinnerAdapter);
            if (student != null) {
                for (int i = 0; i < departmentList.size(); i++) {
                    if (departmentList.get(i).getDepartmentCode().equals(student.getDepartmentCode())) {
                        positionDepartmentSelect = i;
                        binding.spDepartment.setSelection(i);
                        break;

                    }
                }
            }
            //Bắt sự kiện cho Spinner, khi chọn phần tử nào thì hiển thị lên Toast
            binding.spDepartment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    //đối số postion là vị trí phần tử trong list Data
//                    String msg = "position :" + position + " value :" + list.get(position);
//                    Toast.makeText(SpinnerActivity.this, msg, Toast.LENGTH_SHORT).show();
                    positionDepartmentSelect = position;
                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
//                    Toast.makeText(SpinnerActivity.this, "onNothingSelected", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
