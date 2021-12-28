package com.parkit.parkingsystem.controller;

import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.service.ParkingService;
import com.parkit.parkingsystem.util.InputReaderUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AlphaController {

    private boolean working = true;
    private static final Logger logger = LogManager.getLogger("AlphaController");
    private ParkingSpotDAO parkingSpotDAO = new ParkingSpotDAO();
    private TicketDAO ticketDAO = new TicketDAO();
    private ParkingService parkingService = new ParkingService(parkingSpotDAO, ticketDAO);
    private IncomingVehicleController incomingVehicleController = new IncomingVehicleController(parkingService);
    
    

    /**
     * calls on next methode run
     */
    public static void loadInterface(){
        logger.info("App initialized!!!");
        System.out.println("Welcome to Parking System!");
        AlphaController myAlphaController= new AlphaController();
        myAlphaController.run();       
    }
    public void run(){
        while(working){
            selectionAction();
        }
    }
    public void selectionAction(){
        //Display
        System.out.println("Please select an option. Simply enter the number to choose an action");
        System.out.println("1 New Vehicle Entering - Allocate Parking Space");
        System.out.println("2 Vehicle Exiting - Generate Ticket Price");
        System.out.println("3 Shutdown System");
        //Get input
        int selectedAction = InputReaderUtil.readInt();
        //Treat input
        processSelectAction(selectedAction);
    }


    /**
     * Excute the right methode or CASE
     * Returns True if App must continue
     */
    public void processSelectAction(int action){
        switch(action){
            case 1: {
                incomingVehicleController.runSelectVehicleType();
                break;

            }
            case 2: {
                //TODO Faire la méthode associée.
                System.out.println("doExitingVehicle");
                //doExitingVehicle();
                break;
            }
            case 3: {
                System.out.println("Exiting from the system!");
                working = false;
                break;
            }
            default: System.out.println("Unsupported option. Please enter a number corresponding to the provided menu");
        }
    }
}
