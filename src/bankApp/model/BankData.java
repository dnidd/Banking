/**
* Class holding data to be saved as one object using ObjectOutputStream.
*/

package bankApp.model;

import java.io.Serializable;
import java.util.Map;


public class BankData implements Serializable{
	private static final long serialVersionUID = -1676118569258777357L;
	private Map<String, Customer> customers;
	private int currentAccountNumber;

	/**
	 * Constructor.
	 * @param customers customers.
	 * @param accountNumber accountNumber.
	 */
	public BankData( Map<String, Customer> customers, int accountNumber) {
		this.customers = customers;
		this.currentAccountNumber = accountNumber;
	}
	
	/**
	 * Getter customers.
	 * @return customers.
	 */
	public Map<String, Customer> getCustomers() {
		return customers;
	}
	
	/**
	 * Getter account number.
	 * @return current account number.
	 */
	public int getAccountNumber() {
		return currentAccountNumber;
	}

}
