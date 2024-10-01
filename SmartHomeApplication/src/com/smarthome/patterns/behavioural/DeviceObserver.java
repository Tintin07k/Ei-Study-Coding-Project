package com.smarthome.patterns.behavioural;

import com.smarthome.exceptions.UnsupportedActionException;

/**
 * The {@code DeviceObserver} interface represents an observer for smart home devices.
 */

public interface DeviceObserver {
    void update(String message) throws UnsupportedActionException;
}
