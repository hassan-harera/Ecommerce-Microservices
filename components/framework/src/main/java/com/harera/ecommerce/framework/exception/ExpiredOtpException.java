package com.harera.ecommerce.framework.exception;

import lombok.Getter;

import org.springframework.web.bind.annotation.ResponseStatus;

import com.harera.ecommerce.framework.util.ErrorCode;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Getter
@ResponseStatus(value = BAD_REQUEST)
public class ExpiredOtpException extends RuntimeException {

    private final String code;

    public ExpiredOtpException(
            String mobile,
            String otp
    ) {
        super(String.format("otp %s is expired", otp, mobile));
        code = ErrorCode.EXPIRED_OTP;
    }
}
