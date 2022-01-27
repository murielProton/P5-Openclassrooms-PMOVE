package com.parkit.parkingsystem;


import com.parkit.parkingsystem.integration.service.DataBasePrepareService;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.TicketDAO;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
	/**
	 * @author Muriel Proton
	 * Test in class TicketDAO method saveTicket(Ticket)
	 * run with success the 27 01 2022 at 15h00
	 */
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
    /**
	 * @author Muriel Proton
	 * Test in class TicketDAO method getTicket(STRING)
	 * run with success the 27 01 2022 at 15h00
	 */
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
    /**
	 * @author Muriel Proton
	 * Test in class TicketDAO method getTicketOfExitingVehicul(STRING)
	 * and method resultSetToTicket(STRING, RESULTSET)
	 * run with success the 27 01 2022 at 15h00
	 */
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
    /**
	 * @author Muriel Proton
	 * Test in class TicketDAO method getTicketIfVehiculeAlreadyInside(STRING, ParkingType)
	 * run with success the 27 01 2022 at 15h00
	 */
    @Test
    public void getTicketIfVehiculeAlreadyInsideTest(){
    	LocalDateTime inTime = LocalDateTime.now();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, true);
        Ticket ticketToSave = new Ticket();
        ticketToSave.setParkingSpot(parkingSpot);
        ticketToSave.setVehicleRegNumber("123456789");
        ticketToSave.setPrice(0);
        ticketToSave.setInTime(inTime);
        ticketToSave.setOutTime(null);
        ticketDAO.saveTicket(ticketToSave);
    	int oneTicketInDB = ticketDAO.getTicketIfVehiculeAlreadyInside("123456789", ParkingType.CAR);
    	int zeroTicketInDB = ticketDAO.getTicketIfVehiculeAlreadyInside("987654321", ParkingType.BIKE);
    	//getInt(int columnIndex) Retrieves the value of the designated column in the current row of this ResultSet object as an int in the Java programming language.
    	assertEquals(3, oneTicketInDB);
    	assertEquals(0, zeroTicketInDB);
    }
    /**
	 * @author Muriel Proton
	 * Test in class TicketDAO method updateOutTimeOfCurrentTicket(Ticket)
	 * run with success the 27 01 2022 at 15h00
	 */
    @Test
    public void updateOutTimeOfCurrentTicketTest() {
    	LocalDateTime inTime = LocalDateTime.now();
    	LocalDateTime outTime = inTime.plusMinutes(40);
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, true);
        Ticket ticketToSave = new Ticket();
        ticketToSave.setParkingSpot(parkingSpot);
        ticketToSave.setVehicleRegNumber("123456789");
        ticketToSave.setPrice(0);
        ticketToSave.setInTime(inTime);
        ticketToSave.setOutTime(null);
        ticketDAO.saveTicket(ticketToSave);
        ticketToSave.setOutTime(outTime);
    	boolean wasTheTicketRealyUpdated = ticketDAO.updateOutTimeOfCurrentTicket(ticketToSave);
    	assertEquals(true, wasTheTicketRealyUpdated);
    }
    /**
	 * @author Muriel Proton
	 * Test in class TicketDAO method updatePriceOfCurrentTicket(Ticket, DOUBLE)
	 * run with success the 27 01 2022 at 15h00
	 */
    @Test
    public void updatePriceOfCurrentTicketTest() {
    	LocalDateTime inTime = LocalDateTime.now();
    	LocalDateTime outTime = inTime.plusMinutes(40);
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, true);
        Ticket ticketToSave = new Ticket();
        ticketToSave.setParkingSpot(parkingSpot);
        ticketToSave.setVehicleRegNumber("123456789");
        ticketToSave.setPrice(0);
        ticketToSave.setInTime(inTime);
        ticketToSave.setOutTime(outTime);
        ticketDAO.saveTicket(ticketToSave);
    	Double price = 10.4;
    	ticketToSave.setPrice(price);
    	boolean wasTheTicketRealyUpdated = ticketDAO.updatePriceOfCurrentTicket(ticketToSave, price);
    	assertEquals(true, wasTheTicketRealyUpdated);
    }
}
