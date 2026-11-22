/**
 * Class holds and handles customers accounts.
 */

package bankApp.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public class CustomerAccountHandler implements Serializable {
	private static final long serialVersionUID = -1981150503906189320L;
	private Map<String, Customer> customers;
	
	/**
	 * Constructor instantiates HashMap.
	 */
	public CustomerAccountHandler() {
		this.customers = new LinkedHashMap<>();
	}
	
	/**
	 * Create customer.
	 * @param customer = Customer.
	 * @return true if Customer did not exist.
	 */
	public boolean createCustomer(Customer customer) {
		// If Customer exist.
		if(customers.containsKey(customer.getPno())) { // .get() gets key, (pNo)
			return false;
		}
		// Put pNo, customer.
		customers.put(customer.getPno(), customer);
		return true;
	}
	
	
	/**
	 * Create account.
	 * @param pNo = personal ID number.
	 * @param account = generic Account.
	 * @return account number, -1 if non existing customer. 
	 */
	public int createAccount(String pNo, Account account) {
		
		if(!customers.containsKey(pNo)) {
			return -1;
		}
		
		// Find Customer based on pNo.
		Customer customer = customers.get(pNo);
		// Add Account to Customer account list.
		customer.addAccount(account);
		
		return account.getAccountNumber();
	}
	
	
	/**
	 * Deposit to account.
	 * @param pNo = personal ID number.
	 * @param accountId = 4 digit Account number.
	 * @param amount = amount.
	 * @return true if deposit successful, else false.
	 */
	public boolean deposit(String pNo, int accountId, int amount) {
		Account account = findCustomerAccount(pNo, accountId);
		// If account found -> deposit.
		if(account != null) {
			account.deposit(BigDecimal.valueOf(amount));
			return true;
		}
		return false;
	}
	
	
	/**
	 * Withdraw from specific Customer and specific Account.
	 * @param pNo = personal ID number.
	 * @param accountId = 4 digit Account number.
	 * @param amount = integer to withdraw from Account.
	 * @return true if withdrawal successful, else false.
	 */
	public boolean withdraw(String pNo, int accountId, int amount) {
		Account account = findCustomerAccount(pNo, accountId);
		// if account found -> withdraw
		if(account != null) {
			return	account.withdraw(BigDecimal.valueOf(amount));
		}
		return false;
	}
	
	
	/**
	 * Change customer name.
	 * @param name = first name.
	 * @param surname = last name.
	 * @param pNo = personal ID number.
	 * @return true if name changed, else false.
	 */
	public boolean changeCustomerName(String name, String surname, String pNo) {
		boolean nameChange = false;
		Customer customer = findCustomer(pNo);
		
		if(customer != null) {
			if(name.equals("") && !surname.equals("")) {
				System.out.println("\nLast name changed from " + customer.getSurname() + " to " + surname + "\n");
				customer.setSurname(surname);
				nameChange = true;
			}
			// if !name & surname.
			else if (!name.equals("") && surname.equals("")) {
				System.out.println("\nFirst name changed from " + customer.getName() + " to " + name + "\n");
				customer.setName(name);
				nameChange = true;
			}
			// if !name & !surname.
			else if(!name.equals("") && !surname.equals("")) {
				System.out.println("\nName changed from " + customer.getName() + " " + customer.getSurname() + " to " + name + " " + surname + "\n");
				customer.setName(name);
				customer.setSurname(surname);
				nameChange = true;
			}
			// name.equals(null) && surname.equals(null).
			else  {
				System.out.println("\nNo names changed\n");
				nameChange = false;
			}
		}
		return nameChange;
	}
	
	
	/**
	 * Get specific Customer and its Account/s.
	 * @param pNo = personal ID number.
	 * @return new ArrayList of Customer & Account/s, else null.
	 */
	public ArrayList<String> getCustomer(String pNo) {
		Customer customer;
		ArrayList<Account> accounts = new ArrayList<>();
		ArrayList<String> customerAccounts = new ArrayList<>();
		
		// if Customer pNo -> get specific info
		if(findCustomer(pNo) != null) {
			customer = findCustomer(pNo);
			accounts = customer.getAccounts();
			customerAccounts.add(customer.toString());
			for(Account acc : accounts) {
				customerAccounts.add(acc.toString());	
			}
			return customerAccounts;
		}
		return null;
	}
	
	
	/**
	 * Get a list with all customers in Bank.
	 * @return new ArrayList of customers, else empty ArrayList.
	 */
	public ArrayList<String> getAllCustomers() {
		ArrayList<String> allCustomers = new ArrayList<>();
		
		// if data structure not empty -> get specific info per Customer
		if(!customers.isEmpty()) {
			for (Entry<String, Customer> entry : customers.entrySet()) {
			    String pNo = entry.getKey();
			    Customer customer = entry.getValue();
			    String name = customer.getName();
			    String surName = customer.getSurname();

			    allCustomers.add(pNo + " " + name + " " + surName);
			}
			return allCustomers;
		}
		return allCustomers;
	}
	
	
	/**
	 * Get customer account.
	 * @param pNo = personal ID number.
	 * @param accountId = 4 digit Account number.
	 * @return Customer String, else null.
	 */
	public String getAccount(String pNo, int accountId) {
		Account account = findCustomerAccount(pNo, accountId);
		
		// if Customer Account not found, else return Customer info.
		if(account == null) {
			return null;
		}
		return account.toString();
	}
	
	
	/**
	 * Get customer transactions.
	 * @param pNo = personal ID number.
	 * @param accountId = 4 digit Account number.
	 * @return new ArrayList of transaction Strings, else null.
	 */
	public ArrayList<String> getTransactions(String pNo, int accountId) {
		Account account = findCustomerAccount(pNo, accountId);
		if(account == null ) {
			return null;
		}
		ArrayList<Transaction> transactions = new ArrayList<>();
		// Get all Customer transactions.
		transactions = account.getTransactions();
		ArrayList<String> customerTransactions = new ArrayList<>();
		
		// Get all Customer transactions.toString().
		for(int i = 0; i < transactions.size(); i++) {
			customerTransactions.add(transactions.get(i).toString());
		}
		return customerTransactions;
	}
	
	
	/**
	 * Deletes specific Account from specific Customer.
	 * @param pNo = personal ID number.
	 * @param accountId = 4 digit Account number.
	 * @return String of Account info (including calculated interest) for specific Customer, else null.
	 */
	public String closeAccount(String pNo, int accountId) {
		Customer customer = findCustomer(pNo);
		
		// No Customer found.
		if(customer == null) {
			System.out.println("No Customer");
			return null;
		}
		
		// No Customer Account/s found.
		ArrayList<Account> accounts = customer.getAccounts();
		if(accounts == null || accounts.isEmpty()) {
			return null;
		}
		
		// Get Customer Account/s info as String.
		for(int i = 0; i < accounts.size(); i ++) {
			Account acc = accounts.get(i);
			if(acc.getAccountNumber() == accountId) {
				String account = acc.toCloseAccountString();
				accounts.remove(i);
				return account;
			}
		}
		// No Account.
		return null;
	}
	
	
	/**
	 * Delete Customer and get an ArrayList of Customer and its Account/s.
	 * @param pNo = personal ID number.
	 * @return new ArrayList of Customer info and its Account/s (including calculated interest), else null.
	 */
	public ArrayList<String> deleteCustomer(String pNo) {
		ArrayList<String> customerAccounts = new ArrayList<>();
		
		// if Customer in data structure.
		if(customers.containsKey(pNo)) {
			Customer customer = customers.remove(pNo);
			ArrayList<Account> accounts = customer.getAccounts();
			
			// add Customer info.
			customerAccounts.add(customer.toString());
			
			// Customer have Account/s.
			if(accounts != null && !accounts.isEmpty()) {
				
				// add Account/s.
				for(int i = 0; i < accounts.size(); i++) {
					Account acc = accounts.get(i);
					if(acc instanceof SavingsAccount) {
						customerAccounts.add(((SavingsAccount) acc).toCloseAccountString());
					} else if(acc instanceof CreditAccount) {
						customerAccounts.add(((CreditAccount) acc).toCloseAccountString());
					}
				}
			}
		} else if(!customers.containsKey(pNo)) {
			customerAccounts = null;
		}
		return customerAccounts;
	}
	
	// ***************************************************************
	// ***************************************************************
	// Created for JavaFX GUI
	
	/**
	 * Gets all accounts from a customer .
	 * @param pNo = personal ID number.
	 * @return ArrayList of accounts from customer, null if customer not found or customer has no account.
	 */
	public ArrayList<String> getCustomerAccounts(String pNo) {
		ArrayList<String> customerAccounts = new ArrayList<>();
		
		Customer customer = findCustomer(pNo);
		
		// No Customer found.
		if(customer == null) {
			System.out.println("No Customer");
			return null;
		}
		// get accounts.
		ArrayList<Account> accounts = customer.getAccounts();
		
		// No Customer Account/s found.
		if(accounts == null || accounts.isEmpty()) {
			return null;
		}
		
		// Get Customer Account/s info as ArrayList<String> customerAccounts.
		for(int i = 0; i < accounts.size(); i++) {
			Account acc = accounts.get(i);
			String account = acc.toString();
			customerAccounts.add(account);
		}
		// No Account.
		return customerAccounts;
	}
	
	/**
	 * Gets all Accounts from a customer.
	 * @param pNo = personal ID number.
	 * @return ArrayList of Account objects from customer, null if customer not found or customer has no account.
	 */
	public ArrayList<Account> getCustomerAccountObjects(String pNo) {
		Customer customer = findCustomer(pNo);
		
		// No Customer found.
		if(customer == null) {
			System.out.println("No Customer");
			return null;
		}
		
		// get accounts.
		ArrayList<Account> accounts = customer.getAccounts();
		
		// No Customer Account/s found.
		if(accounts == null || accounts.isEmpty()) {
			return null;
		}
		return accounts;
	}
	
	// ***************************************************************
	// ***************************************************************
	// Created for saving and loading file
	
	/**
	 * Getter used to store customers 
	 * in object to be be saved in BankData.
	 * @return customers HashMap.
	 */
	public Map<String, Customer> getCustomers() {
		return customers;
	}
	
	
	/**
	 * Clear customers.
	 * Used when loading file and populated Bank.
	 */
	public void clearCustomers() {
		customers.clear();
	}
	
	/**
	 * Populate customers from loaded file.
	 * @param customers = Map customers.
	 */
	public void setLoadedCustomers(Map<String, Customer> customers) {
		// 'this' because this instance.
		this.customers.putAll(customers);
	}
	
	
	/**
	 * Get balance.
	 * @param pNo = personal ID number.
	 * @param accountId = 4 digit Account number.
	 * @return account balance.
	 */
	public BigDecimal getBalance(String pNo, int accountId) {
		Account account = findCustomerAccount(pNo, accountId);
		BigDecimal balance= account.getBalance();
		return balance;
	}
	
	
	// ***************************************************************
	// ***************************************************************
	// Helper methods
	
	
	/**
	 * Check if customer exist.
	 * @param pNo = personal ID number.
	 * @return true if Customer exist.
	 */
	public boolean customerExist(String pNo) {
		return customers.containsKey(pNo);
	}
	
	
	/**
	 * Find Customer Account.
	 * @param pNo = personal ID number.
	 * @param accountId = 4 digit Account number.
	 * @return Customer Account or null
	 */
	private Account findCustomerAccount(String pNo, int accountId) {
		Account account = null;
		
		// if Customer in data structure.
		if(customers.containsKey(pNo)) {
			// get Customer.
			Customer customer = customers.get(pNo);
			// get Customer Account/s.
			ArrayList<Account> accounts = customer.getAccounts();
			// find Account.
			for(Account acc : accounts) {
				if(acc.getAccountNumber() == accountId) {
					account = acc;
				}
			}
		}
		return account;
	}
	
	
	/**
	 * Find Customer.
	 * @param pNo = personal ID number.
	 * @return Customer, else null.
	 */
	public Customer findCustomer(String pNo) {
		Customer customer;
		// if Customer in data structure.
		if(customers.containsKey(pNo)) {
			// find Customer.
			customer = customers.get(pNo);
			return customer;
		}
		return null;
	}
	

}





















