package com.smarthome.patterns.structural;

import java.util.ArrayList;
import java.util.List;

import com.smarthome.devices.Device;
import com.smarthome.exceptions.UnsupportedActionException;

public class DeviceGroup implements DeviceComponent {
    private List<Device> devices = new ArrayList<>();

    public void addDevice(Device device) {
        devices.add(device);
    }

    @Override
    public void turnOn(int id) {
        for (Device device : devices) {
            device.turnOn(device.getId());
        }
    }

    @Override
    public void turnOff(int id) {
        for (Device device : devices) {
            device.turnOff(device.getId());
        }
    }
    
    // Overloaded methods without the need to pass 'id'
    public void turnOn() throws UnsupportedActionException {
        turnOn(0);  // Default behavior to turn on all devices
    }

    public void turnOff() throws UnsupportedActionException {
        turnOff(0);  // Default behavior to turn off all devices
    }

}