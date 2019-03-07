package com.application.message;

import java.util.Date;

public class ErrorMessage {
    private Date timestamp;
    private String reason;

    public ErrorMessage() {
    }

    public ErrorMessage(Date timestamp, String reason) {
        this.timestamp = timestamp;
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "ErrorMessage{" +
                "timestamp=" + timestamp +
                ", reason='" + reason + '\'' +
                '}';
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
