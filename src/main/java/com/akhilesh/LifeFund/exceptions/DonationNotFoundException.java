package com.akhilesh.LifeFund.exceptions;

public class DonationNotFoundException extends RuntimeException {

    public DonationNotFoundException(String message) {
        super(message);
    }

}