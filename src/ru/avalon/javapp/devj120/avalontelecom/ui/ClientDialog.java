package ru.avalon.javapp.devj120.avalontelecom.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;


import javax.swing.*;

import ru.avalon.javapp.devj120.avalontelecom.models.CompanyInfo;
import ru.avalon.javapp.devj120.avalontelecom.models.PersonInfo;

/**
 * Dialog window for client attributes entering/editing.
 */
public class ClientDialog extends JDialog {
	private final JTextField areaCodeField;
	private final JTextField phoneNumField;
	private final JTextField clientNameField;
	private final JTextField clientAddrField;
	private final JTextField dateOfBirthField;
	private final JTextField directorNameField;
	private final JTextField contactPersonField;
	private JLabel lblDateOfBirth;
	private JLabel lblDirectorName;
	private JLabel lblContactPerson;
	
	/**
	 * Set to {@code true}, when a user closes the dialog with "OK" button. 
	 * This field value is returned by {@link #showModal} method.
	 */
	private boolean okPressed;
	
	/**
	 * Constructs the dialog.
	 *
	 * @param owner dialog window parent container
	 */
	public ClientDialog(JFrame owner) {
		super(owner, true);

		areaCodeField = new JTextField(3);
		phoneNumField = new JTextField(5);
		clientNameField = new JTextField(30);
		clientAddrField = new JTextField(30);
		dateOfBirthField = new JTextField(30);
		directorNameField =  new JTextField(30);
		contactPersonField = new JTextField(30);
		
		initLayout();
		
		setResizable(false);
	}

	/**
	 * Initializes dialog layout and puts required controls onto the dialog.
	 */
	private void initLayout() {
		initControls();
		initOkCancelButtons();
	}

	/**
	 * Creates layout for client information input fields and adds the fields
	 * to the dialog controls hierarchy.
	 */
	private void initControls() {
		JPanel controlsPane = new JPanel(null);
		controlsPane.setLayout(new BoxLayout(controlsPane, BoxLayout.Y_AXIS));
		
		JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel lbl = new JLabel("Phone number (");
		lbl.setLabelFor(areaCodeField);
		p.add(lbl);
		p.add(areaCodeField);
		lbl = new JLabel(")");
		lbl.setLabelFor(phoneNumField);
		p.add(lbl);
		p.add(phoneNumField);
		controlsPane.add(p);
		
		p = new JPanel(new FlowLayout(FlowLayout.LEFT));
		lbl = new JLabel("Customer name");
		lbl.setLabelFor(clientNameField);
		p.add(lbl);
		p.add(clientNameField);
		controlsPane.add(p);
		
		p = new JPanel(new FlowLayout(FlowLayout.LEFT));
		lbl = new JLabel("Customer address");
		lbl.setLabelFor(clientAddrField);
		p.add(lbl);
		p.add(clientAddrField);
		controlsPane.add(p);

		p = new JPanel(new FlowLayout(FlowLayout.LEFT));
		lblDateOfBirth = new JLabel("Customer date of birth (!!! input format : yyyy-MM-dd !!!)");
		lblDateOfBirth.setLabelFor(dateOfBirthField);
		p.add(lblDateOfBirth);
		p.add(dateOfBirthField);
		controlsPane.add(p);

		p = new JPanel(new FlowLayout(FlowLayout.LEFT));
		lblDirectorName = new JLabel("Director name");
		lblDirectorName.setLabelFor(directorNameField);
		p.add(lblDirectorName);
		p.add(directorNameField);
		controlsPane.add(p);

		p = new JPanel(new FlowLayout(FlowLayout.LEFT));
		lblContactPerson = new JLabel("Contact person");
		lblContactPerson.setLabelFor(contactPersonField);
		p.add(lblContactPerson);
		p.add(contactPersonField);
		controlsPane.add(p);
		
		add(controlsPane, BorderLayout.CENTER);
	}

