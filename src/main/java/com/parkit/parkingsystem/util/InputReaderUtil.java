package com.parkit.parkingsystem.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;

public class InputReaderUtil {

	private static Scanner scan = new Scanner(System.in);
	private static final Logger logger = LogManager.getLogger("InputReaderUtil");

	public static int readSelection() {
		try {
			int input = Integer.parseInt(scan.nextLine());
			return input;
		} catch (Exception e) {
			logger.error("Error while reading user input from Shell", e);
			System.out.println("Error reading input. Please enter valid number for proceeding further");
			return -1;
		}
	}

	public static String readVehicleRegistrationNumber() throws Exception {
		String vehicleRegNumber = scan.nextLine();
		RegistrationNumberSecurityUtil securityRegNumber = new RegistrationNumberSecurityUtil(vehicleRegNumber);
		if (RegistrationNumberSecurityUtil.checkIfVehicleRegistrationNumberIsValid(vehicleRegNumber)) {
			return vehicleRegNumber;
		}
		return null;
	}

	/**
	 * Old methods TODO sort old methods ------------------ New methods added by
	 * Muriel
	 */
	/*
	 * public static String readNextLine(String whatIsAsked) { try { return
	 * scan.nextLine(); }catch(Exception e){
	 * logger.error("Error while reading next line from the user input in Shell",
	 * e); System.out.println("Error reading input. Please enter valid "
	 * +whatIsAsked+" for proceeding further"); return null; } }
	 */

	public static String readNextLine() {
		try {
			return scan.nextLine();
		} catch (Exception e) {
			return null;
		}
	}

	public static int readInt() {
		try {
			return Integer.parseInt(scan.nextLine());
		} catch (Exception e) {
			return -1;
		}
	}

}
