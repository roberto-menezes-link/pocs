package com.link.apache.camel.demo.routes.parallel;

public class Api2Response {
    private String status;

    // Getters e Setters
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Api2Response{" +
                "status='" + status + '\'' +
                '}';
    }
}
