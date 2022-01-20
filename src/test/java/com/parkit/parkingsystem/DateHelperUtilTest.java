package com.parkit.parkingsystem;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import com.parkit.parkingsystem.util.DateHelperUtil;
/**
 * @author Muriel Proton
 * Test in class DateHelperUtil
 * run with success the 20 01 2022 at 17h29
 */
public class DateHelperUtilTest {
	LocalDateTime inTime = LocalDateTime.now();
	LocalDateTime outTime = LocalDateTime.now().plusMinutes(50);
	Duration duration = Duration.ofMinutes(50);
    /**
     * @author Muriel Proton
     * Test in class DateHelperUtil the method findLengthOfTimeBetweenTwoLocalDateTimes(LocalDateTime,LocalDateTime)
     */
    @Test
    public void findLengthOfTimeBetweenTwoLocalDateTimesTest() {
    	Duration realDuration = DateHelperUtil.findLengthOfTimeBetweenTwoLocalDateTimes(inTime, outTime);
    	assertEquals( duration.toMinutes(), realDuration.toMinutes());	
    }
    /**
     * @author Muriel Proton
     * Test in class DateHelperUtil the method transformDurationIntoDouble(Duration)
     */
    @Test
    public void transformDurationIntoDoubleTest() {
    	//(double)duration.toMinutes()/60 = 0.8333333333333334
    	Double exptectedDouble = 0.8333333333333334;
    	Double realDouble = DateHelperUtil.transformDurationIntoDouble(duration);
    	assertEquals( exptectedDouble, realDouble);	
    }
    /**
     * @author Muriel Proton
     * Test in class DateHelperUtil the method convertLocalDateTimeToTimestamp(LocalDateTime)
     */
    @Test
    public void convertLocalDateTimeToTimestampTest() {
    	Timestamp expectedTimestamp = Timestamp.valueOf(inTime);
    	Timestamp realTimestamp = DateHelperUtil.convertLocalDateTimeToTimestamp(inTime);
    	assertEquals( expectedTimestamp, realTimestamp);
    }
    /**
     * @author Muriel Proton
     * Test in class DateHelperUtil the method convertLocalDateTimeToTimestamp(null)
     */
    @Test
    public void convertLocalDateTimeToTimestampNullTest() {
    	Timestamp realTimestamp = DateHelperUtil.convertLocalDateTimeToTimestamp(null);
    	assertEquals( null, realTimestamp);
    }
    /**
     * @author Muriel Proton
     * Test in class DateHelperUtil the method convertTimestampsToLocalDateTime(Timestamp)
     */
    @Test
    public void convertTimestampsToLocalDateTimeTest() {
    	Timestamp aTimestampToTest = Timestamp.valueOf(inTime);
    	LocalDateTime realLocalDateTime = DateHelperUtil.convertTimestampsToLocalDateTime(aTimestampToTest);
    	assertEquals( inTime, realLocalDateTime);
    }
    /**
     * @author Muriel Proton
     * Test in class DateHelperUtil the method convertTimestampsToLocalDateTime(null)
     */
    @Test
    public void convertTimestampsToLocalDateTimeNullTest() {
    	Timestamp aTimestampToTest = null;
    	LocalDateTime realLocalDateTime = DateHelperUtil.convertTimestampsToLocalDateTime(aTimestampToTest);
    	assertEquals( null, realLocalDateTime);
    }

}
