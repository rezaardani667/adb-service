package com.pinuspintar.be.scheduler;

import java.io.IOException;

public class ScreenshotTaker {

    private String destinationPath;

    public ScreenshotTaker(String destinationPath) {
        this.destinationPath = destinationPath;
    }

    public void takeScreenshot() throws IOException, InterruptedException {

        Process process = Runtime.getRuntime().exec
                ("adb shell screencap -p /sdcard/screenshot.png");
        process.waitFor();

        Process pullProcess = Runtime.getRuntime().exec
                ("adb pull /sdcard/screenshot.png" + destinationPath);
        pullProcess.waitFor();

        System.out.println("Screenshot berhasil disimpan di " + destinationPath);
    }
}