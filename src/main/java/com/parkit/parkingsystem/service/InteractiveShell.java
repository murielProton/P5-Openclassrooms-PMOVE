package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.util.InputReaderUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class InteractiveShell {

    private static final Logger logger = LogManager.getLogger("InteractiveShell");

    private InputReaderUtil inputReaderUtil = new InputReaderUtil();
    private ParkingSpotDAO parkingSpotDAO = new ParkingSpotDAO();
    private TicketDAO ticketDAO = new TicketDAO();
    public ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);

    /**
     * calls on next methode run
     */
    public static void loadInterface(){
        logger.info("App initialized!!!");
        System.out.println("Welcome to Parking System!");
        InteractiveShell myShell= new InteractiveShell();
        myShell.run();       
    }
    /**
     * calls on next methode processOption(gets info form terminal)
     */
    public void run (){
        boolean continueApp = true;
        while(continueApp){
            loadMenu();
            continueApp = processOption(inputReaderUtil.readSelection());
        }
    }

    /**
     * Excute the right methode or CASE
     * Returns True if App must continue
     */
    public boolean processOption(int option){
        switch(option){
            case 1: {
                parkingService.processIncomingVehicle();
                break;
            }
            case 2: {
                parkingService.processExitingVehicle();
                break;
            }
            case 3: {
                System.out.println("Exiting from the system!");
                return false;
            }
            default: System.out.println("Unsupported option. Please enter a number corresponding to the provided menu");
        }
        return true;
    }

    private static void loadMenu(){
        System.out.println("Please select an option. Simply enter the number to choose an action");
        System.out.println("1 New Vehicle Entering - Allocate Parking Space");
        System.out.println("2 Vehicle Exiting - Generate Ticket Price");
        System.out.println("3 Shutdown System");
    }

}
