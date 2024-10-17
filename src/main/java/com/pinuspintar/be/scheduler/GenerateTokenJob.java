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

import java.util.List;

@Component
public class GenerateTokenJob {

	private static final Logger logger = LoggerFactory.getLogger(GenerateTokenJob.class);

	@Autowired
	TokenRequestRepository tokenRequestRepository;

	@Autowired
	TokenRequestService tokenRequestService;

	private static final String TESSDATA_PATH = "/usr/local/share/tessdata";

	private static final String SCREENSHOT_DESTINATION = "/Users/pinus/Documents/adb-service/Screenshot/screenshot.png";

	@Scheduled(fixedRate = 10000)
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
		}); // Menutup forEach lambda dengan '});'
	}

	private void executeAdbCommands() throws Exception {
		String[] commands = { "adb shell input tap 693 1394", "adb shell input tap 546 418",
				"adb shell input tap 556 821", "adb shell input tap 518 1891", "adb shell input tap 260 1227",
				"adb shell input tap 553 2176", "adb shell input tap 574 2162" };

		for (String command : commands) { // Menambahkan ')' setelah 'commands'
			Process process = Runtime.getRuntime().exec(command);
			process.waitFor();
			Thread.sleep(2000); // Menunggu 2 detik setelah setiap tap
		}
	}

}