package com.parkit.parkingsystem;

import com.parkit.parkingsystem.util.InputReaderUtil;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class InputReaderUtilTest {
    @Test
    public void checkVehicleRegistrationNumberThrowableTest() throws Exception {
        assertThrows(IllegalArgumentException.class, () -> {
            InputReaderUtil.checkVehicleRegistrationNumber(null);
        });
        String registrationNumberLengthZero = "";
        assertThrows(IllegalArgumentException.class, () -> {
            InputReaderUtil.checkVehicleRegistrationNumber(registrationNumberLengthZero);
        });
        String registrationNumberLength20 = "AAa-00c0-ArA2571-edrt";
        assertThrows(IllegalArgumentException.class, () -> {
            InputReaderUtil.checkVehicleRegistrationNumber(registrationNumberLength20);
        });
        String validregistrationPlate = "AA-000-AA";
        String plateToTest = InputReaderUtil.checkVehicleRegistrationNumber(validregistrationPlate);
        assertEquals("AA-000-AA", plateToTest);

    }
}
