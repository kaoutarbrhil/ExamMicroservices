package org.example.servicenotifications.model;

public class Notification {

    private Long commandeId;
    private String message;

    public Notification() {
    }

    public Notification(Long commandeId, String message) {
        this.commandeId = commandeId;
        this.message = message;
    }

    // Getters et setters
    public Long getCommandeId() {
        return commandeId;
    }

    public void setCommandeId(Long commandeId) {
        this.commandeId = commandeId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
