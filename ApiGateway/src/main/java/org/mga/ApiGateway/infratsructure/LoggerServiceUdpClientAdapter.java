package org.mga.ApiGateway.infratsructure;

import org.mga.ApiGateway.application.domain.model.protobuf.LogEventMessageOuterClass.LogEventMessage;
import org.mga.ApiGateway.application.ports.LoggerServicePort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.net.*;

@Service
public class LoggerServiceUdpClientAdapter implements LoggerServicePort {

    private DatagramSocket socket;
    private InetAddress address = InetAddress.getLoopbackAddress();

    public LoggerServiceUdpClientAdapter() {
        initSocket();
    }

    private void initSocket() {
        try {
            this.socket = new DatagramSocket();
            this.address = InetAddress.getByName("127.0.0.1");

        } catch (SocketException socketException) {
            System.out.print(socketException);
        } catch (UnknownHostException unknownHostException) {
            System.out.print(unknownHostException);
        }
    }

    // Sends out the binary representation for logEventMessage (Google Protocol Buffer)
    // The possible IOException is not catched here and will be passed all the way back
    // to the RestController
    public Mono<Void> sendMessage(LogEventMessage logEventMessage) throws IOException {
        byte[] sendBuffer = logEventMessage.toByteArray();  // this is doing the trick
        byte[] receiveBuffer = new byte[1000];
        DatagramPacket sendDatagramPacket = prepareDatagramPacket(sendBuffer);
        socket.setSoTimeout(500);
        socket.send(sendDatagramPacket);
        DatagramPacket receivedDatagramPacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
        socket.receive(receivedDatagramPacket);

        return Mono.empty();    // Everything is good. So put an empty Mono onto the chain back to the RestController
    }

    private DatagramPacket prepareDatagramPacket(byte[] buffer) {
        return new DatagramPacket(buffer, buffer.length, this.address, 7070);
    }

}
