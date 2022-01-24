package com.parkit.parkingsystem;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Scanner;

import org.junit.jupiter.api.Test;

import com.parkit.parkingsystem.util.InputReaderUtil;

public class InputReaderUtilTest {
	/*String stringTotestNull;
	String stringLengthZero = "";
	String stringLength20 = "AAa-00c0-ArA2571-edrt";
	int intToTestZero = 0;
	int inToTestNull;
	int intToTestTreeHundreds = 300;*/
	//private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
   /* @Test
    public void readNextLineTest() throws Exception {
        assertThrows(IllegalArgumentException.class, () -> {
            InputReaderUtil.readNextLine();
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            InputReaderUtil.readInt();
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            InputReaderUtil.readNextLine();
        });
        String plateToTest = InputReaderUtil.readNextLine();
        assertEquals("AA-000-AA", plateToTest);

    }
	@Test
	public 
		 String userInput = String.format("Dan%sVega%sdanvega@gmail.com",
		            System.lineSeparator(),
		            System.lineSeparator());
		    ByteArrayInputStream bais = new ByteArrayInputStream(userInput.getBytes());
		    System.setIn(bais);

		    String expected = "Dan,Vega,danvega@gmail.com";
		    ByteArrayOutputStream baos = new ByteArrayOutputStream();
		    PrintStream printStream = new PrintStream(baos);
		    System.setOut(printStream);
		 //WHEN
		 String receivedInput = InputReaderUtil.readNextLine();
		 //VERIFY
		 Assert.assertEquals(expected,receivedInput);
		 
	}
	@BeforeEach
	public void setUp() {
		 Scanner in = new Scanner(System.in);
	}

	@Test
	public void readNextLineTest() {
		 //WHEN
		 String receivedInput = InputReaderUtil.readNextLine();
		 String expected = in.nextLine("toto");
		 //VERIFY
		 Assert.assertEquals(expected,receivedInput);
	}
	@BeforeAll
	public void setUp() {
	}
	@Test
	void readNextLineTest(){
		 try (MockedStatic<InputReaderUtil> utilities = Mockito.mockStatic(InputReaderUtil.class)) {

				AlphaController spy = Mockito.spy(AlphaController.class);
				spy.setWorking(true);
			 utilities.when(InputReaderUtil::readInt).thenReturn(3);
		        AlphaController.loadInterface();
		        Assert.assertEquals(3, utilities.when(InputReaderUtil::readInt).thenReturn(3));
		    }
	}
	@Test
	public void readNextLineTest(){
		String expected = "AAa-00c0-ArA2571-edrt";
		String realOutput = InputReaderUtil.readNextLine();//Waiting upon response from console
		System.out.println(expected);//this should be a mock of the println and not the real thing
		Assert.assertEquals(expected,realOutput);
	}
	@Test
	public void readNextLineTest(){
		String expected = "AAa-00c0-ArA2571-edrt";
		String realOutput = InputReaderUtil.readNextLine();//Waiting upon response from console
		System.out.println(expected);//this should be a mock of the println and not the real thing
		Assert.assertEquals(expected,realOutput);
	}*/
	@Test
	public void readNextLineTest(){
		InputReaderUtil bar = mock(InputReaderUtil.class);
		Scanner scan = new Scanner(System.in);
		bar.readNextLine();
	verify(bar, times(1)).scan.nextLine();
	}
}
