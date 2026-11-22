/**
 * TransactionView
 * 
 * Class implements nodes & logic to nodes associated to deposit and withdraw of money.
 * Class connected to associated controller.
 */

package bankApp.view;

import java.util.ArrayList;

import bankApp.controller.TransactionController;
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

public class TransactionView {
	private TransactionController controller;
	private GridPane root;
	int accountId;
	int amount;
	
	/**
	 * Constructor.
	 * @param controller TransactionController.
	 */
	public TransactionView(TransactionController controller) {
		this.controller = controller;
		createUI();
	}
	
	/**
	 * Create transaction view.
	 */
	private void createUI() {
		root = new GridPane();
		root.setHgap(10);
		root.setVgap(10);
		root.setPadding(new Insets(10));
		

		Label lblPno = new Label("Ssn:");
		root.add(lblPno, 0, 2, 1, 1);
		TextField tfPno = new TextField();
		root.add(tfPno, 1, 2, 1, 1);
		
		Button btnGetAccounts = new Button("Ok");
		root.add(btnGetAccounts, 2, 2, 1, 1);
		
		Label lblNoCustomer = new Label();
		lblNoCustomer.setVisible(false);
		root.add(lblNoCustomer, 3, 2, 1, 1);
		
		Label lblAccounts = new Label("Accounts");
		root.add(lblAccounts, 2, 4, 1, 1); // root.add(node, columnIndex, rowIndex, columnSpan, rowSpan);	
		
		// Accounts
		ListView<Account> accountsList = new ListView<>();
		
		// get Customer Accounts
		btnGetAccounts.setOnAction(e -> {
			
			if(tfPno.getText().isEmpty()) {
				return;
			}
			// if customer don't exist
			// show label
			else {
				String pNo = tfPno.getText();
				if(!controller.customerExist(pNo)) {
					lblNoCustomer.setText("No such customer");
					lblNoCustomer.setVisible(true);
					PauseTransition pause = new PauseTransition(Duration.seconds(4));
					pause.setOnFinished(event -> lblNoCustomer.setVisible(false));
					pause.play();
					return;
				}
				ArrayList<Account> accs = controller.btnGetAccounts(pNo);
				accountsList.getItems().setAll(accs);
			}
		});
		
		// get Account id from Account object
		accountsList.setOnMouseClicked(e ->{
			Account acc = accountsList.getSelectionModel().getSelectedItem();
			if(acc != null) {
				accountId = acc.getAccountNumber();
			}
		});
		
		// Position ListView
		accountsList.setPrefWidth(390);
		accountsList.setPrefHeight(90);
		root.add(accountsList, 1, 4, 4, 5);  // root.add(node, columnIndex, rowIndex, columnSpan, rowSpan);	
		
		Label lblAmount = new Label("Amount:");
		root.add(lblAmount, 0, 11, 1, 1);
		TextField tfAmount = new TextField();
		root.add(tfAmount, 1, 11, 1, 1);
		
		Label lblAmountFail = new Label();
		lblAmountFail.setVisible(false);
		root.add(lblAmountFail, 1, 12, 1, 1);
		
		Button btnAmount = new Button("Ok");
		root.add(btnAmount, 2, 11, 1, 1);
		
		
		// get amount from TextField
		btnAmount.setOnAction(e -> {
			if(tfAmount.getText().isEmpty()) {
				return;
			}
			// if integer and > 0
			// catch if input not integer
			try {
				int digits = Integer.parseInt(tfAmount.getText());
				if(digits <= 0) {
					lblAmountFail.setText("Amount too low");
					lblAmountFail.setVisible(true);
					PauseTransition pause = new PauseTransition(Duration.seconds(4));
					pause.setOnFinished(event -> lblAmountFail.setVisible(false));
					pause.play();
					amount = 0;
					tfAmount.clear();
				}
				else {
					amount = Integer.parseInt(tfAmount.getText());
				}
			}
			catch (NumberFormatException ex) {
				lblAmountFail.setText("Amount failed");
				lblAmountFail.setVisible(true);
				PauseTransition pause = new PauseTransition(Duration.seconds(4));
				pause.setOnFinished(event -> lblAmountFail.setVisible(false));
				pause.play();
				amount = 0;
				tfAmount.clear();
			}
		});
		
		Button btnDeposit = new Button("Deposit");
		root.add(btnDeposit, 1, 15, 1, 1);
		
		Button btnWithdraw = new Button("Withdraw");
		root.add(btnWithdraw, 2, 15, 1, 1);
		
		Label lblDeposit = new Label();
		lblDeposit.setVisible(false);
		root.add(lblDeposit, 1, 16, 1, 1);
		
		// ----------------------------------
		// Deposit logic
		btnDeposit.setOnAction(e -> {
			if(tfPno.getText().isEmpty() || accountId == 0 || tfAmount.getText().isEmpty()) {
				lblDeposit.setVisible(false);
			}
			
			boolean deposit = controller.btnDeposit(tfPno.getText(), accountId, Integer.parseInt(tfAmount.getText()) );
			
			// if deposit fail
			if(!deposit) {
				// set visible close account label
				lblDeposit.setText("Deposit failed");
				lblDeposit.setVisible(true);
				PauseTransition pause = new PauseTransition(Duration.seconds(4));
				pause.setOnFinished(event -> lblDeposit.setVisible(false));
				pause.play();
			}
			// if deposit success
			else {
				// set visible close account label
				lblDeposit.setText("Deposit: " + tfAmount.getText());
				lblDeposit.setVisible(true);
				PauseTransition pause = new PauseTransition(Duration.seconds(4));
				pause.setOnFinished(event -> lblDeposit.setVisible(false));
				pause.play();
				tfPno.clear();
				tfAmount.clear();
				accountsList.getItems().clear();
				accountId = 0;
				amount = 0;
			}
		});
		
		
		// ----------------------------------
 		// Withdraw logic
		
		Label lblWithdraw = new Label();
		lblWithdraw.setVisible(false);
		root.add(lblWithdraw, 2, 16, 1, 1);
		
		btnWithdraw.setOnAction(e -> {
			if(tfPno.getText().isEmpty() || accountId == 0 || tfAmount.getText().isEmpty()) {
				lblWithdraw.setVisible(false);
			}
			
			boolean withdraw = controller.btnWithdraw(tfPno.getText(), accountId, Integer.parseInt(tfAmount.getText()) );
			
			// if withdraw fail
			if(!withdraw) {
				// set visible close account label
				lblWithdraw.setText("Withdraw failed");
				lblWithdraw.setVisible(true);
				PauseTransition pause = new PauseTransition(Duration.seconds(4));
				pause.setOnFinished(event -> lblWithdraw.setVisible(false));
				pause.play();
			}
			// if withdraw success
			else {
				lblWithdraw.setText("Withdraw: " + tfAmount.getText());
				lblWithdraw.setVisible(true);
				PauseTransition pause = new PauseTransition(Duration.seconds(4));
				pause.setOnFinished(event -> lblWithdraw.setVisible(false));
				pause.play();
				tfPno.clear();
				tfAmount.clear();
				accountsList.getItems().clear();
				accountId = 0;
				amount = 0;
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