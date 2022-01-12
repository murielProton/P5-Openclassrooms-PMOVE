package com.parkit.parkingsystem.service;


import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.util.InputReaderUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;

public class ParkingService {

    private static final Logger logger = LogManager.getLogger("ParkingService");

    private static FareCalculatorService fareCalculatorService = new FareCalculatorService();

    private ParkingSpotDAO parkingSpotDAO;
    private TicketDAO ticketDAO;

    public ParkingService(ParkingSpotDAO parkingSpotDAO, TicketDAO ticketDAO){

        this.parkingSpotDAO = parkingSpotDAO;
        this.ticketDAO = ticketDAO;
    }

    


    
    public ParkingSpot getNextParkingNumberIfAvailable(ParkingType parkingType){
        int parkingNumber=0;
        ParkingSpot parkingSpot = null;
        try{
            parkingNumber = parkingSpotDAO.getNextAvailableSlot(parkingType);
            if(parkingNumber > 0){
                parkingSpot = new ParkingSpot(parkingNumber,parkingType, true);
            }else{
                throw new Exception("Error fetching parking number from DB. Parking slots might be full.");
            }
        }catch(IllegalArgumentException ie){
            logger.error("Error parsing user input for type of vehicle", ie);
        }catch(Exception e){
            logger.error("Error fetching next available parking slot", e);
        }
        return parkingSpot;
    }
    private static void loadMenuGetVehiculeType(){
        System.out.println("Please select vehicle type from menu");
        System.out.println("1 CAR");
        System.out.println("2 BIKE");
    }
    /**
     * calls on next methode getVehichleType(gets info form terminal)
     */
    public ParkingType runGetVehichleType (){
        ParkingType type = null;
        while(type == null){
            loadMenuGetVehiculeType();
            type = getVehichleType(InputReaderUtil.readSelection());
        }
        return type;
    }
    public ParkingType getVehichleType(int input){
        switch(input){
            case 1: {
                return ParkingType.CAR;
            }
            case 2: {
                return ParkingType.BIKE;
            }
            default: {
                System.out.println("Incorrect input provided");
            }
        }
        return null;
    }

   
    /** Old Methods TODO sort throug
     * -------------------------------------
     * New methods by Muriel
     */
    public boolean isThereAParkingSpotForAnyType(){
        return parkingSpotDAO.getTotalNumberOfAvailableSlot() > 0;
    }
    public boolean isThereAParkingSpotForType(ParkingType parkingType){
        return parkingSpotDAO.getNumberOfAvailableSlotForType(parkingType) > 0;
    }
    public boolean isThereAlreadyThisVehicleInDB(String registrationNumber, ParkingType parkingType){
        boolean result = ticketDAO.getTicketIfVehiculeAlreadyInside(registrationNumber, parkingType) > 0;
        return ticketDAO.getTicketIfVehiculeAlreadyInside(registrationNumber, parkingType) > 0;
    }
    public void saveParkingSpot(ParkingSpot parkingSpot){
        try{
            parkingSpot.setAvailable(false);
            parkingSpotDAO.updateParking(parkingSpot);
        }catch(Exception e){
            logger.error("Unable to process save Parking Spot",e);
        }
    }
    public void saveIncomingVehicleInDB(ParkingSpot parkingSpot, String vehicleRegNumber) {
        try{
            if(parkingSpot !=null && parkingSpot.getId() > 0){
                //allot this parking space and mark it's availability as false
                
                LocalDateTime inTime = LocalDateTime.now();
                Ticket ticket = new Ticket();
                //ID, PARKING_NUMBER, VEHICLE_REG_NUMBER, PRICE, IN_TIME, OUT_TIME)
                //ticket.setId(ticketID);
                ticket.setParkingSpot(parkingSpot);
                ticket.setVehicleRegNumber(vehicleRegNumber);
                ticket.setPrice(0);
                ticket.setInTime(inTime);
                //TODO : replace null by now LocalDateTime
                ticket.setOutTime(null);
                ticketDAO.saveTicket(ticket);
            }
        }catch(Exception e){
            logger.error("Unable to save incoming vehicle",e);
        }
    }
    public Ticket getTicketForExitingVehicle(String vehicleRegistrationNumber) {
    	try {
	    	Ticket currentTicket = TicketDAO.getTicket(vehicleRegistrationNumber);
	    	return currentTicket;
    	}catch(Exception e) {
    		logger.error("Unable to find this outcomming vehicle number : "+vehicleRegistrationNumber,e);
    		return null;
    	}
    }
    public void updateTicketOutTime(Ticket ticket) {
    	try {
    		ticket.setOutTime(LocalDateTime.now());
    	}catch(Exception e) {
    		logger.error("Unable to update ticket with the right out time.");
    	}
    }
	public void updateTicketAndParkingSpotAfterPayement(Ticket ticket) {
		// TODO Auto-generated method stub
		
	}
}
