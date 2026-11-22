/**
 * EditCustomerController
 * 
 * Class handles logic to edit customer name, close account & delete customer.
 * Class connects Main(scene) and View.
 */

package bankApp.controller;

import java.util.ArrayList;

import bankApp.model.Account;
import bankApp.model.BankLogic;
import bankApp.view.EditCustomerView;
import javafx.scene.layout.GridPane;

public class EditCustomerController {
	private EditCustomerView view;
	private BankLogic bankLogic;
	
	/**
	 * Constructor.
	 * @param bankLogic = bankLogic.
	 */
	public EditCustomerController(BankLogic bankLogic) {
		this.view = new EditCustomerView(this);
		this.bankLogic = bankLogic;
	}
	
	
	/**
	 * Edit customer name.
	 * @param name = name.
	 * @param surname = surname.
	 * @param pNo = personal ID number.
	 * @return true if name changed, else false.
	 */
	public boolean btnEditName(String name, String surname, String pNo) {
		return bankLogic.changeCustomerName(name, surname, pNo);
	}
	
	/**
	 * Close bank account.
	 * @param pNo = personal ID number.
	 * @param accountId = 4 digit Account number.
	 * @return String of Account info (including calculated interest) for specific Customer, else null.
	 */
	public String btnCloseAccount(String pNo, int accountId) {
		return bankLogic.closeAccount(pNo, accountId);
	}
	
	/**
	 * Delete customer.
	 * @param pNo = personal ID number
	 * @return ArrayList of Customer info and its Account/s (including calculated interest), else null.
	 */
	public ArrayList<String> btnDeleteCustomer(String pNo) {
		ArrayList<String> customer = bankLogic.deleteCustomer(pNo);
		if(customer == null) {
			return new ArrayList<>();
		}
		return customer;
		
	}
	
	/**
	 * Get customer accounts.
	 * @param pNp = personal ID number.
	 * @return List of accounts, else empty List.
	 */
	public ArrayList<Account> getAccounts(String pNp) {
		ArrayList<Account> accounts = bankLogic.getCustomerAccountObjects(pNp);
		if(accounts == null) {
			return new ArrayList<>();
		}
		return bankLogic.getCustomerAccountObjects(pNp);
	}
	
	/**
	 * Check if customer exist.
	 * @param pNo = personal ID number.
	 * @return true if Customer exist.
	 */
	public boolean customerExist(String pNo) {
		return bankLogic.customerExist(pNo);
	}
	
	/**
	 * Get View.
	 * @return root/pane/view.
	 */
	public GridPane getView() {
		return	view.getRoot();
	}
	
}
