package com.parkit.parkingsystem;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.ReflectionUtils;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import com.parkit.parkingsystem.controller.AlphaController;
import com.parkit.parkingsystem.controller.IncomingVehicleController;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.service.ParkingService;
import com.parkit.parkingsystem.util.InputReaderUtil;

import junit.framework.Assert;


/**
 * @author Muriel Proton
 *
 */
public class AlphaControllerTest {
	private final PrintStream standardOut = System.out;
	private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

	@BeforeEach
	public void setUp() {
	    System.setOut(new PrintStream(outputStreamCaptor));
	}
	
	@AfterEach
	public void tearDown() {
	    System.setOut(standardOut);
	}
	@Mock
	private AlphaController myAlphaControllerTest = Mockito.mock(AlphaController.class);
	
	@Test
	void loadInterfaceSystemOutPrintLNTest(){
		 try (MockedStatic<InputReaderUtil> utilities = Mockito.mockStatic(InputReaderUtil.class)) {

				AlphaController spy = Mockito.spy(AlphaController.class);
				spy.setWorking(true);
			 utilities.when(InputReaderUtil::readInt).thenReturn(3);
		        AlphaController.loadInterface();
		        Assert.assertTrue(outputStreamCaptor.toString().contains("Welcome to Parking System!"));
		    }
	}
	@Test
	void selectionActionDoNothingTest(){
		AlphaController spy = Mockito.spy(AlphaController.class);
		Mockito.doNothing().when(spy).selectionAction();		
	}
	@Test
	void selectionActionSystemOutPrintLNTest(){
		 try (MockedStatic<InputReaderUtil> utilities = Mockito.mockStatic(InputReaderUtil.class)) {
			 AlphaController spy = Mockito.spy(AlphaController.class);
		        utilities.when(InputReaderUtil::readInt).thenReturn(3);
		        spy.selectionAction();
		        Assert.assertTrue(outputStreamCaptor.toString().contains("Please select an option. Simply enter the number to choose an action"));
		    }
	}
	@Test
	void processSelectAction4Test() {
		 try (MockedStatic<InputReaderUtil> utilities = Mockito.mockStatic(InputReaderUtil.class)) {
			 AlphaController spy = Mockito.spy(AlphaController.class);
		        utilities.when(InputReaderUtil::readInt).thenReturn(4);
		        spy.processSelectAction(4);
		        Assert.assertTrue(outputStreamCaptor.toString().contains("Unsupported option. Please enter a number corresponding to the provided menu"));
		 }
	}
	@Test
	void processSelectAction1Test() {
		ParkingSpotDAO myParkingSpotDAOTest = new ParkingSpotDAO(); 
		TicketDAO myTicketDAOTest = new TicketDAO();
		ParkingService myParkingServiceTest = new ParkingService(myParkingSpotDAOTest, myTicketDAOTest);
		IncomingVehicleController myIncomingVehicleControllerTest = new IncomingVehicleController(myParkingServiceTest);
		try (MockedStatic<InputReaderUtil> utilities = Mockito.mockStatic(InputReaderUtil.class)) {
			AlphaController spy = Mockito.spy(AlphaController.class);
			utilities.when(InputReaderUtil::readInt).thenReturn(1);
	        spy.processSelectAction(4);
	        verify(spy, times(1).myIncomingVehicleControllerTest.runSelectVehicleType());
		}
	}
//	processSelectAction(int action)
//	@Test
//	public void selectionActionSystemOutPrintLNTest() {
//		myAlphaControllerTest.selectionAction();
//		Assert.assertEquals("Please select an option. Simply enter the number to choose an action", outputStreamCaptor.toString().trim());
//		Assert.assertEquals("1 New Vehicle Entering - Allocate Parking Space", outputStreamCaptor.toString().trim());
//		Assert.assertEquals("2 Vehicle Exiting - Generate Ticket Price", outputStreamCaptor.toString().trim());
//		Assert.assertEquals("3 Shutdown System", outputStreamCaptor.toString().trim());
//	}
//	/*Again I'm not sure I can unitest this case because the switch is not implemented to return a value but calls on an other methode insted.*/
//	@Test
//	public void processSelectActionIf1Test() {
//		myAlphaControllerTest.processSelectAction(1);
//		
//	}
//	@Test
//	public void processSelectActionIf2Test() {
//		myAlphaControllerTest.processSelectAction(2);
//		
//	}
//	@Test
//	public void processSelectActionIf3Test() {
//		myAlphaControllerTest.processSelectAction(3);
//		
//	}
//	/*I don't know how to test the fact that AlphaController.loadInterface() calls AlphaController.run()
//	 * Should I test it ? HOW ?*/
//	@Test
//	public void loadInterfaceIsMethodRunCalledTest1() {
//		AlphaController.loadInterface();
//		verify(myAlphaControllerTest).run();
//	}
//	@Test
//	public void loadInterfaceIsMethodRunCalledTest2() {
//		AlphaController.loadInterface();
//		verify(myAlphaControllerTest, times(1)).run();
//	}
//
//	/*-Examples found on the Internet for help-*/
//	@Test
//	void givenSystemOutRedirection_whenInvokePrintln_thenOutputCaptorSuccess() {
//	    print("Hello Baeldung Readers!!");
//	        
//	    Assert.assertEquals("Hello Baeldung Readers!!", outputStreamCaptor.toString()
//	      .trim());
//	}
//
//	private void print(String output) {
//	    System.out.println(output);
//	}

}
