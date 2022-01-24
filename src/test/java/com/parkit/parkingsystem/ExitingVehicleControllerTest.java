package com.parkit.parkingsystem;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.controller.ExitingVehicleController;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.ParkingService;
import com.parkit.parkingsystem.util.DateHelperUtil;

import junit.framework.Assert;

public class ExitingVehicleControllerTest {
	@Mock
	ParkingSpotDAO myParkingSpotDAOTest = new ParkingSpotDAO();
	TicketDAO myTicketDAOTest = new TicketDAO();
	ParkingService myParkingServiceTest = new ParkingService(myParkingSpotDAOTest, myTicketDAOTest);
	ExitingVehicleController myExitingVehicleControllerTest = new ExitingVehicleController(myParkingServiceTest);
	/* as it is a case of a function calling to an other i suppose this is also a case of integration testing, not unit testint
	 * I do not know how to do it.
	@Test
	public void getRegistrationNumberReturnsRegNumberTest(){
		myExitingVehicleControllerTest.getRegistrationNumber();
	}*/
	@Test
	public void getRegistrationNumberReturnNullTest(){
		String myTest = myExitingVehicleControllerTest.getRegistrationNumber();
		Assert.assertEquals(null, myTest);
	}
    @BeforeEach
    private void setUpPerTest() {
        ParkingSpot myParkingSpotTest = new ParkingSpot(1, ParkingType.CAR,false);
    	String vehicleRegNumber = "1234567890";
    	LocalDateTime inTime = LocalDateTime.now();
        //LocalDateTime.now().plusMinutes(MUST HAVE A NUMBER ABOVE 30)
        LocalDateTime outTime =  LocalDateTime.now().plusMinutes(90);
        Ticket myTicketTest = new Ticket();
        myTicketTest.setInTime(inTime);
        myTicketTest.setVehicleRegNumber(vehicleRegNumber);
        myTicketTest.setParkingSpot(myParkingSpotTest);
    }

    @Test
    public void getTicketOfExitingVehicleAndSetOutTimeTest() {
    	Ticket myTicketToTest = myExitingVehicleControllerTest.getTicketOfExitingVehicleAndSetOutTime("1234567890");
    }
    @Test
    public void processExitingVehicleTest() throws Exception{
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR,false);
        Ticket ticket = new Ticket();
        Date today = new Date(System.currentTimeMillis() - (60*60*1000));
        ticket.setInTime(LocalDateTime.now());
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

}
