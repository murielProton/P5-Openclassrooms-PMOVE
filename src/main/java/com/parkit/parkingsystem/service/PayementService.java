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
    public static Ticket getTicketOfExitingVehicle(String vehiculRegNumber){
        return new TicketDAO().getTicketOfExitingVehicul(vehiculRegNumber);
    }
    
}