package com.project.memo.exception;

import com.project.memo.util.response.ResponseMsg;
import com.project.memo.util.response.StatusCode;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 *     토큰이 만료된 경우 발생
 *     error code: 605
 * </pre>
 * @author hyerimkim108
 */
@Getter
@Slf4j
public class TokenExpiredException extends RuntimeException {
    private int errorCode;
    public TokenExpiredException() {
        super(ResponseMsg.NO_ACCESSTOKEN);
        this.errorCode = StatusCode.UNACCESSTOKEN;
    }
}