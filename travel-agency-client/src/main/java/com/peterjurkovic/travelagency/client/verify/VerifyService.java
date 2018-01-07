package com.peterjurkovic.travelagency.client.verify;

import java.io.IOException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.nexmo.client.NexmoClient;
import com.nexmo.client.NexmoClientException;
import com.nexmo.client.verify.CheckResult;
import com.nexmo.client.verify.VerifyResult;
import com.peterjurkovic.travelagency.common.model.User;

@Service
public class VerifyService {
    
    private final Logger log = LoggerFactory.getLogger(getClass());
    
    private final NexmoClient nexmoClient;
    
    @Value("${company.name}")
    private String companyName;
    
    
    @Autowired
    public VerifyService(NexmoClient nexmoClient){
        this.nexmoClient = nexmoClient;
    }
    public void cancelVerifycation(String requestId){
        log.info("Canceling verificaion request ID: {}" , requestId);
        try {
            nexmoClient.getVerifyClient().cancelVerification(requestId);
            log.info("A request {} has been canceled" , requestId);
        } catch (Exception e) {
            log.error("Verification cancellation has failed for requestId " + requestId, e);
        }
    }
    
    public Optional<VerifyResult> verify(User user){
        String phone = user.getPhone();
        
        log.info("Sending a verify request to: {} to verify user {}", phone, user.getEmail());
        
        try {
            VerifyResult result = nexmoClient.getVerifyClient().verify(phone, companyName);
            
            log.debug("Verify result of {} -> request ID {} Status {}", phone, result.getRequestId(), result.getStatus());
            
            return Optional.of(result);
            
        } catch (IOException | NexmoClientException e) {
            log.error("Verification request of phone "+phone+" has failed", e);
            return Optional.empty();
        }catch( Exception e){
            log.error("Verification request of phone "+phone+" has failed", e);
            return Optional.empty();
        }
        
    }
    
    
    public Optional<CheckResult> check(String requestId, String code){
        log.info("Checing Request ID {} core: {}", requestId, code);
        
        try {
            CheckResult result = nexmoClient.getVerifyClient().check(requestId, code);
            
            log.info("Status: Request Id {} Status {}", requestId, result.getStatus());
            return Optional.of(result);
        } catch (IOException | NexmoClientException e) {
            log.error("Verification request ID "+requestId+" has failed", e);
            return Optional.empty();
        }
        
    }

}
