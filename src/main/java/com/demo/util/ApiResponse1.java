package com.demo.util;

import java.util.Map;

public class ApiResponse1 {
    private String status;
    private String message;
    private Map<String, Object> data;

    public ApiResponse1(String status, String message, Map<String, Object> data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public static ApiResponse1 success(String message, Map<String, Object> data) {
        return new ApiResponse1("success", message, data);
    }

    public static ApiResponse1 error(String message) {
        return new ApiResponse1("error", message, null);
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public Map<String, Object> getData() {
        return data;
    }
}
