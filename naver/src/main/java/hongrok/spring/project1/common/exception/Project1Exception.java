package hongrok.spring.project1.common.exception;

import hongrok.spring.project1.common.Constants;
import org.springframework.http.HttpStatus;

public class Project1Exception extends Exception{

    private static final long serialVersionUID = 44444444;

    private Constants.ExceptionClass exceptionClass;
    private HttpStatus httpStatus;

    public Project1Exception(Constants.ExceptionClass exceptionClass, HttpStatus httpStatus, String message) {
        super(exceptionClass.toString() + message);
        this.exceptionClass = exceptionClass;
        this.httpStatus = httpStatus;
    }

    public Constants.ExceptionClass getExceptionClass() {
        return exceptionClass;
    }

    public int getHttpStatusCode() {
        return httpStatus.value();
    }
    public String getHttpStatusType(){
        return httpStatus.getReasonPhrase();
    }
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
