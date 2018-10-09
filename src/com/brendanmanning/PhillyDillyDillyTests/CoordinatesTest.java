package com.brendanmanning.PhillyDillyDillyTests;

import com.brendanmanning.PhillyDillyDilly.Coordinates;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * PDD
 * Copyright 2018 Brendan Manning. All rights reserved.
 */

class CoordinatesTest {

    @Test
    void getLat() {
        Coordinates coordinates = new Coordinates(123,456);
        assertEquals(123,coordinates.getLat());
    }

    @Test
    void setLat() {
        Coordinates coordinates = new Coordinates(123,456);
        coordinates.setLat(888);
        assertEquals(888, coordinates.getLat());
    }

    @Test
    void getLon() {
        Coordinates coordinates = new Coordinates(123,456);
        assertEquals(456, coordinates.getLon());
    }

    @Test
    void setLon() {
        Coordinates coordinates = new Coordinates(123,456);
        coordinates.setLon(999);
        assertEquals(999, coordinates.getLon());
    }

}