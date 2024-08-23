package com.PH39949.TestXuong.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter

public enum ApiResponseCode {
    SUCCESS(200, "Success"),
    ERROR(500, "Internal Server Error"),
    NOT_FOUND(404, "Not Found"),
    BAD_REQUEST(400, "Bad Request"),
    DUPLICATE_CODE(409, "Duplicate Staff Code Found"),
    DUPLICATE_FE(409, "Duplicate Account FE Found"),
    DUPLICATE_FPT(409, "Duplicate Account FPT Found"),
    MAX_CODE(410,"Staff code max length 15")
    ;

    private final int code;
    private final String message;
}
