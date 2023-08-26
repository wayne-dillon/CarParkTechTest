package com.waynedillon;

import com.waynedillon.utils.DateTimeUtils;
import lombok.Getter;

import java.time.LocalDateTime;

public class CarParkManager {
    @Getter
    private final String carParkFullMsg = "Vehicle not parked as there are no spaces available";
    @Getter
    private final String carAlreadyParkedMsg = "Vehicle has already been parked";

    public String addVehicle(String registration) {
        if (CarPark.getAvailableSpaces() <= 0) {
            return carParkFullMsg;
        }
        Vehicle toAdd = new Vehicle(registration);
        if (CarPark.getSpaces().containsKey(toAdd)) {
            return carAlreadyParkedMsg;
        }

        LocalDateTime arrivalTime = LocalDateTime.now();
        CarPark.getSpaces().put(toAdd, arrivalTime);
        return "Vehicle registration: " + registration + " parked at: " + arrivalTime;
    }

    public double removeVehicle(String registration) {
        Vehicle toRemove = new Vehicle(registration);
        LocalDateTime arrivalTime = CarPark.getSpaces().get(new Vehicle(registration));
        if (arrivalTime == null) {
            return 0.0;
        }
        CarPark.getSpaces().remove(toRemove);

        LocalDateTime exitTime = LocalDateTime.now();
        int hoursStayed = DateTimeUtils.getHoursBetween(arrivalTime, exitTime);
        return hoursStayed * CarPark.getPrice();
    }
}
