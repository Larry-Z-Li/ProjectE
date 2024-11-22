package com.example.projecte;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class sessionTimingTest {

    @Test
    public void validTimes() {
        assertTrue(validTimes("11:00", "12:00"));
    }

    @Test
    public void validTimesLarger() {
        assertTrue(validTimes("17:00", "19:00"));
    }

    @Test
    public void notValidTimes() {
        assertFalse(validTimes("6:00", "4:00"));
    }

    @Test
    public void notValidTimesLarger() {
        assertFalse(validTimes("16:00", "14:00"));
    }

    @Test
    public void notValidTimesEmpty() {
        assertFalse(validTimes("16:00", ""));
    }

    @Test
    public void incorrectFormat() {
        assertFalse(validTimes("hello", "11:00"));
    }

    public boolean validTimes(String startTime, String endTime) {

        try {
            LocalTime start = LocalTime.parse(startTime);
            LocalTime end = LocalTime.parse(endTime);

            if (end.isBefore(start)) {
                return false;
            }
        } catch (DateTimeParseException e) {
            return false;
        }

        return true;
    }

}
