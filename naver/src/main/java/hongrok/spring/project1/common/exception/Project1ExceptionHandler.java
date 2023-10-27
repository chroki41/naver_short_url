package hongrok.spring.project1.common.exception;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;
//import java.util.logging.Logger;

public class Project1ExceptionHandler {

    private final Logger LOGGER = LoggerFactory.getLogger(Project1ExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Map<String, String>> ExceptionHandler(Exception e) {
        HttpHeaders responseHeaders = new HttpHeaders();

        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        LOGGER.info(e.getMessage());
        //LOGGer.info(e.getLocalizedMessage());
        LOGGER.info("Advice 내 Exception Handler 호출");

        Map<String, String> map =new HashMap<>();
        map.put("error type", httpStatus.getReasonPhrase());
        map.put("code", "400");
        map.put("message", "에러 발생");

        return new ResponseEntity<>(map, responseHeaders, httpStatus);
    }
    //해당 프로그램을 실행하면 controller의 exception handler가 호출됨: 이는 전체를 총괄하는 exception handler보다
    //controller 내의 exception handler가 더 높은 우선순위를 가지기 때문이다.
    @ExceptionHandler(value = Project1Exception.class)
    public ResponseEntity<Map<String, String>> ExceptionHandler(Project1Exception e) {
        HttpHeaders responseHeaders = new HttpHeaders();

        Map<String, String> map = new HashMap<>();
        map.put("error type", e.getHttpStatusType());
        map.put("error code", Integer.toString(e.getHttpStatusCode()));
        map.put("message", e.getMessage());

        return new ResponseEntity<>(map, responseHeaders, e.getHttpStatus());
    }
}
