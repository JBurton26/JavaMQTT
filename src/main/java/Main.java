import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.*;

import java.util.Random;
//Main Class for
public class Main {
    public static void main(String[] args){

        Random numVals = new Random();
        int intVals = numVals.nextInt(10);
        intVals += 1;
        while (intVals < 5){
            intVals = numVals.nextInt(10);
        }
        messages(intVals);
    }
    private static void messages(int num){
        MqttClient client;
        Random numVals = new Random();
        for(int i = 0; i < num; i++){
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
                try {
                    client.publish("IoT_Data", message);
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                client.disconnect();
            } catch (MqttException e) {
                e.printStackTrace();
            }
        }

    }
}
