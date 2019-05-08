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

public class Device implements Runnable {

    private String deviceId;
    private String dummyEvent;
    private boolean interrupted = false;
    private int sleepTime;
    private Map<String, JSONObject> messageOutBox;


    public Device() {
        this.deviceId = UUID.randomUUID().toString();
        this.dummyEvent = "This is some dummy event from device " + this.deviceId;
        this.sleepTime = getRandomSleepTime(new Random());
    }

    @Override
    public void run() {

        while(!interrupted) {
            try {
                sendLogEvent();
                TimeUnit.SECONDS.sleep(this.sleepTime);
            } catch(InterruptedException ie) {
                System.out.println("Device: " + deviceId + " has been interrupted");
                System.out.println(ie.getMessage());
                interrupted = true;
            }
        }
    }

    private void sendLogEvent() {
        try {
            URL url = new URL("http://localhost:8080/api/logEvent");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setDoOutput(true);
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestMethod("POST");

            OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
            wr.write(prepareMspJson().toString());
            wr.flush();

            int httpCode = con.getResponseCode();

            if(httpCode != 200) {
                System.out.print("Sending request failed with http Code: " + httpCode);
            }

        } catch(ProtocolException pe) {
            System.out.println(pe.getMessage());
            interrupted = true;
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
            interrupted = true;
        }
    }

    private JSONObject prepareMspJson() {
        Date date = new Date();

        JSONObject msg = new JSONObject();
        msg.put("deviceId", deviceId);
        msg.put("event", dummyEvent);
        msg.put("timestamp", getTimestamp());

        return msg;
    }

    private Timestamp getTimestamp() {
        Date date = new Date();
        long time = date.getTime();

        return new Timestamp(time);
    }

    private int getRandomSleepTime(Random random) {
        return random.nextInt(30 - 5 + 1) + 5;
    }
}
