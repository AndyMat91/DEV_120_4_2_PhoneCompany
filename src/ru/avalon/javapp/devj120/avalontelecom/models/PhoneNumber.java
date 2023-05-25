package ru.avalon.javapp.devj120.avalontelecom.models;
import java.io.Serializable;
import java.util.Objects;

/**
 * Encapsulates phone number, which consists of area code and local number.
 * Both parts of the phone number must consist of digits only. 
 */
public class PhoneNumber implements Serializable {
	/**
	 * Phone number area code.
	 */
    private final String areaCode;
    /**
     * Phone number local number.
     */
    private final String localNum;
    /**
     * String representation of the phone number.
     * The value is returned by {@link #toString()}, and 
     * it is initialized by the {@code toString()} first call. 
     */
    private String strVal;

    /**
     * Initializes instance with specified area code and phone number.
     * 
     * @param areaCode phone number area code
     * @param localNum phone number local number
     * 
     * @throws IllegalArgumentException If either area code or local number are {@code null},
     * 		or empty strings, or contain characters other than digits. 
     */
    public PhoneNumber(String areaCode, String localNum) {
        checkArg(areaCode, "area code");
        checkArg(localNum, "local number");
        this.areaCode = areaCode;
        this.localNum = localNum;
    }
    
    /**
     * Checks specified {@code value}, and throws {@code IllegalArgumentException}, 
     * if it is {@code null}, or empty string, or contains characters other than digits.
     * 
     * @param value value to check
     * @param field specifies value kind, which is used when exception message is generated 
     */
    private void checkArg(String value, String field) {
        if(value == null || value.isEmpty())
            throw new IllegalArgumentException(field + " is null or empty.");
        for(int i = 0; i < value.length(); i++) {
            if(!Character.isDigit(value.charAt(i)))
                throw new IllegalArgumentException(field + " must consist of digits only.");
        }
    }
    
    /**
     * Returns phone number area code.
     * 
     * @return Phone number area code.
     */
    public String getAreaCode() {
        return areaCode;
    }

    /**
     * Returns phone number local number.
     * 
     * @return Phone number local number.
     */
    public String getLocalNum() {
        return localNum;
    }

    /**
     * Returns {@code true}, iff {@code o} is an instance of {@code PhoneNumber}
     * and it area code and local number are the same as area code and local number 
     * of {@code this} instance.
     */
    @Override
    public boolean equals(Object o) {
        if(!(o instanceof PhoneNumber))
            return false;
        PhoneNumber other = (PhoneNumber)o;
        return this.areaCode.equals(other.areaCode)
                && this.localNum.equals(other.localNum);
    }

    /**
     * Returns instance hash code, 
     * which is evaluated from number area code and local number.
     */
    @Override
    public int hashCode() {
        return Objects.hash(areaCode, localNum);
    }
    
    /**
     * Returns string representation of the phone number in form of 
     * "(<i>&lt;area code&gt;</i>)<i>&lt;phone number&gt;"</i>. 
     */
    @Override
    public String toString() {
        if(strVal == null) {
            StringBuilder sb = new StringBuilder(localNum);
            int p = sb.length() - 2;
            while(p > 1) {
                sb.insert(p, '-');
                p -= 2;
            }
            strVal = "(" + areaCode + ")" + sb.toString();
        }
        return strVal;
    }
}
