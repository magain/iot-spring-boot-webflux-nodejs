package org.mga.iot.simulator.device;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.*;
import java.sql.Timestamp;
import java.util.concurrent.TimeUnit;
import org.json.JSONObject;

/*
    This class represents a device and it does the following things:
    - waking up after sleeping time has ended
      (sleeping time generated for each device instances basing on a random value in seconds)
    - send a message containing a LogEvent as JSON object to the ApiGateway
    - fall asleep
 */
public class Device implements Runnable {

    private String deviceId;
    private String dummyEvent;
    private int sleepTime;

    public Device() {
        // Defining a unique id for the device
        this.deviceId = UUID.randomUUID().toString();

        // Defining some string as event for sending the message
        this.dummyEvent = "Device " + this.deviceId + " woke up again";

        // Defining the time in seconds the device falls asleep between sending messages
        this.sleepTime = getRandomDeviceSleepTime(new Random());
    }

    @Override
    public void run() {
        System.out.println("Stating device: " + deviceId);
        JSONObject logEvent;

        while(true) {   // endless loop
            try {
                logEvent = createLogEventJson();
                sendLogEventMessage(logEvent);
                TimeUnit.SECONDS.sleep(this.sleepTime);
            } catch(InterruptedException ie) {
                System.out.println("Device: " + this.deviceId + " has been interrupted");
                System.out.println(ie.getMessage());
            }
        }
    }

    /*
        Sending a message as HTTP POST request to the ApiGateway including a JSON object representing a log event
     */
    private void sendLogEventMessage(JSONObject logEvent) {

        try {
            // Create HTTP the connection and configure it
            URL url = new URL("http://localhost:8080/api/logEvent");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setDoOutput(true);
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestMethod("POST");
            con.setConnectTimeout(500);

            // send the POST request including its payload
            OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
            wr.write(logEvent.toString());
            wr.flush();

            // Analyze response
            if(con.getResponseCode() != 200) {
                // Something went wrong - check if ApiGateway and LoggerService are up and running
                System.out.println("Device: " + this.deviceId + " - Sending request failed with http Code: " + con.getResponseCode());
            }

        } catch(ProtocolException pe) {
            // Something went wrong - check if ApiGateway and LoggerService are up and running
            System.out.println("Device: " + this.deviceId + " - Sending request failed");
            System.out.println(pe.getMessage());
        } catch (IOException ioe) {
            // Something went wrong - check if ApiGateway and LoggerService are up and running
            System.out.println("Device: " + this.deviceId + " - Sending request failed");
            System.out.println(ioe.getMessage());
        }
    }

    /*
        Creates the message payload as JSONObject
     */
    private JSONObject createLogEventJson() {
        Date date = new Date();

        JSONObject msg = new JSONObject();
        msg.put("deviceId", deviceId);
        msg.put("event", dummyEvent);
        msg.put("timestamp", getCurrentTimestamp());

        return msg;
    }

    /*
        Generates the current timestamp to put into the message payload object
     */
    private Timestamp getCurrentTimestamp() {
        Date date = new Date();
        long time = date.getTime();

        return new Timestamp(time);
    }

    /*
        Generates a random int value used as device/thread sleeping time
     */
    private int getRandomDeviceSleepTime(Random random) {

        return random.nextInt(30 - 5 + 1) + 5;
    }
}
