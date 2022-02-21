package com.parkit.parkingsystem.integration;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.controller.IncomingVehicleController;
import com.parkit.parkingsystem.controller.RegistrationNumberController;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;
import com.parkit.parkingsystem.integration.service.DataBasePrepareService;
import com.parkit.parkingsystem.service.ParkingService;
import com.parkit.parkingsystem.util.InputReaderUtil;

import junit.framework.Assert;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ParkingDataBaseIT {

	private static DataBaseTestConfig dataBaseTestConfig = new DataBaseTestConfig();
	private static DataBasePrepareService dataBasePrepareService = new DataBasePrepareService();

	@Mock
	private static InputReaderUtil inputReaderUtil;

	@BeforeEach
    private void setUpPerTest() throws Exception {
		ParkingSpotDAO.dataBaseConfig = dataBaseTestConfig;
		TicketDAO.dataBaseConfig = dataBaseTestConfig;
    	dataBasePrepareService.clearDataBaseEntries();
	}
	/**
	 * @author Muriel Proton 
	 * 		   in the new architecture parkingService.processIncomingVehicle(); has become
	 *         IncomingVehicleController.runRegistrationNumberController(ParkingType)
	 *         This class is not mend to be handled in test or otherwise, it is private.
	 *         It is handled by incomingVehicleController.selectVehicleType();
	 *         Therefore we begin the test N to N here
	 *         Integration test Test running through classes : 
	 *         + ParkingService 
	 *         + IncomingVehicleController 
	 *         + InputReaderUtil
	 *         + RegistrationNumberController
	 *         run with success the 27 01 2022 at 17h438
	 */
	@Test
	public void testParkingABike() {
		// Given
		dataBasePrepareService = new DataBasePrepareService();
		ParkingService parkingService = new ParkingService(new ParkingSpotDAO(), new TicketDAO());
		try (MockedStatic<RegistrationNumberController> regNumberController = Mockito
				.mockStatic(RegistrationNumberController.class)) {
			try (MockedStatic<InputReaderUtil> inputReaderUtil = Mockito
					.mockStatic(InputReaderUtil.class)) {
				//Preparation select Bike
				inputReaderUtil.when(InputReaderUtil::readInt).thenReturn(2);
				String vehicleRegNumber = "147852369";
				regNumberController.when(RegistrationNumberController::inputRegistrationNumber)
						.thenReturn(vehicleRegNumber);
				IncomingVehicleController incomingVehicleController = new IncomingVehicleController(parkingService);
				// When
				incomingVehicleController.runSelectVehicleType();
				// Then
				Assert.assertTrue(parkingService.isThereAlreadyThisVehicleInDB(vehicleRegNumber, ParkingType.BIKE));
			}
		}
	}	 
}