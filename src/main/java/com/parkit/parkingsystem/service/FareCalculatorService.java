package com.parkit.parkingsystem.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.util.DateHelperUtil;
import com.parkit.parkingsystem.util.MathHelperUtil;

/**
 * @author Muriel Proton
 *
 */
public class FareCalculatorService {

    private TicketDAO ticketDAO =  new TicketDAO();
	
	
    /**
     * Used in Class FareCalculatorService by Method : calculateFare(Ticket)
     * @param Ticket
     * @return DURATION
     */
    public Duration getDurationOfTicket(Ticket ticket){
    	/*When not rounded the outTime == inTime
    	 * there for we need to have a outTimeSafe
    	 * first macke sure outTime has been assigned to a value
    	 * then round it.
    	 */
    	LocalDateTime outTimeSafe = null;
		if( ticket.getOutTime() != null) {
			outTimeSafe = ticket.getOutTime().plusSeconds(1).truncatedTo(ChronoUnit.SECONDS);
		}
		/*now we can test safely if the out time is after the in time. The test will always work.
		 * or not ^v^ */
		if( outTimeSafe == null || ticket.getInTime().isAfter(outTimeSafe) ){
            throw new IllegalArgumentException("Out time provided is incorrect: "+outTimeSafe);
        }

        LocalDateTime inHour = ticket.getInTime();
        LocalDateTime outHour = outTimeSafe;

        Duration lengthOfTimeDuringWhenCarWasParked = DateHelperUtil.findLengthOfTimeBetweenTwoLocalDateTimes(inHour, outHour);
        return lengthOfTimeDuringWhenCarWasParked;
    }
    /**
     * Used in Class ExitingVehicleController by Method : getExitingVehiculePriceFare(Ticket)
     * @param Ticket
     * @return DOUBLE
     */
    public double calculateFare(Ticket ticket){
        double parkingTypeRatePerHour = getRatePerHour(ticket);
        Duration lengthOfTimeDuringWhenCarWasParked = getDurationOfTicket(ticket);
        double hoursOfParkedTime = ifWasLessThanThirtyMinutesGetItFree(lengthOfTimeDuringWhenCarWasParked);
        double priceRateForCustumer = getPriceRateForCustumer(ticket.getVehicleRegNumber());
        double parkingFee = MathHelperUtil
        		.roundingPrice(hoursOfParkedTime * parkingTypeRatePerHour * priceRateForCustumer);
        ticket.setPrice(parkingFee);
        return parkingFee;
    }
    /**
     * Used in Class FareCalculatorService by Method : calculateFare(DURATION)
     * @param Ticket
     * @return DOUBLE
     */
	private double getRatePerHour(Ticket ticket) {
		double parkingTypeRatePerHour;
        switch (ticket.getParkingSpot().getParkingType()){
            case CAR: {
            	parkingTypeRatePerHour = Fare.CAR_RATE_PER_HOUR;
            	break;
            }
            case BIKE: {
            	parkingTypeRatePerHour = Fare.BIKE_RATE_PER_HOUR;
            	break;
            }
            default: throw new IllegalArgumentException("Unkown Parking Type");
        }
		return parkingTypeRatePerHour;
	}
    /**
     * Used in Class FareCalculatorService by Method : calculateFare(DURATION)
     * @param STRING
     * @return DOUBLE
     */
    private double getPriceRateForCustumer(String vehicleRegNumber) {
    	if(ticketDAO.isItALoyalCustomer(vehicleRegNumber)) {
    		return 0.95;
    	}else {
    		return 1;
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