package org.mga.ApiGateway.application.domain.usecases;

import org.mga.ApiGateway.application.domain.model.LogEvent;
import org.mga.ApiGateway.application.ports.LoggerServicePort;
import org.mga.ApiGateway.application.ports.SensorDeviceApiPort;
import org.mga.ApiGateway.infratsructure.LoggerServiceUdpClientAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.IOException;

@Service
public class LogDispatcherService implements SensorDeviceApiPort {

    @Autowired
    private LoggerServicePort loggerServicePort;
    //private LoggerServiceUdpClientAdapter loggerServiceUdpClientAdapter;

    @Autowired
    private final GpbConverterService gpbConverterService;

    public LogDispatcherService(LoggerServicePort loggerServiceUdpClientAdapter,
                                GpbConverterService gpbConverterService) {
        this.loggerServicePort = loggerServiceUdpClientAdapter;
        this.gpbConverterService = gpbConverterService;
    }


    public Mono<Void> dispatchLogEvent(LogEvent logEvent) throws IOException {
        return loggerServicePort.sendMessage(gpbConverterService.convertLogEventToGpb(logEvent));
    }
}
