package com.pinuspintar.be.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.sourceforge.tess4j.Tesseract;

public class OCR {
	private static final String TESSERACT_CMD = "tesseract";
	private static final Logger log = LoggerFactory.getLogger(OCR.class);
	private static final String RESULT_FILE_PATH = "/Users/pinus/Documents/adb-service/Screenshot/screenshot";

	public static String extractNumbers(String imagePath) throws IOException, InterruptedException {
		ProcessBuilder processBuilder = new ProcessBuilder(TESSERACT_CMD, imagePath, RESULT_FILE_PATH);
		processBuilder.redirectErrorStream(true);
		Process process = processBuilder.start();
		int exitCode = process.waitFor();
		if (exitCode != 0) {
			throw new RuntimeException("Error executing Tesseract: exit code " + exitCode);
		}

		String outputFilePath = RESULT_FILE_PATH + ".txt"; // Path untuk file output Tesseract

		// Cek apakah file output ada
		if (!Files.exists(Paths.get(outputFilePath))) {
			log.error("File screenshot.txt tidak ditemukan.");
			return ""; // Kembalikan string kosong jika file tidak ada
		}

		// Membaca hasil output dari file
		String result = new String(Files.readAllBytes(Paths.get(outputFilePath)));

		// Ekstrak hanya angka dari hasil OCR
		String extractedNumbers = extractNumbersFromText(result);

		// Simpan angka yang diekstrak ke dalam file 'screenshot.txt'
		saveExtractedNumbers(extractedNumbers);

		return extractedNumbers.trim();
	}

	// Method untuk mengekstrak angka dari teks yang dihasilkan OCR
	private static String extractNumbersFromText(String text) {
		StringBuilder numbersOnly = new StringBuilder();

		Pattern pattern = Pattern.compile("\\d+");
		Matcher matcher = pattern.matcher(text);

		while (matcher.find()) {
			numbersOnly.append(matcher.group()).append("\n");
		}

		return numbersOnly.toString();
	}

	// Method untuk menyimpan hasil angka yang diekstrak ke file 'screenshot.txt'
	private static void saveExtractedNumbers(String numbers) {
		String outputFilePath = "/Users/pinus/Documents/adb-service/Screenshot/screenshot.txt"; // Lokasi yang benar

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {
			writer.write(numbers);
		} catch (IOException e) {
			log.error("Gagal menyimpan angka ke screenshot.txt", e);
		}
	}
}