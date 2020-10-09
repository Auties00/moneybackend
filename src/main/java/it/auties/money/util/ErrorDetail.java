package it.auties.money.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class ErrorDetail {
    private final String title;
    private final String detail;
    private final long timeStamp;
    private final String developerMessage;
    private final String exception;
    private final int code;
    private final int subcode;
}
