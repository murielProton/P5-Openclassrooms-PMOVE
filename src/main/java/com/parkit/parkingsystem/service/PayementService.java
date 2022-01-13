package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.dao.TicketDAO;

import java.time.LocalDateTime;

/**
 * @author Muriel Proton
 *
 */
public class PayementService {
	
    /**
     * Used in Class ExitingVehicleController by Method : getTicketOfExitingVehicleAndSetOutTime(String) & processExitingVehicle()
     * @param vehiculRegNumber
     * @return Ticket
     */
    public static Ticket getTicketOfExitingVehicleAndSetOutTime(String vehiculRegNumber){
        Ticket ticketOfExitingVehicle = TicketDAO.getTicketOfExitingVehicul(vehiculRegNumber);
        LocalDateTime outTime = LocalDateTime.now();
        ParkingService.updateOutTimeOfTicketForExitingVehicle(ticketOfExitingVehicle, outTime);
        return ticketOfExitingVehicle;
    }
    
}