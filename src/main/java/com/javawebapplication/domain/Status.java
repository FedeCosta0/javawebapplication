package com.javawebapplication.domain;

public enum Status {
    REQUEST_PENDING("Richiesta non ancora presa in carico"),
    REQUEST_ACCEPTED("Richiesta accettata, in attesa dell'acconto"),
    DRAWING_IN_PROGRESS("Disegno in lavorazione"),
    DRAWING_COMPLETED("Disegno completato"),
    APPOINTMENT_MADE("Appuntamento fissato");

    private final String status;

    Status(String status) {
        this.status = status;
    }



    public String getStatus() {
        return this.status;
    }
}