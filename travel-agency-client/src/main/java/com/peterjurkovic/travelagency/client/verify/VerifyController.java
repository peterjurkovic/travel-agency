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
import org.springframework.web.bind.annotation.ResponseBody;

import com.nexmo.client.verify.CheckResult;
import com.nexmo.client.verify.VerifyResult;
import com.peterjurkovic.travelagency.common.model.User;

@Controller
public class VerifyController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private VerifyAction verifyAction;
    
    
    private final VerifyService verifyService;
    
    public VerifyController(VerifyService verifyService){
        this.verifyService = verifyService;
    }
    
    
    @GetMapping("/verify")
    public String verify(ModelMap model){
        
        Optional<User> user = verifyAction.getUser();
        
        if ( ! user.isPresent() ){
            log.info("No user context present ");
            return "redirect:/login";
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
    @PostMapping("/verify")
    public VerifyCheckResult check(Code code){
    
        String requestId = verifyAction.getRequestId();
        Optional<CheckResult> checkResult = verifyService.check(requestId, code.getCode());
        
        if(checkResult.isPresent()){
            if(checkResult.get().getStatus() == CheckResult.STATUS_OK)
                return ok(verifyAction.getSuccessRedirectUrl());
            
            return fail(checkResult.get().getErrorText());
        }
        return fail("Verifycation has failed");
    }
    
}
