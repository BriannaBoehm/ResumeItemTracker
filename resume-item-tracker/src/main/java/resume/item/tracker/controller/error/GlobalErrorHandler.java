package resume.item.tracker.controller.error;

import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalErrorHandler {
	
	@ExceptionHandler(NoSuchElementException.class) //handles errors from the NSEE class and returns a message 
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	public Map<String,String> handleNoSuchElementException(NoSuchElementException ex){
		log.info("This element is not found.");
		Map<String,String> errorMessage = new HashMap<String,String>();
		errorMessage.put("message:", ex.toString());
		return errorMessage;
	}

}
