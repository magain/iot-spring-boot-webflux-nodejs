package org.mga.ApiGateway.application.ports;

import org.mga.ApiGateway.application.domain.model.protobuf.LogEventMessageOuterClass.LogEventMessage;
import reactor.core.publisher.Mono;

import java.io.IOException;

public interface LoggerServicePort {
    Mono<Void> sendMessage(LogEventMessage logEventMessage) throws IOException;
}
