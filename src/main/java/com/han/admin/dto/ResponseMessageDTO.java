package com.han.admin.dto;

public class ResponseMessageDTO {
	
    private boolean success;
    private String message;

    public ResponseMessageDTO(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}