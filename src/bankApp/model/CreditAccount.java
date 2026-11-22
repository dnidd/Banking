/**
 * Subclass CreditAccount extends abstract class Account.
 */

package bankApp.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CreditAccount extends Account {
	private static final long serialVersionUID = -5661894085731755683L;
	private final BigDecimal ACCOUNT_CLOSURE_INTEREST;
	private final BigDecimal DEBT_INTEREST;
	private final BigDecimal CREDIT_BOUNDRY;
	private final String ACCOUNT_TYPE = "Kreditkonto";
	
	/**
	 * Constructor
	 */
	public CreditAccount() {
		this.ACCOUNT_CLOSURE_INTEREST = new BigDecimal("0.011");
		this.DEBT_INTEREST = new BigDecimal("0.05");
		this.CREDIT_BOUNDRY = new BigDecimal("-5000");
	}
	
	/**
	 * Deposit.
	 */
	public void deposit(BigDecimal amount) {
		// get now date & time
		LocalDateTime now = LocalDateTime.now();
		
		super.setBalance(super.getBalance().add(amount));
		BigDecimal newBalance = super.getBalance();
		// create + add transaction
		super.addTransaction(new Transaction(now, amount, newBalance));
	}
	
	
	/**
	 * Withdraw.
	 */
	public boolean withdraw(BigDecimal amount) {
		// get now date & time
		LocalDateTime now = LocalDateTime.now();
		BigDecimal newBalance = super.getBalance().subtract(amount);
		
		// if amount greater than credit
		if(newBalance.compareTo(CREDIT_BOUNDRY) < 0) {
			System.out.println("Amound exceeds credit (-5000).");
			return false;
		} else {
			super.setBalance(newBalance);
			// create + add transaction
			super.addTransaction(new Transaction(now, amount.negate(), newBalance));
			return true;
		}
	}
	
	
	/**
	 * to String.
	 * @return Account number, balance, Account type, interest.
	 */
	@Override
	public String toString() {
		BigDecimal balance = super.getBalance();
		BigDecimal rate;
		
		// Control which fee to use based on balance
		if(balance.compareTo(BigDecimal.ZERO) < 0) {
			rate = DEBT_INTEREST;
		} else {
			rate = ACCOUNT_CLOSURE_INTEREST;
		}
		
		return String.format("%d %s %s %s",
				super.getAccountNumber(),
				super.getCurrencyFormat().format(balance),
				ACCOUNT_TYPE,
				super.getPercentFormat().format(rate)
				);
	}
	
	
	/**
	 * toString for Account closure.
	 * @return Account number, balance, Account type, interest.
	 */
	@Override
	public String toCloseAccountString() {
		BigDecimal originalBalance = super.getBalance();
		BigDecimal interestAmount;
		
		// Control which fee to calculate
		if(originalBalance.compareTo(BigDecimal.ZERO) < 0) {
			interestAmount = originalBalance.abs().multiply(DEBT_INTEREST).negate();
		} else {
			interestAmount = originalBalance.multiply(ACCOUNT_CLOSURE_INTEREST);
		}
		return String.format("%d %s %s %s",
				super.getAccountNumber(),
				super.getCurrencyFormat().format(originalBalance),
				ACCOUNT_TYPE,
				super.getCurrencyFormat().format(interestAmount)
				);
	}
	

}











