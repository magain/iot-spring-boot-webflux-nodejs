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

    // The presentation port
    private SensorDeviceApiPort sensorDeviceApiPort;

    @Autowired
    public SensorDeviceApiAdapter(SensorDeviceApiPort sensorDeviceApiPort) {
        this.sensorDeviceApiPort = sensorDeviceApiPort;
    }

    // The route (api/logEvent)
    // The method logEvent for this route extracts the payload JSON and creates an instances of domain model pojo LogEvent
    // The endpoint returns a Mono typed to Void meaning there is no data send back to the request sender
    // The possible IOException will be catched by the ControllerAdvice SensorDeviceApiAdapterErrorAdvice
    @PostMapping(value = "logEvent")
    public Mono<Void> logEvent(@RequestBody LogEvent logEvent) throws IOException {
        return Mono.from(sensorDeviceApiPort.dispatchLogEvent(logEvent));
    }
}
