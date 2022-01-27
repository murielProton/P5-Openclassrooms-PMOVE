package com.parkit.parkingsystem;


import com.parkit.parkingsystem.integration.service.DataBasePrepareService;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.constants.DBConstants;
//import com.parkit.parkingsystem.service.ParkingService;
import com.parkit.parkingsystem.constants.ParkingType;
//import com.parkit.parkingsystem.dao.ParkingSpotDAO;
//import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;

import org.junit.jupiter.api.*;
//import org.mockito.Mock;
//import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.verify;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TicketDAOTest {
	private static DataBaseTestConfig dataBaseTestConfig = new DataBaseTestConfig();
	private static TicketDAO ticketDAO= new TicketDAO();
	private static DataBasePrepareService dataBasePrepareService = new DataBasePrepareService();
	
	/*@Mock
	private static TicketDAO mockOfTicketDAO= new TicketDAO();*/
	@BeforeEach
    private void setUpPerTest() throws Exception {
    	dataBasePrepareService.clearDataBaseEntries();
    	ticketDAO.dataBaseConfig = dataBaseTestConfig;
    }
	
    @Test
    public void saveTicketTest() {
    	LocalDateTime inTime = LocalDateTime.now();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, true);
        Ticket ticketToSave = new Ticket();
        ticketToSave.setParkingSpot(parkingSpot);
        ticketToSave.setVehicleRegNumber("123456789");
        ticketToSave.setPrice(0);
        ticketToSave.setInTime(inTime);
        ticketToSave.setOutTime(null);
    	boolean wasTheTicketRealySaved = ticketDAO.saveTicket(ticketToSave);
    	assertEquals(true, wasTheTicketRealySaved);
    }
    @Test
    public void getTicketTest() { 
    	Ticket ticketToSave = new Ticket();
    	Ticket realTicket = new Ticket();
    	LocalDateTime inTime = LocalDateTime.now();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, true);
        ticketToSave.setParkingSpot(parkingSpot);
        ticketToSave.setVehicleRegNumber("123456789");
        ticketToSave.setPrice(0);
        ticketToSave.setInTime(inTime);
        ticketToSave.setOutTime(null);
        ticketDAO.saveTicket(ticketToSave);
        realTicket = ticketDAO.getTicket("123456789");
        assertEquals(parkingSpot, realTicket.getParkingSpot());
    	assertEquals("123456789", realTicket.getVehicleRegNumber());
    	assertEquals(0, realTicket.getPrice());
    }
    @Test
    public void getTicketOfExitingVehiculTest() { 
    	Ticket ticketToSave = new Ticket();
    	Ticket ticketToSave1 = new Ticket();
    	Ticket realTicket = new Ticket();
    	LocalDateTime inTime = LocalDateTime.now();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, true);
        ParkingSpot parkingSpot1 = new ParkingSpot(2, ParkingType.CAR, true);
        ticketToSave.setParkingSpot(parkingSpot);
        ticketToSave.setVehicleRegNumber("123456789");
        ticketToSave.setPrice(0);
        ticketToSave.setInTime(inTime);
        ticketToSave.setOutTime(null);
        ticketToSave1.setParkingSpot(parkingSpot1);
        ticketToSave1.setVehicleRegNumber("987456321");
        ticketToSave1.setPrice(0);
        ticketToSave1.setInTime(inTime);
        ticketToSave1.setOutTime(null);
        ticketDAO.saveTicket(ticketToSave);
        ticketDAO.saveTicket(ticketToSave1);
        realTicket = ticketDAO.getTicketOfExitingVehicul("123456789");
        assertEquals(parkingSpot, realTicket.getParkingSpot());
    	assertEquals("123456789", realTicket.getVehicleRegNumber());
    	assertEquals(0, realTicket.getPrice());
    }
    public void resultSetToTicket(){
    	/*Difficult to test because I need to mock a Type ResultSet
    	 * Not usefull to be tested as it was tested in previous test
    	String vehicleRegNumber= "123456789";
	    LocalDateTime inTime = LocalDateTime.now();
	    ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, true);
	    Ticket ticketToSave = new Ticket();
	    ticketToSave.setParkingSpot(parkingSpot);
	    ticketToSave.setVehicleRegNumber("123456789");
	    ticketToSave.setPrice(0);
	    ticketToSave.setInTime(inTime);
	    ticketToSave.setOutTime(null);
	    ticketDAO.saveTicket(ticketToSave);
	    ticketDAO.resultSetToTicket("123456789", ResultSet ticketFromDatabase)
	    assertEquals(ticketToSave, realTicket);
    */}
}
