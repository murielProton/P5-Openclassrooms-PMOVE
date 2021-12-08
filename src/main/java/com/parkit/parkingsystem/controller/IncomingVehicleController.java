package com.parkit.parkingsystem.controller;

import com.parkit.parkingsystem.constants.ParkingType;
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
                        System.out.println("run save in database.");
                    }else{
                        System.out.println("Incorrect vehicule registered number.");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }else{
            System.out.println("We are verry sorry all our parking slots for are currently takent.");
            System.out.println("Please accept our sincere appologies, while waiting.");
        }
    }
}