package com.parkit.parkingsystem;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.ParkingService;
import com.parkit.parkingsystem.util.DateHelperUtil;
import com.parkit.parkingsystem.util.InputReaderUtil;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Date;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;


/**
 * @author Muriel Proton
 *
 */
@ExtendWith(MockitoExtension.class)
public class ParkingServiceTest {

    private static ParkingService parkingService;

    @Mock
    private static InputReaderUtil inputReaderUtil;
    @Mock
    private static TicketDAO ticketDAO;
    @Mock
    private static ParkingSpotDAO parkingSpotDAO;
    @Mock
    private static ParkingType parkingType;



    @BeforeEach
    private void setUpPerTest() {
            parkingService = new ParkingService(parkingSpotDAO, ticketDAO);
    }

    /**
     * This is a unit test
     * There for we will only test if the right method is called upon, not if it returns the right value.
     * @author Muriel Proton
     */
    @Test
    public void getNextParkingNumberIfAvailableForCarTest() {
    	ParkingService mokedParkingService = mock(ParkingService.class);
    	ParkingSpotDAO mokedParkingSpotDAO = mock(ParkingSpotDAO.class);
    	//WHEN
    	mokedParkingService.getNextParkingNumberIfAvailable(parkingType.CAR);
    	//THEN -> verify that parkingSpotDAO.getNextAvailableSlot(parkingType); was called upon
    	verify(mokedParkingService, atLeast(1)).mokedParkingSpotDAO.getNextAvailableSlot(parkingType.CAR);
    	((mokedParkingService) Mockito.verify(mokedParkingService)).getNextAvailableSlot(parkingType.CAR);
    	
    }/*
    @Test
    public void getVehichleTypeCarTest(){
        // If I press 1 doese the methode returns ParkingType CAR
        assertEquals(ParkingType.CAR, parkingService.getVehichleType(1));
    }

    @Test
    public void getVehichleTypeBikeTest(){
        // If I press 1 doese the methode returns ParkingType CAR
        assertEquals(ParkingType.BIKE, parkingService.getVehichleType(2));
    }
    @Test
    public void getVehichleTypeUnknownTest(){
        // If I press 1 doese the methode returns ParkingType CAR
        assertEquals(null, parkingService.getVehichleType(3));
    }*/
}
