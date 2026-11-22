/**
 * BankCustomersView
 * 
 * Class implements nodes & logic to nodes associated to Bank customers.
 * Class connected to associated controller.
 */

package bankApp.view;

import java.util.ArrayList;

import bankApp.controller.BankCustomersController;
import bankApp.model.Account;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;


public class BankCustomersView {
	private BankCustomersController controller;
	private GridPane root;
	private String pNo;
	private int accountId;
	
	/**
	 * Constructor.
	 * @param controller BankCustomersController.
	 */
	public BankCustomersView(BankCustomersController controller) {
		this.controller = controller;
		createUI();
	}
	
	/**
	 * Create bank customers view.
	 */
	public void createUI() {
		root = new GridPane();
		root.setHgap(10);
		root.setVgap(10);
		root.setPadding(new Insets(10));
		
		// --------------------------------------
		// All customers
		
		Button btnBankCustomers = new Button("Show bank customers");
		root.add(btnBankCustomers, 0, 1, 1, 1);  // root.add(node, columnIndex, rowIndex, columnSpan, rowSpan);
		
		Button btnClear = new Button("Clear lists");
		root.add(btnClear, 3, 1, 1, 1);
		
		Label lblAllCutomers = new Label("Bank customers");
		root.add(lblAllCutomers, 0, 2, 1, 1);
		
		ListView<String> allCustomersList = new ListView<>();
		
		// click button get list of Customer Accounts.
		btnBankCustomers.setOnAction(e -> {
			ArrayList<String> bankCustomers = controller.btnBankCustomers();
			if(bankCustomers == null || bankCustomers.isEmpty()) {
				allCustomersList.getItems().setAll("No customers");
				return;
			}
			else {
				allCustomersList.getItems().setAll(bankCustomers);	
			}
		});
		
		allCustomersList.setPrefWidth(400);
		allCustomersList.setPrefHeight(120);
		root.add(allCustomersList, 0, 3, 6, 7);  // root.add(node, columnIndex, rowIndex, columnSpan, rowSpan);	
		
		
		// --------------------------------------
		// Customer accounts
		Label lblCustAccount = new Label("Customer accounts");
		root.add(lblCustAccount, 8, 2, 1, 1);
		
		ListView<Account> customerAccountsList = new ListView<>();
		
		// Click customer -> show accounts.
		allCustomersList.setOnMouseClicked(e -> {
			String clickedCustomer = allCustomersList.getSelectionModel().getSelectedItem();
			
			// prevent crash on empty list click.
			if(allCustomersList.getItems().isEmpty()) {
				return;
			}
			
			// prevent crash if click beside object inside ListView .
			if(clickedCustomer == null) {
				return;
			}
			
			// storing pNo from clicked account.
			pNo = clickedCustomer.substring(0, 10);

			// Get Accounts of customer.
			// set Accounts to ListView.
			ArrayList<Account> accounts = controller.getAccounts(pNo);
			// prevent from crash if no accounts.
			if(accounts == null || accounts.isEmpty()) {
				return;
			}
			customerAccountsList.getItems().setAll(controller.getAccounts(pNo));
		});
		
		customerAccountsList.setPrefWidth(450);
		customerAccountsList.setPrefHeight(120);
		root.add(customerAccountsList, 8, 3, 7, 7);  // root.add(node, columnIndex, rowIndex, columnSpan, rowSpan);
		
		
		// --------------------------------------
		// Account information
		Label lblAccountInfo = new Label("Account information");
		root.add(lblAccountInfo, 0, 14, 1, 1);
		
		ListView<String> accountInfoList = new ListView<>();
		
		// Click account -> get account info.
		customerAccountsList.setOnMouseClicked(e -> {
			Account clickedAccount = customerAccountsList.getSelectionModel().getSelectedItem();
			
			if(customerAccountsList.getItems().isEmpty()) {
				return;
			}

			// prevent crash if click beside object inside ListView.
			if(clickedAccount == null) {
				return;
			}
			
			// get accountId from chosen account.
			accountId = clickedAccount.getAccountNumber();
			
			// Get chosen Accounts.
			// set Account to ListView.
			accountInfoList.getItems().setAll(controller.getCustomerAccounts(pNo, accountId));	
			
		});
		
		accountInfoList.setPrefWidth(400);
		accountInfoList.setPrefHeight(120);
		root.add(accountInfoList, 0, 15, 6, 7);  // root.add(node, columnIndex, rowIndex, columnSpan, rowSpan);
		
		// --------------------------------------
		// Transactions
		
		Label lblTransactions = new Label("Account transactions");
		root.add(lblTransactions, 8, 14, 1, 1);
		
		ListView<String> transactionsList = new ListView<>();
		
		// Click account -> get transactions.
		accountInfoList.setOnMouseClicked(e -> {
			String clickedAccount = accountInfoList.getSelectionModel().getSelectedItem();
			
			if(accountInfoList.getItems().isEmpty()) {
				return;
			}
			
			// prevent crash if click beside object inside ListView.
			if(clickedAccount == null) {
				return;
			}
			ArrayList<String> transactions = controller.getTransactions(pNo, accountId);
			if(transactions == null || transactions.isEmpty()) {
				transactionsList.getItems().setAll("No transactions");
			}
			else {
				transactionsList.getItems().setAll(transactions);
			}
		});
		
		transactionsList.setPrefWidth(450);
		transactionsList.setPrefHeight(120);
		root.add(transactionsList, 8, 15, 7, 7);  // root.add(node, columnIndex, rowIndex, columnSpan, rowSpan);
		
		// clear all ListViews.
		btnClear.setOnAction(e -> {
			allCustomersList.getItems().clear();
			customerAccountsList.getItems().clear();
			accountInfoList.getItems().clear();
			transactionsList.getItems().clear();
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
