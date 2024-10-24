package com.pinuspintar.be.scheduler;

import com.pinuspintar.be.enums.Status;
import com.pinuspintar.be.model.TokenRequest;
import com.pinuspintar.be.repository.TokenRequestRepository;
import com.pinuspintar.be.util.ScreenshotTaker;
import com.pinuspintar.be.util.OCR;
import com.pinuspintar.be.util.TokenRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class GenerateTokenJob {

	private static final Logger logger = LoggerFactory.getLogger(GenerateTokenJob.class);
	private static final String SCREENSHOT_PATH = "/Users/pinus/Documents/adb-service/Screenshot/screenshot.png";

	@Autowired
	private TokenRequestRepository tokenRequestRepository;

	@Autowired
	private TokenRequestService tokenRequestService;

	private final ScreenshotTaker screenshotTaker = new ScreenshotTaker(SCREENSHOT_PATH);

	@Scheduled(fixedRate = 1000)
	public void generateToken() {
		List<TokenRequest> pendingTokenRequests = tokenRequestRepository.findTokenRequestByStatus(Status.pending.name());

		pendingTokenRequests.forEach(tokenRequest -> {
			try {
				generateTokenForRequest(tokenRequest);
			} catch (Exception e) {
				logger.error("Error memproses TokenRequest ID: " + tokenRequest.getId(), e);
			}
		});
	}

	// Method untuk memproses tiap TokenRequest dengan mekanisme retry
	private void generateTokenForRequest(TokenRequest tokenRequest) {
		int retryCount = 0;
		while (retryCount < 3) {
			try {
				int[][] coordinates = {{693, 1394}, {532, 427}, {405, 742}, {396, 1856}, {288, 1187}, {496, 2153}, {496, 2153}};
				for (int[] coordinate : coordinates) {
					screenshotTaker.tapScreen(coordinate[0], coordinate[1]);
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						logger.error("Jeda 3 detik terganggu setelah tap.");
					}
				}

				screenshotTaker.takeScreenshot();

				String extractedNumbers = OCR.extractNumbers(SCREENSHOT_PATH);

				if (!extractedNumbers.isEmpty()) {
					tokenRequestService.updateToken(tokenRequest.getId(), extractedNumbers);
					tokenRequestService.updateStatus(tokenRequest.getId(), Status.success.name());
					logger.info("TokenRequest ID: {} berhasil diproses dengan token: {}", tokenRequest.getId(), extractedNumbers);
				} else {
					logger.error("Tidak ada angka yang diekstrak untuk TokenRequest ID: {}", tokenRequest.getId());
				}

				break;
			} catch (Exception e) {
				retryCount++;
				logger.warn("Attempt " + retryCount + " failed for TokenRequest ID: " + tokenRequest.getId(), e);
				if (retryCount == 3) {
					logger.error("Final failure for TokenRequest ID: " + tokenRequest.getId(), e);
				}
			}
		}
	}

	public void takeScreenshot() {
		try {
			TimeUnit.SECONDS.sleep(2);
			screenshotTaker.takeScreenshot();
		} catch (InterruptedException e) {
			logger.error("Delay interrupted while taking screenshot.",e);
		}
	}
}