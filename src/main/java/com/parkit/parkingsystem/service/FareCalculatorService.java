package com.parkit.parkingsystem.service;

import java.time.Duration;
import java.time.LocalDateTime;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.util.DateHelperUtil;
import com.parkit.parkingsystem.util.MathHelperUtil;

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
        if( ticket.getOutTime() == null || ticket.getInTime().isAfter(ticket.getOutTime()) ){
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
        double hoursOfParkedTime = ifWasLessThanThirtyMinutesGetItFree(lengthOfTimeDuringWhenCarWasParked);
        switch (ticket.getParkingSpot().getParkingType()){
            case CAR: {
                
                double carParkingFee = MathHelperUtil.roundingPrice(
                		hoursOfParkedTime* Fare.CAR_RATE_PER_HOUR);
                ticket.setPrice(carParkingFee);
                return carParkingFee;
            }
            case BIKE: {
                double bikeParkingFee = MathHelperUtil.roundingPrice(
                		hoursOfParkedTime * Fare.BIKE_RATE_PER_HOUR);
                ticket.setPrice(bikeParkingFee);
                return bikeParkingFee;
            }
            default: throw new IllegalArgumentException("Unkown Parking Type");
        }
    }
    /**
     * Used in Class ExitingVehicleController by Method : ifWasLessThanThirtyMinutesGetItFree(DURATION)
     * @param DURATION
     * @return BOOLEAN
     */
    public boolean wasItThirtyMinutes(Duration lengthOfTimeDuringWhenCarWasParked) {
    	Duration thirtyMinutes = Duration.ofMinutes(30);
    	/**
    	 * Duration < thirtyMinutes = Duration.compareTo(thirtyMinutes) = -1
    	 * Duration == thirtyMinutes = Duration.compareTo(thirtyMinutes) = 0 
    	 * Duration > thirtyMinutes = Duration.compareTo(thirtyMinutes) = 1
    	 */
    	int wasItInferiorOrEqual = lengthOfTimeDuringWhenCarWasParked.compareTo(thirtyMinutes);
    	if(wasItInferiorOrEqual < 1) {
    		return true;
    	}else {
    		return false;
    	}
	}
    /**
     * Used in Class ExitingVehicleController by Method : calculateFare(Ticket)
     * @param DURATION
     * @return DOUBLE
     */
    public double ifWasLessThanThirtyMinutesGetItFree(Duration lengthOfTimeDuringWhenCarWasParked) {
    	if(wasItThirtyMinutes(lengthOfTimeDuringWhenCarWasParked)) {
    		return 0.0;
    	}else {
    		return DateHelperUtil.transformDurationIntoDouble(lengthOfTimeDuringWhenCarWasParked);
    	}
	}
}