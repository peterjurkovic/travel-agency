package com.peterjurkovic.travelagency.client.siqnup;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.google.i18n.phonenumbers.NumberParseException;
import com.peterjurkovic.travelagency.common.model.User;
import com.peterjurkovic.travelagency.common.model.User.Status;
import com.peterjurkovic.travelagency.common.utils.PhoneUtils;


public class SignupForm {
    
    @Email(message = "{Email.signupForm.email}")
    @NotEmpty
    private String email;
    
    @Pattern(regexp = "\\+\\d{5,15}", message = "{Pattern.signupForm.phone}")
    @NotEmpty(message = "{NotEmpty.signupForm.phone}")
    private String phone;
    
    @NotEmpty
    private String firstName;
    
    @NotEmpty
    private String lastName;
    
    @NotEmpty
    @Size( min = 8, max = 32, message = "{Size.signupForm.password}")
    private String password;
    
    @NotEmpty
    private String passwordConfirmation;
    
    
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }

    public User toUser(){
        User user = new User();
        user.setEmail(email.trim());
        user.setFirstName(firstName);
        user.setLastName(lastName);
        try {
            user.setPhone(PhoneUtils.toInternationalFormat(phone));
        } catch (NumberParseException e) {
            throw new IllegalArgumentException(e);
        }
        user.setPassword(password);
        user.setStatus(Status.VERIFY);
        return user;
    }
    
}
