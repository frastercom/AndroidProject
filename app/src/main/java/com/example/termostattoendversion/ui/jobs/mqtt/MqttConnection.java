package com.example.termostattoendversion.ui.jobs.mqtt;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.termostattoendversion.ui.jobs.device.Device;
import com.example.termostattoendversion.ui.jobs.json.JsonStatusMessage;
import com.example.termostattoendversion.ui.jobs.json.JsonWidgetMessage;
import com.example.termostattoendversion.ui.jobs.statics.StaticsStatus;
import com.example.termostattoendversion.ui.view.adapters.WidgetAdapter;
import com.google.gson.Gson;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Date;

public class MqttConnection {

    private final static String SERVER_URL = "tcp://iotml.ml:1883";
    private final static String CLIENT_ID = "app".concat(Long.toString(System.currentTimeMillis()));
    private final static String CHANEL_NAME = "/IoTmanager/*/config";
    private final static String TOPIC_HELLO = "/IoTmanager";

    private static WidgetAdapter widgetAdapter;

    private static MqttAndroidClient mqtt_client;
    private static MqttConnectOptions options;               // Пароль, соответствующий имени пользователя, те же самые различные облачные платформы будут генерировать пароль соответственно, здесь моя платформа EMQ не ограничена, поэтому имя пользователя и пароль могут быть введены случайно
    public static boolean isStatus;
    public static Date date;

    public MqttConnection() {
    }

    public static WidgetAdapter getWidgetAdapter() {
        return widgetAdapter;
    }

    public static void setWidgetAdapter(WidgetAdapter widgetAdapter) {
        MqttConnection.widgetAdapter = widgetAdapter;
    }

    public static void connectMqtt(FragmentActivity activity, RecyclerView view, Device device) {
        isStatus = true;
        Runnable runnable = new Runnable() {
            public void run() {
                Log.e("mqtt:", "connect ");
                mqtt_client = new MqttAndroidClient(activity, SERVER_URL, CLIENT_ID);
                try {
                    // Создание и создание экземпляра объекта параметра соединения MQTT
                    options = new MqttConnectOptions();
                    // Затем устанавливаем соответствующие параметры
                    options.setUserName(device.getUserName());                  // Устанавливаем имя пользователя подключения
                    options.setPassword(device.getPassword().toCharArray());    // Устанавливаем пароль для подключения
                    options.setConnectionTimeout(30);               // Устанавливаем период ожидания в секундах
                    options.setKeepAliveInterval(60);               // Устанавливаем сердцебиение, 30 с
                    options.setAutomaticReconnect(true);            // Следует ли повторно подключаться
                    // Устанавливаем, очищать ли сеанс, значение false означает, что сервер будет хранить запись о подключении клиента, значение true означает подключение с новым идентификатором каждый раз, когда вы подключаетесь к серверу
                    options.setCleanSession(true);
                    mqtt_client.connect(options);

                    int i = 0;
                    while ((!mqtt_client.isConnected()) && i < 10) {
                        i++;
                        Thread.sleep(1000);
                        if (mqtt_client.isConnected()) {
                            mqtt_client.subscribe(CHANEL_NAME, 0);
                            outputHelloMessage();
                            try {
                                addListener(view);
                            } catch (MqttException e) {
                                Log.e("MQTT", "Слушатель не был добавлен. Ошибка: " + e.getMessage());
                            }
                        }
                    }
                    if (!mqtt_client.isConnected()) {
                        Log.d("MQTT", "Соедиенеие не установлено");
                    }


                } catch (Exception e) {
                    Log.e("MQTT", "Соедиенеие не установлено. Ошибка: " + e.getMessage());
                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();

    }

    public static void mqttOffline() {
        try {
            if (mqtt_client != null && mqtt_client.isConnected()) {
                mqtt_client.disconnect();
                Log.d("MQTT", "Дисконнект удачно произведен");
            }
        } catch (Exception ex) {
            Log.e("MQTT", "Дисконнект MQTT завершился ошибкой");
        }
    }

    public static void topic(String topic) {
        try {
            mqtt_client.subscribe(topic.concat("/status"), 1);
            Log.d("MQTT", String.format("Подписка на топик (%s) удачно произведена", topic));
        } catch (MqttException e) {
            Log.e("MQTT", String.format("Подписка на топик (%s) не удалась. Ошибка: %s", topic, e.getMessage()));
        }
    }

    /**
     * Отправка сообщения
     *
     * @param topic   топик
     * @param message текст сообщения
     */
    public static void outputMessage(String topic, String message) {
        try {
            MqttMessage m = new MqttMessage();
            m.setPayload(message.getBytes(StandardCharsets.UTF_8));
            mqtt_client.publish(topic, m);
            Log.d("MQTT", String.format("Сообщение (%s) отправлено на топик (%s)", message, topic));
        } catch (Exception ex) {
            Log.e("MQTT", String.format("Сообщение (%s) на топик (%s) не отправлено. Ошибка: %s", message, topic, ex.getMessage()));
        }
    }

    public static void outputHelloMessage() {
        try {
            MqttMessage m = new MqttMessage();
            m.setPayload("HELLO".getBytes());
            mqtt_client.publish("/IoTmanager", m);
            Log.d("MQTT", "Сообщение 'HELLO' отправлено");
        } catch (Exception ex) {
            Log.e("MQTT", "Сообщение HELLO не отправлено. Ошибка: " + ex.getMessage());
        }
    }

    public static boolean isConnection() {
        return mqtt_client.isConnected();
    }

    public static void addListener(RecyclerView view) throws MqttException {
        if (mqtt_client.isConnected()) {
            mqtt_client.subscribe("/IoTmanager", 0);
            mqtt_client.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {

                }

                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    String m = byteArrayToHexString(message.getPayload());
                    Log.d("MQTT", String.format("Входящее сообщение (%s) на топик (%s)", m, topic));
                    //если сообщение не приходит в течении 1,5 секунды, отправляем Hello и получаем статусы
                    if (isStatus && date != null && (Calendar.getInstance().getTime().getTime() - date.getTime()) > 1500) {
                        isStatus = false;
                        outputHelloMessage();
                        Log.d("MQTT", "Отправили HELLO, получаем статусы");
                    }
                    if (m != null && !m.equals("")) {
                        if (!m.contains("{\"status\"") && isStatus) {
                            //Добавляем виджет
                            ((WidgetAdapter) view.getAdapter()).addWidget(new Gson().fromJson(m, JsonWidgetMessage.class));
                            date = Calendar.getInstance().getTime();
                        } else {
                            //обновляем статус
                            StaticsStatus.setStatus(topic, new Gson().fromJson(m, JsonStatusMessage.class));
                        }
                    }
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {

                }
            });
        }

    }

    //преоброзование байтов в сторку
    private static String byteArrayToHexString(final byte[] bytes) throws UnsupportedEncodingException {
        return new String(bytes, "UTF-8");
    }

}
