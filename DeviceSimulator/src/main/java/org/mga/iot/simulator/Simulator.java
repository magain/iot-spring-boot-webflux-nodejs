package org.mga.iot.simulator;

import org.mga.iot.simulator.device.Device;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Simulator {
    public static void main(String[] args) {

        int numberOfSimulatedDevices = 20;
        ExecutorService executor = Executors.newFixedThreadPool(numberOfSimulatedDevices);

        for(int i=0;i<numberOfSimulatedDevices;i++) {
            Device device = new Device();
            executor.execute(device);
        }
    }
}
