/**
 * Class handling (calling) bank logic.
 */

package bankApp.model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.NotSerializableException;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;

public class BankLogic {
	private CustomerAccountHandler customerAccountHandler;
	private SaveLoadBank saveLoadBank = new SaveLoadBank();
	private SaveLoadAccountStatement accountStatement = new SaveLoadAccountStatement();
	
	/**
	 * Constructor with no parameters
	 * to be compatible with TestBank2.java
	 */
	public BankLogic() {
		this(new CustomerAccountHandler());
	}
	
	
	/**
	 * Constructor.
	 * @param customerAccountHandler = customerAccountHandler.
	 */
	public BankLogic(CustomerAccountHandler customerAccountHandler) {
		this.customerAccountHandler = customerAccountHandler;
	}
	
	
	/**
	 * Creates customer if pNo is unique.
	 * @param name = first name.
	 * @param surname = last name.
	 * @param pNo = personal ID number.
	 * @return true if unique pNo, else false.
	 */
	public boolean createCustomer(String name, String surname, String pNo) {
		if(customerAccountHandler.createCustomer(new Customer(name, surname, pNo))) {
			System.out.println("Customer sucessfully created.");
			return true;	
		}
		System.out.println("Customer already exist.");
		return false;
	}
	
	
	/**
	 * Savings Account is created for specific customer.
	 * @param pNo = personal ID number
	 * @return account number, -1 if non existing customer 
	 */
	public int createSavingsAccount(String pNo) {
		if(!customerAccountHandler.customerExist(pNo)) {
			return -1;
		}
		int result = customerAccountHandler.createAccount(pNo, new SavingsAccount());
		System.out.println(result);
		return result;
	}
	
	
	/**
	 * Credit Account is created for specific customer.
	 * @param pNo = personal ID number.
	 * @return account number, -1 if non existing customer .
	 */
	public int createCreditAccount(String pNo) {
		if(!customerAccountHandler.customerExist(pNo)) {
			return -1;
		}
		int result = customerAccountHandler.createAccount(pNo, new CreditAccount());
		System.out.println(result);
		return result;
	}
	
	
	/**
	 * Deposit to specific Customer and specific Account.
	 * @param pNo = personal ID number.
	 * @param accountId = 4 digit Account number.
	 * @param amount = integer to deposit to Account.
	 * @return true if deposit successful, else false.
	 */
	public boolean deposit(String pNo, int accountId, int amount) {
		if(amount < 0) {
			System.out.println("Amount must be greater than 0;");
			return false;
		} else {
			return customerAccountHandler.deposit(pNo, accountId, amount);
		}
	}
	
	
	/**
	 * Withdraw from specific Customer and specific Account.
	 * @param pNo = personal ID number.
	 * @param accountId = 4 digit Account number.
	 * @param amount = integer to withdraw from Account.
	 * @return true if withdrawal successful, else false.
	 */
	public boolean withdraw(String pNo, int accountId, int amount) {
		if(amount < 0) {
			System.out.println("Amount must be greater than 0.");
			return false;
		}
		return customerAccountHandler.withdraw(pNo, accountId, amount);
	}
	
	
	/**
	 * Change specific Customer name and/or surname or none.
	 * @param name = first name.
	 * @param surname = last name.
	 * @param pNo = personal ID number.
	 * @return true if name changed, else false.
	 */
	public boolean changeCustomerName(String name, String surname, String pNo) {
		return customerAccountHandler.changeCustomerName(name, surname, pNo);
	}
	
	
	/**
	 * Gets specific Customer and its Account/s.
	 * @param pNo = personal ID number.
	 * @return new ArrayList of Customer Account info, else null.
	 */
	public ArrayList<String> getCustomer(String pNo) {
		if(customerAccountHandler.getCustomer(pNo) != null) {
			System.out.println(customerAccountHandler.getCustomer(pNo));
			return customerAccountHandler.getCustomer(pNo);
		} else {
			return null; 	
		}
	}
	
	
	/**
	 * Gets a list with all customers in Bank.
	 * @return new ArrayList of customers, else empty ArrayList.
	 */
	public ArrayList<String> getAllCustomers() {
		System.out.println(customerAccountHandler.getAllCustomers());
		return customerAccountHandler.getAllCustomers();
	}
	
	
	/**
	 * Gets a String of specific Customer and specific Account.
	 * @param pNo = personal ID number.
	 * @param accountId = 4 digit Account number.
	 * @return String of Account info, else null if Account non-existant or belongs to another Customer.
	 */
	public String getAccount(String pNo, int accountId) {
		String account = customerAccountHandler.getAccount(pNo, accountId);
		System.out.println(account);
		return account;
	}
	
	
	/**
	 * Gets a List of transactions strings.
	 * @param pNo = personal ID number.
	 * @param accountId = 4 digit Account number.
	 * @return new ArrayList of transaction Strings, else null.
	 */
	public ArrayList<String> getTransactions(String pNo, int accountId) {
		return customerAccountHandler.getTransactions(pNo, accountId);
	}
	
	
	/**
	 * Deletes specific Account from specific Customer.
	 * @param pNo = personal ID number.
	 * @param accountId = 4 digit Account number.
	 * @return String of Account info (including calculated interest) for specific Customer, else null.
	 */
	public String closeAccount(String pNo, int accountId) {
		String result = customerAccountHandler.closeAccount(pNo, accountId);
		return result;
	}
	
	
	/**
	 * Delete Customer and get an ArrayList of Customer and its Account/s.
	 * @param pNo = personal ID number.
	 * @return new ArrayList of Customer info and its Account/s (including calculated interest), else null.
	 */
	public ArrayList<String> deleteCustomer(String pNo) {
		ArrayList<String> deleted = customerAccountHandler.deleteCustomer(pNo);
	    System.out.println(deleted);
	    return deleted;
	}
	
