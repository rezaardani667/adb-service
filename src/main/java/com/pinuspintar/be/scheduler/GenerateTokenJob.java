package com.pinuspintar.be.scheduler;

import com.pinuspintar.be.enums.Status;
import com.pinuspintar.be.model.TokenRequest;
import com.pinuspintar.be.repository.TokenRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

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
                Process p = Runtime.getRuntime().exec(new String[]{"ls"});
                System.out.println(p.getOutputStream().toString());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

    }
}
