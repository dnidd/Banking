/**
 * BankCustomersController.
 * 
 * Class handles logic to get bank customers information, account information, transaction information
 * Class connects Main(scene) and View.
 */

package bankApp.controller;

import java.util.ArrayList;

import bankApp.model.Account;
import bankApp.model.BankLogic;
import bankApp.view.BankCustomersView;
import javafx.scene.layout.GridPane;

public class BankCustomersController {
	private BankCustomersView view;
	private BankLogic bankLogic;
	
	/**
	 * Constructor.
	 * @param bankLogic = bankLogic.
	 */
	public BankCustomersController(BankLogic bankLogic) {
		this.view = new BankCustomersView(this);
		this.bankLogic = bankLogic;
	}
	
	
	/**
	 * Get all customers.
	 * @return customers.
	 */
	public ArrayList<String> btnBankCustomers() {
		return bankLogic.getAllCustomers();
	}
	
	/**
	 * Get customers account objects.
	 * @param pNp = personal ID number
	 * @return account objects.
	 */
	public ArrayList<Account> getAccounts(String pNp) {
		return bankLogic.getCustomerAccountObjects(pNp);
	}
	
	/**
	 * Get customers account.
	 * @param pNo = personal ID number.
	 * @param accountId = 4 digit Account number.
	 * @return accounts as String.
	 */
	public String getCustomerAccounts(String pNo, int accountId) {
		return bankLogic.getAccount(pNo, accountId);
	}
	
	/**
	 * Get customers account transactions.
	 * @param pNo = personal ID number.
	 * @param accountId = 4 digit Account number.
	 * @return account transactions.
	 */
	public ArrayList<String> getTransactions(String pNo, int accountId) {
		return bankLogic.getTransactions(pNo, accountId);
	}
	
	/**
	 * Get view.
	 * @return root/pane.
	 */
	public GridPane getView() {
		return view.getRoot();
	}
}
