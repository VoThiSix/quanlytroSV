package com.donghh.studentmanagement.ui.payment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.donghh.studentmanagement.database.DatabaseHandler;
import com.donghh.studentmanagement.databinding.ActivitySearchPaymentBinding;
import com.donghh.studentmanagement.entity.Department;
import com.donghh.studentmanagement.entity.Payment;
import com.donghh.studentmanagement.entity.Room;
import com.donghh.studentmanagement.entity.Student;
import com.donghh.studentmanagement.ui.student.StudentAdapter;

import java.util.ArrayList;

public class SearchPaymentActivity extends AppCompatActivity {

    ActivitySearchPaymentBinding binding;
    private DatabaseHandler databaseHandler;

    private PaymentAdapter paymentAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchPaymentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        databaseHandler = new DatabaseHandler(this, DatabaseHandler.DATABASE_NAME, null, DatabaseHandler.DATABASE_VERSION);

        initRecyclerListPayment(databaseHandler.getAllPayment());
        initListener();


    }
    private void initRecyclerListPayment(ArrayList<Payment> data) {
        try {
            binding.rvPayment.setLayoutManager(new LinearLayoutManager(SearchPaymentActivity.this, LinearLayoutManager.VERTICAL, false));
            paymentAdapter = new PaymentAdapter(SearchPaymentActivity.this,data, new PaymentAdapter.IPayment() {

                @Override
                public void onClickPayment(Payment item) {

                }
            });
            binding.rvPayment.setAdapter(paymentAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initListener() {
        try {
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
                        initRecyclerListPayment(databaseHandler.getAllPayment());

                    } else {
                        initRecyclerListPayment(databaseHandler.getAllPaymentsBKeySearch(binding.tipStudentCode.getText().toString()));
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

}
