/**
 * Class representing Customer.
 */

package bankApp.model;

import java.io.Serializable;
import java.util.ArrayList;


public class Customer implements Serializable{
	private static final long serialVersionUID = -6749556131425885817L;
	private String name;
	private String surname;
	private String pNo = "";
	private ArrayList<Account> accounts = new ArrayList<>();
	
	
	/**
	 * Constructor.
	 * @param name name.
	 * @param surname surname.
	 * @param pNo pNo.
	 */
	public Customer(String name, String surname, String pNo) {
		this.name = name;
		this.surname = surname;
		this.pNo = pNo;
	}

	
	/**
	 * Getter pNo.
	 * @return pNo.
	 */
	public String getPno() {
		return pNo;
	}
	
	
	/**
	 * Setter name.
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	
	/**
	 * Getter name.
	 * @return name.
	 */
	public String getName() {
		return name;
	}
	
	
	/**
	 * Setter surname.
	 */
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	
	/**
	 * Getter surname.
	 * @return surname.
	 */
	public String getSurname() {
		return surname;
	}
	
	
	/**
	 * Add Account to accounts.
	 * @param account = Account.
	 */
	public void addAccount(Account account) {
		accounts.add(account);
	}
	
	
	/**
	 * Getter accounts list.
	 * @return accounts.
	 */
	public ArrayList<Account> getAccounts() {
		return accounts;
	}


	/**
	 * toString.
	 * @return pNo, name, surname.
	 */
	public String toString() {
		return String.format("%s %s %s", pNo, name, surname);
	}

	
}


