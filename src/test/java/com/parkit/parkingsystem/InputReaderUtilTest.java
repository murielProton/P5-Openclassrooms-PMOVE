package com.parkit.parkingsystem;
import com.parkit.parkingsystem.util.InputReaderUtil;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

public class InputReaderUtilTest {
    @Test
    public void checkVehicleRegistrationNumberThrowableTest(){
        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                InputReaderUtil.checkVehicleRegistrationNumber(null);
                String registrationNumberLengthZero ="";
                InputReaderUtil.checkVehicleRegistrationNumber(registrationNumberLengthZero);
                String registrationNumberLength20 ="AAa-00c0-ArA2571-edrt";
                InputReaderUtil.checkVehicleRegistrationNumber(registrationNumberLength20);
                String validregistrationPlate = "AA-000-AA";
                String plateToTest = InputReaderUtil.checkVehicleRegistrationNumber(validregistrationPlate);
                assertEquals("AA-000-AA", plateToTest);
            }
        });
    }
}
