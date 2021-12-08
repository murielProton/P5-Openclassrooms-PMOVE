package com.parkit.parkingsystem.util;

public class RegistrationNumberSecurityUtil {

    String registrationNumber;

    public RegistrationNumberSecurityUtil(String registrationNumber){
        this.registrationNumber = registrationNumber;
    }

    public boolean checkIfVehicleRegistrationNumberIsValid(String registrationNumber){
        System.out.println("checkIfVehicleRegistrationNumberIsValid -> "+registrationNumber);
        if(registrationNumber != null && registrationNumber.length() >= 7 && registrationNumber.trim().length() <= 14 ) {
            System.out.println("This registration number is valid.");
            return true;
        }else{
            System.out.println("Error reading input. Please enter a valid string for vehicle registration number.");
            return false;
        }
    }
}
