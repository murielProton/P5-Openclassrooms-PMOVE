package com.parkit.parkingsystem.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.service.ParkingService;
import com.parkit.parkingsystem.util.InputReaderUtil;
import com.parkit.parkingsystem.util.RegistrationNumberSecurityUtil;

public class IncomingVehicleController{
    private boolean needStopVehicleType = false;
    private ParkingType currentType = null;
    private String vehicleRegNumber = null;
    private ParkingService parkingService;
    RegistrationNumberSecurityUtil instanceOfRegistrationNumberSecurityUtil = new RegistrationNumberSecurityUtil(vehicleRegNumber);
    
    public IncomingVehicleController(ParkingService parkingService) {
        this.parkingService = parkingService;
    }
    public void runSelectVehicleType(){
        //TODO Faire la méthode associée.
        if(parkingService.isThereAParkingSpotForAnyType()){
            while(needStopVehicleType==false){
                selectVehicleType();
            }
        }else{
            System.out.println("We are verry sorry all our parking slots are currently takent.");
            System.out.println("Please accept our sincere appologies, while waiting.");
        }
    }
    public void selectVehicleType(){
            System.out.println("Please select vehicle type from menu");
            System.out.println("1 CAR");
            System.out.println("2 BIKE");
            int selectVihicleType = InputReaderUtil.readInt();
            processSelectedType(selectVihicleType);
    }
    private ParkingType processSelectedType(int vehicleType) {
        switch(vehicleType){
                case 1: {
                    System.out.println("You are driving a car.");
                    runRegistrationNumberController(currentType.CAR);
                    //currentType = ParkingType.CAR;
                    // select TYpe + screen registration slate
                    break;
                }
                case 2: {
                    System.out.println("You are driving a motorbike.");
                    runRegistrationNumberController(currentType.BIKE);
                    //currentType = ParkingType.BIKE;
                    // select TYpe + screen registration slate
                    break;
                }
                default: {
                    System.out.println("Incorrect type provided.");
                    needStopVehicleType = true;
                    break;
                }
            }
            return null;
    }
    private void runRegistrationNumberController(ParkingType currentType) {
        if(parkingService.isThereAParkingSpotForType(currentType)){
            while(needStopVehicleType==false){
                try {
                    vehicleRegNumber = RegistrationNumberController.inputRegistrationNumber();
                    if(instanceOfRegistrationNumberSecurityUtil.checkIfVehicleRegistrationNumberIsValid(vehicleRegNumber) &&
                    !parkingService.isThereAlreadyThisVehicleInDB(vehicleRegNumber, currentType)){
                        runSavingTicket(vehicleRegNumber, currentType);
                    }else{
                        System.out.println("Incorrect vehicule registered number.");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }else{
            System.out.println("We are verry sorry all our parking slots for are currently taken.");
            System.out.println("Please accept our sincere appologies, while waiting.");
        }
    }
    private void runSavingTicket(String vehicleRegNumber, ParkingType currentType){
        try{
            LocalDateTime inTime = LocalDateTime.now();
            ParkingSpot parkingSpotWhereToGo = parkingService.getNextParkingNumberIfAvailable(currentType);
            parkingService.saveParkingSpot(parkingSpotWhereToGo);
            parkingService.saveIncomingVehicleInDB(parkingSpotWhereToGo, vehicleRegNumber);
            successInSavingTicket(vehicleRegNumber, inTime, parkingSpotWhereToGo);
            //TODO : REAL PB HERE
            AlphaController.loadInterface();
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Unable to process incoming vehicle");
        }
    }
    private void successInSavingTicket(String vehicleRegNumber, LocalDateTime inTime, ParkingSpot parkingSpot){
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    	String dateAndTimeOfArrival = inTime.format(formatter);
        System.out.println("You've entered this day : "+ dateAndTimeOfArrival);
        System.out.println("Please park your vehicle in spot number : "+parkingSpot.getId());
        System.out.println("The gate opens !");
    }
}