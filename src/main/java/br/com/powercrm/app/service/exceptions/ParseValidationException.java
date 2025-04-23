package br.com.powercrm.app.service.exceptions;

public class ParseValidationException extends RuntimeException{

    public ParseValidationException(String message){
        super(message);
    }
}
