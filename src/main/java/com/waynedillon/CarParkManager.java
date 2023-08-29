package com.waynedillon;

import com.waynedillon.exceptions.NoAvailableSpacesException;
import com.waynedillon.exceptions.VehicleAlreadyParkedException;
import com.waynedillon.exceptions.VehicleNotFoundException;
import com.waynedillon.utils.DateTimeUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;

public class CarParkManager {
    private static final Logger logger = LogManager.getLogger(CarParkManager.class);

    private final CarParkService carPark;

    public CarParkManager(CarParkService carPark) {
        this.carPark = carPark;
    }

    public String addVehicle(String registration) {
        logger.info("Attempting to add vehicle with registration: " + registration);
        Vehicle toAdd = new Vehicle(registration);
        LocalDateTime arrivalTime = DateTimeUtils.now();
        try {
            carPark.addVehicle(toAdd, arrivalTime);
        } catch (VehicleAlreadyParkedException | NoAvailableSpacesException e) {
            logger.error(e.getMessage());
            return e.getMessage();
        }

        String ret = "Vehicle registration: " + registration + " parked at: " + arrivalTime;
        logger.info(ret);
        return ret;
    }

    public double removeVehicle(String registration) {
        logger.info("Attempting to remove vehicle with registration: " + registration);
        Vehicle toRemove = new Vehicle(registration);
        LocalDateTime arrivalTime = null;
        try {
            arrivalTime = carPark.getArrivalTime(toRemove);
            carPark.removeVehicle(toRemove);
        } catch (VehicleNotFoundException e) {
            logger.error(e.getMessage());
            return 0.0;
        }

        LocalDateTime exitTime = DateTimeUtils.now();
        int hoursStayed = DateTimeUtils.getHoursBetween(arrivalTime, exitTime);
        double fee = hoursStayed * BasicCarPark.getPrice();

        logger.info("Vehicle: " + registration + " charged £" + fee);
        return fee;
    }
}
