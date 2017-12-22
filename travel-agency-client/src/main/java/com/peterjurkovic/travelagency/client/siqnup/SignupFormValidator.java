package com.peterjurkovic.travelagency.client.siqnup;

import java.util.Objects;
import java.util.Optional;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.peterjurkovic.travelagency.common.model.User;
import com.peterjurkovic.travelagency.common.repository.UserRepository;
import com.peterjurkovic.travelagency.common.utils.PhoneUtils;

class SignupFormValidator implements Validator {

    private final UserRepository userRepository;
    
    SignupFormValidator(UserRepository userRepository){
        this.userRepository = Objects.requireNonNull(userRepository);
    }
    
    @Override
    public boolean supports(Class<?> clazz) {
        return SignupForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SignupForm form = (SignupForm) target;
        validateUniqueEmail(form, errors);
        validateUniquePhone(form, errors);
        validateValidPhone(form, errors);
        validatePassword(form, errors);
    }
    
    
    private void validateUniqueEmail(SignupForm form, Errors errors){
        Optional<User> existingUser = userRepository.findByEmail(form.getEmail());
        if(existingUser.isPresent()){
            errors.rejectValue("email", "email.unique", "Please user a different email address (Is taken)");
        }
    }

    
    private void validateUniquePhone(SignupForm form, Errors errors){
        Optional<User> existingUser = userRepository.findByEmail(form.getEmail());
        if(existingUser.isPresent()){
            errors.rejectValue("phone", "phone.unique", "Please user a different phone (Is taken)");
        }
    }
    
    private void validateValidPhone(SignupForm form, Errors errors){
        try {
          PhoneUtils.toInternationalFormat(form.getPhone());
        } catch (NumberParseException e) {
            errors.rejectValue("phone", "phone.invalid", "It looks like " +form.getPhone()+ " is not a valid international number.");
        }
    }
    
    private void validatePassword(SignupForm form, Errors errors){
        String password = form.getPassword();
        if(password == null || !password.equals(form.getPasswordConfirmation())){
            errors.rejectValue("password", "password.equal", "Passwords do not match");
        }
    }
    
    

}
