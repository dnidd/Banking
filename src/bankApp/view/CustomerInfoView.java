/**
 * CustomerInfoView
 * 
 * Class implements nodes & logic to nodes associated to customer, account & transaction information.
 * Class connected to associated controller.
 */


package bankApp.view;

import java.util.ArrayList;

import bankApp.controller.CustomerInfoController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class CustomerInfoView {
	private CustomerInfoController controller;
	private GridPane root;
	private int accountId;
	private String pNo;
	
	/**
	 * Constructor.
	 * @param controller CustomerInfoController.
	 */
	public CustomerInfoView(CustomerInfoController controller) {
		this.controller = controller;
		createUI();
	}
	
	
	/**
	 * Create Customer information view.
	 */
	public void createUI() {
		root = new GridPane();
		root.setHgap(10);
		root.setVgap(10);
		root.setPadding(new Insets(10));
		
		//***************************************************************************
		// customer information.
		Label lblCustomerInfo = new Label("Customer information");
		root.add(lblCustomerInfo, 0, 0);
		
		Label lblInfoPno = new Label("Ssn:");
		root.add(lblInfoPno, 0, 1);
		
		TextField tfInfoPno = new TextField();
		root.add(tfInfoPno, 1, 1);
	

		// List of customer info.
		ListView<String> customerInfoList = new ListView<>();
		
		Button btnCustInfo = new Button("Ok");
		
		// get list of Customer info & Accounts.
		btnCustInfo.setOnAction(e -> {
			if(tfInfoPno.getText().isEmpty()) {
				return;
			}
			else {
				pNo = tfInfoPno.getText();
				ArrayList<String> accs = controller.btnCustInfo(pNo);
				
				String fail = "No such customer";
				if(accs == null) {
					customerInfoList.getItems().setAll(fail);
				}
				else {
					customerInfoList.getItems().setAll(accs);
					tfInfoPno.clear();		
				}
			}
		});
		
		root.add(btnCustInfo, 1, 2);
		
		customerInfoList.setPrefWidth(500);
		customerInfoList.setPrefHeight(60);
		root.add(customerInfoList, 6, 1, 6, 5);  // root.add(node, columnIndex, rowIndex, columnSpan, rowSpan);	
		
		
		//***************************************************************************
		// customer account/s
		Label lblCustomerAcc = new Label("Account information");
		root.add(lblCustomerAcc, 0, 9);
		
		Label lblCustAccPno = new Label("Ssn");
		root.add(lblCustAccPno, 0, 10);
		
		TextField tfCustomerAccPno = new TextField();
		root.add(tfCustomerAccPno, 1, 10);
		
		Label lblCustAccId = new Label("Account id:");
		root.add(lblCustAccId, 0, 11);
		
		TextField tfCustAccId = new TextField();
		root.add(tfCustAccId, 1, 11);
		
		Button btnCustAcc = new Button("Ok");
		root.add(btnCustAcc, 1, 12);
		
		// Account info
		// ListView click -> show account in next ListView
		// Clicked account shown in customerAccountsList
		// Customer not clickable
		ListView<String> customerAccountsList = new ListView<>();
		
		customerInfoList.setOnMouseClicked(e -> {
			// prevent from clicking/showing customer in customerAccountsList
			int personData = customerInfoList.getSelectionModel().getSelectedIndex();
			if(personData == 0) {
				customerInfoList.getSelectionModel().clearSelection();
				return;
			}
			String clickedAcc = customerInfoList.getSelectionModel().getSelectedItem();
			if(clickedAcc != null) {
				// storing Account number to later get transactions  
				accountId = Integer.parseInt(clickedAcc.substring(0, 4));
				customerAccountsList.getItems().setAll(clickedAcc);
			}
		});
		
		// Account info
		// Fill out form -> btn click -> show in ListView
		btnCustAcc.setOnAction(e -> {
			if(tfCustomerAccPno.getText().isEmpty() || tfCustAccId.getText().isEmpty()) {
				return;
			}
			else {
				String fail = "Account do not exist or belongs to another customer";
				String acc = controller.btnCustomerAcc(tfCustomerAccPno.getText(), Integer.parseInt(tfCustAccId.getText()));
				if(acc == null) {
					customerAccountsList.getItems().setAll(fail);
				}
				else {
					customerAccountsList.getItems().setAll(acc);
					tfCustomerAccPno.clear();
					tfCustAccId.clear();	
				}	
			}
		});
		
		customerAccountsList.setPrefWidth(500);
		customerAccountsList.setPrefHeight(60);
		root.add(customerAccountsList, 6, 10, 6, 3);  // root.add(node, columnIndex, rowIndex, columnSpan, rowSpan);
		
		
		//***************************************************************************
		// customer transactions
		Label lblCustomerTransactions = new Label("Customer transactions");
		root.add(lblCustomerTransactions, 0, 15);
		
		Label lblCustTansPno = new Label("Ssn:");
		root.add(lblCustTansPno, 0, 16);
		
		TextField tfCustTransPno = new TextField();
		root.add(tfCustTransPno, 1, 16);
		
		Label lblCustTransAccId = new Label("Account id:");
		root.add(lblCustTransAccId, 0, 17);
		
		TextField tfCustTransAccId = new TextField();
		root.add(tfCustTransAccId, 1, 17);
		
		Button btnCustTrans = new Button("Ok");
		root.add(btnCustTrans, 1, 18);
		
		
		// ---------------------------------------------------------------------------------------------------
		// Transaction info
		// ListView click -> show transaction in next ListView
		// Clicked account shows transaction in customerAccountsList
		ListView<String> transactionsList = new ListView<>();
		
		customerAccountsList.setOnMouseClicked(e -> {
			if(customerAccountsList.getItems().isEmpty()) {
				return;
			}
			
			String clickedAcc = customerAccountsList.getSelectionModel().getSelectedItem();
			if(clickedAcc == null || clickedAcc.isEmpty()) {
				return;
			}
			
			ArrayList<String> transactions = controller.btnCustTrans(pNo, accountId);
			
			if(transactions == null || transactions.isEmpty()) {
				transactionsList.getItems().setAll("No transactions");
			}
			else {
				transactionsList.getItems().setAll(transactions);
			}
		});
		
		
		// get account transactions
		// using form and button
		btnCustTrans.setOnAction(e -> {
			if(tfCustTransPno.getText().isEmpty() || tfCustTransAccId.getText().isEmpty()) {
				return;
			}
			
			ArrayList<String> transactions = controller.btnCustTrans(tfCustTransPno.getText(), Integer.parseInt(tfCustTransAccId.getText()));
			
			if(transactions == null || transactions.isEmpty()) {
				transactionsList.getItems().setAll("No transactions");
			}
			else {
				transactionsList.getItems().setAll(transactions);
				tfCustTransPno.clear();
				tfCustTransAccId.clear();
				}
		});
		
		
		
		transactionsList.setPrefWidth(500);
		transactionsList.setPrefHeight(60);
		root.add(transactionsList, 6, 16, 6, 3);  // root.add(node, columnIndex, rowIndex, columnSpan, rowSpan);
		
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
