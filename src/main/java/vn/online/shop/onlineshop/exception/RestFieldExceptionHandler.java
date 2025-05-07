package vn.online.shop.onlineshop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class RestFieldExceptionHandler extends RuntimeException {
	@Serial
	private static final long serialVersionUID = 1L;

	public RestFieldExceptionHandler(@NonNull String field, @NonNull String message) {
		super("{\"field\": \"" + field + "\", \"message\": \"" + message + "\"}");
	}
}
