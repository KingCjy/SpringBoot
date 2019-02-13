package com.kingcjy.main.exception;

import com.kingcjy.main.domain.ApiResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<ApiResult<Void>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<FieldError> errorList = new ArrayList<>();
        errorList.addAll(e.getBindingResult().getFieldErrors());

        if(errorList.size() > 0) {
            FieldError error = errorList.get(0);
            String message = new StringBuilder()
                    .append(error.getField())
                    .append("은(는) ")
                    .append(error.getDefaultMessage())
                    .toString();
            return ApiResult.getResponse(HttpStatus.BAD_REQUEST, message);
        } else {
            return ApiResult.getResponse(HttpStatus.BAD_REQUEST);
        }
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public ResponseEntity<ApiResult<Void>> handleHpptMessageNotReadableException(HttpMessageNotReadableException e) {
        return ApiResult.getResponse(HttpStatus.BAD_REQUEST, e.getMessage());
    }
}
