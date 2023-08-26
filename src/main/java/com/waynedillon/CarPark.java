package com.waynedillon;

import com.waynedillon.exceptions.NoAvailableSpacesException;
import com.waynedillon.exceptions.VehicleAlreadyParkedException;
import com.waynedillon.exceptions.VehicleNotFoundException;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class CarPark {
    // Using static map to simulate database backing
    private static final Map<Vehicle, LocalDateTime> spaces = new HashMap<>();
    private static final int capacity = 100;
    @Getter
    private static final double price = 2.0;

    public static int getAvailableSpaces() {
        return capacity - spaces.size();
    }

    public static void addVehicle(Vehicle vehicle, LocalDateTime arrivalTime) throws NoAvailableSpacesException, VehicleAlreadyParkedException {
        if (getAvailableSpaces() <= 0) {
            throw new NoAvailableSpacesException();
        }
        if (hasVehicle(vehicle)) {
            throw new VehicleAlreadyParkedException();
        }

        spaces.put(vehicle, arrivalTime);
    }

    public static boolean hasVehicle(Vehicle vehicle) {
        return spaces.containsKey(vehicle);
    }

    public static LocalDateTime getArrivalTime(Vehicle vehicle) throws VehicleNotFoundException {
        if (!hasVehicle(vehicle)) {
            throw new VehicleNotFoundException();
        }

        return spaces.get(vehicle);
    }

    public static void removeVehicle(Vehicle vehicle) throws VehicleNotFoundException {
        if (!hasVehicle(vehicle)) {
            throw new VehicleNotFoundException();
        }

        spaces.remove(vehicle);
    }

    public static void empty() {
        spaces.clear();
    }
}
