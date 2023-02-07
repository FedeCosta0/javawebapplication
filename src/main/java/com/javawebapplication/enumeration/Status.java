package com.javawebapplication.enumeration;

/*
  1) Richiesta creata e non ancora presa in carico
  2) Richiesta accettata, attesa dell'acconto
  3) Richiesta in lavorazione, lavorazione al disegno
  4) Disegno completato
  5) Appuntamento fissato


 */
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