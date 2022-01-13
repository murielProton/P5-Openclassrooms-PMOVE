package com.parkit.parkingsystem.controller;

import java.time.format.DateTimeFormatter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.FareCalculatorService;
import com.parkit.parkingsystem.service.ParkingService;
import com.parkit.parkingsystem.service.PayementService;

/**
 * @author Muriel Proton
 *
 */
public class ExitingVehicleController {

	private ParkingService parkingService;
	private static final Logger logger = LogManager.getLogger("ExitingVehicleController");

	/**
	 * CONSTRUCTOR method
     * Used in Class  AlphaController 
     * @param ParkingService
	 */
	public ExitingVehicleController(ParkingService parkingService) {
		this.parkingService = parkingService;
	}

	/*
	 * gets the registration number of vehicle
	 * Used in Class processExitingVehicle by Method : processExitingVehicle
	 * @param NONE
	 * @return STRING
	 **/
	public String getRegistrationNumber() {
		try {
			String vehicleRegNumber = RegistrationNumberController.inputRegistrationNumber();
			return vehicleRegNumber;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * gets price of the ticket
	 * Used in Class processExitingVehicle by Method : processExitingVehicle 
	 * @param STRING vehicleRegNumber
	 * @return Ticket
	 */
	public Ticket getTicketOfExitingVehicleAndSetOutTime(String vehiculRegNumber) {
		try {
			Ticket currentTicket = PayementService.getTicketOfExitingVehicleAndSetOutTime(vehiculRegNumber);
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
			DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			String timeOfDeparture = currentTicket.getOutTime().format(formatter);
			String timeOfArrival = currentTicket.getInTime().format(formatter);
			String dayOfArrival = currentTicket.getInTime().format(dayFormatter);
			
			System.out.println("ExitingVehicleController Recorded out-time for your vehicle, registration number "
					+ currentTicket.getVehicleRegNumber() + ", is : " + timeOfDeparture+".");
			System.out.println("You have entered our facilities on the : "+dayOfArrival+ ", at : "+ timeOfArrival+".");
			return currentTicket;
		} catch (Exception e) {
			logger.error("Unable to process exiting vehicle and set out time.", e);
			throw new RuntimeException("Unable to process ticket.", e);
		}
	}

	/**
	 * gets price of the ticket
	 * Used in Class processExitingVehicle by Method : processExitingVehicle 
	 * @param Ticket ticket
	 * @return DOUBLE
	 */
	public Double getExitingVehiculePriceFare(Ticket ticket) {
		try {
			FareCalculatorService currentFare = new FareCalculatorService();
			Double currentPrice = currentFare.calculateFare(ticket);
			System.out.println("Your price fare is : " + currentPrice);
			return currentPrice;
		} catch (Exception e) {
			logger.error("Unable to process price fare for exiting vehicle.", e);
			throw new RuntimeException("Unable to process ticket.", e);
		}
	}

	/**
	 * upadates ticket and parking spot's avalability
	 * Used in Class processExitingVehicle by Method : processExitingVehicle 
	 * @param DOUBLE price
	 * @return BOOLEAN
	 */
	public boolean payementTerminal(Double currentPrice) {
		System.out.println("Please insert your CB, and folow the instructions on screen.");
		// TODO Please insert Security of payement Here
		boolean payementSecured = true;
		if (payementSecured) {
			return true;
		} else {
			System.out.println("return false");
			return false;
		}
	}

	/**
	 * This method has 2 actions 1 updates parking spot and ticket 2 opens the gate
	 * Used in Class processExitingVehicle by Method : processExitingVehicle 
	 * @param BOOLEAN payementValidated, Ticket
	 * @return VOID
	 */
	public void openGateForExitingVehicle(boolean payementValidated, Ticket ticket) {
		if (payementValidated) {
			try {
				System.out.println("Please exite by the opening gate.");
			} catch (Exception e) {
				System.out.println("Unable to update ticket information. Error occurred");
			}

		} else {
			System.out.println("Please waite help is on its way.");
		}
	}

	/**
	 * runs all the methods of this class
	 * Used in Class AlphaController by Method : processSelectAction(integer)
	 * @param NONE
	 * @return VOID
	 */
	public void processExitingVehicle() {
		String vehiculRegNumber = getRegistrationNumber();
		Ticket thisTicket = getTicketOfExitingVehicleAndSetOutTime(vehiculRegNumber);
		Double priceForThisTicket = getExitingVehiculePriceFare(thisTicket);
		boolean payementValidated = payementTerminal(priceForThisTicket);
		ParkingSpot parkingSpotToVacate = thisTicket.getParkingSpot();
		if(payementValidated) {
			ParkingService.updatePriceOfCurrentTicket(thisTicket, priceForThisTicket);
			System.out.println("Update of ticket is a success.");
			openGateForExitingVehicle(payementValidated, thisTicket);
			//TODO Verify that the vehicle realy left the facilities
			parkingService.emptyParkingSpot(parkingSpotToVacate);
			}
		
	}
}
