/**
 * TransactionController.
 * 
 * Class handles logic to deposit and withdraw.
 * Class connects Main(scene) and View.
 */

package bankApp.controller;

import java.util.ArrayList;

import bankApp.model.Account;
import bankApp.model.BankLogic;
import bankApp.view.TransactionView;
import javafx.scene.layout.GridPane;

public class TransactionController {
	private TransactionView view;
	private BankLogic bankLogic;
	
	/**
	 * Constructor.
	 * @param bankLogic = bankLogic.
	 */
	public TransactionController(BankLogic bankLogic) {
		this.view = new TransactionView(this);
		this.bankLogic = bankLogic;
	}
	
	/**
	 * Get accounts.
	 * @param pNp = personal ID number.
	 * @return account objects.
	 */
	public ArrayList<Account> btnGetAccounts(String pNp) {
		return bankLogic.getCustomerAccountObjects(pNp);
	}
	
	/**
	 * Deposit to account.
	 * @param pNo = personal ID number.
	 * @param accountId = 4 digit Account number.
	 * @param amount = amount to deposit.
	 * @return true if success.
	 */
	public boolean btnDeposit(String pNo, int accountId, int amount) {
		boolean deposit = bankLogic.deposit(pNo, accountId, amount);
		return deposit;
	}
	
	/**
	 * Withdraw from account.
	 * @param pNo = personal ID number
	 * @param accountId = 4 digit Account number.
	 * @param amount = amount to withdraw.
	 * @return true if success.
	 */
	public boolean btnWithdraw(String pNo, int accountId, int amount) {
		boolean withdraw = bankLogic.withdraw(pNo, accountId, amount);
		return withdraw;
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
	 * Get view.
	 * @return view.
	 */
	public GridPane getView() {
		return view.getRoot();
	}

}
