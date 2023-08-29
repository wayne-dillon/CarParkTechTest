package com.waynedillon;

import com.waynedillon.exceptions.NoAvailableSpacesException;
import com.waynedillon.exceptions.VehicleAlreadyParkedException;
import com.waynedillon.exceptions.VehicleNotFoundException;

import java.time.LocalDateTime;

public interface CarParkService {
    int getAvailableSpaces();

    void addVehicle(Vehicle vehicle, LocalDateTime arrivalTime) throws NoAvailableSpacesException, VehicleAlreadyParkedException;

    boolean hasVehicle(Vehicle vehicle);

    LocalDateTime getArrivalTime(Vehicle vehicle) throws VehicleNotFoundException;

    void removeVehicle(Vehicle vehicle) throws VehicleNotFoundException;
}
