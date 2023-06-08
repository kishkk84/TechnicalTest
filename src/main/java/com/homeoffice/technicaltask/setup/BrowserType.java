package com.homeoffice.technicaltask.setup;

public enum BrowserType {
    Chrome("Chrome"),
    Firefox("Firefox");

    private final String value;

    BrowserType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
