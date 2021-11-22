package com.parkit.parkingsystem.service;

import java.time.Duration;
import java.time.LocalDateTime;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.model.Ticket;

public class FareCalculatorService {
    
    public void calculateFare(Ticket ticket){
        if( (ticket.getOutTime() == null) || (ticket.getOutTime().isBefore(ticket.getInTime())) ){
            throw new IllegalArgumentException("Out time provided is incorrect:"+ticket.getOutTime().toString());
        }

        LocalDateTime inHour = ticket.getInTime();
        System.out.println("inHour -> "+inHour);
        LocalDateTime outHour = ticket.getOutTime();
        System.out.println("outHour -> "+outHour);

        Duration lengthOfTimeDuringWhenCarWasParked = findLengthOfTimeBetweenTwoLocalDateTimes(inHour, outHour);
        double hoursOfParkedTime = transformDurationIntoDouble(lengthOfTimeDuringWhenCarWasParked);
        switch (ticket.getParkingSpot().getParkingType()){
            case CAR: {
                ticket.setPrice(hoursOfParkedTime * Fare.CAR_RATE_PER_HOUR);
                double carParkingFee = hoursOfParkedTime * Fare.CAR_RATE_PER_HOUR;
                System.out.println("car x number of hours the car was parked -> "+carParkingFee);
                break;
            }
            case BIKE: {
                ticket.setPrice(hoursOfParkedTime * Fare.BIKE_RATE_PER_HOUR);
                double bikeParkingFee = hoursOfParkedTime * Fare.CAR_RATE_PER_HOUR;
                System.out.println("bike x number of hours the bike was parked -> "+bikeParkingFee);
                break;
            }
            default: throw new IllegalArgumentException("Unkown Parking Type");
        }
    }
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
}