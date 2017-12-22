package com.peterjurkovic.travelagency.client.siqnup;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.validation.Errors;

import com.peterjurkovic.travelagency.common.model.User;
import com.peterjurkovic.travelagency.common.repository.UserRepository;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class SignupFormValidatorTest {

    @Mock
    private UserRepository userRepository;
    
    @Mock 
    private User user;
    @Mock
    private SignupForm form;
    @Mock
    private Errors errors;
    
    @Captor
    private ArgumentCaptor<String> captor;
    @InjectMocks
    private SignupFormValidator validator;
    
    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(userRepository.findByPhone(anyString())).thenReturn(Optional.empty());
        when(form.getEmail()).thenReturn("ANY_EMAIL");
        when(form.getPhone()).thenReturn("+447756738680");
        when(form.getPassword()).thenReturn("123456");
        when(form.getPasswordConfirmation()).thenReturn("123456");
    }
    
    @Test
    public void shoudBeValid(){
        
        validator.validate(form, errors);
        
        verifyZeroInteractions(errors);
    }
    
    @Parameters({
        
    })
    @Test
    public void testValidNumbers(String number){
        
    }
    
    
}
