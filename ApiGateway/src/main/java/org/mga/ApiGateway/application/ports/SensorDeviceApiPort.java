package org.mga.ApiGateway.application.ports;

import org.mga.ApiGateway.application.domain.model.LogEvent;
import reactor.core.publisher.Mono;

import java.io.IOException;

public interface SensorDeviceApiPort {
    Mono<Void> dispatchLogEvent(LogEvent logEvent) throws IOException;
}
