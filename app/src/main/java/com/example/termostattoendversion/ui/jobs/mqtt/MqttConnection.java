package com.example.termostattoendversion.ui.jobs.mqtt;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.termostattoendversion.ui.jobs.json.JsonStatusMessage;
import com.example.termostattoendversion.ui.jobs.json.JsonWidgetMessage;
import com.example.termostattoendversion.ui.jobs.message.MessageClass;
import com.example.termostattoendversion.ui.view.adapters.WidgetAdapter;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class MqttConnection {

    private final static String SERVER_URL = "tcp://iotml.ml:1883";
    private final static String CLIENT_ID = "app".concat(Long.toString(System.currentTimeMillis()));
    private final static String CHANEL_NAME = "/IoTmanager/*/config";
    private final static String TOPIC_HELLO = "/IoTmanager";
    private static MqttAndroidClient mqtt_client;
    private static MqttConnectOptions options;               // Пароль, соответствующий имени пользователя, те же самые различные облачные платформы будут генерировать пароль соответственно, здесь моя платформа EMQ не ограничена, поэтому имя пользователя и пароль могут быть введены случайно

    public MqttConnection() {

    }

    public static void connectMqtt(FragmentActivity activity, RecyclerView view) {
        String serverUri = "tcp://130.61.92.192:1883";  // Здесь вы можете ввести доменное имя + номер порта 1883 для различных облачных платформ IoT. Примечание: префикс «tcp: //» обязателен. Я не писал его раньше, поэтому долго не могу подключиться к нему.
        String userName = "tim:tim";                    // Тогда ваше имя пользователя, Alibaba Cloud, Tencent Cloud, Baidu Yuntian Gongwu подключается к этим платформам, оно будет автоматически сгенерировано после создания нового устройства
        String passWord = "tim";                    // Пароль, соответствующий имени пользователя, те же самые различные облачные платформы будут генерировать пароль соответственно, здесь моя платформа EMQ не ограничена, поэтому имя пользователя и пароль могут быть введены случайно
        String clientId = "app" + System.currentTimeMillis(); // clientId очень важен и не может быть повторен, иначе он не будет подключен, поэтому я определил его как приложение + текущее время
        String channelName = "/IoTmanager/*/config";
        String topicHello = "/IoTmanager";

        Runnable runnable = new Runnable() {
            public void run() {
                Log.e("mqtt:", "connect ");
                mqtt_client = new MqttAndroidClient(activity, serverUri, clientId);
                try {
                    // Создание и создание экземпляра объекта параметра соединения MQTT
                    options = new MqttConnectOptions();
                    // Затем устанавливаем соответствующие параметры
                    options.setUserName(userName);                  // Устанавливаем имя пользователя подключения
                    options.setPassword(passWord.toCharArray());    // Устанавливаем пароль для подключения
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
                            mqtt_client.subscribe(channelName, 0);
                            MqttMessage m = new MqttMessage();
                            m.setPayload("HELLO".getBytes());
                            mqtt_client.publish(topicHello, m);
                            Log.i("mqtt:", "hello>> сообщение отправлено");
//                    pJSONMessage.setMQTTclient(mqtt_client);
                            mqttSetClient(view);
                        }
                    }
                    if (!mqtt_client.isConnected()) {
                        Log.e("mqtt:", " not connect ");
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    addListener(view);
                } catch (MqttException e) {
                    e.printStackTrace();
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
            }
        } catch (Exception ex) {
            Log.d("Errors", "Дисконнект MQTT завершился ошибкой");
        }
    }

    public static void topic(String topic) {
        try {
            mqtt_client.subscribe(topic.concat("/status"), 1);
        } catch (MqttException e) {
            e.printStackTrace();
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
            mqtt_client.publish("/IoTmanager",m);
        } catch (Exception ex) {
            Log.d("Errors", "Сообщение topic не отправлено");
        }
    }

    public static boolean isConnection() {
        return mqtt_client.isConnected();
    }

    public static void addListener(RecyclerView view) throws MqttException {
        if (mqtt_client.isConnected()) {
            Log.d("Errors", String.valueOf(mqtt_client != null));
            mqtt_client.subscribe("/IoTmanager", 0);
            mqtt_client.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {

                }

                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    String m = byteArrayToHexString(message.getPayload());
                    Log.e("MESSAGE", "message: " + m + " topic: " + topic);
                    if (m != null && !m.equals("")) {
                        if (!m.contains("status")) {
                            ((WidgetAdapter) view.getAdapter()).addWidget(new JsonWidgetMessage(m));
                        } else {
                            ((WidgetAdapter) view.getAdapter()).addStatus(topic, new JsonStatusMessage(m));
                        }
                    }
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {

                }
            });
        }
    }

    public static void mqttSetClient(RecyclerView view)
    {
        mqtt_client.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean reconnect, String serverURI) {

                try {
                    mqtt_client.subscribe(CHANEL_NAME, 0);
                    MqttMessage m = new MqttMessage();
                    m.setPayload("HELLO".getBytes());
                    mqtt_client.publish(TOPIC_HELLO, m);
                } catch (MqttException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void connectionLost(Throwable cause) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                String m = byteArrayToHexString(message.getPayload());
                if (!m.contains("status")) {
                    Log.e("WIDGET","message: "+ m);
                    ((WidgetAdapter) view.getAdapter()).addWidget(new JsonWidgetMessage(m));
                } else {
                    Log.e("STATUS", "message: "+m);
                    ((WidgetAdapter) view.getAdapter()).addStatus(topic, new JsonStatusMessage(m));
                }
            }


            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });
    }

    //преоброзование байтов в сторку
    private static String byteArrayToHexString(final byte[] bytes) throws UnsupportedEncodingException {
        return new String(bytes, "UTF-8");
    }

}
