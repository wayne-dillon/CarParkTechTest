package com.waynedillon;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Objects;

@Data
@AllArgsConstructor
public class Vehicle {
    private String registration;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vehicle vehicle = (Vehicle) o;
        return Objects.equals(registration, vehicle.registration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(registration);
    }
}
