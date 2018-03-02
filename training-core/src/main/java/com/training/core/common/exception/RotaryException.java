package com.training.core.common.exception;

import java.util.HashMap;
import java.util.Map;

public class RotaryException extends RuntimeException {
    public Map<String,String> dataMap = new HashMap<String,String>();

    public RotaryException() {
        super();
    }

    public RotaryException(String message) {
        super(message);
    }

    public RotaryException(String dataKey,String dataValue,String message) {
        super(message);
        dataMap.put(dataKey,dataValue);
    }

    public RotaryException(String message, Throwable cause) {
        super(message, cause);
    }

    public RotaryException(Throwable cause) {
        super(cause);
    }

    protected RotaryException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
