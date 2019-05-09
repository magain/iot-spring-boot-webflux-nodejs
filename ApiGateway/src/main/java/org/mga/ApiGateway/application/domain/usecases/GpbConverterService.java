package org.mga.ApiGateway.application.domain.usecases;

import org.mga.ApiGateway.application.domain.model.LogEvent;
import org.mga.ApiGateway.application.domain.model.protobuf.LogEventMessageOuterClass.LogEventMessage;
import org.springframework.stereotype.Service;

@Service
public class GpbConverterService {

    public LogEventMessage convertLogEventToGpb(LogEvent logEvent) {

        // Build the LogEventMessage (calss has been generated using protobuf-java)
        // and return it
        return LogEventMessage.newBuilder()
                              .setEvent(logEvent.getEvent())
                              .setDeviceId(logEvent.getDeviceId())
                              .setTimestamp(logEvent.getTimestamp())
                              .build();
    }
}
