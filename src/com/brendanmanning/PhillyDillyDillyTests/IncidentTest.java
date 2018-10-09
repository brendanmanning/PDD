package com.brendanmanning.PhillyDillyDillyTests;

/**
 * PDD
 * Copyright 2018 Brendan Manning. All rights reserved.
 */

import com.brendanmanning.PhillyDillyDilly.Coordinates;
import com.brendanmanning.PhillyDillyDilly.DateTime;
import com.brendanmanning.PhillyDillyDilly.Incident;
import com.brendanmanning.PhillyDillyDilly.MalformedIncidentException;

import static org.junit.jupiter.api.Assertions.*;

class IncidentTest {

    @org.junit.jupiter.api.Test
    void constructor() {

        // Case: All good
        try {
            Incident incident = new Incident("0101000020E6100000447E479E35C552C076963005B1074440,-75.0813976,121,02,3,2015-06-22 21:13:00,2015-06-22,21:13:00,0101000020110F00004C70BABC21E25FC1C0F4E28C6D985241,201502040130,1100 BLOCK COTTMAN AVE,600,Thefts,-75.0813976,40.06008973,40.06008973,21");
            assertTrue(true);
        } catch (MalformedIncidentException mie){
            assertTrue(false);
        }

        // Case: null input
        try {
            Incident incident = new Incident(null);
            assertTrue(false);
        } catch (MalformedIncidentException mie){
            assertTrue(true);
        }

        // Case: empty input
        try {
            Incident incident = new Incident("");
            assertTrue(false);
        } catch (MalformedIncidentException mie){
            assertTrue(true);
        }

        // Case: Too few properties (no commas)
        try {
            Incident incident = new Incident(" hi.");
            assertTrue(false);
        } catch (MalformedIncidentException mie){
            assertTrue(true);
        }

        // Case: Too few properties (with commas)
        try {
            Incident incident = new Incident("0101000020E6100000447E479E35C552C076963005B1074440,-75.0813976,121,02,3,2015-06-22 21:13:00,");
            assertTrue(false);
        } catch (MalformedIncidentException mie){
            assertTrue(true);
        }

        // Case: Latitude malformed (string)
        try {
            Incident incident = new Incident("0101000020E6100000447E479E35C552C076963005B1074440,-75.0813976,121,02,3,2015-06-22 21:13:00,2015-06-22,21:13:00,0101000020110F00004C70BABC21E25FC1C0F4E28C6D985241,201502040130,1100 BLOCK COTTMAN AVE,600,Thefts,-75.0813976,40.06008973,should make it fail,21");
            assertTrue(false);
        } catch (MalformedIncidentException mie){
            assertTrue(true);
        }

        // Case: Longitude malformed (string)
        try {
            Incident incident = new Incident("0101000020E6100000447E479E35C552C076963005B1074440,should make it fail,121,02,3,2015-06-22 21:13:00,2015-06-22,21:13:00,0101000020110F00004C70BABC21E25FC1C0F4E28C6D985241,201502040130,1100 BLOCK COTTMAN AVE,600,Thefts,-75.0813976,40.06008973,40.06008973,21");
            assertTrue(false);
        } catch (MalformedIncidentException mie){
            assertTrue(true);
        }

        // Case: Date format no match (added dash)
        try {
            Incident incident = new Incident("0101000020E6100000447E479E35C552C076963005B1074440,-75.0813976,121,02,3,2015-06-22-21:13:00,2015-06-22,21:13:00,0101000020110F00004C70BABC21E25FC1C0F4E28C6D985241,201502040130,1100 BLOCK COTTMAN AVE,600,Thefts,-75.0813976,40.06008973,40.06008973,21");
            assertTrue(false);
        } catch (MalformedIncidentException mie){
            assertTrue(true);
        }

        // Case: Crime code invalid (string)
        try {
            Incident incident = new Incident("0101000020E6100000447E479E35C552C076963005B1074440,-75.0813976,121,02,3,2015-06 22-21:13:00,2015-06-22,21:13:00,0101000020110F00004C70BABC21E25FC1C0F4E28C6D985241,201502040130,1100 BLOCK COTTMAN AVE,hi,Thefts,-75.0813976,40.06008973,40.06008973,21");
            assertTrue(false);
        } catch (MalformedIncidentException mie){
            assertTrue(true);
        }

    }


