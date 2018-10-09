package com.brendanmanning.PhillyDillyDilly;/**
 * PDD
 * <p>
 * Copyright 2018 Brendan Manning. All rights reserved.
 */

public class MalformedIncidentException extends Error {
    public MalformedIncidentException(String incident) {
        super("Incident object could not be created as the supplied data was malformed.\nPlease see incident/error report below\n===== START INCIDENT =====\n" + incident + "\n===== END INCIDENT =====\n");
    }
}
