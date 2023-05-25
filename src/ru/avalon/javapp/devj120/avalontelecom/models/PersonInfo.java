package ru.avalon.javapp.devj120.avalontelecom.models;

import java.time.LocalDate;
import java.time.Period;

public class PersonInfo extends ClientInfo {
    private String dateOfBirth;

    public PersonInfo(PhoneNumber phoneNumber, String name, String address, String dateOfBirth) {
        super(phoneNumber,name,address);
        setDateOfBirth(dateOfBirth);
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getAge() {
            return String.valueOf(Period.between(LocalDate.parse(dateOfBirth), LocalDate.now()).getYears());
        }

    public void setDateOfBirth(String dateOfBirth) {
        if(dateOfBirth == null)
            throw new IllegalArgumentException("date of birth can't be null.");
        this.dateOfBirth = dateOfBirth;
    }
}
