package com.brendanmanning.PhillyDillyDilly;

import java.util.ArrayList;
import java.util.List;

/**
 * PDD
 * Copyright 2018 Brendan Manning. All rights reserved.
 */
public class Grid {

    private Coordinates topLeft = null;

    private double height = -1;
    private double width = -1;

    private List<Incident> incidents = new ArrayList<Incident>();

    public Grid(Coordinates topLeft, double height, double width) {
        this.topLeft = topLeft;
        this.height = height;
        this.width = width;
    }

    public Coordinates getTopLeft() {
        return this.topLeft;
    }

    public double getHeight() {
        return this.height;
    }

    public double getWidth() {
        return this.width;
    }

    public void addIncident(Incident incident) {
        this.incidents.add(incident);
    }

    public List<Incident> getIncidents() {
        return this.incidents;
    }

    public int numberOfIncidents() {
        return incidents.size();
    }

    public boolean contains(Incident incident) {
        return contains(incident.coordinates);
    }

    public boolean contains(Coordinates coordinates) {
        return (height > Math.abs(topLeft.getLat() - coordinates.getLat())) && (width > Math.abs(topLeft.getLon() - coordinates.getLon()));
    }

}
