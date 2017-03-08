package wbs.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by livia on 05.03.17.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidObjectException extends RuntimeException {
    public InvalidObjectException(String s) {
        super(s);
    }
}
