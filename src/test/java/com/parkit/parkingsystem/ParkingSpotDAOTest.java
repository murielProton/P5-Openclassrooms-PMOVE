package com.parkit.parkingsystem;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.controller.AlphaController;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;


/**
 * Warning, here should be unit test.
 * Initialy these test were thought in such a manner that they would test the DB.
 * So initialy thoese tests were integration tests.
 * you will find them in test/com.parkit.parkingsystem/integration/ParingDataBaseIT.java
 * TODO unit testing of ParkingSpotDAO
 * @author Muriel Proton
 *
 */
public class ParkingSpotDAOTest {
	@BeforeEach
	public void setUp() {
	    //Set ParkingNumber = 1 to 5
		//Set Available to True = 1
		//Set Type of Parking Number 1 to 3 to CAR
		//Set Type of Parking Number 3 to 4 to BIKE
	}
	@Test
	public void getNextAvailableSlot1TestForCars(){
		ParkingSpotDAO spyParkingSpotDAO = Mockito.spy(ParkingSpotDAO.class);
		
		ParkingType parkingTypeToTest = spyParkingSpotDAO.setParkingType(CAR);
		int actualParkingNumber = spyParkingSpotDAO.getNextAvailableSlot(parkingTypeToTest);
		assertEquals(1, actualParkingNumber);
	}
	@Test
	public void getNextAvailableSlot4TestForBikes(){
		int actualParkingNumber=0;
		assertEquals(4, actualParkingNumber);
	}
	@Test
	public void getNextAvailableSlotThrowsExceptionTest(){
		//Set Available to False = 0
		ParkingSpotDAO spyParkingSpotDAO = Mockito.spy(ParkingSpotDAO.class);
		ParkingSpot(int number, ParkingType parkingType, boolean isAvailable)
		spyParkingSpotDAO.set
	}
}
