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
        InteractiveShell mySheel = new InteractiveShell();
        mySheel.parkingService = parkingService;
        // Je vérifie que j'ai bien true en résultat de méthode
        assertTrue(mySheel.processOption(1));
        // J'utilise Mockito pour lui faire vérifier les interaction avec le Mock(fausse instance) sur la méthode que je veux.
        Mockito.verify(parkingService, Mockito.times(1)).processIncomingVehicle();
        Mockito.verify(parkingService, Mockito.times(0)).processExitingVehicle();
    }
    @Test
    public void chooseTypeOfExitingVehicle(){
        ParkingService parkingService = Mockito.mock(ParkingService.class);
        InteractiveShell mySheel = new InteractiveShell();
        mySheel.parkingService = parkingService;
        // Je vérifie que j'ai bien true en résultat de méthode
        assertTrue(mySheel.processOption(2));
        // J'utilise Mockito pour lui faire vérifier les interaction avec le Mock(fausse instance) sur la méthode que je veux.
        Mockito.verify(parkingService, Mockito.times(0)).processIncomingVehicle();
        Mockito.verify(parkingService, Mockito.times(1)).processExitingVehicle();
    }

    @Test
    public void chooseTypeOfExitingVehicleWhenFail(){
        ParkingService parkingService = Mockito.mock(ParkingService.class);
        InteractiveShell mySheel = new InteractiveShell();
        mySheel.parkingService = parkingService;
        Mockito.doThrow(new RuntimeException("Oups")).when(parkingService).processExitingVehicle(Mockito.eq);
        // Je vérifie que j'ai bien true en résultat de méthode
        assertTrue(mySheel.processOption(2));
        // J'utilise Mockito pour lui faire vérifier les interaction avec le Mock(fausse instance) sur la méthode que je veux.
        Mockito.verify(parkingService, Mockito.times(0)).processIncomingVehicle();
        Mockito.verify(parkingService, Mockito.times(1)).processExitingVehicle();
    }
}
