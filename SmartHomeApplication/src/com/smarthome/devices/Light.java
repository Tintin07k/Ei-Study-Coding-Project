package com.smarthome.devices;

import java.util.logging.Logger;

import com.smarthome.patterns.structural.DeviceComponent;

/**
 * The {@code Light} class represents a smart home light device.
 * It implements the {@link Device} interface and provides methods to control and query the light's status.
 */
public class Light extends AbstractDevice implements DeviceComponent{

    private String status; // "on" or "off"
    private static final Logger logger = Logger.getLogger(Light.class.getName());

    public Light(int id, String type, String status) {
    	super(id,type);
        this.status = status;
    }

    @Override
    public void turnOn(int id) {
        if (this.id == id) {
            if (!status.equals("on")) {
                status = "on";
                logger.info("Light " + id + " is turned on.");
                System.out.println("Light " + id + " is now on.");
            } else {
                logger.warning("Light " + id + " is already on.");
                System.out.println("Light " + id + " is already on.");
            }
        } else {
            logger.warning("Invalid ID for turning on light: " + id);
            System.out.println("Invalid ID: " + id);
        }
    }

    @Override
    public void turnOff(int id) {
        if (this.id == id) {
            if (!status.equals("off")) {
                status = "off";
                logger.info("Light " + id + " is turned off.");
                System.out.println("Light " + id + " is now off.");
            } else {
                logger.warning("Light " + id + " is already off.");
                System.out.println("Light " + id + " is already off.");
            }
        } else {
            logger.warning("Invalid ID for turning off light: " + id);
            System.out.println("Invalid ID: " + id);
        }
    }

    @Override
    public String deviceType() {
        return type;
    }

    public String getStatus() {
        return status;
    }

    public int getId() {
        return id;
    }

    @Override
    public void update(String message) {
        logger.info("[Received update for Light " + getId() + ": " + message + "]");
        
        switch (message) {
        /*Assuming door 1 is always main door*/
		case "door 3 is on.": {
			this.status = "on";
			break;
		}
		case "door 3 is off.": {
			this.status = "off";
			break;
		}
		}
    }
}