package com.example.termostattoendversion.ui.jobs.mqtt;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.termostattoendversion.ui.jobs.json.JsonWidgetMessage;
import com.example.termostattoendversion.ui.jobs.message.MessageClass;
import com.example.termostattoendversion.ui.view.adapters.WidgetAdapter;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class MqttConnection {

    private final static String SERVER_URL = "tcp://130.61.92.192:1883";
    private final static String CLIENT_ID = "app".concat(Long.toString(System.currentTimeMillis()));
    private static MqttAndroidClient mqtt;
    private static MqttConnectOptions options;
    private static MessageClass messageClass;
    private final MemoryPersistence persistence = new MemoryPersistence();

    public MqttConnection() {

    }

    public static void setMqtt(Context context, String username, String password) {
        if (mqtt == null) {
            // Создание и создание экземпляра объекта параметра соединения MQTT
            options = new MqttConnectOptions();
            // Затем устанавливаем соответствующие параметры
            options.setUserName(username);                  // Устанавливаем имя пользователя подключения
            options.setPassword(password.toCharArray());    // Устанавливаем пароль для подключения
            options.setConnectionTimeout(30);               // Устанавливаем период ожидания в секундах
            options.setKeepAliveInterval(60);               // Устанавливаем сердцебиение, 30 с
            options.setAutomaticReconnect(true);            // Следует ли повторно подключаться
            // Устанавливаем, очищать ли сеанс, значение false означает, что сервер будет хранить запись о подключении клиента, значение true означает подключение с новым идентификатором каждый раз, когда вы подключаетесь к серверу
            options.setCleanSession(true);
            mqtt = new MqttAndroidClient(context, SERVER_URL, CLIENT_ID);
            Log.d("Debug", "Создан MQTT");
        }
    }

    public static MqttAndroidClient getMqtt() {
        return mqtt;
    }

    public static void setMessageClass(MessageClass messageClass) {
        MqttConnection.messageClass = messageClass;
    }

    public static boolean mqttOnline() {
        try {
            mqtt.connect(options);
            Log.d("Errors", "Коннект MQTT login"+options.getUserName());
            Log.d("Errors", "Коннект MQTT password"+ Arrays.toString(options.getPassword()));
            Log.d("Errors", "Коннект MQTT url"+mqtt.getServerURI());
            Log.d("Errors", "Коннект MQTT id"+mqtt.getClientId());
            int i = 0;
            while ((!mqtt.isConnected()) && i<10)
            {
                i++;
                Thread.sleep(1000);
            }
            return mqtt.isConnected();
        } catch (Exception ex) {
            Log.d("Errors", "Коннект MQTT завершился ошибкой");
            return false;
        }
    }

    public static void mqttOffline() {
        try {
            if (mqtt != null && mqtt.isConnected()) {
                mqtt.disconnect();
            }
        } catch (Exception ex) {
            Log.d("Errors", "Дисконнект MQTT завершился ошибкой");
        }
    }

    public static void outputMessageHello() {
        try {

        } catch (Exception ex) {
            Log.d("Errors", "Сообщение hello не отправлено");
        }
    }

    /**
     *  Отправка сообщения
     *
     * @param topic     топик
     * @param message   текст сообщения
     */
    public static void outputMessage(String topic, String message) {
        try {

        } catch (Exception ex) {
            Log.d("Errors", "Сообщение topic не отправлено");
        }
    }

    public static void outputHelloMessage() {
        try {
            MqttMessage m = new MqttMessage();
            m.setPayload("HELLO".getBytes());
            mqtt.publish("/IoTmanager",m);
        } catch (Exception ex) {
            Log.d("Errors", "Сообщение topic не отправлено");
        }
    }

    public static boolean isConnection() {
        return mqtt.isConnected();
    }

    public static void addListener(RecyclerView view) throws MqttException {
        if (mqtt.isConnected()) {
            Log.d("Errors", String.valueOf(mqtt != null));
            mqtt.subscribe("/IoTmanager", 0);
            mqtt.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {

                }

                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    String m = byteArrayToHexString(message.getPayload());
                    ((WidgetAdapter) view.getAdapter()).addWidget(new JsonWidgetMessage(m));
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
