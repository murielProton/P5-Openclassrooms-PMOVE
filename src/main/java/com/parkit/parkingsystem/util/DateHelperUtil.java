package com.parkit.parkingsystem.util;

import java.time.LocalDateTime;
import java.sql.Timestamp;
import java.time.Duration;

/**
 * @author Muriel Proton
 *
 */

public class DateHelperUtil {
	
	/**
	 * Used in Class FareCalculatorService by Method : getDurationOfTicket(Ticket)
	 * @param LOCALDATETIME in
	 * @param LOCALDATETIME out
     * @return DURATION
	 *
	 */
    public static Duration findLengthOfTimeBetweenTwoLocalDateTimes(LocalDateTime in, LocalDateTime out){
        Duration lengthOfTimeDuringWhenCarWasParked = Duration.between(in, out);
        return lengthOfTimeDuringWhenCarWasParked;
    }
    
    /**
     * Used in Class FareCalculatorService by Method : calculateFare(Ticket
     * @param DURATION
     * @return DOUBLE
     */
    public static double transformDurationIntoDouble(Duration newDuration){
        
        double durationTypeConvertedToDouble = (double)newDuration.toMinutes()/60;
        return durationTypeConvertedToDouble;
    }
    
    /**
     * Used in Class TicketDAO by Method : saveTicket(Ticket) & updateOutTimeOfCurrentTicket(Ticket)
     * @param LOCALDATETIME
     * @return TIMESTAMP
     */
    public static Timestamp convertLocalDateTimeToTimestamp(LocalDateTime aDateAndTime){
    	if(aDateAndTime == null) {
    		return null;
    	}
    	return Timestamp.valueOf(aDateAndTime);
    }
    
    /**
     * Used in Class TicketDAO by Method : resultSetToTicket(String, ResultSet)
     * @param TIMESTAMP
     * @return LOCALDATETIME
     */
    public static LocalDateTime convertTimestampsToLocalDateTime(Timestamp aTimestamp){
    	if(aTimestamp == null) {
    		return null;
    	}
    	return aTimestamp.toLocalDateTime();
    }
}
