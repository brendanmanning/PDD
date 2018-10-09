package com.brendanmanning.PhillyDillyDillyTests;

import com.brendanmanning.PhillyDillyDilly.DateTime;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class DateTimeTest {
    @Test
    void year() {
        DateTime datetime = new DateTime(244228244);
        assertEquals(datetime.year(), 1970);
    }

    @Test
    void month() {
        DateTime datetime = new DateTime(244228244);
        assertEquals(datetime.month(), 1);
    }

    @Test
    void day() {
        DateTime datetime = new DateTime(244228244);
        assertEquals(datetime.day(), 3);
    }

}