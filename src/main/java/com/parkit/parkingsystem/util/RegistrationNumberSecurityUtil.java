package com.parkit.parkingsystem.util;

/**
 * @author Muriel Proton
 *
 */
public class RegistrationNumberSecurityUtil {

    /**
     * Used in Class IncomingVehicleController by Method : runRegistrationNumberController(ParkingType)
     * @param STRING registrationNumber
     * @return BOOLEAN
     */
    public static boolean checkIfVehicleRegistrationNumberIsValid(String registrationNumber){
        if(registrationNumber != null && registrationNumber.length() >= 7 && registrationNumber.trim().length() <= 14 ) {
            return true;
        }else{
            System.out.println("Error reading input. Please enter a valid string for vehicle registration number.");
            return false;
        }
    }
}
