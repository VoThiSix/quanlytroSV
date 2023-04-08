package com.donghh.studentmanagement.ui.student;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.donghh.studentmanagement.R;
import com.donghh.studentmanagement.databinding.ItemStudentBinding;
import com.donghh.studentmanagement.entity.Student;

import java.util.ArrayList;

public class StudentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<Student> userArrayList;
    private IStudent iStudentListener;



    public StudentAdapter(ArrayList<Student> userArrayList,IStudent iStudentListener) {
        this.userArrayList = userArrayList;
        this.iStudentListener = iStudentListener;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_student, parent, false);
        return new RecyclerViewViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Student user = userArrayList.get(position);
        RecyclerViewViewHolder viewHolder = (RecyclerViewViewHolder) holder;

        viewHolder.binding.tvStudentName.setText(user.getStudentName());
        viewHolder.binding.tvStudentCode.setText(user.getStudentCode());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iStudentListener.onClickStudent(user);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }

    public void updateUserList(final ArrayList<Student> userArrayList) {
        this.userArrayList.clear();
        this.userArrayList = userArrayList;
        notifyDataSetChanged();
    }

    class RecyclerViewViewHolder extends RecyclerView.ViewHolder {
        ItemStudentBinding binding;

        public RecyclerViewViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemStudentBinding.bind(itemView);


        }
    }

    public  interface IStudent{
        void onClickStudent(Student student);
    }
}
