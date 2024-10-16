package com.fernandogreca.login.exceptions;

public class EmailExistException extends Exception{
    
    private static final long serialVersionUID = 1L;

    public EmailExistException(String msg) {
        super(msg);
    }
}
