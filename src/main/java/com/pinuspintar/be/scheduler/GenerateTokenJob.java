package com.pinuspintar.be.scheduler;

import com.pinuspintar.be.enums.Status;
import com.pinuspintar.be.model.TokenRequest;
import com.pinuspintar.be.repository.TokenRequestRepository;
import com.pinuspintar.be.util.ADBOperations;
import com.pinuspintar.be.util.ScreenshotTaker;
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

	private static final String SCREENSHOT_DESTINATION = "/Users/pinus/Documents/adb-service/Screenshot/ss.png";

	private ADBOperations adbOperations = new ADBOperations();
	private ScreenshotTaker screenshotTaker = new ScreenshotTaker(SCREENSHOT_DESTINATION);

	@Scheduled(fixedRate = 1000)
	public void genToken() {
		List<TokenRequest> tokenRequestList = tokenRequestRepository.findTokenRequestByStatus(Status.pending.name());

		tokenRequestList.forEach(tokenReq -> {
			try {
				int[][] adbTapCoordinates = {
						{693, 1394}, {546, 418}, {556, 821},
						{518, 1891}, {260, 1227}, {553, 2176}, {574, 2162}
				};
				adbOperations.executeMultipleTapCommands(adbTapCoordinates);

				screenshotTaker.takeScreenshot();

				String extractedText = OCR.extractText(SCREENSHOT_DESTINATION);
				extractedText = (extractedText != null) ? extractedText.replaceAll("\\D+", "") : null;
				logger.info("Teks yg diekstrak: {}", extractedText);

				if (extractedText != null && !extractedText.isEmpty()) {
					// Update token dan status
					tokenRequestService.updateToken(tokenReq.getId(), extractedText);
					tokenRequestService.updateStatus(tokenReq.getId(), Status.success.name());
					logger.info("TokenRequest dgn ID: {} berhasil diproses.", tokenReq.getId());
				} else {
					logger.error("Tidak ada angka yg diekstrak untuk TokenRequest dgn ID: {}", tokenReq.getId());
				}
			} catch (Exception e) {
				logger.error("Error memproses TokenRequest dgn ID: {}", tokenReq.getId(), e);
				e.printStackTrace();
			}
		});
	}
}
