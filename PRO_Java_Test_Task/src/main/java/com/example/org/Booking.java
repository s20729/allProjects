package com.example.org;

import java.time.LocalDateTime;

public class Booking {
    private String status;

    public int id;

    private LocalDateTime arrivalDate;

    public  LocalDateTime departureDate;

    public String reference;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(LocalDateTime arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public LocalDateTime getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDateTime departureDate) {
        this.departureDate = departureDate;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Booking() {
    }

    public Booking(String status, int id, LocalDateTime arrivalDate, LocalDateTime departureDate, String reference) {
        this.status = status;
        this.id = id;
        this.arrivalDate = arrivalDate;
        this.departureDate = departureDate;
        this.reference = reference;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "status='" + status + '\'' +
                ", id=" + id +
                ", arrivalDate=" + arrivalDate +
                ", departureDate=" + departureDate +
                ", reference='" + reference + '\'' +
                '}';
    }
}
