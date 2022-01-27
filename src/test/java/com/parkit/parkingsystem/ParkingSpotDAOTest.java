package com.parkit.parkingsystem;



import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.integration.service.DataBasePrepareService;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;

public class ParkingSpotDAOTest {
	private static DataBaseTestConfig dataBaseTestConfig = new DataBaseTestConfig();
	private static ParkingSpotDAO parkingSpotDAO= new ParkingSpotDAO();
	private static DataBasePrepareService dataBasePrepareService = new DataBasePrepareService();

    @BeforeEach
    private void setUpPerTest() throws Exception {
    	dataBasePrepareService.clearDataBaseEntries();
    	parkingSpotDAO.dataBaseConfig = dataBaseTestConfig;
    }
    /**
	 * @author Muriel Proton
	 * Test in class ParkingSpotDAO method getNextAvailableSlot(ParkingType)
	 * run with success the 27 01 2022 at 15h14
	 */
    @Test
    public void getNextAvailableSlotTest() {
    	int realParkingSpotNumber = parkingSpotDAO.getNextAvailableSlot(ParkingType.CAR);
    	assertEquals(1, realParkingSpotNumber);
    }
    /**
	 * @author Muriel Proton
	 * Test in class ParkingSpotDAO method updateParking(ParkingSpot)
	 * run with success the 27 01 2022 at 15h14
	 */
    @Test
    public void updateParkingTest() {
    	ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, true);
    	parkingSpot.setAvailable(false);
    	boolean realParkingSpot = parkingSpotDAO.updateParking(parkingSpot);
    	assertEquals(true, realParkingSpot);
    }
    /**
	 * @author Muriel Proton
	 * Test in class ParkingSpotDAO method getTotalNumberOfAvailableSlot()
	 * run with success the 27 01 2022 at 15h14
	 */
    @Test
    public void getTotalNumberOfAvailableSlotTest() {
    	int realNumberOfAvailableParkingSpot = parkingSpotDAO.getTotalNumberOfAvailableSlot();
    	assertEquals(5, realNumberOfAvailableParkingSpot);
    }
    /**
	 * @author Muriel Proton
	 * Test in class ParkingSpotDAO method getNumberOfAvailableSlotForType(ParkingType)
	 * run with success the 27 01 2022 at 15h14
	 */
    @Test
    public void getNumberOfAvailableSlotForTypeTest() {
    	int realNumberOfAvailableParkingSpot = parkingSpotDAO.getNumberOfAvailableSlotForType(ParkingType.CAR);
    	assertEquals(3, realNumberOfAvailableParkingSpot);
    }
}
