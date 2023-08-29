package com.waynedillon;

import com.waynedillon.exceptions.NoAvailableSpacesException;
import com.waynedillon.exceptions.VehicleAlreadyParkedException;
import com.waynedillon.exceptions.VehicleNotFoundException;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BasicCarPark implements CarParkService {
    // Using static map to simulate database backing
    private static final Map<Vehicle, LocalDateTime> spaces = new ConcurrentHashMap<>();
    private static final int capacity = 100;
    @Getter
    private static final double price = 2.0;

    public int getAvailableSpaces() {
        return capacity - spaces.size();
    }

    public void addVehicle(Vehicle vehicle, LocalDateTime arrivalTime) throws NoAvailableSpacesException, VehicleAlreadyParkedException {
        if (getAvailableSpaces() <= 0) {
            throw new NoAvailableSpacesException();
        }
        if (hasVehicle(vehicle)) {
            throw new VehicleAlreadyParkedException();
        }

        spaces.put(vehicle, arrivalTime);
    }

    public boolean hasVehicle(Vehicle vehicle) {
        return spaces.containsKey(vehicle);
    }

    public LocalDateTime getArrivalTime(Vehicle vehicle) throws VehicleNotFoundException {
        if (!hasVehicle(vehicle)) {
            throw new VehicleNotFoundException();
        }

        return spaces.get(vehicle);
    }

    public void removeVehicle(Vehicle vehicle) throws VehicleNotFoundException {
        if (!hasVehicle(vehicle)) {
            throw new VehicleNotFoundException();
        }

        spaces.remove(vehicle);
    }

    public void empty() {
        spaces.clear();
    }
}
