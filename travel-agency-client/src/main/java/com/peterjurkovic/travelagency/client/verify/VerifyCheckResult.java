package com.peterjurkovic.travelagency.client.verify;


public class VerifyCheckResult {
    
    private enum Status{OK, FAILED}
    
    private final String message;
    private final Status status;
    private final String redirectUrl;
    
    public VerifyCheckResult(String message, Status status, String redirectUrl) {
        this.message = message;
        this.status = status;
        this.redirectUrl = redirectUrl;
    }

    public static VerifyCheckResult ok(String redirectUrl){
        return new VerifyCheckResult("OK", Status.OK, redirectUrl);
    }
    
    public static VerifyCheckResult fail(String message){
        return new VerifyCheckResult(message, Status.FAILED, null);
    }

    public String getMessage() {
        return message;
    }

    public Status getStatus() {
        return status;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }
    
    
}
