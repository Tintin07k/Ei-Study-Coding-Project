package com.smarthome.patterns.creational;

import com.smarthome.devices.Device;
import com.smarthome.exceptions.UnsupportedActionException;

public interface DeviceBuilder<T extends Device> {
	
    DeviceBuilder<T> setId(int id);
    DeviceBuilder<T> setType(String type);
    DeviceBuilder<T> setStatus(String status);
    T build() throws NumberFormatException, UnsupportedActionException;
    
}
