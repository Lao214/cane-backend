package com.example.integratedHub.handler;

import com.example.integratedHub.entity.enumVo.ErrorCode;
import com.example.integratedHub.utils.Result;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Result> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        // 这里可以自定义错误信息
        String errorMessage = "登录失败，找不到用户名！";
        Result code = Result.error().msg(errorMessage).code(ErrorCode.AUTH_FAILURE.getCode());
        // 返回给前端的错误信息，状态码可以根据需要调整
        return new ResponseEntity<>(code, HttpStatus.UNAUTHORIZED);
    }


    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Result> handleBadCredentialsException(BadCredentialsException ex) {
        // 这里可以自定义错误信息
        String errorMessage = "登录失败，用户名或密码错误！";
        Result code = Result.error().msg(errorMessage).code(ErrorCode.AUTH_FAILURE.getCode());
        // 返回给前端的错误信息，状态码可以根据需要调整
        return new ResponseEntity<>(code, HttpStatus.UNAUTHORIZED);
    }

    /*
     * Security框架权限校验失败异常捕获
     * */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Result> unauthorizedException(AccessDeniedException ex){
        log.error("无权限：" + ex.getLocalizedMessage());
        Result code = Result.error().msg(ErrorCode.UN_ACCESS.getMsg()).code(ErrorCode.UN_ACCESS.getCode());
        return new ResponseEntity<>(code,HttpStatus.UNAUTHORIZED);
    }

}