package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.dao.TicketDAO;

import java.time.LocalDateTime;

public class PayementService {
    public static Ticket getTicketForExitingVehicules(String vehiculeRegNumber){
        Ticket ticketOfCurrentExitintVehicule = TicketDAO.getTicket(vehiculeRegNumber);
        LocalDateTime outTime = LocalDateTime.now();
        ticketOfCurrentExitintVehicule.setOutTime(outTime);
        return ticketOfCurrentExitintVehicule;
    }

}
