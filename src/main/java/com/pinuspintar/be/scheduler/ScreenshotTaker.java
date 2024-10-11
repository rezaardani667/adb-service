package com.pinuspintar.be.scheduler;

import java.io.IOException;

public class ScreenshotTaker {

    public void takeScreenshot() {
        try {
            Process process = Runtime.getRuntime().exec
                    ("adb shell screencap -p /sdcard/screenshot.png");
            process.waitFor();

            Process pullProcess = Runtime.getRuntime().exec
                    ("adb pull /sdcard/screenshot.png /Users/pinus/Documents/adb-service/Screenshot");
            pullProcess.waitFor();

            System.out.println("Screenshot berhasil disimpan di /Users/pinus/Documents/adb-service/Screenshot.");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new ScreenshotTaker().takeScreenshot();
    }
}
