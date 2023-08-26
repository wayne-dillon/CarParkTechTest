package com.waynedillon;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class CarPark {
    // Using static map to simulate database backing
    @Getter
    private static final Map<Vehicle, LocalDateTime> spaces = new HashMap<>();
    private static final int capacity = 100;
    @Getter
    private static final double price = 2.0;

    public static int getAvailableSpaces() {
        return capacity - spaces.size();
    }
}
