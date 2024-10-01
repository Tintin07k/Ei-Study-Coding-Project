package com.smarthome.modes;

import com.smarthome.devices.Thermostat;
import com.smarthome.exceptions.UnsupportedActionException;
import com.smarthome.patterns.behavioural.TemperatureControlStrategy;

public class ComfortMode implements TemperatureControlStrategy {
	
    @Override
    public void controlTemperature(Thermostat thermostat) throws UnsupportedActionException {
        thermostat.setTemperature(65);
        System.out.println("Temperature set to comfort mode: 65 degree");
    }
    
}