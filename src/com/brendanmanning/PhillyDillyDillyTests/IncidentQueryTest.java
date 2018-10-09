package com.brendanmanning.PhillyDillyDillyTests;

import com.brendanmanning.PhillyDillyDilly.Coordinates;
import com.brendanmanning.PhillyDillyDilly.DateTime;
import com.brendanmanning.PhillyDillyDilly.Incident;
import com.brendanmanning.PhillyDillyDilly.IncidentQuery;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * PDD
 * Copyright 2018 Brendan Manning. All rights reserved.
 */
class IncidentQueryTest {

    @Test
    void isWithinRadius() {

        Incident incident = new Incident("0101000020E6100000447E479E35C552C076963005B1074440,-75.0813976,121,02,3,2015-06-22 21:13:00,2015-06-22,21:13:00,0101000020110F00004C70BABC21E25FC1C0F4E28C6D985241,201502040130,1100 BLOCK COTTMAN AVE,600,Thefts,-75.0813976,40.06008973,40.06008973,21");

        IncidentQuery incidentQuery1 = new IncidentQuery();
        incidentQuery1.setRadius(1000);
        incidentQuery1.setCenterCoordinate(new Coordinates(39.979931,-75.153562));
        assertFalse(incidentQuery1.isWithinRadius(incident));

        IncidentQuery incidentQuery2 = new IncidentQuery();
        incidentQuery2.setRadius(1000);
        incidentQuery2.setCenterCoordinate(new Coordinates(40.054908,-75.090892));
        assertTrue(incidentQuery2.isWithinRadius(incident));

    }

    @Test
    void isWithinDateRange() {

        Incident incident = new Incident("0101000020E6100000447E479E35C552C076963005B1074440,-75.0813976,121,02,3,2015-06-22 21:13:00,2015-06-22,21:13:00,0101000020110F00004C70BABC21E25FC1C0F4E28C6D985241,201502040130,1100 BLOCK COTTMAN AVE,600,Thefts,-75.0813976,40.06008973,40.06008973,21");

        IncidentQuery incidentQuery1 = new IncidentQuery();
        incidentQuery1.setStartTime(new DateTime(new Date(1435021979L * 1000)));
        assertTrue(incidentQuery1.isWithinDateRange(incident));

        IncidentQuery incidentQuery2 = new IncidentQuery();
        incidentQuery2.setStartTime(new DateTime(new Date(1435021981L * 1000)));
        assertFalse(incidentQuery2.isWithinDateRange(incident));
    }

}