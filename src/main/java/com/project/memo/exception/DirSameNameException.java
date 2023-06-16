package com.project.memo.exception;

import com.project.memo.util.response.ResponseMsg;
import com.project.memo.util.response.StatusCode;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
/**
 * <pre>
 *     디렉토리 이름 같은 경우에 에러
 *     error code: 606
 * </pre>
 * @author hyerimkim108
 */
@Getter
@Slf4j
public class DirSameNameException extends RuntimeException {
    private int errorCode;

    public DirSameNameException() {
        super(ResponseMsg.SAMENAME);
        this.errorCode = StatusCode.SAMENAME;
    }
}
