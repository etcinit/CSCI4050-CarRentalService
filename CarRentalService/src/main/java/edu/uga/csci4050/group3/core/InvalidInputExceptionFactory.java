package edu.uga.csci4050.group3.core;

import java.util.Set;

import javax.validation.ConstraintViolation;

/**
 * Simple class for constructing validation exceptions from the result of a validator
 * @author delta6
 *
 * @param <E> Object class that was validated
 */
public class InvalidInputExceptionFactory<E> {
	
	public InvalidInputException buildException(Set<ConstraintViolation<E>> violations){
		InvalidInputException ex = new InvalidInputException();
		
		for(ConstraintViolation<E> violation : violations){
			ex.addMessage(violation.getMessage());
		}
		
		return ex;
	}
}