	// ***************************************************************
	// ***************************************************************
	// Helper methods
	
	/**
	 * Checks pNo format and length.
	 * @param pNo = personal ID number.
	 * @return true if 10 integers, else false.
	 */
	public boolean isPno(String pNo) {
	    if (pNo.length() != 10) {
	        return false;
	    }
	    for (int i = 0; i < pNo.length(); i++) {
	        if (!Character.isDigit(pNo.charAt(i))) {
	            return false;
	        }
	    }
	    return true;
	}
	
	// ***************************************************************
	// ***************************************************************
	// Created for JavaFX GUI
	
	/**
	 * Gets all accounts from a customer.
	 * @param pNo = personal ID number.
	 * @return ArrayList of accounts from customer, null if customer not found or customer has no account.
	 */
	public ArrayList<String> getCustomerAccounts(String pNo) {
		ArrayList<String> customerAccounts = customerAccountHandler.getCustomerAccounts(pNo);
		return customerAccounts;
	}
	
	/**
	 * Get customers accounts objects.
	 * @param pNo = personal ID number.
	 * @return Account objects.
	 */
	public ArrayList<Account> getCustomerAccountObjects(String pNo) {
		ArrayList<Account> customerAccounts = customerAccountHandler.getCustomerAccountObjects(pNo);
		return customerAccounts;
	}
	
	/**
	 * Check if customer exist.
	 * @param pNo = personal ID number.
	 * @return true if Customer exist.
	 */
	public boolean customerExist(String pNo) {
		return customerAccountHandler.customerExist(pNo);
	}
	
	
	// ***************************************************************
	// ***************************************************************
	// Save, load, read, write
	
	/**
	 * Save bank data to byte file.
	 * @param path = path to file in file save directory.
	 */
	public void saveBankData(String path) throws NoSuchFileException, NotSerializableException, FileNotFoundException, ClassNotFoundException, IOException {
		saveLoadBank.saveBank(customerAccountHandler, path);
	}
	
	/**
	 * Load bank data from byte file.
	 * @param path = path to chosen file in file chooser directory.
	 */
	public void loadBankData(String path) throws NotSerializableException, FileNotFoundException, ClassNotFoundException, IOException {
		saveLoadBank.loadBank(customerAccountHandler, path);
	}
	
	/**
	 * Saves transactions as .txt-file.
	 * @param pNo = personal ID number.
	 * @param accountId = 4 digit Account number.
	 */
	public void saveStatement(String pNo, int accountId) throws FileNotFoundException, IOException {
		accountStatement.saveStatement(customerAccountHandler, pNo, accountId);
	}
	
	/**
	 * Load transactions from .txt-file.
	 * @param file = path.
	 * @return statement as String .
	 */
	public String loadStatement(String file) throws FileNotFoundException, IOException {
		return accountStatement.loadStatement(file);
	}
	
	
	
}
