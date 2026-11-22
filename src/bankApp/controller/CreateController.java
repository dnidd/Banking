/**
 * CreateController
 * 
 * Class handles logic to create customers & accounts.
 * Class connects Main(scene) and View.
 */

package bankApp.controller;

import bankApp.model.BankLogic;
import bankApp.view.CreateView;
import javafx.scene.layout.GridPane;

public class CreateController {
	private CreateView view;
	private BankLogic bankLogic;
	
	/**
	 * Constructor.
	 * @param bankLogic = BankLogic.
	 */
	public CreateController(BankLogic bankLogic) {
		this.view = new CreateView(this);
		this.bankLogic = bankLogic;
	}
	
	// on button click, call:
	// createCustomer(String name, String surname, String pNo)
	/**
	 * Create customer.
	 * @param name name.
	 * @param surname surname.
	 * @param pNo pNo.
	 * @return true if customer created, else false.
	 */
	public boolean btnCreateCustomer(String name, String surname, String pNo) {
		return bankLogic.createCustomer(name, surname, pNo);
	}
	
	/**
	 * Create savings account.
	 * @param pNo pNo.
	 */
	public void btnCreateSavings(String pNo) {
		bankLogic.createSavingsAccount(pNo);
	}
	
	/**
	 * Create credit account.
	 * @param pNo pNo.
	 */
	public void btnCreateCredit(String pNo) {
		bankLogic.createCreditAccount(pNo);
	}
	
	/**
	 * Check if customer exist.
	 * @param pNo pNo.
	 * @return true or false.
	 */
	public boolean customerExist(String pNo) {
		return bankLogic.customerExist(pNo);
	}
	
	/**
	 * Validate pNo, 10 digits.
	 * @param pNo
	 * @return
	 */
	public boolean isPno(String pNo) {
		return bankLogic.isPno(pNo);
	}
	
	/**
	 * Getter view.
	 * @return view.
	 */
	public GridPane getView() {
		return view.getRoot();
	}

}
