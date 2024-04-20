package com.api.logger;

public class Logger {
    /**
     * Print an information message to the console.
     * @param message The message to be displayed as info.
     */
    public static void info(String message) {
        System.out.println("[INFO] " + message);
    }

    public static void error(String message) {
        System.err.println("[ERROR] " + message);
    }
}
