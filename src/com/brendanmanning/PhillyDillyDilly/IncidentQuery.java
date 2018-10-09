package com.brendanmanning.PhillyDillyDilly;

/**
 * PDD
 * Copyright 2018 Brendan Manning. All rights reserved.
 */
public class IncidentQuery {

    private boolean isRadiusFilterEnabled = false;
    private boolean isDateFilterEnabled = false;

    private Coordinates centerCoordinate = null;
    private double radius = 1000; // 1 km = 1000m

    private DateTime startTime = null;

    public void setRadiusFilterEnabled(boolean enabled) {
        this.isDateFilterEnabled = enabled;
    }
    public boolean isRadiusFilterEnabled() {
        return this.isRadiusFilterEnabled;
    }

    public void setDateFilterEnabled(boolean enabled) {
        this.isDateFilterEnabled = true;
    }
    public boolean isDateFilterEnabled() {
        return this.isDateFilterEnabled;
    }

    public void setCenterCoordinate(Coordinates c) {
        this.centerCoordinate = c;
    }
    public void setRadius(double r) {
        this.radius = r;
    }
    public void setStartTime(DateTime dt) {
        this.startTime = dt;
    }

    public boolean isWithinRadius(Incident i) {
        if(centerCoordinate == null) {
            return true;
        }
        return i.getDistance(centerCoordinate) <= this.radius;
    }

    public boolean isWithinDateRange(Incident i) {
        if(this.startTime == null) {
            return true;
        }
        return i.getDate().getTime() - this.startTime.getTime() >= 0;
    }
}
