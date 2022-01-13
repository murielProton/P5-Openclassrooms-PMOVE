package com.parkit.parkingsystem.controller;

import com.parkit.parkingsystem.util.InputReaderUtil;

/**
 * @author Muriel Proton
 *
 */
public class RegistrationNumberController {
	
    /**
     * Used in Class IncomingVehicleController by Method : runRegistrationNumberController(ParkingType)
     * Used in Class ExitingVehicleController by Method : getRegistrationNumber()
     * @param NONE
     * @return STRING
     * @throws Exception
     */
    public static String inputRegistrationNumber() throws Exception {
        String registrationNumber = null;
        try{
        System.out.println("Please type the vehicle registration number and press enter key.");
        registrationNumber = InputReaderUtil.readNextLine();
        return registrationNumber;
        }catch (Exception regNumberException){
            regNumberException.printStackTrace();
        }
        return registrationNumber;
    }
}
