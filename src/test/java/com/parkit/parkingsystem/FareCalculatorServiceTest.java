package com.parkit.parkingsystem;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Date;
import java.time.LocalDateTime; 
import java.time.format.DateTimeFormatter;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.FareCalculatorService;

import org.hibernate.type.LocalDateTimeType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;

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

    @Test
    public void calculateFareCar(){
        LocalDateTime inTime = LocalDateTime.now();
        //LocalDateTime.now().plusMinutes(MUST HAVE A NUMBER ABOVE 30)
        LocalDateTime outTime =  LocalDateTime.now().plusMinutes(90);
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR,false);
        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        fareCalculatorService.calculateFare(ticket);
        Duration durationTypeLengthOfTimeDuringWhenCarWasParked = FareCalculatorService.findLengthOfTimeBetweenTwoLocalDateTimes(inTime, outTime);
        double doubleTypehoursOfParkedTime = FareCalculatorService.transformDurationIntoDouble(durationTypeLengthOfTimeDuringWhenCarWasParked);
        double expectedCarFarePerHour = ticket.getPrice()/doubleTypehoursOfParkedTime;
        assertEquals(expectedCarFarePerHour, Fare.CAR_RATE_PER_HOUR);
        assertEquals(2.25, ticket.getPrice());
    }

    @Test
    public void calculateFareBike(){
        LocalDateTime inTime = LocalDateTime.now();
        //LocalDateTime.now().plusMinutes(MUST HAVE A NUMBER ABOVE 30)
        LocalDateTime outTime =  LocalDateTime.now().plusMinutes(90);
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE,false);
        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        fareCalculatorService.calculateFare(ticket);
        Duration durationTypeLengthOfTimeDuringWhenCarWasParked = FareCalculatorService.findLengthOfTimeBetweenTwoLocalDateTimes(inTime, outTime);
        double doubleTypehoursOfParkedTime = FareCalculatorService.transformDurationIntoDouble(durationTypeLengthOfTimeDuringWhenCarWasParked);
        double expectedBikeFarePerHour = ticket.getPrice()/doubleTypehoursOfParkedTime;
        assertEquals(expectedBikeFarePerHour, Fare.BIKE_RATE_PER_HOUR);
        assertEquals(1.5, ticket.getPrice());
    }

    @Test
    public void findLengthOfTimeBetweenTwoLocalDateTimesTEST(){
        LocalDateTime inTime = LocalDateTime.now();
        LocalDateTime outTime =  LocalDateTime.now().plusMinutes(30);
        Duration durationExpected = Duration.between(inTime, outTime);
        Duration durationTypeLengthOfTimeDuringWhenCarWasParked = FareCalculatorService.findLengthOfTimeBetweenTwoLocalDateTimes(inTime, outTime);
        assertEquals(durationExpected, durationTypeLengthOfTimeDuringWhenCarWasParked);
    }

    @Test
    public void transformDurationIntoDoubleTEST(){
        LocalDateTime inTime = LocalDateTime.now();
        LocalDateTime outTime =  LocalDateTime.now().plusMinutes(30);
        Duration durationTypeLengthOfTimeDuringWhenCarWasParked = FareCalculatorService.findLengthOfTimeBetweenTwoLocalDateTimes(inTime, outTime);
        double doubleTypehoursOfParkedTime = FareCalculatorService.transformDurationIntoDouble(durationTypeLengthOfTimeDuringWhenCarWasParked);
        assertEquals(0.5, doubleTypehoursOfParkedTime);
    }

    @Test
    public void calculateFareUnkownType(){
        Date inTime = new Date();
        inTime.setTime( System.currentTimeMillis() - (  60 * 60 * 1000) );
        Date outTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, null,false);

        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        assertThrows(NullPointerException.class, () -> fareCalculatorService.calculateFare(ticket));
    }

    @Test
    public void calculateFareBikeWithFutureInTime(){
        Date inTime = new Date();
        inTime.setTime( System.currentTimeMillis() + (  60 * 60 * 1000) );
        Date outTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE,false);

        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        assertThrows(IllegalArgumentException.class, () -> fareCalculatorService.calculateFare(ticket));
    }

    @Test
    public void calculateFareBikeWithLessThanOneHourParkingTime(){
        Date inTime = new Date();
        inTime.setTime( System.currentTimeMillis() - (  45 * 60 * 1000) );//45 minutes parking time should give 3/4th parking fare
        Date outTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE,false);

        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        fareCalculatorService.calculateFare(ticket);
        assertEquals((0.75 * Fare.BIKE_RATE_PER_HOUR), ticket.getPrice() );
    }

    @Test
    public void calculateFareCarWithLessThanOneHourParkingTime(){
        Date inTime = new Date();
        inTime.setTime( System.currentTimeMillis() - (  45 * 60 * 1000) );//45 minutes parking time should give 3/4th parking fare
        Date outTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR,false);

        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        fareCalculatorService.calculateFare(ticket);
        assertEquals( (0.75 * Fare.CAR_RATE_PER_HOUR) , ticket.getPrice());
    }

    @Test
    public void calculateFareCarWithMoreThanADayParkingTime(){
        Date inTime = new Date();
        inTime.setTime( System.currentTimeMillis() - (  24 * 60 * 60 * 1000) );//24 hours parking time should give 24 * parking fare per hour
        Date outTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR,false);

        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        fareCalculatorService.calculateFare(ticket);
        assertEquals( (24 * Fare.CAR_RATE_PER_HOUR) , ticket.getPrice());
    }

}
