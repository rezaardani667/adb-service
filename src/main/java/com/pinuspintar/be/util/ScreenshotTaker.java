package com.pinuspintar.be.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScreenshotTaker {

    private static final Logger logger = LoggerFactory.getLogger(ScreenshotTaker.class);
    private String screenshotDestination;

    public ScreenshotTaker(String screenshotDestination) {
        this.screenshotDestination = screenshotDestination;
    }

    public void takeScreenshot() throws Exception {
        String command = String.format("adb shell screencap -p > %s", screenshotDestination);
        Process process = Runtime.getRuntime().exec(command);
        process.waitFor();

        logger.info("Screenshot berhasil diambil dan disimpan di: {}", screenshotDestination);
    }
}
