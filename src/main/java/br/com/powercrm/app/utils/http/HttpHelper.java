package br.com.powercrm.app.utils.http;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;

public class HttpHelper {

    private HttpHelper(){}


    public static String getPathUrlFromRequest(HttpServletRequest httpServletRequest){
        return httpServletRequest.getRequestURI();
    }

    public static int getStatusCodeValue(HttpStatus httpStatus){
        return httpStatus.value();
    }
}
