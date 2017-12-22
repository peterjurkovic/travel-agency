package com.peterjurkovic.travelagency.client.siqnup;


import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;

import com.peterjurkovic.travelagency.client.verify.VerifyAction;
import com.peterjurkovic.travelagency.common.model.User;
import com.peterjurkovic.travelagency.common.repository.UserRepository;

@Controller
public class SignupController {

    private final Logger log = LoggerFactory.getLogger(getClass());
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Autowired
    private VerifyAction verifyAction;
    
    public SignupController(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    @InitBinder
    public void initBinder(WebDataBinder binder){
        binder.addValidators( new SignupFormValidator(userRepository) );
    }
    
    @GetMapping("/sign-up")
    public String showSignupForm(ModelMap model){
        model.addAttribute(new SignupForm());
        return "sign-up";
    }
        
    @PostMapping("/sign-up")
    public String processSignup(ModelMap model, @Valid SignupForm form, BindingResult result){
        if(result.hasErrors()){
            model.addAttribute(form);
            log.warn("Form contains error {} ", form );
        }else{
            User user = form.toUser();
            user.setPassword( passwordEncoder.encode( form.getPassword()) ); 
            userRepository.save(user);
            verifyAction.with(user, "/?welcome=1");
            log.info("New user created email={} phone={} ", form.getEmail(), form.getPhone() );
            return "redirect:/verify";
        }
        return "sign-up";
    }
}
