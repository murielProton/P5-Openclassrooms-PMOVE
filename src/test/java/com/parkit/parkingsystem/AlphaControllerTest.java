package com.parkit.parkingsystem;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.controller.AlphaController;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.integration.service.DataBasePrepareService;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.util.InputReaderUtil;
import com.parkit.parkingsystem.util.MathHelperUtil;

import junit.framework.Assert;

public class AlphaControllerTest {
	//config DB for tests
	private static DataBaseTestConfig dataBaseTestConfig = new DataBaseTestConfig();
	private static DataBasePrepareService dataBasePrepareService = new DataBasePrepareService();
	//Instances of objects needed for tests
	private static TicketDAO ticketDAO= new TicketDAO();
	private static ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, true);
	//Variable needed for tests
	private static String LoyalRegNumber ="123456789";
	private static String classicRegNumber ="555555555";
	private static LocalDateTime lasWeekInTime = LocalDateTime.now().minus(7, ChronoUnit.DAYS);
	private static LocalDateTime lasWeekOutTime = LocalDateTime.now().minus(7, ChronoUnit.DAYS).plusMinutes(60);

	@BeforeEach
    private void setUpPerTest() throws Exception {
    	ParkingSpotDAO.dataBaseConfig = dataBaseTestConfig;
		TicketDAO.dataBaseConfig = dataBaseTestConfig;
    	dataBasePrepareService.clearDataBaseEntries();
    }
	@AfterEach
    private void setAfterEachTest() throws Exception {
    	dataBasePrepareService.clearDataBaseEntries();
    }
	@Test
    public void  myTestAlphaControllerExistWithoutInteraction() {
    	try (MockedStatic<InputReaderUtil> mockedInput = Mockito.mockStatic(InputReaderUtil.class)) {
    		// GIVEN
    		mockedInput.when(InputReaderUtil::readInt)
    		.thenReturn(3);
    		// Calls wich will be made by this test
    		
    		//WHEN
    		AlphaController.loadInterface();
    		// THEN
    		Assert.assertEquals(4,new ParkingSpotDAO().getNextAvailableSlot(ParkingType.BIKE));
    		Assert.assertEquals(1,new ParkingSpotDAO().getNextAvailableSlot(ParkingType.CAR));
    	}
    }
	
	@Test
    public void  myTestAlphaControllerEntering() {
		String regNumberCar = "123456789";
    	try (MockedStatic<InputReaderUtil> mockedInput = Mockito.mockStatic(InputReaderUtil.class)) {
    		// GIVEN
    		// Calls wich will be made by this test
    		mockedInput.when(InputReaderUtil::readInt)
    		.thenReturn(1)// Enter
    		.thenReturn(1) // CAR
    		.thenReturn(3);// Exit app
			mockedInput.when(InputReaderUtil::readNextLine)
				.thenReturn(regNumberCar); // car for enter

    		
    		//WHEN
    		AlphaController.loadInterface();
    		// THEN
    		Assert.assertEquals(4,new ParkingSpotDAO().getNextAvailableSlot(ParkingType.BIKE));
    		Assert.assertEquals(2,new ParkingSpotDAO().getNextAvailableSlot(ParkingType.CAR));
    	}
    }
	
	@Test
    public void  myTestAlphaControllerEnteringAndExitingCar() {
		String regNumberCar = "123456789";
    	try (MockedStatic<InputReaderUtil> mockedInput = Mockito.mockStatic(InputReaderUtil.class)) {
    		// GIVEN
    		// Calls wich will be made by this test
    		mockedInput.when(InputReaderUtil::readInt)
    		.thenReturn(1)// Enter
    		.thenReturn(1) // CAR
    		.thenReturn(2) // Exit car
    		.thenReturn(3); // Exit app
			mockedInput.when(InputReaderUtil::readNextLine)
			.thenReturn(regNumberCar) // car for enter
			.thenReturn(regNumberCar);// car for exit
    		
    		//WHEN
    		AlphaController.loadInterface();
    		// THEN
    		Assert.assertEquals(4,new ParkingSpotDAO().getNextAvailableSlot(ParkingType.BIKE));
    		Assert.assertEquals(1,new ParkingSpotDAO().getNextAvailableSlot(ParkingType.CAR));
    	}
    }
	
	/*@Test
    public void  myTestAlphaControllerEnteringAndExitingBike() {
		String regNumberBike = "123456789";
    	try (MockedStatic<InputReaderUtil> mockedInput = Mockito.mockStatic(InputReaderUtil.class)) {
    		// GIVEN
    		// Les appels qui vons être fait par le test
    		mockedInput.when(InputReaderUtil::readInt)
    		.thenReturn(1) // Enter
    		.thenReturn(2) // BIKE
    		.thenReturn(2) // exit parking
    		.thenReturn(3); // Exit app
			mockedInput.when(InputReaderUtil::readNextLine)
			.thenReturn(regNumberBike) // bike for enter
			.thenReturn(regNumberBike); // bike for exit
    		
    		//WHEN
    		AlphaController.loadInterface();
    		// THEN
    		Assert.assertEquals(4,new ParkingSpotDAO().getNextAvailableSlot(ParkingType.BIKE));
    		Assert.assertEquals(1,new ParkingSpotDAO().getNextAvailableSlot(ParkingType.CAR));
    	}
    }*/
	@Test
	 public void testFareWithLoyalCustumers(){
		try (MockedStatic<InputReaderUtil> mockedInput = Mockito.mockStatic(InputReaderUtil.class)){
			//  inject in DB a Ticket with all informations
			// enters a car at two different times
			insertLoyalTicketInDB();
			insertPendingLoyalTicketInDB();
			mockedInput.when(InputReaderUtil::readInt)
    		.thenReturn(2)// EXIT 
			.thenReturn(3); // Exit app
			mockedInput.when(InputReaderUtil::readNextLine)
			.thenReturn(LoyalRegNumber); // car for enter
			// WHEN
			AlphaController.loadInterface();
			// THEN
			Double realPriceForLoyalCar= ticketDAO.getTicket(LoyalRegNumber).getPrice() ;
			Double expectedPriceForLoyalCar =  MathHelperUtil.roundingPrice(Fare.CAR_RATE_PER_HOUR * 0.95) ;
			Assert.assertEquals(expectedPriceForLoyalCar, realPriceForLoyalCar);
		}
	}
	
	@Test
	 public void testFareWithFirstTimeCustumer(){
		try (MockedStatic<InputReaderUtil> mockedInput = Mockito.mockStatic(InputReaderUtil.class)){
			//  inject in DB a Ticket with all informations
			// enters a car for the first time
			insertPendingTicketInDB();
			mockedInput.when(InputReaderUtil::readInt)
			.thenReturn(2)// EXIT 
			.thenReturn(3); // Exit app
			mockedInput.when(InputReaderUtil::readNextLine)
			.thenReturn(classicRegNumber); // car for enter
			// WHEN
			AlphaController.loadInterface();
			// THEN
			Double realPriceForCar= ticketDAO.getTicket(classicRegNumber).getPrice() ;
			Double expectedPriceForCar =  MathHelperUtil.roundingPrice(Fare.CAR_RATE_PER_HOUR) ;
			Assert.assertEquals(expectedPriceForCar, realPriceForCar);
		}
	}
	
	
