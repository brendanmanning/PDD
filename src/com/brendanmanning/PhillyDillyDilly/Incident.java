package com.brendanmanning.PhillyDillyDilly;
/**
 * PDD
 * <p>
 * Copyright 2018 Brendan Manning. All rights reserved.
 */

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Incident
{

    /**
     * Creates an incident object from a CSV row
     * @param str CSV row to parse incident from
     * @throws MalformedIncidentException
     */
    public Incident(String str) throws MalformedIncidentException {

        // Null/Empty check
        if (str == null || str.length() == 0) {
            throw new MalformedIncidentException("The incident was " + ((str == null) ? "a null" : "an empty") + " string.");
        }

        // Is the input the right size?
        String[] components = str.split(",");
        if(components.length != 17) {
            throw new MalformedIncidentException(str + "\n(There were not 17 CSV columns)");
        }

        // Bind properties to object
        try {

            this.coordinates = new Coordinates(Double.parseDouble(components[15]), Double.parseDouble(components[1]));
            this.date = new DateTime(PDDConfig.getInstance().PhillyCrimeDataDateFormat.parse(components[5]));
            this.code = Integer.parseInt(components[11]);
            this.description = components[12];

        } catch (NumberFormatException nfe) {
            throw new MalformedIncidentException(str + "\n(One of the numbers/coordinates could not be parsed)." + nfe.getLocalizedMessage());
        } catch (ParseException pe) {
            throw new MalformedIncidentException(str + "\n(The date was not supplied in the correct format. The date string was " + components[5] + ".");
        }
    }


    public Coordinates getCoordinates() {
        return this.coordinates;
    }
    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }
    Coordinates coordinates;

    public DateTime getDate() {
        return this.date;
    }
    public void setDate(DateTime date) {
        this.date = date;
    }
    DateTime date;

    public String getDescription() {
        return this.description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    String description;

    public int getCode() {
        return this.code;
    }
    public void setCode(int code) {
        this.code = code;
    }
    int code;

    public double getDistance(Coordinates to) {
        return Main.distance(this.coordinates, to);
    }


}
