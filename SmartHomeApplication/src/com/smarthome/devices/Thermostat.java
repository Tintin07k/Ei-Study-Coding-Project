package com.smarthome.devices;

import java.util.logging.Logger;
import com.smarthome.core.SmartHomeHub;
import com.smarthome.exceptions.UnsupportedActionException;
import com.smarthome.patterns.behavioural.TemperatureControlStrategy;

/**
 * The {@code Thermostat} class represents a smart home thermostat device.
 * It implements the {@link Device} interface and provides methods to control and query the thermostat's status.
 */
public class Thermostat extends AbstractDevice {

    private int temperature; // The current temperature set on the thermostat
    private TemperatureControlStrategy strategy;
    private static final String TYPE = "Thermostat"; // Constant for device type
    private static final Logger logger = Logger.getLogger(Thermostat.class.getName());

    public Thermostat(int id, String type, int initialTemperature) throws UnsupportedActionException {
    	super(id,type);
    	setTemperature(initialTemperature); //Validations are triggered via setTemperature method
    }
    
    public void setStrategy(TemperatureControlStrategy strategy) {
        this.strategy = strategy;
    }

    public void control() throws UnsupportedActionException {
        if (strategy != null) {
            strategy.controlTemperature(this);
        }
    }

    @Override
    public void turnOn(int id) {
        if (this.id == id) {
            logger.info("Thermostat " + id + " is turned on.");
            System.out.println("Thermostat " + id + " is now on.");
        } else {
            logger.warning("Invalid ID for turning on thermostat: " + id);
            System.out.println("Invalid ID: " + id);
        }
    }

    @Override
    public void turnOff(int id) {
        if (this.id == id) {
            logger.info("Thermostat " + id + " is turned off.");
            System.out.println("Thermostat " + id + " is now off.");
        } else {
            logger.warning("Invalid ID for turning off thermostat: " + id);
            System.out.println("Invalid ID: " + id);
        }
    }

    @Override
    public String deviceType() {
        return TYPE; // Return constant for device type
    }

    public int getTemperature() {
        return temperature;
    }

    public int getId() {
        return id;
    }

    public void setTemperature(int temperature) throws UnsupportedActionException {
        // Check for valid temperature range before setting
        if (temperature < 50 || temperature > 85) {
            throw new UnsupportedActionException("Temperature " + temperature + " is out of range.");
        }
        this.temperature = temperature;
        logger.info("Temperature of Thermostat " + id + " set to " + temperature + " degrees.");
        SmartHomeHub.getInstance().checkTriggers(); // Check for triggers after setting temperature
    }

    @Override
    public void update(String message) throws UnsupportedActionException {
        logger.info("[Received update for Thermostat " + getId() + ": " + message + "]");
        
    }
}