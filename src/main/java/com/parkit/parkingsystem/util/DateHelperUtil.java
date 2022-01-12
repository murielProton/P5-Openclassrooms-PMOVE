package com.parkit.parkingsystem.util;

import java.util.Date;
import java.time.LocalDateTime;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.Duration;

public class DateHelperUtil {
    public static Duration findLengthOfTimeBetweenTwoLocalDateTimes(LocalDateTime in, LocalDateTime out){
        Duration lengthOfTimeDuringWhenCarWasParked = Duration.between(in, out);
        return lengthOfTimeDuringWhenCarWasParked;
    } 
    public static double transformDurationIntoDouble(Duration newDuration){
        
        double durationTypeConvertedToDouble = (double)newDuration.toMinutes()/60;
        return durationTypeConvertedToDouble;
    }
    public static Timestamp convertLocalDateTimeToTimestamp(LocalDateTime aDateAndTime){
    	if(aDateAndTime == null) {
    		return null;
    	}
    	return Timestamp.valueOf(aDateAndTime);
    }
    
    public static LocalDateTime convertTimestampsToLocalDateTime(Timestamp aTimestamp){
    	if(aTimestamp == null) {
    		return null;
    	}
    	return aTimestamp.toLocalDateTime();
    }

    public static LocalDateTime convertDateToLocalDateTime(Date aDate){
    	if(aDate == null) {
    		return null;
    	}
    	return LocalDateTime.ofInstant(aDate.toInstant(), ZoneId.systemDefault());
    }
}
