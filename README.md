# Ei-Study-Coding-Project
Smart Home System Programming Exercise

## Introduction
My name is Karaka Sri Sai Nitin & I am pursuing my Final Year in B.Tech Computer Science and Engineering(CSE) with specialization in Cyber Security. My enthusiasm for Cyber Security, Machine Learning, and Web Development has motivated me to engage in a variety of projects, continuously expanding my expertise and honing my skills in these fields.

## About the Project - **Smart Home System Simulation**
**The repository provides the solutions for both the coding exercises developed in Java.**

### Project Overview

This project involves implementing a **Smart Home System simulation** that allows users to control various smart devices such as lights, thermostats, and door locks through a central hub. The system enables users to:

- Switch ON or OFF devices.
- Setting schedules for device operations.
- Automating Tasks based on Triggers (e.g., turning off lights when the thermostat reaches a certain temperature).

The solution utilizes Object-Oriented Programming (OOP) principles and incorporates the following design patterns:

- **Observer Pattern**: All devices are updated automatically whenever a change is made in the system.
- **Factory Method Pattern**: Used to create different type of smart devices (lights, thermostats, door locks).
- **Proxy Pattern**: Controls access to the devices, ensuring security and management through the central hub.

This ensures modularity, encapsulation, and a strong design architecture.

## Exercise 1: Design Patterns

### Behavioral Design Patterns

#### Use Case 1: Observer Pattern for Device Status Monitoring
- **Scenario**: When the main door is unlocked, all lights are automatically turned on. When the main door is locked, all lights are turned off.
- **Pattern**: Observer Pattern ensures that the status of all connected devices is updated based on changes in the system (e.g., door lock status).

#### Use Case 2: Strategy Pattern for Temperature Control
- **Scenario**: Different temperature control strategies can be applied based on user preferences or energy-saving goals.
- **Pattern**: Strategy Pattern enables switching between different temperature control modes, such as Eco mode or Comfort mode, based on the user’s choice.

### Creational Design Patterns

#### Use Case 3: Factory Pattern for Device Creation
- **Scenario**: A factory method is used to create various smart home devices (lights, thermostats, door locks) based on input parameters.
- **Pattern**: Factory Pattern ensures that a single point of creation is maintained for all devices. If a device type is unsupported, an error is generated.

#### Use Case 3 Alternative: Builder Pattern for Device Creation
- **Scenario**: Used as an alternative to the Factory Pattern to create complex smart home devices, offering flexibility in their construction.
- **Pattern**: Builder Pattern provides an alternate way of constructing devices, especially when Factory Pattern has already been implemented.

#### Use Case 4: Singleton Pattern for SmartHomeHub
- **Scenario**: The SmartHomeHub must maintain only one instance to control all connected devices.
- **Pattern**: Singleton Pattern ensures that only one instance of the SmartHomeHub exists throughout the system, centralizing control of the devices.

### Structural Design Patterns

#### Use Case 5: Adapter Pattern for Legacy Device Integration
- **Scenario**: Legacy devices that don’t follow the modern interface of new smart home devices need to be integrated.
- **Pattern**: Adapter Pattern allows the legacy devices to work seamlessly within the smart home ecosystem by adapting their interface.

#### Use Case 6: Composite Pattern for Grouping Devices
- **Scenario**: Multiple devices are grouped together (e.g., all lights in a room) so that operations can be performed on the group as a whole.
- **Pattern**: Composite Pattern facilitates the grouping of devices, enabling actions such as turning all lights on or off with a single operation.
