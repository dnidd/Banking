/**
 * SaveController
 * 
 * Handles save to file and loading from file.
 * Class connects Main(scene) and View.
 */


package bankApp.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.NotSerializableException;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;

import bankApp.model.Account;
import bankApp.model.BankLogic;
import bankApp.view.SaveLoadView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class SaveLoadController {
	private SaveLoadView view;
	private BankLogic bankLogic;
	private Stage primaryStage;
	
	/**
	 * Constructor.
	 * @param bankLogic bankLogic.
	 * @param primaryStage primaryStage.
	 */
	public SaveLoadController(BankLogic bankLogic, Stage primaryStage) {
		this.view = new SaveLoadView(this);
		this.bankLogic = bankLogic;
		this.primaryStage = primaryStage;
	}
	
	/**
	 * Save Bank to file.
	 * @param path = path to file in file save directory.
	 */
	public void saveBank(String path) throws NoSuchFileException, NotSerializableException, FileNotFoundException, ClassNotFoundException, IOException{
		bankLogic.saveBankData(path);
	}
	
	/**
	 * Load bank data from byte file.
	 * @param path = path to chosen file in file chooser directory.
	 */
	public void loadBank(String path) throws NotSerializableException, FileNotFoundException, ClassNotFoundException, IOException {
		bankLogic.loadBankData(path);
	}
	
	/**
	 * Get customer accounts.
	 * @param pNp = personal ID number.
	 * @return customers accounts.
	 */
	public ArrayList<Account> getAccounts(String pNp) {
		return bankLogic.getCustomerAccountObjects(pNp);
	}
	
	/**
	 * Get customer transactions.
	 * @param pNo = personal ID number.
	 * @param accountId = 4 digit Account number.
	 * @return user transactions.
	 */
	public ArrayList<String> getTransactions(String pNo, int accountId) {
		return bankLogic.getTransactions(pNo, accountId);
	}
	
	/**
	 * Save statement/receipt.
	 * Save transaction statement to file.
	 * @param pNo = personal ID number.
	 * @param accountId = 4 digit account id.
	 */
	public void saveStatement(String pNo, int accountId) throws FileNotFoundException, IOException{
		bankLogic.saveStatement(pNo, accountId);
	}
	
	
	/**
	 * Load statement/receipt.
	 * @param file = path.
	 * @return statement String.
	 */
	public String loadStatement(String file) throws FileNotFoundException, IOException {
		return bankLogic.loadStatement(file);
	}
	
	/**
	 * Get file chooser stage/window.
	 * @return Stage.
	 */
	public Stage getStage() {
		return primaryStage;
	}
	
	/**
	 * Get View.
	 * @return pane/root/view
	 */
	public GridPane getView() {
		return view.getRoot();
	}
	
	
}
