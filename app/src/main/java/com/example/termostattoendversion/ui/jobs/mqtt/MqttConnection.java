package com.example.termostattoendversion.ui.jobs.mqtt;

import android.content.Context;
import android.util.Log;

import com.example.termostattoendversion.ui.jobs.message.MessageClass;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

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
            Thread.sleep(1000);
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

    public static boolean isConnection() {
        return mqtt.isConnected();
    }
}
