package com.PH39949.TestXuong.dto;

import com.PH39949.TestXuong.utils.ApiResponseCode;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ApiResponse<T>{

    private int code;
    private String message;
    private T data;

    public ApiResponse(ApiResponseCode apiResponseCode, T data) {
        this.code =  apiResponseCode.getCode();
        this.message = apiResponseCode.getMessage();
        this.data = data;
    }
}
