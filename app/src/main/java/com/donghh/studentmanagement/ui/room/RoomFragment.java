package com.donghh.studentmanagement.ui.room;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;

import com.donghh.studentmanagement.R;
import com.donghh.studentmanagement.database.DatabaseHandler;
import com.donghh.studentmanagement.databinding.FragmentRoomBinding;
import com.donghh.studentmanagement.entity.Room;
import com.donghh.studentmanagement.entity.Student;
import com.donghh.studentmanagement.ui.student.detailstudent.DetailStudentActivity;

public class RoomFragment extends Fragment  implements LifecycleOwner {

    private FragmentRoomBinding binding;

    private RoomAdapter roomAdapter;

    private DatabaseHandler databaseHandler;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public static RoomFragment newInstance() {
        
        Bundle args = new Bundle();
        
        RoomFragment fragment = new RoomFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_room, container, false);
        binding = FragmentRoomBinding.bind(view);


        databaseHandler = new DatabaseHandler(getContext(), DatabaseHandler.DATABASE_NAME, null, DatabaseHandler.DATABASE_VERSION);
        initRecyclerListRoom();

//        binding.flbAddStudent.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent= new Intent(getContext(), AddStudentActivity.class);
//                startActivity(intent);
//            }
//        });
        return view;
    }
    private void initRecyclerListRoom() {
        try{

            roomAdapter = new RoomAdapter(getContext(),databaseHandler.getAllRoom(), new RoomAdapter.IRoomListener() {
                @Override
                public void onClickRoom(Room room) {
                    Intent intent= new Intent(getContext(), ListStudentByRoomActivity.class);
                    intent.putExtra(ListStudentByRoomActivity.ROOM,room);
                    startActivity(intent);

                }
            });
            binding.rvRoom.setAdapter(roomAdapter);
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