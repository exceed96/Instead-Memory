package com.project.memo.exception;

import com.project.memo.util.response.ResponseMsg;
import com.project.memo.util.response.StatusCode;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 *     리프레시 토큰이 만료된 경우 발생
 *  *     error code: 392
 * </pre>
 * @author hyerimkim108
 */
@Getter
@Slf4j
public class RefreshExpiredException extends RuntimeException {
    private int errorCode;

    public RefreshExpiredException() {
        super(ResponseMsg.NO_REFRESHTOKEN);
        this.errorCode = StatusCode.UNREFRESHTOKEN;
    }
}
