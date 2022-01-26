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

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class ParkingServiceTest {
/*
    private static ParkingService parkingService;

    @Mock
    private static InputReaderUtil inputReaderUtil;
    @Mock
    private static TicketDAO ticketDAO;
    @Mock
    private static ParkingSpotDAO parkingSpotDAO;



    @BeforeEach
    private void setUpPerTest() {
            parkingService = new ParkingService(parkingSpotDAO, ticketDAO);
    }

    @Test
    public void processExitingVehicleTest() throws Exception{
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR,false);
        Ticket ticket = new Ticket();
        Date today = new Date(System.currentTimeMillis() - (60*60*1000));
        ticket.setInTime(DateHelperUtil.convertDateToLocalDateTime(today));
        ticket.setParkingSpot(parkingSpot);
        ticket.setVehicleRegNumber("ABCDEF");
        when(ticketDAO.getTicket(anyString())).thenReturn(ticket);
        when(ticketDAO.updateTicket(any(Ticket.class))).thenReturn(true);
        when(parkingSpotDAO.updateParking(any(ParkingSpot.class))).thenReturn(true);
        when(inputReaderUtil.readVehicleRegistrationNumber()).thenReturn("ABCDEF");
        parkingService.processExitingVehicle();
        verify(parkingSpotDAO, Mockito.times(1)).updateParking(any(ParkingSpot.class));
    }

    @Test
    public void processExitingVehicleTest_whenUpdateTicketRuturnFalse() throws Exception{
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR,false);
        Ticket ticket = new Ticket();
        Date today = new Date(System.currentTimeMillis() - (60*60*1000));
        ticket.setInTime(DateHelperUtil.convertDateToLocalDateTime(today));
        ticket.setParkingSpot(parkingSpot);
        ticket.setVehicleRegNumber("ABCDEF");
        when(ticketDAO.getTicket(any())).thenReturn(ticket);
        when(ticketDAO.updateTicket(any(Ticket.class))).thenReturn(false);
        parkingService.processExitingVehicle();
        verify(parkingSpotDAO, Mockito.times(0)).updateParking(any(ParkingSpot.class));
    }
    @Test
    public void processExitingVehicleTest_WhenErrorThrow() throws Exception{
        RuntimeException myException = new RuntimeException("test");
        when(ticketDAO.getTicket(any())).thenThrow(myException);
        Exception exception = assertThrows(RuntimeException.class, () -> {
            parkingService.processExitingVehicle();
        });
        
        assertEquals("Unable to process exiting vehicle", exception.getMessage());
        assertEquals(myException, exception.getCause());
    }

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
