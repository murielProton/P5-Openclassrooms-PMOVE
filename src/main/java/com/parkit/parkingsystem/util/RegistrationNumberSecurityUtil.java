package com.parkit.parkingsystem.util;

public class RegistrationNumberSecurityUtil {

    String registrationNumber;

    public RegistrationNumberSecurityUtil(String registrationNumber){
        this.registrationNumber = registrationNumber;
    }

    public boolean checkIfVehicleRegistrationNumberIsValid(String registrationNumber){
        if(registrationNumber != null && registrationNumber.length() >= 7 && registrationNumber.trim().length() <= 14 ) {
            return true;
        }else{
            System.out.println("Error reading input. Please enter a valid string for vehicle registration number.");
            return false;
        }
    }
}
