package ru.itmo.soalab2.model;

public class OperationResponse {
    private Long humanBeingId;
    private String message;

    public OperationResponse(Long humanBeingId, String message) {
        this.humanBeingId = humanBeingId;
        this.message = message;
    }

    public Long getHumanBeingId() {
        return humanBeingId;
    }

    public void setHumanBeingId(Long humanBeingId) {
        this.humanBeingId = humanBeingId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