/** *****The following Classes can only be called in this context of TESTTING***** */
	private void insertLoyalTicketInDB() {
		Ticket ticketToSave = new Ticket();
    	ticketToSave.setParkingSpot(parkingSpot);
        ticketToSave.setVehicleRegNumber(LoyalRegNumber);
        ticketToSave.setPrice(99999);
        ticketToSave.setInTime(lasWeekInTime);
        ticketToSave.setOutTime(lasWeekOutTime);
        ticketDAO.saveTicket(ticketToSave);
    }
	private void insertPendingLoyalTicketInDB() {
		Ticket ticketToSave = new Ticket();
    	ticketToSave.setParkingSpot(parkingSpot);
        ticketToSave.setVehicleRegNumber(LoyalRegNumber);
        ticketToSave.setPrice(0);
        ticketToSave.setInTime(LocalDateTime.now().minus(1, ChronoUnit.HOURS));
        ticketDAO.saveTicket(ticketToSave);
    }
	private void insertPendingTicketInDB() {
		Ticket ticketToSave = new Ticket();
    	ticketToSave.setParkingSpot(parkingSpot);
        ticketToSave.setVehicleRegNumber(classicRegNumber);
        ticketToSave.setPrice(0);
        ticketToSave.setInTime(LocalDateTime.now().minus(1, ChronoUnit.HOURS));
        ticketToSave.setOutTime(null);
        ticketDAO.saveTicket(ticketToSave);
    }

}
