package com.suri.springboot.exception;

/**
 *
 * Error class created to log customised messages.
 *
 * @author lakshay13@gmail.com
 */
public class CustomisedErrorType {

    private String errorMessage;

    public CustomisedErrorType(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
