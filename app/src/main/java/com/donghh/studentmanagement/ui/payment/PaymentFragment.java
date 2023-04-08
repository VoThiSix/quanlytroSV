package com.donghh.studentmanagement.ui.payment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.donghh.studentmanagement.Common;
import com.donghh.studentmanagement.R;
import com.donghh.studentmanagement.database.DatabaseHandler;
import com.donghh.studentmanagement.databinding.FragmentDashboardBinding;
import com.donghh.studentmanagement.entity.Payment;
import com.donghh.studentmanagement.entity.Student;
import com.donghh.studentmanagement.ui.student.StudentAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class PaymentFragment extends Fragment {

    private FragmentDashboardBinding binding;
    private StudentAdapter studentAdapter;
    private DatabaseHandler databaseHandler;

    final Calendar myCalendar = Calendar.getInstance();

    public static PaymentFragment newInstance() {
        
        Bundle args = new Bundle();
        
        PaymentFragment fragment = new PaymentFragment();
        fragment.setArguments(args);
        return fragment;
    }
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        binding = FragmentDashboardBinding.bind(view);
        databaseHandler = new DatabaseHandler(getContext(), DatabaseHandler.DATABASE_NAME, null, DatabaseHandler.DATABASE_VERSION);


        updateDatePayment();
        initListener();

        return view;
    }

    private void initRecyclerListStudent(ArrayList<Student> data) {
        try {
            binding.rvStudent.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
            studentAdapter = new StudentAdapter(data, new StudentAdapter.IStudent() {
                @Override
                public void onClickStudent(Student student) {
                    binding.tipStudentCode.setText(student.getStudentCode());
                    binding.rvStudent.setVisibility(View.GONE);
                }
            });
            binding.rvStudent.setAdapter(studentAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateDatePayment() {
        //ngày thanh toán mặc định là ngày hiện tại
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
        binding.tipDatePayment.setText(dateFormat.format(myCalendar.getTime()));
    }

    private void initListener() {
        try {
            binding.flbSearchPayment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(),SearchPaymentActivity.class);
                    startActivity(intent);
                }
            });

            binding.tvAccept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!binding.tipStudentCode.getText().toString().isEmpty()
                    &&!binding.tipElectricityBill.getText().toString().isEmpty()
                    &&!binding.tipWaterBill.getText().toString().isEmpty()
                    &&!binding.tipRoomBill.getText().toString().isEmpty()
                    &&!binding.tipDatePayment.getText().toString().isEmpty()
                    ){
                        databaseHandler.addPayment(new Payment(binding.tipStudentCode.getText().toString(),
                                Double.parseDouble(binding.tipElectricityBill.getText().toString()),
                                Double.parseDouble( binding.tipWaterBill.getText().toString()),
                                Double.parseDouble( binding.tipRoomBill.getText().toString()),
                                Common.formatDateToString(myCalendar.getTime())));
                        Toast.makeText(getContext(), getString(R.string.add_payment), Toast.LENGTH_SHORT).show();
                        binding.tipStudentCode.getText().clear();
                        binding.tipElectricityBill.getText().clear();
                        binding.tipWaterBill.getText().clear();
                        binding.tipRoomBill.getText().clear();
                    }else {
                        Toast.makeText(getContext(), "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int day) {
                    myCalendar.set(Calendar.YEAR, year);
                    myCalendar.set(Calendar.MONTH, month);
                    myCalendar.set(Calendar.DAY_OF_MONTH, day);
                    updateDatePayment();
                }
            };
            binding.tipDatePayment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new DatePickerDialog(getContext(), date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
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
                        binding.rvStudent.setVisibility(View.GONE);
                    } else {
                        binding.rvStudent.setVisibility(View.VISIBLE);
                        initRecyclerListStudent(databaseHandler.getAllStudentsBKeySearch(binding.tipStudentCode.getText().toString()));

                    }

                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}