package com.donghh.studentmanagement.ui.room;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.donghh.studentmanagement.R;
import com.donghh.studentmanagement.database.DatabaseHandler;
import com.donghh.studentmanagement.databinding.ItemRoomBinding;
import com.donghh.studentmanagement.entity.CategoryRoom;
import com.donghh.studentmanagement.entity.Room;
import com.donghh.studentmanagement.entity.Student;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

public class RoomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<Room> userArrayList;
    private IRoomListener iStudentListener;
    private DatabaseHandler databaseHandler;



    public RoomAdapter(Context context,ArrayList<Room> userArrayList, IRoomListener iStudentListener) {
        this.userArrayList = userArrayList;
        this.iStudentListener = iStudentListener;
        databaseHandler = new DatabaseHandler(context, DatabaseHandler.DATABASE_NAME, null, DatabaseHandler.DATABASE_VERSION);

    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_room, parent, false);
        return new RecyclerViewViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Room room = userArrayList.get(position);
        RecyclerViewViewHolder viewHolder = (RecyclerViewViewHolder) holder;

        CategoryRoom categoryRoom =databaseHandler.getCategoryRoomByCode(room.getCategoryRoomCode());
        viewHolder.binding.tvRoomName.setText(room.getRoomName());
        viewHolder.binding.tvCategoryRoomName.setText("Tên loại phòng: "+categoryRoom.getCategoryRoomName());
        switch (categoryRoom.getCategoryRoomCode()){
            case "VIP1":
                viewHolder.binding.ivAvatar.setImageResource(R.drawable.ic_bedroom);
                break;
            case "VIP2":
                viewHolder.binding.ivAvatar.setImageResource(R.drawable.ic_bedroom2);
                break;
            case "VIP3":
                viewHolder.binding.ivAvatar.setImageResource(R.drawable.ic_bedroom3);
                break;
            default:
                viewHolder.binding.ivAvatar.setImageResource(R.drawable.ic_bedroom);
                break;

        }

        NumberFormat formatter = new DecimalFormat("#,###");
        viewHolder.binding.tvPrice.setText("Giá phòng: "+formatter.format(categoryRoom.getPrice())+"đ");

        //Tính số giường trống của phòng này
        ArrayList<Student> listStudent= databaseHandler.getAllStudentByRoomCode(room.getRoomCode());//số sinh viên của phòng này
        viewHolder.binding.tvNumberRoomEmpty.setText("Số giường trống: "+String.valueOf(categoryRoom.getNumberRoom()-listStudent.size()));

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iStudentListener.onClickRoom(room);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }

    public void updateUserList(final ArrayList<Room> userArrayList) {
        this.userArrayList.clear();
        this.userArrayList = userArrayList;
        notifyDataSetChanged();
    }

    class RecyclerViewViewHolder extends RecyclerView.ViewHolder {
        ItemRoomBinding binding;

        public RecyclerViewViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemRoomBinding.bind(itemView);


        }
    }

    public  interface IRoomListener {
        void onClickRoom(Room room);
    }
}
