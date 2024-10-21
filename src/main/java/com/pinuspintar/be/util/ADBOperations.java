package com.pinuspintar.be.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ADBOperations {

    private static final Logger logger = LoggerFactory.getLogger(ADBOperations.class);

    public void excecuteTapCommand(int x, int y) throws Exception {
        String command = String.format("adb shell input tap %d %d", x, y);
        Process process = Runtime.getRuntime().exec(command);
        process.waitFor();

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            logger.info("Output ADB: " + line);
        }
        Thread.sleep(2000);
    }

    public void executeMultipleTapCommands(int[][] coordinates) throws Exception {
        for (int[] coord : coordinates) {
            excecuteTapCommand(coord[0], coord[1]);
        }
    }
}
