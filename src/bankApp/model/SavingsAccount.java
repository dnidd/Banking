/**
 * Subclass SavingsAccount extends abstract class Account.
 */

package bankApp.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class SavingsAccount extends Account{
	private static final long serialVersionUID = -4428123649744438218L;
	private final BigDecimal ACCOUNT_CLOSURE_INTEREST;
	private final BigDecimal ADDITIONAL_WITHDRAW_INTEREST;
	private BigDecimal interest;
	private final int FREE_ANNUAL_WITHDRAWS = 1;
	private int withdrawCounter;
	private LocalDateTime latestWithdrawDate;
	private final String ACCOUNT_TYPE = "Sparkonto";
	
	
	/**
	 * Constructor
	 */
	public SavingsAccount() {
		this.ACCOUNT_CLOSURE_INTEREST = new BigDecimal("0.024");
		this.ADDITIONAL_WITHDRAW_INTEREST = new BigDecimal("0.02");
		this.withdrawCounter = 0;
		this.latestWithdrawDate = null;
	}
	
	
	/**
	 * Deposit to account.
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
	 * Withdraw for free if number of withdraws per year in range, account balance > withdraw.
	 * Withdraw with fee if number of withdraws per year out of range.
	 * Set new balance including fee.
	 * @param amount amount.
	 * @return true if success, else false.
	 */
	public boolean withdraw(BigDecimal amount) {
		// get now date & time.
		LocalDateTime now = LocalDateTime.now();
		
		// If latest withdraw not yet set or not this year —> new year and 0 withdraws.
		if(latestWithdrawDate == null || latestWithdrawDate.getYear() != now.getYear() ) {
			withdrawCounter = 0;
		}
		
		BigDecimal newBalance = super.getBalance();
		
		// Free withdraw.
		if(withdrawCounter < FREE_ANNUAL_WITHDRAWS) {
			// check if withdraw is possible with balance.
			if(newBalance.compareTo(amount) < 0) {
				System.out.println("Amount is greater than balance.");
				return false;
			} else {
				// calculate balance without fee.
				newBalance = newBalance.subtract(amount);
				
				withdrawCounter++;
				// set date & time.
				latestWithdrawDate = now;
				
				super.setBalance(newBalance);
				// create + add transaction.
				super.addTransaction(new Transaction(now, amount.negate(), newBalance));
				return true;
			}
			
		// withdraw with fee.
		} else if(withdrawCounter >= FREE_ANNUAL_WITHDRAWS) {
			// calculate withdraw amount + fee.
			BigDecimal additionalFee = amount.multiply(ADDITIONAL_WITHDRAW_INTEREST);
			BigDecimal totalWithdraw = amount.add(additionalFee);
			
			// check if withdraw + fee is possible with balance.
			if(newBalance.compareTo(totalWithdraw) < 0) {
				System.out.println("Amount + additional fee is greater than balance.");
				return false;
			} else {
				newBalance = newBalance.subtract(totalWithdraw);
				
				super.setBalance(newBalance);
				
				withdrawCounter++;
				// set date & time.
				latestWithdrawDate = now;
				
				// create + add transaction, fee included.
				super.addTransaction(new Transaction(now, totalWithdraw.negate(), newBalance));
				return true;
			}
		}
		
		return false;
		
		}
	
	
	/**
	 * to String.
	 * @return Account number, balance, Account type, interest.
	 */
	@Override
	public String toString() {
		return String.format("%d %s %s %s",
				super.getAccountNumber(),
				super.getCurrencyFormat().format(super.getBalance()),
				ACCOUNT_TYPE,
				super.getPercentFormat().format(ACCOUNT_CLOSURE_INTEREST)
				);
	}

	
	/**
	 * Account closure to String.
	 * @return Account number, balance, Account type, interest.
	 */
	@Override
	public String toCloseAccountString() {
		interest = super.getBalance().multiply(ACCOUNT_CLOSURE_INTEREST);
		
		return String.format("%d %s %s %s",
				super.getAccountNumber(),
				super.getCurrencyFormat().format(super.getBalance()),
				ACCOUNT_TYPE,
				super.getCurrencyFormat().format(interest)
				);
	}
	
	
}
