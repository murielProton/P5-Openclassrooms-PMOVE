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

    public void processIncomingVehicle() {
        try{
            ParkingSpot parkingSpot = getNextParkingNumberIfAvailable();
            if(parkingSpot !=null && parkingSpot.getId() > 0){
                String vehicleRegNumber = getVehichleRegNumber();
                parkingSpot.setAvailable(false);
                parkingSpotDAO.updateParking(parkingSpot);//allot this parking space and mark it's availability as false
                
                LocalDateTime inTime = LocalDateTime.now();
                Ticket ticket = new Ticket();
                //ID, PARKING_NUMBER, VEHICLE_REG_NUMBER, PRICE, IN_TIME, OUT_TIME)
                //ticket.setId(ticketID);
                ticket.setParkingSpot(parkingSpot);
                ticket.setVehicleRegNumber(vehicleRegNumber);
                ticket.setPrice(0);
                ticket.setInTime(inTime);
                ticket.setOutTime(null);
                ticketDAO.saveTicket(ticket);
                System.out.println("Generated Ticket and saved in DB");
                System.out.println("Please park your vehicle in spot number:"+parkingSpot.getId());
                System.out.println("The gate opens !");
                System.out.println("Recorded in-time for vehicle number:"+vehicleRegNumber+" is:"+inTime);
            }
        }catch(Exception e){
            logger.error("Unable to process incoming vehicle",e);
        }
    }

    private String getVehichleRegNumber() throws Exception {
        System.out.println("Please type the vehicle registration number and press enter key");
        return InputReaderUtil.readVehicleRegistrationNumber();
    }
    
    public ParkingSpot getNextParkingNumberIfAvailable(){
        int parkingNumber=0;
        ParkingSpot parkingSpot = null;
        try{
            ParkingType parkingType = runGetVehichleType();

            parkingNumber = parkingSpotDAO.getNextAvailableSlot(parkingType);
            if(parkingNumber > 0){
                parkingSpot = new ParkingSpot(parkingNumber,parkingType, true);
            }else{
                throw new Exception("Error fetching parking number from DB. Parking slots might be full");
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

    public void processExitingVehicle() {
        try{            
            String vehicleRegNumber = getVehichleRegNumber();
            Ticket ticket = ticketDAO.getTicket(vehicleRegNumber);
            LocalDateTime outTime = LocalDateTime.now();
            ticket.setOutTime(outTime);
            fareCalculatorService.calculateFare(ticket);
            if(ticketDAO.updateTicket(ticket)) {
                ParkingSpot parkingSpot = ticket.getParkingSpot();
                parkingSpot.setAvailable(true);
                parkingSpotDAO.updateParking(parkingSpot);
                System.out.println("Please pay the parking fare : " + ticket.getPrice());
                System.out.println("Recorded out-time for vehicle number : " + ticket.getVehicleRegNumber() + " is : " + outTime);
                //put here window to payement param ticket + parkingSpot
                //PayementController.asLongAsPayementHasNOTBeenMade();
            }else{
                System.out.println("Unable to update ticket information. Error occurred");
            }
        }catch(Exception e){
            logger.error("Unable to process exiting vehicle", e);
            throw new RuntimeException("Unable to process exiting vehicle", e);
        }
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
        System.out.println("isThereAlreadyThisVehicleInDB" + registrationNumber+" - "+ parkingType +" result "+result);
        return ticketDAO.getTicketIfVehiculeAlreadyInside(registrationNumber, parkingType) > 0;
    }
}
