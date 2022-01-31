package com.parkit.parkingsystem;

import com.parkit.parkingsystem.util.MathHelperUtil;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MathHelperUtilTest {
    /**
     * @author Muriel Proton
     * Test in class MathHelperUtil the method roundingPrice(DOUBLE)
     * run with success the 31 01 2022 at h
     */
	@Test
	public void roundingPriceTest(){
		double doubleToRound = 3.4499999999999997;
		double expectedDouble = 3.45;
		double doubleToTest = MathHelperUtil.roundingPrice(doubleToRound);
		assertEquals(expectedDouble, doubleToTest);
	  }
}
