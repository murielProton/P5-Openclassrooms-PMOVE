package com.parkit.parkingsystem.util;

import java.util.Date;
import java.time.LocalDateTime;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.Duration;

public class DateHelperUtil {
    public static Duration findLengthOfTimeBetweenTwoLocalDateTimes(LocalDateTime in, LocalDateTime out){
        Duration lengthOfTimeDuringWhenCarWasParked = Duration.between(in, out);
        System.out.println("lengthOfTimeDuringWhenCarWasParked -> "+lengthOfTimeDuringWhenCarWasParked);
        return lengthOfTimeDuringWhenCarWasParked;
    } 
    public static double transformDurationIntoDouble(Duration newDuration){
        
        double durationTypeConvertedToDouble = (double)newDuration.toMinutes()/60;
        System.out.println("durée du parkcing en type double "+durationTypeConvertedToDouble);
        
        return durationTypeConvertedToDouble;
    }
    public static Timestamp convertLocalDateTimeToTimestamp(LocalDateTime aDateAndTime){
        Timestamp aTimeStamp = Timestamp.valueOf(aDateAndTime);
        return aTimeStamp;
    }
    
    public static LocalDateTime convertTimestampsToLocalDateTime(Timestamp aTimestamp){
        LocalDateTime aLocalDateTime = aTimestamp.toLocalDateTime();
        return aLocalDateTime;
    }

    public static LocalDateTime convertDateToLocalDateTime(Date aDate){
        LocalDateTime aLocalDateTime = LocalDateTime.ofInstant(aDate.toInstant(), ZoneId.systemDefault());
        return aLocalDateTime;
    }
}
