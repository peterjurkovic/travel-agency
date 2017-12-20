package com.peterjurkovic.travelagency.common.model;

public enum Country {

    GB("United Kingdom"),
    CZ("Czechia"),
    US("United States");

    private final String countryName;

    private Country(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryName() {
        return countryName;
    }

}
