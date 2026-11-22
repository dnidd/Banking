/**
 * Abstract Class Account used as base class for subclasses.
 */

package bankApp.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public abstract class Account implements Serializable {
	private static final long serialVersionUID = 838571793103549341L;
	private BigDecimal balance;
	private static int accountNumberCounter = 1001;
	private final int accountNumber;
	private static final Locale SV_SE = Locale.of("sv", "SE");
	private NumberFormat numberFormat;
	private NumberFormat percent;
	private ArrayList<Transaction> transactions;
	
	
	/**
	 * Constructor.
	 * Increments account number.
	 */
	public Account() {
		// Set balance to 0 at creation.
		this.balance = BigDecimal.ZERO;
		// Account number increment.
		this.accountNumber = accountNumberCounter++;
		this.numberFormat = NumberFormat.getCurrencyInstance(SV_SE);
		this.percent = NumberFormat.getPercentInstance(SV_SE);
		this.transactions = new ArrayList<>();
	}
	
	// *****************
	// Abstract methods.
	
	/**
	 * Deposit to account.
	 * @param amount = amount to deposit.
	 */
	public abstract void deposit(BigDecimal amount);
	
	
	/**
	 * Withdraw from account.
	 * @param amount = amount to withdraw.
	 * @return true if successful, else false.
	 */
	public abstract boolean withdraw(BigDecimal amount);
	
	
	/**
	 * Calculate and present Account.
	 * @return Account number, balance, Account type, interest.
	 */
	public abstract String toCloseAccountString();

	
	// ********************
	// Non abstract methods
	
	
	/**
	 * Sets balance when deposit or withdraw.
	 * @param amount = amount.
	 */
	public void setBalance(BigDecimal amount) {
		this.balance = amount;
	}
	
	
	/**
	 * Getter balance.
	 * @return balance.
	 */
	public BigDecimal getBalance() {
		return balance;
	}
	
	
	/**
	 * Getter account number.
	 * @return account number.
	 */
	public int getAccountNumber() {
		return accountNumber;
	}
	
	
	/**
	 * Method used for save/load file.
	 * Getter current increment of new account number for file.
	 * @return accountNumberCounter.
	 */
	public static int getAccountNumberCounter() {
		return accountNumberCounter;
	}
	
	/**
	 * Method used for save/load file.
	 * Setter account number from file.
	 * @param accountNumber = account number in save file.
	 */
	public static void setAccountNumberCounter(int accountNumber) {
		accountNumberCounter = accountNumber;
	}
	
	
	/**
	 * Add Transaction to transactions.
	 * @param transaction = Transaction.
	 */
	public void addTransaction(Transaction transaction) {
		transactions.add(transaction);
	}

	
	/**
	 * Getter transactions ArrayList.
	 * @return transactions.
	 */
	public ArrayList<Transaction> getTransactions() {
		return transactions;
	}
	
	
	/**
	 * Getter currency.
	 * @return currency instance of type NumberFormat.
	 */
	public NumberFormat getCurrencyFormat() {
		return numberFormat;
	}
	
	
	/**
	 * Getter percent.
	 * @return percent instance of type NumberFormat.
	 */
	public NumberFormat getPercentFormat() {
		percent.setMaximumFractionDigits(1);
		return percent;
	}
	
	
	/**
	 * to String.
	 * @return account number + balance.
	 */
	public String toString() {
		return String.format("%d %s %s %s", accountNumber, getCurrencyFormat().format(balance));
	}

}
