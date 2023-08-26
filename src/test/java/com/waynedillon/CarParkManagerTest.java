package com.waynedillon;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

public class CarParkManagerTest {
    CarParkManager manager;

    @BeforeEach
    private void setUp() {
        manager = new CarParkManager();
        CarPark.getSpaces().clear();
    }

    @Test
    public void testCarParkCapacity() {
        Assertions.assertEquals(100, CarPark.getAvailableSpaces());
    }

    @Test
    public void testCantAddToFullCarPark() {
        fillCarPark(0);
        Assertions.assertEquals(manager.getCarParkFullMsg(), manager.addVehicle("ABC123"));
    }

    @Test
    public void testAddToLastAvailableSpace() {
        fillCarPark(1);
        Assertions.assertEquals(1, CarPark.getAvailableSpaces());

        String reg1 = "ABC123";
        String confirmationMsg = "Vehicle registration: " + reg1 + " parked at";
        Assertions.assertTrue(manager.addVehicle(reg1).startsWith(confirmationMsg));
        Assertions.assertEquals(0, CarPark.getAvailableSpaces());
    }

    @Test
    public void testAdd2ToLastAvailableSpace() {
        fillCarPark(1);
        Assertions.assertEquals(1, CarPark.getAvailableSpaces());

        String reg1 = "ABC123";
        String reg2 = "ABC456";
        String confirmationMsg = "Vehicle registration: " + reg1 + " parked at";
        Assertions.assertTrue(manager.addVehicle(reg1).startsWith(confirmationMsg));
        Assertions.assertEquals(manager.getCarParkFullMsg(), manager.addVehicle(reg2));
        Assertions.assertEquals(0, CarPark.getAvailableSpaces());
    }

    @Test
    public void testAddToEmptyCarPark() {
        String reg1 = "ABC123";
        String confirmationMsg = "Vehicle registration: " + reg1 + " parked at";
        Assertions.assertTrue(manager.addVehicle(reg1).startsWith(confirmationMsg));
        Assertions.assertEquals(99, CarPark.getAvailableSpaces());
    }

    @Test
    public void testRemoveJustAdded() throws InterruptedException {
        String reg1 = "ABC123";
        manager.addVehicle(reg1);
        // wait to avoid issues caused by quick processing
        TimeUnit.SECONDS.sleep(1);
        Assertions.assertEquals(2.0, manager.removeVehicle(reg1));
        Assertions.assertEquals(100, CarPark.getAvailableSpaces());
    }

    @Test
    public void testRemoveAfter2Hours() {
        String reg1 = "ABC123";
        // add a few minutes to arrival time to avoid total going over two hours
        CarPark.getSpaces().put(new Vehicle(reg1), LocalDateTime.now().minusHours(2).plusMinutes(2));
        Assertions.assertEquals(4.0, manager.removeVehicle(reg1));
        Assertions.assertEquals(100, CarPark.getAvailableSpaces());
    }

    @Test
    public void testAddDuplicate() {
        String reg1 = "ABC123";
        String confirmationMsg = "Vehicle registration: " + reg1 + " parked at";
        Assertions.assertTrue(manager.addVehicle(reg1).startsWith(confirmationMsg));
        Assertions.assertEquals(99, CarPark.getAvailableSpaces());
        Assertions.assertEquals(manager.getCarAlreadyParkedMsg(), manager.addVehicle(reg1));
        Assertions.assertEquals(99, CarPark.getAvailableSpaces());
    }

    private void fillCarPark(int spacesRemaining) {
        for (int i = 0; CarPark.getAvailableSpaces() > spacesRemaining; i++) {
            CarPark.getSpaces().put(new Vehicle("ABC" + i), LocalDateTime.now());
        }
    }
}
