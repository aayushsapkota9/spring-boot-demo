package com.example.demo.dto;

public class CodeResponse {
    private String output;
    private boolean success;

    public CodeResponse(String output, boolean success) {
        this.output = output;
        this.success = success;
    }

    // Getters and Setters
    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}