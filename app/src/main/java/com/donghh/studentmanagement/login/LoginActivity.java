package com.donghh.studentmanagement.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.donghh.studentmanagement.Common;
import com.donghh.studentmanagement.MainActivity;
import com.donghh.studentmanagement.R;
import com.donghh.studentmanagement.database.DatabaseHandler;
import com.donghh.studentmanagement.databinding.ActivityLoginBinding;
import com.donghh.studentmanagement.entity.Account;
import com.donghh.studentmanagement.entity.UpdateStudentEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;


public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    private DatabaseHandler databaseHandler;

    ArrayList<Account> accounts = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            EventBus.getDefault().register(this);

            binding = ActivityLoginBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());
            databaseHandler = new DatabaseHandler(this, DatabaseHandler.DATABASE_NAME, null, DatabaseHandler.DATABASE_VERSION);
            accounts = databaseHandler.getAllAccount();
            binding.tvLogin.setOnClickListener(v -> {
                if (validateLogin()) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);

                    Common.putBoolean(LoginActivity.this, Common.IS_LOGIN, true);

                } else {
                    Toast.makeText(LoginActivity.this, "Tài khoản hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
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


            binding.tipPassword.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    binding.tilPassword.setError(null);

                }
            });


            binding.tvAddAccount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(LoginActivity.this, CreateAccountActivity.class);
                    startActivity(intent);
                }
            });

            binding.tvForgotPassword.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
                    startActivity(intent);
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    @Subscribe
    public void OnUpdateStudentEvent(UpdateStudentEvent event) {
        accounts = databaseHandler.getAllAccount();
    }
    private boolean validateLogin() {
        //user name
        if (binding.tipUserName.getText().toString().isEmpty()) {
            binding.tilUserName.setError(getString(R.string.not_user_name));
        } else {
            binding.tilUserName.setError(null);
        }

        //password
        if (binding.tipPassword.getText().toString().isEmpty()) {
            binding.tilPassword.setError(getString(R.string.not_password));
        } else {
            binding.tilPassword.setError(null);
        }
        //
        if (accounts.size() > 0) {
            for (Account account : accounts) {
                if (account.getPassword().equals(binding.tipPassword.getText().toString().trim())
                        && account.getUserName().equals(binding.tipUserName.getText().toString().trim())) {

                    Common.putBoolean(LoginActivity.this, Common.IS_MANAGER, account.getIsManager()==1);
                    return true;
                }
            }
        }

        return false;
    }


}
