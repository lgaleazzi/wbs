package wbs.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidObjectException extends RuntimeException {
    public InvalidObjectException(String s) {
        super(s);
    }
}
