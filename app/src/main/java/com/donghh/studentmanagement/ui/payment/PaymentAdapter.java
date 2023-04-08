package com.donghh.studentmanagement.ui.payment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.donghh.studentmanagement.R;
import com.donghh.studentmanagement.database.DatabaseHandler;
import com.donghh.studentmanagement.databinding.ItemPaymentBinding;
import com.donghh.studentmanagement.entity.Payment;
import com.donghh.studentmanagement.entity.Student;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

public class PaymentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<Payment> userArrayList;
    private IPayment iStudentListener;
    private Context context;
    private DatabaseHandler databaseHandler;


    public PaymentAdapter(Context context,ArrayList<Payment> userArrayList, IPayment iStudentListener) {
        this.context = context;
        this.userArrayList = userArrayList;
        this.iStudentListener = iStudentListener;
        databaseHandler = new DatabaseHandler(context, DatabaseHandler.DATABASE_NAME, null, DatabaseHandler.DATABASE_VERSION);
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_payment, parent, false);
        return new RecyclerViewViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Payment payment = userArrayList.get(position);
        RecyclerViewViewHolder viewHolder = (RecyclerViewViewHolder) holder;

        Student student=databaseHandler.getStudentByStudentCode(payment.getStudentCode());


        NumberFormat formatter = new DecimalFormat("#,###");

        viewHolder.binding.tvPaymentCode.setText(context.getString(R.string.payment_code,String.valueOf(payment.getId())));
        viewHolder.binding.tvStudentCode.setText(context.getString(R.string.student_code,payment.getStudentCode()));
        viewHolder.binding.tvStudentName.setText(context.getString(R.string.student_name,student.getStudentName()));
        viewHolder.binding.tvElectricityBill.setText(context.getString(R.string.electricity_bill_format, formatter.format(payment.getElectricityBill())));
        viewHolder.binding.tvWaterBill.setText(context.getString(R.string.water_bill_format, formatter.format(payment.getWaterBill())));
        viewHolder.binding.tvRoomBill.setText(context.getString(R.string.room_bill_format, formatter.format(payment.getRoomBill())));
        viewHolder.binding.tvDatePayment.setText(context.getString(R.string.date_payment_format,payment.getDatePayment()));

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iStudentListener.onClickPayment(payment);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }

    public void updateUserList(final ArrayList<Payment> userArrayList) {
        this.userArrayList.clear();
        this.userArrayList = userArrayList;
        notifyDataSetChanged();
    }

    class RecyclerViewViewHolder extends RecyclerView.ViewHolder {
        ItemPaymentBinding binding;

        public RecyclerViewViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemPaymentBinding.bind(itemView);


        }
    }

    public  interface IPayment {
        void onClickPayment(Payment item);
    }
}
