package com.brendanmanning.PhillyDillyDilly;
import java.io.*;
import java.text.SimpleDateFormat;

/**
 * Created by Pharoah610 on 10/5/18.
 */
public class PDDConfig {

    private static PDDConfig ourInstance = new PDDConfig();
    public static PDDConfig getInstance() {
        return ourInstance;
    }
    private PDDConfig() {}

    File PhillyCrimeDataCsv = new File("/Users/Pharoah610/PDD/assets/PhillyCrimeDataIncidents.csv");
    File MapViewTemplate = new File("/Users/Pharoah610/PDD/assets/MapViewTemple.html");
    File MapViewOutputDirectory = new File("/Users/Pharoah610/Documents/PDDMaps/");
    SimpleDateFormat PhillyCrimeDataDateFormat = new SimpleDateFormat("y-MM-dd HH:mm:ss");

    int GRIDS_EW = 75;
    int GRIDS_NS = 150;
}
