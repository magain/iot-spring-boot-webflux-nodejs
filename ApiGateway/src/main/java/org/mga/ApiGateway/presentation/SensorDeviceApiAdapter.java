package org.mga.ApiGateway.presentation;

import org.mga.ApiGateway.application.domain.model.LogEvent;
import org.mga.ApiGateway.application.ports.SensorDeviceApiPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.io.IOException;

@RestController
@RequestMapping(path = "api")
public class SensorDeviceApiAdapter {

    private SensorDeviceApiPort sensorDeviceApiPort;

    @Autowired
    public SensorDeviceApiAdapter(SensorDeviceApiPort sensorDeviceApiPort) {
        this.sensorDeviceApiPort = sensorDeviceApiPort;
    }

    @PostMapping(value = "logEvent")
    public Mono<Void> logEvent(@RequestBody LogEvent logEvent) throws IOException {
        return Mono.from(sensorDeviceApiPort.dispatchLogEvent(logEvent));
    }
}
