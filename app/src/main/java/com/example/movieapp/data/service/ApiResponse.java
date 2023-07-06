package com.example.movieapp.data.service;

public class ApiResponse {
    private boolean _success;
    private String _message;

    public ApiResponse(boolean success, String message){
        this._success = success;
        this._message = message;
    }

    public boolean isSuccess() {
        return _success;
    }

    public String getMessage() {
        return _message;
    }
}
