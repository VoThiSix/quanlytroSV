package com.donghh.studentmanagement.ui.student.detailstudent;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.donghh.studentmanagement.Common;
import com.donghh.studentmanagement.MainActivity;
import com.donghh.studentmanagement.R;
import com.donghh.studentmanagement.database.DatabaseHandler;
import com.donghh.studentmanagement.databinding.ActivityDetailStudentBinding;
import com.donghh.studentmanagement.entity.Department;
import com.donghh.studentmanagement.entity.Room;
import com.donghh.studentmanagement.entity.Student;
import com.donghh.studentmanagement.entity.UpdateStudentEvent;
import com.donghh.studentmanagement.ui.student.AddStudentActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

public class DetailStudentActivity extends AppCompatActivity {

    public static String DetailStudent = "DetailStudent";
    ActivityDetailStudentBinding binding;
    private DatabaseHandler databaseHandler;

    private ArrayList<Department> departmentList = new ArrayList<>();
    private ArrayList<Room> roomList = new ArrayList<>();

    private Student student;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            EventBus.getDefault().register(this);
            binding = ActivityDetailStudentBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());
            databaseHandler = new DatabaseHandler(this, DatabaseHandler.DATABASE_NAME, null, DatabaseHandler.DATABASE_VERSION);
//        departmentList = databaseHandler.getAllDepartment();
//        roomList = databaseHandler.getAllRoom();
            student = getIntent().getParcelableExtra(DetailStudent);
            binding.tvCode.setText(getString(R.string.student_code, student.getStudentCode()));
            binding.tvName.setText(getString(R.string.student_name, student.getStudentName()));
            binding.tvPhone.setText(getString(R.string.student_phone, student.getStudentPhone()));
            binding.tvDepartmentCode.setText(getString(R.string.student_department_code, student.getDepartmentCode()));
            binding.tvRoomCode.setText(getString(R.string.student_room_code, student.getRoomCode()));
            if (student.getSex() == 0) {
                binding.tvSex.setText(getString(R.string.sex, "Nam"));
            } else {
                binding.tvSex.setText(getString(R.string.sex, "Nữ"));

            }
            binding.tvAddress.setText(getString(R.string.address2, student.getAddress()));

            Department department = databaseHandler.getDepartmentByCode(student.getDepartmentCode());
            binding.tvDepartment.setText(getString(R.string.student_department_name, department.getDepartmentName()));

            Room room = databaseHandler.getRoomByCode(student.getRoomCode());
            binding.tvRoom.setText(getString(R.string.student_room_name, room.getRoomName()));

            initListener();

            if (Common.getBoolean(DetailStudentActivity.this, Common.IS_MANAGER)) {
                binding.tvUpdate.setVisibility(View.VISIBLE);
                binding.tvDelete.setVisibility(View.VISIBLE);
            }else {
                binding.tvUpdate.setVisibility(View.GONE);
                binding.tvDelete.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void initListener() {
        try {
            binding.tvUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(DetailStudentActivity.this, AddStudentActivity.class);
                    intent.putExtra(DetailStudentActivity.DetailStudent, student);
                    startActivity(intent);
                }
            });

            binding.tvDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    databaseHandler.deleteStudentByID(student.getId());
                    finish();
                    EventBus.getDefault().post(new UpdateStudentEvent());
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

    @Subscribe
    public void OnUpdateStudentEvent(UpdateStudentEvent event) {
       finish();
    }

}
