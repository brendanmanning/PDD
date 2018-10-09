package com.brendanmanning.PhillyDillyDilly;

import java.awt.*;
import java.io.*;
import java.text.ParseException;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.io.*;

public class Main {


    private static int malformedIncidents = 0;
    private static Scanner scanner = new Scanner(System.in);

    private static Vector<Incident> incidents = new Vector<Incident>(0);
    private static Grid[][] grids;

    public static void main(String[] args) {

        // Load the data into the array
        System.out.println("Loading data into memory...");
        try (BufferedReader br = new BufferedReader(new FileReader( PDDConfig.getInstance().PhillyCrimeDataCsv ))) {
            for(String line; (line = br.readLine()) != null; ) {
                try {
                    incidents.add(new Incident(line));
                } catch (MalformedIncidentException mie) {
                    malformedIncidents++;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //System.out.println("Crime data loaded into memory.\n  Vector size: " + incidents.size() + " elements.\n  Malformed incidents: " + malformedIncidents);


        // Generate the grids
        System.out.println("Generating grids...");
        grids = GridGenerator.getInstance().getGrids(incidents);

        System.exit(0);
        generateGrids();


        while(true) {

            // Promt for map name & create file reference
            System.out.print("New map name: ");
            String mapName = scanner.nextLine().replace(" ", "_");
            File mapFile = new File(PDDConfig.getInstance().MapViewOutputDirectory + File.separator + mapName + ".html");

            // Use IncidentQuery to chain conditions
            IncidentQuery incidentQuery = new IncidentQuery();

            // Should we overwrite on exist?
            if(mapFile.exists()) {
                System.out.print("This file already exists. Overwrite? [y/N] ");
                String response = scanner.nextLine();
                if(!response.equals("y"))
                    continue;
            }

            // Configure location filter
            System.out.print("Filter by location? [y/N] ");
            String response = scanner.nextLine();
            if(response.equals("y")) {

                Coordinates coordinates = null;
                double radius = 0;

                boolean coordinatesOk = false;
                boolean radiusOk = false;

                do {
                    try {
                        System.out.print("  Search coordinates (latitude): ");
                        double latitude = Double.parseDouble(scanner.nextLine());
                        System.out.print("  Search coordinates (longitude): ");
                        double longitude = Double.parseDouble(scanner.nextLine());

                        coordinates = new Coordinates(latitude, longitude);
                        coordinatesOk = true;
                    } catch (NumberFormatException nfe) {
                        System.out.println("  [X] Coordinates invalid. Please try again.");
                    }
                } while (!coordinatesOk);

                do {
                    try {
                        System.out.print("  Search radius (km): ");
                        radius = Double.parseDouble(scanner.nextLine()) * 1000;

                        if(radius < 10) {
                            System.out.println("  [X] Radius must be at least 10m.");
                        } else {
                            radiusOk = true;
                        }
                    } catch (NumberFormatException nfe) {
                        System.out.println("  [X] Radius invalid. Please try again.");
                    }
                } while (!radiusOk);

                incidentQuery.setRadiusFilterEnabled(true);
                incidentQuery.setCenterCoordinate(coordinates);
                incidentQuery.setRadius(radius);
            }

            // Configure date filter
            System.out.print("Filter by date? [y/N] ");
            response = scanner.nextLine();
            if(response.equals("y")) {

                DateTime startDate = null;
                boolean startDateOk = false;

                do {
                    try {
                        System.out.print("  Start date (y-MM-dd HH:mm:ss): ");
                        String dateInput = scanner.nextLine();

                        startDate = new DateTime(PDDConfig.getInstance().PhillyCrimeDataDateFormat.parse(dateInput));
                        startDateOk = true;
                    } catch (ParseException pe) {
                        System.out.println("  [X] Date input invalid. See valid examples below.");
                        System.out.println("     • 2018-01-09 23:59:00 is January 9th 2018 at 11:59:00 PM");
                        System.out.println("     • 2015-12-25 01:02:59 is December 25th 2015 at 1:02:59 AM");
                    }
                } while (!startDateOk);

                incidentQuery.setDateFilterEnabled(true);
                incidentQuery.setStartTime(startDate);

            }

            // Apply the queries to the data set
            List<Incident> results = applyQuery(incidentQuery);

            // Generate the map
            System.out.println("Generating map with " + results.size() + " incidents...");
            generateMap(results, mapName);

            // Tell the user we're done
            System.out.println("[✓] Map " + mapName + " created!");

            // Ask to open in the browser
            System.out.print("  Open map in browser? [y/N]");
            response = scanner.nextLine();
            if(response.equals("y")) {
                try {
                    Desktop.getDesktop().open(mapFile);
                } catch (IOException e) {
                    System.out.println("  [X] Could not open map in browser.");
                }
            }

            // Ask to create another map
            System.out.println("Create another map? [y/N]");
            response = scanner.nextLine();
            if(!response.equals("y")) {
                System.exit(0);
            }
        }
    }

    public static List<Incident> applyQuery(IncidentQuery incidentQuery) {

        return incidents.stream()
                .filter(incident ->
                        (!incidentQuery.isRadiusFilterEnabled() || incidentQuery.isWithinRadius(incident)) &&
                        (!incidentQuery.isDateFilterEnabled() || incidentQuery.isWithinDateRange(incident)))
                .collect(Collectors.toList());

    }

    public static void generateGrids() {

        GridGenerator.getInstance().getGrids(incidents);

        /*try {
            String template = FileUtils.readFileToString(PDDConfig.getInstance().MapViewTemplate);

            String markerHtml = "";
            for(int j = 0; j < grids.length; j++) {
                markerHtml += "new google.maps.Marker({position: {lat:" + grids[j].topLeft.getLat() + ",lng:" + grids[j].topLeft.getLon() + "}, map: map}).addListener('click', function() {alert('Some topLeft');});";
            }
            template = template.replace("{MARKERS}", markerHtml);

            System.out.println(PDDConfig.getInstance().MapViewOutputDirectory.getAbsolutePath() + "TESTING123.html");
            FileUtils.writeStringToFile(new File(PDDConfig.getInstance().MapViewOutputDirectory.getAbsolutePath() + File.separator + "TESTING123.html"), template);
        } catch (IOException ioe) {

        }

        System.out.println("Done");*/
    }

    public static void generateMap(List<Incident> results, String mapName) {
        try {
            String template = FileUtils.readFileToString(PDDConfig.getInstance().MapViewTemplate);

            String markerHtml = "";
            for(int i = 0; i < results.size(); i++) {
                //System.out.println(i);
                Incident incident = results.get(i);
                markerHtml += "new google.maps.Marker({position: {lat:" + incident.getCoordinates().getLat() + ",lng:" + incident.getCoordinates().getLon() + "}, map: map}).addListener('click', function() {alert('" + incident.getDescription() + " on " + incident.getDate().month() + "/" + incident.getDate().day() + "/" + incident.getDate().year() + "');});";
            }
            template = template.replace("{MARKERS}", markerHtml);

            System.out.println(PDDConfig.getInstance().MapViewOutputDirectory.getAbsolutePath() + File.separator + mapName + ".html");
            FileUtils.writeStringToFile(new File(PDDConfig.getInstance().MapViewOutputDirectory.getAbsolutePath() + File.separator + mapName + ".html"), template);
        } catch (IOException ioe) {

        }
    }

    public static void generateMap(List<Incident> results, Grid grid, String mapName) {
        try {
            String template = FileUtils.readFileToString(PDDConfig.getInstance().MapViewTemplate);

            String markerHtml = "";
            for(int i = 0; i < results.size(); i++) {
                //System.out.println(i);
                Incident incident = results.get(i);
                markerHtml += "new google.maps.Marker({position: {lat:" + incident.getCoordinates().getLat() + ",lng:" + incident.getCoordinates().getLon() + "}, map: map}).addListener('click', function() {alert('" + incident.getCoordinates() + "\\n" + (incident.getCoordinates().getLat() - grid.getTopLeft().getLat()) + "\\n" + (incident.getCoordinates().getLon() - grid.getTopLeft().getLon()) + "');});\n";
            }

            markerHtml += "new google.maps.Marker({position: {lat:" + grid.getTopLeft().getLat() + ",lng:" + grid.getTopLeft().getLon() + "}, icon: 'https://developers.google.com/maps/documentation/javascript/examples/full/images/beachflag.png', map: map});";

            markerHtml += "var squareCoords = [ new google.maps.LatLng(" + (grid.getTopLeft().getLat()) + "," + (grid.getTopLeft().getLon()) + "),new google.maps.LatLng(" + (grid.getTopLeft().getLat()) + "," + (grid.getTopLeft().getLon() + grid.getWidth()) + "),new google.maps.LatLng(" + (grid.getTopLeft().getLat() - grid.getHeight()) + "," + (grid.getTopLeft().getLon() + grid.getWidth()) + "),new google.maps.LatLng(" + (grid.getTopLeft().getLat() - grid.getHeight()) + "," + (grid.getTopLeft().getLon()) + ")];\n";
            markerHtml += "mapsquare = new google.maps.Polygon({paths: squareCoords,strokeColor: \"#FF0000\",strokeOpacity: 1,strokeWeight: 1,fillColor: \"#FF0000\",fillOpacity: 0.35});mapsquare.setMap(map);\n";

            template = template.replace("{MARKERS}", markerHtml);
            template = template.replace("{CENTER_LAT}", grid.getTopLeft().getLat() + "");
            template = template.replace("{CENTER_LON}", grid.getTopLeft().getLon() + "");


            System.out.println(PDDConfig.getInstance().MapViewOutputDirectory.getAbsolutePath() + File.separator + mapName + ".html");
            FileUtils.writeStringToFile(new File(PDDConfig.getInstance().MapViewOutputDirectory.getAbsolutePath() + File.separator + mapName + ".html"), template);
        } catch (IOException ioe) {

        }
    }

    public static void generateMap(Coordinates[] results, String mapName) {
        try {
            String template = FileUtils.readFileToString(PDDConfig.getInstance().MapViewTemplate);

            String markerHtml = "";
            for(int i = 0; i < results.length; i++) {
                //System.out.println(i);
                Coordinates coordinates = results[i];
                markerHtml += "new google.maps.Marker({position: {lat:" + coordinates.getLat() + ",lng:" + coordinates.getLon() + "}, map: map}).addListener('click', function() {alert('" + coordinates + "');});";
            }
            template = template.replace("{MARKERS}", markerHtml);

            System.out.println(PDDConfig.getInstance().MapViewOutputDirectory.getAbsolutePath() + File.separator + mapName + ".html");
            FileUtils.writeStringToFile(new File(PDDConfig.getInstance().MapViewOutputDirectory.getAbsolutePath() + File.separator + mapName + ".html"), template);
        } catch (IOException ioe) {

        }
    }

    /**
     *
     * https://stackoverflow.com/questions/9243578/java-util-date-and-getyear/9243597
     *
     * Calculate distance between two points in latitude and longitude taking
     * into account height difference. If you are not interested in height
     * difference pass 0.0. Uses Haversine method as its base.
     *
     * lat1, lon1 Start point lat2, lon2 End point el1 Start altitude in meters
     * el2 End altitude in meters
     * @returns Distance in Meters
     */
    public static double distance(Coordinates c1, Coordinates c2) {

        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(c2.getLat() - c1.getLat());
        double lonDistance = Math.toRadians(c2.getLon() - c1.getLon());
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(c1.getLat())) * Math.cos(Math.toRadians(c2.getLat()))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        double height = 0 - 0;

        distance = Math.pow(distance, 2) + Math.pow(height, 2);

        return Math.sqrt(distance);
    }
}
