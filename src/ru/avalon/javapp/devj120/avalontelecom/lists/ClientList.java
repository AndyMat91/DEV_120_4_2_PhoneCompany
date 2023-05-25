package ru.avalon.javapp.devj120.avalontelecom.lists;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import ru.avalon.javapp.devj120.avalontelecom.models.ClientInfo;
import ru.avalon.javapp.devj120.avalontelecom.models.PersonInfo;
import ru.avalon.javapp.devj120.avalontelecom.models.CompanyInfo;
import ru.avalon.javapp.devj120.avalontelecom.models.PhoneNumber;

/**
 * Manages list of clients.
 */
public class ClientList {
    /**
     * The only instance of this class.
     *
     * @see #getInstance
     */
    private static final ClientList instance = new ClientList();
    /**
     * Keeps the list of clients. Clients are stored in the same order,
     * which they have been registered in.
     */
    private static List<ClientInfo> clients;
    private static List<ClientInfo> company;
    /**
     * List of client phone numbers. The list is used by {@link #addClient} method to check,
     * that specified phone number is not used.
     * This set is updated simultaneously with {@link #clients} list, when
     * clients are {@linkplain #addClient added} or {@linkplain # removed}.
     */
    private static Set<PhoneNumber> numbersPerson;
    private static Set<PhoneNumber> numbersCompany;

    /**
     * Prevents instance creation out of the class.
     */
    private ClientList() {
    }

    /**
     * Adds client with specified attributes. Creates instance of {@code ClientInfo}
     * (see {@link PersonInfo#PersonInfo(PhoneNumber, String, String, String)}) while adding new client.
     *
     * @param number  client phone number
     * @param name    client name
     * @param address client address
     * @throws IllegalArgumentException If some client with specified phone number
     *                                  has already been registered.
     */
    public void addClient(PhoneNumber number, String name, String address, String dateOfBirth) {
        if (numbersPerson.contains(number))
            throw new IllegalArgumentException("Such a number has already been registered earlier.");
        clients.add(new PersonInfo(number, name, address, dateOfBirth));
        numbersPerson.add(number);
    }

    public void addCompany(PhoneNumber number, String name, String address, String directorName, String contactPerson) {
        if (numbersCompany.contains(number))
            throw new IllegalArgumentException("Such a number has already been registered earlier.");
        company.add(new CompanyInfo(number, name, address, directorName, contactPerson));
        numbersCompany.add(number);
    }

    /**
     * Removes client with the specified index.
     *
     * @param index index of a client to be removed
     * @throws IndexOutOfBoundsException If the index is out of range (index < 0 || index >= {@link #getClientsCount}).
     */
    public void removeClient(int index) {
        ClientInfo personInfo = clients.get(index);
        numbersCompany.remove(personInfo.getPhoneNumber());
        clients.remove(index);
    }

    public void removeCompany(int index) {
        ClientInfo companyInfo = company.get(index);
        numbersPerson.remove(companyInfo.getPhoneNumber());
        company.remove(index);
    }

    /**
     * Returns amount of clients, kept by the list.
     *
     * @return Number of clients, kept by the list.
     */
    public int getClientsCount() {
        return clients.size();
    }

    public int getCompanyCount() {
        return company.size();
    }

    /**
     * Returns information about a client with specified index.
     *
     * @param index client index, which data is retrieved
     * @return {@code ClientInfo}
     * @throws IndexOutOfBoundsException If the index is out of range (index < 0 || index >= {@link #getClientsCount}).
     */
    public ClientInfo getClientInfo(int index) {
        return clients.get(index);
    }

    public ClientInfo getCompanyInfo(int index) {
        return company.get(index);
    }

    /**
     * Returns the only instance of this class.
     */
    public static ClientList getInstance() {
        return instance;
    }

    public void savePerson() throws IOException {
        File p = new File("Person.dat");
        FileOutputStream fos = new FileOutputStream(p);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(clients);
        oos.close();
    }

    public void saveCompany() throws IOException {
        File c = new File("Company.dat");
        FileOutputStream fos = new FileOutputStream(c);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(company);
        oos.close();
    }

    public static void loadPerson() {
        File c = new File("Person.dat");
        try {
            if (c.exists()) {
                FileInputStream fis = new FileInputStream(c);
                ObjectInputStream ois = new ObjectInputStream(fis);
                clients = (List<ClientInfo>) ois.readObject();
                numbersPerson = new HashSet<>();
                for (ClientInfo x : clients) {
                    numbersPerson.add(x.getPhoneNumber());
                }
                ois.close();
            } else {
                clients = new ArrayList<>();
                numbersPerson = new HashSet<>();
            }
        } catch (IOException ignored) {
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void loadCompany() {
        File c = new File("Company.dat");
        try {
            if (c.exists()) {
                FileInputStream fis = new FileInputStream(c);
                ObjectInputStream ois = new ObjectInputStream(fis);
                company = (List<ClientInfo>) ois.readObject();
                numbersCompany = new HashSet<>();
                for (ClientInfo x : company) {
                    numbersCompany.add(x.getPhoneNumber());
                }
                ois.close();
            } else {
                company = new ArrayList<>();
                numbersCompany = new HashSet<>();
            }
        } catch (IOException ignored) {
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
