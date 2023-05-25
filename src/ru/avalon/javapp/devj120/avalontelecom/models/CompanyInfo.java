package ru.avalon.javapp.devj120.avalontelecom.models;

/**
 * Keeps information about a company client.
 */
public class CompanyInfo extends ClientInfo{
    private String directorName;
    private String contactPerson;
    public CompanyInfo(PhoneNumber phoneNumber, String name, String address, String directorName, String contactPerson) {
        super(phoneNumber, name, address);
        setDirectorName(directorName);
        setContactPerson(contactPerson);
    }
    public String getDirectorName() {
        return directorName;
    }

    public void setDirectorName(String directorName) {
        if(directorName == null)
            throw new IllegalArgumentException("date of birth can't be null.");
        this.directorName = directorName;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        if(contactPerson == null)
            throw new IllegalArgumentException("date of birth can't be null.");
        this.contactPerson = contactPerson;
    }
}
