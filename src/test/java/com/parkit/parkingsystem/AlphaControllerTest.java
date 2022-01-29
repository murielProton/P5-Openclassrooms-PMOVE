package com.parkit.parkingsystem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.controller.AlphaController;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.integration.service.DataBasePrepareService;
import com.parkit.parkingsystem.util.InputReaderUtil;

import junit.framework.Assert;

public class AlphaControllerTest {
	
	private static DataBaseTestConfig dataBaseTestConfig = new DataBaseTestConfig();
	private static DataBasePrepareService dataBasePrepareService = new DataBasePrepareService();

	@BeforeEach
    private void setUpPerTest() throws Exception {
    	ParkingSpotDAO.dataBaseConfig = dataBaseTestConfig;
		TicketDAO.dataBaseConfig = dataBaseTestConfig;
    	dataBasePrepareService.clearDataBaseEntries();
    }
	@Test
    public void  myTestAlphaControllerExistWithoutInteraction() {
    	try (MockedStatic<InputReaderUtil> mockedInput = Mockito.mockStatic(InputReaderUtil.class)) {
    		// GIVEN
    		mockedInput.when(InputReaderUtil::readInt)
    		.thenReturn(3);
    		// Les appels qui vons être fait par le test
    		
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
    		// Les appels qui vons être fait par le test
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
    		// Les appels qui vons être fait par le test
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
	
	@Test
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
    }
}
