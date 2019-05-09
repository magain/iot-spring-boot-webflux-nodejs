package org.mga.ApiGateway.application.domain.usecases;

import org.mga.ApiGateway.application.domain.model.LogEvent;
import org.mga.ApiGateway.application.ports.LoggerServicePort;
import org.mga.ApiGateway.application.ports.SensorDeviceApiPort;
import org.mga.ApiGateway.infratsructure.LoggerServiceUdpClientAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.IOException;

/*
    The connecting element between the presentation adapter and infrastructure adapter
 */

@Service
public class LogDispatcherService implements SensorDeviceApiPort {

    @Autowired
    private LoggerServicePort loggerServicePort;

    @Autowired
    private final GpbConverterService gpbConverterService;

    public LogDispatcherService(LoggerServicePort loggerServiceUdpClientAdapter,
                                GpbConverterService gpbConverterService) {
        this.loggerServicePort = loggerServiceUdpClientAdapter;
        this.gpbConverterService = gpbConverterService;
    }


    // Pass on the the received request payload on to the outgoing infratstructure adapter after converting it to GPB
    // Possible IOException will be handled inderectly by the ControllerAdvice
    public Mono<Void> dispatchLogEvent(LogEvent logEvent) throws IOException {
        return loggerServicePort.sendMessage(gpbConverterService.convertLogEventToGpb(logEvent));
    }
}
