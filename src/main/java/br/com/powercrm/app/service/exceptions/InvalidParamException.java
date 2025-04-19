package br.com.powercrm.app.service.exceptions;

public class InvalidParamException extends RuntimeException{

    public InvalidParamException(String message){
        super(message);
    }
}
