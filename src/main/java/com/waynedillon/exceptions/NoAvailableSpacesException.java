package com.waynedillon.exceptions;

public class NoAvailableSpacesException extends Throwable {
    public NoAvailableSpacesException() {
        super("Car park is full");
    }
}
