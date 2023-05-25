package ru.avalon.javapp.devj120.avalontelecom.ui;

import ru.avalon.javapp.devj120.avalontelecom.lists.ClientList;
import ru.avalon.javapp.devj120.avalontelecom.models.ClientInfo;
import ru.avalon.javapp.devj120.avalontelecom.models.CompanyInfo;
import ru.avalon.javapp.devj120.avalontelecom.models.PhoneNumber;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Clients list table model for the table of application main window. 
 */
public class CompanyListTableModel implements TableModel {
    private static final String[] COLUMN_HEADERS = new String[]{
            "Phone number",
            "Company name",
            "Client address",
			"Director name",
			"Contact person",
            "Registration date"
        };

    private final Set<TableModelListener> modelListeners = new HashSet<>();
    
	@Override
	public int getColumnCount() {
		return COLUMN_HEADERS.length;
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		switch(columnIndex) {
			case 0: 
				return PhoneNumber.class;
			case 1: 
			case 2:
			case 3:
			case 4:
				return String.class;
			case 5:
				return LocalDate.class;
		}
		throw new IllegalArgumentException("unknown columnIndex");
	}

	@Override
	public String getColumnName(int columnIndex) {
		return COLUMN_HEADERS[columnIndex];
	}

	@Override
	public int getRowCount() {
		return ClientList.getInstance().getCompanyCount();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		CompanyInfo ci = (CompanyInfo) ClientList.getInstance().getCompanyInfo(rowIndex);
		switch(columnIndex) {
			case 0: return ci.getPhoneNumber();
			case 1: return ci.getName();
			case 2: return ci.getAddress();
			case 3: return ci.getDirectorName();
			case 4: return ci.getContactPerson();
			case 5: return ci.getRegDate();
		}
		throw new IllegalArgumentException("unknown columnIndex");
	}

	/**
	 * Returns {@code false}, since in-cell editing is prohibited.
	 */
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	/**
	 * Does nothing, since in-cell editing is prohibited.
	 */
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        /* Nothing to do, since isCellEditable is always false. */
	}
	
	@Override
	public void addTableModelListener(TableModelListener l) {
        modelListeners.add(l);
	}

	@Override
	public void removeTableModelListener(TableModelListener l) {
        modelListeners.remove(l);
	}
	
	public ClientInfo getClient(int rowNdx) {
		return ClientList.getInstance().getCompanyInfo(rowNdx);
	} 
	
	/**
	 * Registers new client. Adds new client by calling of {@link ClientList#addClient}
	 * and notifies model listeners about changes. 
	 */

	public void addCompany(PhoneNumber number, String companyName, String address, String directorName, String contactPerson) {
		ClientList.getInstance().addCompany(number, companyName, address, directorName, contactPerson);
		int rowNdx = ClientList.getInstance().getCompanyCount() - 1;
		fireTableModelEvent(rowNdx, TableModelEvent.INSERT);
	}
	
	/**
	 * Just notifies model listeners, that data of a client with specified index has been changed.
	 * 
	 * @param index index of a client in the client list, which data has been changed 
	 */
	public void clientChanged(int index) {
        fireTableModelEvent(index, TableModelEvent.UPDATE);
	}

	/**
	 * Removes client with the specified index. Notifies model listeners about removal.
	 *  
	 * @param index index of a client record to remove
	 */
	public void dropClient(int index) {
		ClientList.getInstance().removeCompany(index);
        fireTableModelEvent(index, TableModelEvent.DELETE);
	}

	/**
	 * Creates {@code TableModelEvent} of specified type (see {@link TableModelEvent} constants),
	 * and calls listeners to notify them about the change.
	 * 
	 * @param rowNdx index of table row, which is the reason of the event
	 * @param evtType event type; one of {@code TableModelEvent} constants 
	 * 		({@link TableModelEvent#UPDATE} for example)
	 */
    private void fireTableModelEvent(int rowNdx, int evtType) {
        TableModelEvent tme = new TableModelEvent(this, rowNdx, rowNdx,
                TableModelEvent.ALL_COLUMNS, evtType);
        for (TableModelListener l : modelListeners) {
            l.tableChanged(tme);
        }
    }

	public void saveCompany() throws IOException {
		ClientList.getInstance().saveCompany();
	}
}
