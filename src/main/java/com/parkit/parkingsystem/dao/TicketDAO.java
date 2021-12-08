package com.parkit.parkingsystem.dao;

import com.parkit.parkingsystem.config.DataBaseConfig;
import com.parkit.parkingsystem.constants.DBConstants;
import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.util.DateHelperUtil;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;


public class TicketDAO {

    private static final Logger logger = LogManager.getLogger("TicketDAO");

    public static DataBaseConfig dataBaseConfig = new DataBaseConfig();

    public boolean saveTicket(Ticket ticket){
        Connection saveTicketConnection = null;
        try {
            saveTicketConnection = dataBaseConfig.getConnection();
            PreparedStatement querrySaveTicket = saveTicketConnection.prepareStatement(DBConstants.SAVE_TICKET);
            //ID, PARKING_NUMBER, VEHICLE_REG_NUMBER, PRICE, IN_TIME, OUT_TIME)
            //querrySaveTicket.setInt(1,ticket.getId());
            querrySaveTicket.setInt(1,ticket.getParkingSpot().getId());
            querrySaveTicket.setString(2, ticket.getVehicleRegNumber());
            querrySaveTicket.setDouble(3, ticket.getPrice());
            querrySaveTicket.setTimestamp(4, DateHelperUtil.convertLocalDateTimeToTimestamp(ticket.getInTime()));
            querrySaveTicket.setTimestamp(5, null);
            return querrySaveTicket.execute();
        }catch (Exception ex){
            logger.error("Error fetching next available slot",ex);
        }finally {
            dataBaseConfig.closeConnection(saveTicketConnection);
        
        }
        return false;
    }

    public static Ticket getTicket(String vehicleRegNumber) {
        Connection getTicketConnection = null;
        Ticket ticket = null;
        try {
            getTicketConnection = dataBaseConfig.getConnection();
            PreparedStatement querryGetTicket = getTicketConnection.prepareStatement(DBConstants.GET_TICKET);
            //ID, PARKING_NUMBER, VEHICLE_REG_NUMBER, PRICE, IN_TIME, OUT_TIME)
            querryGetTicket.setString(1,vehicleRegNumber);
            ResultSet ticketFromDatabase = querryGetTicket.executeQuery();
            if(ticketFromDatabase.next()){
                ticket = new Ticket();
                ParkingSpot parkingSpot = new ParkingSpot(ticketFromDatabase.getInt(1), ParkingType.valueOf(ticketFromDatabase.getString(6)),false);
                ticket.setParkingSpot(parkingSpot);
                ticket.setId(ticketFromDatabase.getInt(2));
                ticket.setVehicleRegNumber(vehicleRegNumber);
                ticket.setPrice(ticketFromDatabase.getDouble(3));
                ticket.setInTime(DateHelperUtil.convertTimestampsToLocalDateTime(ticketFromDatabase.getTimestamp(4)));
                ticket.setOutTime(DateHelperUtil.convertTimestampsToLocalDateTime(ticketFromDatabase.getTimestamp(5)));
            }
            dataBaseConfig.closeResultSet(ticketFromDatabase);
            dataBaseConfig.closePreparedStatement(querryGetTicket);
        }catch (Exception ex){
            logger.error("Error fetching next available slot",ex);
        }finally {
            dataBaseConfig.closeConnection(getTicketConnection);
           
        }
        return ticket;
    }

    public boolean updateTicket(Ticket ticket) {
        Connection updateTicketConnexion = null;
        try {
            updateTicketConnexion = dataBaseConfig.getConnection();
            PreparedStatement querryUpdateTicket = updateTicketConnexion.prepareStatement(DBConstants.UPDATE_TICKET);
            querryUpdateTicket.setDouble(1, ticket.getPrice());
            querryUpdateTicket.setTimestamp(2, DateHelperUtil.convertLocalDateTimeToTimestamp(ticket.getOutTime()));
            querryUpdateTicket.setInt(3,ticket.getId());
            querryUpdateTicket.execute();
            return true;
        }catch (Exception ex){
            logger.error("Error saving ticket info",ex);
        }finally {
            dataBaseConfig.closeConnection(updateTicketConnexion);
        }
        return false;
    }
    public int getTicketIfVehiculeAlreadyInside(String vehicleRegNumber, ParkingType parkingType){
        Connection con = null;
        int result =0;
        try {
            con = dataBaseConfig.getConnection();
            PreparedStatement ps = con.prepareStatement(DBConstants.GET_NUMBER_OF_TICKETS_WHERE_VREG_TYPE);
            //After the Where in quarry replace the xth parametter or ?
            ps.setString(1, vehicleRegNumber.toString());
            ps.setString(2, parkingType.toString());
            ResultSet ticketsFromDatabase = ps.executeQuery();
            // get only the first line of the result of the querry
            if(ticketsFromDatabase.next()){
                System.out.println("getTicketIfVehiculeAlreadyInside - " + ticketsFromDatabase.getString(1));
                System.out.println("getTicketIfVehiculeAlreadyInside - " + ticketsFromDatabase);
                result = ticketsFromDatabase.getInt(1); 
            }
            //close execution of the querry wich is the 'array' returned by the querry
            dataBaseConfig.closeResultSet(ticketsFromDatabase);
            //close the prepared statement or link to db
            dataBaseConfig.closePreparedStatement(ps);
        }catch (Exception ex){
            logger.error("Error fetching number of available slot for "+ parkingType.toString() +" ",ex);
        }finally {
            dataBaseConfig.closeConnection(con);
        }
        System.out.println("getTicketIfVehiculeAlreadyInside" + vehicleRegNumber+" - "+ parkingType +" result "+result);
        return result;
    }


}
