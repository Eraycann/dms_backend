package org.gokdemir.dms.exception;

public class BaseException extends RuntimeException{

    public BaseException(ErrorMessage errorMessage) {
        super(errorMessage.prepareErrorMessage());
    }
}