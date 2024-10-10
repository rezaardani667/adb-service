package com.pinuspintar.be.scheduler;

import com.pinuspintar.be.enums.Status;
import com.pinuspintar.be.model.TokenRequest;
import com.pinuspintar.be.repository.TokenRequestRepository;
import com.pinuspintar.be.util.TokenRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.prefs.Preferences;

@Component
public class GenerateTokenJob {

    @Autowired
    private TokenRequestRepository tokenRequestRepository;

    @Autowired
    private TokenRequestService tokenRequestService;

    @Scheduled(fixedRate = 1000)
    public void genToken() {
        List<TokenRequest> tokenRequestList = tokenRequestRepository.findTokenRequestByStatus(Status.pending.name());
        tokenRequestList.forEach(tokenReq -> {
            try {
                System.out.println("execute");
                Process p = Runtime.getRuntime().exec("adb shell input tap 693 1394");
                p.waitFor();
                Thread.sleep(2000);
                p = Runtime.getRuntime().exec("adb shell input tap 546 418");
                p.waitFor();
                Thread.sleep(2000);
                p = Runtime.getRuntime().exec("adb shell input tap 556 821");
                p.waitFor();
                Thread.sleep(2000);
                p = Runtime.getRuntime().exec("adb shell input tap 518 1891");
                p.waitFor();
                Thread.sleep(2000);
                p = Runtime.getRuntime().exec("adb shell input tap 260 1227");
                p.waitFor();
                Thread.sleep(2000);
                p = Runtime.getRuntime().exec("adb shell input tap 553 2176");
                p.waitFor();
                Thread.sleep(2000);
                p = Runtime.getRuntime().exec("adb shell input tap 574 2162");
                p.waitFor();
                //@TODO: adb screen shhot, hasil di simpan di suatu tempat
                //@TODO: run tesseract untuk ambil angka dari screent file
                //@TODO: angkanya di simpan ke database
                BufferedReader reader =
                        new BufferedReader(new InputStreamReader(p.getInputStream()));

                String line = "";
                while ((line = reader.readLine())!= null) {
                    System.out.println(line);
                }
                //@todo: update token tokenRequestService.updateToken(tokenReq.getId(), token);
                tokenRequestService.updateStatus(tokenReq.getId(), Status.success.name());
                System.out.println("Processed TokenRequest with ID: " + tokenReq.getId());
            } catch (Exception e) {
                System.err.println("Error processing TokenRequest with ID: " + tokenReq.getId());
                e.printStackTrace();
            }
        });

    }
}
