package com.parkit.parkingsystem.service;

import java.time.Duration;
import java.time.LocalDateTime;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.util.DateHelperUtil;

/**
 * @author Muriel Proton
 *
 */
public class FareCalculatorService {
    /**
     * Used in Class FareCalculatorService by Method : calculateFare(Ticket)
     * @param Ticket
     * @return DURATION
     */
    public Duration getDurationOfTicket(Ticket ticket){
        if( (ticket.getOutTime() == null) || (ticket.getOutTime().isBefore(ticket.getInTime())) ){
            throw new IllegalArgumentException("Out time provided is incorrect: "+ticket.getOutTime());
        }

        LocalDateTime inHour = ticket.getInTime();
        LocalDateTime outHour = ticket.getOutTime();

        Duration lengthOfTimeDuringWhenCarWasParked = DateHelperUtil.findLengthOfTimeBetweenTwoLocalDateTimes(inHour, outHour);
        return lengthOfTimeDuringWhenCarWasParked;
    }
    /**
     * Used in Class ExitingVehicleController by Method : getExitingVehiculePriceFare(Ticket)
     * @param Ticket
     * @return DOUBLE
     */
    public double calculateFare(Ticket ticket){
        Duration lengthOfTimeDuringWhenCarWasParked = getDurationOfTicket(ticket);
        double hoursOfParkedTime = DateHelperUtil.transformDurationIntoDouble(lengthOfTimeDuringWhenCarWasParked);
        switch (ticket.getParkingSpot().getParkingType()){
            case CAR: {
                ticket.setPrice(hoursOfParkedTime * Fare.CAR_RATE_PER_HOUR);
                double carParkingFee = hoursOfParkedTime * Fare.CAR_RATE_PER_HOUR;
                return carParkingFee;
            }
            case BIKE: {
                ticket.setPrice(hoursOfParkedTime * Fare.BIKE_RATE_PER_HOUR);
                double bikeParkingFee = hoursOfParkedTime * Fare.CAR_RATE_PER_HOUR;
                return bikeParkingFee;
            }
            default: throw new IllegalArgumentException("Unkown Parking Type");
        }
    }

}