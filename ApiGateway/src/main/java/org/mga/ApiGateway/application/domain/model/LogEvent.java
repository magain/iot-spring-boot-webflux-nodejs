package org.mga.ApiGateway.application.domain.model;

/*
    Java Pojo defining the LogEvent.
 */
public class LogEvent {

    // The class could be simplified using lombok
    private String deviceId;
    private String event;
    private String timestamp;

    public LogEvent() {}

    public LogEvent(LogEvent logEvent) {
        this.deviceId = logEvent.deviceId;
        this.event = logEvent.event;
        this.timestamp = logEvent.timestamp;
    }

    // Getters and Setters

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getEvent() {
        return event;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
