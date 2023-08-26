package com.waynedillon;

import com.waynedillon.exceptions.NoAvailableSpacesException;
import com.waynedillon.exceptions.VehicleAlreadyParkedException;
import com.waynedillon.exceptions.VehicleNotFoundException;
import com.waynedillon.utils.DateTimeUtils;
import lombok.Getter;

import java.time.LocalDateTime;

public class CarParkManager {
    @Getter
    private final String carParkFullMsg = "Vehicle not parked as there are no spaces available";
    @Getter
    private final String carAlreadyParkedMsg = "Vehicle has already been parked";

    public String addVehicle(String registration) {
        Vehicle toAdd = new Vehicle(registration);
        LocalDateTime arrivalTime = DateTimeUtils.now();
        try {
            CarPark.addVehicle(toAdd, arrivalTime);
        } catch (VehicleAlreadyParkedException | NoAvailableSpacesException e) {
            return e.getMessage();
        }
        return "Vehicle registration: " + registration + " parked at: " + arrivalTime;
    }

    public double removeVehicle(String registration) {
        Vehicle toRemove = new Vehicle(registration);
        LocalDateTime arrivalTime = null;
        try {
            arrivalTime = CarPark.getArrivalTime(toRemove);
            CarPark.removeVehicle(toRemove);
        } catch (VehicleNotFoundException e) {
            return 0.0;
        }

        LocalDateTime exitTime = DateTimeUtils.now();
        int hoursStayed = DateTimeUtils.getHoursBetween(arrivalTime, exitTime);
        return hoursStayed * CarPark.getPrice();
    }
}
