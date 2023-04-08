package com.donghh.studentmanagement.ui.room;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.donghh.studentmanagement.R;
import com.donghh.studentmanagement.database.DatabaseHandler;
import com.donghh.studentmanagement.databinding.ActivityListStudentByRoomBinding;
import com.donghh.studentmanagement.entity.Room;
import com.donghh.studentmanagement.entity.Student;
import com.donghh.studentmanagement.ui.student.StudentAdapter;
import com.donghh.studentmanagement.ui.student.detailstudent.DetailStudentActivity;

public class ListStudentByRoomActivity extends AppCompatActivity {

    private ActivityListStudentByRoomBinding binding;

    public static String ROOM = "ROOM";
    private StudentAdapter studentAdapter;

    private DatabaseHandler databaseHandler;

    private Room room;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListStudentByRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        room= (Room) getIntent().getSerializableExtra(ROOM);
        databaseHandler = new DatabaseHandler(ListStudentByRoomActivity.this, DatabaseHandler.DATABASE_NAME, null, DatabaseHandler.DATABASE_VERSION);

        binding.tvTitle.setText(getString(R.string.list_student_room,room.getRoomName()));
        initRecyclerListStudent();
        initListener();
    }

    private void initListener() {
        binding.ivBack.setOnClickListener(view -> {
            finish();
        });
    }

    private void initRecyclerListStudent() {
        try {

            studentAdapter = new StudentAdapter(databaseHandler.getAllStudentByRoomCode(room.getRoomCode()), new StudentAdapter.IStudent() {
                @Override
                public void onClickStudent(Student student) {
                    Intent intent = new Intent(ListStudentByRoomActivity.this, DetailStudentActivity.class);
                    intent.putExtra(DetailStudentActivity.DetailStudent, student);
                    startActivity(intent);

                }
            });
            binding.rvStudent.setAdapter(studentAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}