package com.donghh.studentmanagement.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.donghh.studentmanagement.Common;
import com.donghh.studentmanagement.MainActivity;
import com.donghh.studentmanagement.R;
import com.donghh.studentmanagement.database.DatabaseHandler;
import com.donghh.studentmanagement.databinding.ActivityCreateAccountBinding;
import com.donghh.studentmanagement.entity.Account;


public class CreateAccountActivity extends AppCompatActivity {
    ActivityCreateAccountBinding binding;
    private DatabaseHandler databaseHandler;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            binding = ActivityCreateAccountBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());
            databaseHandler = new DatabaseHandler(this, DatabaseHandler.DATABASE_NAME, null, DatabaseHandler.DATABASE_VERSION);

            binding.tvCreateAccount.setOnClickListener(v -> {
                if (validateCreateAccount()) {
                    int manager=0;
                    if (binding.ckbIsMananger.isChecked()){
                        manager=1;
                    }else {
                        manager=0;
                    }
                    Account addAccount = new Account(binding.tipUserName.getText().toString(), binding.tipPassword.getText().toString(),manager);
                    databaseHandler.addAccount(addAccount);

                    Intent intent = new Intent(CreateAccountActivity.this, MainActivity.class);
                    startActivity(intent);
                    Common.putBoolean(CreateAccountActivity.this, Common.IS_MANAGER, binding.ckbIsMananger.isChecked());
                    Common.putBoolean(CreateAccountActivity.this, Common.IS_LOGIN, true);
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

        //password
        if (binding.tipPassword.getText().toString().isEmpty()) {
            binding.tilPassword.setError(getString(R.string.not_password));
            return false;
        } else {
            binding.tilPassword.setError(null);
        }

        return true;
    }


}
