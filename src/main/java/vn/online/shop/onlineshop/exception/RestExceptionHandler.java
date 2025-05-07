package vn.online.shop.onlineshop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RestExceptionHandler extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public RestExceptionHandler(String message) {
        super(message);
    }

}