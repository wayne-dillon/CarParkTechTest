package com.waynedillon.exceptions;

public class VehicleAlreadyParkedException extends Throwable {
    public VehicleAlreadyParkedException() {
        super("This vehicle is already parked");
    }
}
