package com.parkit.parkingsystem;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import com.parkit.parkingsystem.controller.AlphaController;
import com.parkit.parkingsystem.util.InputReaderUtil;

public class AlphaControllerTest {
	@Test
    public void  myTestAlphaControllerExistWithoutInteraction() {
    	
    	//GIVEN
    	// Setup
    	//WHEN
    	// appel à ce qu'il faut tester
    	//THEN
    	// Controle.
    	try (MockedStatic<InputReaderUtil> mockedInput = Mockito.mockStatic(InputReaderUtil.class)) {
//    		//Pour tester l'AlphaController
    		//1- faire la liste de tous les input que l'application va demander
    		//2- appeler AlphaController.loadInterface();
    		//3- faire ls assert equals qui vont bien
    		//comme fait dans l'exemple ci après
    		//NB dans le cas où il y a plus de InputReaderUtil.readInt() ou readNextLine()
    		// que de thenReturn 
    		// le InputReaderUtil retournera la valeur du dernier thenReturn. 
    		// dans le cas présent "call3"
    		//mockedInput.when(InputReaderUtil::readNextLine)
//    		.thenReturn("call1")
//    		.thenReturn("call2")
//    		.thenReturn("call3");
    		mockedInput.when(InputReaderUtil::readInt)
    		
    		.thenReturn(3);
    		// Les appels qui vons être fait par le test
    		AlphaController.loadInterface();
//    		System.out.println(InputReaderUtil.readInt());
//    		System.out.println(InputReaderUtil.readNextLine());
//    		System.out.println(InputReaderUtil.readNextLine());
//    		System.out.println(InputReaderUtil.readNextLine());
//    		System.out.println(InputReaderUtil.readInt());
//    		System.out.println(InputReaderUtil.readNextLine());
//    		System.out.println(InputReaderUtil.readNextLine());
    	}
    }
}
