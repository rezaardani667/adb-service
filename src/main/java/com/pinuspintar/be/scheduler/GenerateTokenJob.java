package com.pinuspintar.be.scheduler;

import com.pinuspintar.be.enums.Status;
import com.pinuspintar.be.model.TokenRequest;
import com.pinuspintar.be.repository.TokenRequestRepository;
import com.pinuspintar.be.util.TokenRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.prefs.Preferences;

@Component
public class GenerateTokenJob {

	private static final Logger logger = LoggerFactory.getLogger(GenerateTokenJob.class);

	@Autowired
	TokenRequestRepository tokenRequestRepository;

	@Autowired
	TokenRequestService tokenRequestService;

	private static final String TESSDATA_PATH = "/usr/local/share/tessdata";

	private static final String SCREENSHOT_DESTINATION = "/Users/pinus/Documents/adb-service/Screenshot/screenshot.png";

	@Scheduled(fixedRate = 1000)
	public void genToken() {
		logger.info("Generating token...");

		List<TokenRequest> tokenRequestList = tokenRequestRepository.findTokenRequestByStatus(Status.pending.name());

		tokenRequestList.forEach(tokenReq -> {
			try {
				executeAdbCommands();

				ScreenshotTaker screenshotTaker = new ScreenshotTaker(SCREENSHOT_DESTINATION);
				screenshotTaker.takeScreenshot();

				String extractedText = OCR.extractText(SCREENSHOT_DESTINATION);
				if (extractedText != null) {
					extractedText = extractedText.replaceAll("\\D+", "");
				}
				logger.info("Teks yg diekstrak: {}", extractedText);

				if (extractedText != null && !extractedText.isEmpty()) {
					tokenRequestService.updateToken(tokenReq.getId(), extractedText);
					tokenRequestService.updateStatus(tokenReq.getId(), Status.success.name());
					logger.info("TokenRequest dgn ID: {} berhasil diproses.", tokenReq.getId());
				}
				else {
					logger.error("Tidak ada angka yg diekstrak untuk TokenRequest dgn ID: {}", tokenReq.getId());
				}

			}
			catch (Exception e) {
				logger.error("Error memproses TokenRequest dgn ID: {}", tokenReq.getId(), e);
				e.printStackTrace();
			}
		});
	}

	private void executeAdbCommands() throws Exception {
		String[] commands = { "adb shell input tap 693 1394", "adb shell input tap 546 418",
				"adb shell input tap 556 821", "adb shell input tap 518 1891", "adb shell input tap 260 1227",
				"adb shell input tap 553 2176", "adb shell input tap 574 2162" };

		for (String command : commands) {
			Process process = Runtime.getRuntime().exec(command);
			process.waitFor();
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line;
			while ((line = reader.readLine()) != null) {
				logger.info("Output ADB: " + line);
			}
			Thread.sleep(2000);
		}
	}

}