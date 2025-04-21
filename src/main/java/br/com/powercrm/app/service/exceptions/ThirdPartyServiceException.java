package br.com.powercrm.app.service.exceptions;

public class ThirdPartyServiceException extends RuntimeException{

    public ThirdPartyServiceException(String message){
        super(message);
    }
}
