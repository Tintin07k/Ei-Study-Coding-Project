package com.smarthome.patterns.creational;

import com.smarthome.devices.Device;
import com.smarthome.devices.Door;
import com.smarthome.devices.Light;
import com.smarthome.devices.Thermostat;
import com.smarthome.exceptions.UnsupportedActionException;

/**
 * The {@code DeviceFactory} class is responsible for creating instances of various devices in a smart home system.
 */

public class DeviceFactory {
    public static Device buildDevice(int id,String type,String status) throws NumberFormatException, UnsupportedActionException{
        return switch (type.toLowerCase()) {
            case "light" -> new Light(id, type, status);
            case "thermostat" -> new Thermostat(id, type, Integer.parseInt(status));
            case "door" -> new Door(id, type, status);
            default -> throw new IllegalArgumentException("Unsupported device type - " + type);
        };
    }
}
