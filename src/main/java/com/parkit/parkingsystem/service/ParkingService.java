package com.parkit.parkingsystem.service;


import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;

/**
 * @author Muriel Proton
 *
 */
public class ParkingService {

	private static final Logger logger = LogManager.getLogger("ParkingService");


    private ParkingSpotDAO parkingSpotDAO;
    private TicketDAO ticketDAO;

    /**
     * CONSTRUCTOR method
     * Used in Class AlphaController to instantiate IncomingVehicleController & ExitingVehicleController
     * @param ParkingSpotDAO
     * @param TicketDAO
     */
    public ParkingService(ParkingSpotDAO parkingSpotDAO, TicketDAO ticketDAO){

        this.parkingSpotDAO = parkingSpotDAO;
        this.ticketDAO = ticketDAO;
    }

    /**
     * Used in Class IncomingVehicleController by Method : runSavingTicket(String, ParkingType)
     * @param ParkingType
     * @return ParkingSpot
     */
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
    /**
     * Used in Class IncomingVehicleController by Method : runSelectVehicleType()
     * @param NONE
     * @return BOOLEAN
     */
    public boolean isThereAParkingSpotForAnyType(){
        return parkingSpotDAO.getTotalNumberOfAvailableSlot() > 0;
    }
    /**
     * Used in Class IncomingVehicleController by Method : runRegistrationNumberController(ParkingType)
     * @param ParkingType
     * @return BOOLEAN
     */
    public boolean isThereAParkingSpotForType(ParkingType parkingType){
        return parkingSpotDAO.getNumberOfAvailableSlotForType(parkingType) > 0;
    }
    /**
     * Used in Class IncomingVehicleController by Method : runRegistrationNumberController(ParkingType)
     * @param registrationNumber
     * @param ParkingType
     * @return
     */
    public boolean isThereAlreadyThisVehicleInDB(String registrationNumber, ParkingType parkingType){
        return ticketDAO.getTicketIfVehiculeAlreadyInside(registrationNumber, parkingType) > 0;
    }
    /**
     * Used in Class IncomingVehicleController by Method : runSavingTicket(String, ParkingType)
     * @param ParkingSpot
     * @return VOID
     */
    public void fillParkingSpot(ParkingSpot parkingSpot){
        try{
        	parkingSpot.setAvailable(false);
            parkingSpotDAO.updateParking(parkingSpot);
        }catch(Exception e){
            logger.error("Unable to fill Parking Spot",e);
        }
    }
    /**
     * Used in Class ExitingVehicleController by Method : processExitingVehicle()
     * @param parkingSpot
     * @return VOID
     */
    public void emptyParkingSpot(ParkingSpot parkingSpot){
        try{
            parkingSpot.setAvailable(true);
            parkingSpotDAO.updateParking(parkingSpot);
        }catch(Exception e){
            logger.error("Unable to empty Parking Spot",e);
        }
    }
    /**
     * Used in Class IncomingVehicleController by Method : runSavingTicket(String, ParkingType)
     * @param ParkingType
     * @param STRING vehicleRegNumber
     * @return VOID
     */
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
                ticket.setOutTime(null);
                ticketDAO.saveTicket(ticket);
            }
        }catch(Exception e){
            logger.error("Unable to save incoming vehicle",e);
        }
    }
    
	/**
	 * Used in Class PayementService by Method : getTicketOfExitingVehicleAndSetOutTime(String)
	 * @param Ticket
	 * @param LocalDateTime outTime
	 * @return VOID
	 */
	public static void updateOutTimeOfTicketForExitingVehicle(Ticket ticket, LocalDateTime outTime) {
		ticket.setOutTime(outTime);
		TicketDAO ticketDAO= new TicketDAO();
		ticketDAO.updateOutTimeOfCurrentTicket(ticket);
		
	}
	/**
	 * Used in Class ExitingVehicleController by Method : processExitingVehicle()
	 * @param Ticket
	 * @param DOUBLE price
	 * @return VOID
	 */
	public static void updatePriceOfCurrentTicket(Ticket ticket, Double price) {
		ticket.setPrice(price);
		TicketDAO ticketDAO= new TicketDAO();
		ticketDAO.updatePriceOfCurrentTicket(ticket, price);
		
	}
}
