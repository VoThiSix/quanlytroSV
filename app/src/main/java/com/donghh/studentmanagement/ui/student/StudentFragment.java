package com.donghh.studentmanagement.ui.student;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;

import com.donghh.studentmanagement.Common;
import com.donghh.studentmanagement.MainActivity;
import com.donghh.studentmanagement.R;
import com.donghh.studentmanagement.database.DatabaseHandler;
import com.donghh.studentmanagement.databinding.FragmentHomeBinding;
import com.donghh.studentmanagement.entity.Student;
import com.donghh.studentmanagement.entity.UpdateStudentEvent;
import com.donghh.studentmanagement.ui.student.detailstudent.DetailStudentActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

public class StudentFragment extends Fragment  implements LifecycleOwner {

    private FragmentHomeBinding binding;

    private StudentAdapter studentAdapter;

    private   DatabaseHandler databaseHandler;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);

    }

    public static StudentFragment newInstance() {
        
        Bundle args = new Bundle();
        
        StudentFragment fragment = new StudentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        binding = FragmentHomeBinding.bind(view);


        databaseHandler = new DatabaseHandler(getContext(), DatabaseHandler.DATABASE_NAME, null, DatabaseHandler.DATABASE_VERSION);
        initRecyclerListStudent(databaseHandler.getAllStudents());

        if (Common.getBoolean(getContext(), Common.IS_MANAGER)) {
            binding.flbAddStudent.setVisibility(View.VISIBLE);
        }else {
            binding.flbAddStudent.setVisibility(View.GONE);

        }
        binding.flbAddStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getContext(), AddStudentActivity.class);
                startActivity(intent);
            }
        });


        binding.tipStudentCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (binding.tipStudentCode.getText().toString().isEmpty()) {
                    initRecyclerListStudent(databaseHandler.getAllStudents());
                } else {
                    initRecyclerListStudent(databaseHandler.getAllStudentsBKeySearch(binding.tipStudentCode.getText().toString()));

                }

            }
        });
        return view;
    }
    @Subscribe
    public void OnUpdateStudentEvent(UpdateStudentEvent event) {
        studentAdapter.updateUserList(databaseHandler.getAllStudents());
    }
    private void initRecyclerListStudent(ArrayList<Student> studentList) {
        try{

            studentAdapter = new StudentAdapter(studentList, new StudentAdapter.IStudent() {
                @Override
                public void onClickStudent(Student student) {
                    Intent intent= new Intent(getContext(), DetailStudentActivity.class);
                    intent.putExtra(DetailStudentActivity.DetailStudent,student);
                    startActivity(intent);



                }
            });
            binding.rvStudent.setAdapter(studentAdapter);
        }catch(Exception e){
          e.printStackTrace();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}