package org.mga.iot.simulator;

import org.mga.iot.simulator.device.Device;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Simulator {
    public static void main(String[] args) {

        // 20 devices are simulated
        int numberOfSimulatedDevices = 20;

        // Defining a thread pool dimensioned by the number of devices to simulate
        ExecutorService executor = Executors.newFixedThreadPool(numberOfSimulatedDevices);

        // create device by device
        for(int i=0;i<numberOfSimulatedDevices;i++) {
            // instanciate a device to be simulated
            Device device = new Device();

            // Let's go and start the device
            executor.execute(device);
        }
    }
}
