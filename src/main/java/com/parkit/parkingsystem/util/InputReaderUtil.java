package com.parkit.parkingsystem.util;

import java.util.Scanner;

/**
 * @author Muriel Proton
 *
 */
public class InputReaderUtil {

	private static Scanner scan = new Scanner(System.in);

	/**
	 * Used in Class RegistrationNumberController by Method : inputRegistrationNumber()
	 * @param NONE
	 * @return STRING
	 */
	public static String readNextLine() {
		try {
			return scan.nextLine();
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Used in Class AlphaController by Method : selectionAction()
	 * Used in Class IncomingVehicleController by Method : selectVehicleType()
	 * @param NONE
	 * @return INTEGER
	 */
	public static int readInt() {
		try {
			return Integer.parseInt(scan.nextLine());
		} catch (Exception e) {
			return -1;
		}
	}

}
