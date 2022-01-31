package com.parkit.parkingsystem.util;

public class MathHelperUtil {
	  public static double roundingPrice(double doubleToRound)
	  {
	      double roundedDouble = Math.round(doubleToRound*100.0)/100.0;
	      return roundedDouble;
	  }
}
