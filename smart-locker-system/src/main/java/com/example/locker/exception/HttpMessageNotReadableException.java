// size가 "HUGE"
// GlobalExceptionHandler에서 400 처리

package com.example.locker.exception;

public class HttpMessageNotReadableException
    extends RuntimeException{

        public HttpMessageNotReadableException(){
            super("잘못된 HTTP 요청 메세지 입니다.");
        }
    }