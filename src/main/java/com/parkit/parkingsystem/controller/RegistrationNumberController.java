package com.parkit.parkingsystem.controller;

import com.parkit.parkingsystem.util.InputReaderUtil;

public class RegistrationNumberController {
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
