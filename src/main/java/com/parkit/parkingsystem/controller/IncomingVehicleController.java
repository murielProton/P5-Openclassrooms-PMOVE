package com.parkit.parkingsystem.controller;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.service.ParkingService;
import com.parkit.parkingsystem.util.InputReaderUtil;

public class IncomingVehicleController{
    private boolean needStopVehicleType = false;
    private ParkingType currentType = null;
    private ParkingService parkingService;
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
                    //currentType = ParkingType.CAR;
                    // select TYpe + screen registration slate
                    break;
                }
                case 2: {
                    System.out.println("You are driving a motorbike.");
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
    
}