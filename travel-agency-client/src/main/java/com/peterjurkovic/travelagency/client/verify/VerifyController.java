package com.peterjurkovic.travelagency.client.verify;

import static com.peterjurkovic.travelagency.client.verify.VerifyCheckResult.fail;
import static com.peterjurkovic.travelagency.client.verify.VerifyCheckResult.ok;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nexmo.client.verify.CheckResult;
import com.nexmo.client.verify.VerifyResult;
import com.peterjurkovic.travelagency.client.user.UserUtils;
import com.peterjurkovic.travelagency.common.model.User;

@Controller
public class VerifyController {
    
    public static final String URL = "/verify";
    
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private VerifyAction verifyAction;
    
    @Autowired
    private UserUtils userUtils;

    private final VerifyService verifyService;

    @Autowired
    public VerifyController(VerifyService verifyService){
        this.verifyService = verifyService;
    }
    
    
    @GetMapping(URL)
    public String verify(ModelMap model){
        
        Optional<User> user = verifyAction.getUser();
        
        if ( ! user.isPresent() ){
            log.info("No user context present ");
            user = userUtils.getLoggedUser();
            if( ! user.isPresent()){
                log.info("No logged user bound in security context");
                return "redirect:/login";
            }
            verifyAction.with(user.get());
        }
        
        Optional<VerifyResult> verifyRsult = verifyService.verify(user.get());
        
        model.put("user", user.get());
        
        if ( verifyRsult.isPresent() ){
            verifyAction.setRequestId(verifyRsult.get().getRequestId());
            if(verifyRsult.get().getStatus() == 0){
                log.info("Verify request was successfull");
            }else{
                model.addAttribute("verifyError", verifyRsult.get().getErrorText());
            }
        }else{
            model.addAttribute("verifyError", "Failed to verify your phone number, try it later please");
        }
        return "verify";
    }
 
    @ResponseBody
    @PostMapping(URL)
    public VerifyCheckResult check(@RequestBody Code code){
        
        String requestId = verifyAction.getRequestId();
        if(requestId == null){
            log.info("No requestId found.");
            return fail("Your code has expired, reload the page");
        }
        log.info("Verifing request ID {} code {}", requestId, code);
        Optional<CheckResult> checkResult = verifyService.check(requestId, code.getCode());
        
        if(checkResult.isPresent()){
            if(checkResult.get().getStatus() == CheckResult.STATUS_OK){
                log.info("Verification of Request ID was sucessfull", requestId);
                userUtils.getLoggedUserDetails().get().markTwoFaAsCompleted();
                return ok(verifyAction.getSuccessRedirectUrl());
            }
            
            return fail(checkResult.get().getErrorText());
        }
        return fail("Verification has failed");
    }
    
    @ResponseBody
    @PutMapping(URL)
    public void resendCode(){
        Optional<User> user = userUtils.getLoggedUser();
        
        if(user.isPresent()){
            String requestId = verifyAction.getRequestId();
            if(requestId  != null){
                verifyService.cancelVerifycation(requestId);
            }
            Optional<VerifyResult> verifyRsult = verifyService.verify(user.get());
            if(verifyRsult.isPresent()){
                verifyAction.setRequestId(verifyRsult.get().getRequestId());
            }
        }
        
        
    }
    
}
