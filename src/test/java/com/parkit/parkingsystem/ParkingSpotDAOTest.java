package com.parkit.parkingsystem;



import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.Mockito.*;
//import org.mockito.Mock;

//import com.parkit.parkingsystem.config.DataBaseConfig;
import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.integration.service.DataBasePrepareService;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;

public class ParkingSpotDAOTest {
	private static DataBaseTestConfig dataBaseTestConfig = new DataBaseTestConfig();
	private static ParkingSpotDAO parkingSpotDAO= new ParkingSpotDAO();
	private static DataBasePrepareService dataBasePrepareService = new DataBasePrepareService();
	 //private static ParkingSpotDAO parkingSpotDAO;
	 //private static TicketDAO ticketDAO;
	 //private static DataBasePrepareService dataBasePrepareService;

	//@Mock
    //private static ParkingType parkingType;

    //@BeforeAll
    /*private static void setUp() throws Exception {
    	
    }*/

    @BeforeEach
    private void setUpPerTest() throws Exception {
        /*when(inputReaderUtil.readSelection()).thenReturn(1);
        when(inputReaderUtil.readVehicleRegistrationNumber()).thenReturn(LICENSE_PLATE);
        dataBasePrepareService.clearDataBaseEntries();*/
    	dataBasePrepareService.clearDataBaseEntries();
    	parkingSpotDAO.dataBaseConfig = dataBaseTestConfig;
    }
    @Test
    public void getNextAvailableSlotTest() {
    	int realParkingSpotNumber = parkingSpotDAO.getNextAvailableSlot(ParkingType.CAR);
    	assertEquals(1, realParkingSpotNumber);
    }
    @Test
    public void updateParkingTest() {
    	ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, true);
    	parkingSpot.setAvailable(false);
    	boolean realParkingSpot = parkingSpotDAO.updateParking(parkingSpot);
    	assertEquals(true, realParkingSpot);
    }
    @Test
    public void getTotalNumberOfAvailableSlotTest() {
    	int realNumberOfAvailableParkingSpot = parkingSpotDAO.getTotalNumberOfAvailableSlot();
    	assertEquals(5, realNumberOfAvailableParkingSpot);
    }
    @Test
    public void getNumberOfAvailableSlotForTypeTest() {
    	int realNumberOfAvailableParkingSpot = parkingSpotDAO.getNumberOfAvailableSlotForType(ParkingType.CAR);
    	assertEquals(3, realNumberOfAvailableParkingSpot);
    }
}
