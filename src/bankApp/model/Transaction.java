/**
 * Class Transaction represents transaction data.
 */

package bankApp.model;

import java.io.ObjectInputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Transaction implements Serializable {
	private static final long serialVersionUID = 7412532974034672152L;
	private final LocalDateTime dateTime;
	private final BigDecimal amount;
	private final BigDecimal balance;
	private static final Locale SV_SE = Locale.of("sv", "SE");
	private NumberFormat numberFormat;
	private NumberFormat percent;
	private transient DateTimeFormatter formatter;
	
	/**
	 * Constructor.
	 * @param dateTime = LocalDateTime object
	 * @param amount = amount
	 * @param balance = balance
	 */
	public Transaction(LocalDateTime dateTime, BigDecimal amount, BigDecimal balance) {
		this.dateTime = LocalDateTime.now();
		this.amount = amount;
		this.balance = balance;
		this.numberFormat = NumberFormat.getCurrencyInstance(SV_SE);
		this.percent = NumberFormat.getPercentInstance(SV_SE);

		// Oracle: https://www.oracle.com/technical-resources/articles/java/serializationapi.html
		createFormatter();
	}
	
	/**
	 * Date formatter.
	 */
	private void createFormatter() {
		formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	}
	
	
	/**
	 * Pseudo constructor recreating serialization.
	 * Restore serialization.
	 * Method used for customization needed in the serialization process.
	 * Oracle: https://www.oracle.com/technical-resources/articles/java/serializationapi.html  
	 * @param in ObjectInputStream.
	 * @throws Exception e.
	 */
    private void readObject(ObjectInputStream in) throws Exception {
        in.defaultReadObject();
        createFormatter();
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
	 * toString Transaction.
	 * @return date time, amount, balance.
	 */
	@Override
	public String toString() {
		String strDate = dateTime.format(formatter);
		return String.format("%s %s %s %s" ,
				strDate,
				getCurrencyFormat().format(amount),
				"Saldo:",
				getCurrencyFormat().format(balance));
	}
	
	
}
