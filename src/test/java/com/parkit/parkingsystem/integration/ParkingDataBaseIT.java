package com.parkit.parkingsystem.integration;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.controller.AlphaController;
import com.parkit.parkingsystem.controller.IncomingVehicleController;
import com.parkit.parkingsystem.controller.RegistrationNumberController;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;
import com.parkit.parkingsystem.integration.service.DataBasePrepareService;
import com.parkit.parkingsystem.service.ParkingService;
import com.parkit.parkingsystem.util.InputReaderUtil;

import junit.framework.Assert;

//import org.junit.jupiter.api.AfterAll;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ParkingDataBaseIT {

    private static DataBaseTestConfig dataBaseTestConfig = new DataBaseTestConfig();
    private static ParkingSpotDAO parkingSpotDAO;
    private static TicketDAO ticketDAO;
    private static DataBasePrepareService dataBasePrepareService;

    @Mock
    private static InputReaderUtil inputReaderUtil;

   /* @BeforeAll
    private static void setUp() throws Exception{
        parkingSpotDAO = new ParkingSpotDAO();
        parkingSpotDAO.dataBaseConfig = dataBaseTestConfig;
        ticketDAO = new TicketDAO();
        ticketDAO.dataBaseConfig = dataBaseTestConfig;
        dataBasePrepareService = new DataBasePrepareService();
    }

    @BeforeEach
    private void setUpPerTest() throws Exception {
        when(InputReaderUtil.readInt()).thenReturn(2);
        when(InputReaderUtil.readNextLine()).thenReturn("123456789");
        dataBasePrepareService.clearDataBaseEntries();
    }

    @AfterAll
    private static void tearDown(){

    }*/
    /**
     * in the new architecture parkingService.processIncomingVehicle(); has become 
     * IncomingVehicleController.runRegistrationNumberController(ParkingType currentType)
    */
    @Test
    public void testParkingACar(){
    	parkingSpotDAO = new ParkingSpotDAO();
        parkingSpotDAO.dataBaseConfig = dataBaseTestConfig;
        ticketDAO = new TicketDAO();
        ticketDAO.dataBaseConfig = dataBaseTestConfig;
        dataBasePrepareService = new DataBasePrepareService();
        ParkingService parkingService = new ParkingService(parkingSpotDAO, ticketDAO);
        try (MockedStatic<RegistrationNumberController> regNumberController = Mockito.mockStatic(RegistrationNumberController.class)) {
        	String vehicleRegNumber = "123456789";
			regNumberController.when(RegistrationNumberController::inputRegistrationNumber).thenReturn(vehicleRegNumber);
        	 IncomingVehicleController incomingVehicleController = new IncomingVehicleController(parkingService);
             incomingVehicleController.runRegistrationNumberController(ParkingType.BIKE);
             incomingVehicleController.setNeedStopVehicleType(false);
             Assert.assertTrue(parkingService.isThereAlreadyThisVehicleInDB(vehicleRegNumber, ParkingType.BIKE));
        }
    }
    
    
    /**
     * in the new architecture parkingService.processExitingVehicle(); has become 
     * ExitingVehicleController.processExitingVehicle()
    *//*
    @Test
    public void testParkingLotExit(){
        testParkingACar();
        ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
        
        //TODO: check that the fare generated and out time are populated correctly in the database
    }
*/
}