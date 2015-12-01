package pl.gebickionline.exception;

import java.util.List;

/**
 * Created by Łukasz on 2015-11-28.
 */
public class BadRequestException extends RuntimeException {
    public BadRequestException(List<String> errors) {
        super(String.join(", ", errors));
    }
}
