package ru.avalon.javapp.devj120.avalontelecom.ui;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;

import javax.swing.*;

import ru.avalon.javapp.devj120.avalontelecom.lists.ClientList;
import ru.avalon.javapp.devj120.avalontelecom.models.ClientInfo;
import ru.avalon.javapp.devj120.avalontelecom.models.CompanyInfo;
import ru.avalon.javapp.devj120.avalontelecom.models.PersonInfo;
import ru.avalon.javapp.devj120.avalontelecom.models.PhoneNumber;

/**
 * Application main window.
 */
public class MainFrame extends JFrame {
    private final ClientListTableModel clientsTableModel = new ClientListTableModel();
    private final CompanyListTableModel companyTableModel = new CompanyListTableModel();
    private final JTable clientsTable = new JTable();
    private final ClientDialog clientDialog = new ClientDialog(this);
    private final JCheckBox checkbox = new JCheckBox("Company");

    public MainFrame(){
        super("AvalonTelecom Ltd. clients list");

        initMenu();
        initLayout();
        ClientList.loadCompany();
        ClientList.loadPerson();


        setBounds(300, 200, 600, 400);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    }

    private void initMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu operations = new JMenu("Operations");
        operations.setMnemonic('O');
        menuBar.add(operations);
        menuBar.add(checkbox);

        addMenuItemTo(operations, "Add Person/Company", 'A', KeyStroke.getKeyStroke('A', InputEvent.ALT_DOWN_MASK), e -> addClient());

        addMenuItemTo(operations, "Change", 'C', KeyStroke.getKeyStroke('C', InputEvent.ALT_DOWN_MASK), e -> changeClient());

        addMenuItemTo(operations, "Delete", 'D', KeyStroke.getKeyStroke('D', InputEvent.ALT_DOWN_MASK), e -> delClient());

        setJMenuBar(menuBar);
    }

    /**
     * Auxiliary method, which creates menu item with specifies text, mnemonic and accelerator,
     * installs specified action listener to the item, and adds the item to the specified menu.
     *
     * @param parent menu, which the created item is added to
     */
    private void addMenuItemTo(JMenu parent, String text, char mnemonic, KeyStroke accelerator, ActionListener al) {
        JMenuItem mi = new JMenuItem(text, mnemonic);
        mi.setAccelerator(accelerator);
        mi.addActionListener(al);
        parent.add(mi);
    }

    private void initLayout() {
        clientsTable.setModel(clientsTableModel);
        clientsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        add(clientsTable.getTableHeader(), BorderLayout.NORTH);
        add(new JScrollPane(clientsTable), BorderLayout.CENTER);

        checkbox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    clientsTable.setModel(companyTableModel);
                } else {
                    clientsTable.setModel(clientsTableModel);
                }
            }
        });

        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if (JOptionPane.showConfirmDialog(MainFrame.this,
                        "Are you sure you want to close this window?", "Close Window?",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
                    try {
                        clientsTableModel.savePerson();
                        companyTableModel.saveCompany();

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    System.exit(0);
                }
            }
        });
    }


    private void addClient() {
        if (checkbox.isSelected()) {
            clientDialog.prepareForAddCompany();
            while (clientDialog.showModal()) {
                try {
                    PhoneNumber pn = new PhoneNumber(clientDialog.getAreaCode(), clientDialog.getPhoneNum());
                    companyTableModel.addCompany(pn, clientDialog.getClientName(), clientDialog.getClientAddr(), clientDialog.getDirectorName(), clientDialog.getContactPerson());
                    return;
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Client registration error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            clientDialog.prepareForAddPerson();
            while (clientDialog.showModal()) {
                try {
                    PhoneNumber pn = new PhoneNumber(clientDialog.getAreaCode(), clientDialog.getPhoneNum());
                    clientsTableModel.addClient(pn, clientDialog.getClientName(), clientDialog.getClientAddr(), clientDialog.getDateOfBirth());
                    return;
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Client registration error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private void changeClient() {
        int seldRow = clientsTable.getSelectedRow();
        if (seldRow == -1) return;

        if (checkbox.isSelected()) {
            ClientInfo ci = companyTableModel.getClient(seldRow);
            clientDialog.prepareForChangeCompany((CompanyInfo) ci);
            if (clientDialog.showModal()) {
                ci.setName(clientDialog.getClientName());
                ci.setAddress(clientDialog.getClientAddr());
                ((CompanyInfo) ci).setDirectorName(clientDialog.getDirectorName());
                ((CompanyInfo) ci).setContactPerson(clientDialog.getContactPerson());
                companyTableModel.clientChanged(seldRow);
            }
        } else {
            ClientInfo ci = clientsTableModel.getClient(seldRow);
            clientDialog.prepareForChangePerson((PersonInfo) ci);
            if (clientDialog.showModal()) {
                ci.setName(clientDialog.getClientName());
                ci.setAddress(clientDialog.getClientAddr());
                ((PersonInfo) ci).setDateOfBirth(clientDialog.getDateOfBirth());
                clientsTableModel.clientChanged(seldRow);
            }
        }
    }

    private void delClient() {
        int seldRow = clientsTable.getSelectedRow();
        if (seldRow == -1) return;
        if (checkbox.isSelected()) {
            ClientInfo ci = companyTableModel.getClient(seldRow);
            if (JOptionPane.showConfirmDialog(this, "Are you sure you want to delete client\n" + "with phone number " + ci.getPhoneNumber() + "?", "Delete confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                companyTableModel.dropClient(seldRow);
            }
        } else {
            ClientInfo cii = clientsTableModel.getClient(seldRow);
            if (JOptionPane.showConfirmDialog(this, "Are you sure you want to delete client\n" + "with phone number " + cii.getPhoneNumber() + "?", "Delete confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                clientsTableModel.dropClient(seldRow);
            }
        }
    }
}