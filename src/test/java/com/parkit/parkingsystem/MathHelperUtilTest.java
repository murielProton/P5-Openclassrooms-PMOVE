package com.parkit.parkingsystem;

import com.parkit.parkingsystem.util.MathHelperUtil;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MathHelperUtilTest {
	@Test
	public static void roundingPriceTest()
	  {
		double doubleToRound = 3.4499999999999997;
		double expectedDouble = 3.45;
		double doubleToTest = MathHelperUtil.roundingPrice(doubleToRound);
		assertEquals(expectedDouble, doubleToTest);
	  }
}
