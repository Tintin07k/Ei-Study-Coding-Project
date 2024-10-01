package com.smarthome.patterns.creational;

import com.smarthome.devices.Thermostat;
import com.smarthome.exceptions.UnsupportedActionException;

public class ThermostatBuilder implements DeviceBuilder<Thermostat> {
	
    private int id;
    private String type;
    private String status;

    @Override
    public DeviceBuilder<Thermostat> setId(int id) {
        this.id = id;
        return this;
    }

    @Override
    public DeviceBuilder<Thermostat> setType(String type) {
        this.type = type;
        return this;
    }

    @Override
    public DeviceBuilder<Thermostat> setStatus(String status) {
        this.status = status;
        return this;
    }

    @Override
    public Thermostat build() throws NumberFormatException, UnsupportedActionException {
        return new Thermostat(id, type, Integer.parseInt(status));
    }
}