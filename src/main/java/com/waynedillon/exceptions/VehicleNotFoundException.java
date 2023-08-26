package com.waynedillon.exceptions;

public class VehicleNotFoundException extends Throwable {
    public VehicleNotFoundException() {
        super("The requested vehicle is not parked here");
    }
}
