package com.smarthome.modes;

import com.smarthome.devices.Thermostat;
import com.smarthome.exceptions.UnsupportedActionException;
import com.smarthome.patterns.behavioural.TemperatureControlStrategy;

public class EcoMode implements TemperatureControlStrategy {
	
    @Override
    public void controlTemperature(Thermostat thermostat) throws UnsupportedActionException {
        thermostat.setTemperature(70);
        System.out.println("Temperature set to eco mode: 70 degree");
    }
    
}
