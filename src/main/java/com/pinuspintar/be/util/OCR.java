package com.pinuspintar.be.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OCR {
	private static final String TESSERACT_CMD = "tesseract"; // Pastikan ini diatur ke path Tesseract jika tidak ada di PATH
	private static final Logger log = LoggerFactory.getLogger(OCR.class);

	public static String extractNumbers(String imagePath) throws IOException, InterruptedException {
		String resultFilePath = "/Users/pinus/Documents/adb-service/Screenshot/screenshot";
		ProcessBuilder processBuilder = new ProcessBuilder(TESSERACT_CMD, imagePath, resultFilePath);
		processBuilder.redirectErrorStream(true);
		Process process = processBuilder.start();
		int exitCode = process.waitFor();
		if (exitCode != 0) {
			throw new RuntimeException("Error executing Tesseract: exit code " + exitCode);
		}

		String outputFilePath = resultFilePath + ".txt"; // Path untuk file output

		// Cek apakah file output ada
		if (!Files.exists(Paths.get(outputFilePath))) {
			log.error("File screenshot.txt tidak ditemukan.");
			return ""; // Kembalikan string kosong jika file tidak ada
		}

		// Membaca hasil output dari file
		String result = new String(Files.readAllBytes(Paths.get(outputFilePath)));

		// Mengambil hanya angka dari hasil OCR
		return extractNumbersFromText(result);
	}

	private static String extractNumbersFromText(String text) {
		Pattern pattern = Pattern.compile("\\d+"); // Regex untuk mencocokkan angka
		Matcher matcher = pattern.matcher(text);
		StringBuilder numbers = new StringBuilder();
		while (matcher.find()) {
			numbers.append(matcher.group()).append(" "); // Menambahkan pemisah spasi jika diinginkan
		}
		return numbers.toString().trim(); // Kembalikan hasil angka yang diekstrak
	}
}