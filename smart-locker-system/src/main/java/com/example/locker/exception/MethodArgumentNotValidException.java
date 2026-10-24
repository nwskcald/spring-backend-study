// userId가 공백
// GlobalExceptionHandler에서 400 처리

package com.example.locker.exception;

public class MethodArgumentNotValidException
    extends RuntimeException{

        public MethodArgumentNotValidException(){
            super("빈 HTTP 요청 메세지 입니다.");
        }
    }