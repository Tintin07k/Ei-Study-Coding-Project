package com.smarthome.patterns.behavioural;

import com.smarthome.devices.Thermostat;
import com.smarthome.exceptions.UnsupportedActionException;

public interface TemperatureControlStrategy {
	
    void controlTemperature(Thermostat thermostat) throws UnsupportedActionException;
}
