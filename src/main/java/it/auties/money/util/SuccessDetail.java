package it.auties.money.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class SuccessDetail {
    private final String title;
    private final String detail;
    private final long timeStamp;
}
