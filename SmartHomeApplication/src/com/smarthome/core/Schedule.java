package com.smarthome.core;

import java.util.logging.Level;
import java.util.logging.Logger;
import com.smarthome.devices.Device;
import com.smarthome.exceptions.UnsupportedActionException;

/**
 * The {@code Schedule} class represents a scheduled task for a smart home device.
 * It specifies a time and action to be executed on a particular device.
 */
public class Schedule {
    private final Device device;     // The device associated with the schedule
    @SuppressWarnings("unused")
	private final String time;        // The time at which to execute the action
    private final String action;      // The action to be executed (e.g., "Turn On", "Turn Off")
    private static final Logger logger = Logger.getLogger(Schedule.class.getName());

    /**
     * Constructs a new Schedule for a specific device.
     *
     * @param device   the device associated with the schedule
     * @param time     the time at which to execute the action
     * @param action   the action to be executed on the device
     */
    public Schedule(Device device, String time, String action) {
        this.device = device;
        this.time = time;
        this.action = action;
    }

    /**
     * Executes the scheduled action on the specified device.
     *
     * @throws UnsupportedActionException if the action is not supported
     */
    public void execute() throws UnsupportedActionException {
        try {
            switch (action) {
                case "Turn On":
                    device.turnOn(device.getId());
                    logger.info("Executed action: " + action + " on device ID: " + device.getId());
                    break;

                case "Turn Off":
                    device.turnOff(device.getId());
                    logger.info("Executed action: " + action + " on device ID: " + device.getId());
                    break;

                default:
                    logger.log(Level.WARNING, "Unsupported Action - " + action);
                    throw new UnsupportedActionException("Unsupported Action - " + action);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to execute action: " + action + " on device ID: " + device.getId(), e);
            throw new UnsupportedActionException("Failed to execute action: " + action, e);
        }
    }

	public Device getDevice() {
		return device;
	}

	public String getTime() {
		return time;
	}

	public String getAction() {
		return action;
	}
	
    @Override
    public String toString() {
        return "{device: " + device.getId() + ", time: " + time + ", command: " + action + "}";
    }

}