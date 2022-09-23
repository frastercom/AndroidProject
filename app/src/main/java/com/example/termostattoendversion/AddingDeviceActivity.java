package com.example.termostattoendversion;

import android.app.Activity;
import android.content.Context;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
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
        ActionBar actionBar =getSupportActionBar();
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
        if (itsOnline(this)) {
            addingDevice.setVisibility(View.GONE);
            addingDeviceSettings.setVisibility(View.VISIBLE);

        }
        else
        {
            Toast toast = Toast.makeText(this, "Соединение отсутствует", Toast.LENGTH_LONG);
            toast.show();
        }
//        Runnable runnable = new Runnable() {
//                    public void run() {
//                        try {
//                            getSerialNumber();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                };
//                Thread thread = new Thread(runnable);
//                thread.start();


    }

    public void connect(View view) {
        if ((usidText.getText().toString().length()>1) && (passwordText.getText().toString().length()>7)
                && (login.getText().toString().length()>=3) && (password.getText().toString().length()>=3)) {
            String serialNumber = "";
            if (setWiFiConnection(usidText.getText().toString(), passwordText.getText().toString())) {
                String userName = login.getText().toString();                    // Тогда ваше имя пользователя, Alibaba Cloud, Tencent Cloud, Baidu Yuntian Gongwu подключается к этим платформам, оно будет автоматически сгенерировано после создания нового устройства
                String password = this.password.getText().toString();
                if (setMQTTClient(userName, password)) {
                    Device device = new Device(userName, password);
                    try {
                        FileOutputStream fos = openFileOutput("alice.csv", MODE_PRIVATE);
                        fos.write(device.getUserName().getBytes());
                    } catch (IOException e) {
//                        e.printStackTrace();
                        Toast toast = Toast.makeText(this, "Данные не установлены", Toast.LENGTH_LONG);
                    }
                }

                resetWiFiConnection();
                finish();
            } else {
                Toast toast = Toast.makeText(this, "Данные не установлены", Toast.LENGTH_LONG);
//                toast.setGravity(Gravity.TOP, 0,160);   // import android.view.Gravity;
                toast.show();
            }
        }
        else
        {
            Toast toast = Toast.makeText(this, "Заполните все поля корректно", Toast.LENGTH_LONG);
//                toast.setGravity(Gravity.TOP, 0,160);   // import android.view.Gravity;
            toast.show();
        }
    }

    private boolean setWiFiConnection(String usid, String password)
    {
        try {
            URL url = new URL("http://admin:admin@192.168.4.1/set?routerssid="+usid+"&routerpass="+password);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
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

    private boolean setMQTTClient(String usid, String password)
    {
        try {
            URL url = new URL("http://admin:admin@192.168.4.1/set?mqttUser="+usid+"&mqttPass="+password);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
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

    private void addWiFiTermostat()
    {
        String url = "http://admin:admin@192.168.1.107/?set.mqtt:28";
        try {
            readJsonFromUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public JSONObject readJsonFromUrl(String adr) throws IOException, JSONException {
        URL url = new URL(adr);
//            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
//            connection.getResponseCode();
        Log.i("json:", " url >> "+url.toString());
        InputStreamReader is = new InputStreamReader(url.openStream());
        BufferedReader rd = new BufferedReader(is);
        String jsonText = readAll(rd);
        Log.i("json:", "json url >> "+jsonText);
        JSONObject json = new JSONObject(jsonText);
        is.close();
        return json;
    }

    private String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    private boolean resetWiFiConnection()
    {
        try {
            URL url = new URL("http://admin:admin@192.168.4.1/set?reset");
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
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

    private boolean itsOnline(Context context) {
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();

            StrictMode.setThreadPolicy(policy);

            int timeoutMs = 2000;
            Socket sock = new Socket();
            SocketAddress sockaddr = new InetSocketAddress("192.168.4.1", 80);
//            SocketAddress sockaddr = new InetSocketAddress("192.168.1.107", 80);

            sock.connect(sockaddr, timeoutMs);
            sock.close();
            Log.i("CONNECTION STATUS:", "connected");

            return true;
        } catch (IOException e) {
            Log.i("CONNECTION STATUS:", "disconnected");
            return false;
        }
    }

    private String getSerialNumber() throws IOException {
//        String url = "http://admin:admin@192.168.1.107/?set.device";
//        HttpClient httpclient = new DefaultHttpClient(); // Create HTTP Client
//        HttpGet httpget = new HttpGet("http://admin:admin@192.168.1.107/?set.device"); // Set the action you want to do
//        HttpResponse response = httpclient.execute(httpget); // Executeit
//        HttpEntity entity = response.getEntity();
//        InputStream is = entity.getContent(); // Create an InputStream with the response
//        BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
//        StringBuilder sb = new StringBuilder();
//        String line = null;
//        while ((line = reader.readLine()) != null) // Read line by line
//        {
//            sb.append(line + "\n");
//            Log.i("http test:", "sb line >>" + sb.toString());
//        }
//
//        String resString = sb.toString(); // Result is here
//
//        is.close(); // Close the stream
//        String url = "http://ya.ru";
//        try {
//            readJsonFromUrl(url);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        return "";
    }


}
