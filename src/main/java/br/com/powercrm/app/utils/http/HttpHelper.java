package br.com.powercrm.app.utils.http;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

public class HttpHelper {

    private HttpHelper(){}


    public static String getPathUrlFromRequest(HttpServletRequest httpServletRequest){
        return httpServletRequest.getRequestURI();
    }

    public static int getStatusCodeValue(HttpStatus httpStatus){
        return httpStatus.value();
    }

    public static<T>ResponseEntity<T> ok(T data){
        return ResponseEntity.status(HttpStatus.OK).body(data);
    }

    public static<T>ResponseEntity<T> created(URI uri, T data){
        return ResponseEntity.created(uri).body(data);
    }

    public static<T> ResponseEntity<T> badRequest(T data){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(data);
    }

    public static<T> ResponseEntity<T> unprocessedEntity(T data){
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(data);
    }

    public static<T> ResponseEntity<T> notFound(T data){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(data);
    }

    public static<Void> ResponseEntity<Void> noContent(){
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    public static URI makeURI(Object id){
        return ServletUriComponentsBuilder.fromCurrentRequest().buildAndExpand("/{id}", id).toUri();
    }

}
