package com.teama.requestsubsystem;

// for use by controller to generate appropriate form
public enum RequestType {
    FOOD("Food"), SEC("Security"), TRANS("Transportation"), INTR("Interpreter"), MAIN("Maintenance");

    private final String value;

    private RequestType(String value) {
        this.value = value;
    }

    public String toString(){
        return this.value;
    }

}