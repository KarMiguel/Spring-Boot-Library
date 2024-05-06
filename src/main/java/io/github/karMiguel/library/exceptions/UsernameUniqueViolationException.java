package io.github.karMiguel.library.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UsernameUniqueViolationException extends RuntimeException{
    public UsernameUniqueViolationException() {
        super("Already registered username.");
    }

    public UsernameUniqueViolationException(String ex) {
        super(ex);
    }

}
