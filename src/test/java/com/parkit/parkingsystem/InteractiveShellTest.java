package com.parkit.parkingsystem;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.parkit.parkingsystem.service.InteractiveShell;
import com.parkit.parkingsystem.service.ParkingService;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class InteractiveShellTest {
    
    @Test
    public void chooseTypeIncomingVehicle(){
        ParkingService parkingService = Mockito.mock(ParkingService.class);
        InteractiveShell myShell = new InteractiveShell();
        myShell.parkingService = parkingService;
        // If I press 1 doese the methode returns True
        assertTrue(myShell.processOption(1));
        // Mockito will instanciate a false instance of the methode I chose. Verify that this methode is instanciated at least once
        Mockito.verify(parkingService, Mockito.times(1)).processIncomingVehicle();
        // Mockito will instanciate a false instance of the methode I chose. Verify that this methode is NOT instanciated
        Mockito.verify(parkingService, Mockito.times(0)).processExitingVehicle();
    }
    @Test
    public void chooseTypeOfExitingVehicle(){
        ParkingService parkingService = Mockito.mock(ParkingService.class);
        InteractiveShell myShell = new InteractiveShell();
        myShell.parkingService = parkingService;
        // If I press 2 doese the methode returns True
        assertTrue(myShell.processOption(2));
        // Mockito will instanciate a false instance of the methode I chose. Verify that this methode is NOT instanciated
        Mockito.verify(parkingService, Mockito.times(0)).processIncomingVehicle();
        // Mockito will instanciate a false instance of the methode I chose. Verify that this methode is instanciated at least once
        Mockito.verify(parkingService, Mockito.times(1)).processExitingVehicle();
    }

    @Test
    public void chooseTypeOfExitingFromSystem(){
        ParkingService parkingService = Mockito.mock(ParkingService.class);
        InteractiveShell myShell = new InteractiveShell();
        myShell.parkingService = parkingService;
        // If I press 3 doese the methode returns False
        assertFalse(myShell.processOption(3));
        // Mockito will instanciate a false instance of the methode I chose. Verify that this methode is NOT instanciated
        Mockito.verify(parkingService, Mockito.times(0)).processIncomingVehicle();
        // Mockito will instanciate a false instance of the methode I chose. Verify that this methode is NOT instanciated
        Mockito.verify(parkingService, Mockito.times(0)).processExitingVehicle();
    }

    @Test
    public void processUnvalidOptions(){
        ParkingService parkingService = Mockito.mock(ParkingService.class);
        InteractiveShell myShell = new InteractiveShell();
        myShell.parkingService = parkingService;
        // If I press 4 doese the methode returns Default
        assertTrue(myShell.processOption(4));
        // verify if an ecception is thrown
        Mockito.doThrow(new RuntimeException("Oups")).when(parkingService).processExitingVehicle();
        // Mockito will instanciate a false instance of the methode I chose. Verify that this methode is NOT instanciated
        Mockito.verify(parkingService, Mockito.times(0)).processIncomingVehicle();
        // Mockito will instanciate a false instance of the methode I chose. Verify that this methode is NOT instanciated
        Mockito.verify(parkingService, Mockito.times(0)).processExitingVehicle();
    }

}
