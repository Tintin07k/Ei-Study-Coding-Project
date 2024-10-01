package com.smarthome;

import com.smarthome.core.SmartHomeHub;
import com.smarthome.devices.Device;
import com.smarthome.devices.Thermostat;
import com.smarthome.exceptions.InvalidTriggerException;
import com.smarthome.exceptions.UnsupportedActionException;
import com.smarthome.modes.ComfortMode;
import com.smarthome.modes.EcoMode;
import com.smarthome.patterns.behavioural.TemperatureControlStrategy;
import com.smarthome.patterns.creational.DeviceFactory;

import java.util.Scanner;

public class MainConsole {
    private final SmartHomeHub smartHomeHub = SmartHomeHub.getInstance();
    private final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        MainConsole console = new MainConsole();
        console.run();
    }

    private void run() {
        boolean continueRunning = true;

        while (continueRunning) {
            showMenu();
            int choice = getUserInput();
            switch (choice) {
                case 1:
                    createDevice();
                    break;
                case 2:
                    performDeviceAction();
                    break;
                case 3:
                    viewCurrentDevices();
                    break;
                case 4:
                    removeDevice();
                    break;
                case 5:
                    setControlSystemForThermostat(); // New menu option for setting control systems
                    break;
                case 6:
                    setSchedule(); // New option for setting a schedule
                    break;
                case 7:
                    createTrigger(); // New option for creating a trigger
                    break;
                case 8:
                    listPendingSchedules(); // New option for listing pending schedules
                    break;
                case 9:
                    listPendingTriggers(); // New option for listing pending triggers
                    break;
                case 10:
                    continueRunning = false;
                    exitSystem();
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
        System.out.println("Exiting Smart Home System.");
    }

    private void setSchedule() {
        System.out.print("Enter Device ID: ");
        int scheduleId = getUserInput();
        System.out.print("Enter time for the schedule (HH:MM): ");
        String time = scanner.nextLine();
        System.out.print("Enter action to perform (Turn On/Turn Off): ");
        String action = scanner.nextLine();

        if (!action.equalsIgnoreCase("Turn On") && !action.equalsIgnoreCase("Turn Off")) {
            System.out.println("Error: Invalid action. Please enter 'Turn On' or 'Turn Off'.");
            return; // Exit the method if action is invalid
        }

        try {
            smartHomeHub.setSchedule(scheduleId, time, action);
            System.out.println("Schedule set successfully.");
        } catch (UnsupportedActionException e) {
            System.out.println("Exception while trying to set the given schedule: " + e.getMessage());
        }
    }

    private void createTrigger() {
        System.out.print("Enter the condition for the trigger: ");
        String condition = scanner.nextLine();
        System.out.print("Enter the action to perform when triggered: ");
        String action = scanner.nextLine();

        try {
            smartHomeHub.addTrigger(condition, action);
            System.out.println("Trigger created successfully.");
        } catch (InvalidTriggerException e) {
            System.out.println("Invalid Trigger Exception" + e.getMessage());
        } catch (UnsupportedActionException e) {
            System.out.println("Given Action Exception" + e.getMessage());
        }
    }

    private void listPendingSchedules() {
        System.out.println("Pending schedules:");
        smartHomeHub.printPendingTasks();
    }

    private void listPendingTriggers() {
        System.out.println("Pending triggers:");
        smartHomeHub.printPendingTriggers();
    }

    private void setControlSystemForThermostat() {
        System.out.print("Enter the ID of the thermostat you want to control: ");
        int thermostatId = getUserInput();

        Device thermostat = smartHomeHub.getDeviceById(thermostatId);
        if (thermostat != null && thermostat instanceof Thermostat) {
            System.out.println("Select an option:");
            System.out.println("1. Set control strategy");
            System.out.println("2. Set custom temperature");
            System.out.print("Choose an option: ");
            int choice = getUserInput();

            switch (choice) {
                case 1:
                    System.out.println("Select control strategy:");
                    System.out.println("1. Eco Mode");
                    System.out.println("2. Comfort Mode");
                    System.out.print("Choose an option: ");
                    int strategyChoice = getUserInput();

                    TemperatureControlStrategy strategy = switch (strategyChoice) {
                        case 1 -> new EcoMode();
                        case 2 -> new ComfortMode();
                        default -> {
                            System.out.println("Invalid choice. No changes made.");
                            yield null;
                        }
                    };

                    if (strategy != null) {
                        ((Thermostat) thermostat).setStrategy(strategy);
                        try {
                            ((Thermostat) thermostat).control();
                        } catch (UnsupportedActionException e) {
                            System.out.println("Some issue occurred while controlling the thermostat with the selected strategy.");
                        }
                        System.out.println("Control strategy set successfully for thermostat ID " + thermostatId);
                    }
                    break;

                case 2:
                    System.out.print("Enter the custom temperature: ");
                    String tempInput = scanner.nextLine();
                    try {
                        int customTemperature = Integer.parseInt(tempInput);
                        ((Thermostat) thermostat).setTemperature(customTemperature);
                        System.out.println("Custom temperature set to " + customTemperature + " for thermostat ID " + thermostatId);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid temperature. Please enter a numeric value.");
                    } catch (UnsupportedActionException e) {
                        System.out.println("Unsupported Action.");
                    }
                    break;

                default:
                    System.out.println("Invalid choice. No changes made.");
            }
        } else {
            System.out.println("Thermostat not found or invalid ID.");
        }
    }

    private void showMenu() {
        System.out.println("Smart Home System");
        System.out.println("1. Create a new device");
        System.out.println("2. Select a device and perform an action");
        System.out.println("3. View current devices");
        System.out.println("4. Remove a device");
        System.out.println("5. Set control system or custom temperature for thermostat");
        System.out.println("6. Set a schedule");
        System.out.println("7. Create a trigger");
        System.out.println("8. List pending schedules");
        System.out.println("9. List pending triggers");
        System.out.println("10. Exit");
        System.out.print("Choose an option: ");
    }

    private int getUserInput() {
        while (true) {
            String input = scanner.nextLine();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a numeric option: ");
            }
        }
    }

    private void createDevice() {
        System.out.print("Enter device type (light/thermostat/door): ");
        String type = scanner.nextLine();

        int id;
        while (true) {
            System.out.print("Enter device ID: ");
            String idInput = scanner.nextLine();
            try {
                id = Integer.parseInt(idInput);
                if (smartHomeHub.getDeviceById(id) == null) {
                    break; // Unique ID
                } else {
                    System.out.println("ID already exists. Please enter a unique device ID.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid ID. Please enter a numeric device ID.");
            }
        }

        String status = null;
        boolean validStatus = false;

        while (!validStatus) {
            if ("light".equalsIgnoreCase(type)) {
                System.out.print("Enter device status (on/off): ");
                status = scanner.nextLine();
                if ("on".equals(status) || "off".equals(status)) {
                    validStatus = true;
                } else {
                    System.out.println("Invalid status for light. Please enter 'on' or 'off'.");
                }
            } else if ("thermostat".equalsIgnoreCase(type)) {
                System.out.print("Enter temperature: ");
                String tempInput = scanner.nextLine();
                try {
                    int temperature = Integer.parseInt(tempInput);
                    status = String.valueOf(temperature);
                    validStatus = true;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid temperature. Please enter a numeric value.");
                }
            } else if ("door".equalsIgnoreCase(type)) {
                System.out.print("Enter door status (locked/unlocked): ");
                status = scanner.nextLine();
                if ("locked".equals(status) || "unlocked".equals(status)) {
                    validStatus = true;
                } else {
                    System.out.println("Invalid status for door. Please enter 'locked' or 'unlocked'.");
                }
            } else {
                System.out.println("Unsupported device type. Please enter 'light', 'thermostat', or 'door'.");
                return;
            }
        }

        try {
            Device device = DeviceFactory.buildDevice(id, type, status);
            smartHomeHub.addDevice(device);
            System.out.println("Device added successfully.");
        } catch (UnsupportedActionException e) {
            System.out.println("Error adding device: " + e.getMessage());
        }
    }

    private void performDeviceAction() {
        System.out.print("Enter device ID to perform action: ");
        int id = getUserInput();

        try {
            System.out.print("Enter action (turn on/turn off): ");
            String action = scanner.nextLine();
            if ("turn on".equalsIgnoreCase(action)) {
                smartHomeHub.turnOn(id);
            } else if ("turn off".equalsIgnoreCase(action)) {
                smartHomeHub.turnOff(id);
            } else {
                System.out.println("Invalid action. Please enter 'turn on' or 'turn off'.");
            }
        } catch (UnsupportedActionException e) {
            System.out.println(e.getMessage());
        }
    }

    private void viewCurrentDevices() {
        try {
            System.out.println("Current devices:");
            System.out.println(smartHomeHub.getStatusReport());
        } catch (UnsupportedActionException e) {
            System.out.println("Error retrieving device status: " + e.getMessage());
        }
    }

    private void removeDevice() {
        int id;
        while (true) {
            System.out.print("Enter device ID to remove: ");
            String idInput = scanner.nextLine();
            try {
                id = Integer.parseInt(idInput);
                if (smartHomeHub.getDeviceById(id) != null) {
                    smartHomeHub.removeDevice(smartHomeHub.getDeviceById(id));
                    System.out.println("Device removed successfully.");
                    break;
                } else {
                    System.out.println("Device not found. Please enter a valid device ID.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a numeric device ID.");
            }
        }
    }

    private void exitSystem() {
        System.out.println("Thank you for using the Smart Home System.");
    }
}
