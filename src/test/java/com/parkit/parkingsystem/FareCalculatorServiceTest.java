package com.parkit.parkingsystem;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime; 

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.FareCalculatorService;
import com.parkit.parkingsystem.util.DateHelperUtil;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
//OK to ALL le 18 01 2022 à 14h05
public class FareCalculatorServiceTest {

    private static FareCalculatorService fareCalculatorService;
    private Ticket ticket;

    @BeforeAll
    private static void setUp() {
        fareCalculatorService = new FareCalculatorService();
    }

    @BeforeEach
    private void setUpPerTest() {
        ticket = new Ticket();
    }
    /**
     * @author Muriel Proton
     * Test in class FareCalculatorService the method calculateFare(Ticket)
     * run with success the 18 01 2022 at 13h58
     */
    @Test
    public void calculateFareCarTest(){
        LocalDateTime inTime = LocalDateTime.now();
        //LocalDateTime.now().plusMinutes(MUST HAVE A NUMBER ABOVE 30)
        LocalDateTime outTime =  LocalDateTime.now().plusMinutes(90);
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR,false);
        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        fareCalculatorService.calculateFare(ticket);
        Duration durationTypeLengthOfTimeDuringWhenCarWasParked = DateHelperUtil.findLengthOfTimeBetweenTwoLocalDateTimes(inTime, outTime);
        double doubleTypehoursOfParkedTime = DateHelperUtil.transformDurationIntoDouble(durationTypeLengthOfTimeDuringWhenCarWasParked);
        double expectedCarFarePerHour = ticket.getPrice()/doubleTypehoursOfParkedTime;
        assertEquals(expectedCarFarePerHour, Fare.CAR_RATE_PER_HOUR);
        assertEquals(2.25, ticket.getPrice());
    }
    /**
     * @author Muriel Proton
     * Test in class FareCalculatorService the method calculateFare(Ticket)
     * run with success the 18 01 2022 at 14h00
     */
    @Test
    public void calculateFareBikeTest(){
        LocalDateTime inTime = LocalDateTime.now();
        //LocalDateTime.now().plusMinutes(MUST HAVE A NUMBER ABOVE 30)
        LocalDateTime outTime =  LocalDateTime.now().plusMinutes(90);
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE,false);
        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        fareCalculatorService.calculateFare(ticket);
        Duration durationTypeLengthOfTimeDuringWhenCarWasParked = DateHelperUtil.findLengthOfTimeBetweenTwoLocalDateTimes(inTime, outTime);
        double doubleTypehoursOfParkedTime = DateHelperUtil.transformDurationIntoDouble(durationTypeLengthOfTimeDuringWhenCarWasParked);
        double expectedBikeFarePerHour = ticket.getPrice()/doubleTypehoursOfParkedTime;
        assertEquals(expectedBikeFarePerHour, Fare.BIKE_RATE_PER_HOUR);
        assertEquals(1.5, ticket.getPrice());
    }
    /**
     * @author Muriel Proton
     * Test in class FareCalculatorService the method calculateFare(Ticket)
     * run with success the 18 01 2022 at 14h01
     */
    @Test
    public void findLengthOfTimeBetweenTwoLocalDateTimesTest(){
        LocalDateTime inTime = LocalDateTime.now();
        LocalDateTime outTime =  LocalDateTime.now().plusMinutes(30);
        Duration durationExpected = Duration.between(inTime, outTime);
        Duration durationTypeLengthOfTimeDuringWhenCarWasParked = DateHelperUtil.findLengthOfTimeBetweenTwoLocalDateTimes(inTime, outTime);
        assertEquals(durationExpected, durationTypeLengthOfTimeDuringWhenCarWasParked);
    }
    /**
     * @author Muriel Proton
     * Test in class FareCalculatorService the method calculateFare(Ticket)
     * run with success the 18 01 2022 at 14h02
     */
    @Test
    public void transformDurationIntoDoubleTest(){
        LocalDateTime inTime = LocalDateTime.now();
        LocalDateTime outTime =  LocalDateTime.now().plusMinutes(30);
        Duration durationTypeLengthOfTimeDuringWhenCarWasParked = DateHelperUtil.findLengthOfTimeBetweenTwoLocalDateTimes(inTime, outTime);
        double doubleTypehoursOfParkedTime = DateHelperUtil.transformDurationIntoDouble(durationTypeLengthOfTimeDuringWhenCarWasParked);
        assertEquals(0.5, doubleTypehoursOfParkedTime);
    }
    /**
     * @author Muriel Proton
     * Test in class FareCalculatorService the method calculateFare(Ticket)
     * run with success the 18 01 2022 at 14h02
     */
    @Test
    public void calculateFareUnkownTypeTest(){
        ParkingSpot parkingSpot = new ParkingSpot(1, null,false);
        LocalDateTime inTime = LocalDateTime.now();
        //LocalDateTime.now().plusMinutes(MUST HAVE A NUMBER ABOVE 30)
        LocalDateTime outTime =  LocalDateTime.now().plusMinutes(90);
        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        assertThrows(NullPointerException.class, () -> fareCalculatorService.calculateFare(ticket));
    }
    /**
     * @author Muriel Proton
     * Test in class FareCalculatorService the method calculateFare(Ticket)
     * run with success the 18 01 2022 at 14h03
     */
    @Test
    public void calculateFareBikeWithFutureInTimeTest(){
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE,false);
        LocalDateTime inTime = LocalDateTime.now();
        //Carefull this is a start date after end date
        LocalDateTime outTime =  LocalDateTime.now().minusMinutes(90);
        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        assertThrows(IllegalArgumentException.class, () -> fareCalculatorService.calculateFare(ticket));
    }
    /**
     * @author Muriel Proton
     * Test in class FareCalculatorService the method calculateFare(Ticket)
     * run with success the 18 01 2022 at 14h03
     */
    @Test
    public void calculateFareBikeWithLessThanOneHourParkingTimeTest(){
        LocalDateTime inTime = LocalDateTime.now();
        //LocalDateTime.now().plusMinutes(MUST HAVE A NUMBER ABOVE 30 and less than 60)
        LocalDateTime outTime =  LocalDateTime.now().plusMinutes(45);
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE,false);
        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        fareCalculatorService.calculateFare(ticket);
        assertEquals((0.75 * Fare.BIKE_RATE_PER_HOUR), ticket.getPrice() );
    }
    /**
     * @author Muriel Proton
     * Test in class FareCalculatorService the method calculateFare(Ticket)
     * run with success the 18 01 2022 at 14h04
     */
    @Test
    public void calculateFareCarWithLessThanOneHourParkingTimeTest(){
        LocalDateTime inTime = LocalDateTime.now();
        //LocalDateTime.now().plusMinutes(MUST HAVE A NUMBER ABOVE 30 and less than 60)
        LocalDateTime outTime =  LocalDateTime.now().plusMinutes(45);
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR,false);
        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        fareCalculatorService.calculateFare(ticket);
        assertEquals( (0.75 * Fare.CAR_RATE_PER_HOUR) , ticket.getPrice());
    }
    /**
     * @author Muriel Proton
     * Test in class FareCalculatorService the method calculateFare(Ticket)
     * run with success the 18 01 2022 at 14h04
     */
    @Test
    public void calculateFareCarWithMoreThanADayParkingTimeTest(){
        LocalDateTime inTime = LocalDateTime.now();
        //LocalDateTime.now().plusHours(MUST HAVE 24)
        LocalDateTime outTime =  LocalDateTime.now().plusHours(24);
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR,false);

        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        fareCalculatorService.calculateFare(ticket);
        assertEquals( (24 * Fare.CAR_RATE_PER_HOUR) , ticket.getPrice());
    }
    /**
     * @author Muriel Proton
     * Test in class FareCalculatorService the method getDurationOfTicket(Ticket)
     * run with success the 18 01 2022 at 14h26
     */
    @Test
    public void getDurationOfTicketTest() {
    	ParkingSpot parkingSpotForTest = new ParkingSpot(1, ParkingType.CAR,false);
    	ticket.setId(1);
    	ticket.setInTime(LocalDateTime.now().minusMinutes(40));
    	ticket.setOutTime(LocalDateTime.now());
    	ticket.setParkingSpot(parkingSpotForTest);
    	ticket.setVehicleRegNumber("123456789");
    	//WHEN
    	Duration realDuration = fareCalculatorService.getDurationOfTicket(ticket);
    	//THEN
    	Duration expectedDuration = Duration.ofMinutes(40);
    	assertEquals( expectedDuration, realDuration);
    }

}
