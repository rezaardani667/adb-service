package com.pinuspintar.be.scheduler;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseeract;


public class OCR {
    public static String extractText(String imagePath) {
        ITesseract instance = new Tesseract();
        instance.setDatapath(/usr/local/share/tessdata/);
        try {
            String result = instance.doOCR(new File(imagePath));
            return result;
        } catch (TesseractException e) {
            e.printStacktrace();
            return null;
        }
    }
}