    @org.junit.jupiter.api.Test
    void getCoordinates() {
        try {
            Incident incident = new Incident("0101000020E6100000447E479E35C552C076963005B1074440,-75.0813976,121,02,3,2015-06-22 21:13:00,2015-06-22,21:13:00,0101000020110F00004C70BABC21E25FC1C0F4E28C6D985241,201502040130,1100 BLOCK COTTMAN AVE,600,Thefts,-75.0813976,40.06008973,40.06008973,21");
            assertEquals(40.06008973, incident.getCoordinates().getLat());
            assertEquals(-75.0813976, incident.getCoordinates().getLon());
        } catch (MalformedIncidentException mie) {
            assertTrue(false);
        }
    }

    @org.junit.jupiter.api.Test
    void setCoordinates() {
        try {
            Incident incident = new Incident("0101000020E6100000447E479E35C552C076963005B1074440,-75.0813976,121,02,3,2015-06-22 21:13:00,2015-06-22,21:13:00,0101000020110F00004C70BABC21E25FC1C0F4E28C6D985241,201502040130,1100 BLOCK COTTMAN AVE,600,Thefts,-75.0813976,40.06008973,40.06008973,21");
            incident.setCoordinates(new Coordinates(1.0,2.0));

            assertEquals(1.0, incident.getCoordinates().getLat());
            assertEquals(2.0, incident.getCoordinates().getLon());
        } catch (MalformedIncidentException mie){
            assertTrue(false);
        }
    }

    @org.junit.jupiter.api.Test
    void getDate() {
        try {
            Incident incident = new Incident("0101000020E6100000447E479E35C552C076963005B1074440,-75.0813976,121,02,3,2015-06-22 21:13:00,2015-06-22,21:13:00,0101000020110F00004C70BABC21E25FC1C0F4E28C6D985241,201502040130,1100 BLOCK COTTMAN AVE,600,Thefts,-75.0813976,40.06008973,40.06008973,21");
            assertTrue((incident.getDate().getTime() / 1000) == 1435021980);
        } catch (MalformedIncidentException mie){
            assertTrue(false);
        }
    }

    @org.junit.jupiter.api.Test
    void setDate() {
        try {
            Incident incident = new Incident("0101000020E6100000447E479E35C552C076963005B1074440,-75.0813976,121,02,3,2015-06-22 21:13:00,2015-06-22,21:13:00,0101000020110F00004C70BABC21E25FC1C0F4E28C6D985241,201502040130,1100 BLOCK COTTMAN AVE,600,Thefts,-75.0813976,40.06008973,40.06008973,21");
            incident.setDate(new DateTime(12345000));
            assertTrue((incident.getDate().getTime() / 1000) == 12345);
        } catch (MalformedIncidentException mie){
            assertTrue(false);
        }
    }

    @org.junit.jupiter.api.Test
    void getDescription() {
        Incident incident = new Incident("0101000020E6100000447E479E35C552C076963005B1074440,-75.0813976,121,02,3,2015-06-22 21:13:00,2015-06-22,21:13:00,0101000020110F00004C70BABC21E25FC1C0F4E28C6D985241,201502040130,1100 BLOCK COTTMAN AVE,600,Thefts,-75.0813976,40.06008973,40.06008973,21");
        assertTrue(incident.getDescription().equals("Thefts"));
    }

    @org.junit.jupiter.api.Test
    void setDescription() {
        Incident incident = new Incident("0101000020E6100000447E479E35C552C076963005B1074440,-75.0813976,121,02,3,2015-06-22 21:13:00,2015-06-22,21:13:00,0101000020110F00004C70BABC21E25FC1C0F4E28C6D985241,201502040130,1100 BLOCK COTTMAN AVE,600,Thefts,-75.0813976,40.06008973,40.06008973,21");
        incident.setDescription("Test");
        assertTrue(incident.getDescription().equals("Test"));
    }

    @org.junit.jupiter.api.Test
    void getCode() {
        Incident incident = new Incident("0101000020E6100000447E479E35C552C076963005B1074440,-75.0813976,121,02,3,2015-06-22 21:13:00,2015-06-22,21:13:00,0101000020110F00004C70BABC21E25FC1C0F4E28C6D985241,201502040130,1100 BLOCK COTTMAN AVE,600,Thefts,-75.0813976,40.06008973,40.06008973,21");
        assertEquals(incident.getCode(), 600);
    }

    @org.junit.jupiter.api.Test
    void setCode() {
        Incident incident = new Incident("0101000020E6100000447E479E35C552C076963005B1074440,-75.0813976,121,02,3,2015-06-22 21:13:00,2015-06-22,21:13:00,0101000020110F00004C70BABC21E25FC1C0F4E28C6D985241,201502040130,1100 BLOCK COTTMAN AVE,600,Thefts,-75.0813976,40.06008973,40.06008973,21");
        incident.setCode(610);
        assertEquals(incident.getCode(), 610);
    }

}