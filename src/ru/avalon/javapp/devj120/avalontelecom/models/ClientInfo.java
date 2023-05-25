package ru.avalon.javapp.devj120.avalontelecom.models;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Keeps information about a company client.
 */
public class ClientInfo implements Serializable {
	/**
	 * Phone number.
	 */
    private final PhoneNumber phoneNumber;
    /**
     * Registration date. The field is filled by {@linkplain ClientInfo#ClientInfo constructor}.
     */
    private final LocalDate regDate;
    /**
     * Client name.
     */
    private String name;
    /**
     * Client address.
     */
    private String address;

    /**
     * Initializes instance attributes with values from corresponding constructor parameters.
     * Sets client registration date to current (today) date. 
     * 
     * @exception IllegalArgumentException If either {@code phoneNumber}, or {@code name}, 
     * 		or {@code address} is {@code null}.
     */
    public ClientInfo(PhoneNumber phoneNumber, String name, String address) {
    	if(phoneNumber == null)
    		throw new IllegalArgumentException("phone number can't be null.");
        this.phoneNumber = phoneNumber;
        this.regDate = LocalDate.now();
        setName(name);
        setAddress(address);
    }

    /**
     * Returns client name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets client name.
     * 
     * @exception IllegalArgumentException if {@code address} is {@code null}.
     */
    public void setName(String name) {
    	if(name == null)
    		throw new IllegalArgumentException("name can't be null.");
        this.name = name;
    }

    /**
     * Returns client address.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets client address.
     * 
     * @exception IllegalArgumentException if {@code address} is {@code null}.
     */
    public void setAddress(String address) {
    	if(address == null)
    		throw new IllegalArgumentException("address can't be null.");
        this.address = address;
    }
    
    /**
     * Returns client phone number.
     * This attribute has no setter.
     */
    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Returns registration date.
     * This attribute has no setter.
     */
    public LocalDate getRegDate() {
        return regDate;
    }
}
