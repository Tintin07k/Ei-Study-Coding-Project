package com.smarthome.devices;

import com.smarthome.exceptions.UnsupportedActionException;

public class HeaterAdapter extends AbstractDevice {
	
    private String status;                // Door status (e.g., "locked", "unlocked")


    public HeaterAdapter(LegacyHeater legacyHeater, int id,  String type, String status) {
    	super(id,type);
        this.status = status;
    }

    @Override
    public void turnOn(int id) {
    	if (!status.equals("on")) {
            status = "on";
            logger.info("Heater " + id + " is turned on.");
            System.out.println("Heater " + id + " is now on.");
        } else {
            logger.warning("Heater " + id + " is already on.");
            System.out.println("Heater " + id + " is already on.");
        }
    }

    @Override
    public void turnOff(int id) {
    	if (!status.equals("off")) {
            status = "off";
            logger.info("Heater " + id + " is turned off.");
            System.out.println("Heater " + id + " is now off.");
        } else {
            logger.warning("Heater " + id + " is already off.");
            System.out.println("Heater " + id + " is already off.");
        }
    }

    @Override
    public void update(String message) throws UnsupportedActionException {
        logger.info("[Received update for Heater " + getId() + ": " + message + "]");
        // You might add more logic here based on your application's requirements
    }

    @Override
    public String deviceType() {
        return "Heater";
    }

    @Override
    public int getId() {
        return id;
    }

	public Object getStatus() {
		return status;
	}
}
