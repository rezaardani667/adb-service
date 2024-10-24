package com.pinuspintar.be.util;

import java.io.File;
import java.io.IOException;
import java.lang.ProcessBuilder;

public class ScreenshotTaker {
    private final String screenshotDestination;

    public ScreenshotTaker(String screenshotDestination) {
        this.screenshotDestination = screenshotDestination;
    }

    public void takeScreenshot() {
        try {
            // Mengambil screenshot menggunakan ADB
            ProcessBuilder processBuilder = new ProcessBuilder("adb", "shell", "screencap", "-p", "/sdcard/screenshot.png");
            Process process = processBuilder.inheritIO().start();
            process.waitFor();

            // Menyalin screenshot dari perangkat ke folder lokal
            processBuilder = new ProcessBuilder("adb", "pull", "/sdcard/screenshot.png", screenshotDestination);
            process = processBuilder.inheritIO().start();
            process.waitFor();

            // Menghapus screenshot dari perangkat setelah menyalin
            processBuilder = new ProcessBuilder("adb", "shell", "rm", "/sdcard/screenshot.png");
            process = processBuilder.inheritIO().start();
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Tambahkan metode untuk men-tap di koordinat tertentu
    public void tapScreen(int x, int y) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("adb", "shell", "input", "tap", String.valueOf(x), String.valueOf(y));
            Process process = processBuilder.inheritIO().start();
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}