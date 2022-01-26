package com.parkit.parkingsystem;


import com.parkit.parkingsystem.integration.service.DataBasePrepareService;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
//import com.parkit.parkingsystem.service.ParkingService;
import com.parkit.parkingsystem.constants.ParkingType;
//import com.parkit.parkingsystem.dao.ParkingSpotDAO;
//import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;

import org.junit.jupiter.api.*;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;

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
    	//when(mockOfTicketDAO.getTicket(anyString())).thenReturn(ticketToSave);
    	boolean wasTheTicketRealySaved = ticketDAO.saveTicket(ticketToSave);
    	assertEquals(true, wasTheTicketRealySaved);
    }
}
