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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class TicketDAO {

    private static final Logger logger = LogManager.getLogger("TicketDAO");

    public DataBaseConfig dataBaseConfig = new DataBaseConfig();

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
            return false;
        }
    }

    public Ticket getTicket(String vehicleRegNumber) {
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
            return ticket;
        }
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



}
