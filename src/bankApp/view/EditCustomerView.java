/**
 * EditCustomerView
 * 
 * Class implements nodes & logic to nodes associated to editing customer name, close account & delete customer.
 * Class connected to associated controller.
 */


package bankApp.view;

import bankApp.controller.EditCustomerController;
import bankApp.model.Account;
import javafx.animation.PauseTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

public class EditCustomerView {
	private EditCustomerController controller;
	private GridPane root;
	private int accountId;
	
	/**
	 * Constructor.
	 * @param controller EditCustomerController.
	 */
	public EditCustomerView(EditCustomerController controller) {
		this.controller = controller;
		createUI();
	}
	
	
	/**
	 * Create edit customer view.
	 */
	public void createUI() {
		root = new GridPane();
		root.setHgap(10);
		root.setVgap(10);
		root.setPadding(new Insets(10));
		
		// ************************************************
		// Edit name
		Label lblEditName = new Label("Edit name");
		root.add(lblEditName, 0, 1, 3, 1); // root.add(node, columnIndex, rowIndex, columnSpan, rowSpan);
		// first name
		Label lblName = new Label("First name:");
		root.add(lblName, 0, 2, 1, 1);
		TextField tfName = new TextField();
		root.add(tfName, 1, 2, 1, 1);
		
		// last name
		Label lblSurname = new Label("Last name:");
		root.add(lblSurname, 0, 3, 1, 1);
		TextField tfSurname = new TextField();
		root.add(tfSurname, 1, 3, 1, 1);
		
		// id
		Label lblPno = new Label("ssn:");
		root.add(lblPno, 0, 4, 1, 1);
		TextField tfPno = new TextField();
		root.add(tfPno, 1, 4, 1, 1);
		
		Button btnEditnName = new Button("Ok");
		root.add(btnEditnName, 1, 5, 1, 1);
		
		Label lblNoCustomer = new Label();
		lblNoCustomer.setVisible(false);
		root.add(lblNoCustomer, 8, 4, 1, 1 );
		
		btnEditnName.setOnAction(e -> {
			String ssn = tfPno.getText();
			// if empty
			if(tfPno.getText().isEmpty()) {
				return;
			}
			// if pNo don't exist
			if(!controller.customerExist(ssn)) {
				lblNoCustomer.setText("No such customer");
				lblNoCustomer.setVisible(true);
				PauseTransition pause = new PauseTransition(Duration.seconds(4));
				pause.setOnFinished(event -> lblNoCustomer.setVisible(false));
				pause.play();
				return;
			}
			// If (!name && !surName) & pNo
			if( (tfName.getText().isEmpty() && tfSurname.getText().isEmpty() ) && !tfPno.getText().isEmpty()) {
				lblNoCustomer.setText("Name/last name missing");
				lblNoCustomer.setVisible(true);
				PauseTransition pause = new PauseTransition(Duration.seconds(4));
				pause.setOnFinished(event -> lblNoCustomer.setVisible(false));
				pause.play();
				tfName.clear();
				tfSurname.clear();
				tfPno.clear();
			}
			// If (name || surName) && pNo
			else {
				controller.btnEditName(tfName.getText(), tfSurname.getText(), ssn);
				lblNoCustomer.setText("Name edited");
				lblNoCustomer.setVisible(true);
				PauseTransition pause = new PauseTransition(Duration.seconds(4));
				pause.setOnFinished(event -> lblNoCustomer.setVisible(false));
				pause.play();
				tfName.clear();
				tfSurname.clear();
				tfPno.clear();
			}
		});
		
		
		
		// ************************************************
		// Close account
		Label lblCloseAccount =  new Label("Close account");
		root.add(lblCloseAccount, 0, 8, 1, 1);
		
		// id
		Label lblClosePno = new Label("Ssn:");
		root.add(lblClosePno, 0, 9, 1, 1);
		TextField tfClosePno = new TextField();
		root.add(tfClosePno, 1, 9, 1, 1);
		
		ListView<Account> customerAccountsList = new ListView<>();
		
		Button btnAcoounts = new Button("Show accounts");
		root.add(btnAcoounts, 1, 10, 1, 1);
		
		// click btn set accounts to customerAccountsList
		btnAcoounts.setOnAction(e -> {
			String ssn = tfClosePno.getText();
			// if empty
			if(tfClosePno.getText().isEmpty()) {
				return;
			}
			// if pNo don't exist
			if(!controller.customerExist(ssn)) {
				lblNoCustomer.setText("No such customer");
				lblNoCustomer.setVisible(true);
				PauseTransition pause = new PauseTransition(Duration.seconds(4));
				pause.setOnFinished(event -> lblNoCustomer.setVisible(false));
				pause.play();
				return;
			}
			else {
				customerAccountsList.getItems().setAll(controller.getAccounts((ssn) ));	
			}
		});
		
		customerAccountsList.setPrefWidth(300);
		customerAccountsList.setPrefHeight(90);
		root.add(customerAccountsList, 1, 11, 6, 2);
		
		// Click Account
		// get accountId
		customerAccountsList.setOnMouseClicked(e -> {
			Account clickedAccount = customerAccountsList.getSelectionModel().getSelectedItem();
			if(clickedAccount != null) {
				accountId = clickedAccount.getAccountNumber();	
			}
		});
		
		Button btnCloseAccount = new Button("Close account");
		root.add(btnCloseAccount, 1, 14, 1, 1); // root.add(node, columnIndex, rowIndex, columnSpan, rowSpan);
		
		// ListView showing close account / delete customer
		ListView<String> closeAccountDeleteCustomerList = new ListView<>();
		
		closeAccountDeleteCustomerList.setPrefWidth(330);
		closeAccountDeleteCustomerList.setPrefHeight(90);
		root.add(closeAccountDeleteCustomerList, 8, 15, 7, 4); // root.add(node, columnIndex, rowIndex, columnSpan, rowSpan);
		
		// invisible close account label
		Label lblAccountClosed = new Label();
		lblAccountClosed.setVisible(false);
		root.add(lblAccountClosed, 8, 14, 1, 1);
		
		// click btn, close account
		btnCloseAccount.setOnAction(e -> {
			// add return from btnCloseAccount to closeAccountDeleteCustomerList
			String result = controller.btnCloseAccount(tfClosePno.getText(), accountId );
			
			if(result == null || result.isEmpty()) {
				lblAccountClosed.setVisible(false);
			}
			else {
				closeAccountDeleteCustomerList.getItems().setAll( result );
				// set visible close account label
				lblAccountClosed.setText("Account: " + accountId + " closed");
				lblAccountClosed.setVisible(true);
				PauseTransition pause = new PauseTransition(Duration.seconds(4));
				pause.setOnFinished(event -> lblAccountClosed.setVisible(false));
				pause.play();
				tfClosePno.clear();
				
				// clear customerAccounts
				customerAccountsList.getItems().clear();
			}
		});
		
		// ************************************************
		// Delete customer
		Label lblDeleteCustomer = new Label("Delete customer");
		root.add(lblDeleteCustomer, 7, 8, 1, 1); // root.add(node, columnIndex, rowIndex, columnSpan, rowSpan);
		
		// id
		Label lblDeletePno = new Label("Ssn");
		root.add(lblDeletePno, 7, 9, 1, 1);
		TextField tfDeletePno = new TextField();
		root.add(tfDeletePno, 8, 9, 1, 1);
		
		// invisible delete customer label
		Label lblCustomerDeleted = new Label();
		lblCustomerDeleted.setVisible(false);
		root.add(lblCustomerDeleted, 8, 14, 1, 1);
		
		Button btnDeleteCustomer = new Button("Delete customer");
		root.add(btnDeleteCustomer, 8, 10, 1, 1);
		
		// delete customer
		btnDeleteCustomer.setOnAction(e -> {
			String ssn = tfDeletePno.getText();
			
			// if empty
			if(tfDeletePno.getText().isEmpty()) {
				return;
			}
			// if pNo don't exist
			if(!controller.customerExist(ssn)) {
				// show label
				lblNoCustomer.setText("No such customer");
				lblNoCustomer.setVisible(true);
				PauseTransition pause = new PauseTransition(Duration.seconds(4));
				pause.setOnFinished(event -> lblNoCustomer.setVisible(false));
				pause.play();
				return;
			}
			// delete customer
			// show label
			else {
				closeAccountDeleteCustomerList.getItems().setAll( controller.btnDeleteCustomer(ssn) );
				// set visible delete customer label
				lblCustomerDeleted.setText("Customer: " + tfDeletePno.getText() + " deleted");
				lblCustomerDeleted.setVisible(true);
				PauseTransition pause = new PauseTransition(Duration.seconds(4));
				pause.setOnFinished(event -> lblCustomerDeleted.setVisible(false));
				pause.play();
				tfDeletePno.clear();
			}
		});
		
		
		root.setAlignment(Pos.TOP_LEFT);
	}
	
	/**
	 * Get Root.
	 * @return GridPane.
	 */
	public GridPane getRoot() {
		return root;
	}
	
}
