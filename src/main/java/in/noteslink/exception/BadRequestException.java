package in.noteslink.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//BadRequestException is a custom exception you throw when:
//The client sends invalid input, and
//The server cannot process the request as it is.

@ResponseStatus(HttpStatus.BAD_REQUEST) //It tells, Whenever  BadRequestException is thrown, respond with HTTP 400
public class BadRequestException extends  RuntimeException{
    public  BadRequestException(String message){    //Constructor accepts a human-readable message and sends to client
        super(message);         //Passes the message to RuntimeException. So it’s accessible via getMessage().
    }
}
