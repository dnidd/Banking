/**
 * CustomerInfoController
 * 
 * Class handles logic to customer information, accounts & transactions.
 * Class connects Main(scene) and View.
 */

package bankApp.controller;

import java.util.ArrayList;

import bankApp.model.BankLogic;
import bankApp.view.CustomerInfoView;
import javafx.scene.layout.GridPane;

public class CustomerInfoController {
	private CustomerInfoView view;
	private BankLogic bankLogic;

	/**
	 * Constructor.
	 * @param bankLogic = BankLogic.
	 */
	public CustomerInfoController(BankLogic bankLogic) {
		this.view = new CustomerInfoView(this);
		this.bankLogic = bankLogic;
	}
		
	/**
	 * Get customer info.
	 * @param pNo = personal ID number.
	 * @return customer and accounts.
	 */
	public ArrayList<String> btnCustInfo(String pNo) {
		return bankLogic.getCustomer(pNo);
		
	}

	/**
	 * Get customer account on account id.
	 * @param pNo = personal ID number.
	 * @param accountId = 4 digit Account number.
	 * @return customer account.
	 */
	public String btnCustomerAcc(String pNo, int accountId) {
		return bankLogic.getAccount(pNo, accountId);
	}
	
	/**
	 * Get customer transactions on 
	 * @param pNo = personal ID number account number.
	 * @param accountId = 4 digit Account number.
	 * @return transactions.
	 */
	public ArrayList<String> btnCustTrans(String pNo, int accountId) {
		return bankLogic.getTransactions(pNo, accountId);
	}
	
	/**
	 * Getter view
	 * @return view
	 */
	public GridPane getView() {
		return view.getRoot();
	}
}
