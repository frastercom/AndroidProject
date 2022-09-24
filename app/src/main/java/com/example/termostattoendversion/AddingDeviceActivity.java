package com.example.termostattoendversion;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.termostattoendversion.ui.jobs.device.Device;
import com.google.gson.Gson;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.URL;

public class AddingDeviceActivity extends AppCompatActivity {

    private LinearLayout addingDevice;
    private LinearLayout addingDeviceSettings;
    private LinearLayout controlDeviceSettings;
    private EditText usidText;
    private EditText passwordText;
    private EditText login;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adding_device);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Добавить устройство");
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        addingDevice = findViewById(R.id.add_device);
        addingDeviceSettings = findViewById(R.id.add_device_settings);
        controlDeviceSettings = findViewById(R.id.control_settings);
        addingDevice.setVisibility(View.VISIBLE);
        addingDeviceSettings.setVisibility(View.GONE);
        controlDeviceSettings.setVisibility(View.GONE);
        usidText = findViewById(R.id.login_WiFi);
        passwordText = findViewById(R.id.password_WiFi);
        login = findViewById(R.id.login_device);
        password = findViewById(R.id.password_device);
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

    public void checkNetwork(View view) {
        if (itsOnline()) {
            addingDevice.setVisibility(View.GONE);
            addingDeviceSettings.setVisibility(View.VISIBLE);

        } else {
            Toast.makeText(this, "Соединение отсутствует", Toast.LENGTH_LONG).show();
        }
    }

    public void connect(View view) {
        if ((usidText.getText().toString().length() > 1) && (passwordText.getText().toString().length() > 7)
                && (login.getText().toString().length() >= 3) && (password.getText().toString().length() >= 3)) {
            //серийный номер устройства
            String serialNumber = "";
            if (setWiFiConnection(usidText.getText().toString(), passwordText.getText().toString())) {
                String userName = login.getText().toString();                    // Тогда ваше имя пользователя, Alibaba Cloud, Tencent Cloud, Baidu Yuntian Gongwu подключается к этим платформам, оно будет автоматически сгенерировано после создания нового устройства
                String password = this.password.getText().toString();
                if (setMQTTClient(userName, password)) {

                    Device device = new Device(userName, password, serialNumber);
                    try {
                        FileOutputStream fos = openFileOutput("alice.csv", MODE_PRIVATE);
                        fos.write(new Gson().toJson(device).getBytes());
                        Log.d("DEVICE_SAVE", "Учетка устройсва сохранена");
                    } catch (IOException e) {
                        Log.e("DEVICE_SAVE", "Произошла внутренняя ошибка записи данных в хэш");
                        Toast.makeText(this, "Данные не установлены", Toast.LENGTH_LONG).show();
                    }
                }

                resetWiFiConnection();
                finish();
            } else {
                Log.d("DEVICE_SAVE", "от устройста пришел не 200-ый статус");
                Toast.makeText(this, "Данные не установлены, внутренняя ошибка", Toast.LENGTH_LONG).show();
            }
        } else {
            Log.d("DEVICE_SAVE", "Пользователь указал некорректные данные");
            Toast.makeText(this, "Заполните все поля корректно, логин и пароль должны быть не менее 3 символов и не более 7 символов", Toast.LENGTH_LONG).show();
        }
    }

    private boolean setWiFiConnection(String usid, String password) {
        try {
            URL url = new URL("http://admin:admin@192.168.4.1/set?routerssid=" + usid + "&routerpass=" + password);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                // все ок
                return true;
            } else {
                // ошибка
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean setMQTTClient(String usid, String password) {
        try {
            URL url = new URL("http://admin:admin@192.168.4.1/set?mqttUser=" + usid + "&mqttPass=" + password);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                // все ок
                return true;
            } else {
                // ошибка
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean resetWiFiConnection() {
        try {
            URL url = new URL("http://admin:admin@192.168.4.1/set?reset");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                // все ок
                return true;
            } else {
                // ошибка
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean itsOnline() {
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            int timeoutMs = 2000;
            Socket sock = new Socket();
            SocketAddress sockaddr = new InetSocketAddress("192.168.4.1", 80);
            sock.connect(sockaddr, timeoutMs);
            sock.close();
            Log.i("CONNECTION STATUS:", "подключено к устройству");
            return true;
        } catch (IOException e) {
            Log.i("CONNECTION STATUS:", "отсутствует подключение к устройству");
            return false;
        }
    }


}
