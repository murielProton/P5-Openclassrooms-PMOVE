package com.parkit.parkingsystem.constants;

public class DBConstants {

    public static final String GET_NEXT_PARKING_SPOT = "select min(PARKING_NUMBER) from parking where AVAILABLE = true and TYPE = ?";
    public static final String GET_PARKING_SPOT_OF_EXITING_VEHICLE = "select PARKING_NUMBER, AVAILABLE, TYPE from parking where PARKING_NUMBER = ?";
    public static final String GET_NUMBER_OF_AVAILABLE_PARKING_SPOT = "select count(*) from parking where AVAILABLE = true";
    public static final String GET_NUMBER_OF_AVAILABLE_PARKING_SPOT_TYPE = "select count(*) from parking where AVAILABLE = true and TYPE = ?";
    public static final String UPDATE_PARKING_SPOT = "update parking set available = ? where PARKING_NUMBER = ?";

    public static final String SAVE_TICKET = "insert into ticket(PARKING_NUMBER, VEHICLE_REG_NUMBER, PRICE, IN_TIME, OUT_TIME) values(?,?,?,?,?)";
    public static final String UPDATE_TICKET = "update ticket set PRICE=?, OUT_TIME=? where ID=?";
    public static final String UPDATE_OUT_TIME_OF_TICKET = "update ticket set OUT_TIME=? where ID=?";
    public static final String UPDATE_PRICE_OF_TICKET = "update ticket set PRICE=? where ID=?";
    public static final String GET_TICKET = 
    		"select t.PARKING_NUMBER, t.ID, t.PRICE, t.IN_TIME, t.OUT_TIME, p.TYPE from ticket t,parking p "
    		+ "where p.parking_number = t.parking_number "
    		+ "and t.VEHICLE_REG_NUMBER=? order by t.IN_TIME  limit 1";
    public static final String GET_EXITING_VEHICUL_TICKET = 
    		"select t.PARKING_NUMBER, t.ID, t.PRICE, t.IN_TIME, t.OUT_TIME, p.TYPE from ticket t,parking p "
    		+ "where p.parking_number = t.parking_number "
    		+ "and t.OUT_TIME is null and t.VEHICLE_REG_NUMBER=? order by t.IN_TIME limit 1";
    public static final String GET_NUMBER_OF_TICKETS_WHERE_VREG_TYPE = "select count(*) from ticket t, parking p where t.VEHICLE_REG_NUMBER=? and p.TYPE=? and t.OUT_TIME IS NULL";
	
}
