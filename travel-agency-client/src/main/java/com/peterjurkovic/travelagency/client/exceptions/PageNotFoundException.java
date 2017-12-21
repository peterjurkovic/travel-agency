package com.peterjurkovic.travelagency.client.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class PageNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 5559465615733016451L;

}
