package com.parkit.parkingsystem;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.parkit.parkingsystem.util.RegistrationNumberSecurityUtil;

/**
 * @author Muriel Proton
 * Test in class DateHelperUtil
 * run with success the 20 01 2022 at 17h29
 */
public class RegistrationNumberSecurityUtilTest {
	@Test
	public void checkIfVehicleRegistrationNumberIsValidTest(){
		String registrationNumber = "AA-3654-BB";
		boolean realReturn = RegistrationNumberSecurityUtil.checkIfVehicleRegistrationNumberIsValid(registrationNumber);
		assertTrue( realReturn);
	}
	@Test
	public void checkIfVehicleRegistrationNumberIsFalseTest(){
		String registrationNumber = "";
		boolean realReturn = RegistrationNumberSecurityUtil.checkIfVehicleRegistrationNumberIsValid(registrationNumber);
		assertFalse( realReturn);
	}
}
