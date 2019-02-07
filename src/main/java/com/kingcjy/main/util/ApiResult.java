package com.kingcjy.main.util;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Timestamp;
import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResult<T> {
    private Meta meta;
    private T body;

    @Data
    private class Meta {
        private Integer status;
        private String message;
        private String timestamp;
    }
    @JsonIgnore
    public ApiResult(HttpStatus httpStatus, String message, T body) {
        this.meta = new Meta();
        this.meta.setStatus(httpStatus.value());
        this.meta.setMessage(message);
        this.meta.setTimestamp(new Timestamp(new Date().getTime()).toString());

        this.body = body;
    }

    @JsonIgnore
    public static <T> ResponseEntity<ApiResult<T>> getResponse(HttpStatus httpStatus) {
        return getResponse(httpStatus, "", null);
    }
    @JsonIgnore
    public static <T> ResponseEntity<ApiResult<T>> getResponse(HttpStatus httpStatus, String message) {
        return getResponse(httpStatus, message, null);
    }
    @JsonIgnore
    public static <T> ResponseEntity<ApiResult<T>> getResponse(HttpStatus httpStatus, T body) {
        return getResponse(httpStatus, "", body);
    }
    @JsonIgnore
    public static <T> ResponseEntity<ApiResult<T>> getResponse(HttpStatus httpStatus, String message, T body) {
        return new ResponseEntity<>(new ApiResult<>(httpStatus, message, body), httpStatus);
    }
}
