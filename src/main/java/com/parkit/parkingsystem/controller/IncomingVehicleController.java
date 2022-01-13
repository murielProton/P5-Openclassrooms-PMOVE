package com.parkit.parkingsystem.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.service.ParkingService;
import com.parkit.parkingsystem.util.InputReaderUtil;
import com.parkit.parkingsystem.util.RegistrationNumberSecurityUtil;

/**
 * @author Muriel Proton
 *
 */
public class IncomingVehicleController{
    private boolean needStopVehicleType = false;
    private ParkingType currentType = null;
    private ParkingService parkingService;
    
    /**
     * CONSTRUCTOR method
     * Used in Class AlphaController
     * @param ParkingService
     */
    public IncomingVehicleController(ParkingService parkingService) {
        this.parkingService = parkingService;
    }
    
    /**
     * Used in Class AlphaController by Method : processSelectAction(integer)
     * @param NONE
     * @return VOID
     */
    public void runSelectVehicleType(){
        if(parkingService.isThereAParkingSpotForAnyType()){
            while(needStopVehicleType==false){
                selectVehicleType();
            }
        }else{
            System.out.println("We are verry sorry all our parking slots are currently takent.");
            System.out.println("Please accept our sincere appologies, while waiting.");
        }
    }
    
    /**
     * Used in Class IncomingVehicleController by Method : runSelectVehicleType()
     * @param NONE
     * @return VOID
     */
    public void selectVehicleType(){
            System.out.println("Please select vehicle type from menu");
            System.out.println("1 CAR");
            System.out.println("2 BIKE");
            int selectVihicleType = InputReaderUtil.readInt();
            processSelectedType(selectVihicleType);
    }
    /**
     * Used in Class IncomingVehicleController by Method : selectVehicleType()
     * @param INTEGER vehicleType
     * @return ParkingType
     */
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
    /**
     * Used in Class IncomingVehicleController by Method : processSelectedType(INTEGER)
     * @param ParkingType
     * @return VOID
     */
    private void runRegistrationNumberController(ParkingType currentType) {
        if(parkingService.isThereAParkingSpotForType(currentType)){
            while(needStopVehicleType==false){
                try {
                    String vehicleRegNumber = RegistrationNumberController.inputRegistrationNumber();
                    if(RegistrationNumberSecurityUtil.checkIfVehicleRegistrationNumberIsValid(vehicleRegNumber) &&
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
    /**
     * Used in Class IncomingVehicleController by Method : runRegistrationNumberController(ParkingType)
     * @param STRING vehicleRegNumber
     * @param ParkingType
     * @return VOID
     */
    private void runSavingTicket(String vehicleRegNumber, ParkingType currentType){
        try{
            LocalDateTime inTime = LocalDateTime.now();
            ParkingSpot parkingSpotWhereToGo = parkingService.getNextParkingNumberIfAvailable(currentType);
            parkingService.fillParkingSpot(parkingSpotWhereToGo);
            parkingService.saveIncomingVehicleInDB(parkingSpotWhereToGo, vehicleRegNumber);
            successInSavingTicket(vehicleRegNumber, inTime, parkingSpotWhereToGo);
            AlphaController.loadInterface();
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Unable to process incoming vehicle");
        }
    }
    /**
     * Used in Class IncomingVehicleController by Method : runSavingTicket(String, ParkingType)
     * @param STRING vehicleRegNumber
     * @param LOCALDATETIME inTime
     * @param ParkingSpot
     * @return VOID
     */
    private void successInSavingTicket(String vehicleRegNumber, LocalDateTime inTime, ParkingSpot parkingSpot){
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    	String dateAndTimeOfArrival = inTime.format(formatter);
        System.out.println("You've entered this day : "+ dateAndTimeOfArrival);
        System.out.println("Please park your vehicle in spot number : "+parkingSpot.getId());
        System.out.println("The gate opens !");
    }
}