package com.akhilesh.LifeFund.exceptions;

public class GoogleLoginRequiredException extends RuntimeException {

    public GoogleLoginRequiredException(String message) {
        super(message);
    }

}