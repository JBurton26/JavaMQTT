import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.*;

import java.util.Random;
public class Main {
    public static void main(String[] args){
        MqttClient client;
        Random numVals = new Random();
        int intVals = numVals.nextInt(10);
        intVals += 1;
        while (intVals < 5){
            intVals = numVals.nextInt(10);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("nodeid",numVals.nextInt(5)+1);
        jsonObject.put("temperature", numVals.nextInt(30));
        jsonObject.put("humidity", numVals.nextInt(100));
        try {
            client = new MqttClient("tcp://192.168.137.45:1883", MqttClient.generateClientId());
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            connOpts.setKeepAliveInterval(1000);
            connOpts.setUserName("jakepi");
            connOpts.setPassword("jbpi1234".toCharArray());
            client.connect(connOpts);
            MqttMessage message = new MqttMessage();
            message.setPayload(jsonObject.toString().getBytes());
            client.publish("IoT_Data", message);
            client.disconnect();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}
