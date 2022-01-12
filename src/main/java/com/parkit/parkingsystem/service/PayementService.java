package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;

import java.time.LocalDateTime;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class PayementService {
	
	private ParkingSpotDAO parkingSpotDAO;

    public PayementService(ParkingSpotDAO parkingSpotDAO){
        this.parkingSpotDAO = parkingSpotDAO;
    }
	
	private static final Logger logger = LogManager.getLogger("PayementServiceExceptions");
	
    public static Ticket getTicketForExitingVehicules(String vehiculeRegNumber){
        Ticket ticketOfCurrentExitintVehicule = TicketDAO.getTicket(vehiculeRegNumber);
        LocalDateTime outTime = LocalDateTime.now();
        ticketOfCurrentExitintVehicule.setOutTime(outTime);
        return ticketOfCurrentExitintVehicule;
    }
    public boolean updateTicketAndParkingSpotAfterPayement(Ticket ticket) {
	    try {
	    	if(TicketDAO.updateTicket(ticket)) {
		        ParkingSpot parkingSpot = ticket.getParkingSpot();
		        parkingSpot.setAvailable(true);
		        parkingSpotDAO.updateParking(parkingSpot);
		        return true;
		    } else {
		    	logger.error("Error while updating Ticket after payement.");
		    	return false;
		    }
	    	}
    	catch(Exception e) {
            logger.error("Error while updating after payement.",e);
            	return false;
    		
    	}
	}
    
}