package com.parkit.parkingsystem.integration;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.controller.ExitingVehicleController;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;
import com.parkit.parkingsystem.integration.service.DataBasePrepareService;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.FareCalculatorService;
import com.parkit.parkingsystem.service.ParkingService;
import com.parkit.parkingsystem.util.InputReaderUtil;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Connection;

import junit.framework.Assert;

import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

@ExtendWith(MockitoExtension.class)
public class ParkingDataBaseIT {

    private static DataBaseTestConfig dataBaseTestConfig = new DataBaseTestConfig();
    private static ParkingSpotDAO parkingSpotDAO;
    private static TicketDAO ticketDAO;
    private static DataBasePrepareService dataBasePrepareService;

    @Mock
    private static InputReaderUtil inputReaderUtil;

    @BeforeAll
    private static void setUp() throws Exception{
        parkingSpotDAO = new ParkingSpotDAO();
        parkingSpotDAO.dataBaseConfig = dataBaseTestConfig;
        ticketDAO = new TicketDAO();
        ticketDAO.dataBaseConfig = dataBaseTestConfig;
        dataBasePrepareService = new DataBasePrepareService();
       Connection connectionToTestDB = Mockito.mock(Connection.class);
        Mockito.when(ticketDAO.dataBaseConfig.getConnection()).thenReturn(dataBaseTestConfig.getConnection());
        Mockito.when(parkingSpotDAO.dataBaseConfig.getConnection()).thenReturn(dataBaseTestConfig.getConnection());
    }
    @BeforeEach
    private void setUpPerTest(){
        
        dataBasePrepareService.clearDataBaseEntries();
    }

    @AfterAll
    private static void tearDown(){

    }

    @Test
    public void test_getNextAvailableSlot(){
        // accès sans modification
        Assert.assertEquals(1,  parkingSpotDAO.getNextAvailableSlot(ParkingType.CAR));
        Assert.assertEquals(4,  parkingSpotDAO.getNextAvailableSlot(ParkingType.BIKE));
    }
    @Test
    public void test_getNextAvailableSlot_whenExceptionThrown() {
        /*
         * if all slots are unavailable for cars as for bikes exception is thrown.
         * parkingSpotDAO.getNextAvailableSlot(ParkingType.CAR);
         * parkingSpotDAO.getNextAvailableSlot(ParkingType.BIKE);
    	Exception exception = assertThrows(NumberFormatException.class, () -> {
            Integer.parseInt("1a");
        });

        String expectedMessage = "Error fetching next available slot";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        */
    }

    @Test
    public void test_updateParking_CAR(){
        // GIVEN / BEFORE
        Assert.assertEquals(1,  parkingSpotDAO.getNextAvailableSlot(ParkingType.CAR));
        Assert.assertEquals(4,  parkingSpotDAO.getNextAvailableSlot(ParkingType.BIKE));
        ParkingSpot spot = new ParkingSpot(1, ParkingType.CAR, false);
        // WHEN
        Assert.assertTrue(parkingSpotDAO.updateParking(spot));
        //THEN
        Assert.assertEquals(2,  parkingSpotDAO.getNextAvailableSlot(ParkingType.CAR));
        Assert.assertEquals(4,  parkingSpotDAO.getNextAvailableSlot(ParkingType.BIKE));
    }

    
    @Test
    public void test_updateParking_BIKE(){
        // GIVEN / BEFORE
        Assert.assertEquals(1,  parkingSpotDAO.getNextAvailableSlot(ParkingType.CAR));
        Assert.assertEquals(4,  parkingSpotDAO.getNextAvailableSlot(ParkingType.BIKE));
        ParkingSpot spot = new ParkingSpot(4, ParkingType.BIKE, false);
        // WHEN
        Assert.assertTrue(parkingSpotDAO.updateParking(spot));
        //THEN
        Assert.assertEquals(1,  parkingSpotDAO.getNextAvailableSlot(ParkingType.CAR));
        Assert.assertEquals(5,  parkingSpotDAO.getNextAvailableSlot(ParkingType.BIKE));
    }
   
    @Test
    public void test_ParkingACar(){
        TicketDAO ticketDAOToTest = new TicketDAO();
        ParkingService parkingService = new ParkingService(parkingSpotDAO, ticketDAOToTest);
        ParkingSpot parkingSpotTest = new ParkingSpot(1, ParkingType.CAR, false);
        String vehicleRegNumber = "123456789";
		parkingService.saveIncomingVehicleInDB(parkingSpotTest, vehicleRegNumber);
        Assert.assertEquals(ParkingType.CAR, parkingSpotTest.getParkingType());
        Assert.assertEquals(false, parkingSpotTest.isAvailable());
        Assert.assertEquals(1, parkingSpotTest.getId());
        Assert.assertEquals(vehicleRegNumber,  TicketDAO.getTicket(vehicleRegNumber).getVehicleRegNumber());
    }
    @Test
    public void test_ParkingLotExit(){
    	//GIVEN
        ParkingService parkingService = Mockito.mock(ParkingService.class);
        FareCalculatorService fareCalculatorService = new FareCalculatorService();
        String vehicleRegNumber = "123456789";
        //Initialize Ticket to fake save with : Id, ParkingSpot, Registration Number, InTime
        Ticket ticket = new Ticket();
        ticket.setId(15);
        ParkingSpot parkingSpotForTest = new ParkingSpot(1, ParkingType.CAR, false);
        ticket.setParkingSpot(parkingSpotForTest);
        ticket.setVehicleRegNumber(vehicleRegNumber);
     // calculate price of the fare for 40 minutes with a car 
        //40 minutes so that when I update the code so that the first 30 minutes are free nothing will change for this test
        ticket.setInTime(LocalDateTime.now().minusMinutes(40));
        Double price = fareCalculatorService.calculateFare(ticket);;
        boolean isPay = true;
        //Exit with vehicle registration number 123456789
        
        ExitingVehicleController controller = Mockito.spy(new ExitingVehicleController(parkingService));
        Mockito.when(controller.getRegistrationNumber()).thenReturn(vehicleRegNumber);
        Mockito.when(controller.getTicketOfExitingVehicleAndSetOutTime(vehicleRegNumber)).thenReturn(ticket);
        Mockito.when(controller.getExitingVehiculePriceFare(ticket)).thenReturn(price);
        Mockito.when(controller.payementTerminal(price)).thenReturn(isPay);
        //Mockito.when(controller.openGateForExitingVehicle(isPay, ticket)).thenReturn(isPay);
        Mockito.when(controller.getParkingService()).thenReturn(parkingService);
//        Mockito.doCallRealMethod().when(controller).processExitingVehicle();
        //WHEN
        controller.processExitingVehicle();  
        //THEN
        //Define now the nearer to calling controller.processExitingVehicle();
        LocalDateTime outTimeNow = LocalDateTime.now();  
        //TO TEST
        //TODO: check that the fare generated and out time are populated correctly in the database
        Ticket ticketFromDB = TicketDAO.getTicket(vehicleRegNumber);
        Assert.assertEquals(price, ticketFromDB.getPrice());
        Assert.assertEquals(outTimeNow, ticketFromDB.getOutTime());
    }
}

