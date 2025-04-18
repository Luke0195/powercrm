package br.com.powercrm.app.service.exceptions;

public class EntityAlreadyExistsException extends RuntimeException{

    public EntityAlreadyExistsException(String message){
        super(message);
    }
}
