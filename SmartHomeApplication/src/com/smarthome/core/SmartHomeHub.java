package com.smarthome.core;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.smarthome.devices.Device;
import com.smarthome.devices.Door;
import com.smarthome.devices.HeaterAdapter;
import com.smarthome.devices.Light;
import com.smarthome.devices.Thermostat;
import com.smarthome.exceptions.InvalidTriggerException;
import com.smarthome.exceptions.UnsupportedActionException;
import com.smarthome.patterns.behavioural.DeviceObserver;

/**
 * The {@code SmartHomeHub} class represents a central hub for controlling smart home devices,
 * scheduling tasks, and managing triggers.
 */

public class SmartHomeHub {
	

    private static SmartHomeHub currentInstance;
    private final List<Device> devices = new ArrayList<>();
    private final List<Schedule> schedules = new ArrayList<>();
    private final List<Trigger> triggers = new ArrayList<>();
    private final List<DeviceObserver> observers = new ArrayList<>();
    private Queue<Schedule> pendingTasks = new ConcurrentLinkedQueue<>();  // Use thread-safe queue
    private static final Logger logger = Logger.getLogger(SmartHomeHub.class.getName());
    private static final String TURN_ON_ACTION = "Turn On";
    private static final String TURN_OFF_ACTION = "Turn Off";
    
    private Timer timer = new Timer();

    private SmartHomeHub() {}
    
    public static SmartHomeHub getInstance() {
        if (currentInstance == null) {
        	currentInstance = new SmartHomeHub();
        }
        return currentInstance;
    }
    
    public void getScheduleReport() {
    	for(Schedule schedule : schedules) {
    		System.out.println(schedule);
    	}
    }
    
    public void printPendingTasks() {
        if (pendingTasks.isEmpty()) {
            System.out.println("No pending Schdeules.");
        } else {
            System.out.print("Schdeuled Tasks: ");
        	System.out.println(pendingTasks);        

        }
    }
    
    public void printPendingTriggers() {
    	 if (triggers.isEmpty()) {
             System.out.println("No pending Triggers.");
         } else {
            System.out.print("Automated Triggers: ");
         	System.out.println(triggers);        

         }
      
    }
    
    public void getTriggerReport() {
    	
    }

    public void addDevice(Device device) {
        devices.add(device);
        observers.add(device);
    }

    public void removeDevice(Device device) {
        devices.remove(device);
        observers.remove(device);
    }

    private void notifyObservers(String message) throws UnsupportedActionException {
        for (DeviceObserver observer : observers) {
            observer.update(message);
        }
    }

    public void turnOn(int id) throws UnsupportedActionException {
        executeDeviceAction(id, TURN_ON_ACTION);
    }

    public void turnOff(int id) throws UnsupportedActionException {
        executeDeviceAction(id, TURN_OFF_ACTION);
    }
    
    public void setStrategy(Device device) throws UnsupportedActionException {
        if (device instanceof Thermostat) {
        	
        }else {
            throw new UnsupportedActionException("Unsupported to Set Strategy for device - " + device.deviceType() + " : " + device.getId());
        }
    }
  

    private void executeDeviceAction(int id, String action) throws UnsupportedActionException {
        boolean deviceFound = false;
        for (Device device : devices) {
            if (device.getId() == id) {
                if (action.equals(TURN_ON_ACTION)) {
                    device.turnOn(id);
                    notifyObservers(device.deviceType() + " " + id + " is on.");
                } else if (action.equals(TURN_OFF_ACTION)) {
                    device.turnOff(id);
                    notifyObservers(device.deviceType() + " " + id + " is off.");
                }
                deviceFound = true;
                break;
            }
        }
        if (!deviceFound) {
            logger.log(Level.WARNING, "Device not found with id - " + id);
            throw new UnsupportedActionException("Device not found with id - " + id);
        }
    }

    public void setSchedule(int deviceId, String time, String action) throws UnsupportedActionException {
        Device device = getDeviceById(deviceId);
        if (device != null) {
            try {
            	SimpleDateFormat format = new SimpleDateFormat("HH:mm");
                Date scheduledTime = format.parse(time);
                Date date = new Date();
                String s = format.format(date);
                Date currTime = format.parse(s);

                long delay = scheduledTime.getTime() - currTime.getTime();

                System.out.println("Scheduled Task - [device: " + device.deviceType() + ", time: " + time + ", command: " + action + "]");
                if (delay >= 0) {
                    Schedule schedule = new Schedule(device, time, action);
                    TimerTask task = new TimerTask() {
                        @Override
                        public void run() {
                            schedules.add(schedule);
                            pendingTasks.remove(schedule);  // Remove from pending list when task is done
                            try {
                                schedule.execute();
                                System.out.println("Status Report - " + getStatusReport());
                            } catch (UnsupportedActionException e) {
                                logger.log(Level.WARNING, e.getMessage());
                            }
                        }
                    };

                    // Add task to pending queue (faster than list)
                    pendingTasks.add(schedule);
                    timer.schedule(task, delay);
                } else {
                    throw new UnsupportedActionException("Invalid time");
                }
            } catch (ParseException e) {
                throw new UnsupportedActionException("Invalid time format.");
            }
        } else {
            throw new UnsupportedActionException("Device with ID " + deviceId + " not found.");
        }
    }

    public Device getDeviceById(int id) {
        return devices.stream().filter(device -> device.getId() == id).findFirst().orElse(null);
    }

    public void addTrigger(String condition, String action) throws InvalidTriggerException, UnsupportedActionException {
        String[] arr = action.split("[()]");
        if (arr.length >= 2) {
            String actionType = arr[0];
            int id = Integer.parseInt(arr[1]);
            Device device = getDeviceById(id);
            if (device != null) {
                triggers.add(new Trigger(condition, actionType, id));
            } else {
                throw new UnsupportedActionException("Device with ID " + id + " not found.");
            }
        } else {
            throw new InvalidTriggerException("Invalid Trigger format");
        }
    }

    public void executeSchedules() throws UnsupportedActionException {
        for (Schedule schedule : schedules) {
            schedule.execute();
        }
    }

    public void checkTriggers() throws UnsupportedActionException {
        for (Device device : devices) {
            for (Trigger trigger : triggers) {
                if (trigger.isTriggered(device)) {
                    executeAction(trigger.getAction(), trigger.getId());
                }
            }
        }
    }

    private void executeAction(String action, int id) throws UnsupportedActionException {
        if (action.equals("turnOff")) {
            turnOff(id);
        } else if (action.equals("turnOn")) {
            turnOn(id);
        } else {
            throw new UnsupportedActionException("Unsupported Action - " + action);
        }
    }
    
    public void shutdown() {
        timer.cancel(); // This will stop the Timer tasks
        System.out.println("SmartHomeHub shut down successfully.");
    }

    public String getStatusReport() throws UnsupportedActionException {
        StringBuilder sb = new StringBuilder();
        for (Device device : devices) {
            sb.append(device.deviceType()).append(" ").append(device.getId()).append(" is ");
            if (device instanceof Light) {
                sb.append(((Light) device).getStatus()).append(".");
            } else if (device instanceof Thermostat) {
                sb.append("set to ").append(((Thermostat) device).getTemperature()).append(" degrees.");
            } else if (device instanceof Door) {
                sb.append(((Door) device).getStatus()).append(".");
            }  else if (device instanceof HeaterAdapter) {
                sb.append(((HeaterAdapter) device).getStatus()).append(".");
            } else {
                throw new UnsupportedActionException("Device - " + device.deviceType() + " report doesn't exist");
            }
        }
        return sb.toString();
    }

}