	/**
	 * Creates bottom panel with "OK" and "Cancel" buttons.
	 */
	private void initOkCancelButtons() {
		JPanel btnsPane = new JPanel();
	
		JButton okBtn = new JButton("OK");
		okBtn.addActionListener(e -> {
			okPressed = true;
			setVisible(false);
		});
		okBtn.setDefaultCapable(true);
		btnsPane.add(okBtn);
		
		Action cancelDialogAction = new AbstractAction("Cancel") {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		}; 
		
		JButton cancelBtn = new JButton(cancelDialogAction);
		btnsPane.add(cancelBtn);
		
		add(btnsPane, BorderLayout.SOUTH);
		
		final String esc = "escape";
		getRootPane()
			.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT)
			.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), esc);
		getRootPane()
			.getActionMap()
			.put(esc, cancelDialogAction);
	}
	
	/**
	 * Shows dialog, and returns, when the user finishes his work with the dialog.
	 * Returns {@code true}, if the user closed the dialog with "OK" button, 
	 * {@code false} otherwise. 
	 * 
	 * @return {@code true}, if a user closed the dialog with "OK" button,
	 * 		{@code false} otherwise (with "Cancel" button, or with system close options).
	 */
	public boolean showModal() {
		pack();
		setLocationRelativeTo(getOwner());
		if(areaCodeField.isEnabled())
			areaCodeField.requestFocusInWindow();
		else
			clientNameField.requestFocusInWindow();
		okPressed = false;
		setVisible(true);
		return okPressed;
	}

	/**
	 * Prepares dialog for entering of data about new client.
	 * Clears all dialog controls; enables phone number fields.
	 */
	public void prepareForAddPerson() {
		setTitle("New person registration");
		
		areaCodeField.setText("");
		phoneNumField.setText("");
		clientNameField.setText("");
		clientAddrField.setText("");
		dateOfBirthField.setText("");
		

		lblContactPerson.setVisible(false);
		lblDirectorName.setVisible(false);
		lblDateOfBirth.setVisible(true);
		directorNameField.setVisible(false);
		contactPersonField.setVisible(false);
		areaCodeField.setEnabled(true);
		phoneNumField.setEnabled(true);
		lblDateOfBirth.setVisible(true);
	}

	public void prepareForAddCompany() {
		setTitle("New company registration");

		areaCodeField.setText("");
		phoneNumField.setText("");
		clientNameField.setText("");
		clientAddrField.setText("");
		directorNameField.setText("");
		contactPersonField.setText("");

		dateOfBirthField.setVisible(false);
		lblDateOfBirth.setVisible(false);
		lblContactPerson.setVisible(true);
		lblDirectorName.setVisible(true);
		directorNameField.setVisible(true);
		contactPersonField.setVisible(true);
		areaCodeField.setEnabled(true);
		phoneNumField.setEnabled(true);

	}
	/**
	 * Prepares dialog for editing of information about client.
	 * Fills dialog controls with data from provided client information object;
	 * disables phone number fields.
	 * 
	 * @param ci client information used to fill dialog controls
	 */
	public void prepareForChangePerson(PersonInfo ci) {
		setTitle("Person properties change");
		
		areaCodeField.setText(ci.getPhoneNumber().getAreaCode());
		phoneNumField.setText(ci.getPhoneNumber().getLocalNum());
		clientNameField.setText(ci.getName());
		clientAddrField.setText(ci.getAddress());
		dateOfBirthField.setText(ci.getDateOfBirth());

		lblContactPerson.setVisible(false);
		lblDirectorName.setVisible(false);
		lblDateOfBirth.setVisible(true);
		directorNameField.setVisible(false);
		contactPersonField.setVisible(false);
		areaCodeField.setEnabled(false);
		phoneNumField.setEnabled(false);
		dateOfBirthField.setVisible(true);
	}

	public void prepareForChangeCompany(CompanyInfo ci) {
		setTitle("Company properties change");

		areaCodeField.setText(ci.getPhoneNumber().getAreaCode());
		phoneNumField.setText(ci.getPhoneNumber().getLocalNum());
		clientNameField.setText(ci.getName());
		clientAddrField.setText(ci.getAddress());
		directorNameField.setText(ci.getDirectorName());
		contactPersonField.setText(ci.getContactPerson());

		dateOfBirthField.setVisible(false);
		lblDateOfBirth.setVisible(false);
		lblContactPerson.setVisible(true);
		lblDirectorName.setVisible(true);
		directorNameField.setVisible(true);
		contactPersonField.setVisible(true);
		areaCodeField.setEnabled(false);
		phoneNumField.setEnabled(false);
	}
	
	/**
	 * Returns value of area code entered by a user.
	 */
	public String getAreaCode() {
		return areaCodeField.getText();
	}
	
	/**
	 * Returns value of phone local number entered by a user.
	 */
	public String getPhoneNum() {
		return phoneNumField.getText();
	}
	
	/**
	 * Returns value of client name entered by a user.
	 */
	public String getClientName() {
		return clientNameField.getText();
	}

	/**
	 * Returns value of client address entered by a user.
	 */
	public String getClientAddr() {
		return clientAddrField.getText();
	}
	public String getDateOfBirth() {
		return dateOfBirthField.getText();
	}
	public String getDirectorName() {
		return directorNameField.getText();
	}
	public String getContactPerson() {
		return contactPersonField.getText();
	}

}
