package com.peterjurkovic.travelagency.client.verify;

import lombok.Data;

public @Data class VerifyCheckResult {
    
    private enum Status{OK, FAILED}
    
    private final String message;
    private final Status status;
    private final String redirectUrl;
    
    public static VerifyCheckResult ok(String redirectUrl){
        return new VerifyCheckResult("OK", Status.OK, redirectUrl);
    }
    
    public static VerifyCheckResult fail(String message){
        return new VerifyCheckResult(message, Status.FAILED, null);
    }
}
