package com.pragma.plazoleta_microservice.configuration.exceptionhandler;

import com.pragma.plazoleta_microservice.adapters.driven.jpa.mysql.exception.OrderStateChangeException;
import com.pragma.plazoleta_microservice.adapters.driven.jpa.mysql.exception.RoleNotCorrectException;
import com.pragma.plazoleta_microservice.adapters.driven.jpa.mysql.exception.ValueAlreadyExistsException;
import com.pragma.plazoleta_microservice.configuration.Constants;
import com.pragma.plazoleta_microservice.domain.exceptions.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;


@ControllerAdvice
@RequiredArgsConstructor
public class ControllerAdvisor {
    public String getErrorMessage(FieldError error) {
        if (error == null) {
            return "Validation error";
        }
        String fieldName = error.getField();

        String code = error.getCode();

        if (code != null && code.equals("NotBlank")) {
            return String.format(Constants.VALUE_FORMAT, fieldName, Constants.EMPTY_FIELD_EXCEPTION_MESSAGE);
        } else if (code != null && code.equals("Size")) {
            return String.format(Constants.VALUE_FORMAT, fieldName, Constants.MAX_CHAR_EXCEPTION_MESSAGE);
        } else if (code != null && code.equals("Pattern")) {

            if (fieldName.equals("nit")) {
                return String.format(Constants.VALUE_FORMAT, fieldName, Constants.FIELD_NIT_NUMBER_PATTERN_EXCEPTION_MESSAGE);
            } else if (fieldName.equals("phone")) {
                return String.format(Constants.VALUE_FORMAT, fieldName, Constants.FIELD_PHONE_PATTERN_EXCEPTION_MESSAGE);
            }
        } else if (code != null && code.equals("NotNull")) {
            return String.format(Constants.VALUE_FORMAT, fieldName, Constants.EMPTY_FIELD_EXCEPTION_MESSAGE);
        } else {
            return error.getDefaultMessage();
        }
        return error.getDefaultMessage();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();

        if (result.hasFieldErrors()) {
            FieldError error = result.getFieldError();

            if (error != null) {
                String errorMessage = getErrorMessage(error);

                return ResponseEntity.badRequest().body(new ExceptionResponse(
                        errorMessage, HttpStatus.BAD_REQUEST.toString(), LocalDateTime.now()));
            }
        }
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(ValueAlreadyExistsException.class)
    public ResponseEntity<ExceptionResponse> handleValueAlreadyExitsException(ValueAlreadyExistsException ex) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(
                ex.getMessage() + Constants.VALUE_ALREADY_EXISTS_EXCEPTION_MESSAGE, HttpStatus.BAD_REQUEST.toString(), LocalDateTime.now()));
    }

    @ExceptionHandler(NitRestaurantAlreadyExistsException.class)
    public ResponseEntity<ExceptionResponse> handleNitRestaurantAlreadyExistsException(){
        return ResponseEntity.badRequest().body(new ExceptionResponse(ConstantsDomain.NIT_ALREADY_EXISTS, HttpStatus.CONFLICT.toString(), LocalDateTime.now()));
    }

    @ExceptionHandler(DishNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleDishNotFoundException(){
        return ResponseEntity.badRequest().body(new ExceptionResponse(Constants.DISH_NOT_FOUND_EXCEPTION_MESSAGE, HttpStatus.CONFLICT.toString(), LocalDateTime.now()));
    }

    @ExceptionHandler(RestaurantNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleRestaurantNotFoundException(){
        return ResponseEntity.badRequest().body(new ExceptionResponse(Constants.RESTAURANT_NOT_FOUND_EXCEPTION_MESSAGE, HttpStatus.CONFLICT.toString(), LocalDateTime.now()));
    }



    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleUserNotFoundException(){
        return ResponseEntity.badRequest().body(new ExceptionResponse(Constants.USER_NOT_FOUND, HttpStatus.CONFLICT.toString(), LocalDateTime.now()));
    }

    @ExceptionHandler(OwnerNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleOwnerNotFoundException(){
        return ResponseEntity.badRequest().body(new ExceptionResponse(Constants.OWNER_NOT_FOUND, HttpStatus.CONFLICT.toString(), LocalDateTime.now()));
    }

    @ExceptionHandler(RoleNotCorrectException.class)
    public ResponseEntity<ExceptionResponse> handleRoleNotCorrectException(){
        return ResponseEntity.badRequest().body(new ExceptionResponse(Constants.ROLE_BAD, HttpStatus.CONFLICT.toString(), LocalDateTime.now()));
    }

    @ExceptionHandler(OrderStateChangeException.class)
    public ResponseEntity<ExceptionResponse> handleOrderStateChangeException(){
        return ResponseEntity.badRequest().body(new ExceptionResponse(Constants.ORDER_STATE_CHANGE_EXCEPTION, HttpStatus.CONFLICT.toString(), LocalDateTime.now()));
    }





}
