package com.smarthome.core;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.smarthome.devices.Device;
import com.smarthome.devices.Thermostat;

/**
 * The {@code Trigger} class represents a trigger condition and action for automating tasks
 * in a smart home system.
 */
public class Trigger {
    private final String condition;
    private final String action;
    private final int id;
    private static final Logger logger = Logger.getLogger(Trigger.class.getName());

    /**
     * Constructs a Trigger instance with the specified condition, action, and device ID.
     *
     * @param condition the condition that triggers the action.
     * @param action    the action to be executed when the condition is met.
     * @param id        the ID of the device associated with the trigger.
     */
    public Trigger(String condition, String action, int id) {
        this.condition = condition;
        this.action = action;
        this.id = id;
    }

    /**
     * Checks if the trigger condition is met for the specified device.
     *
     * @param device the device to check the condition against.
     * @return true if the condition is met; false otherwise.
     */
    public boolean isTriggered(Device device) {
        if (condition != null && !condition.isEmpty()) {
            String[] arr = condition.split(" ");
            if (arr.length == 3) {
                String property = arr[0];
                String operator = arr[1];
                int value;

                try {
                    value = Integer.parseInt(arr[2]);
                } catch (NumberFormatException e) {
                    logger.log(Level.WARNING, "Invalid value for condition: " + arr[2], e);
                    return false;
                }

                if (device instanceof Thermostat && "temperature".equals(property)) {
                    int deviceTemperature = ((Thermostat) device).getTemperature();
                    return evaluateCondition(deviceTemperature, operator, value);
                } else {
                    logger.log(Level.WARNING, "Unsupported device type for trigger condition.");
                }
            } else {
                logger.log(Level.WARNING, "Invalid trigger condition format.");
            }
        }
        return false;
    }

    private boolean evaluateCondition(int deviceValue, String operator, int targetValue) {
        switch (operator) {
            case ">":
                return deviceValue > targetValue;
            case "<":
                return deviceValue < targetValue;
            case ">=":
                return deviceValue >= targetValue;
            case "<=":
                return deviceValue <= targetValue;
            case "==":
                return deviceValue == targetValue;
            default:
                logger.log(Level.WARNING, "Unsupported operator: " + operator);
                return false;
        }
    }

    public String getAction() {
        return action;
    }

    public String getCondition() {
        return condition;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "{condition: " + condition + ", action: " + action + ", deviceId: " + id + "}";
    }
}