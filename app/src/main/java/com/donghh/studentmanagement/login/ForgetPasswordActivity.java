package com.donghh.studentmanagement.login;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.donghh.studentmanagement.R;
import com.donghh.studentmanagement.database.DatabaseHandler;
import com.donghh.studentmanagement.databinding.ActivityForgetPasswordBinding;
import com.donghh.studentmanagement.entity.Account;
import com.donghh.studentmanagement.entity.UpdateStudentEvent;

import org.greenrobot.eventbus.EventBus;


public class ForgetPasswordActivity extends AppCompatActivity {
    ActivityForgetPasswordBinding binding;
    private DatabaseHandler databaseHandler;


    Account account = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            binding = ActivityForgetPasswordBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());
            databaseHandler = new DatabaseHandler(this, DatabaseHandler.DATABASE_NAME, null, DatabaseHandler.DATABASE_VERSION);

            binding.tvForgotPassword.setOnClickListener(v -> {
                if (validateCreateAccount()) {
                    account = databaseHandler.getAccountByUserName(binding.tipUserName.getText().toString());
                    if (account != null) {
                        Toast.makeText(this, "Mật khẩu cũ là: " + account.getPassword(), Toast.LENGTH_SHORT).show();
                        binding.tilPasswordOld.setVisibility(View.VISIBLE);
                        binding.tilPasswordNew.setVisibility(View.VISIBLE);
                        binding.tvResetPassword.setVisibility(View.VISIBLE);
                        binding.tvForgotPassword.setVisibility(View.GONE);
                    }

                }


            });

            binding.tvResetPassword.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (validateResetAccount()){
                        if (binding.tipUserName.getText().toString().equals(account.getUserName())
                                && binding.tipPasswordOld.getText().toString().equals(account.getPassword())) {
                            databaseHandler.updateAccountByUserName(binding.tipUserName.getText().toString(),binding.tipPasswordNew.getText().toString());

                            Toast.makeText(ForgetPasswordActivity.this, "Cập nhật mật khẩu thành công", Toast.LENGTH_SHORT).show();
                            EventBus.getDefault().post(new UpdateStudentEvent());
                            finish();
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


            binding.tipUserName.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    binding.tilUserName.setError(null);

                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private boolean validateCreateAccount() {
        //user name
        if (binding.tipUserName.getText().toString().isEmpty()) {
            binding.tilUserName.setError(getString(R.string.not_user_name));
            return false;
        } else {
            binding.tilUserName.setError(null);
        }

        return true;
    }

    private boolean validateResetAccount() {
        //user name
        if (binding.tipUserName.getText().toString().isEmpty()) {
            binding.tilUserName.setError(getString(R.string.not_user_name));
            return false;
        } else {
            binding.tilUserName.setError(null);
        }
        if (binding.tipPasswordOld.getText().toString().isEmpty()) {
            binding.tilPasswordOld.setError(getString(R.string.not_password));
            return false;
        } else {
            binding.tilPasswordOld.setError(null);
        }
        if (binding.tipPasswordNew.getText().toString().isEmpty()) {
            binding.tilPasswordNew.setError(getString(R.string.not_password));
            return false;
        } else {
            binding.tilPasswordNew.setError(null);
        }

        return true;
    }


}
