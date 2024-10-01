package com.smarthome;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.smarthome.core.SmartHomeHub;
import com.smarthome.devices.Device;
import com.smarthome.devices.HeaterAdapter;
import com.smarthome.devices.LegacyHeater;
import com.smarthome.devices.Thermostat;
import com.smarthome.exceptions.InvalidTriggerException;
import com.smarthome.exceptions.UnauthorizedAccessException;
import com.smarthome.exceptions.UnsupportedActionException;
import com.smarthome.modes.ComfortMode;
import com.smarthome.modes.EcoMode;
import com.smarthome.patterns.behavioural.TemperatureControlStrategy;
import com.smarthome.patterns.creational.DeviceFactory;
import com.smarthome.patterns.creational.ThermostatBuilder;
import com.smarthome.patterns.structural.DeviceGroup;
import com.smarthome.patterns.structural.DeviceProxy;

/**
 * The {@code Main} class is the entry point for the Smart Home System application.
 */

public class Main {
    private final static Logger logger = Logger.getLogger(Main.class.getName());
    public static void main(String[] args) {

        try {
            // initialize the smart home hub
            SmartHomeHub smartHomeHub = SmartHomeHub.getInstance();

            // initialize the devices
            Device light1 = DeviceFactory.buildDevice(1,"light","off");
            Device thermostat1 = DeviceFactory.buildDevice(2,"thermostat","75");
            Device door1 = DeviceFactory.buildDevice(3,"door","locked");
            Device door2 = DeviceFactory.buildDevice(4,"door","locked");


            // add devices to smart home hub
            smartHomeHub.addDevice(light1);
            smartHomeHub.addDevice(thermostat1);
            smartHomeHub.addDevice(door1);
            smartHomeHub.addDevice(door2);


            // turn on device with given id
            smartHomeHub.turnOn(1);


            // schedule task with timer (set timer before running the program)
            smartHomeHub.setSchedule(1,"23:45","Turn On");


            // set triggers
            smartHomeHub.addTrigger("temperature > 70", "turnOff(1)");

            // remove device dynamically
            smartHomeHub.removeDevice(door2);

            // initialize proxy device with access control only username with "admin" can access as of now
            DeviceProxy deviceProxy = new DeviceProxy(light1,"admin");
            deviceProxy.turnOn();

            // execution of schedule and triggers
            smartHomeHub.executeSchedules();
            smartHomeHub.checkTriggers();

            // print status report
            System.out.println("Status Report - " + smartHomeHub.getStatusReport());
            
            //Behavioral Design Pattern
            
//          Use Case 1: Observer Pattern for Device Status Monitoring - When Main Door is Unlocked - All Lights are turned ON , When Main Door is Locked - All Lights are turned OFF
            //Assuming Door 1 is main door

            System.out.println("Use Case 1");

            smartHomeHub.turnOn(3);
            
            System.out.println("Status Report - " + smartHomeHub.getStatusReport());
            
            smartHomeHub.turnOff(3);

            System.out.println("Status Report - " + smartHomeHub.getStatusReport());

            
//          Use Case 2: Strategy Pattern for Temperature Control - Different temperature control strategies can be applied based on the user’s preference or energy-saving mode (e.g., Eco mode, Comfort mode).

            System.out.println("Use Case 2");

            TemperatureControlStrategy ecoMode = new EcoMode();
            TemperatureControlStrategy comfortMode = new ComfortMode();

            Thermostat thermostat = (Thermostat) thermostat1;

            System.out.println("Testing Eco Mode:");
            thermostat.setStrategy(ecoMode);
            thermostat.control(); // Should set temperature to 70 degree

            System.out.println("\nTesting Comfort Mode:");
            thermostat.setStrategy(comfortMode);
            thermostat.control(); // Should set temperature to 65 degree
            
            
            //Creational Design Patterns
            
//          Use Case 3: Factory Pattern for Device Creation - A factory method can be used to create different types of smart home devices based on input parameters, ensuring a single point of creation.. Gives error if device is not supported
            
            System.out.println("Use Case 3");

            try {
                Device sensor = DeviceFactory.buildDevice(6,"sensor","off");
                System.out.println(sensor.getId());
			} catch (IllegalArgumentException e) {
	            System.out.println(e);
			}
            
//          Use Case 3 Alternative : Builder Pattern for Device Creation (Alternate as Factory was already implemented)
            
            Thermostat thermostatBuilder = new ThermostatBuilder()
                    .setId(7)
                    .setType("thermostat")
                    .setStatus("60")
                    .build();

            smartHomeHub.addDevice(thermostatBuilder);
            
            System.out.println("Status Report - " + smartHomeHub.getStatusReport()); 

            
//          Use Case 4: Singleton Pattern for SmartHomeHub - Only one instance present, hence ensuring that there is only one instance of the SmartHomeHub controlling all devices.

            System.out.println("Use Case 4");

            System.out.println("Status Report - " + smartHomeHub.getStatusReport());

            smartHomeHub = SmartHomeHub.getInstance();

            System.out.println("Status Report - " + smartHomeHub.getStatusReport()); //Same report as we are getting same instance
            
            
            // Structural Design Patterns
            
//          Use Case 5: Adapter Pattern for Legacy Device Integration - An adapter can be used to integrate legacy devices that do not follow the same interface as the new smart home devices.
            
            System.out.println("Use Case 5");
            
            LegacyHeater legacyHeater = new LegacyHeater();
            HeaterAdapter heaterAdapter = new HeaterAdapter(legacyHeater, 8, "heater", "off"); 
            smartHomeHub.addDevice(heaterAdapter);
            
            smartHomeHub.turnOn(8);

            System.out.println("Status Report - " + smartHomeHub.getStatusReport()); 
            
            smartHomeHub.turnOff(8);

            System.out.println("Status Report - " + smartHomeHub.getStatusReport()); 
            
//          Use Case 6: Composite Pattern for Grouping Devices - The composite pattern can be used to group multiple devices together, allowing operations to be performed on the group as a whole.

            System.out.println("Use Case 6");
            
            Device livingRoomLight1 = DeviceFactory.buildDevice(9,"light","off");
            Device livingRoomLight2 = DeviceFactory.buildDevice(10,"light","off");
            Device livingRoomLight3 = DeviceFactory.buildDevice(11,"light","off");
            
            smartHomeHub.addDevice(livingRoomLight1);
            smartHomeHub.addDevice(livingRoomLight2);
            smartHomeHub.addDevice(livingRoomLight3);

            
            DeviceGroup livingRoomGroup = new DeviceGroup();

            livingRoomGroup.addDevice(livingRoomLight1);
            livingRoomGroup.addDevice(livingRoomLight2);
            livingRoomGroup.addDevice(livingRoomLight3);
            
            livingRoomGroup.turnOn();
            System.out.println("Status Report - " + smartHomeHub.getStatusReport()); 
            
            livingRoomGroup.turnOff();
            System.out.println("Status Report - " + smartHomeHub.getStatusReport()); 
            
            
            //Current Schedule
            smartHomeHub.printPendingTasks();
            smartHomeHub.printPendingTriggers();
            
            smartHomeHub.turnOn(1);
            Thermostat thermostat2 = new ThermostatBuilder()
                    .setId(7)
                    .setType("thermostat")
                    .setStatus("75")
                    .build();
            smartHomeHub.addDevice(thermostat2);
            
            System.out.println("Status Report - " + smartHomeHub.getStatusReport()); 

            
            smartHomeHub.checkTriggers(); // light 1 is turned off as trigger is on

            System.out.println("Status Report - " + smartHomeHub.getStatusReport()); 


//            smartHomeHub.shutdown();

        }catch (UnsupportedActionException | InvalidTriggerException | UnauthorizedAccessException e){
            logger.log(Level.WARNING,e.getMessage());
            System.out.println("Error - " + "Some Error occurred");
        }
    }
}
