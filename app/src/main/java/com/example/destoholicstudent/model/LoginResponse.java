package com.example.destoholicstudent.model;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("status")
    public boolean status;
    @SerializedName("message")
    public String message;
    @SerializedName("data")
    public LoginRequest data;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LoginRequest getData() {
        return data;
    }

    public void setData(LoginRequest data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}