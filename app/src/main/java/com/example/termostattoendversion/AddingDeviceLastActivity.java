package com.example.termostattoendversion;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.termostattoendversion.ui.jobs.device.Device;
import com.google.gson.Gson;

import java.io.FileOutputStream;
import java.io.IOException;

public class AddingDeviceLastActivity extends AppCompatActivity {

    private EditText login;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adding_device_last);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Добавить устройство");
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        login = findViewById(R.id.login_last_device);
        password = findViewById(R.id.password_last_device);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void saveLastDevice(View view) {
        if (isTrueDeviceData(login.getText().toString(), password.getText().toString())) {
            Toast.makeText(this, "Заполните все поля корректно, логин и пароль должны быть не менее 3 символов и не более 7 символов", Toast.LENGTH_LONG).show();
            return;
        }
        String userName = login.getText().toString();
        String passwordDevice = password.getText().toString();
        Device device = new Device(userName, passwordDevice);
        try {
            FileOutputStream fos = openFileOutput("alice.csv", MODE_PRIVATE);
            fos.write(new Gson().toJson(device).getBytes());
            Log.d("DEVICE_SAVE_LAST", "Учетка устройсва сохранена");
        } catch (IOException e) {
            Log.e("DEVICE_SAVE_LAST", "Произошла внутренняя ошибка записи данных в хэш");
            Toast.makeText(this, "Данные не установлены", Toast.LENGTH_LONG).show();
        }
        finish();
    }

    private boolean isTrueDeviceData(String name, String password) {
        if (name.length() > 3 && name.length()<=7 || password.length() > 3 && password.length()<=7 ) {
            return false;
        }
        return true;
    }
}
