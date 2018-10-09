package com.brendanmanning.PhillyDillyDilly;/**
 * PDD
 * <p>
 * Copyright 2018 Brendan Manning. All rights reserved.
 */

import java.util.*;
import java.io.*;
import java.nio.*;

public class DateTime extends Date {

    public DateTime() {
        super();
    }

    public DateTime(int ms) {
        super(ms);
    }

    public DateTime(Date d) {
        super(d.toInstant().toEpochMilli());
    }

    public int year() {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(this);
        return calendar.get(Calendar.YEAR);
    }
    public int month() {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(this);
        return calendar.get(Calendar.MONTH) + 1;
    }
    public int day() {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(this);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }
}
