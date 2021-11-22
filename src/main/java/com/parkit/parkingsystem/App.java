package com.parkit.parkingsystem;

import java.time.LocalDateTime;
import java.util.Calendar;

import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.FareCalculatorService;

import org.apache.commons.lang.ObjectUtils.Null;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class App {
    //private static final Logger logger = LogManager.getLogger("App");
    public void main(String args[]){
       logger.info("Initializing Parking System");
       InteractiveShell.loadInterface();

    }
}
