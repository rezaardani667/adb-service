package com.pinuspintar.be.scheduler;

import com.pinuspintar.be.enums.Status;
import com.pinuspintar.be.model.TokenRequest;
import com.pinuspintar.be.repository.TokenRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;

import java.io.IOException;
import java.util.List;

@Component
public class GenerateTokenJob {

    @Autowired
    TokenRequestRepository tokenRequestRepository;

    @Scheduled(fixedRate = 1000)
    public void genToken() {
        System.out.println("helo");
        List<TokenRequest> tokenRequestList = tokenRequestRepository.findTokenRequestByStatus(Status.pending.name());
        tokenRequestList.stream().forEach(tokenReq -> {
            try {
<<<<<<< Updated upstream
                Process p = Runtime.getRuntime().exec(new String[]{"ls"});
                System.out.println(p.getOutputStream().toString());
            } catch (IOException e) {
                throw new RuntimeException(e);
=======
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
                //@TODO: adb screenshoot, hasil di simpan di suatu tempat
                //@TODO: run tesseract untuk ambil angka dari screenshoot file
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
>>>>>>> Stashed changes
            }
        });

    }
}
