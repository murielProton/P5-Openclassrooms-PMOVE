package com.parkit.parkingsystem.dao;

import com.parkit.parkingsystem.config.DataBaseConfig;
import com.parkit.parkingsystem.constants.DBConstants;
import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.model.ParkingSpot;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Muriel Proton
 *
 */
public class ParkingSpotDAO {
    private static final Logger logger = LogManager.getLogger("ParkingSpotDAO");
    public static DataBaseConfig dataBaseConfig = new DataBaseConfig();

    /**
     * Used in Class ParkingService by Method : getNextParkingNumberIfAvailable(ParkingType)
     * @param ParkingType
     * @return INTEGER
     */
    public int getNextAvailableSlot(ParkingType parkingType){
        Connection con = null;
        int result=-1;
        try {
            con = dataBaseConfig.getConnection();
            PreparedStatement ps = con.prepareStatement(DBConstants.GET_NEXT_PARKING_SPOT);
            ps.setString(1, parkingType.toString());
            ResultSet parkingSpotResult = ps.executeQuery();
            if(parkingSpotResult.next()){
                result = parkingSpotResult.getInt(1);;
            }
            dataBaseConfig.closeResultSet(parkingSpotResult);
            dataBaseConfig.closePreparedStatement(ps);
        }catch (Exception ex){
            logger.error("Error fetching next available slot",ex);
        }finally {
            dataBaseConfig.closeConnection(con);
        }
        return result;
    }

    /**
     * Used in Class ParkingService by Method : fillParkingSpot(ParkingSpot) & emptyParkingSpot(ParkingSpot)
     * Used in Class PayementService by Method updateTicketAndParkingSpotAfterPayement(Ticket)
     * @param parkingSpot
     * @return BOOLEAN
     */
    public boolean updateParking(ParkingSpot parkingSpot){
        //update the availability for that parking slot
        Connection con = null;
        try {
            con = dataBaseConfig.getConnection();
            PreparedStatement ps = con.prepareStatement(DBConstants.UPDATE_PARKING_SPOT);
            ps.setBoolean(1, parkingSpot.isAvailable());
            ps.setInt(2, parkingSpot.getId());
            int updateRowCount = ps.executeUpdate();
            dataBaseConfig.closePreparedStatement(ps);
            return (updateRowCount == 1);
        }catch (Exception ex){
            logger.error("Error updating parking info",ex);
            return false;
        }finally {
        	dataBaseConfig.closeConnection(con);
        }
    }
    /**
     * Used in Class ParkingService by Method : isThereAParkingSpotForAnyType()
     * @return INTEGER
     */
    public int getTotalNumberOfAvailableSlot(){
        Connection con = null;
        int result=0;
        try {
            con = dataBaseConfig.getConnection();
            PreparedStatement ps = con.prepareStatement(DBConstants.GET_NUMBER_OF_AVAILABLE_PARKING_SPOT);
            ResultSet parkingSpotResult = ps.executeQuery();
            // get only the first line
            if(parkingSpotResult.next()){
                result = parkingSpotResult.getInt(1);
            }

            dataBaseConfig.closeResultSet(parkingSpotResult);
            dataBaseConfig.closePreparedStatement(ps);
        }catch (Exception ex){
            logger.error("Error fetching number of available slot",ex);
        }finally {
            dataBaseConfig.closeConnection(con);
        }
        return result;
    }
    /**
     * Used in Class ParkingService by Method : isThereAParkingSpotForType(ParkingType)
     * @param ParkingType
     * @return INTEGER
     */
    public int getNumberOfAvailableSlotForType(ParkingType parkingType){
        Connection con = null;
        int result=0;
        try {
            con = dataBaseConfig.getConnection();
            PreparedStatement ps = con.prepareStatement(DBConstants.GET_NUMBER_OF_AVAILABLE_PARKING_SPOT_TYPE);
            //After the Where in quarry replace the xth parametter or ?
            ps.setString(1, parkingType.toString());
            ResultSet parkingSpotResult = ps.executeQuery();
            // get only the first line of the result of the querry
            if(parkingSpotResult.next()){
                //get value of the querry - 1 stands for the parameter number after the SELECT
                result = parkingSpotResult.getInt(1);
                //close execution of the querry wich is the 'array' returned by the querry
            }
            dataBaseConfig.closeResultSet(parkingSpotResult);
            //close the prepared statement or link to db
            dataBaseConfig.closePreparedStatement(ps);
        }catch (Exception ex){
            logger.error("Error fetching number of available slot for "+ parkingType.toString() +" ",ex);
        }finally {
            dataBaseConfig.closeConnection(con);
        }
        return result;
    }
}
